package dataStructure;

//TODO JButton convert into graphic

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import interfacePckg.EspeakNg;
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
			final JPanel j_Frame = new JPanel();
			j_Frame.setName(i+"panel");
			j_Frame.setVisible(true);
			j_Frame.setBounds(10, y, 400, 50);
			j_Frame.setBorder(BorderFactory.createLineBorder(Color.RED));
			j_Frame.addKeyListener(keyListener);
			
			y += 75;
			j_Frame.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					loadFrame((JPanel) e.getSource());
					j_Frame.requestFocus();
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
		for(Map.Entry<JPanel, Frame> map:mapPanels.entrySet())
		{
			map.getValue().selected = false;
		}
		
		System.out.print(currentPanel.getName()+"\n");
		
		
		

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
			    
			    System.out.print("prev:"+prev.getName());
			    System.out.print("next:"+next.getName()+"\n");
			}
			
			Frame frameToLoad = mapPanels.get(curr);
			
			
			
			int sel_peak = 0;
			boolean shift = ke.isShiftDown();
			
			
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
					//loadFrame(pan);
					break;
				}
				case KeyEvent.VK_LEFT:
				{
					frameToLoad.peaks[sel_peak][0] -= x_incr;
					//loadFrame(pan);
					break;
				}
				case KeyEvent.VK_UP:
				{
					frameToLoad.peaks[sel_peak][1] += y_incr;
					//loadFrame(pan);
					break;
				}
				case KeyEvent.VK_DOWN:
				{
					frameToLoad.peaks[sel_peak][1] -= y_incr;
					//loadFrame(pan);
					break;
				}
				case KeyEvent.VK_PERIOD:
				{
					frameToLoad.peaks[sel_peak][2] += 5;
					//loadFrame(pan);
					break;
				}
				case KeyEvent.VK_COMMA:
				{
					frameToLoad.peaks[sel_peak][2] -= 5;
					//loadFrame(pan);
					break;
				}
				case KeyEvent.VK_SLASH:
				{
					int i = frameToLoad.peaks[sel_peak][2] + frameToLoad.peaks[sel_peak][3];
					frameToLoad.peaks[sel_peak][2] = frameToLoad.peaks[sel_peak][3] = i/2;
					//loadFrame(pan);
					break;
				}
				case KeyEvent.VK_PAGE_UP:
				{
					prev.requestFocus();
					loadFrame(prev);
					break;
				}
				case KeyEvent.VK_PAGE_DOWN:
				{
					next.requestFocus();
					loadFrame(next);
					break;
				}
				default:
				{
					if(ke.getKeyCode()>='0' && ke.getKeyCode()<='9')
					{
						sel_peak = ke.getKeyCode();
					}
					break;
				}	
			}
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			// TODO Auto-generated method stub
			
			//JPanel pan = (JPanel) ke.getSource();
			//Frame frameToLoad = mapPanels.get(pan);
			
			//loadFrame(pan);
			
		}

		@Override
		public void keyTyped(KeyEvent ke) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	
	
	//class SinX extends JPanel {
	//	
	//	/**
	//	 * 
	//	 */
	//	
	//	SinX() {
    //        // set a preferred size for the custom panel.
    //        setPreferredSize(new Dimension(420,420));
    //        setVisible(true);
    //    }
    //
    //    //@Override
    //    public void paintComponent(Graphics g) {
    //        super.paintComponent(g);
    //        
    //        g.drawLine(0,350,900,350); // x-axis
    //        g.drawLine(450,0,450,900); // y-axis
    //        
    //        g.setColor(Color.red);
    //        
    //        for(double x=-450;x<=450;x=x+0.5)
    //        {
    //            double y = 50 * Math.sin(x*(3.1415926/180));
    //            int Y = (int)y;
    //            int X = (int)x;
    //            g.drawLine(450+X,350-Y,450+X,350-Y);
    //        }
    //
    //        //g.drawString(curr.peaks.toString(), 20, 20);
    //    }
	//}
}
