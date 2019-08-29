package org.espeakng.jeditor.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import org.apache.log4j.Logger;
/**
 * This class is used to set or change the language of the program.
 */
public class Language {
	
	private static Logger logger = Logger.getLogger(Language.class.getName());

	
	private static ArrayList<String> translation;

	private Language() {
		throw new IllegalStateException("Language Utility class");
	}
	
	/**
	 * This method is called to change language. It loads a file (formatted in specific way),
	 * containing all the names for buttons. And adds them to an ArrayList.
	 * Then calls initTranslation()
	 * 
	 * @param file - file, formatted in specific way, that contains correct values for names
	 * @param mainW - Main Window
	 */
	public static void initLanguage(File file, MainWindow mainW){
		translation = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			initLanguage(br, mainW);
		} catch (IOException e) {
			logger.warn(e);
		}
		
		initTranslation(mainW);
	}
	
	
	public static void initLanguage(BufferedReader br, MainWindow mainW){
		translation = new ArrayList<>();
		
		String line;
	    
		try {
			while ((line = br.readLine()) != null)
			   translation.add(line);
		
		} catch (IOException e) {
			logger.warn(e);
		}
		
		
		initTranslation(mainW);
	}

	/**
	 * This method uses ArrayList, containing names for buttons and tabs, 
	 * to set names for them.
	 * @param mainW - Main Window
	 */
	public static void initTranslation(MainWindow mainW){
		//There might exist a better way of processing different languages, for example separate ArrayLists
		//for JMenu objects and JMenuItem objects.
		mainW.mnFile.setText(translation.get(0));
		mainW.menuItemOpen.setText(translation.get(1));
		mainW.menuItemOpen2.setText(translation.get(2));
		mainW.menuItemSave.setText(translation.get(3));
		mainW.menuItemSaveAs.setText(translation.get(4));
		mainW.menuItemClose.setText(translation.get(5));
		mainW.menuItemQuit.setText(translation.get(6));
		
		// menuBar group Speak
		mainW.menuSpeak.setText(translation.get(7));
		mainW.menuItemTranslate.setText(translation.get(8));
		mainW.menuItemShowRules.setText(translation.get(9));
		mainW.menuItemShowIPA.setText(translation.get(10));
		mainW.menuItemSpeak.setText(translation.get(11));
		mainW.menuItemSpeakfile.setText(translation.get(12));
		mainW.menuItemPause.setText(translation.get(13));
		mainW.menuItemStop.setText(translation.get(14));
		
		// menuBar group Voice
		mainW.menuVoice.setText(translation.get(15));
		mainW.menuItemSelectVoiceVariant.setText(translation.get(17));
		mainW.menuSelectVoice.setText(translation.get(18));
		mainW.rdbtnmenuItemEnglish.setText(translation.get(19));
	//	mainW.rdbtnmenuItemRussian.setText(translation.get(20));
		mainW.rdbtnmenuItemLatvian.setText(translation.get(21));
		mainW.rdbtnmenuItemPolish.setText(translation.get(22));
		
		// menuBar group Options
		mainW.menuOptions.setText(translation.get(23));
		mainW.menuSetPaths.setText(translation.get(24));
		mainW.menuItemMasterPhonemesFile.setText(translation.get(25));
		mainW.menuItemPhonemeDataSource.setText(translation.get(26));
		mainW.menuItemDictionaryDataSource.setText(translation.get(27));
		mainW.menuItemSynthesizedSoundWAVfile.setText(translation.get(28));
		mainW.menuItemVoiceFileToModifyFormantPeaks.setText(translation.get(29));
		mainW.menuLanguage.setText(translation.get(30));
		mainW.menuItemEnglish.setText(translation.get(31));
		mainW.menuItemLatvian.setText(translation.get(32));
		mainW.menuItemRussian.setText(translation.get(33));
		mainW.menuItemSpeed.setText(translation.get(34));
		mainW.menuItemSpeakPunctuation.setText(translation.get(35));
		mainW.menuItemSpeakCharacters.setText(translation.get(36));
		mainW.menuItemSpeakCharacterName.setText(translation.get(37));
		
		// menuBar group Tools
		mainW.menuTools.setText(translation.get(38));
		mainW.menuMakeVowelsChart.setText(translation.get(39));
		mainW.menuItemFromCompiledPhoneme.setText(translation.get(40));
		mainW.menuItemFromDirectoryVowelFiles.setText(translation.get(41));
		mainW.menuProcessLexicon.setText(translation.get(42));
		mainW.menuItemPLRussian.setText(translation.get(43));
		mainW.menuItemPLBulgarian.setText(translation.get(44));
		mainW.menuItemPLGerman.setText(translation.get(45));
		mainW.menuItemPLItalian.setText(translation.get(46));
		mainW.menuItemCountWordOccurrences.setText(translation.get(48));
		
		// menuBar group Compile
		mainW.mnCompile.setText(translation.get(50));
		mainW.menuItemCompileDictionary.setText(translation.get(51));
		mainW.menuItemCompileDictionarydebug.setText(translation.get(52));
		mainW.menuItemCompilePhonemeData.setText(translation.get(53));
		mainW.menuItemCompileMbrolaPhonemes.setText(translation.get(55));
		mainW.menuItemCompileIntonationData.setText(translation.get(56));
		
		// menuBar group Help
		mainW.menuHelp.setText(translation.get(59));
		mainW.menuItemEspeakDocumentation.setText(translation.get(60));
		mainW.menuItemAbout.setText(translation.get(61));
		
		mainW.btnZoom.setText(translation.get(62));
		mainW.btnZoom_1.setText(translation.get(63));
		mainW.btnTranslate.setText(translation.get(65));
		mainW.btnShowRules.setText(translation.get(66));
		mainW.btnShowIPA.setText(translation.get(68));
		mainW.btnSpeak.setText(translation.get(67));
		mainW.menuItemCloseAll.setText(translation.get(69));
		mainW.menuItemExportGraph.setText(translation.get(70));
		mainW.menuItemTamil.setText(translation.get(71));
		mainW.menuItemKorean.setText(translation.get(72));
		mainW.menuItemJapanese.setText(translation.get(73));
		mainW.menuItemSpanish.setText(translation.get(74));
		
		//text area
				mainW.textAreaIn.setText(translation.get(75));
		
		mainW.rdbtnmenuItemKorean.setText(translation.get(78));
		mainW.rdbtnmenuItemJapanese.setText(translation.get(79));
		mainW.rdbtnmenuItemSpanish.setText(translation.get(80));
		
	
	
	
	}

}