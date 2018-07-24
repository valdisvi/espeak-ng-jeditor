package org.espeakng.jeditor.gui;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.espeakng.jeditor.data.Phoneme;
import org.espeakng.jeditor.data.PhonemeLoad;
import org.espeakng.jeditor.data.PhonemeSave;
import org.espeakng.jeditor.data.VowelChart;
import org.espeakng.jeditor.utils.CommandUtilities;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;


/**
 * This class is for setting up event handlers
 */
public class EventHandlers {
	
	private static Logger logger = Logger.getLogger(EspeakNg.class.getName());
	private MainWindow mainW;
	private JFileChooser fileChooser;
	private Preferences prefs;
    private File file;
    // Files required for buttons. Do not delete.
    private Map<String, File> folders = new HashMap<>();
    // ******************************************
    private EspeakNg espeakNg;
    private String dataPath = new File("../espeak-ng").getAbsolutePath();
    
    
    
	/**
	 * Constructor initializes 2 fileChoosers so that they would both remember
	 * different directory
	 * 
	 * @param mainW
	 */
 
	public EventHandlers(MainWindow mainW) {
		this.mainW = mainW;
		espeakNg = new EspeakNg(mainW);
		
		setFolders();
		
		prefs = Preferences.userRoot().node(getClass().getName());
		fileChooser = new JFileChooser(prefs.get("a", new File(".").getAbsolutePath()));
	}
	
	private void setFolders() {
		folders.put("masterPhFile", new File("../espeak-ng/phsource/phonemes"));
		folders.put("phonemeSource", new File("../espeak-ng/phsource"));
		folders.put("dictSource", new File("../espeak-ng/dictsource"));
	}

	ChangeListener getPhoneme = new ChangeListener() {
		public void stateChanged(ChangeEvent arg0) {
			setVisibleMenuItemsFile(mainW);
			PhonemeLoad.getPhoneme((JScrollPane) MainWindow.tabbedPaneGraphs.getSelectedComponent());
		}
	};


