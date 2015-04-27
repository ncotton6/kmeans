package datareader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CSVValue.java
 * */

/**
 * This annotations only purpose is to help the CSV parser create the objects
 * from the entries correctly.
 * 
 * @author Nathaniel Cotton
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVValue {

	/**
	 * Helps the CSV parser to match up columns in the file with setting methods
	 * in the class.
	 * 
	 * @return
	 */
	String ColumnName();

	/**
	 * Tells the parser what kind of object should be created and passed to the
	 * setting method.
	 * 
	 * @return
	 */
	Class<?> ColumnType();

}
