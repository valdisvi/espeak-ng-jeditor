package dataStructure;

//TODO JButton convert into graphic

import java.awt.Color;

import java.awt.event.ActionEvent;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


import javax.swing.BorderFactory;


import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import dataStructure.Frame;

import interfacePckg.MainWindow;

public class Graph {

	/**
	 * @param args
	 */
	private JTabbedPane tabbedPaneGraphs;
	private JPanel filePanel;

	// mapButtons will collect for each button it own frame
private Map<JPanel, Frame> mapPanels; // each JPanel corresponds to a Frame
	private int sel_peak = 0;
	private ArrayList<Frame> selectedFrames = new ArrayList<Frame>();
	private ArrayList<Frame> copyFrames = new ArrayList<Frame>();

	public Graph(String fileName, ArrayList<Frame> frameList) {

		tabbedPaneGraphs = MainWindow.tabbedPaneGraphs;
		filePanel = new JPanel();
		filePanel.setToolTipText(fileName);
		
		mapPanels = new LinkedHashMap<JPanel, Frame>();

		filePanel.setLayout(null);
		
		ShowFrames (frameList, filePanel, mapPanels);



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
				{
					if(i == sel_peak && currentFrame.selected)
					{
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
					}
				}
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
		double scalex = 400 / 9000; // 400 == panel_width, 9000 - default value
		//System.out.println("\nDOUBLE dx " + currentFrame.dx + "\n");

		double dx = currentFrame.dx;
		while (dx > 10 || dx < -10) {
			dx /= 10;
		}

		//System.out.println("\ndx " + dx + "\n");

		int nx = currentFrame.nx;
		int[] pk;
		int pk_select = 1; // pk_select is currently selected triangle
		int[][] formants = currentFrame.getFormants();
		int[] spect = currentFrame.getSpect();
		xinc = dx;
		x0 = xinc;
		x1 = nx * xinc;

		g.setColor(Color.BLACK);
		if (spect != null) {
			y0 = offy - (int) (spect[0] * scaley);
			for (pt = 1; pt < nx; pt++) {
				x1 = x0 + xinc;
				y1 = offy
						- (int) (SpectTilt(spect[pt], (int) (pt * dx)) * scaley);
				// System.out.println("y1 " + y1 + " x1 " + x1);
				g.drawLine(((int) x0 / 2), y0, ((int) x1 / 2), y1);
				x0 = x1;
				y0 = y1;

			}
		}

	}

