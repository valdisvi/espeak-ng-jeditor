package org.espeakng.jeditor.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class WordFrequencyWindow extends JFrame {

	private static final long serialVersionUID = -5422011242708352299L;

	/**
	 * Create the frame.
	 */
	
	public WordFrequencyWindow(String[] listItems) {
		
		setTitle("Word Frequencies");
		setBounds(100, 100, 270, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblWordFrequencies = new JLabel("Word Frequencies");
		lblWordFrequencies.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblWordFrequencies, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		JList<String> list = new JList<>(listItems);
		scrollPane.setViewportView(list);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
}
