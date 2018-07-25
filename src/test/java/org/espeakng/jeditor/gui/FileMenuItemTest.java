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

public class FileMenuItemTest {
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
	}
	void testExportButton()   {
		fixture.menuItem(mainW.mntmExportGraph.getText()).click();
	}
	
	 void testOpenButton()   {
		 int numberOfTabs = fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length;
		fixture.menuItem(mainW.mntmOpen.getText()).click();
		fixture.fileChooser().selectFile(new File("/home/student/Documents/code/espeak-ng/phsource/j/je"));
		fixture.fileChooser().approve();
		assertEquals(numberOfTabs+1, fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length);
	}
	
	@Test
	public void testOpen2Button()   {
		 int numberOfTabs = fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length;
		fixture.menuItem(mainW.mntmOpen2.getText()).click();
		fixture.fileChooser().selectFile(new File("/home/student/Documents/code/espeak-ng/phsource/j/ji"));
		fixture.fileChooser().approve();
		assertEquals(numberOfTabs+1, fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length);
	}
	
	 void testCloseButton()   {
		 int numberOfTabs = fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length;
		 if(numberOfTabs==0)
			 assertEquals(false, mainW.mntmClose.isVisible());
		 else{
			 fixture.menuItem(mainW.mntmClose.getText()).click();
				assertEquals(numberOfTabs-1, fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length);
		 }
		
	}
	 @Test
	 public void testCloseAllButton()   {
		 testOpenButton();
		 testOpen2Button();
		 int numberOfTabs = fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length;
		 if(numberOfTabs==0)
			 assertEquals(false, mainW.mntmClose.isVisible());
		fixture.menuItem(mainW.mntmCloseAll.getText()).click();
		assertEquals(0, fixture.tabbedPane("tabbedPaneGraphs").tabTitles().length);
	}
	 
}
