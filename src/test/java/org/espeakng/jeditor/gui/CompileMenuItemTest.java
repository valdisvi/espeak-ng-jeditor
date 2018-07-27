package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class CompileMenuItemTest {

	static MainWindow cs;
	private static FrameFixture fixture;
	private static MainWindow mainW;
	private static Logger logger = Logger.getLogger(CompileMenuItemTest.class);
	
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
		mainW.setSize(new Dimension(1000, 850));
	}
	
    @AfterClass
    public static void tearDownAfterClass() {
        fixture.cleanUp();
    }

	@Test
	public void compileTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/en_dict");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.mntmCompileDictionary.getText()).click();
		File selectedFile = new File("../espeak-ng/dictsource/en_list");
		assertTrue("English list file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile dictionary", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileDebugTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/ru_dict");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.mntmCompileDictionarydebug.getText()).click();
		File selectedFile = new File("../espeak-ng/dictsource/ru_list");
		assertTrue("Russian list file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile dictionary", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compilePhonemeTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/phondata");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.mntmCompilePhonemeData.getText()).click();
		assertTrue("Failed to compile phoneme data", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileMbrolaTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/mbrola_ph/en1_phtrans");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.mntmCompileMbrolaPhonemes.getText()).click();
		File selectedFile = new File("../espeak-ng/phsource/mbrola/en1");
		assertTrue("English mbrola file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile mbrola data", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileIntonationTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/intonations");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.mntmCompileIntonationData.getText()).click();
		assertTrue("Failed to compile intonation data", prevMod != compiledFile.lastModified());
	}

}
