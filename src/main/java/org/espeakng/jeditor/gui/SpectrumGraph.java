package org.espeakng.jeditor.gui;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.espeakng.jeditor.data.Frame;

public class SpectrumGraph extends JPanel {
	private static final long serialVersionUID = -27394060722915822L;
	private ArrayList<Frame> frames = new ArrayList<Frame>();
    public SpectrumGraph(ArrayList<Frame> frames){
    	this.frames = frames;
    		
    }
    public SpectrumGraph(){}
     
    public void paint(Graphics g) {
        super.paintComponent(g);
        final int PAD = 20;
     	int frameNumber = frames.size();
		
        double [][] data = new double [frameNumber][7]; 
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        
        for (int i=0;i < frameNumber;i++){
        	data [i][0]= frames.get(i).time;
        	for (int j=0;j<3;j++){
        		data[i][1]= frames.get(i).formants[1].freq;
        		data[i][2]= frames.get(i).formants[2].freq;
        		data[i][3]= frames.get(i).formants[3].freq;
        		data[i][4]= frames.get(i).peaks[1].pkfreq;
        		data[i][5]= frames.get(i).peaks[2].pkfreq;
        		data[i][6]= frames.get(i).peaks[3].pkfreq;
        	}
        }
        double minTime = Integer.MAX_VALUE;
        double maxTime = 0;
        double maxFreq = 0;
        double minFreq = Integer.MAX_VALUE;
        for (int i=0;i<frameNumber;i++){
        	double value= data[i][0];
        	if (data[i][0]> maxTime) maxTime= value;
        	if (data[i][0]< minTime) minTime= value;
        	for (int j = 1; j<7;j++){
        		if (data[i][j]> maxFreq) maxFreq= data[i][j];
        		if (data[i][j]< minFreq) minFreq= data[i][j];
        	}
        }
        double xScale = (maxTime*1.1-minTime*0.9);
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abscissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        
        // Ordinate label.
        String s = "formants&peaks";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abscissa label.
        s = "frame time, mS";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
      	g2.drawString(s, sx, sy);
      	// Draw border
      	g2.setPaint(Color.black);
      	g2.draw(new Line2D.Double(w -PAD,  PAD, w -PAD, h-PAD));
      	g2.draw(new Line2D.Double(PAD, PAD, w-PAD, PAD));
      	
        // Draw lines.
        double scale = (double)(h - 4*PAD);
        double y1;
        g2.setStroke(new BasicStroke(2));
        for(int i = 0; i < frameNumber; i++) {
        	g2.setPaint(Color.ORANGE);
            double x = PAD*1.5 + (data[i][0]-minTime)/xScale*(w-2*PAD);
            g2.draw(new Line2D.Double(x, h-PAD , x, PAD));
            for (int j=1;j<4;j++){
            g2.setPaint(Color.RED); // formants
            byte centerOffset = 4;
            byte circleSize = (byte) (2*centerOffset);
            y1 = h - PAD - scale*data[i][j+3]/maxFreq-centerOffset;
            g2.fill(new Ellipse2D.Double(x-centerOffset, y1, circleSize, circleSize));
            g2.setPaint(Color.BLACK); //peaks
            y1 = h - PAD - scale*data[i][j]/maxFreq;
            g2.draw(new Line2D.Double(x-4, y1, x+4, y1));
          
            }  
        }
       
	}
 
} 