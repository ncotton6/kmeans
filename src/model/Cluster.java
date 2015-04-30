package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Cluster class
 */
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
     * Returns the center. The center must be generated, first.
     *
	 * @return the center
	 */
	public double[] getCenter() {
		return center;
	}

    /**
     * Generates a center
     *
     * @return center
     */
	public double[] generateCenter() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
            
        // Each method in Data corresponds to one "dimension"
		Method[] methods = Data.class.getDeclaredMethods();
		int size = 0;
		for (int i = 0; i < methods.length; ++i)
			if (methods[i].getAnnotation(UseAttribute.class) != null)
				++size;
		center = new double[size];
        
        // Make the center based on the methods in Data
		for (Data data : this) {
			size = 0;
			for (int i = 0; i < methods.length; ++i)
				if (methods[i].getAnnotation(UseAttribute.class) != null)
					center[size++] += (double) methods[i].invoke(data, params);
		}
        
        // Divide by amount of data and return
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
        // Generate center
		try {
			double[] center = generateCenter();
		} catch (Exception e){
			e.printStackTrace();
		}
        // For all data, add the square of the difference between
        // the data and the center above to a value, and return
        // the final value.
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
