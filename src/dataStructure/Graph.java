package dataStructure;

//TODO JButton convert into graphic

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dataStructure.Frame;

import interfacePckg.MainWindow;

public class Graph {

	/**
	 * @param args
	 */
	private JTabbedPane tabbedPaneGraphs;
	private JPanel filePanel;
	// mapButtons will collect for each button it own frame
	// private Map<JButton, Frame> mapButtons;
	private Map<JPanel, Frame> mapPanels;

	public Graph(String fileName, ArrayList<Frame> frameList) {

		tabbedPaneGraphs = MainWindow.tabbedPaneGraphs;

		filePanel = new JPanel();
		filePanel.setToolTipText(fileName);

		filePanel.setLayout(null);

		// for correct order i use LinkedHashMap, because hashMap not guarantee
		// the insertion order.
		mapPanels = new LinkedHashMap<JPanel, Frame>();

		// y - is needed for adding buttons in correct order
		int y = 25;

		for (int i = 0; i < frameList.size(); i++) {

			Frame currentFrame = frameList.get(i);

			/*
			 * JButton jButton = new JButton("" + i);
			 * jButton.addActionListener(new ActionListener() { public void
			 * actionPerformed(ActionEvent arg0) { loadFrame((JButton)
			 * arg0.getSource()); } });
			 * 
			 * jButton.setBounds(10, y, 400, 25);
			 */

			JPanel keyframe = new Draw(currentFrame);
			keyframe.setBounds(10, y, 900, 100);
			keyframe.setBackground(Color.WHITE);
			keyframe.setVisible(true);
			y += 105;

			keyframe.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					loadFrame((JPanel) e.getSource());
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}
			});

			filePanel.add(keyframe);
			// filePanel.add(jButton);
			mapPanels.put(keyframe, currentFrame);

		}

		loadFirstFrame();
		tabbedPaneGraphs.addTab(fileName, null, filePanel, null);
		tabbedPaneGraphs.setSelectedComponent(filePanel);

	}

	class Draw extends JPanel {
		public Frame currentFrame;

		public Draw(Frame currentFrame) {
			this.currentFrame = currentFrame;

		}

		protected void paintComponent(Graphics g) {
			int[][] peaks = currentFrame.getPeaks();
			super.paintComponent(g);

			/*
			 * Polygon p = new Polygon();
			 * 
			 * for (int i =0; i <8; i++) { // for length add points
			 * p.addPoint(peaks[i][0]/20,-(peaks[i][1] >> 6)+100);
			 * 
			 * }
			 * 
			 * 
			 * g.setColor(Color.GREEN); g.drawPolyline(p.xpoints, p.ypoints,
			 * p.npoints);
			 */
			// Polygon p = new Polygon();

			int[] x = new int[3];
			int[] y = { 100, 0, 100 };
			int[][] points = new int[8][4];
			for (int i = 0; i < 8; i++) {
				points[i][0] = peaks[i][0] / 20; // peak x value
				points[i][1] = -(peaks[i][1] >> 6) / 3 + 100; // peak y value
				points[i][2] = (int) (peaks[i][2] / 20 * 0.44); // left x
				points[i][3] = (int) (peaks[i][3] / 20 * 0.44); // right x

				if (y[1] < 5)
					y[1] = 5;

				x[0] = points[i][0] - points[i][2];
				x[1] = points[i][0];
				x[2] = points[i][0] + points[i][3];
				y[1] = points[i][1];

				g.setColor(Color.ORANGE);
				Polygon poly = new Polygon(x, y, 3);
				g.fillPolygon(poly);
				g.setColor(Color.RED);
				g.drawPolygon(poly);
				drawFormants(currentFrame, g);
			}
		}

	}

	public void drawFormants(Frame currentFrame, Graphics g) {
		// draw the measured formants
		int pt;
		int peak;
		int ix;
		int offy = 100; // This is y of graph panel
		double x0, x1;
		int y0, y1;
		int x, x2, x3;
		double xinc;
		double yf;
		double scaley = 100 / currentFrame.max_y;
		double scalex = 400 / 9000;
		// double dx = currentFrame.dx;
		System.out.println("\nDOUBLE dx " + currentFrame.dx + "\n");

		double dx = currentFrame.dx;
		while (dx > 10 || dx < -10) {
			dx /= 10;
		}

		System.out.println("\ndx " + dx + "\n");

		int nx = currentFrame.nx;
		int[] pk;
		int pk_select = 1; // pk_select is currently selected triangle
		int[][] formants = currentFrame.getFormants();
		int[] spect = currentFrame.getSpect();
		xinc = dx;
		x0 = xinc;
		x1 = nx * xinc;
/*
		for (peak = 1; peak <= 5; peak++) {
			if (formants[peak][0] != 0) {
				// set height from linear interpolation of the adjacent
				// points in the spectrum
				pt = (int) (formants[peak][0] / dx);
				System.out.println(" pt "+pt+" spect size "+spect.length);
				y0 = spect[pt - 1];
				y1 = spect[pt];
				yf = (y1 - y0) * (formants[peak][0] - pt * dx) / dx;

				y1 = offy - (int) ((y0 + yf) * scaley);
				x1 = formants[peak][0] * scalex;
				g.setColor(Color.BLUE);
				g.drawLine((int) x1, offy, (int) x1, y1);
			}
		}
*/
		g.setColor(Color.BLACK);
		if (spect != null) {
			y0 = offy - (int) (spect[0] * scaley);
			for (pt = 1; pt < nx; pt++) {
				x1 = x0 + xinc;
				y1 = offy
						- (int) (SpectTilt(spect[pt], (int) (pt * dx)) * scaley);
			//	System.out.println("y1 " + y1 + " x1 " + x1);
				g.drawLine(((int) x0 / 2), y0, ((int) x1 / 2), y1);
				x0 = x1;
				y0 = y1;

			}
		}

	}

	double SpectTilt(int value, int freq) {// =================================
		double x;
		double y;
		//System.out.println("Value " + value + " freq " + freq);
		y = value * value * 2;

		if (freq < 600) {
			return (Math.sqrt(y / 2.5));
		} else if (freq < 1050) {
			x = 1.0 + ((1050.0 - freq) * 1.5) / 450.0;
			return (Math.sqrt(y / x));
		} else {
			return (Math.sqrt(y));
		}
	}

	public JPanel getjPanelOfGraph() {
		return filePanel;
	}

	// to get first element in mapButtons
	public void loadFirstFrame() {
		// load first frame
		Map.Entry<JPanel, Frame> entry = mapPanels.entrySet().iterator().next();
		loadFrame(entry.getKey());
	}

	public void loadFrame(JPanel currentPanel) {

		Frame frameToLoad = mapPanels.get(currentPanel);

		int[][] peaks = frameToLoad.getPeaks();
		String value;

		for (int i = 0; i < 7; i++) {
			MainWindow.tfFreq.get(i).setText("" + peaks[i][0]);
		}
		for (int i = 0; i < 8; i++) {
			MainWindow.tfHeight.get(i).setText("" + (peaks[i][1] >> 6));
		}
		for (int i = 0; i < 6; i++) {
			value = (peaks[i][2] == peaks[i][3]) ? ("" + (peaks[i][2] / 2))
					: ("" + (peaks[i][2] / 2) + "/" + (peaks[i][3] / 2));
			MainWindow.tfWidth.get(i).setText(value);
		}
		for (int i = 0; i < 3; i++) {
			MainWindow.tfBw.get(i).setText("" + (peaks[i + 1][4]));
		}
		for (int i = 0; i < 6; i++) {
			MainWindow.tfAp.get(i).setText("" + (peaks[i][5]));
		}
		for (int i = 0; i < 6; i++) {
			MainWindow.tfBp.get(i).setText("" + (peaks[i + 1][6]));
		}

		// MainWindow.spampF.setValue(frameToLoad.amp_adjust);
		// outputPhonemes();

	}
}
