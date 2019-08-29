package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;

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

public class VoiceMenuItemTest {

	static MainWindow cs;
	private static FrameFixture fixture;
	private static MainWindow mainW;
	private static Logger logger = Logger.getLogger(VoiceMenuItemTest.class);
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(10);
	
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
	public void testSelectVoiceVariant() {
		fixture.menuItem(mainW.menuItemSelectVoiceVariant.getName()).click();
		fixture.fileChooser().selectFile(new File("../espeak-ng/espeak-ng-data/voices/!v/f1"));
		fixture.fileChooser().approve();
		assertEquals("Voice variant not changed.", "f1", mainW.eventHandlers.getVoiceVariant());
	}

	@Test
	public void testSelectVoice() {
		String voiceVar = mainW.eventHandlers.getVoiceVariant();
		if (voiceVar.length()>0) voiceVar = "+" + voiceVar;
		fixture.menuItem(mainW.rdbtnmenuItemEnglish.getName()).click();
		assertEquals("Failed to set english voice", "en" + voiceVar, mainW.eventHandlers.getVoice());
		fixture.menuItem(mainW.rdbtnmenuItemLatvian.getName()).click();
		assertEquals("Failed to set latvian voice", "lv" + voiceVar, mainW.eventHandlers.getVoice());
		fixture.menuItem(mainW.rdbtnmenuItemPolish.getName()).click();
		assertEquals("Failed to set polish voice", "pl" + voiceVar, mainW.eventHandlers.getVoice());
//		fixture.menuItem(mainW.rdbtnmntmRussian.getName()).click();
//		assertEquals("Failed to set russian voice", "ru" + voiceVar, mainW.eventHandlers.getVoice());
		fixture.menuItem(mainW.rdbtnmenuItemKorean.getName()).click();
		assertEquals("Failed to set korean voice", "ko" + voiceVar, mainW.eventHandlers.getVoice());
		fixture.menuItem(mainW.rdbtnmenuItemJapanese.getName()).click();
		assertEquals("Failed to set japanese voice", "ja" + voiceVar, mainW.eventHandlers.getVoice());
		fixture.menuItem(mainW.rdbtnmenuItemSpanish.getName()).click();
		assertEquals("Failed to set spanish voice", "es" + voiceVar, mainW.eventHandlers.getVoice());
		fixture.menuItem(mainW.rdbtnmenuItemEnglish.getName()).click();
		assertEquals("Failed to set english voice", "en" + voiceVar, mainW.eventHandlers.getVoice());
	}
	
}
