package org.espeakng.jeditor.gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.espeakng.jeditor.data.Phoneme;
import org.espeakng.jeditor.data.PhonemeLoad;
import org.espeakng.jeditor.data.PhonemeSave;
import org.espeakng.jeditor.data.VowelChart;
import org.espeakng.jeditor.utils.CommandUtilities;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * This class is for setting up event handlers
 */
public class EventHandlers {

	private MainWindow mainW;
	private JFileChooser fileChooser, fileChooser2, fileChooser3, fileChooser4, fileChooser5;
	private Preferences prefs, prefs2, prefs3, prefs4, prefs5;
    private File file1, file2, file3, file4, file5, voiceFile;
    private String dataPath = new File("../espeak-ng").getAbsolutePath();
	/**
	 * Constructor initializes 2 fileChoosers so that they would both remember
	 * different directory
	 * 
	 * @param mainW
	 */
	public EventHandlers(MainWindow mainW) {
		this.mainW = mainW;
		prefs = Preferences.userRoot().node(getClass().getName());
		fileChooser = new JFileChooser(prefs.get("a", new File(".").getAbsolutePath()));

		prefs2 = Preferences.userRoot().node(getClass().getName());
		fileChooser2 = new JFileChooser(prefs2.get("a", new File(".").getAbsolutePath()));

		prefs3 = Preferences.userRoot().node(getClass().getName());
		fileChooser3 = new JFileChooser(
				prefs3.get("a", new File("../espeak-ng/phonemes/vowelcharts").getAbsolutePath()));
		
		prefs4 = Preferences.userRoot().node(getClass().getName());
        fileChooser4 = new JFileChooser(
                prefs4.get("", new File("../espeak-ng/espeak-ng-data/lang").getAbsolutePath()));
       
        prefs5 = Preferences.userRoot().node(getClass().getName());
        fileChooser5 = new JFileChooser(
                prefs5.get("", new File("../espeak-ng/espeak-ng-data/voices").getAbsolutePath()));
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
			} else if (e.getSource() == mainW.mntmOpen2) {
				if (fileChooser2.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					PhonemeLoad.phonemeOpen(fileChooser2.getSelectedFile(), mainW);
					prefs2.put("a", fileChooser2.getSelectedFile().getParent());
				}
			} else if (e.getSource() == mainW.mntmQuit) {
				mainW.setVisible(false);
				mainW.dispose();
			} else if (e.getSource() == mainW.mntmEnglish) {
				File file = new File("./src/main/resources/english.txt");
				if (!file.exists()) {
					InputStream in = getClass().getResourceAsStream("/english.txt");
					BufferedReader input = new BufferedReader(new InputStreamReader(in));
					Language.initLanguage(input, mainW);
				} else {
					Language.initLanguage(file, mainW);
				}
			} else if (e.getSource() == mainW.mntmLatvian) {
				File file = new File("./src/main/resources/latvian.txt");
				if (!file.exists()) {
					InputStream in = getClass().getResourceAsStream("/latvian.txt");
					BufferedReader input = new BufferedReader(new InputStreamReader(in));
					Language.initLanguage(input, mainW);
				} else {
					Language.initLanguage(file, mainW);
				}
				// Language.initLanguage(new
				// File("./src/main/resources/latvian.txt"), mainW);
			} else if (e.getSource() == mainW.mntmRussian) {
				File file = new File("./src/main/resources/russian.txt");
				if (!file.exists()) {
					InputStream in = getClass().getResourceAsStream("/russian.txt");
					BufferedReader input = new BufferedReader(new InputStreamReader(in));
					Language.initLanguage(input, mainW);
				} else {
					Language.initLanguage(file, mainW);
				}
				// Language.initLanguage(new
				// File("./src/main/resources/russian.txt"), mainW);
			} else if (e.getSource() == mainW.mntmSpeed) {
				mainW.optionsSpeed.showOptionsSpeed();
			} else if (e.getSource() == mainW.mntmAbout) {
				new AboutWindow();
			} else if (e.getSource() == mainW.btnZoom) {

				PhonemeLoad.zoomOut((JScrollPane) mainW.tabbedPaneGraphs.getSelectedComponent());
			} else if (e.getSource() == mainW.btnZoom_1) {
				PhonemeLoad.zoomIn((JScrollPane) mainW.tabbedPaneGraphs.getSelectedComponent());
			} else if (e.getSource() == mainW.mntmExportGraph) {
				exportGraphImage();
			}
		}
	};

	/**
	 * This method clears JTextFields that represent values of peaks
	 */
	public void clearText() {
		for (int i = 0; i < 7; i++) {
			MainWindow.tfFreq.get(i).setText("");
		}
		for (int i = 0; i < 8; i++) {
			MainWindow.tfHeight.get(i).setText("");
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

	// clear the text field and spinner values in this and in closeAllTab
	ActionListener closeTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			mainW.tabbedPaneGraphs.remove(mainW.tabbedPaneGraphs.getSelectedComponent());
			// if this is the last one, then clear text fields
			System.out.println("Component count " + mainW.tabbedPaneGraphs.getComponentCount());
			boolean lastPhoneme = true;
			// tabbedPaneGraphs contains more Components than phoneme files
			for (Component comp : mainW.tabbedPaneGraphs.getComponents()) {
				if (comp.getClass().equals(JScrollPane.class)) {
					lastPhoneme = false;
					break;
				}
			}

			if (lastPhoneme) {
				clearText();
			}
		}
	};
	ActionListener saveTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Phoneme temp = PhonemeLoad.getSelectedPhoneme((JScrollPane) mainW.tabbedPaneGraphs.getSelectedComponent());
			PhonemeSave.saveToDirectory(temp, new File(temp.path));
		}
	};
	ActionListener saveAsTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Phoneme temp = PhonemeLoad.getSelectedPhoneme((JScrollPane) mainW.tabbedPaneGraphs.getSelectedComponent());
			if (fileChooser.showSaveDialog(mainW) == JFileChooser.APPROVE_OPTION) {
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

	// requires espeak-ng library
	ActionListener translate = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("translate");

		}
	};
	

	// requires espeak-ng library
	ActionListener showRules = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("showRules");
		}
	};

	// requires espeak-ng library
	ActionListener showIpa = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			espeakNg.makeAction("showIpa");
		}
	};

	// requires espeak-ng library
	ActionListener speak = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			String voice = espeakNg.getVoiceFromSelection();
			int speedVoice = mainW.optionsSpeed.getSpinnerValue();
			String terminalCommand = "/usr/bin/espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + espeakNg.getText("speak")+ "\" |/usr/bin/aplay 2>/dev/null";
			
			CommandUtilities.executeCmd(terminalCommand);
			//espeakNg.makeAction("speak");
		}
	};

	ActionListener speakFile = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			String voice = espeakNg.getVoiceFromSelection();
			int speedVoice = mainW.optionsSpeed.getSpinnerValue();
			if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				String terminalCommand = "/usr/bin/espeak-ng -v" + voice + " -s" + speedVoice + " -f " + selectedFile.getAbsolutePath() + " --stdout |/usr/bin/aplay 2>/dev/null";
				CommandUtilities.executeCmd(terminalCommand);
			}
		}
	};
	
	ActionListener stopFile = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			CommandUtilities.executeCmd("pkill -9 -f aplay");
		}
		
	};
	
	ActionListener selectVoice = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			// EspeakNg espeakNg = new EspeakNg(mainW);
			// String file8 = "en";
			if (fileChooser4.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				prefs4.put("", fileChooser4.getSelectedFile().getParent());
				// espeakNg.setVoice(fileChooser4.getName(fileChooser4.getSelectedFile()))
				// ;
				System.out.println(fileChooser4.getName(fileChooser4.getSelectedFile()));
			}
		}
	};

	ActionListener selectVoiceVariant = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			
			 EspeakNg espeakNg = new EspeakNg(mainW);
			// String file8 = "en";
			
			JFileChooser chooser = new JFileChooser(voiceFile);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fileChooser5.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				prefs5.put("", fileChooser5.getSelectedFile().getParent());
				if(fileChooser5.getName(fileChooser5.getSelectedFile()).matches("[A-Za-z0-9]+"))
					espeakNg.setVoiceVariant(fileChooser5.getName(fileChooser5.getSelectedFile()));
				
			}
		}
	};

	ActionListener masterPhonemesFile = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			JFileChooser chooser = new JFileChooser(file1);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showDialog(mainW, "Select");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file1 = chooser.getSelectedFile();
				System.out.println(file1.getAbsolutePath() + " " + file1.isDirectory());
			}
		}
	};

	ActionListener phonemeDataSource = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			JFileChooser chooser = new JFileChooser(file2);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showDialog(mainW, "Select");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file2 = chooser.getSelectedFile();
				System.out.println(file2);
				// .getAbsolutePath() + " " + file2.isDirectory());
			}
		}
	};

	ActionListener dictionaryDataSource = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			JFileChooser chooser = new JFileChooser(file3);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showDialog(mainW, "Select");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file3 = chooser.getSelectedFile();
				System.out.println(file3.getAbsolutePath() + " " + file3.isDirectory());

			}
		}
	};

	ActionListener synthesizedSoundWAVFile = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			JFileChooser chooser = new JFileChooser(file4);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showDialog(mainW, "Select");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file4 = chooser.getSelectedFile();
				System.out.println(file4.getAbsolutePath() + " " + file4.isDirectory());
			}
		}

	};

	ActionListener voiceFileToModifyFormantPeaks = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			JFileChooser chooser = new JFileChooser(file5);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = chooser.showDialog(mainW, "Select");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file5 = chooser.getSelectedFile();
				System.out.println(file5.getAbsolutePath() + " " + file5.isDirectory());
			}
		}
	};

	// requires espeak-ng library
	// speaking without ignoring punctuation (says "dot" where is ".")
	ActionListener speakPunc = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			String voice = espeakNg.getVoiceFromSelection();
			int speedVoice = mainW.optionsSpeed.getSpinnerValue();
			String terminalCommand1 = "/usr/bin/espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + espeakNg.getText("speakPunc")+ "\" |/usr/bin/aplay 2>/dev/null";
			CommandUtilities.executeCmd(terminalCommand1);
			//espeakNg.makeAction("speakPunc");
		}
	};

	// requires espeak-ng library
	// splits word and spell it by symbol
	ActionListener speakBySymbol = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			String voice = espeakNg.getVoiceFromSelection();
			int speedVoice = mainW.optionsSpeed.getSpinnerValue();
			String terminalCommand1 = "/usr/bin/espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + espeakNg.getText("speakBySymbol")+ "\" |/usr/bin/aplay 2>/dev/null";
			CommandUtilities.executeCmd(terminalCommand1);
		}
	};
	
	ActionListener speakCharName = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			String voice = espeakNg.getVoiceFromSelection();
			int speedVoice = mainW.optionsSpeed.getSpinnerValue();
			String terminalCommand1 = "/usr/bin/espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + espeakNg.getText("speakCharName")+ "\" |/usr/bin/aplay 2>/dev/null";
			CommandUtilities.executeCmd(terminalCommand1);
			//espeakNg.makeAction("SpeakCharName");
		}
	};

	ActionListener compileDictionary = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompileDictionary) {
				fileChooser.setCurrentDirectory(new File(dataPath + "/dictsource/"));
				if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					String cmd = "export ESPEAK_DATA_PATH="+ dataPath +
							"; cd " + fileChooser.getSelectedFile().getParent() +
							" && " + dataPath + "/src/espeak-ng --compile=" + 
							fileChooser.getSelectedFile().getName().split("_")[0];
					CommandUtilities.executeCmd(cmd);
				}
			}
		}
	};

	ActionListener compileDictionaryDebug = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompileDictionarydebug) {
				fileChooser.setCurrentDirectory(new File(dataPath + "/dictsource/"));
				if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					String cmd = "export ESPEAK_DATA_PATH="+ dataPath +
							"; cd " + fileChooser.getSelectedFile().getParent() +
							" && " + dataPath + "/src/espeak-ng --compile-debug=" + 
							fileChooser.getSelectedFile().getName().split("_")[0];
					CommandUtilities.executeCmd(cmd);
				}
			}
		}
	};

	ActionListener compilePhonemeData = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompilePhonemeData) {
				fileChooser.setSelectedFile(new File(dataPath + "/phsource/phonemes"));
				if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					String cmd = "export ESPEAK_DATA_PATH=" + dataPath +
							"; cd " + fileChooser.getSelectedFile().getParentFile().getParent() +
							" && " + dataPath + "/src/espeak-ng --compile-phonemes=" +
							fileChooser.getSelectedFile().getParentFile().getName();
					CommandUtilities.executeCmd(cmd);
				}
			}
		}
	};
	
	ActionListener compileMbrolaPhonemes = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompileMbrolaPhonemes) {
				fileChooser.setCurrentDirectory(new File(dataPath + "/phsource/mbrola/"));
				if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					String cmd = "export ESPEAK_DATA_PATH=" + dataPath +
							"; cd " + fileChooser.getSelectedFile().getParent() +
							" && " + dataPath + "/src/espeak-ng --compile-mbrola=" +  fileChooser.getSelectedFile().getName();
					CommandUtilities.executeCmd(cmd);
				}
			}
		}
	};
	
	ActionListener compileIntonationData = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompileIntonationData) {
				String cmd = "export ESPEAK_DATA_PATH=" + dataPath
						+ "; " + dataPath + "/src/espeak-ng --compile-intonations";
				CommandUtilities.executeCmd(cmd);
			}
		}
	};
	
	// Any other way of calling browser without relying on one concrete?
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

	/**
	 * This method is called for to add created listeners to GUI elements
	 */
	public void initHandlers() {

		mainW.tabbedPaneGraphs.addChangeListener(getPhoneme);

		// File

		mainW.mntmOpen.addActionListener(event);
		mainW.mntmOpen2.addActionListener(event);
		mainW.mntmExportGraph.addActionListener(event);
		mainW.mntmSave.addActionListener(saveTab);
		mainW.mntmSaveAs.addActionListener(saveAsTab);
		mainW.mntmClose.addActionListener(closeTab);
		mainW.mntmCloseAll.addActionListener(closeAllTab);
		mainW.mntmQuit.addActionListener(event);
		mainW.mntmExportGraph.addActionListener(event);

		// Speak

		mainW.mntmTranslate.addActionListener(translate);
		mainW.mntmShowRules.addActionListener(showRules);
		mainW.mntmShowIPA.addActionListener(showIpa);
		mainW.mntmSpeak.addActionListener(speak);
		mainW.mntmSpeakfile.addActionListener(speakFile);
//  mainW.mntmPause.addActionListener();
		mainW.mntmStop.addActionListener(stopFile);

		// Voice
		//mainW.mntmSelectVoice.addActionListener(selectVoice);
		mainW.mntmSelectVoiceVariant.addActionListener(selectVoiceVariant);
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
		mainW.mntmMasterPhonemesFile.addActionListener(masterPhonemesFile);
		mainW.mntmPhonemeDataSource.addActionListener(phonemeDataSource);
		mainW.mntmDictionaryDataSource.addActionListener(dictionaryDataSource);
		mainW.mntmSynthesizedSoundWAVfile.addActionListener(synthesizedSoundWAVFile);
		mainW.mntmVoiceFileToModifyFormantPeaks.addActionListener(voiceFileToModifyFormantPeaks);
		mainW.mntmEnglish.addActionListener(event);
		mainW.mntmLatvian.addActionListener(event);
		mainW.mntmRussian.addActionListener(event);
		mainW.mntmSpeed.addActionListener(event);
		mainW.mntmSpeakPunctuation.addActionListener(speakPunc);
		mainW.mntmSpeakCharacters.addActionListener(speakBySymbol);
		mainW.mntmSpeakCharacterName.addActionListener(speakCharName);

		// Tools

		mainW.mntmFromDirectoryVowelFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser3.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					prefs3.put("a", fileChooser3.getSelectedFile().getParent());
					VowelChart.vowelOpen(fileChooser3.getSelectedFile(), mainW);
				}
			}
		});

		mainW.mntmFromCompiledPhoneme.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EspeakNg ng = new EspeakNg(mainW);
				String lang = ng.getVoiceFromSelection();
				String path = "";
				switch (lang) {
				case "en":
					path = "../espeak-ng/phsource/vowelcharts/en";
					break;
				case "ru":
					path = "../espeak-ng/phsource/vowelcharts/ru";
					break;
				case "lv":
					path = "../espeak-ng/phsource/vowelcharts/lv";
					break;
				case "pl":
					path = "../espeak-ng/phsource/vowelcharts/pl";
					break;
				}
				VowelChart.createAndShowGui(path, mainW);
			}
		});

		// mainW.mntmPLBulgarian.addActionListener();
		// mainW.mntmPLGerman.addActionListener();
		// mainW.mntmPLItalian.addActionListener();
		// mainW.mntmPLRussian.addActionListener();
		// mainW.mntmConvertFileUTF8.addActionListener();
		// mainW.mntmCountWordFrequencies.addActionListener();
		// mainW.mntmTesttemporary.addActionListener();

		// Compile

		mainW.mntmCompileDictionary.addActionListener(compileDictionary);
		mainW.mntmCompileDictionarydebug.addActionListener(compileDictionaryDebug);
		mainW.mntmCompilePhonemeData.addActionListener(compilePhonemeData);
		mainW.mntmCompileMbrolaPhonemes.addActionListener(compileMbrolaPhonemes);
		mainW.mntmCompileIntonationData.addActionListener(compileIntonationData);
		//mainW.mntmLayoutrulesFile.addActionListener(layoutRulesFile);
		//mainW.mntmSortrulesFile.addActionListener(sortRulesFile);

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

		addTFListeners();
	}

	/**
	 * Method that is called to set visible following menu items: "Save, Save
	 * As, Close. Close All"
	 * 
	 * @param mainW
	 *            mainW (main window) was used in early stages of code, when
	 *            singleton pattern was not implemented, so it is passed as
	 *            parameter in many places.
	 */

	private static void setVisibleMenuItemsFile(MainWindow mainW) {

		boolean toSetVisible = (mainW.tabbedPaneGraphs.getTabCount() == 0) ? false : true;
		mainW.mntmSave.setVisible(toSetVisible);
		mainW.mntmSaveAs.setVisible(toSetVisible);
		mainW.mntmClose.setVisible(toSetVisible);
		mainW.mntmCloseAll.setVisible(toSetVisible);
	}

	/**
	 * This method should be (and is) called from initHandlers(), to add
	 * listeners to JTextFields that represent values of peaks
	 * 
	 * Could not do it in a for loop, that is why it is so long
	 */

	public void addTFListeners() {
		// These listeners works on ENTER button

		// Frequency text fields
		MainWindow.tfFreq.get(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[0].pkfreq = Short
							.parseShort(MainWindow.tfFreq.get(0).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfFreq.get(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainW.focusedFrame.peaks[1].pkfreq = Short
							.parseShort(MainWindow.tfFreq.get(1).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}

			}
		});
		MainWindow.tfFreq.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainW.focusedFrame.peaks[2].pkfreq = Short
							.parseShort(MainWindow.tfFreq.get(2).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}

			}
		});
		MainWindow.tfFreq.get(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainW.focusedFrame.peaks[3].pkfreq = Short
							.parseShort(MainWindow.tfFreq.get(3).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfFreq.get(4).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainW.focusedFrame.peaks[4].pkfreq = Short
							.parseShort(MainWindow.tfFreq.get(4).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfFreq.get(5).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainW.focusedFrame.peaks[5].pkfreq = Short
							.parseShort(MainWindow.tfFreq.get(5).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfFreq.get(6).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainW.focusedFrame.peaks[6].pkfreq = Short
							.parseShort(MainWindow.tfFreq.get(6).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});

		// Height text fields

		MainWindow.tfHeight.get(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(0).getText().toString());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[0].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfHeight.get(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(1).getText().toString());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[1].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfHeight.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(2).getText().toString());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[2].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfHeight.get(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(3).getText().toString());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[3].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfHeight.get(4).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(4).getText().toString());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[4].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfHeight.get(5).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(5).getText().toString());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[5].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfHeight.get(6).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(6).getText().toString());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[6].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfHeight.get(7).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(7).getText().toString());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[7].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		// width text fields (also changes peaks.pkright behind the scenes)

		MainWindow.tfWidth.get(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String[] text = MainWindow.tfWidth.get(0).getText().toString().split("/");
					if (text.length == 1) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[0].pkwidth = (short) (value * 2);
						mainW.focusedFrame.peaks[0].pkright = (short) (value * 2);
					} else if (text.length == 2) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[0].pkright = (short) (value * 2);
						value = Short.parseShort(text[1]);
						mainW.focusedFrame.peaks[0].pkwidth = (short) (value * 2);
					}
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfWidth.get(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String[] text = MainWindow.tfWidth.get(1).getText().toString().split("/");
					if (text.length == 1) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[1].pkwidth = (short) (value * 2);
						mainW.focusedFrame.peaks[1].pkright = (short) (value * 2);
					} else if (text.length == 2) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[1].pkright = (short) (value * 2);
						value = Short.parseShort(text[1]);
						mainW.focusedFrame.peaks[1].pkwidth = (short) (value * 2);
					}
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfWidth.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String[] text = MainWindow.tfWidth.get(2).getText().toString().split("/");
					if (text.length == 1) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[2].pkwidth = (short) (value * 2);
						mainW.focusedFrame.peaks[2].pkright = (short) (value * 2);
					} else if (text.length == 2) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[2].pkright = (short) (value * 2);
						value = Short.parseShort(text[1]);
						mainW.focusedFrame.peaks[2].pkwidth = (short) (value * 2);
					}
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfWidth.get(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String[] text = MainWindow.tfWidth.get(3).getText().toString().split("/");
					if (text.length == 1) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[3].pkwidth = (short) (value * 2);
						mainW.focusedFrame.peaks[3].pkright = (short) (value * 2);
					} else if (text.length == 2) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[3].pkright = (short) (value * 2);
						value = Short.parseShort(text[1]);
						mainW.focusedFrame.peaks[3].pkwidth = (short) (value * 2);
					}
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfWidth.get(4).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String[] text = MainWindow.tfWidth.get(4).getText().toString().split("/");
					if (text.length == 1) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[4].pkwidth = (short) (value * 2);
						mainW.focusedFrame.peaks[4].pkright = (short) (value * 2);
					} else if (text.length == 2) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[4].pkright = (short) (value * 2);
						value = Short.parseShort(text[1]);
						mainW.focusedFrame.peaks[4].pkwidth = (short) (value * 2);
					}
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfWidth.get(5).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String[] text = MainWindow.tfWidth.get(5).getText().toString().split("/");
					if (text.length == 1) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[5].pkwidth = (short) (value * 2);
						mainW.focusedFrame.peaks[5].pkright = (short) (value * 2);
					} else if (text.length == 2) {
						short value = Short.parseShort(text[0]);
						mainW.focusedFrame.peaks[5].pkright = (short) (value * 2);
						value = Short.parseShort(text[1]);
						mainW.focusedFrame.peaks[5].pkwidth = (short) (value * 2);
					}
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});

		// klt_bw text fields

		MainWindow.tfBw.get(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[1].klt_bw = Short.parseShort(MainWindow.tfBw.get(0).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfBw.get(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[2].klt_bw = Short.parseShort(MainWindow.tfBw.get(1).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});

		MainWindow.tfBw.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[3].klt_bw = Short.parseShort(MainWindow.tfBw.get(2).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});

		// klt_ap text fields

		MainWindow.tfAp.get(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[0].klt_ap = Short.parseShort(MainWindow.tfAp.get(0).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfAp.get(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[1].klt_ap = Short.parseShort(MainWindow.tfAp.get(1).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfAp.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[2].klt_ap = Short.parseShort(MainWindow.tfAp.get(2).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfAp.get(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[3].klt_ap = Short.parseShort(MainWindow.tfAp.get(3).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfAp.get(4).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[4].klt_ap = Short.parseShort(MainWindow.tfAp.get(4).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfAp.get(5).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[5].klt_ap = Short.parseShort(MainWindow.tfAp.get(5).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});

		// klt_bp text fields

		MainWindow.tfBp.get(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[1].klt_bp = Short.parseShort(MainWindow.tfBp.get(0).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfBp.get(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[2].klt_bp = Short.parseShort(MainWindow.tfBp.get(1).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfBp.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[3].klt_bp = Short.parseShort(MainWindow.tfBp.get(2).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfBp.get(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[4].klt_bp = Short.parseShort(MainWindow.tfBp.get(3).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfBp.get(4).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[5].klt_bp = Short.parseShort(MainWindow.tfBp.get(4).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});
		MainWindow.tfBp.get(5).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					mainW.focusedFrame.peaks[6].klt_bp = Short.parseShort(MainWindow.tfBp.get(5).getText().toString());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
				}
			}
		});

	}

	/**
	 * Get method for file chooser
	 * 
	 * @return file chooser
	 */
	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	private void exportGraphImage() {
		// mainW.tabbedPaneGraphs.setSize
		// setSize(getPreferredSize());
		BufferedImage image = new BufferedImage(MainWindow.tabbedPaneGraphs.getWidth(),
				MainWindow.tabbedPaneGraphs.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		MainWindow.tabbedPaneGraphs.printAll(g);
		g.dispose();
		try {
			File file = new File("graph.png");
			System.out.println("Exported graphs: " + file.getAbsolutePath());
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
