// imports from java
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// imports from classes to use in k-means
import datareader.CSVReader;
import model.Cluster;
import model.Data;
import model.Distance;
import model.Point;
import model.ThreadValue;
import model.Tuple;
import model.UseAttribute;
import model.Visualize;

/**
 * The {@link Kmean} application will use the K-means clustering algorithm to
 * cluster medical data.
 * 
 * @author Nathaniel Cotton
 * 
 */
public class Kmean {

	private static List<Data> data = null;
	private static HashMap<Method, Tuple<Double, Double>> normMap 
            = new HashMap<Method, Tuple<Double, Double>>();
	private static int[][] initSeeds = new int[][] { new int[] { 8, 7 },
			new int[] { 3, 42 }, new int[] { 1, 1 }, new int[] { 9, 2 },
			new int[] { 2, 13 }, new int[] { 9, 18 }, new int[] { 2, 33 },
			new int[] { 9, 38 }, new int[] { 10, 51 } };

	/**
	 * Entry point into the application. No args will be used.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

        // read/parse file
		try {
			FileInputStream fis = new FileInputStream(new File(
					"QANTAS420_DATA__v043_temp_for_class.csv"));
			data = CSVReader.parse(fis, Data.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

        // quit if no data
		if (data == null) {
			System.out.println("For some reason the data is nonexistent.");
			System.exit(1);
		}

        // The number of "dimensions" for the centers is equal to
        // the number of methods in the Data class.
		int tempCenterLength = 0;
		for (Method m : Data.class.getDeclaredMethods())
			if (m.getAnnotation(UseAttribute.class) != null)
				++tempCenterLength;
		final int centerLength = tempCenterLength;

		// running k = 9
		// setup base data points
		final HashSet<Data> baseset = new HashSet<Data>();
		double[][] centers = new double[9][centerLength];
		for (int i = 0; i < initSeeds.length; ++i) {
			try {
				Data d = lookUpPassenger(initSeeds[i][0], initSeeds[i][1]);
				centers[i] = d.getAsPoint();
				baseset.add(d);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// run visualizer for k = 9
		Visualize vis9 = new Visualize(runKmeans(centers, data));
		vis9.makeVisible();
		// running k = 39
		// setup
		final Random rand = new Random(100000);
        
        // Run k = 39 in multiple threads
		ExecutorService pool = Executors.newFixedThreadPool(8);
		final ThreadValue tv = new ThreadValue();
		for (int i = 0; i < 5000; ++i) {
			final int tempi = i;
			Runnable run = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(tempi);
					HashSet<Data> set = new HashSet<Data>();
					set.addAll(baseset);
					while (set.size() < 39) {
						set.add(data.get(rand.nextInt(data.size())));
					}
					double[][] centers = new double[39][centerLength];
					int index = 0;
					for (Data d : set) {
						try {
							centers[index++] = d.getAsPoint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// running
					HashMap<Integer, Cluster> tempCluster = runKmeans(centers,
							data);
					double sse = 0;
					for (Integer key : tempCluster.keySet()) {
						sse += tempCluster.get(key).sse();
					}
					tv.test(sse, tempCluster);
				}
			};
			pool.execute(run);
		}
		try {
			pool.shutdown();
			pool.awaitTermination(4, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // run visualizer for k = 39
        
		System.out.println("SSE: " + tv.getSse());
		outputClusters(tv.getCluster());
        
		Visualize vis39 = new Visualize(tv.getCluster());
		vis39.makeVisible();
	}

	/**
	 * Runs the Kmeans algorithm on the data that is provided.
	 * 
	 * @param centers
	 * @param data2
	 * @return
	 */
	private static HashMap<Integer, Cluster> runKmeans(double[][] centers,
			List<Data> data) {
		// first run to get start data
		HashMap<Integer, Cluster> clusters = new HashMap<Integer, Cluster>();
		for (Data d : data) {
			Integer closest = getClosestPoint(centers, d);
			if (!clusters.containsKey(closest))
				clusters.put(closest, new Cluster());
			clusters.get(closest).add(d);
		}
		// Generate initial centers
		for (int i = 0; i < centers.length; ++i) {
			try {
				centers[i] = clusters.get(i).generateCenter();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// repeatedly run until stop condition
		int count = 0;
		while (true) {
			HashMap<Integer, Cluster> cluster2 = new HashMap<Integer, Cluster>();
			// Get closest point and add to cluster2
			for (Data d : data) {
				Integer closest = getClosestPoint(centers, d);
				if (!cluster2.containsKey(closest))
					cluster2.put(closest, new Cluster());
				cluster2.get(closest).add(d);
			}
			// Stop condition
			if (identicalSets(clusters, cluster2, centers.length))
				break;
			clusters = cluster2;
			// Generate centers
			for (int i = 0; i < centers.length; ++i) {
				try {
					centers[i] = clusters.get(i).generateCenter();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// System.out.println("Iteration: " + count++);
		}
		// output the cluster data
		// outputClusters(clusters);
		// System.out
		// .println("====================\n====================\n====================");
		return clusters;
	}

	/**
	 * Tests if two clusters are identical.
	 * 
	 * @param cluster1
	 *            first cluster to test
	 * @param cluster2
	 *            second cluster to test
	 * @return true (for now)
	 */
	private static boolean identicalSets(HashMap<Integer, Cluster> cluster1,
			HashMap<Integer, Cluster> cluster2, int count) {
		HashSet<Point> centers = new HashSet<Point>();
		for (Integer key : cluster1.keySet()) {
			try {
				centers.add(new Point(cluster1.get(key).generateCenter()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Integer key : cluster2.keySet()) {
			try {
				centers.add(new Point(cluster2.get(key).generateCenter()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return centers.size() == count;
	}

	/**
	 * Prints information for clusters for a given set of clusters
	 * 
	 * @param clusters
	 *            The collection of clusters
	 */
	private static void outputClusters(HashMap<Integer, Cluster> clusters) {
		// Gets the key set for a cluster as an Integer array, and sorts it
		Integer[] keys = clusters.keySet()
				.toArray(new Integer[clusters.size()]);
		Arrays.sort(keys);
		// Get clusters based on keys, and print the centers.
		for (Integer k : keys) {
			Cluster c = clusters.get(k);
			try {
				System.out.println(Arrays.toString(c.generateCenter()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Print the id, row and aisle data
			for (Data d : c) {
				System.out.println("\t[id= " + d.getId() + "][row= "
						+ d.getSeatRow() + "][aisle=" + d.getSeatAisle() + "]");
			}
		}
	}

	/**
	 * Gets the index of the closest point to d given a set of data centers.
	 * 
	 * @param centers
	 *            The array of centers (each center is an array)
	 * @param d
	 *            The data (point)
	 * 
	 * @return index of the closest point
	 */
	private static Integer getClosestPoint(double[][] centers, Data d) {
		double distance = Double.MAX_VALUE; // Minimum distance
		int index = -1; // Index of closest
		double[] point = null; // stores d as point
		try {
			point = d.getAsPoint();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Loop through centers and get the closest one to d
		for (int i = 0; i < centers.length; ++i) {
			double temp = Distance.l2(point, centers[i]);
			if (temp < distance) {
				distance = temp;
				index = i;
			}
		}
		return index; // return the index of the closest center
	}

	/**
	 * Finds a passenger by their row and aisle
	 * 
	 * @param aisle Aisle number of the passenger
	 * @param row Row number of the passenger
	 * @return Data of the looked up passenger
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static Data lookUpPassenger(int aisle, int row)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		for (Data d : data) {
			if (d.getSeatAisle() == aisle && d.getSeatRow() == row)
				return d;
		}
		return null;
	}

}
