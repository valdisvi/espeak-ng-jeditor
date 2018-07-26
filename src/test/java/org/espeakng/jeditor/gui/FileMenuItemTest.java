package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.File;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.swing.JPanel;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileMenuItemTest {
	static MainWindow cs;
	private static FrameFixture fixture;
	private static MainWindow mainW;

	@Rule
	public Timeout globalTimeout = Timeout.seconds(3);

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
	public void test01OpenButton() {
		int numberOfTabs = MainWindow.tabbedPaneGraphs.getTabCount();
		File temp = new File("../espeak-ng/phsource/vowel/u");
		fixture.menuItem(mainW.mntmOpen.getName()).click();
		fixture.fileChooser().selectFile(temp);
		fixture.fileChooser().approve();
		assertTrue("File /espeak-ng/phsource/vowel/u does not exist", temp.exists());
		assertEquals("Failed to open new tab", numberOfTabs+1, MainWindow.tabbedPaneGraphs.getTabCount());
	}
	
	@Test
	public void test02Open2Button()   {
		int numberOfTabs = MainWindow.tabbedPaneGraphs.getTabCount();
		File temp = new File("../espeak-ng/phsource/vowel/e");
		fixture.menuItem(mainW.mntmOpen2.getName()).click();
		fixture.fileChooser().selectFile(temp);
		fixture.fileChooser().approve();
		assertTrue("File /espeak-ng/phsource/vowel/e does not exist", temp.exists());
		assertEquals("Failed to open new tab", numberOfTabs+1, MainWindow.tabbedPaneGraphs.getTabCount());
	}
	
	//@Test
	public void test03ExportButton()   {
		fail("implement me!");
		int filename = new Random().nextInt();
		File graphFile = new File("./src/main/resources/" + filename + ".png");
		assertFalse("Generated filename already exists", graphFile.exists());
		
		assertTrue("Graph not exported", graphFile.exists());
		graphFile.delete();
	}
	
	@Test
	public void test04SaveButton()   {
		File vowel = new File("../espeak-ng/phsource/vowel/u");
		long vowelModifiedAt = vowel.lastModified();

		fixture.menuItem(mainW.mntmOpen.getName()).click();
		fixture.fileChooser().selectFile(vowel);
		fixture.fileChooser().approve();
		assertTrue("Vowel file not found", vowel.exists());
		
		fixture.menuItem(mainW.mntmSave.getName()).click();
		assertTrue("Failed to save vowel", vowelModifiedAt != vowel.lastModified());
		
	}
	
	@Test
	public void test05SaveAsButton()   {
		int fileName = Math.abs(new Random().nextInt());
		File testFile = new File("/src/main/resources/" + fileName);
		assertFalse("Test file already exists", testFile.exists());
		File vowel = new File("../espeak-ng/phsource/vowel/u");
		assertTrue("Vowel file not found", vowel.exists());
		
		fixture.menuItem(mainW.mntmSaveAs.getName()).click();
		fixture.fileChooser().setCurrentDirectory(testFile.getParentFile()).fileNameTextBox().enterText(Integer.toString(fileName));
		fixture.fileChooser().approve();
		assertTrue("Vowel file not saved!", vowel.exists());
		testFile.delete();
	}
	
	@Test
	public void test06CloseButton()   {
		int numberOfTabs = MainWindow.tabbedPaneGraphs.getTabCount();
		if (numberOfTabs == 0) {
			MainWindow.tabbedPaneGraphs.addTab("TestTab", new JPanel());
			assertFalse("Failed to add new tab", numberOfTabs == MainWindow.tabbedPaneGraphs.getTabCount());
			numberOfTabs++;
		}
		fixture.menuItem(mainW.mntmClose.getName()).click();
		assertEquals("Failed to close a tab", numberOfTabs-1, MainWindow.tabbedPaneGraphs.getTabCount());
	}
	
	@Test
	public void test07CloseAllButton()   {
			
		if (MainWindow.tabbedPaneGraphs.getTabCount() == 0) {
			MainWindow.tabbedPaneGraphs.addTab("TestTab1", new JPanel());
			MainWindow.tabbedPaneGraphs.addTab("TestTab2", new JPanel());
			assertFalse("Failed to add new tab", 0 == MainWindow.tabbedPaneGraphs.getTabCount());
		}
		fixture.menuItem(mainW.mntmCloseAll.getName()).click();
		assertEquals("Failed to close tabs", 0, MainWindow.tabbedPaneGraphs.getTabCount());
	}
	
}
