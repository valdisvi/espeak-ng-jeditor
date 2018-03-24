package org.espeakng.jeditor.data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VowelChart extends JPanel {
	private int width = 1500;
	private int heigth = 750;
	private static int padding = 25; // padding from the borders
	private int labelPadding = 25; // padding of labels from the borders
	private static Color lineColor = new Color(44, 102, 230, 180); // color of
																	// the lines
	private Color pointColor = new Color(100, 100, 100, 180); // color of points
	private Color gridColor = new Color(200, 200, 200, 200); // color of checks
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f); // width of
																	// lines
	private static int pointWidth = 4;
	private int numberYDivisions = 16; // amount of divisions for F1 [y-axis]
	private int numberXDivisions = 16;

	private static int max = 2400;
	private static int min = 200;

	// list of Vowel objects
	public static List<Vowel> vowelsList;
	public static List<Double> xValues;
	public static List<Double> yValues;

	// pass the file's path to the constructor so that it assigns the file's
	// values into vowelsList
	public VowelChart(String fileName) {
		vowelsList = createFromFile(fileName);
		xValues = getXvalues(vowelsList);
		yValues = getYvalues(vowelsList);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth(); // width of the panel
		int h = getHeight(); // height of the panel
		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (getMaxValueX()-getMinValueX());
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxValueY()- getMinValueY());
		
		
		numberYDivisions = (int)((getMaxValueY()-(getMaxValueY()%100)-(getMinValueY()-(getMinValueY()%100)))/100)*2;
		numberXDivisions = (int)((getMaxValueX()-(getMaxValueX()%100)-(getMinValueX()-(getMinValueX()%100)))/100);
	
		// draw WHITE background
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(),getHeight());
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = padding + labelPadding - pointWidth;
			int y0 = ((i * (getHeight() - padding - labelPadding)) / numberYDivisions);
			int y1 = (((i+1) * (getHeight() - padding - labelPadding)) / numberYDivisions);

			g2.setColor(gridColor);
			g2.drawLine(0, y0, getWidth(), y0);
			g2.setColor(Color.BLACK);
			
			int value = (int) (getMinValueY() -(getMinValueY()%100) + (i * 50));
			
			String yLabel = value + "";
			FontMetrics metrics = g2.getFontMetrics();
			int labelWidth = metrics.stringWidth(yLabel);
			if(i%2==0){
			g2.drawString(yLabel, labelPadding -labelWidth, y0 + (metrics.getHeight() / 2) + padding);}
			
		}

		// and for x axis
		for (int i = 0; i < vowelsList.size(); i++) {
			if (vowelsList.size() > 1) {
				int x0 = i * (getWidth()-padding*2-labelPadding) / numberXDivisions + padding + labelPadding;
				int x1 = x0;
				int y0 = padding + labelPadding;
				int y1 = y0 - pointWidth;
				
				g2.setColor(gridColor);
				g2.drawLine(x0, getHeight() - pointWidth, x1, 0);
				g2.setColor(Color.BLACK);
				String xLabel = (getMaxValueX() - i * 100) + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(xLabel);
				if (i%2==0){
				g2.drawString(xLabel, x0 + labelPadding - labelWidth / 2,  metrics.getHeight() + 3);}
			}
		}

	
		
		for (int i = 0; i < vowelsList.size(); i++) {
			int x0 = (int) (((getMaxValueX()-(getMaxValueX()%100))-vowelsList.get(i).getXb()-labelPadding) * xScale + padding - labelPadding);
            int y0 = (int) ((vowelsList.get(i).getYb()-(getMinValueY()-(getMinValueY()%100))-labelPadding) * yScale +padding + labelPadding);
            int x1 = (int) (((getMaxValueX()-(getMaxValueX()%100))-vowelsList.get(i).getXe()-labelPadding) * xScale + padding - labelPadding);
			int y1 = (int) ((vowelsList.get(i).getYe()-(getMinValueY()-(getMinValueY()%100))-labelPadding) * yScale+padding + labelPadding);
			drawPoint(vowelsList.get(i).getName(), x0,y0,x1,y1, g2);
		}
		
		g.drawString(getMaxValueY()+"", 200, 200);
	}

	public static void drawPoint(String s, int x0, int y0, int x1, int y1, Graphics g) {
		 
		g.setColor(lineColor);
		g.drawLine(x0, y0, x1, y1);
		g.setColor(Color.DARK_GRAY);
		g.drawString(s, x0, y0);
		g.drawOval(x0 - pointWidth, y0 - pointWidth, pointWidth, pointWidth);
	}

	/*
	 * this method reads the file, each line is separated into words and the
	 * words are passed as arguments to construct new Vowel object, then the
	 * Vowel object is added to the list and it returns list of Vowel objects
	 */
	public static List<Vowel> createFromFile(String fileName) {
		List<Vowel> vowelList = new ArrayList<>(); // list of

		try {
			File vowelChart = new File(fileName);
			FileReader in = new FileReader(vowelChart);
			BufferedReader inn = new BufferedReader(in);
			Scanner sc = new Scanner(vowelChart);
			String line;
			int j = 0;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				StringTokenizer st = new StringTokenizer(line, " ");
				while (st.hasMoreTokens()) {
					vowelList.add(new Vowel(st.nextToken(), Integer.parseInt(st.nextToken()),
							Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
							Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
							Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vowelList;
	}
	
	public static List<Double> getXvalues(List<Vowel> list){
		List<Double> listX = new ArrayList<>();
		for (int i = 0; i < list.size(); i++){
			listX.add((double) list.get(i).getXb());
			listX.add((double) list.get(i).getXe());
		}
		return listX;
		
	}
	
	public static List<Double> getYvalues(List<Vowel> list){
		List<Double> listY = new ArrayList<>();
		for (int i = 0; i < list.size(); i++){
			listY.add((double) list.get(i).getYb());
			listY.add((double) list.get(i).getYe());
		}
		return listY;
		
	}
	
	public static double getMinValueX() {
		double minValue = Integer.MAX_VALUE;
		for (Double min : xValues) {
			minValue = Math.min(minValue, min);
		}
		return minValue;
		
	}
	
	public static double getMinValueY() {
		double minValue = Integer.MAX_VALUE;
		for (Double min : yValues) {
			minValue = Math.min(minValue, min);
		}
		return minValue;
		
	}
	
	public static double getMaxValueX() {
		double minValue = Integer.MIN_VALUE;
		for (Double min : xValues) {
			minValue = Math.max(minValue, min);
		}
		return minValue;
		
	}
	
	public static double getMaxValueY() {
		double minValue = Integer.MIN_VALUE;
		for (Double min : yValues) {
			minValue = Math.max(minValue, min);
		}
		return minValue;
		
	}
	
	
	 public void setOtherVowels(String fileName) {
	        this.vowelsList = createFromFile(fileName);
	        invalidate();
	        this.repaint();
	    }

//	public static void main(String[] args) {
//		JFrame f = new JFrame();
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.add(new VowelChart("/home/student/code/espeak-ng/phsource/vowelcharts/en"));
//		f.setSize(1500, 750);
//
//		f.setLocation(200, 200);
//		f.setVisible(true);
//
//	}

}
