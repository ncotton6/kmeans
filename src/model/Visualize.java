package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Visualize extends JFrame {

	private static int squareDim = 15;
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
		setSize(squareDim * 10 + 40, squareDim * 51 + 70);
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
					g2.fillRect((int) (d.getSeatAisle() - 1) * squareDim,
							(int) (d.getSeatRow() - 1) * squareDim, squareDim,
							squareDim);
				}
			}

			// rows
			g2.setColor(Color.BLACK);
			for (int i = 5; i < 55; i += 5) {
				g2.drawChars(String.valueOf(i).toCharArray(), 0, String
						.valueOf(i).length(), squareDim * 10,
						((i - 1) * squareDim) + 10);
			}

			// aisles
			for (int i = 1; i < 11; ++i) {
				char[] chars = String.valueOf(i).toCharArray();
				g2.drawChars(chars, 0, chars.length, (i - 1) * squareDim,
						squareDim * 51 + 20);
			}
			
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(5));
			g2.drawOval((8-1)*squareDim, (7-1)*squareDim, squareDim, squareDim);
			g2.drawOval((3-1)*squareDim, (42-1)*squareDim, squareDim, squareDim);
		}
	}

}
