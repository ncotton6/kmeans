import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import datareader.CSVReader;
import model.Data;
import model.Tuple;
import model.UseAttribute;

/**
 * The {@link Kmean} application will use the K-means clustering algorithm to
 * cluster medical data.
 * 
 * @author Nathaniel Cotton
 * 
 */
public class Kmean {

	private static List<Data> data = null;
	private static HashMap<Method, Tuple<Double, Double>> normMap = new HashMap<Method, Tuple<Double, Double>>();
	private static int[][] initSeed = new int[][] { new int[] { 8, 7 },
			new int[] { 3, 42 }, new int[] { 1, 1 }, new int[] { 9, 2 },
			new int[] { 2, 13 }, new int[] { 9, 18 }, new int[] { 2, 33 },
			new int[] { 9, 38 }, new int[] { 10, 51 } };

	/**
	 * Entry point into the application. No args will be used.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			FileInputStream fis = new FileInputStream(new File(
					"QANTAS420_DATA__v043_temp_for_class.csv"));
			data = CSVReader.parse(fis, Data.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (data == null) {
			System.out.println("For some reason the data is non existant");
			System.exit(1);
		}

		// running k = 9
		double[][] centers = new double[9][];
		
		// running k = 32
		centers = new double[32][];
	}
	
}
