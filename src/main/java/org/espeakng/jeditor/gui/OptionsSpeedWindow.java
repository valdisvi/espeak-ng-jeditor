package org.espeakng.jeditor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class OptionsSpeedWindow extends JFrame {

	private JSpinner spinner;
	//if user want to cancel his choice
	private Object oldValue;

	/**
	 * Initialize the contents of the frame.
	 */
	public OptionsSpeedWindow() {

		setTitle("Speed");
		setBounds(100, 100, 257, 166);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JButton btnButtonCancel = new JButton("Cancel");
		btnButtonCancel.setBounds(12, 99, 105, 25);
		getContentPane().add(btnButtonCancel);
		btnButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				spinner.setValue(oldValue);
				dispose();
			}
		});

		JButton btnNewButtonOK = new JButton("OK");
		btnNewButtonOK.setBounds(127, 99, 105, 25);
		getContentPane().add(btnNewButtonOK);
		btnNewButtonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(175, 80, 500, 1));
		spinner.setBounds(22, 29, 200, 34);
		getContentPane().add(spinner);

	}

	
	public int getSpinnerValue() {
		Integer result = (Integer) spinner.getValue();
		return result.intValue();
	}

	public void showOptionsSpeed() {
		oldValue = spinner.getValue();
		setVisible(true);
	}
}
