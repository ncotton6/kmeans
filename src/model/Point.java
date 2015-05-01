package model;

import java.util.Arrays;

/**
 * This class is just to make comparing double arrays easier than converting to
 * Double class arrays for comparison. Which doesn't actually work too well
 * since the Double object arrays don't compare well.
 * 
 * @author Nathaniel Cotton
 * 
 */
public class Point {

	/* private array to represent the point */
	private double[] point;

	/**
	 * Simple constructor for the point class that will just take an array and
	 * set it.
	 * 
	 * @param p
	 */
	public Point(double[] p) {
		this.point = p;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(point);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (!Arrays.equals(point, other.point))
			return false;
		return true;
	}
}
