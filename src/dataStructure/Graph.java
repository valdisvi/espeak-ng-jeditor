package dataStructure;

//TODO JButton convert into graphic

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;

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
		int y2 = 25;

		for (int i = 0; i < frameList.size(); i++) {

			Frame currentFrame = frameList.get(i);

			JButton jButton = new JButton("" + i);
			JPanel j_Frame = new JPanel();
			j_Frame.setVisible(true);
			j_Frame.setBounds(10, y, 400, 50);
			j_Frame.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			y += 75;
			j_Frame.addMouseListener(new MouseListener() {
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
			
			jButton.setBounds(10, y2, 400, 25);
			y2 += 5;

			//filePanel.add(jButton);
			filePanel.add(j_Frame);
			mapPanels.put(j_Frame, currentFrame);

		}

		loadFirstFrame();
		tabbedPaneGraphs.addTab(fileName, null, filePanel, null);
		tabbedPaneGraphs.setSelectedComponent(filePanel);

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
		
		
		class SinX extends JPanel {
			
			/**
			 * 
			 */
			
			JPanel jp = new JPanel();
			
			//jp.setVisible(true);
			//jp.setBounds(10, y, 400, 50);
			//jp.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g)
		    {
				
				super.paintComponent(g);
				
				
		        g.drawLine(0,350,900,350); // x-axis
		        g.drawLine(450,0,450,900); // y-axis
		        
		        g.setColor(Color.red);
		        
		        for(double x=-450;x<=450;x=x+0.5)
		        {
		            double y = 50 * Math.sin(x*(3.1415926/180));
		            int Y = (int)y;
		            int X = (int)x;
		            g.drawLine(450+X,350-Y,450+X,350-Y);
		        }
		    }
		}

	}
}
