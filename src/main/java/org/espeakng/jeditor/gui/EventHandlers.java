package org.espeakng.jeditor.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.espeakng.jeditor.data.Frame;
import org.espeakng.jeditor.data.Phoneme;
import org.espeakng.jeditor.data.PhonemeLoad;
import org.espeakng.jeditor.data.PhonemeSave;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

//FIXME try extending MainWindow class, should shorten the amount of code
public class EventHandlers {

	private MainWindow mainW;
	private JFileChooser fileChooser, fileChooser2;
	private Preferences prefs, prefs2;

	public EventHandlers(MainWindow mainW) {
		this.mainW = mainW;
		prefs = Preferences.userRoot().node(getClass().getName());
		fileChooser = new JFileChooser(prefs.get("a",
			    new File(".").getAbsolutePath()));
		
		prefs2 = Preferences.userRoot().node(getClass().getName());
		fileChooser2 = new JFileChooser(prefs2.get("a",
			    new File(".").getAbsolutePath()));
	}
	

	ChangeListener getPhoneme = new ChangeListener() {
		public void stateChanged(ChangeEvent arg0) {
			setVisibleMenuItemsFile(mainW);
			PhonemeLoad.getPhoneme((JScrollPane) mainW.tabbedPaneGraphs.getSelectedComponent());
		}
	};

	ActionListener event = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == mainW.mntmOpen) {
				if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					PhonemeLoad.phonemeOpen(fileChooser.getSelectedFile(), mainW);
					 prefs.put("a", fileChooser.getSelectedFile().getParent());
				}
			}else if(e.getSource() == mainW.mntmOpen2){
				if (fileChooser2.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					PhonemeLoad.phonemeOpen(fileChooser2.getSelectedFile(), mainW);
					 prefs2.put("a", fileChooser2.getSelectedFile().getParent());
				}
			}else if (e.getSource() == mainW.mntmQuit) {
				mainW.setVisible(false);
				mainW.dispose();
			} else if (e.getSource() == mainW.mntmEnglish) {
				Language.initLanguage(new File("./src/main/resources/english.txt"), mainW);
			} else if (e.getSource() == mainW.mntmLatvian) {
				Language.initLanguage(new File("./src/main/resources/latvian.txt"), mainW);
			} else if (e.getSource() == mainW.mntmRussian) {
				Language.initLanguage(new File("./src/main/resources/russian.txt"), mainW);
			} else if (e.getSource() == mainW.mntmSpeed) {
				mainW.optionsSpeed.showOptionsSpeed();
			} else if (e.getSource() == mainW.mntmAbout) {
				AboutWindow.OpenAboutWindow();
			} else if (e.getSource() == mainW.btnZoom) {
				
				PhonemeLoad.zoomOut((JScrollPane) mainW.tabbedPaneGraphs.getSelectedComponent());
			} else if (e.getSource() == mainW.btnZoom_1) {
				PhonemeLoad.zoomIn((JScrollPane) mainW.tabbedPaneGraphs.getSelectedComponent());
			}
		}
	};
