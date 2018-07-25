package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.File;
import java.util.concurrent.Callable;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class SpeakMenuItemTest {

	static MainWindow cs;

	private static FrameFixture fixture;
	private static MainWindow mainW;


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
	
    @AfterClass
    public static void tearDownAfterClass() {
        fixture.cleanUp();
    }
	
	 @Test
	 public void testTranslateButton()   {
		 mainW.textAreaIn.setText("Hi!");
		 
		 fixture.menuItem(mainW.mntmTranslate.getText()).click();
		assertEquals("h'aI", mainW.textAreaOut.getText().trim());
		
		fixture.tabbedPane("Text").selectTab(1);
		fixture.button("TranslateB").click();
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
		
		fixture.tabbedPane("Text").selectTab(1);
		mainW.textAreaIn.setText("hello");
		fixture.button("ShowRulesB").click();
		assertEquals("Found: 'hello' [h@loU]  \n h@l'oU", mainW.textAreaOut.getText().trim());
		mainW.textAreaIn.setText("h!");
		fixture.button("ShowRulesB").click();
		assertEquals("Found: 'h' [eItS]  \n 'eItS", mainW.textAreaOut.getText().trim());
		mainW.textAreaIn.setText("!");
		fixture.button("ShowRulesB").click();
		assertEquals("Translate '!'\n 22	!        [_:Ekskl@meIS@n_:]\n\n _:'Ekskl@m,eIS@n_:\n", mainW.textAreaOut.getText());
	}
	 @Test
	 public void testShowIPAButton()   {
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
	 public void testSpeakFileButton()   {
		 	assertFalse(mainW.btnPause.isEnabled());
			assertFalse(mainW.btnStop.isEnabled());
			assertFalse(mainW.mntmPause.isEnabled());
			assertFalse(mainW.mntmStop.isEnabled());
		 	fixture.menuItem("SpeakFile").click();
			fixture.fileChooser().selectFile(new File("/home/student/Documents/code/espeak-ng/espeak-ng-data/phondata-manifest"));
			fixture.fileChooser().approve();
			assertTrue(mainW.btnPause.isEnabled());
			assertTrue(mainW.btnStop.isEnabled());
			assertFalse(mainW.btnSpeak.isEnabled());
			assertTrue(mainW.mntmPause.isEnabled());
			assertTrue(mainW.mntmStop.isEnabled());
			
	}
}
