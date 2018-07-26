package org.espeakng.jeditor.gui;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

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
public class SpeakMenuItemTest {

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
	
	@Test
	public void test01TranslateButton()   {
		mainW.textAreaIn.setText("hi!");
		fixture.menuItem(mainW.mntmTranslate.getName()).click();
		assertEquals("h'aI", mainW.textAreaOut.getText().trim());
		
		fixture.tabbedPane("Text").selectTab(1);
		fixture.button("TranslateB").click();
		assertEquals("h'aI", mainW.textAreaOut.getText().trim());
	}
	
	
	public void test02ShowRulesButton()   {
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
	 public void test03ShowIPAButton()   {
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
	 public void test04SpeakFileButton()   {

		 	fixture.menuItem("SpeakFile").click();
			fixture.fileChooser().selectFile(new File("../espeak-ng/espeak-ng-data/phondata-manifest"));
			fixture.fileChooser().approve();
			
			Runtime rt = Runtime.getRuntime();
			try {
				rt.exec("pkill -9 aplay");
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
}
