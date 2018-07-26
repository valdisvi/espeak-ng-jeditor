package org.espeakng.jeditor.gui;

import javax.swing.*;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.espeakng.jeditor.data.Command;
import org.espeakng.jeditor.data.Phoneme;
import org.espeakng.jeditor.data.PhonemeLoad;
import org.espeakng.jeditor.data.PhonemeSave;
import org.espeakng.jeditor.data.VowelChart;

import org.espeakng.jeditor.data.ProsodyPanel;
import org.espeakng.jeditor.data.ProsodyPhoneme;
import org.espeakng.jeditor.utils.CommandUtilities;

import org.espeakng.jeditor.utils.Utilities;
import org.espeakng.jeditor.utils.WrapLayout;



/**
 * This class is for setting up event handlers
 */
public class EventHandlers {
	
	private static Logger logger = Logger.getLogger(EspeakNg.class.getName());
    // Files required for buttons. Do not delete.
    private static Map<Command, File> folders = new EnumMap<>(Command.class);
    // ******************************************
    private EspeakNg espeakNg;
	private MainWindow mainW;
	private JFileChooser fileChooser;
	private Preferences prefs;
    private File file;
    private String dataPath = new File("../espeak-ng").getAbsolutePath();
    private JScrollPane scrollPane;
    
    // Need a getter for tests
    public static Map<Command, File> getFolders() {
    	return folders;
    }
    
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


