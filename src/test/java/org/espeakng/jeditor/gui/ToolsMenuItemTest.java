package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;
import static org.assertj.swing.finder.WindowFinder.findFrame;

import java.awt.Dimension;
import java.io.File;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class ToolsMenuItemTest {

	static MainWindow cs;
	private static FrameFixture fixture;
	private static MainWindow mainW;
	private static Logger logger = Logger.getLogger(ToolsMenuItemTest.class);
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);
	
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
    public static void tearDownAfterClass() {
        fixture.cleanUp();
    }
	
	@Test
	public void vowelsFromPhDataTest() {
		int tabCount = MainWindow.tabbedPaneGraphs.getTabCount();
		fixture.menuItem(mainW.mntmFromCompiledPhoneme.getName()).click();
		assertEquals("Failed to open tab from compiled phoneme data", tabCount+1, mainW.tabbedPaneGraphs.getTabCount());
	}
	
	@Test
	public void vowelsFromVowelFilesTest() {
		int tabCount = MainWindow.tabbedPaneGraphs.getTabCount();
		fixture.menuItem(mainW.mntmFromDirectoryVowelFiles.getText()).click();
		File selectedFile = new File("../espeak-ng/phsource/vowelcharts/ru");
		assertTrue("Russian chart file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertEquals("Failed to open tab from vowel file", tabCount+1, mainW.tabbedPaneGraphs.getTabCount());
	}

	private boolean checkIfContains(String lang) {
		for (String str : fixture.tabbedPane("tabbedPaneGraphs").tabTitles()) {
			if (str.equals(lang)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void bulgarianLexiconTest() {
		fail("Bulgarian lexicon not implemented");
	}
	
	@Test
	public void germanLexiconTest() {
		fail("German lexicon not implemented");
	}
	
	@Test
	public void italianLexiconTest() {
		fail("Italian lexicon not implemented");
	}
	
	@Test
	public void russianLexiconTest() {
		fail("Russian lexicon not implemented");
	}
	
	@Test
	public void wordFrequencyTest() {
		fixture.tabbedPane("Text").click();
		mainW.textAreaIn.setText("hello Hello HELLO world! world java swing");
		fixture.menuItem(mainW.mntmCountWordFrequencies.getText()).click();
		FrameFixture frequencyWindow = findFrame("Word Frequencies").using(fixture.robot());
		assertTrue("Incorrect output of word frequency window, expected first line: hello=3",
				frequencyWindow.list("list").contents()[0].equals("hello=3"));
		assertTrue("Incorrect output of word frequency window, expected last line: world=2",
				frequencyWindow.list("list").contents()[3].equals("world=2"));
		frequencyWindow.close();
	}
	
}
