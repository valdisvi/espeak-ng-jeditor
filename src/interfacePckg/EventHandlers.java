package interfacePckg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import dataStructure.PhonemeLoad;

//FIXME try extending MainWindow class, should shorten the amount of code
public class EventHandlers {

	private MainWindow mainW;
	private JFileChooser fileChooser;

	public EventHandlers(MainWindow mainW) {
		this.mainW = mainW;
		fileChooser = new JFileChooser("./phsource/");
	}

	ChangeListener getPhoneme = new ChangeListener() {
		public void stateChanged(ChangeEvent arg0) {
			setVisibleMenuItemsFile(mainW);
			PhonemeLoad.getPhoneme((JPanel) mainW.tabbedPaneGraphs.getSelectedComponent());
		}
	};

	ActionListener event = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
			if (e.getSource() == mainW.mntmQuit) {
				mainW.setVisible(false);
				mainW.dispose();
			}

=======
>>>>>>> 3ef565f201cdad591b62de8ef1787caeee7f597e
			if (e.getSource() == mainW.mntmOpen) {
				int returnVal = fileChooser.showOpenDialog(mainW);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					PhonemeLoad.phonemeOpen(file, mainW);
				}
			} else if (e.getSource() == mainW.mntmQuit) {
				mainW.setVisible(false);
				mainW.dispose();
			} else if (e.getSource() == mainW.mntmEnglish) {
				File file = new File("./languages/english.txt");
				Language.initLanguage(file, mainW);
			} else if (e.getSource() == mainW.mntmLatvian) {
				File file = new File("./languages/latvian.txt");
				Language.initLanguage(file, mainW);
			} else if (e.getSource() == mainW.mntmRussian) {
				File file = new File("./languages/russian.txt");
				Language.initLanguage(file, mainW);
			} else if (e.getSource() == mainW.mntmSpeed) {
				mainW.optionsSpeed.showOptionsSpeed();
			} else if (e.getSource() == mainW.mntmAbout) {
				AboutWindow.OpenAboutWindow();
			}
		}
	};
	

	// TODO clear the text field and spinner values in this and in closeAllTab
	ActionListener closeTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			mainW.tabbedPaneGraphs.remove(mainW.tabbedPaneGraphs.getSelectedComponent());
		}
	};

	ActionListener closeAllTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			mainW.tabbedPaneGraphs.removeAll();
		}
	};

	ActionListener translate = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("translate");

		}
	};

	ActionListener showRules = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("showRules");
		}
	};

	//requires espeak-ng library
	ActionListener showIpa = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("showIpa");
		}
	};

	ActionListener speak = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("speak");
		}
	};

	//requires espeak-ng library
	// speaking without ignoring punctuation (says "dot" where is ".")
	ActionListener speakPunctuation = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("speakPunctuation");
		}
	};

	//requires espeak-ng library
	// splits word and spell it by symbol
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

		// File
		
		mainW.mntmOpen.addActionListener(event);
		// mainW.mntmOpen2.addActionListener();
		// mainW.mntmSave.addActionListener();
		// mainW.mntmSaveAs.addActionListener();
		mainW.mntmClose.addActionListener(closeTab);
		mainW.mntmCloseAll.addActionListener(closeAllTab);
		mainW.mntmQuit.addActionListener(event);
<<<<<<< HEAD
		// file>open

		mainW.mntmOpen.addActionListener(event);
=======
>>>>>>> 3ef565f201cdad591b62de8ef1787caeee7f597e

		// Speak
		
		mainW.mntmTranslate.addActionListener(translate);
		mainW.mntmShowRules.addActionListener(showRules);
		mainW.mntmShowIPA.addActionListener(showIpa);
		mainW.mntmSpeak.addActionListener(speak);
		// mainW.mntmSpeakfile.addActionListener();
		// mainW.mntmPause.addActionListener();
		// mainW.mntmStop.addActionListener();
		
		// Voice
		// mainW.mntmSelectVoice.addActionListener();
		// mainW.mntmSelectVoiceVariant.addActionListener();
		// mainW.rdbtnmntmEnglish.addActionListener();
		// mainW.rdbtnmntmLatvian.addActionListener();
		// mainW.rdbtnmntmPolish.addActionListener();
		// mainW.rdbtnmntmRussian.addActionListener();
		
		// Options
		
		// mainW.mntmMasterPhonemesFile.addActionListener();
		// mainW.mntmPhonemeDataSource.addActionListener();
		// mainW.mntmDictionaryDataSource.addActionListener();
		// mainW.mntmSynthesizedSoundWAVfile.addActionListener();
		// mainW.mntmVoiceFileToModifyFormantPeaks.addActionListener();
		mainW.mntmEnglish.addActionListener(event);
		mainW.mntmLatvian.addActionListener(event);
		mainW.mntmRussian.addActionListener(event);
		mainW.mntmSpeed.addActionListener(event);
		mainW.mntmSpeakPunctuation.addActionListener(speakPunctuation);
		mainW.mntmSpeakCharacters.addActionListener(speakBySymbol);
		// mainW.mntmSpeakCharacterName.addActionListener();

		// Tools

		// mainW.mntmFromCompiledPhoneme.addActionListener();
		// mainW.mntmFromDirectoryVowelFiles.addActionListener();
		// mainW.mntmPLBulgarian.addActionListener();
		// mainW.mntmPLGerman.addActionListener();
		// mainW.mntmPLItalian.addActionListener();
		// mainW.mntmPLRussian.addActionListener();
		// mainW.mntmConvertFileUTF8.addActionListener();
		// mainW.mntmCountWordFrequencies.addActionListener();
		// mainW.mntmTesttemporary.addActionListener();
		
		// Compile
		
		// mainW.mntmCompileDictionary.addActionListener();
		// mainW.mntmCompileDictionarydebug.addActionListener();
		// mainW.mntmCompilePhonemeData.addActionListener();
		// mainW.mntmCompileAtSample.addActionListener();
		// mainW.mntmCompileMbrolaPhonemes.addActionListener();
		// mainW.mntmCompileIntonationData.addActionListener();
		// mainW.mntmLayoutrulesFile.addActionListener();
		// mainW.mntmSortrulesFile.addActionListener();
		
		// Help
		
		mainW.mntmEspeakDocumentation.addActionListener(showDocumentation);
		mainW.mntmAbout.addActionListener(event);
		
		// Prosody ("Text") tab buttons
		mainW.btnTranslate.addActionListener(translate);
		mainW.btnSpeak.addActionListener(speak);
		mainW.btnShowRules.addActionListener(showRules);
		mainW.btnShowIPA.addActionListener(showIpa);
	}

	
	private static void setVisibleMenuItemsFile(MainWindow mainW) {

		boolean toSetVisible = (mainW.tabbedPaneGraphs.getTabCount() == 0) ? false : true;
		mainW.mntmSave.setVisible(toSetVisible);
		mainW.mntmSaveAs.setVisible(toSetVisible);
		mainW.mntmClose.setVisible(toSetVisible);
		mainW.mntmCloseAll.setVisible(toSetVisible);
	}
	
	//TODO Implement FocusListener for changing values in text fields
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
