package model;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;

public class Visualize extends JFrame {
	
	private static Color[] colors = new Color[]{Color.BLUE,Color.CYAN,Color.DARK_GRAY,Color.GREEN,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.RED,Color.WHITE,Color.YELLOW, new Color(233,233,233)};
	private HashMap<Integer, Cluster> clusters;

	public Visualize(HashMap<Integer,Cluster> clusters){
		super("Kmeans");
		this.clusters = clusters;
	}
	
	public void makeVisible(){
		setSize(400, 800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		drawClusters();
		setVisible(true);
	}

	private void drawClusters() {
		
	}
}
