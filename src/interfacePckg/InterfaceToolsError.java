package interfacePckg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

public class InterfaceToolsError {

	private JFrame frmEspeakeditorError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceToolsError window = new InterfaceToolsError();
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
	public InterfaceToolsError() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEspeakeditorError = new JFrame();
		frmEspeakeditorError.setTitle("Espeakeditor error");
		frmEspeakeditorError.setBounds(100, 100, 310, 147);
		frmEspeakeditorError.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEspeakeditorError.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(203, 80, 80, 25);
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		        System.exit(0);
		    }
		});
		frmEspeakeditorError.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Can't read compile_prog_log;");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel.setBounds(62, 12, 234, 38);
		frmEspeakeditorError.getContentPane().add(lblNewLabel);
	}

}
