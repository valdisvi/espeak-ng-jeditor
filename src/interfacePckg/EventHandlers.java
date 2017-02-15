package interfacePckg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dataStructure.PhonemeLoad;

//FIXME try extending MainWindow class, should shorten the amount of code
public class EventHandlers {

	private MainWindow mainW;
	private JFileChooser fc;

	public EventHandlers(MainWindow mainW) {
		this.mainW = mainW;
		fc = new JFileChooser("./phsource/");
	}

	ActionListener event = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmQuit) {
				mainW.setVisible(false);
				mainW.dispose();
			}
			if (e.getSource() == mainW.mntmOpen) {
				int returnVal = fc.showOpenDialog(mainW);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					PhonemeLoad.phonemeOpen(file, mainW);
				}
			}
			if (e.getSource() == mainW.mntmNewMenuItem_21) {
				AboutWindow.OpenAboutWindow();
			}
			if (e.getSource() == mainW.mntmSpeed) {
				mainW.optionsSpeed.showOptionsSpeed();
			}
			if (e.getSource() == mainW.mntmEnglish) {
				File file = new File("./languages/english.txt");
				Language.initLanguage(file, mainW);
			}
			if (e.getSource() == mainW.mntmLatvian) {
				File file = new File("./languages/latvian.txt");
				Language.initLanguage(file, mainW);
			}
			if (e.getSource() == mainW.mntmRussian) {
				File file = new File("./languages/russian.txt");
				Language.initLanguage(file, mainW);
			}
		}
	};

	ActionListener translate = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("translate");

		}
	};

	ActionListener speak = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("speak");
		}
	};

	ActionListener showRules = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("showRules");
		}
	};

	ActionListener closeTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			mainW.tabbedPaneGraphs.remove(mainW.tabbedPaneGraphs
					.getSelectedComponent());
		}
	};

	ActionListener closeAllTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			mainW.tabbedPaneGraphs.removeAll();
		}
	};
	
	//requires espeak-ng library
	ActionListener showIpa = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("showIpa");
		}
	};

	ChangeListener getPhoneme = new ChangeListener() {
		public void stateChanged(ChangeEvent arg0) {
			setVisibleMenuItemsFile(mainW);
			PhonemeLoad.getPhoneme((JPanel) mainW.tabbedPaneGraphs
					.getSelectedComponent());
		}
	};

	//requires espeak-ng library
	ActionListener speakPunctuation = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("speakPunctuation");
		}
	};

	//requires espeak-ng library
	ActionListener speakBySymbol = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("speakBySymbol");
		}
	};

	//Any other way of calling browser without relying on one concrete?
	ActionListener showDocumentation = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Runtime rt = Runtime.getRuntime();
			try {
				rt.exec("firefox ./docs/docindex.html");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	public void initHandlers() {

		mainW.tabbedPaneGraphs.addChangeListener(getPhoneme);

		mainW.mntmClose.addActionListener(closeTab);
		
		mainW.mntmCloseAll.addActionListener(closeAllTab);

		mainW.btnNewButton.addActionListener(showRules);

		mainW.btnSpeak.addActionListener(speak);

		mainW.btnTranslate.addActionListener(translate);

		mainW.btnNewButton_1.addActionListener(showIpa);
		
		// file>quit
		mainW.mntmQuit.addActionListener(event);
		// file>open
		mainW.mntmOpen.addActionListener(event);

		// // file>open2 mainW.mntmOpen.addActionListener();
		//
		//
		// Speak
		//
		//
		// // Speak>Translate
		mainW.mntmNewMenuItem_1.addActionListener(translate);
		//
		// // Speak>Show_Rules
		mainW.mntmShowRules.addActionListener(showRules);

		// Speak>Show_IPA 
		mainW.mntmNewMenuItem_2.addActionListener(showIpa);
		
		// Speak>Speak 
		mainW.mntmNewMenuItem_3.addActionListener(speak);

		// // Options>Speak punctuation
		// speaking without ignoring punctuation (says "dot" where is ".")
		mainW.mntmSpeakPunctuation.addActionListener(speakPunctuation);

		// // Options>Speak characters
		// splits word and spell it by symbol
		mainW.mntmSpeakCharacters.addActionListener(speakBySymbol);

		// // Speak>Speak_file... mainW.mntmNewMenuItem_4.addActionListener();
		// // Speak>Pause mainW.mntmNewMenuItem_5.addActionListener();
		//
		// // Speak>Stop mainW.mntmNewMenuItem_6.addActionListener();
		//
		//
		// Voice
		//
		//
		// mainW.mntmNewMenuItem_7.addActionListener();
		//
		// // Voice>Select_Voice_Variant...
		// mainW.mntmNewMenuItem_8.addActionListener();
		//
		//
		// Options
		//
		//
		// // Options>See_Paths>Master_Phonemes_File...
		// mainW.mntmNewMenuItem_9.addActionListener(); 
		// // Options>See_Paths>Phoneme data source...
		// mainW.mntmNewMenuItem_10.addActionListener();
		// // Options>See_Paths>Dictionary data source...
		// mainW.mntmNewMenuItem_11.addActionListener();
		// // Options>See_Paths>Synthesized sound wAV file...
		// mainW.mntmNewMenuItem_12.addActionListener();
		// // Options>See_Paths>Voice file to modify formant peaks...
		// mainW.mntmNewMenuItem_13.addActionListener();
		//
		// Options>Language
		mainW.mntmEnglish.addActionListener(event);
		mainW.mntmLatvian.addActionListener(event);
		mainW.mntmRussian.addActionListener(event);
		// Options>Speed...
		mainW.mntmSpeed.addActionListener(event);

		// // Options>Speak character name
		// mainW.mntmSpeakCharacterName.addActionListener();
		//
		//
		// Tools
		//
		//
		// // Tools>Make Vowels Chart>From compiled phoneme "en"data
		// mainW.mntmFromCompiledPhoneme.addActionListener();
		//
		// // Tools>Make Vowels Chart>From directory of vowel files...
		// mainW.mntmNewMenuItem_14.addActionListener();
		//
		// // Tools>Process Lexicon>Russian
		// mainW.mntmNewMenuItem_15.addActionListener();
		//
		// // Tools>Process Lexicon>Bulgarian
		// mainW.mntmNewMenuItem_16.addActionListener();
		//
		// // Tools>Process Lexicon>German
		// mainW.mntmNewMenuItem_17.addActionListener();
		//
		// // Tools>Process Lexicon>Italian
		// mainW.mntmNewMenuItem_18.addActionListener();
		//
		// // Tools>Convert fimainWle to UTF8...
		// mainW.mntmNewMenuItem_19.addActionListener();
		//
		// // Tools>Count word frequencies...
		// mainW.mntmCountWordFrequencies.addActionListener();
		//
		// // Tools>Test (temporary)
		//
		//
		// Compile
		//
		// // Compile>Compile dictionary
		// mainW.mntmCompileDictionary.addActionListener();
		//
		// // Compile>Compile dictionary (debug)
		// mainW.mntmCompileDictionarydebug.addActionListener();
		//
		// // Compile>Compile phoneme data 22050HZ
		// mainW.mntmCompilePhonemeData.addActionListener();
		//
		// // Compile>Compile at sample rate
		// Window for this event is ready - InterfaceCompileAtSample.java
		// mainW.mntmCompileAtSample.addActionListener();
		//
		// // Compile>Compile mbrola phonemes list...
		// mainW.mntmCompileMbrolaPhonemes.addActionListener();
		//
		// // Compile>Compile intonation data
		// mainW.mntmCompileIntonationData.addActionListener();
		//
		// // Compile>Layout '_rules' file
		// mainW.mntmLayoutrulesFile.addActionListener();
		//
		// // Compile>Sort '_rules' file
		// mainW.mntmSortrulesFile.addActionListener();
		//
		// Help
		//
		//
		// // Help>eSpeak Documentation
		mainW.mntmNewMenuItem_20.addActionListener(showDocumentation);

		// Help>About
		mainW.mntmNewMenuItem_21.addActionListener(event);
	}

	
	private static void setVisibleMenuItemsFile(MainWindow mainW) {

		if (mainW.tabbedPaneGraphs.getTabCount() != 0) {
			mainW.mntmSave.setVisible(true);
			mainW.mntmSaveAs.setVisible(true);
			mainW.mntmClose.setVisible(true);
			mainW.mntmCloseAll.setVisible(true);
		} else {
			mainW.mntmSave.setVisible(false);
			mainW.mntmSaveAs.setVisible(false);
			mainW.mntmClose.setVisible(false);
			mainW.mntmCloseAll.setVisible(false);
		}
	}
	
	//TODO Implement FocusListener for changing values in textfields
	FocusListener tfSave=new FocusListener(){
		
		//Not needed
		public void focusGained(FocusEvent e) {
		}

		public void focusLost(FocusEvent e) {
			for (int i=0;i<MainWindow.tfFreq.size();i++){
				if (e.getSource()==MainWindow.tfFreq.get(i)){
					
				}
			}
			
			for (int i=0;i<MainWindow.tfHeight.size();i++){
				if (e.getSource()==MainWindow.tfHeight.get(i)){
					
				}
			}
			
			for (int i=0;i<MainWindow.tfWidth.size();i++){
				if (e.getSource()==MainWindow.tfWidth.get(i)){
					
				}
			}
			
			for (int i=0;i<MainWindow.tfAp.size();i++){
				if (e.getSource()==MainWindow.tfAp.get(i)){
					
				}	
			}
			
			for (int i=0;i<MainWindow.tfBp.size();i++){
				if (e.getSource()==MainWindow.tfBp.get(i)){
					
				}
			}
			
			for (int i=0;i<MainWindow.tfBw.size();i++){
				if (e.getSource()==MainWindow.tfBw.get(i)){
					
				}
			}
		}
		
	};
}
