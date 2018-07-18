package org.espeakng.jeditor.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This class is used to set or change the language of the program.
 */
public class Language {
	private static ArrayList<String> translation;

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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
		mainW.mntmOpen.setText(translation.get(1));
		mainW.mntmOpen2.setText(translation.get(2));
		mainW.mntmSave.setText(translation.get(3));;
		mainW.mntmSaveAs.setText(translation.get(4));;
		mainW.mntmClose.setText(translation.get(5));;
		mainW.mntmQuit.setText(translation.get(6));
		
		// menuBar group Speak
		mainW.mnSpeak.setText(translation.get(7));
		mainW.mntmTranslate.setText(translation.get(8));
		mainW.mntmShowRules.setText(translation.get(9));
		mainW.mntmShowIPA.setText(translation.get(10));
		mainW.mntmSpeak.setText(translation.get(11));
		mainW.mntmSpeakfile.setText(translation.get(12));
		mainW.mntmPause.setText(translation.get(13));
		mainW.mntmStop.setText(translation.get(14));
		
		// menuBar group Voice
		mainW.mnVoice.setText(translation.get(15));
		
		//mainW.mntmSelectVoice.setText(translation.get(16));
		mainW.mntmSelectVoiceVariant.setText(translation.get(17));
		mainW.mnSelectVoice.setText(translation.get(18));
		mainW.rdbtnmntmEnglish.setText(translation.get(19));
		mainW.rdbtnmntmRussian.setText(translation.get(20));
		mainW.rdbtnmntmLatvian.setText(translation.get(21));
		mainW.rdbtnmntmPolish.setText(translation.get(22));
		
		// menuBar group Options
		mainW.mnOptions.setText(translation.get(23));
		mainW.mnSetPaths.setText(translation.get(24));
		mainW.mntmMasterPhonemesFile.setText(translation.get(25));
		mainW.mntmPhonemeDataSource.setText(translation.get(26));
		mainW.mntmDictionaryDataSource.setText(translation.get(27));
		mainW.mntmSynthesizedSoundWAVfile.setText(translation.get(28));
		mainW.mntmVoiceFileToModifyFormantPeaks.setText(translation.get(29));
		mainW.mnLanguage.setText(translation.get(30));
		mainW.mntmEnglish.setText(translation.get(31));
		mainW.mntmLatvian.setText(translation.get(32));
		mainW.mntmRussian.setText(translation.get(33));
		mainW.mntmSpeed.setText(translation.get(34));
		mainW.mntmSpeakPunctuation.setText(translation.get(35));
		mainW.mntmSpeakCharacters.setText(translation.get(36));
		mainW.mntmSpeakCharacterName.setText(translation.get(37));
		
		// menuBar group Tools
		mainW.mnTools.setText(translation.get(38));
		mainW.mnMakeVowelsChart.setText(translation.get(39));
		mainW.mntmFromCompiledPhoneme.setText(translation.get(40));
		mainW.mntmFromDirectoryVowelFiles.setText(translation.get(41));
		mainW.mnProcessLexicon.setText(translation.get(42));
		mainW.mntmPLRussian.setText(translation.get(43));
		mainW.mntmPLBulgarian.setText(translation.get(44));
		mainW.mntmPLGerman.setText(translation.get(45));
		mainW.mntmPLItalian.setText(translation.get(46));
		mainW.mntmConvertFileUTF8.setText(translation.get(47));
		mainW.mntmCountWordFrequencies.setText(translation.get(48));
		mainW.mntmTesttemporary.setText(translation.get(49));
		
		// menuBar group Compile
		mainW.mnCompile.setText(translation.get(50));
		mainW.mntmCompileDictionary.setText(translation.get(51));
		mainW.mntmCompileDictionarydebug.setText(translation.get(52));
		mainW.mntmCompilePhonemeData.setText(translation.get(53));
		mainW.mntmCompileAtSample.setText(translation.get(54));
		mainW.mntmCompileMbrolaPhonemes.setText(translation.get(55));
		mainW.mntmCompileIntonationData.setText(translation.get(56));
		mainW.mntmLayoutrulesFile.setText(translation.get(57));
		mainW.mntmSortrulesFile.setText(translation.get(58));
		
		// menuBar group Help
		mainW.mnHelp.setText(translation.get(59));
		mainW.mntmEspeakDocumentation.setText(translation.get(60));
		mainW.mntmAbout.setText(translation.get(61));
	}
}
