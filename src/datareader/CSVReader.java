package datareader;

/**
 * 
 * CSVReader.java
 * 
 * 
 * */

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Tuple;

/**
 * The {@link CSVReader} is a class that will parse a CSV file and dynamically
 * determine what values to set in the list of requested object type. It does
 * this by keying off of the name of a column of data (hopefully column names
 * are formatted in a way that they can be used) then appends set to the name.
 * It will then proceed to search the class for a method that will matches and
 * accepts a string as the parameter.
 * 
 * @author Nathaniel Cotton
 * 
 */
public class CSVReader {

	/**
	 * Gives an input stream to the CSV file, and the class that is requested to
	 * come out.
	 * 
	 * *** Note that this method will assume that there is a header ***
	 * 
	 * @param ifs
	 *            input stream to the file
	 * @param rowClass
	 *            requested class
	 * @return List of the class passed in
	 */
	public static <T> List<T> parse(InputStream ifs, Class<T> rowClass) {
		return parse(ifs, rowClass, true);
	}

	/**
	 * Gives an input stream to the CSV file, and the class that is requested to
	 * come out.
	 * 
	 * 
	 * @param ifs
	 *            input stream to the file
	 * @param rowClass
	 *            requested class
	 * @param header
	 *            whether or not there is a header
	 * @return List of the requested class type
	 */
	public static <T> List<T> parse(InputStream ifs, Class<T> rowClass,
			boolean header) {
		try {
			List<T> lst = new ArrayList<T>();
			List<String> columns = new ArrayList<String>();
			Map<String, Tuple<Method, Class<?>>> methodMap = generateMethodMap(rowClass);
			Scanner scan = new Scanner(ifs);
			boolean first = true;
			while (scan.hasNext()) {
				String line = scan.nextLine();
				String[] brokenup = line.split(",");
				if (first && header) {
					// csv, column names, these will be used to populate the
					// object.
					for (String str : brokenup) {
						columns.add(str);
					}
					first = false;
				} else {
					T row = null;
					for (int i = 0; i < brokenup.length; ++i) {
						String str = brokenup[i].trim();
						if (rowClass == Integer.class
								|| rowClass == Float.class
								|| rowClass == Double.class
								|| rowClass == String.class) {
							Constructor<T> constructor = rowClass
									.getConstructor(String.class);
							row = constructor.newInstance(str);
						} else {
							if (row == null)
								row = rowClass.newInstance();
							Tuple<Method, Class<?>> methodData = methodMap
									.get(columns.get(i).toUpperCase());
							Object data = null;
							if (methodData.v2 == Boolean.class) {
								// to support boolean data objects that use 0 for false and 1 for true
								// TODO make a far more robust solution
								data = (str.equalsIgnoreCase("1") || str.equalsIgnoreCase("yes"));
							} else {
								Constructor<?> constructor = methodData.v2
										.getConstructor(String.class);
								data = constructor.newInstance(str);
							}
							methodData.v1.invoke(row, data);
						}
					}
					if (row != null)
						lst.add(row);
				}
			}
			scan.close();
			return lst;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method will analyze the passed in requested object for key
	 * annotations that will indicate to the parser where certain csv columns
	 * should be placed.
	 * 
	 * @param rowClass
	 * @return
	 */
	public static <T> Map<String, Tuple<Method, Class<?>>> generateMethodMap(
			Class<T> rowClass) {
		HashMap<String, Tuple<Method, Class<?>>> map = new HashMap<String, Tuple<Method, Class<?>>>();
		Method[] methods = rowClass.getDeclaredMethods();
		for (Method m : methods) {
			CSVValue annotation = m.getAnnotation(CSVValue.class);
			if (annotation != null) {
				Tuple<Method, Class<?>> tuple = new Tuple<Method, Class<?>>(m,
						annotation.ColumnType());
				map.put(annotation.ColumnName(), tuple);
			}
		}
		return map;
	}
}
