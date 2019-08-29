package org.espeakng.jeditor.gui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.espeakng.jeditor.gui.CompileMenuItemTest;
import org.espeakng.jeditor.gui.FileMenuItemTest;
import org.espeakng.jeditor.gui.SpeakMenuItemTest;
import org.espeakng.jeditor.gui.VoiceMenuItemTest;
import org.espeakng.jeditor.gui.OptionsMenuItemTest;
import org.espeakng.jeditor.gui.ToolsMenuItemTest;
import org.espeakng.jeditor.gui.HelpMenuItemTest;

@RunWith(Suite.class)
// Keep HelpMenuItemTest last as it opens a webpage and breaks every other test that comes after it
@SuiteClasses({ 
	CompileMenuItemTest.class, 
	FileMenuItemTest.class, 
	SpeakMenuItemTest.class, 
	VoiceMenuItemTest.class,
	OptionsMenuItemTest.class, 
	ToolsMenuItemTest.class, 
	HelpMenuItemTest.class })

public class AllTests {
	
}
