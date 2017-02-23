package interfacePckg;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

public class InterfaceVoiceError {

	private JFrame frmEspeakeditorError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceVoiceError window = new InterfaceVoiceError();
					window.frmEspeakeditorError.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfaceVoiceError() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEspeakeditorError = new JFrame();
		frmEspeakeditorError.setTitle("Espeakeditor Error");
		frmEspeakeditorError.setBounds(100, 100, 234, 118);
		frmEspeakeditorError.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEspeakeditorError.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(135, 63, 80, 25);
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		        System.exit(0);
		    }
		});
		frmEspeakeditorError.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Failed to load voice data");
		lblNewLabel.setBounds(50, 10, 169, 20);
		frmEspeakeditorError.getContentPane().add(lblNewLabel);
	}

}
