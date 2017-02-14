package interfacePckg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class Language {
	private static ArrayList<String> translation;
	public static void initLanguage(File file, MainWindow mainW){
		BufferedReader br;
		translation=new ArrayList<String>();
		try {
			String line;
			br = new BufferedReader(new FileReader(file));
		    while ((line = br.readLine()) != null) {
			       translation.add(line);
			    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initTranslation(mainW);
	}
	
	public static void initTranslation(MainWindow mainW){
		//There might exist a better way of processing different languages, for example separate ArrayLists
		//for JMenu objects and JMenuItem objects.
		mainW.mnNewMenu.setText(translation.get(0));
		mainW.mntmNewMenuItem.setText(translation.get(1));
		mainW.mntmOpen.setText(translation.get(2));
		mainW.mntmSave.setText(translation.get(3));;
		mainW.mntmSaveAs.setText(translation.get(4));;
		mainW.mntmClose.setText(translation.get(5));;
		mainW.mntmQuit.setText(translation.get(6));
		// menuBar group Speak
		mainW.mnNewMenu_1.setText(translation.get(7));
		mainW.mntmNewMenuItem_1.setText(translation.get(8));
		mainW.mntmShowRules.setText(translation.get(9));
		mainW.mntmNewMenuItem_2.setText(translation.get(10));
		mainW.mntmNewMenuItem_3.setText(translation.get(11));
		mainW.mntmNewMenuItem_4.setText(translation.get(12));
		mainW.mntmNewMenuItem_5.setText(translation.get(13));
		mainW.mntmNewMenuItem_6.setText(translation.get(14));
		// menuBar group Voice
		mainW.mnNewMenu_2.setText(translation.get(15));
		mainW.mntmNewMenuItem_7.setText(translation.get(16));
		mainW.mntmNewMenuItem_8.setText(translation.get(17));
		mainW.mnNewMenu_7.setText(translation.get(18));
		mainW.rdbtnmntmEnglish.setText(translation.get(19));
		mainW.rdbtnmntmRussian.setText(translation.get(20));
		mainW.rdbtnmntmLatvian.setText(translation.get(21));
		mainW.rdbtnmntmPolish.setText(translation.get(22));
		// menuBar group Options
		mainW.mnNewMenu_3.setText(translation.get(23));
		mainW.mnNewMenu_4.setText(translation.get(24));
		mainW.mntmNewMenuItem_9.setText(translation.get(25));
		mainW.mntmNewMenuItem_10.setText(translation.get(26));
		mainW.mntmNewMenuItem_11.setText(translation.get(27));
		mainW.mntmNewMenuItem_12.setText(translation.get(28));
		mainW.mntmNewMenuItem_13.setText(translation.get(29));
		mainW.mnNewMenu_8.setText(translation.get(30));
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
		mainW.mntmNewMenuItem_14.setText(translation.get(41));
		mainW.mnNewMenu_5.setText(translation.get(42));
		mainW.mntmNewMenuItem_15.setText(translation.get(43));
		mainW.mntmNewMenuItem_16.setText(translation.get(44));
		mainW.mntmNewMenuItem_17.setText(translation.get(45));
		mainW.mntmNewMenuItem_18.setText(translation.get(46));
		mainW.mntmNewMenuItem_19.setText(translation.get(47));
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
		mainW.mnNewMenu_6.setText(translation.get(59));
		mainW.mntmNewMenuItem_20.setText(translation.get(60));
		mainW.mntmNewMenuItem_21.setText(translation.get(61));
	}
}