	ChangeListener getPhoneme = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			setVisibleMenuItemsFile(mainW);
			PhonemeLoad.getPhoneme((JScrollPane) mainW.tabbedPaneGraphs.getSelectedComponent());
			JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
			int index = sourceTabbedPane.getSelectedIndex();
			// FIX ME: changing tabs graphs are shown in incorrect order... Graph sequence changes after each click on tab. 
			if(index < MainWindow.getMainWindow().spectrumGraphList.size()){
				  MainWindow.getMainWindow().panel_Spect.add(MainWindow.getMainWindow().spectrumGraphList.get(index)).repaint();
			
			}
			
		}	

	};



	private void setFolders() {
		folders.put(Command.PH_FILE, new File("../espeak-ng/phsource/phonemes"));
		folders.put(Command.PHONEME_SOURCE, new File("../espeak-ng/phsource"));
		folders.put(Command.DICT_SOURCE, new File("../espeak-ng/dictsource"));
	}
	
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
				CommandUtilities.executeCmd("pkill -9 -f aplay");
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
			} else if (e.getSource() == mainW.mntmTamil) {
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
			} else if (e.getSource() == mainW.panel_Spect){
				mainW.panel_Spect.repaint();
			}
		}
		
		private void exportGraphImage() {
			fileChooser.setCurrentDirectory(new File("./"));
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				BufferedImage image = new BufferedImage(MainWindow.tabbedPaneGraphs.getWidth(),
						MainWindow.tabbedPaneGraphs.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				MainWindow.tabbedPaneGraphs.printAll(g);
				g.dispose();
				try {
					
					File file = fileChooser.getSelectedFile();
			        if (!file.getName().endsWith(".png")) {
			            file = new File(file.getParentFile(), file.getName() + ".png");
			        }
			        
			        int confirm;
			        if (file.exists()) {
			            confirm = JOptionPane.showConfirmDialog(
			                            null, "File already exists, overwrite?", "Overwrite?", JOptionPane.YES_NO_OPTION);
			            if (confirm == JOptionPane.NO_OPTION) {
			                return;
			            }
			        }
					logger.debug("Exported graphs: " + file.getAbsolutePath());
					ImageIO.write(image, "png", file);
				} catch (IOException e) {
					logger.warn(e);
				}
			}
		}
	};

	/**
	 * This method clears JTextFields that represent values of peaks
	 */
	public void clearText() {
		for(List<JTextField> jTextArray : MainWindow.array ){
			for(JTextField jTextField : jTextArray){
				jTextField.setText("");
			}
		}
	}


	// clear the text field and spinner values in this and in closeAllTab
	ActionListener closeTab = (ActionEvent arg0) -> {
		MainWindow.tabbedPaneGraphs.remove(MainWindow.tabbedPaneGraphs.getSelectedComponent());
		// if this is the last one, then clear text fields
		logger.debug("Component count " + MainWindow.tabbedPaneGraphs.getComponentCount());
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
	};
	
	ActionListener saveTab = (ActionEvent arg0) -> {
		Phoneme temp = PhonemeLoad.getSelectedPhoneme((JScrollPane) MainWindow.tabbedPaneGraphs.getSelectedComponent());
		PhonemeSave.saveToDirectory(temp, new File(temp.path));
	};
	
	ActionListener saveAsTab = (ActionEvent arg0) -> {
		Phoneme temp = PhonemeLoad.getSelectedPhoneme((JScrollPane) MainWindow.tabbedPaneGraphs.getSelectedComponent());
		
		if (fileChooser.showSaveDialog(mainW) == JFileChooser.APPROVE_OPTION) {
			PhonemeSave.saveToCustomDirectory(temp, fileChooser.getSelectedFile().getAbsolutePath());
		}
	};
	
	ActionListener closeAllTab = (ActionEvent arg0) -> {
		MainWindow.tabbedPaneGraphs.removeAll();
		clearText();
		
		mainW.mntmSave.setVisible(false);
		mainW.mntmSaveAs.setVisible(false);
		mainW.mntmClose.setVisible(false);
		mainW.mntmCloseAll.setVisible(false);
	};

	private class MakeActionListener implements ActionListener {

		private Command action;
		
		public MakeActionListener(Command action) {
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
	ActionListener speak = (ActionEvent arg0) -> {
		String voice = espeakNg.getVoiceFromSelection();
		int speedVoice = mainW.optionsSpeed.getSpinnerValue();
		String text = espeakNg.getText(Command.SPEAK);

		String terminalCommand = "espeak-ng -v" + voice + " -s" +speedVoice+ " --stdout \"" + text + "\" |aplay 2>/dev/null";
		CommandUtilities.executeCmd(terminalCommand);
		lastThread = CommandUtilities.getLastThread();
		
		Thread tMonitor = createMonitorThread();
		tMonitor.start();
		
		terminalCommand = "espeak-ng -vmb-en1 --pho " + "\"" + text + "\"";
		String data = CommandUtilities.executeBlockingCmd(terminalCommand);
		
		JPanel mg = new JPanel();
		WrapLayout wl = new WrapLayout(FlowLayout.LEFT, 0, 0);
		mg.setLayout(wl);
		
		ArrayList<ProsodyPhoneme> prosodyPhonemes = (ArrayList<ProsodyPhoneme>) Utilities.getProsodyData(data);

		for (ProsodyPhoneme prosodyPhoneme : prosodyPhonemes)
			mg.add(new ProsodyPanel(prosodyPhoneme));
					
		MainWindow.tabbedPaneGraphs.remove(scrollPane);
		
		scrollPane = new JScrollPane(mg);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(MainWindow.tabbedPaneGraphs.getPreferredSize());
        
		MainWindow.tabbedPaneGraphs.add("Prosody", scrollPane);
	};

	ActionListener speakFile = (ActionEvent arg0) -> {
		String voice = espeakNg.getVoiceFromSelection();
		int speedVoice = mainW.optionsSpeed.getSpinnerValue();
		
		if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String terminalCommand = "espeak-ng -v" + voice + " -s" + speedVoice + " -f " + selectedFile.getAbsolutePath() + " --stdout |aplay 2>/dev/null";
			
			CommandUtilities.executeCmd(terminalCommand);
			lastThread = CommandUtilities.getLastThread();
			
			Thread tMonitor = createMonitorThread();
			tMonitor.start();
		}
	};
	
	private Thread createMonitorThread() {
		return new Thread() {
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
					Thread.currentThread().interrupt();
				}
				mainW.mntmSpeak.setEnabled(true); mainW.mntmSpeakfile.setEnabled(true);
				mainW.btnSpeak.setEnabled(true); mainW.btnPause.setEnabled(false);
				mainW.btnStop.setEnabled(false); mainW.mntmPause.setEnabled(false);
				mainW.mntmStop.setEnabled(false); mainW.mntmSpeakPunctuation.setEnabled(true);
				mainW.mntmSpeakCharacters.setEnabled(true); mainW.mntmSpeakCharacterName.setEnabled(true);
			}
		};
	}
	
	ActionListener pauseFile = (ActionEvent e) -> {
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
	};
	
	
	ActionListener stopFile = (ActionEvent arg0) -> {
		CommandUtilities.executeCmd("pkill -9 -f aplay");
		isPaused = false;
		
		mainW.mntmPause.setText("Pause");
		mainW.btnPause.setIcon(mainW.pauseIcon);
	};
	
	ActionListener selectVoice = (ActionEvent a) -> {
		if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
			prefs.put("", fileChooser.getSelectedFile().getParent());
			logger.info(fileChooser.getName(fileChooser.getSelectedFile()));
		}
	};


	ActionListener selectVoiceVariant = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			fileChooser = new JFileChooser(prefs.get("", new File("../espeak-ng/espeak-ng-data/voices").getAbsolutePath()));
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				prefs.put("", fileChooser.getSelectedFile().getParent());
				if(fileChooser.getName(fileChooser.getSelectedFile()).matches("[A-Za-z0-9]+"))
					espeakNg.setVoiceVariant(fileChooser.getName(fileChooser.getSelectedFile()));
			}
		}
	};
	
	ActionListener viaFileChooser = (ActionEvent e) -> usingFileChooser();
	
	private File usingFileChooser(){
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fileChooser.showDialog(mainW, "Select");
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			logger.debug(file.getAbsolutePath() + " " + file.isDirectory());
			return fileChooser.getSelectedFile();
		}
		
		return null;
	}
	
	private class FoldersListener implements ActionListener {

		private Command key;
		
		public FoldersListener(Command key) {
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

		private Command command;
		
		public GetTextListener(Command command) {
			this.command = command;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String voice = espeakNg.getVoiceFromSelection();
			int speedVoice = mainW.optionsSpeed.getSpinnerValue();

			String terminalCommand1 = "espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + espeakNg.getText(command)+ "\" |aplay 2>/dev/null";
			CommandUtilities.executeCmd(terminalCommand1);
			lastThread = CommandUtilities.getLastThread();
			
			Thread tMonitor = createMonitorThread();
			tMonitor.start();
		}
	}

	


	ActionListener countWordOccurance = (ActionEvent e) -> {
		String[] keys = mainW.textAreaIn.getText().toLowerCase().replaceAll("\n", " ")
				.replaceAll("[^a-zA-Z0-9\\s\\.-]", "").split("\\s+");
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
	};
  
	
	private class CompileListener implements ActionListener {

		private String compileCommand;
		
		public CompileListener(String compileCommand) {
			this.compileCommand = compileCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompileDictionary || e.getSource() == mainW.mntmCompileDictionarydebug) {
				fileChooser.setCurrentDirectory(folders.get(Command.DICT_SOURCE));
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
	
	ActionListener compilePhonemeData = (ActionEvent e) -> {
		if (e.getSource() == mainW.mntmCompilePhonemeData) {
			String cmd = "export ESPEAK_DATA_PATH=" + new File("../espeak-ng").getAbsolutePath()
					+ "; cd " + folders.get(Command.PHONEME_SOURCE).getParent() + " && "
					+ dataPath + "/src/espeak-ng --compile-phonemes=" + folders.get(Command.PHONEME_SOURCE).getName();
			CommandUtilities.executeCmd(cmd);
		}
	};
	
	ActionListener compileMbrolaPhonemes = (ActionEvent e) -> {
		if (e.getSource() == mainW.mntmCompileMbrolaPhonemes) {
			fileChooser.setCurrentDirectory(new File(dataPath + "/phsource/mbrola/"));
			if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) { 
				String cmd = "export ESPEAK_DATA_PATH=" + dataPath +
						"; cd " + fileChooser.getSelectedFile().getParent() +
						" && " + dataPath + "/src/espeak-ng --compile-mbrola=" +  fileChooser.getSelectedFile().getName();
				CommandUtilities.executeCmd(cmd);
			}
		}
	};
	
	ActionListener compileIntonationData = (ActionEvent e) -> {
		if (e.getSource() == mainW.mntmCompileIntonationData) {
			String cmd = "export ESPEAK_DATA_PATH=" + dataPath
					+ "; " + dataPath + "/src/espeak-ng --compile-intonations";
			CommandUtilities.executeCmd(cmd);
		}
	};
	
	ActionListener showDocumentation = (ActionEvent arg0) -> {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		File file = new File("./docs/docindex.html");
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(file.toURI());
			} catch (IOException e) {
				logger.warn(e);
			}
		}
	};
	
	ActionListener exportDirectoryVowelFiles = (ActionEvent e) -> {
		fileChooser= new JFileChooser("../espeak-ng/phsource/vowelcharts/");
		if (e.getSource() == mainW.mntmFromDirectoryVowelFiles && fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
			String cmd = "export ESPEAK_DATA_PATH=" + dataPath +
					"; cd " + fileChooser.getSelectedFile().getParent() +
					" && " + dataPath + "/src/espeak-ng --compile-mbrola=" +  fileChooser.getSelectedFile().getName();
			CommandUtilities.executeCmd(cmd);
			VowelChart.createAndShowGui(fileChooser.getSelectedFile().getPath(), mainW);
		}
	};
	
	ActionListener switchLang = (ActionEvent e) -> {
		String lang = espeakNg.getVoiceFromSelection();
		String path = "";

		switch (lang) {
			case "ru":
				path = "../espeak-ng/phsource/vowelcharts/ru";
				break;
			case "lv":
				path = "../espeak-ng/phsource/vowelcharts/lv";
				break;
			case "pl":
				path = "../espeak-ng/phsource/vowelcharts/pl";
				break;
			default:
				path = "../espeak-ng/phsource/vowelcharts/en";
				break;
		}
		
		VowelChart.createAndShowGui(path, mainW);
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

		// Speak

		mainW.mntmTranslate.addActionListener(new MakeActionListener(Command.TRANSLATE));
		mainW.mntmShowRules.addActionListener(new MakeActionListener(Command.SHOW_RULES));
		mainW.mntmShowIPA.addActionListener(new MakeActionListener(Command.SHOW_IPA));
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

		mainW.mntmMasterPhonemesFile.addActionListener(new FoldersListener(Command.PH_FILE));
		mainW.mntmPhonemeDataSource.addActionListener(new FoldersListener(Command.PHONEME_SOURCE));
		mainW.mntmDictionaryDataSource.addActionListener(new FoldersListener(Command.DICT_SOURCE));
		mainW.mntmSynthesizedSoundWAVfile.addActionListener(new FoldersListener(Command.WAV_FILE));
		mainW.mntmVoiceFileToModifyFormantPeaks.addActionListener(new FoldersListener(Command.VOICE_FILE));
		mainW.mntmEnglish.addActionListener(event);
		mainW.mntmLatvian.addActionListener(event);
		mainW.mntmRussian.addActionListener(event);
		mainW.mntmTamil.addActionListener(event);
		mainW.mntmSpeed.addActionListener(event);
		mainW.mntmSpeakPunctuation.addActionListener(new GetTextListener(Command.SPEAK_PUNC));
		mainW.mntmSpeakCharacters.addActionListener(new GetTextListener(Command.SPEAK_BY_SYMBOL));
		mainW.mntmSpeakCharacterName.addActionListener(new GetTextListener(Command.SPEAK_CHAR_NAME));
        mainW.openMI.addActionListener(event);
        mainW.exportMI.addActionListener(event);
        mainW.clMI.addActionListener(event);
        mainW.quitMI.addActionListener(event);

		// Tools

		mainW.mntmFromDirectoryVowelFiles.addActionListener(exportDirectoryVowelFiles);
		mainW.mntmFromCompiledPhoneme.addActionListener(switchLang);
		// mainW.mntmPLBulgarian.addActionListener();
		// mainW.mntmPLGerman.addActionListener();
		// mainW.mntmPLItalian.addActionListener();
		// mainW.mntmPLRussian.addActionListener();
		mainW.mntmCountWordOccurrences.addActionListener(countWordOccurance);

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
		mainW.btnTranslate.addActionListener(new MakeActionListener(Command.TRANSLATE));
		mainW.btnShowRules.addActionListener(new MakeActionListener(Command.SHOW_RULES));
		mainW.btnShowIPA.addActionListener(new MakeActionListener(Command.SHOW_IPA));
		mainW.btnSpeak.addActionListener(speak);
		mainW.btnPause.addActionListener(pauseFile);
		mainW.btnStop.addActionListener(stopFile);

		mainW.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				CommandUtilities.executeCmd("pkill -9 -f aplay");
			}
		});
		
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

		boolean toSetVisible = MainWindow.tabbedPaneGraphs.getTabCount() != 0;
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
			MainWindow.tfFreq.get(i).addActionListener((ActionEvent ae) -> {
				try {
					mainW.focusedFrame.peaks[index].klt_bp = Short.parseShort(MainWindow.tfBp.get(index).getText());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
					logger.warn(ex);
				}
			});
		}
		// Height text fields
		for(int i=0; i<MainWindow.tfHeight.size();i++){
			final int index =i;
			MainWindow.tfHeight.get(i).addActionListener((ActionEvent ae) -> {
				try {
					short value = Short.parseShort(MainWindow.tfHeight.get(index).getText());
					value = (short) (value << 6);
					mainW.focusedFrame.peaks[index].pkheight = value;
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
					logger.warn(ex);
				}
			});
		}
		// width text fields (also changes peaks.pkright behind the scenes)
		
		for(int i=0; i<MainWindow.tfWidth.size();i++){
			final int index =i;
			MainWindow.tfWidth.get(i).addActionListener((ActionEvent ae) -> {
				try {
					String[] text = MainWindow.tfWidth.get(index).getText().split("/");
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
					logger.warn(ex);
				}
			});
		}

		// klt_bw text fields
		for(int i=0; i<MainWindow.tfBw.size();i++){
			final int index =i;
			MainWindow.tfBw.get(i).addActionListener((ActionEvent ae) -> {
				try {
					mainW.focusedFrame.peaks[index+1].klt_ap = Short.parseShort(MainWindow.tfAp.get(index).getText());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
					logger.warn(ex);
				}
			});
		}
	
		// klt_ap text fields

		for(int i=0; i<MainWindow.tfAp.size();i++){
			final int index =i;
			MainWindow.tfAp.get(i).addActionListener((ActionEvent ae) -> {
				try {
					mainW.focusedFrame.peaks[index].klt_ap = Short.parseShort(MainWindow.tfAp.get(index).getText());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
					logger.warn(ex);
				}
			});
		}
	
		// klt_bp text fields
		
		for(int i=0; i<MainWindow.tfBp.size();i++) {
			final int index =i;
			MainWindow.tfBp.get(i).addActionListener((ActionEvent ae) -> {
				try {
					mainW.focusedFrame.peaks[index+1].klt_bp = Short.parseShort(MainWindow.tfBp.get(index).getText());
					mainW.focusedPanel.repaint();
				} catch (NumberFormatException ex) {
					logger.warn(ex);
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
	
	// Getters for tests.
	public String getVoice() {
		return espeakNg.getVoiceFromSelection();
	}
	
	public String getVoiceVariant() {
		return espeakNg.getVoiceVariant();
	}
	
}
