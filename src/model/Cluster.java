package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Cluster extends ArrayList<Data> {

	private static final long serialVersionUID = 1L;
	private static Object[] params = new Object[] {};
	private double[] center = null;

	public Cluster() {
	}

    /**
     * 
     *
     * @throws IllegalAccessException
     */
	public Cluster(Data data) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		this.add(data);
		generateCenter();
	}

	/**
	 * @return the center
	 */
	public double[] getCenter() {
		return center;
	}

    /**
     * 
     */
	public double[] generateCenter() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method[] methods = Data.class.getDeclaredMethods();
		int size = 0;
		for (int i = 0; i < methods.length; ++i)
			if (methods[i].getAnnotation(UseAttribute.class) != null)
				++size;
		center = new double[size];
		for (Data data : this) {
			size = 0;
			for (int i = 0; i < methods.length; ++i)
				if (methods[i].getAnnotation(UseAttribute.class) != null)
					center[size++] += (double) methods[i].invoke(data, params);
		}
		for (int i = 0; i < center.length; ++i)
			center[i] /= this.size();
		return getCenter();
	}
	
    /**
     * Generates the sum of squared errors (SSE)
     *
     * @return SSE as double
     */
	public double sse(){
		double value = 0;
		try {
			double[] center = generateCenter();
		} catch (Exception e){
			e.printStackTrace();
		}
		for(Data d : this){
			double[] tempDist = null;
			try {
				tempDist = d.getAsPoint();
			} catch (Exception e){
				e.printStackTrace();
			}
			value += sqr(center, tempDist);
		}
		return value;
	}
    
    /**
     * Returns squared sum between two points. Basically 
     * Euclidean (L2) distance equation without the square root.
     *
     * @param a First point
     * @param b Second point
     * @return squared sum
     */
    private double sqr(double[] a, double[] b)
    {
        double n = 0;
        for(int i = 0; i < a.length; ++i)
        {
            n += (a[i]-b[i])*(a[i]-b[i]);
        }
        return n;
    }

}
