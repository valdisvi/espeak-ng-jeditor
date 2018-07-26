package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class KeyStrikeTest {
	
	static MainWindow cs;
	private static FrameFixture fixture;
	private static MainWindow mainW;
	private static Logger logger = Logger.getLogger(HelpMenuItemTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public static void tearDownAfterClass() throws Exception {
		  fixture.cleanUp();
	}

	@Test
	public void testRight() throws InterruptedException {
		 int numberOfTabs = fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length;
			fixture.menuItem(mainW.mntmOpen.getText()).click();
			fixture.fileChooser().selectFile(new File("/home/student/Documents/code/espeak-ng/phsource/j/je"));
			fixture.fileChooser().approve();
			assertEquals(numberOfTabs+1, fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length);
			fixture.pressAndReleaseKeys(KeyEvent.VK_RIGHT);
			
	}

}
