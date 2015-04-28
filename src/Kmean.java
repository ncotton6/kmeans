import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import datareader.CSVReader;
import model.Cluster;
import model.Data;
import model.Distance;
import model.Point;
import model.Tuple;
import model.UseAttribute;

/**
 * The {@link Kmean} application will use the K-means clustering algorithm to
 * cluster medical data.
 * 
 * @author Nathaniel Cotton
 * 
 */
public class Kmean {

	private static List<Data> data = null;
	private static HashMap<Method, Tuple<Double, Double>> normMap = new HashMap<Method, Tuple<Double, Double>>();
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

		try {
			FileInputStream fis = new FileInputStream(new File(
					"QANTAS420_DATA__v043_temp_for_class.csv"));
			data = CSVReader.parse(fis, Data.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (data == null) {
			System.out.println("For some reason the data is non existant");
			System.exit(1);
		}

		int centerLength = 0;
		for (Method m : Data.class.getDeclaredMethods())
			if (m.getAnnotation(UseAttribute.class) != null)
				++centerLength;

		// running k = 9
		// setup
		HashSet<Data> set = new HashSet<Data>();
		double[][] centers = new double[9][centerLength];
		for (int i = 0; i < initSeeds.length; ++i) {
			try {
				Data d = lookUpPassenger(initSeeds[i][0], initSeeds[i][1]);
				centers[i] = d.getAsPoint();
				set.add(d);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// running
		runKmeans(centers, data);
		// running k = 32
		// setup
		Random rand = new Random(100000);
		while (set.size() < 32) {
			set.add(data.get(rand.nextInt(data.size())));
		}
		centers = new double[32][centerLength];
		int index = 0;
		for (Data d : set) {
			try {
				centers[index++] = d.getAsPoint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// running
		runKmeans(centers, data);
	}

	/**
	 * Runs the Kmeans algorithm on the data that is provided.
	 * 
	 * @param centers
	 * @param data2
	 */
	private static void runKmeans(double[][] centers, List<Data> data) {
		// first run to get start data
		HashMap<Integer, Cluster> clusters = new HashMap<Integer, Cluster>();
		for (Data d : data) {
			Integer closest = getClosestPoint(centers, d);
			if (!clusters.containsKey(closest))
				clusters.put(closest, new Cluster());
			clusters.get(closest).add(d);
		}
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
			for (Data d : data) {
				Integer closest = getClosestPoint(centers, d);
				if (!cluster2.containsKey(closest))
					cluster2.put(closest, new Cluster());
				cluster2.get(closest).add(d);
			}
			if (identicalSets(clusters, cluster2, centers.length))
				break;
			clusters = cluster2;
			for (int i = 0; i < centers.length; ++i) {
				try {
					centers[i] = clusters.get(i).generateCenter();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("Iteration: "+ count++);
		}
		// output the cluster data
		outputClusters(clusters);
		System.out.println("====================\n====================\n====================");
	}

	private static boolean identicalSets(HashMap<Integer, Cluster> cluster1,
			HashMap<Integer, Cluster> cluster2, int count) {
		HashSet<Point> centers = new HashSet<Point>();
		for(Integer key : cluster1.keySet()){
			try {
				centers.add(new Point(cluster1.get(key).generateCenter()));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		for(Integer key : cluster2.keySet()){
			try {
				centers.add(new Point(cluster2.get(key).generateCenter()));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return centers.size() == count;
	}

	private static void outputClusters(HashMap<Integer, Cluster> clusters) {
		Integer[] keys = clusters.keySet()
				.toArray(new Integer[clusters.size()]);
		Arrays.sort(keys);
		for (Integer k : keys) {
			Cluster c = clusters.get(k);
			try {
				System.out.println(Arrays.toString(c.generateCenter()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (Data d : c) {
				System.out.println("\t[id= " + d.getId() + "][row= "
						+ d.getSeatRow() + "][aisle=" + d.getSeatAisle() + "]");
			}
		}
	}

	private static Integer getClosestPoint(double[][] centers, Data d) {
		double distance = Double.MAX_VALUE;
		int index = -1;
		double[] point = null;
		try {
			point = d.getAsPoint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < centers.length; ++i) {
			double temp = Distance.l2(point, centers[i]);
			if (temp < distance) {
				distance = temp;
				index = i;
			}
		}
		return index;
	}

	/**
	 * Finds a passenger by their row and aisle
	 * 
	 * @param aisle
	 * @param row
	 * @return
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
