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

public class CompileMenuItemTest {

	static MainWindow cs;
	private static FrameFixture fixture;
	private static MainWindow mainW;
	
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
	public void compileENTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/en_dict");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.menuItemCompileDictionary.getText()).click();
		File selectedFile = new File("../espeak-ng/dictsource/en_list");
		assertTrue("English list file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile dictionary", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileRUDebugTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/ru_dict");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.menuItemCompileDictionarydebug.getText()).click();
		File selectedFile = new File("../espeak-ng/dictsource/ru_list");
		assertTrue("Russian list file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile dictionary", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileLVDebugTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/lv_dict");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.menuItemCompileDictionarydebug.getText()).click();
		File selectedFile = new File("../espeak-ng/dictsource/lv_list");
		assertTrue("Latvian list file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile dictionary", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileKRDebugTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/ko_dict");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.menuItemCompileDictionarydebug.getText()).click();
		File selectedFile = new File("../espeak-ng/dictsource/ko_list");
		assertTrue("Korean list file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile dictionary", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileJPDebugTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/ja_dict");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.menuItemCompileDictionarydebug.getText()).click();
		File selectedFile = new File("../espeak-ng/dictsource/ja_list");
		assertTrue("Japanese list file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile dictionary", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileSPDebugTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/es_dict");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.menuItemCompileDictionarydebug.getText()).click();
		File selectedFile = new File("../espeak-ng/dictsource/es_list");
		assertTrue("Spanish list file not found", selectedFile.exists());
		fixture.fileChooser().selectFile(selectedFile);
		fixture.fileChooser().approve();
		assertTrue("Failed to compile dictionary", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compilePhonemeTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/phondata");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.menuItemCompilePhonemeData.getText()).click();
		assertTrue("Failed to compile phoneme data", prevMod != compiledFile.lastModified());
	}
	
	@Test
	public void compileMbrolaTest() {
		File compiledFile = new File("../espeak-ng/espeak-ng-data/mbrola_ph/en1_phtrans");
		long prevMod = compiledFile.lastModified();
		fixture.menuItem(mainW.menuItemCompileMbrolaPhonemes.getText()).click();
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
		fixture.menuItem(mainW.menuItemCompileIntonationData.getText()).click();
		assertTrue("Failed to compile intonation data", prevMod != compiledFile.lastModified());
	}
}
