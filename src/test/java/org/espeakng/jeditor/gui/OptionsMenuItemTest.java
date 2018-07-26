package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;
import static org.assertj.swing.finder.WindowFinder.findFrame;


import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.espeakng.jeditor.data.Command;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class OptionsMenuItemTest {

	static MainWindow cs;
	private static FrameFixture fixture;
	private static MainWindow mainW;
	private static Logger logger = Logger.getLogger(CompileMenuItemTest.class);
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(15);
	
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
	public void setPathsTest() {
		File setPath = new File("../espeak-ng");
		String absPath = setPath.getName();
		
		fixture.menuItem(mainW.mntmMasterPhonemesFile.getName()).click();
		selectAndApprove(setPath);
		assertTrue("Master phonemes file location not changed", 
				EventHandlers.getFolders().get(Command.PH_FILE).getName().equals(absPath));
		
		fixture.menuItem(mainW.mntmPhonemeDataSource.getName()).click();
		selectAndApprove(setPath);
		assertTrue("Phonemes source file location not changed", 
				EventHandlers.getFolders().get(Command.PHONEME_SOURCE).getName().equals(absPath));
		
		fixture.menuItem(mainW.mntmDictionaryDataSource.getName()).click();
		selectAndApprove(setPath);
		assertTrue("Dictionary source file location not changed", 
				EventHandlers.getFolders().get(Command.DICT_SOURCE).getName().equals(absPath));
		
		fixture.menuItem(mainW.mntmSynthesizedSoundWAVfile.getName()).click();
		selectAndApprove(setPath);
		assertTrue("Synthesized WAV file location not changed", 
				EventHandlers.getFolders().get(Command.WAV_FILE).getName().equals(absPath));
		
		fixture.menuItem(mainW.mntmVoiceFileToModifyFormantPeaks.getText()).click();
		selectAndApprove(setPath);
		assertTrue("Voice file location not changed", 
				EventHandlers.getFolders().get(Command.VOICE_FILE).getName().equals(absPath));
		
	}
	
	private void selectAndApprove(File toSelect) {
		fixture.fileChooser().selectFile(toSelect);
		fixture.fileChooser().approve();
	}
	
	@Test
	public void setLanguageTest() {
		// Roughly checks if at least 1 word has been changed to file word
		try {
			BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/latvian.txt"));
			fixture.menuItem(mainW.mntmLatvian.getName()).click();
			checkChanges(br.readLine());
			br.close();
			br = new BufferedReader(new FileReader("./src/main/resources/russian.txt"));
			fixture.menuItem(mainW.mntmRussian.getName()).click();
			checkChanges(br.readLine());
			br.close();
			br = new BufferedReader(new FileReader("./src/main/resources/tamil.txt"));
			fixture.menuItem(mainW.mntmTamil.getName()).click();
			checkChanges(br.readLine());
			br.close();
			br = new BufferedReader(new FileReader("./src/main/resources/english.txt"));
			fixture.menuItem(mainW.mntmEnglish.getName()).click();
			checkChanges(br.readLine());
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Failed to read file");
		}
	}
	
	private void checkChanges(String word) {
		assertEquals("Error in changing language", mainW.mnFile.getText(), word);
	}
	
	@Test
	public void setSpeedTest() {
		int oldValue = mainW.optionsSpeed.getSpinnerValue();
		fixture.menuItem(mainW.mntmSpeed.getText()).click();
		FrameFixture speedFixture = findFrame("Speed").using(fixture.robot());
		speedFixture.spinner("spinner").increment(30);
		speedFixture.button("OK").click();
		int newValue = mainW.optionsSpeed.getSpinnerValue();
		assertTrue("Spinner value not changed!", oldValue != newValue);
		
		fixture.menuItem(mainW.mntmSpeed.getText()).click();
		speedFixture.spinner("spinner").decrement(20);
		speedFixture.button("Cancel").click();
		assertEquals("Spinner saves value on cancel", newValue, mainW.optionsSpeed.getSpinnerValue());
	}
	
	@Test
	public void speakPunctuationTest() {
		mainW.textAreaIn.setText("*******************Hello******************");
		fixture.menuItem(mainW.mntmSpeakPunctuation.getName()).click();
		checkRunningProcess();
	}
	
	@Test
	public void speakCharactersTest() {
		mainW.textAreaIn.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
				+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"
				+ " when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
		fixture.menuItem(mainW.mntmSpeakCharacters.getName()).click();
		checkRunningProcess();
	}
	
	@Test
	public void speakCharacterNameTest() {
		mainW.textAreaIn.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
				+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"
				+ " when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
		fixture.menuItem(mainW.mntmSpeakCharacterName.getName()).click();
		checkRunningProcess();
	}

	private void checkRunningProcess() {
		Runtime rt = Runtime.getRuntime();
		try {
			//Kills current running speak process (if there is one)
			rt.exec("pkill -9 aplay");
			
			Process pc = rt.exec("pgrep aplay");
			pc.waitFor();
			assertEquals("Running process (aplay) not found", 0, pc.exitValue());
			rt.exec("pkill -9 aplay");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			fail("Command line failure");
		}
	}
	
}
