package org.espeakng.jeditor.gui;
//package org.espeakng.jeditor.data;
//package org.espeakng.jeditor.jni;
//package org.espeakng.jeditor.utils;

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
import org.espeakng.jeditor.data.GraphTest;
import org.espeakng.jeditor.data.VowelChartTest;
import org.espeakng.jeditor.jni.ESpeakServiceTest;
import org.espeakng.jeditor.jni.TextFieldsHasRightValues;
import org.espeakng.utils.CommandUtilitiesTest;
import org.espeakng.utils.ProcessThreadTest;

@RunWith(Suite.class)
// Keep HelpMenuItemTest last as it opens a webpage and breaks every other test that comes after it
@SuiteClasses({
	GraphTest.class,
	VowelChartTest.class,
	ESpeakServiceTest.class,
	TextFieldsHasRightValues.class,
	CommandUtilitiesTest.class,
	ProcessThreadTest.class,
	CompileMenuItemTest.class, 
	FileMenuItemTest.class, 
	VoiceMenuItemTest.class,
	OptionsMenuItemTest.class, 
	ToolsMenuItemTest.class, 
	SpeakMenuItemTest.class, 
	HelpMenuItemTest.class })

public class AllTests {
	
}