	ActionListener event = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fileChooser = new JFileChooser(prefs.get("a", new File(".").getAbsolutePath()));
			if (e.getSource() == mainW.mntmOpen||e.getSource() == mainW.openMI) {
				if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					PhonemeLoad.phonemeOpen(fileChooser.getSelectedFile(), mainW);
					prefs.put("a", fileChooser.getSelectedFile().getParent());
				}
			} else if (e.getSource() == mainW.mntmOpen2) {
				fileChooser = new JFileChooser(prefs.get("a", new File(".").getAbsolutePath()));
				if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
					PhonemeLoad.phonemeOpen(fileChooser.getSelectedFile(), mainW);
					prefs.put("a", fileChooser.getSelectedFile().getParent());
				}
			} else if (e.getSource() == mainW.mntmQuit||e.getSource() == mainW.quitMI) {
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
			} else if (e.getSource() == mainW.mntmRussian) {
				File file = new File("./src/main/resources/russian.txt");
				if (!file.exists()) {
					InputStream in = getClass().getResourceAsStream("/russian.txt");
					BufferedReader input = new BufferedReader(new InputStreamReader(in));
					Language.initLanguage(input, mainW);
				} else {
					Language.initLanguage(file, mainW);
				}
      }else if (e.getSource() == mainW.mntmTamil) {
					File file = new File("./src/main/resources/tamil.txt");
					if (!file.exists()) {
						InputStream in = getClass().getResourceAsStream("/tamil.txt");
						BufferedReader input = new BufferedReader(new InputStreamReader(in));
						Language.initLanguage(input, mainW);
					} else {
						Language.initLanguage(file, mainW);
					}
			} else if (e.getSource() == mainW.mntmSpeed) {
				mainW.optionsSpeed.showOptionsSpeed();
			} else if (e.getSource() == mainW.mntmAbout) {
				new AboutWindow();
			} else if (e.getSource() == mainW.btnZoom) {
				PhonemeLoad.zoomOut((JScrollPane) MainWindow.tabbedPaneGraphs.getSelectedComponent());
			} else if (e.getSource() == mainW.btnZoom_1) {
				PhonemeLoad.zoomIn((JScrollPane) MainWindow.tabbedPaneGraphs.getSelectedComponent());
			} else if (e.getSource() == mainW.mntmExportGraph||e.getSource() == mainW.exportMI) {
				exportGraphImage();
			}
		}
	};

	/**
	 * This method clears JTextFields that represent values of peaks
	 */
	public void clearText() {
		for (int i = 0; i < MainWindow.tfFreq.size(); i++) {
			MainWindow.tfFreq.get(i).setText("");
		
		}
		for (int i = 0; i < MainWindow.tfHeight.size(); i++) {
			MainWindow.tfHeight.get(i).setText("");
		}
		for (int i = 0; i < MainWindow.tfWidth.size(); i++) {
			MainWindow.tfWidth.get(i).setText("");
		}
		for (int i = 0; i < MainWindow.tfBw.size(); i++) {
			MainWindow.tfBw.get(i).setText("");
		}
		for (int i = 0; i < MainWindow.tfAp.size(); i++) {
			MainWindow.tfAp.get(i).setText("");
		}
		for (int i = 0; i < MainWindow.tfBp.size(); i++) {
			MainWindow.tfBp.get(i).setText("");
		}
	}


	// clear the text field and spinner values in this and in closeAllTab
	ActionListener closeTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			MainWindow.tabbedPaneGraphs.remove(MainWindow.tabbedPaneGraphs.getSelectedComponent());
			// if this is the last one, then clear text fields
			System.out.println("Component count " + MainWindow.tabbedPaneGraphs.getComponentCount());
			boolean lastPhoneme = true;
			// tabbedPaneGraphs contains more Components than phoneme files
			for (Component comp : MainWindow.tabbedPaneGraphs.getComponents()) {
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
			Phoneme temp = PhonemeLoad.getSelectedPhoneme((JScrollPane) MainWindow.tabbedPaneGraphs.getSelectedComponent());
			PhonemeSave.saveToDirectory(temp, new File(temp.path));
		}
	};
	ActionListener saveAsTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Phoneme temp = PhonemeLoad.getSelectedPhoneme((JScrollPane) MainWindow.tabbedPaneGraphs.getSelectedComponent());
			if (fileChooser.showSaveDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				PhonemeSave.saveToCustomDirectory(temp, fileChooser.getSelectedFile().getAbsolutePath());
			}

		}
	};
	ActionListener closeAllTab = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			MainWindow.tabbedPaneGraphs.removeAll();
			clearText();
			mainW.mntmSave.setVisible(false);
			mainW.mntmSaveAs.setVisible(false);
			mainW.mntmClose.setVisible(false);
			mainW.mntmCloseAll.setVisible(false);
		}
	};


	private class MakeActionListener implements ActionListener {

		private String action;
		
		public MakeActionListener(String action) {
			this.action = action;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			espeakNg.makeAction(action);
		}
		
	}

	
	private boolean isPaused = false;
	private Thread lastThread;
	
	// requires espeak-ng library
	ActionListener speak = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			String voice = espeakNg.getVoiceFromSelection();
			int speedVoice = mainW.optionsSpeed.getSpinnerValue();
			String terminalCommand = "/usr/bin/espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + espeakNg.getText("speak")+ "\" |/usr/bin/aplay 2>/dev/null";
			CommandUtilities.executeCmd(terminalCommand);
			lastThread = CommandUtilities.getLastThread();
			Thread tMonitor = createMonitorThread();
			tMonitor.start();
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
				lastThread = CommandUtilities.getLastThread();
				Thread tMonitor = createMonitorThread();
				tMonitor.start();
			}
		}
	};
	
	private Thread createMonitorThread() {
		Thread tMonitor = new Thread() {
			@Override
			public void run() {
				mainW.mntmSpeak.setEnabled(false); mainW.mntmSpeakfile.setEnabled(false);
				mainW.btnSpeak.setEnabled(false); mainW.btnPause.setEnabled(true);
				mainW.btnStop.setEnabled(true); mainW.mntmPause.setEnabled(true);
				mainW.mntmStop.setEnabled(true); mainW.mntmSpeakPunctuation.setEnabled(false);
				mainW.mntmSpeakCharacters.setEnabled(false); mainW.mntmSpeakCharacterName.setEnabled(false);
				try {
					while (lastThread.isAlive()) {
						Thread.sleep(50);
					}
				} catch (InterruptedException e) {
					logger.warn(e);
				}
				mainW.mntmSpeak.setEnabled(true); mainW.mntmSpeakfile.setEnabled(true);
				mainW.btnSpeak.setEnabled(true); mainW.btnPause.setEnabled(false);
				mainW.btnStop.setEnabled(false); mainW.mntmPause.setEnabled(false);
				mainW.mntmStop.setEnabled(false); mainW.mntmSpeakPunctuation.setEnabled(true);
				mainW.mntmSpeakCharacters.setEnabled(true); mainW.mntmSpeakCharacterName.setEnabled(true);
			}
		};
		return tMonitor;
	}
	
	ActionListener pauseFile = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isPaused) {
				CommandUtilities.executeCmd("kill -STOP $(pgrep aplay)");
				mainW.mntmPause.setText("Unpause");
				mainW.btnPause.setIcon(mainW.resumeIcon);
			}
			else {
				CommandUtilities.executeCmd("kill -CONT $(pgrep aplay)");
				mainW.mntmPause.setText("Pause");
				mainW.btnPause.setIcon(mainW.pauseIcon);
			}
			isPaused = !isPaused;
		}
	};
	
	
	ActionListener stopFile = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			CommandUtilities.executeCmd("pkill -9 -f aplay");
			isPaused = false;
			mainW.mntmPause.setText("Pause");
			mainW.btnPause.setIcon(mainW.pauseIcon);
		}
	};
	
	ActionListener selectVoice = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				prefs.put("", fileChooser.getSelectedFile().getParent());
				System.out.println(fileChooser.getName(fileChooser.getSelectedFile()));
			}
		}
	};

	ActionListener selectVoiceVariant = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			fileChooser = new JFileChooser(prefs.get("", new File("../espeak-ng/espeak-ng-data/voices/v!").getAbsolutePath()));
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				prefs.put("", fileChooser.getSelectedFile().getParent());
				if(fileChooser.getName(fileChooser.getSelectedFile()).matches("[A-Za-z0-9]+"))
					espeakNg.setVoiceVariant(fileChooser.getName(fileChooser.getSelectedFile()));
			}
		}
	};
	
	ActionListener viaFileChooser = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			usingFileChooser();
		}
		
	};
	
	private File usingFileChooser(){
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fileChooser.showDialog(mainW, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			System.out.println(file.getAbsolutePath() + " " + file.isDirectory());
			return fileChooser.getSelectedFile();
		}
		return null;
	}
	
	private class FoldersListener implements ActionListener {

		private String key;
		
		public FoldersListener(String key) {
			this.key = key;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			File temp = usingFileChooser();
			if (temp != null) {
				folders.put(key, temp);
			}
		}
		
	}

	private class GetTextListener implements ActionListener {

		private String command;
		
		public GetTextListener(String command) {
			this.command = command;
		}
		
		// requires espeak-ng library
		@Override
		public void actionPerformed(ActionEvent e) {
			EspeakNg espeakNg = new EspeakNg(mainW);
			String voice = espeakNg.getVoiceFromSelection();
			int speedVoice = mainW.optionsSpeed.getSpinnerValue();

			String terminalCommand1 = "/usr/bin/espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + espeakNg.getText(command)+ "\" |/usr/bin/aplay 2>/dev/null";
			CommandUtilities.executeCmd(terminalCommand1);
			lastThread = CommandUtilities.getLastThread();
			Thread tMonitor = createMonitorThread();
			tMonitor.start();
		}
	}
	

	ActionListener countWordFreq = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String[] keys = espeakNg.getText("").toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split(" ");
			Map<String, Integer> map = new TreeMap<>();
			
			for (String key : keys) {
				map.compute(key, (k, v) -> v == null ? 1 : v+1);
			}
			
			String[] words = map.toString().split(", ");
			if (words.length > 0){
				words[0] = words[0].substring(1);
				words[words.length-1] = words[words.length-1].substring(0, words[words.length-1].length()-1);
			}
				
			new WordFrequencyWindow(words);
			
 		}
	};
  
	
	private class CompileListener implements ActionListener {

		private String compileCommand;
		
		public CompileListener(String compileCommand) {
			this.compileCommand = compileCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompileDictionary || e.getSource() == mainW.mntmCompileDictionarydebug) {
				fileChooser.setCurrentDirectory(folders.get("dictSource"));
  				if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) { 
  					String cmd = "export ESPEAK_DATA_PATH="+ dataPath +
  							"; cd " + fileChooser.getSelectedFile().getParent() +
  							" && " + dataPath + "/src/espeak-ng --" + compileCommand + "=" + 
  							fileChooser.getSelectedFile().getName().split("_")[0];
  					CommandUtilities.executeCmd(cmd);
  				}
			}
		}
	}
	
	ActionListener compilePhonemeData = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompilePhonemeData) {
				String cmd = "export ESPEAK_DATA_PATH=" + new File("../espeak-ng").getAbsolutePath()
						+ "; cd " + folders.get("phonemeSource").getParent() + " && "
						+ dataPath + "/src/espeak-ng --compile-phonemes=" + folders.get("phonemeSource").getName();
				CommandUtilities.executeCmd(cmd);
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
	
	ActionListener showDocumentation = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			File file = new File("./docs/docindex.html");
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(file.toURI());
				} catch (IOException e) {
					logger.warn(e);
				}
			}
		}
	};

	/**
	 * This method is called for to add created listeners to GUI elements
	 */
	public void initHandlers() {

		MainWindow.tabbedPaneGraphs.addChangeListener(getPhoneme);

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

		mainW.mntmTranslate.addActionListener(new MakeActionListener("translate"));
		mainW.mntmShowRules.addActionListener(new MakeActionListener("showRules"));
		mainW.mntmShowIPA.addActionListener(new MakeActionListener("showIpa"));
		mainW.mntmSpeak.addActionListener(speak);
		mainW.mntmSpeakfile.addActionListener(speakFile);
		mainW.mntmPause.addActionListener(pauseFile);
		mainW.mntmStop.addActionListener(stopFile);

		// Voice
		//mainW.mntmSelectVoice.addActionListener(selectVoice);
		mainW.mntmSelectVoiceVariant.addActionListener(selectVoiceVariant);
		// mainW.mntmSelectVoice.addActionListener();
		// mainW.rdbtnmntmEnglish.addActionListener();
		// mainW.rdbtnmntmLatvian.addActionListener();
		// mainW.rdbtnmntmPolish.addActionListener();
		// mainW.rdbtnmntmRussian.addActionListener();

		// Options

		mainW.mntmMasterPhonemesFile.addActionListener(new FoldersListener("masterPhFile"));
		mainW.mntmPhonemeDataSource.addActionListener(new FoldersListener("phonemeSource"));
		mainW.mntmDictionaryDataSource.addActionListener(new FoldersListener("dictSource"));
		mainW.mntmSynthesizedSoundWAVfile.addActionListener(new FoldersListener("WAVFile"));
		mainW.mntmVoiceFileToModifyFormantPeaks.addActionListener(new FoldersListener("voiceFile"));
		mainW.mntmEnglish.addActionListener(event);
		mainW.mntmLatvian.addActionListener(event);
		mainW.mntmRussian.addActionListener(event);
		mainW.mntmTamil.addActionListener(event);
		mainW.mntmSpeed.addActionListener(event);
		mainW.mntmSpeakPunctuation.addActionListener(new GetTextListener("speakPunc"));
		mainW.mntmSpeakCharacters.addActionListener(new GetTextListener("speakBySymbol"));
		mainW.mntmSpeakCharacterName.addActionListener(new GetTextListener("speakCharName"));
        mainW.openMI.addActionListener(event);
        mainW.exportMI.addActionListener(event);
        mainW.clMI.addActionListener(event);
        mainW.quitMI.addActionListener(event);

		// Tools

		mainW.mntmFromDirectoryVowelFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fileChooser= new JFileChooser("../espeak-ng/phsource/vowelcharts/");
				if (e.getSource() == mainW.mntmFromDirectoryVowelFiles) {
					if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
						String cmd = "export ESPEAK_DATA_PATH=" + dataPath +
								"; cd " + fileChooser.getSelectedFile().getParent() +
								" && " + dataPath + "/src/espeak-ng --compile-mbrola=" +  fileChooser.getSelectedFile().getName();
						CommandUtilities.executeCmd(cmd);
						VowelChart.createAndShowGui(fileChooser.getSelectedFile().getPath(), mainW);
						
					}
				}

			}
		});
		mainW.mntmFromCompiledPhoneme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String lang = espeakNg.getVoiceFromSelection();
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
		mainW.mntmCountWordFrequencies.addActionListener(countWordFreq);

		// Compile

		mainW.mntmCompileDictionary.addActionListener(new CompileListener("compile"));
		mainW.mntmCompileDictionarydebug.addActionListener(new CompileListener("compile-debug"));
		mainW.mntmCompilePhonemeData.addActionListener(compilePhonemeData);
		mainW.mntmCompileMbrolaPhonemes.addActionListener(compileMbrolaPhonemes);
		mainW.mntmCompileIntonationData.addActionListener(compileIntonationData);

		// Help

		mainW.mntmEspeakDocumentation.addActionListener(showDocumentation);
		mainW.mntmAbout.addActionListener(event);

		// Zoom buttons
		mainW.btnZoom.addActionListener(event);
		mainW.btnZoom_1.addActionListener(event);

		// Prosody ("Text") tab buttons
		mainW.btnTranslate.addActionListener(new MakeActionListener("translate"));
		mainW.btnShowRules.addActionListener(new MakeActionListener("showRules"));
		mainW.btnShowIPA.addActionListener(new MakeActionListener("showIpa"));
		mainW.btnSpeak.addActionListener(speak);
		mainW.btnPause.addActionListener(pauseFile);
		mainW.btnStop.addActionListener(stopFile);

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

		boolean toSetVisible = (MainWindow.tabbedPaneGraphs.getTabCount() == 0) ? false : true;
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
		for(int i=0; i<MainWindow.tfFreq.size();i++){
			final int index =i;
			MainWindow.tfFreq.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						mainW.focusedFrame.peaks[index].klt_bp = Short.parseShort(MainWindow.tfBp.get(index).getText().toString());
						mainW.focusedPanel.repaint();
					} catch (NumberFormatException ex) {
					}
				}
			});
		}
		// Height text fields
		for(int i=0; i<MainWindow.tfHeight.size();i++){
			final int index =i;
			MainWindow.tfHeight.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						short value = Short.parseShort(MainWindow.tfHeight.get(index).getText().toString());
						value = (short) (value << 6);
						mainW.focusedFrame.peaks[index].pkheight = value;
						mainW.focusedPanel.repaint();
					} catch (NumberFormatException ex) {
					}
				}
			});
		}
		// width text fields (also changes peaks.pkright behind the scenes)
		
		for(int i=0; i<MainWindow.tfWidth.size();i++){
			final int index =i;
			MainWindow.tfWidth.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						String[] text = MainWindow.tfWidth.get(index).getText().toString().split("/");
						if (text.length == 1) {
							short value = Short.parseShort(text[0]);
							mainW.focusedFrame.peaks[index].pkwidth = (short) (value * 2);
							mainW.focusedFrame.peaks[index].pkright = (short) (value * 2);
						} else if (text.length == 2) {
							short value = Short.parseShort(text[0]);
							mainW.focusedFrame.peaks[index].pkright = (short) (value * 2);
							value = Short.parseShort(text[1]);
							mainW.focusedFrame.peaks[index].pkwidth = (short) (value * 2);
						}
						mainW.focusedPanel.repaint();
					} catch (NumberFormatException ex) {
					}
				}
			});
		}

		// klt_bw text fields
		for(int i=0; i<MainWindow.tfBw.size();i++){
			final int index =i;
			MainWindow.tfBw.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						mainW.focusedFrame.peaks[index+1].klt_ap = Short.parseShort(MainWindow.tfAp.get(index).getText().toString());
						mainW.focusedPanel.repaint();
					} catch (NumberFormatException ex) {
					}
				}
			});
		}
	

		// klt_ap text fields

		for(int i=0; i<MainWindow.tfAp.size();i++){
			final int index =i;
			MainWindow.tfAp.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						mainW.focusedFrame.peaks[index].klt_ap = Short.parseShort(MainWindow.tfAp.get(index).getText().toString());
						mainW.focusedPanel.repaint();
					} catch (NumberFormatException ex) {
					}
				}
			});
		}
	
		// klt_bp text fields
		
		for(int i=0; i<MainWindow.tfBp.size();i++){
			final int index =i;
			MainWindow.tfBp.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						mainW.focusedFrame.peaks[index+1].klt_bp = Short.parseShort(MainWindow.tfBp.get(index).getText().toString());
						mainW.focusedPanel.repaint();
					} catch (NumberFormatException ex) {
					}
				}
			});
		}
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
		// MainWindow.tabbedPaneGraphs.setSize
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
			logger.warn(e);
		}
	}
}
