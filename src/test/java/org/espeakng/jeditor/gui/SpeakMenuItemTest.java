package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class SpeakMenuItemTest {

	static MainWindow cs;
	static Color color;

	private static FrameFixture fixture;
	private static MainWindow mainW;

	private static Logger logger = Logger.getLogger(SpeakMenuItemTest.class);

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	/**
	 * Prepare application for testing
	 */
	@BeforeClass
	public static void onSetUp() {

	
		mainW = GuiActionRunner.execute(new Callable<MainWindow>() {
			@Override
			public MainWindow call() throws Exception {
				return MainWindow.getMainWindow();
			}
		});
		fixture = new FrameFixture(mainW);
		fixture.show();
		mainW.setSize(new Dimension(1000, 600));
	}
	 @Test
	 public void testTranslateButton()   {
		 mainW.textAreaIn.setText("Hi!");
		 fixture.menuItem(mainW.mntmTranslate.getText()).click();
		assertEquals("h'aI", mainW.textAreaOut.getText().trim());
	}
	 @Test
	 public void testhowRulesButton()   {
		 mainW.textAreaIn.setText("hello");
		 fixture.menuItem(mainW.mntmShowRules.getText()).click();
		assertEquals("Found: 'hello' [h@loU]  \n h@l'oU", mainW.textAreaOut.getText().trim());
		mainW.textAreaIn.setText("h!");
		 fixture.menuItem(mainW.mntmShowRules.getText()).click();
		assertEquals("Found: 'h' [eItS]  \n 'eItS", mainW.textAreaOut.getText().trim());
		mainW.textAreaIn.setText("!");
		 fixture.menuItem(mainW.mntmShowRules.getText()).click();
		assertEquals("Translate '!'\n 22	!        [_:Ekskl@meIS@n_:]\n\n _:'Ekskl@m,eIS@n_:\n", mainW.textAreaOut.getText());
	}
	 @Test
	 public void testhowIPAButton()   {
		 mainW.textAreaIn.setText("hello");
		 fixture.menuItem(mainW.mntmShowRules.getText()).click();
		assertEquals("Found: 'hello' [h@loU]  \n h@l'oU", mainW.textAreaOut.getText().trim());
		mainW.textAreaIn.setText("h!");
		 fixture.menuItem(mainW.mntmShowRules.getText()).click();
		assertEquals("Found: 'h' [eItS]  \n 'eItS", mainW.textAreaOut.getText().trim());
		mainW.textAreaIn.setText("!");
		 fixture.menuItem(mainW.mntmShowRules.getText()).click();
		assertEquals("Translate '!'\n 22	!        [_:Ekskl@meIS@n_:]\n\n _:'Ekskl@m,eIS@n_:\n", mainW.textAreaOut.getText());
	}
}
