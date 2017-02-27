package org.espeakng.jeditor.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.SpinnerNumberModel;

public class InterfaceCompileAtSample {

	private JFrame frmResampleneedssox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceCompileAtSample window = new InterfaceCompileAtSample();
					window.frmResampleneedssox.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfaceCompileAtSample() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmResampleneedssox = new JFrame();
		frmResampleneedssox.setTitle("Resample (needs 'sox' program)");
		frmResampleneedssox.setBounds(100, 100, 363, 176);
		frmResampleneedssox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmResampleneedssox.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Compile phoneme data with a specified sample rate");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel.setBounds(12, 10, 345, 25);
		frmResampleneedssox.getContentPane().add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 97, 340, 17);
		frmResampleneedssox.getContentPane().add(separator);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(255, 109, 85, 25);
		frmResampleneedssox.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setBounds(163, 109, 85, 25);
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		        System.exit(0);
		    }
		});
		frmResampleneedssox.getContentPane().add(btnNewButton_1);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(22050, 5000, 48000, 1));
		spinner.setBounds(140, 49, 200, 25);
		frmResampleneedssox.getContentPane().add(spinner);
		
		JLabel lblNewLabel_1 = new JLabel("Sample rate");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(12, 49, 120, 25);
		frmResampleneedssox.getContentPane().add(lblNewLabel_1);
	}

}
