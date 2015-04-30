package model;

/**
 * Contains basic methods for calculating distance
 */
public class Distance {

    /**
     * Find the L1 norm (Manhattan distance) between two points.
     *
     * @param point1 First point
     * @param point2 Second point
     * @return L1 distance between two points
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
     * Find the L2 norm (Euclidean distance) between two points.
     *
     * @param point1 First point
     * @param point2 Second point
     * @return L2 distance between two points
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