public void clearText(){
	for (int i = 0; i < 7; i++) {
		MainWindow.tfFreq.get(i).setText("");
	}
	for (int i = 0; i < 8; i++) {
		MainWindow.tfHeight.get(i).setText("" );
	}
	for (int i = 0; i < 6; i++) {
		MainWindow.tfWidth.get(i).setText("");
	}
	for (int i = 0; i < 3; i++) {
		MainWindow.tfBw.get(i).setText("");
	}
	for (int i = 0; i < 6; i++) {
		MainWindow.tfAp.get(i).setText("");
	}
	for (int i = 0; i < 6; i++) {
		MainWindow.tfBp.get(i).setText("");
	}
}
	//  clear the text field and spinner values in this and in closeAllTab
	ActionListener closeTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			mainW.tabbedPaneGraphs.remove(mainW.tabbedPaneGraphs.getSelectedComponent());
			// if this is the last one, then clear text fields
			System.out.println("Component count "+mainW.tabbedPaneGraphs.getComponentCount());
			boolean lastPhoneme = true;
			// tabbedPaneGraphs contains more Components than phoneme files
			for (Component comp : mainW.tabbedPaneGraphs.getComponents()){
				if(comp.getClass().equals(JScrollPane.class)){
					lastPhoneme = false;
					break;
				}
			}
				
			if(lastPhoneme){
				clearText();
			}
		}
	};
	ActionListener saveTab = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			Phoneme temp = PhonemeLoad.getSelectedPhoneme((JScrollPane)mainW.tabbedPaneGraphs.getSelectedComponent());
			PhonemeSave.saveToDirectory(temp, new File(temp.path));
		}
	};
	ActionListener saveAsTab = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			Phoneme temp = PhonemeLoad.getSelectedPhoneme((JScrollPane)mainW.tabbedPaneGraphs.getSelectedComponent());
			if(fileChooser.showSaveDialog(mainW)==JFileChooser.APPROVE_OPTION){
				PhonemeSave.saveToCustomDirectory(temp, fileChooser.getSelectedFile().getAbsolutePath());
			}
			
		}
	};
	ActionListener closeAllTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			mainW.tabbedPaneGraphs.removeAll();
			clearText();
			mainW.mntmSave.setVisible(false);
			mainW.mntmSaveAs.setVisible(false);
			mainW.mntmClose.setVisible(false);
			mainW.mntmCloseAll.setVisible(false);
		}
	};

	
	//requires espeak-ng library
	//requires espeak-ng library
	ActionListener translate = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("translate");

		}
	};
	
	//requires espeak-ng library
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

	//requires espeak-ng library
	ActionListener speak = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("speak");
		}
	};
	

	//requires espeak-ng library
	//requires espeak-ng library
	// speaking without ignoring punctuation (says "dot" where is ".")
	ActionListener speakPunctuation = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("speakPunctuation");
		}
	};
	
	//requires espeak-ng library

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
		mainW.mntmOpen2.addActionListener(event);
		// mainW.mntmSave.addActionListener(saveTab); //TODO uncomment when PhonemeSave is fixed
		//mainW.mntmSaveAs.addActionListener(saveAsTab); //TODO uncomment when PhonemeSave is fixed or for testing
		mainW.mntmClose.addActionListener(closeTab);
		mainW.mntmCloseAll.addActionListener(closeAllTab);
		mainW.mntmQuit.addActionListener(event);


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
		
		// Zoom buttons
		mainW.btnZoom.addActionListener(event);
		mainW.btnZoom_1.addActionListener(event);
		
		// Prosody ("Text") tab buttons
		mainW.btnTranslate.addActionListener(translate);
		mainW.btnSpeak.addActionListener(speak);
		mainW.btnShowRules.addActionListener(showRules);
		mainW.btnShowIPA.addActionListener(showIpa);
		
		mainW.panel_Spect.addFocusListener(tfSave);
	}

	private static void setVisibleMenuItemsFile(MainWindow mainW) {

		boolean toSetVisible = (mainW.tabbedPaneGraphs.getTabCount() == 0) ? false : true;
		mainW.mntmSave.setVisible(toSetVisible);
		mainW.mntmSaveAs.setVisible(toSetVisible);
		mainW.mntmClose.setVisible(toSetVisible);
		mainW.mntmCloseAll.setVisible(toSetVisible);
	}
	
	//TODO Implement FocusListener for changing values in text fields
	
	FocusListener tfSave=new FocusAdapter(){
		
		
		public void focusLost(FocusEvent e) {
			Frame frame = mainW.focusedFrame;
			for (int i=0;i<MainWindow.tfFreq.size();i++){
				if (e.getSource()==MainWindow.tfFreq.get(i)){
					frame.peaks[i].pkfreq = Short.parseShort(MainWindow.tfFreq.get(i).getText().toString());
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
	public JFileChooser getFileChooser(){
		return fileChooser;
	}
}
