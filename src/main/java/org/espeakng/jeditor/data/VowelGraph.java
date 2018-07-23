package org.espeakng.jeditor.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class VowelGraph extends JPanel {

	private static final long serialVersionUID = 7933989079965484732L;

    private int height = 150;
    private int delimeter = 30;
    private int rowsCount;
    private int sum = 2000;
    private int separator = 10;
    private double lengthMultiplier = 2;
    private int rightSpacing = 250;
    private int leftSpacing = 20;
    private String[][] data;
	
	public VowelGraph(String data) {
		String[] dataRow = data.split("\\n");
		this.data = new String[dataRow.length - 2][];
		
		int sum = 0;
		
		for (int i = 0; i < dataRow.length - 2; i++) {
			this.data[i] = dataRow[i].split("\\t");
			sum += this.data[i].length >= 2 ? Integer.parseInt(this.data[i][1]) * lengthMultiplier : 0;
		}
		
		this.sum = sum;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        rowsCount = (int) Math.ceil((double) sum / (getWidth() - rightSpacing + leftSpacing));
		this.setPreferredSize(new Dimension(getWidth(), (height + delimeter) * rowsCount));
        
        for (int i = 0; i < rowsCount; i++) {
        	g2.setColor(Color.WHITE);
        	g2.fillRect(0, i * (height + delimeter), getWidth(), height);
        	
        	for (int j = 0; j < 5; j++) {
        		g2.setColor(Color.LIGHT_GRAY);
        		int cc = height / 6;
        		int lY = i * (height + delimeter) + cc + j * cc;
        		g2.drawLine(0, lY, getWidth(), lY);
        	}
        }
        
        
        int offset = leftSpacing;
        int rowOffset = 0;
        for (int i = 0; i < data.length; i++) {
        	g2.setColor(Color.BLUE);
        	int length = (int) (Math.ceil(data[i].length > 1 ? Integer.parseInt(data[i][1]) : 0) * lengthMultiplier);

        	
        	if (data[i].length == 3) {
        		String[] pitchData = data[i][2].split("\\s+");
        		
        		int innerOffset = 0;
        		int innerLength = 0;
        		int wholeLength = 0;
        		
        		for (int j = 1; j < pitchData.length - 2; j += 2) {
        			double percent = Double.parseDouble(pitchData[j + 2]) / 100;
        			
        			innerLength = (int) (length * percent - wholeLength);
        			wholeLength = (int) (length * percent);
        			
        			int current = (int) Math.ceil(Integer.parseInt(pitchData[j + 1]));
        			int next = (int) Math.ceil(Integer.parseInt(pitchData[j + 3]));

        			g2.drawLine(offset + innerOffset, height - current + rowOffset, offset + innerOffset + innerLength, height - next + rowOffset);
        			innerOffset += innerLength;
        		}
        	}
        	
        	g2.setColor(Color.BLACK);
        	g2.drawString(data[i][0], offset - 25 + length / 2, rowOffset + height + delimeter / 2);
        	offset += length + separator;

        	if (offset >= getWidth() - rightSpacing - leftSpacing) {
        		offset = leftSpacing;
        		rowOffset += height + delimeter;
        	}
        }
        
	}
	

}
