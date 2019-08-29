package org.espeakng.jeditor.gui;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class HelpMenuItemTest {

	static MainWindow cs;
	private static FrameFixture fixture;
	private static MainWindow mainW;
	private static Logger logger = Logger.getLogger(HelpMenuItemTest.class);
	
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

	//@Test
	public void showDocumentationTest() {
		fixture.menuItem(mainW.menuItemEspeakDocumentation.getText()).click();
	}
	
	@Test
	public void aboutTest() {
		fixture.menuItem(mainW.menuItemAbout.getText()).click();
		FrameFixture aboutWindow = findFrame("About").using(fixture.robot());
		aboutWindow.button("OK").click();
	}

}
