package model;

/**
 * Tuple.java
 */

/**
 * {@link Tuple} is a helper class that can hold two random objects. This will
 * normally be used to draw an association between two objects, nesting tuples
 * can allow for more than one association.
 * 
 * @author Nathaniel Cotton
 * 
 * @param <T>
 * @param <H>
 */
public class Tuple<T, H> {

	// Once these variables are set in the constructor they cannot be changed.
	public final T v1;
	public final H v2;

	/**
	 * Simple constructor that allows for setting the tuple variables, although
	 * once they are set they cannot be changed.
	 * 
	 * @param v1
	 * @param v2
	 */
	public Tuple(T v1, H v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
}