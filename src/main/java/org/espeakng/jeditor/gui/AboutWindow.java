package org.espeakng.jeditor.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * 
 * This class constructs window called by Help->About on main menu bar. The
 * window shows general information about the program (version, author, link to
 * program documentation).
 *
 */

public class AboutWindow extends JFrame {
	private static Logger logger = Logger.getLogger(AboutWindow.class.getName());
	private static final long serialVersionUID = -6382834025393046240L;
	private static final String DIALOG_TEXT = "Dialog";
	private JPanel contentPane;
	
	public AboutWindow() {
		
		setTitle("About");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 380, 226);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try {
			setIconImage(ImageIO.read(new File("./src/main/resources/lips.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JButton btnOk = new JButton("OK");
		btnOk.setBounds(207, 146, 117, 25);
		btnOk.addActionListener((ActionEvent e) -> dispose());
		contentPane.add(btnOk);

		JLabel lblNewLabel = new JLabel("espeakedit 1.48.15 16.Apr.15");
		lblNewLabel.setFont(new Font(DIALOG_TEXT, Font.PLAIN, 10));
		lblNewLabel.setBounds(10, 10, 356, 25);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel1 = new JLabel("Author: Jonathan Duddington (c) 2009");
		lblNewLabel1.setFont(new Font(DIALOG_TEXT, Font.PLAIN, 10));
		lblNewLabel1.setBounds(10, 32, 356, 25);
		contentPane.add(lblNewLabel1);

		JLabel lblNewLabel2 = new JLabel("<html><head></head><body><a href=\"http://espeak.sourceforge.net\">http://espeak.sourceforge.net</a></body></html>");
		lblNewLabel2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("firefox http://espeak.sourceforge.net/");
				} catch (IOException e) {
					logger.warn(e);
				}
			}
		});
		
		lblNewLabel2.setFont(new Font(DIALOG_TEXT, Font.PLAIN, 10));
		lblNewLabel2.setBounds(10, 57, 356, 31);
		contentPane.add(lblNewLabel2);

		JLabel lblNewLabel3 = new JLabel("<html><body>Licensed under " +
					"<a href = \"http://espeak.sourceforge.net/license.html\">" +
				"GNU General Public License version 3" +
					"</a></body></html>");
		lblNewLabel3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Runtime rt = Runtime.getRuntime();
				try {
					rt.exec("firefox http://espeak.sourceforge.net/license.html");
				} catch (IOException e) {
					logger.warn(e);
				}
			}
		});
		lblNewLabel3.setFont(new Font(DIALOG_TEXT, Font.PLAIN, 10));
		lblNewLabel3.setBounds(10, 82, 356, 31);
		contentPane.add(lblNewLabel3);
		
		setVisible(true);
	}
}
