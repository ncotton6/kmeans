package model;

/**
 * The {@link Distance} class will be used to make calculating distances far
 * easier.
 * 
 * @author Nathaniel Cotton
 * 
 */
public class Distance {

	/**
	 * Manhatten / L1 norm
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static double l1(double[] point1, double[] point2) {
		double value = 0;
		if (point1.length != point2.length)
			throw new RuntimeException("Arrays are not of the same length");
		for (int i = 0; i < point1.length && i < point2.length; ++i) {
			value += Math.abs(point1[i] - point2[i]);
		}
		return value;
	}

	/**
	 * Euclidean / L2 norm
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static double l2(double[] point1, double[] point2) {
		double value = 0;
		if (point1.length != point2.length)
			throw new RuntimeException("Arrays are not of the same length");
		for (int i = 0; i < point1.length && i < point2.length; ++i) {
			value += Math.pow(point1[i] - point2[i], 2);
		}
		return Math.sqrt(value);
	}

}
