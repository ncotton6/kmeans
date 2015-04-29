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

	public Cluster(Data data) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		this.add(data);
		generateCenterAsObject();
	}

	/**
	 * @return the center
	 */
	public double[] getCenter() {
		return center;
	}

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

}
