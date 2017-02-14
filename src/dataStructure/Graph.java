package dataStructure;

//TODO JButton convert into graphic

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import interfacePckg.MainWindow;

public class Graph {

	/**
	 * @param args
	 */
	private JTabbedPane tabbedPaneGraphs;
	private JPanel filePanel;
	// mapButtons will collect for each button it own frame
	//private Map<JButton, Frame> mapButtons;
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

	/*	JButton jButton = new JButton("" + i);
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					loadFrame((JButton) arg0.getSource());
				}
			});

			jButton.setBounds(10, y, 400, 25);

			*/
			
			
			JPanel sin = new DrawGraph(currentFrame);
			sin.setBounds(10,y,500,100);
			sin.setBackground(Color.WHITE);
			sin.setVisible(true);
			y += 105;

			sin.addMouseListener(new MouseListener() {
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

			filePanel.add(sin);
			//filePanel.add(jButton);
			mapPanels.put(sin, currentFrame);

		}

		loadFirstFrame();
		tabbedPaneGraphs.addTab(fileName, null, filePanel, null);
		tabbedPaneGraphs.setSelectedComponent(filePanel);

	}
	class DrawGraph extends JPanel {
		public Frame currentFrame;
		public DrawGraph(Frame currentFrame) {
			this.currentFrame = currentFrame;
			
			
		}


	    protected void paintComponent(Graphics g) 
	    {
	    	int[][] peaks = currentFrame.getPeaks();
	        super.paintComponent(g);


	        Polygon p = new Polygon(); 

	       for (int i =0; i <8; i++) { // for length add points 
	            p.addPoint(peaks[i][0]/20,-(peaks[i][1] >> 6)+100);

	        }


	        g.setColor(Color.GREEN);
	        g.drawPolyline(p.xpoints, p.ypoints, p.npoints);

	    }
	 }
	
	

	public JPanel getjPanelOfGraph() {
		return filePanel;
	}

	// to get first element in mapButtons
	public void loadFirstFrame() {
		// load first frame
		Map.Entry<JPanel, Frame> entry = mapPanels.entrySet().iterator()
				.next();
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
