package org.espeakng.jeditor.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfaceError {

	private JFrame frmEspeakError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceError window = new InterfaceError();
					window.frmEspeakError.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfaceError() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEspeakError = new JFrame();
		frmEspeakError.setTitle("Espeak error");
		frmEspeakError.setBounds(100, 100, 133, 112);
		frmEspeakError.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEspeakError.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Voice not set");
		lblNewLabel.setBounds(12, 0, 107, 29);
		frmEspeakError.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(47, 57, 64, 20);
		frmEspeakError.getContentPane().add(btnNewButton);
	}

}
