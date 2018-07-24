package org.espeakng.jeditor.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;

import javax.swing.JPanel;

public class ProsodyPanel extends JPanel {


	private static final long serialVersionUID = 7933989079965484732L;

    private int height = 150;
    private int stringOffset = 20;
    private int width;
    private int bgLinesCount = 5;
    private double lengthMultiplier = 1.5;
    private ProsodyPhoneme prosodyPhoneme;
	
	public ProsodyPanel(ProsodyPhoneme prosodyPhoneme) {
		this.prosodyPhoneme = prosodyPhoneme;
		System.out.println(prosodyPhoneme.getFrequencies().toString());
		width = (int) (prosodyPhoneme.getDuration() * lengthMultiplier);
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		this.initialize(g2);
		this.drawGrid(g2);
		this.drawLine(g2);
		this.writeProsodyNames(g2);
	}
	
	private void initialize(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension sizes = new Dimension(width, height + stringOffset);
		this.setSize(sizes);
		this.setPreferredSize(sizes);
	}
	
	private void drawGrid(Graphics2D g2) {
		int bgRowsOffset = height / (bgLinesCount + 1);
				
		g2.setColor(Color.LIGHT_GRAY);
		int lineY;
		for (int i = 0; i < bgLinesCount; i++) {
			lineY = bgRowsOffset * (1 + i);
			g2.drawLine(0, lineY, getWidth(), lineY);
		}

		g2.drawLine(0, 0, 0, getHeight() - stringOffset);
		g2.drawLine(getWidth(), 0, getWidth(), getHeight() - stringOffset);
	}
	
	private void drawLine(Graphics2D g2) {
		g2.setColor(Color.blue);
		
		int previousKey = -1;
		int previousValue = 0;
		int previousLength = 0;
		for (Map.Entry<Integer, Integer> entry : prosodyPhoneme.getFrequencies().entrySet()) {
			if (previousKey == -1) {
				previousKey = entry.getKey();
				previousValue = entry.getValue();
				continue;
			}
			
			double percent = entry.getKey() / 100.0;
			
			int wholeLength = (int) (width * percent);
			int length = wholeLength - previousLength;
			
			g2.drawLine(previousLength, height - previousValue, previousLength + length, height - entry.getValue());
			
			previousLength = wholeLength;
			previousKey = entry.getKey();
			previousValue = entry.getValue();
		}
	}
	
	private void writeProsodyNames(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.drawString(prosodyPhoneme.getName(), getWidth() / 2 - 5, height + 15);
	}
}