	double SpectTilt(int value, int freq) {
		double x;
		double y;
		// System.out.println("Value " + value + " freq " + freq);
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

	// to get first element in mapPanels
	public void loadFirstFrame() {
		// load first frame
		
		Map.Entry<JPanel, Frame> entry = mapPanels.entrySet().iterator()
				.next();
		entry.getKey().requestFocus();
		loadFrame(entry.getKey());
	}

	public void loadFrame(JPanel currentPanel) {

		Frame frameToLoad = mapPanels.get(currentPanel);
		frameToLoad.selected = true;
		selectedFrames.clear();
		selectedFrames.add(frameToLoad);
		
		for (Map.Entry<JPanel, Frame> entry : mapPanels.entrySet())
		{
		    if(!entry.getValue().equals(frameToLoad))
		    {
		    	Border raisedbevel = BorderFactory.createRaisedBevelBorder();
				Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		    	entry.getValue().selected = false;
		    	entry.getKey().setBorder(BorderFactory.createCompoundBorder(
	                    raisedbevel, loweredbevel));
		    }
		}
		currentPanel.requestFocus();
		currentPanel.setBorder(BorderFactory.createMatteBorder(
                1, 5, 1, 1, Color.red));

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

	
	KeyListener keyListener = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent ke) {
			// TODO Auto-generated method stub
			//System.out.println(ke.getKeyCode());
			
			int x_incr=0;
			int y_incr=0;
			final int[] incr_1 = {4,4,4,8,8,8,8,8,8};
			final int[] incr_2 = {8,8,20,20,20,20,25,25,25};
			
			
			JPanel prev = null, curr, next = null;
			curr = (JPanel)ke.getSource();
			
			next = curr;
			prev = curr;
			
			for (Iterator<JPanel> i = mapPanels.keySet().iterator(); i.hasNext();) {
			    JPanel element = i.next();

			   if(element.equals(curr))
			   {
				   if(i.hasNext())
				   {
					   next = i.next(); 
				   }
				   break;
			   }
			    prev = element;
			    
			}
			
			Frame frameToLoad = mapPanels.get(curr);
			
			
			
			
			boolean shift = ke.isShiftDown();
			boolean control = ke.isControlDown();
			
			
			if(shift)
			{
				x_incr = incr_1[sel_peak];
				y_incr = 0x20;
			}
			else
			{
				x_incr = incr_2[sel_peak];
				y_incr = 0x80;
			}
			
			
			switch(ke.getKeyCode())
			{
				case KeyEvent.VK_RIGHT:
				{
					frameToLoad.peaks[sel_peak][0] += x_incr;
					loadFrame(curr);
					curr.repaint();
					curr.validate();
					break;
				}
				case KeyEvent.VK_LEFT:
				{
					if((mapPanels.get(curr).peaks[sel_peak][0] - x_incr) >= 0)
					{
						frameToLoad.peaks[sel_peak][0] -= x_incr;
						loadFrame(curr);
						curr.validate();
						curr.repaint();
					}
					break;
				}
				case KeyEvent.VK_UP:
				{
					frameToLoad.peaks[sel_peak][1] += y_incr;
					loadFrame(curr);
					curr.validate();
					curr.repaint();
					break;
				}
				case KeyEvent.VK_DOWN:
				{
					if((mapPanels.get(curr).peaks[sel_peak][0] - y_incr) >= 0)
					{
						frameToLoad.peaks[sel_peak][1] -= y_incr;
						loadFrame(curr);
						curr.validate();
						curr.repaint();
					}
					break;
				}
				case KeyEvent.VK_PERIOD:
				{
					frameToLoad.peaks[sel_peak][2] += 5;
					loadFrame(curr);
					curr.validate();
					curr.repaint();
					break;
				}
				case KeyEvent.VK_COMMA:
				{
					if((mapPanels.get(curr).peaks[sel_peak][0] - 5) >= 0)
					{
						frameToLoad.peaks[sel_peak][2] -= 5;
						loadFrame(curr);
						curr.validate();
						curr.repaint();
					}
					break;
				}
				case KeyEvent.VK_SLASH:
				{
					int i = frameToLoad.peaks[sel_peak][2] + frameToLoad.peaks[sel_peak][3];
					frameToLoad.peaks[sel_peak][2] = frameToLoad.peaks[sel_peak][3] = i/2;
					loadFrame(curr);
					curr.validate();
					curr.repaint();
					break;
				}
				case KeyEvent.VK_PAGE_UP:
				{
					prev.requestFocus();
					loadFrame(prev);
					for (Iterator<JPanel> i = mapPanels.keySet().iterator(); i.hasNext();) {
					    JPanel element = i.next();
					    
					   element.repaint();
					   element.revalidate();
					}
					
					break;
				}
				case KeyEvent.VK_PAGE_DOWN:
				{
					next.requestFocus();
					loadFrame(next);
					
					for (Iterator<JPanel> i = mapPanels.keySet().iterator(); i.hasNext();) {
					    JPanel element = i.next();
					    
					   element.repaint();
					   element.revalidate();
					}
					break;
				}
				default:
				{
					if(ke.getKeyCode()>=0x30 && ke.getKeyCode()<=0x39)
					{
						sel_peak =  ke.getKeyCode()-48;
						curr.repaint();
						curr.revalidate();
					}
					break;
				}
			}
			
			//control pressed down
			if(control)
			{
				switch(ke.getKeyCode())
				{
					case KeyEvent.VK_A:
					{
						selectedFrames.clear();
						selectedFrames = new ArrayList<Frame>(mapPanels.values());
						
						for(Iterator<JPanel> i = mapPanels.keySet().iterator(); i.hasNext();) {
						    JPanel element = i.next();
						    element.setBorder(BorderFactory.createMatteBorder(
                                    1, 5, 1, 1, Color.red));
						    mapPanels.get(element).selected = true;
						}
						
						break;
					}
					case KeyEvent.VK_C:
					{
						copyFrames.clear();
						copyFrames = new ArrayList<Frame>(selectedFrames);
						selectedFrames.clear();
						break;
					}
					case KeyEvent.VK_V:
					{		
						if(!copyFrames.isEmpty())
						{
							ShowFrames(copyFrames,filePanel, mapPanels);
						}
						copyFrames.clear();
						break;
					}
					case KeyEvent.VK_S:
					{
						
						//public String type; // Type-name of file (SPECTSEQ,SPECTSEK,SPECTSQ2)
						//public int file_format;
						//public int name_length;
						//public int n;
						//public int amplitude;
						//public int max_y;
						//public String fileName;
						//public ArrayList<Frame> frameList;
						//private Graph graph;
						
						
						//Phoneme p = new Phoneme();
						//p.amplitude = 
						//p.frameList = new ArrayList<Frame>(mapPanels.values());
						//p.max_y = 
						//p.file_format = 1;
						//p.fileName = filePanel.getName();
						
						//saveToDirectory(p, file);
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent ke) {
			// TODO Auto-generated method stub
		}
		
	};
	
	public void ShowFrames (ArrayList<Frame> frames, final JPanel filePanel, final Map<JPanel,Frame> mapPanels)
	{
		filePanel.removeAll();
		mapPanels.clear();
		
		int y = 25;
		for (int i = 0; i < frames.size(); i++) {

			Frame currentFrame = frames.get(i);
			currentFrame.selected = false;
			
			final JPanel keyframe = new Draw(currentFrame);
			
			Border raisedbevel = BorderFactory.createRaisedBevelBorder();
			Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			keyframe.setBounds(10, y, 900, 100);
			keyframe.setBackground(Color.WHITE);
			keyframe.setBorder(BorderFactory.createCompoundBorder(
                    raisedbevel, loweredbevel));
			keyframe.setVisible(true);
			y += 105;
			keyframe.addKeyListener(keyListener);
			
			keyframe.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
											
					if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK)
					{
						Frame f = mapPanels.get(keyframe);
						
						if(!f.selected)
						{
							keyframe.setBorder(BorderFactory.createMatteBorder(
                                    1, 5, 1, 1, Color.red));
							f.selected = true;
							selectedFrames.add(f);
							System.out.println(selectedFrames);
						}
						else
						{
							Border raisedbevel = BorderFactory.createRaisedBevelBorder();
							Border loweredbevel = BorderFactory.createLoweredBevelBorder();
							keyframe.setBorder(BorderFactory.createCompoundBorder(
				                    raisedbevel, loweredbevel));
							selectedFrames.remove(f);
							f.selected=false;
							System.out.println(selectedFrames);
						}
					}
					else
					{
						Border raisedbevel = BorderFactory.createRaisedBevelBorder();
						Border loweredbevel = BorderFactory.createLoweredBevelBorder();
						keyframe.setBorder(BorderFactory.createCompoundBorder(
			                    raisedbevel, loweredbevel));
						loadFrame((JPanel) e.getSource());
						keyframe.requestFocus();
					}
					
					if(e.getSource().equals(filePanel))
					{
						selectedFrames.clear();
						
						for (Map.Entry<JPanel, Frame> entry : mapPanels.entrySet())
						{
						    
						    	Border raisedbevel = BorderFactory.createRaisedBevelBorder();
								Border loweredbevel = BorderFactory.createLoweredBevelBorder();
						    	entry.getValue().selected = false;
						    	entry.getKey().setBorder(BorderFactory.createCompoundBorder(
					                    raisedbevel, loweredbevel));
						    	System.out.println(selectedFrames);
						}

					}
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
			mapPanels.put(keyframe, currentFrame);

		}
		filePanel.revalidate();
		filePanel.repaint(); 
		filePanel.addKeyListener(keyListener);
		filePanel.requestFocus();

		loadFirstFrame();
	}
	
}

