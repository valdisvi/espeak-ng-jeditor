package org.espeakng.jeditor.data;

import java.awt.*;
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.*;

import org.espeakng.jeditor.gui.*;

//This Class is responsible for providing vowel charts
//Vowels() constructor is for Tools->VowelCharts->FromCompiledPhonemeData
//Vowels(fileName) is for Tools->VowelCharts->FromDirectoryOfVowelFiles

public class Vowels extends JPanel {

	private JTabbedPane tabbedPaneGraphs;

	private String fileName;

	private int width = 800;
	private int heigth = 400;
	private int padding = 30; // padding from the borders
	private int labelPadding = 30; // padding of labels from the borders
	private Color lineColor = new Color(44, 102, 230, 180); // color of the
															// lines
	private Color pointColor = new Color(100, 100, 100, 180); // color of points
	private Color gridColor = new Color(200, 200, 200, 200); // color of checks
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f); // width of
																	// lines
	private int pointWidth = 4;
	private int numberYDivisions = 8; // amount of divisions for F1 [y-axis]
	private int numberXDivisions = 10;

	public Vowels(String fileName) {
		tabbedPaneGraphs = MainWindow.tabbedPaneGraphs;
		this.fileName = fileName;
	}

	public Vowels() {
		tabbedPaneGraphs = MainWindow.tabbedPaneGraphs;
	}

	class Draw extends JPanel {

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int w = getWidth(); // width of the panel
			int h = getHeight(); // height of the panel
			// double xScale = ((double) getWidth() - (2 * padding) -
			// labelPadding);
			// double yScale = ((double) getHeight() - 2 * padding -
			// labelPadding) /
			// (getMaxScore() - getMinScore());
			int x0 = padding;
			int xMax = w - padding;
			int yMax = h - padding;
			int y0 = padding;
			g2.setColor(Color.WHITE);
			g2.fillRect(x0, y0, xMax - labelPadding, yMax - labelPadding);
			g2.setColor(Color.BLACK);
			// Draw x-axis
			g2.draw(new Line2D.Double(x0, y0, x0, h - y0));
			// Draw y-axis
			g2.drawLine(x0, y0, w - x0, y0);

			// create hatch marks and grid lines for y axis.
			for (int i = 0; i < numberYDivisions + 1; i++) {
				int x1 = x0 - pointWidth;
				y0 = getHeight() - (((i) * (getHeight() - padding * 2)) / numberYDivisions + padding);
				int y1 = y0;
				int j = 900;
				g2.setColor(gridColor);
				g2.drawLine(padding + 1 + pointWidth, y0, getWidth() - padding, y1);

				g2.setColor(Color.BLACK);
				String yLabel = (int) ((8 - i) * 100) + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x1 - labelWidth, y0 + (metrics.getHeight() / 2) - 1);

				g2.drawLine(x0, y0, x1, y1);
			}

			// create hatch marks and grid lines for x axis.
			for (int i = 0; i < numberXDivisions + 1; i++) {
				x0 = (i + 1) * (getWidth() - padding * 2) / numberXDivisions + padding;
				int x1 = x0;
				y0 = yMax;
				int y1 = pointWidth + y0;

				g2.setColor(gridColor);
				g2.drawLine(x0, getHeight() - padding - 1 - pointWidth, x1, padding);
				g2.setColor(Color.BLACK);
				String yLabel = (int) (2500 - (i * 200)) + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x1 - labelWidth, h - (y0 + (metrics.getHeight() / 2) - 1));
				g2.drawLine(x0, h - y0, x1, h - y1);

			}
			
//			s
		}

		/*
		 * this function actually draws the vowels coordinates on the Panel
		 */
		public void creatAndShow() {
			List<Integer> vowels = new ArrayList<>(); // list of
			List<String> coordinates = new ArrayList<>();
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
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
//		//draw 1 point with the name of the point s
//		public  void drawPoint(String s,int x0, int y0, int x1, int y1, Graphics g){
//				g.setColor(lineColor);
//				g.drawLine(x0, y0, x0, y0);
//				g.setColor(Color.DARK_GRAY);
//				g.drawString(s, x0, y0);
//				g.drawOval(x0-2, y0-2, 4, 4);
//		}

	}

	
}
