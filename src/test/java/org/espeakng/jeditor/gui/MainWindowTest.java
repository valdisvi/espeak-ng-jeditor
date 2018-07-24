package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;


import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.concurrent.Callable;


import org.apache.log4j.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


// Call test methods in

public class MainWindowTest {
	
	static MainWindow cs;
	static Color color;

	private static FrameFixture fixture;
	private static MainWindow mainW;

	private static Logger logger = Logger.getLogger(MainWindowTest.class);

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);

	/**
	 * Prepare application for testing
	 */
	@BeforeClass
	public static void onSetUp() {

		/*- if project is compiled to Java8, Lambda expression can be used, e.g.
		 *
		 *     app = GuiActionRunner.execute(() -> new ColorSlider());
		 *
		 * For Java7 anonymous class should be used as following:
		 */
		mainW = GuiActionRunner.execute(new Callable<MainWindow>() {
			@Override
			public MainWindow call() throws Exception {
				return MainWindow.getMainWindow();
			}
		});
		/*
		 * If test Application extends JFrame, you can assign reference from it
		 * to frame fixture directly. Otherwise you have to provide access to
		 * used JFrame object of the application.
		 */
		fixture = new FrameFixture(mainW);
		// Show window of the application
		fixture.show();
		// This is just workaround to fix size of the application window:
		mainW.setSize(new Dimension(1000, 600));
		//mainW.setBounds(100, 100, 450, 300);
	}

	/*
	 * 
	 * Tests for File Menu 
	 * 
	*/
	@Test
	public void testOpenButtonAndExportButton() { 
		testOpenButton(); 
		testExportButton();
		testCloseButton();
		testCloseAllButton();
		}
	void testExportButton()   {
		fixture.menuItem(mainW.mntmExportGraph.getText()).click();
	//	fixture.fileChooser().selectFile(new File("/home/student/Documents/code/espeak-ng/phsource/j/ji"));
	//fixture.fileChooser().approve();
		
	}
	
	 void testOpenButton()   {
		fixture.menuItem(mainW.mntmOpen.getText()).click();
		fixture.fileChooser().selectFile(new File("/home/student/Documents/code/espeak-ng/phsource/j/je"));
		fixture.fileChooser().approve();
	}
	
	@Test
	public void testOpen2Button()   {
		fixture.menuItem(mainW.mntmOpen2.getText()).click();
		fixture.fileChooser().selectFile(new File("/home/student/Documents/code/espeak-ng/phsource/j/ji"));
		fixture.fileChooser().approve();
		
	}
	
	 void testCloseButton()   {
		 int numberOfTabs = fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length;
		fixture.menuItem(mainW.mntmClose.getText()).click();
		assertEquals(numberOfTabs-1, fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length);
	}
	 @Test
	 public void testCloseAllButton()   {
		 testOpenButton();
		 testOpen2Button();
		fixture.menuItem(mainW.mntmCloseAll.getText()).click();
		assertEquals(0, fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length);
	}
	 
	 
	 /*
	  * Test for Speak Menu
	 */
	 
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
	
	
	
	
	 
}
