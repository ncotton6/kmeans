package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Visualize extends JFrame {

	private Random rand = new Random(100000);
	private Color[] colors = null;
	private HashMap<Integer, Cluster> clusters;

	public Visualize(HashMap<Integer, Cluster> clusters) {
		super("Kmeans");
		this.clusters = clusters;
		generateColors(clusters.size());
	}

	private void generateColors(int size) {
		HashSet<Color> distinctTest = new HashSet<Color>();
		colors = new Color[size];
		for (int i = 0; i < size; ++i) {
			Color c = null;
			while (true) {
				c = new Color(rand.nextInt(255), rand.nextInt(255),
						rand.nextInt(255));
				if (!distinctTest.contains(c))
					break;
			}
			distinctTest.add(c);
			colors[i] = c;
		}
	}

	public void makeVisible() {
		setSize(220, 1020);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().add(new Squares());
		setVisible(true);
	}

	class Squares extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			for (Integer key : clusters.keySet()) {
				g2.setColor(colors[key]);
				for (Data d : clusters.get(key)) {
					g2.fillRect((int) (d.getSeatAisle() - 1) * 20,
							(int) (d.getSeatRow() - 1) * 20, 20, 20);
				}
			}
		}
	}

}
