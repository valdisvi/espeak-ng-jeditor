package interfacePckg;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class AboutWindow extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public static void OpenAboutWindow() {
		AboutWindow ab = new AboutWindow();
	}
	
	public AboutWindow() {
		
		setTitle("About");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 380, 226);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnOk = new JButton("OK");
		btnOk.setBounds(207, 146, 117, 25);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(btnOk);

		JLabel lblNewLabel = new JLabel("espeakedit 1.48.15 16.Apr.15");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblNewLabel.setBounds(10, 10, 356, 25);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Author: Jonathan Duddington (c) 2009");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(10, 32, 356, 25);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("<html><head></head><body><a href=\"http://espeak.sourceforge.net\">http://espeak.sourceforge.net</a></body></html>");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("firefox ./docs/index.html");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblNewLabel_2.setBounds(10, 57, 356, 31);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("<html><body>Licensed under " +
					"<a href = \"http://espeak.sourceforge.net/license.html\">" +
				"GNU General Public License version 3" +
					"</a></body></html>");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("firefox http://espeak.sourceforge.net/license.html");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblNewLabel_3.setBounds(10, 82, 356, 31);
		contentPane.add(lblNewLabel_3);
		
		setVisible(true);
	}
}
