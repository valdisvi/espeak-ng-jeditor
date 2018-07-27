package org.espeakng.jeditor.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

/**
 * 
 * This class builds window with a spinner (opened Options->Speed...), used
 * to adjust the speed of pronounced text. The speed value is further used as 
 * parameter for espeak-ng program run in terminal.
 *
 */

public class OptionsSpeedWindow extends JFrame {

	private static Logger logger = Logger.getLogger(OptionsSpeedWindow.class.getName());
	private static final long serialVersionUID = 6781488050526787847L;


	private JSpinner spinner;
	
	/* If user wants to cancel his choice, spinner is set
		to the previously chosen value (which is stored here). */
	private transient Object oldValue;

	/**
	 * Initialize the contents of the frame.
	 */
	public OptionsSpeedWindow() {

		setTitle("Speed");
		setName(getTitle());
		setBounds(100, 100, 257, 166);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		try {
			setIconImage(ImageIO.read(MainWindow.class.getResourceAsStream("/lips.png")));
		} catch (IOException e) {
			logger.warn(e);
		}

		JButton btnButtonCancel = new JButton("Cancel");
		btnButtonCancel.setName("Cancel");
		btnButtonCancel.setBounds(12, 99, 105, 25);
		getContentPane().add(btnButtonCancel);
		btnButtonCancel.addActionListener((ActionEvent e) -> {
			spinner.setValue(oldValue);
			dispose();
		});

		JButton btnNewButtonOK = new JButton("OK");
		btnNewButtonOK.setName("OK");
		btnNewButtonOK.setBounds(127, 99, 105, 25);
		getContentPane().add(btnNewButtonOK);
		btnNewButtonOK.addActionListener((ActionEvent e) -> dispose());

		spinner = new JSpinner();
		spinner.setName("spinner");
		spinner.setModel(new SpinnerNumberModel(175, 80, 500, 1));
		spinner.setBounds(22, 29, 200, 34);
		getContentPane().add(spinner);
	}

	/**
	 * This method returns spinner value, used to set the speed of speech. It is
	 * invoked from EspeakNg class. The value it returns is passed as a parameter
	 * for espeak-ng program which is run on terminal.
	 * 
	 */
	public int getSpinnerValue() {
		return (Integer) spinner.getValue();
	}

	/**
	 * This method displays "Speed" window with spinner to adjust the speed of
	 * speech. It is invoked from EventHandlers class.
	 */
	public void showOptionsSpeed() {
		oldValue = spinner.getValue();
		setVisible(true);
	}
}
