package model;

public class Distance {

	public static double l1(double[] point1, double[] point2) {
		double value = 0;
		if (point1.length != point2.length)
			throw new RuntimeException("Arrays are not of the same length");
		for (int i = 0; i < point1.length && i < point2.length; ++i) {
			value += Math.abs(point1[i] - point2[i]);
		}
		return value;
	}

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
