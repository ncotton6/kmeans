package model;

import java.util.HashMap;

/**
 * Thread value that contains an sse value and cluster value.
 */
public class ThreadValue {

	private double sse = Double.MAX_VALUE;
	private HashMap<Integer, Cluster> cluster = null;

	/**
	 * @return the sse
	 */
	public double getSse() {
		return sse;
	}

	/**
	 * @param sse
	 *            the sse to set
	 */
	public void setSse(double sse) {
		this.sse = sse;
	}

	/**
	 * @return the cluster
	 */
	public HashMap<Integer, Cluster> getCluster() {
		return cluster;
	}

	/**
	 * @param cluster
	 *            the cluster to set
	 */
	public void setCluster(HashMap<Integer, Cluster> cluster) {
		this.cluster = cluster;
	}

	public synchronized void test(double sse, HashMap<Integer, Cluster> cluster) {
		if (sse < this.sse) {
			this.sse = sse;
			this.cluster = cluster;
		}
	}

}
