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
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.espeakng.jeditor.data.Phoneme;
import org.espeakng.jeditor.data.PhonemeLoad;
import org.espeakng.jeditor.data.PhonemeSave;
import org.espeakng.jeditor.data.VowelChart;
import org.espeakng.jeditor.data.ProsodyPanel;
import org.espeakng.jeditor.data.ProsodyPhoneme;
import org.espeakng.jeditor.utils.CommandUtilities;
import org.espeakng.jeditor.utils.Utilities;
import org.espeakng.jeditor.utils.WrapLayout;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
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
    private EspeakNg espeakNg;
    private JScrollPane scrollPane;
//    private Runtime rt;
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
		
		prefs = Preferences.userRoot().node(getClass().getName());
		fileChooser = new JFileChooser(prefs.get("a", new File(".").getAbsolutePath()));
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
			if (e.getSource() == mainW.mntmOpen) {
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
			} else if (e.getSource() == mainW.mntmRussian) {
				File file = new File("./src/main/resources/russian.txt");
				if (!file.exists()) {
					InputStream in = getClass().getResourceAsStream("/russian.txt");
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
			} else if (e.getSource() == mainW.mntmExportGraph) {
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
			String text = espeakNg.getText("speak");

			String terminalCommand = "/usr/bin/espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + text + "\" |/usr/bin/aplay 2>/dev/null";
			CommandUtilities.executeCmd(terminalCommand);
			lastThread = CommandUtilities.getLastThread();
			
			Thread tMonitor = createMonitorThread();
			tMonitor.start();
			
			terminalCommand = "espeak-ng -vmb-en1 --pho " + "\"" + text + "\"";
			String data = CommandUtilities.executeBlockingCmd(terminalCommand);
			
			JPanel mg = new JPanel();
			WrapLayout wl = new WrapLayout(FlowLayout.LEFT, 0, 0);
			mg.setLayout(wl);
			
			ArrayList<ProsodyPhoneme> prosodyPhonemes = Utilities.getProsodyData(data);

			for (ProsodyPhoneme prosodyPhoneme : prosodyPhonemes) {
				mg.add(new ProsodyPanel(prosodyPhoneme));
			}
			
			MainWindow.tabbedPaneGraphs.remove(scrollPane);
			
			scrollPane = new JScrollPane(mg);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			
			MainWindow.tabbedPaneGraphs.add("Prosody", scrollPane);
			MainWindow.tabbedPaneGraphs.revalidate();
			MainWindow.tabbedPaneGraphs.repaint();
			
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
				mainW.mntmSpeak.setEnabled(false);
				mainW.mntmSpeakfile.setEnabled(false);
				mainW.btnSpeak.setEnabled(false);
				mainW.mntmPause.setEnabled(true);
				mainW.mntmStop.setEnabled(true);
				try {
					while (lastThread.isAlive()) {
						Thread.sleep(50);
					}
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
				mainW.mntmSpeak.setEnabled(true);
				mainW.mntmSpeakfile.setEnabled(true);
				mainW.btnSpeak.setEnabled(true);
				mainW.mntmPause.setEnabled(false);
				mainW.mntmStop.setEnabled(false);
			}
		};
		return tMonitor;
	}
	
	ActionListener pauseFile = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isPaused) {
				CommandUtilities.executeCmd("kill -STOP $(pgrep aplay)");
				isPaused = true;
				mainW.mntmPause.setText("Unpause");
			}
			else {
				CommandUtilities.executeCmd("kill -CONT $(pgrep aplay)");
				isPaused = false;
				mainW.mntmPause.setText("Pause");
			}
		}
	};
	
	
	ActionListener stopFile = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			CommandUtilities.executeCmd("pkill -9 -f aplay");
			isPaused = false;
			mainW.mntmPause.setText("Pause");
		}
	};
	
	ActionListener selectVoice = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			// String file8 = "en";
			if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
				prefs.put("", fileChooser.getSelectedFile().getParent());
				System.out.println(fileChooser.getName(fileChooser.getSelectedFile()));
			}
		}
	};

	ActionListener selectVoiceVariant = new ActionListener() {
		public void actionPerformed(ActionEvent a) {
			fileChooser = new JFileChooser(prefs.get("", new File("../espeak-ng/espeak-ng-data/voices").getAbsolutePath()));
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
	
	private void usingFileChooser(){
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fileChooser.showDialog(mainW, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			System.out.println(file.getAbsolutePath() + " " + file.isDirectory());
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
			String terminalCommand = "/usr/bin/espeak-ng -v" +voice+ " -s" +speedVoice+ " --stdout \"" + espeakNg.getText(command)+ "\" |/usr/bin/aplay 2>/dev/null";
			CommandUtilities.executeCmd(terminalCommand);
		}
		
	}
	
	private class CompileListener implements ActionListener {

		private String compileCommand;
		
		public CompileListener(String compileCommand) {
			this.compileCommand = compileCommand;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mainW.mntmCompileDictionary) {
				fileChooser.setCurrentDirectory(new File(dataPath + "/dictsource/"));
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
				logger.warn(e);
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
		mainW.mntmMasterPhonemesFile.addActionListener(viaFileChooser);
		mainW.mntmPhonemeDataSource.addActionListener(viaFileChooser);
		mainW.mntmDictionaryDataSource.addActionListener(viaFileChooser);
		mainW.mntmSynthesizedSoundWAVfile.addActionListener(viaFileChooser);
		mainW.mntmVoiceFileToModifyFormantPeaks.addActionListener(viaFileChooser);
		mainW.mntmEnglish.addActionListener(event);
		mainW.mntmLatvian.addActionListener(event);
		mainW.mntmRussian.addActionListener(event);
		mainW.mntmSpeed.addActionListener(event);
		mainW.mntmSpeakPunctuation.addActionListener(new GetTextListener("speakPunc"));
		mainW.mntmSpeakCharacters.addActionListener(new GetTextListener("speakBySymbol"));
		mainW.mntmSpeakCharacterName.addActionListener(new GetTextListener("speakCharName"));

		// Tools

		mainW.mntmFromDirectoryVowelFiles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mainW.mntmCompileMbrolaPhonemes) {
					if (fileChooser.showOpenDialog(mainW) == JFileChooser.APPROVE_OPTION) {
						String cmd = "export ESPEAK_DATA_PATH=" + dataPath +
								"; cd " + fileChooser.getSelectedFile().getParent() +
								" && " + dataPath + "/src/espeak-ng --compile-mbrola=" +  fileChooser.getSelectedFile().getName();
						CommandUtilities.executeCmd(cmd);
					}
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
		mainW.btnSpeak.addActionListener(speak);
		mainW.btnShowRules.addActionListener(new MakeActionListener("showRules"));
		mainW.btnShowIPA.addActionListener(new MakeActionListener("showIpa"));

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
			e.printStackTrace();
		}
	}
}
