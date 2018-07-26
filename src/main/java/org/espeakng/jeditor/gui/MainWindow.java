package org.espeakng.jeditor.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.log4j.Logger;
import org.espeakng.jeditor.data.Frame;
import org.espeakng.jeditor.data.PhonemeLoad;


/**
 * This class is an entry point for the program.
 * It does all the initial preparations for the program to start,
 * builds the main window interface.
 *
 */

public class MainWindow extends JFrame {
    private static Logger logger = Logger.getLogger(MainWindow.class.getName());

	private static final long serialVersionUID = 6548939748883665055L;
	// some containers.
	private JMenuBar mMenuBar;
	public static final JTabbedPane tabbedPaneGraphs = new JTabbedPane(JTabbedPane.TOP);;
	
	// Grouping of JMenu objects and JMenuItem objects, suggestion is in Language.java
	// menuBar group File
	public JMenu mnFile;
	public JMenuItem mntmOpen;
	public JMenuItem mntmOpen2;
	public JMenuItem mntmSave;
	public JMenuItem mntmSaveAs;
	public JMenuItem mntmClose;
	public JMenuItem mntmCloseAll;
	public JMenuItem mntmExportGraph;
	public JMenuItem mntmQuit;
	// menuBar group Speak
	public JMenu mnSpeak;
	public JMenuItem mntmTranslate;
	public JMenuItem mntmShowRules;
	public JMenuItem mntmShowIPA;
	public JMenuItem mntmSpeak;
	public JMenuItem mntmSpeakfile;
	public JMenuItem mntmPause;
	public JMenuItem mntmStop;
	// menuBar group Voice
	public JMenu mnVoice;
	//public JMenuItem mntmSelectVoice;
	public JMenuItem mntmSelectVoiceVariant;
	public ButtonGroup groupOfVoices;
	public JMenu mnSelectVoice;
	public JRadioButtonMenuItem rdbtnmntmEnglish;
	public JRadioButtonMenuItem rdbtnmntmLatvian;
	public JRadioButtonMenuItem rdbtnmntmPolish;
	public JRadioButtonMenuItem rdbtnmntmRussian;
	// menuBar group Options
	public JMenu mnOptions;
	public JMenu mnSetPaths;
	public JMenuItem mntmMasterPhonemesFile;
	public JMenuItem mntmPhonemeDataSource;
	public JMenuItem mntmDictionaryDataSource;
	public JMenuItem mntmSynthesizedSoundWAVfile;
	public JMenuItem mntmVoiceFileToModifyFormantPeaks;
	public JMenu mnLanguage;
	public JMenuItem mntmEnglish;
	public JMenuItem mntmLatvian;
	public JMenuItem mntmRussian;
	public JMenuItem mntmTamil;
	public JMenuItem mntmSpeed;
	public OptionsSpeedWindow optionsSpeed;
	public JMenuItem mntmSpeakPunctuation;
	public JMenuItem mntmSpeakCharacters;
	public JMenuItem mntmSpeakCharacterName;
	// menuBar group Tools
	public JMenu mnTools;
	public JMenu mnMakeVowelsChart;
	public JMenuItem mntmFromCompiledPhoneme;
	public JMenuItem mntmFromDirectoryVowelFiles;
	public JMenu mnProcessLexicon;
	public JMenuItem mntmPLBulgarian;
	public JMenuItem mntmPLGerman;
	public JMenuItem mntmPLItalian;
	public JMenuItem mntmPLRussian;
	public JMenuItem mntmCountWordOccurrences;
	// menuBar group Compile
	public JMenu mnCompile;
	public JMenuItem mntmCompileDictionary;
	public JMenuItem mntmCompileDictionarydebug;
	public JMenuItem mntmCompilePhonemeData;
	public JMenuItem mntmCompileMbrolaPhonemes;
	public JMenuItem mntmCompileIntonationData;
	// menuBar group Help
	public JMenu mnHelp;
	public JMenuItem mntmEspeakDocumentation;
	public JMenuItem mntmAbout;
	
	// positions & dimensions of components
	private int labelHeight = 15;
	private int tfx0 = 20; // horizontal starting position
	private int tfxgap = 1; // horizontal interval
	private int tfy0 = 27; // vertical starting position
	private int tfygap = 1; // vertical interval
	private int compWidth = 56;
	private int compHeight = 23;
	private int labelyOffset = compHeight / 4; // vertical offset from the top of the text field/spinner
	
	// some components
	public static final List<JTextField> tfFreq = new ArrayList<>();
	public static final List<JTextField> tfHeight = new ArrayList<>();
	public static final List<JTextField> tfWidth = new ArrayList<>();
	public static final List<JTextField> tfBw = new ArrayList<>();
	public static final List<JTextField> tfAp = new ArrayList<>();
	public static final List<JTextField> tfBp = new ArrayList<>();
	public static final JTextField tfmS = new JTextField();
	public static final JSpinner spampF = new JSpinner();
	public JButton btnZoom;
	public JButton btnZoom_1;
	public JTextArea textAreaIn;
	public JTextArea textAreaOut;
	public JButton btnTranslate;
	public JButton btnSpeak;
	public JButton btnPause;
	public JButton btnStop;
	public JButton btnShowRules;
	public JButton btnShowIPA;
	public JPanel panel_Spect;
	public JPopupMenu pmenu;
	public JMenuItem openMI;
	public JMenuItem exportMI;
	public JMenuItem clMI;
	public JMenuItem clalMI;
	public JMenuItem quitMI;
	
	private enum Texts {
		RUSSIAN("Russian"),
		DIALOG("Dialog");
		
		private String text;
		
		Texts(String text) {
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
	}
	
	// eventHandler object
	public EventHandlers eventHandlers;
	
	// Frame and panel currently being focused
	public Frame focusedFrame;
	public JPanel focusedPanel;
	
	public ImageIcon pauseIcon, resumeIcon;
	
	// Singleton design pattern, also easier to access main window from anywhere in code.
	private static MainWindow instance = new MainWindow();
	
	public static MainWindow getMainWindow(){return instance;}
	
	private  MainWindow() {
		
		frameInit();
		menuBarInit();
		bodyInit();
		PhonemeLoad.phonemeListInit();
		
		eventHandlers = new EventHandlers(this);
		eventHandlers.initHandlers();
	}

	public static void main(String[] args) {		
		MainWindow mainW = MainWindow.getMainWindow();
		
		mainW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainW.setTitle("eSpeak NG Java Editor");
		mainW.setSize(new Dimension(1000, 600));
		
		try {
			mainW.setIconImage(ImageIO.read(new File("./src/main/resources/lips.png")));
		} catch (IOException e) {
			logger.warn(e);
		}
		
		mainW.setVisible(true);
		mainW.setUp();
	}
	
	/**
	 * This method copies libespeakservice.so file to hidden folder where the
	 * executable jar runs for passing all tests in Maven. It is required to have hidden
	 * lib folder containing that file.
	 * For proper work with code just create hiden .lib folder nearby Your project src folder
	 * and copy libespeakservice.so from lib folder.
	 */
	

	public void setUp() {
		if (!(new File(".lib/libespeakservice.so").exists())) {
			new File(".lib").mkdir();
			try {
				Files.copy(new File("lib/libespeakservice.so").toPath(),new File(".lib/libespeakservice.so").toPath());
			} catch (IOException e) {
				logger.warn(e);
			}
		}
	}
	

	
/*
 * This method copies files correct way. It is used for setUp() method.
 * 
 * 
 */
	

	
	/**
	 * This is an auxiliary method employed by setUp() method. It copies the
	 * contents of hidden file passed as stream (first parameter) to the file
	 * witch path is specified as String (second parameter).
	 * 
	 * @param io
	 * @param fileName
	 * @throws IOException
	 */

	public void setFile(InputStream io, String fileName) throws IOException {
		int read;
		try (FileOutputStream fos = new FileOutputStream(fileName)) {
			while ((read = io.read()) != -1)
				fos.write(read);
		} catch(NullPointerException e){
			logger.warn("There is no libespeakservice.so file in .lib folder!");
			logger.warn(e);
		}
	}
	
	/**
	 * This method creates menu bar which contain
	 * following menus: File, Speak, Voice, Options, Tools,
	 * Compile and Help. All necessary menu items and
	 * separators for each of mentioned menus are created as well.
	 */
	
	private void menuBarInit() {
		mMenuBar = new JMenuBar();
		
		////////////////
		// File group //
		////////////////
		
		mnFile = new JMenu("File");
		mMenuBar.add(mnFile);

		mntmOpen = new JMenuItem("Open...");
		mnFile.add(mntmOpen);

		mntmOpen2 = new JMenuItem("Open2...");
		mnFile.add(mntmOpen2);
		

		mntmExportGraph = new JMenuItem("Export graph");
		mnFile.add(mntmExportGraph);

		mntmSave = new JMenuItem("Save");
		mntmSave.setVisible(false);
		mnFile.add(mntmSave);

		mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setVisible(false);
		mnFile.add(mntmSaveAs);

		mntmClose = new JMenuItem("Close");
		mntmClose.setVisible(false);
		mnFile.add(mntmClose);
		
		mntmCloseAll = new JMenuItem("Close all");
		mntmCloseAll.setVisible(false);
		mnFile.add(mntmCloseAll);

		mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		
		/////////////////
		// Speak group //
		/////////////////
		
		mnSpeak = new JMenu("Speak");
		mMenuBar.add(mnSpeak);

		mntmTranslate = new JMenuItem("Translate");
		mnSpeak.add(mntmTranslate);

		mntmShowRules = new JMenuItem("Show Rules");
		mnSpeak.add(mntmShowRules);

		mntmShowIPA = new JMenuItem("Show IPA");
		mnSpeak.add(mntmShowIPA);

		mntmSpeak = new JMenuItem("Speak");
		mnSpeak.add(mntmSpeak);

		mnSpeak.add(new JSeparator());

		mntmSpeakfile = new JMenuItem("Speak file...");
		mnSpeak.add(mntmSpeakfile);

		mntmPause = new JMenuItem("Pause");
		mntmPause.setEnabled(false);
		mnSpeak.add(mntmPause);

		mntmStop = new JMenuItem("Stop");
		mntmStop.setEnabled(false);
		mnSpeak.add(mntmStop);
		
		/////////////////
		// Voice group //
		/////////////////
		
		mnVoice = new JMenu("Voice");
		mMenuBar.add(mnVoice);

		mntmSelectVoiceVariant = new JMenuItem("Select Voice Variant...");
		mnVoice.add(mntmSelectVoiceVariant);

		mnVoice.add(new JSeparator());

		mnSelectVoice = new JMenu("Select Voice ");
		mnVoice.add(mnSelectVoice);

		rdbtnmntmEnglish = new JRadioButtonMenuItem("English");
		rdbtnmntmEnglish.setSelected(true);
		mnSelectVoice.add(rdbtnmntmEnglish);

		rdbtnmntmLatvian = new JRadioButtonMenuItem("Latvian");
		mnSelectVoice.add(rdbtnmntmLatvian);

		rdbtnmntmPolish = new JRadioButtonMenuItem("Polish");
		mnSelectVoice.add(rdbtnmntmPolish);

		rdbtnmntmRussian = new JRadioButtonMenuItem(Texts.RUSSIAN.getText());
		mnSelectVoice.add(rdbtnmntmRussian);

		groupOfVoices = new ButtonGroup();
		groupOfVoices.add(rdbtnmntmEnglish);
		groupOfVoices.add(rdbtnmntmRussian);
		groupOfVoices.add(rdbtnmntmLatvian);
		groupOfVoices.add(rdbtnmntmPolish);
		
		///////////////////
		// Options group //
		///////////////////
		
		mnOptions = new JMenu("Options");
		mMenuBar.add(mnOptions);

		mnSetPaths = new JMenu("Set paths");
		mnOptions.add(mnSetPaths);

		mntmMasterPhonemesFile = new JMenuItem("Master phonemes file...");
		mnSetPaths.add(mntmMasterPhonemesFile);

		mntmPhonemeDataSource = new JMenuItem("Phoneme data source...");
		mnSetPaths.add(mntmPhonemeDataSource);

		mntmDictionaryDataSource = new JMenuItem("Dictionary data source...");
		mnSetPaths.add(mntmDictionaryDataSource);

		mntmSynthesizedSoundWAVfile = new JMenuItem("Synthesized sound WAV file...");
		mnSetPaths.add(mntmSynthesizedSoundWAVfile);

		mnSetPaths.add(new JSeparator());

		mntmVoiceFileToModifyFormantPeaks = new JMenuItem("Voice file to modify formant peaks...");
		mnSetPaths.add(mntmVoiceFileToModifyFormantPeaks);

		mnLanguage = new JMenu("Language");
		mnOptions.add(mnLanguage);

		mntmEnglish = new JMenuItem("English");
		mnLanguage.add(mntmEnglish);

		mntmLatvian = new JMenuItem("Latvian");
		mnLanguage.add(mntmLatvian);

		mntmRussian = new JMenuItem(Texts.RUSSIAN.getText());
		mnLanguage.add(mntmRussian);
		
		mntmTamil = new JMenuItem("Tamil");
		mnLanguage.add(mntmTamil);

		mntmSpeed = new JMenuItem("Speed...");
		mnOptions.add(mntmSpeed);
		optionsSpeed = new OptionsSpeedWindow();

		mnOptions.add(new JSeparator());

		mntmSpeakPunctuation = new JMenuItem("Speak punctuation");
		mnOptions.add(mntmSpeakPunctuation);

		mntmSpeakCharacters = new JMenuItem("Speak characters");
		mnOptions.add(mntmSpeakCharacters);

		mntmSpeakCharacterName = new JMenuItem("Speak character name");
		mnOptions.add(mntmSpeakCharacterName);
		
		/////////////////
		// Tools group //
		/////////////////
		
		mnTools = new JMenu("Tools");
		mMenuBar.add(mnTools);

		mnMakeVowelsChart = new JMenu("Make Vowels Chart");
		mnTools.add(mnMakeVowelsChart);

		mntmFromCompiledPhoneme = new JMenuItem("From compiled phoneme data");
		mnMakeVowelsChart.add(mntmFromCompiledPhoneme);

		mntmFromDirectoryVowelFiles = new JMenuItem("From directory of vowel files...");
		mnMakeVowelsChart.add(mntmFromDirectoryVowelFiles);

		mnProcessLexicon = new JMenu("Process Lexicon");
		mnTools.add(mnProcessLexicon);

		mntmPLBulgarian = new JMenuItem("Bulgarian");
		mnProcessLexicon.add(mntmPLBulgarian);

		mntmPLGerman = new JMenuItem("German");
		mnProcessLexicon.add(mntmPLGerman);

		mntmPLItalian = new JMenuItem("Italian");
		mnProcessLexicon.add(mntmPLItalian);

		mntmPLRussian = new JMenuItem(Texts.RUSSIAN.getText());
		mnProcessLexicon.add(mntmPLRussian);

		mntmCountWordOccurrences = new JMenuItem("Count word occurrences...");
		mnTools.add(mntmCountWordOccurrences);

		///////////////////
		// Compile group //
		///////////////////
		
		mnCompile = new JMenu("Compile");
		mMenuBar.add(mnCompile);

		mntmCompileDictionary = new JMenuItem("Compile dictionary\"");
		mnCompile.add(mntmCompileDictionary);

		mntmCompileDictionarydebug = new JMenuItem("Compile dictionary (debug)");
		mnCompile.add(mntmCompileDictionarydebug);

		mntmCompilePhonemeData = new JMenuItem("Compile phoneme data 22050HZ");
		mnCompile.add(mntmCompilePhonemeData);

		mnCompile.add(new JSeparator());

        mntmCompileMbrolaPhonemes = new JMenuItem("Compile mbrola phonemes list...");
		mnCompile.add(mntmCompileMbrolaPhonemes);

		mntmCompileIntonationData = new JMenuItem("Compile intonation data");
		mnCompile.add(mntmCompileIntonationData);
		
		////////////////
		// Help group //
		////////////////
		
		mnHelp = new JMenu("Help");
		mMenuBar.add(mnHelp);

		mntmEspeakDocumentation = new JMenuItem("eSpeak Documentation");
		mnHelp.add(mntmEspeakDocumentation);

		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
	    pmenu = new JPopupMenu();
	    openMI = new JMenuItem("Open");
	    exportMI = new JMenuItem("Export");
	    clMI = new JMenuItem("Close graph");
	    clalMI = new JMenuItem("Close all graph");
	    quitMI = new JMenuItem("Quit");
		
	    clMI.addActionListener((ActionEvent e) -> MainWindow.tabbedPaneGraphs.remove(MainWindow.tabbedPaneGraphs.getSelectedComponent()));
	    clalMI.addActionListener((ActionEvent e) -> MainWindow.tabbedPaneGraphs.removeAll());
	    
	    pmenu.add(openMI);
	    pmenu.add(exportMI);
	    pmenu.add(clMI);
	    pmenu.add(clalMI);
	    pmenu.add(quitMI);
	    addMouseListener(
    		new MouseAdapter(){
    			@Override
    			public void mouseReleased(MouseEvent e){
    				if(e.getButton() == MouseEvent.BUTTON3)
    					pmenu.show(e.getComponent(),e.getX(),e.getY());
    			}
    		});
	    }

	/**
	 * This method initiates frame body. Creates "Spect" and "Text" tabs on
	 * tabbed pane with all the necessary text fields and labels, as well as
	 * tabbed pane for graphs.
	 */

	public void bodyInit() {
		
		// TODO Implement "Amplitude frame" in bottom left corner.
		
		
		// initiate keyframe sequence/prosody tab pane:
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		// initiate keyframe sequence graph pane:
		
        tabbedPaneGraphs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPaneGraphs.setComponentPopupMenu(pmenu);
        JScrollPane scrollPane = new JScrollPane(tabbedPaneGraphs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        InputMap  actionMap = (InputMap) UIManager.getDefaults().get("ScrollPane.ancestorInputMap");
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), null);
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), null);
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), null);
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), null);
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0), null);
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0), null);
        
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        			.addGap(35))
        		.addComponent(mMenuBar, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(mMenuBar, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
        				.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(0))
        );

        ///////////////////////////
        //////// Spect Tab ////////
        ///////////////////////////
        
		panel_Spect = new JPanel();
		panel_Spect.setLayout(null);
		panel_Spect.setComponentPopupMenu(pmenu);
		tabbedPane.addTab("Spect", null, panel_Spect, null);
	
		///////////////////////////////////////////////
		// formant parameter text fields with labels //
		///////////////////////////////////////////////
		
		JLabel lblFreq = new JLabel("Frequency");
		lblFreq.setBounds(3, 6, 100, labelHeight);
		lblFreq.setFont(new Font(Texts.DIALOG.getText(), Font.BOLD, 12));
		panel_Spect.add(lblFreq);

		JLabel lblHt = new JLabel("Height");
		lblHt.setBounds(80, 6, 50, labelHeight);
		lblHt.setFont(new Font(Texts.DIALOG.getText(), Font.BOLD, 12));
		panel_Spect.add(lblHt);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(135, 6, 42, labelHeight);
		panel_Spect.add(lblWidth);
		
		JLabel lblklatt = new JLabel("(Klatt)");
		lblklatt.setBounds(231, 6, 44, labelHeight);
		panel_Spect.add(lblklatt);
		
		for (int i = 0; i <= 7; i++) {
			JLabel label = new JLabel(String.valueOf(i));
			
			label.setBounds(6, 31 + i * 22, 8, labelHeight);
			panel_Spect.add(label);			
		}
		
		for (int i = 0; i <= 6; i++) {
			JTextField tfFreqOption = new JTextField();
			
			tfFreqOption.setBounds(tfx0, tfy0 + i * (compHeight + tfygap), compWidth, compHeight);
			tfFreqOption.setHorizontalAlignment(SwingConstants.CENTER);
			tfFreqOption.setColumns(10);
			
			tfFreq.add(tfFreqOption);
			panel_Spect.add(tfFreqOption);
		}
		
		for (int i = 0; i <= 7; i++) {
			JTextField tfHeightOption = new JTextField();
			
			tfHeightOption.setBounds(tfx0 + tfxgap + compWidth, tfy0 + i * (compHeight + tfygap), compWidth, compHeight);
			tfHeightOption.setHorizontalAlignment(SwingConstants.CENTER);
			tfHeightOption.setColumns(10);
			
			tfHeight.add(tfHeightOption);
			panel_Spect.add(tfHeightOption);
		}
		
		for (int i = 0; i <= 5; i++) {
			JTextField tfWidthOption = new JTextField();
			
			tfWidthOption.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + i * (compHeight + tfygap), compWidth, compHeight);
			tfWidthOption.setHorizontalAlignment(SwingConstants.CENTER);
			tfWidthOption.setColumns(10);
			
			tfWidth.add(tfWidthOption);
			panel_Spect.add(tfWidthOption);	
		}
		
		/////////////////////////////////////////////
		// Klatt synthesis text fields with labels //
		/////////////////////////////////////////////
		
		JLabel lblBw = new JLabel("Bw");
		lblBw.setBounds(196, 31, 21, labelHeight);
		lblBw.setFont(new Font(Texts.DIALOG.getText(), Font.BOLD, 12));
		panel_Spect.add(lblBw);
		
		JLabel lblAp = new JLabel("Ap");
		lblAp.setBounds(247, 31, 18, labelHeight);
		lblAp.setFont(new Font(Texts.DIALOG.getText(), Font.BOLD, 12));
		panel_Spect.add(lblAp);
		
		JLabel lblBp = new JLabel("Bp");
		lblBp.setBounds(304, 31, 18, labelHeight);
		lblBp.setFont(new Font(Texts.DIALOG.getText(), Font.BOLD, 12));
		panel_Spect.add(lblBp);
		
		for (int i = 1; i <= 3; i++) {
			JTextField tfBwOption = new JTextField();
			
			tfBwOption.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + i * (compHeight + tfygap), compWidth, compHeight);
			tfBwOption.setHorizontalAlignment(SwingConstants.CENTER);
			tfBwOption.setColumns(10);
			
			tfBw.add(tfBwOption);
			panel_Spect.add(tfBwOption);
		}
		
		for (int i = 1; i <= 6; i++) {
			JTextField tfApOption = new JTextField();
			
			tfApOption.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + i * (compHeight + tfygap), compWidth, compHeight);
			tfApOption.setHorizontalAlignment(SwingConstants.CENTER);
			tfApOption.setColumns(10);
			
			tfAp.add(tfApOption);
			panel_Spect.add(tfApOption);
		}
		
		for (int i = 1; i <= 6; i++) {
			JTextField tfBpOption = new JTextField();
			
			tfBpOption.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + i * (compHeight + tfygap), compWidth, compHeight);
			tfBpOption.setHorizontalAlignment(SwingConstants.CENTER);
			tfBpOption.setColumns(10);
			
			tfBp.add(tfBpOption);
			panel_Spect.add(tfBpOption);
		}
		
		// mS text field & label //
		
		tfmS.setBounds(tfx0, tfy0 + 9 * (compHeight + tfygap), compWidth, compHeight);
		tfmS.setHorizontalAlignment(SwingConstants.CENTER);
		tfmS.setColumns(10);
		panel_Spect.add(tfmS);
		
		JLabel lblMsTf = new JLabel("mS");
		lblMsTf.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 9 * (compHeight + tfygap) + labelyOffset, 21, labelHeight);
		panel_Spect.add(lblMsTf);
		
		///////////////////////////////////////////////
		// Additional parameter spinners with labels //
		// (AV, Tilt, Avp, kopen, FNZ, Aspr...).	 //
		///////////////////////////////////////////////
		
		spampF.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 9 * (compHeight + tfygap), compWidth, compHeight);
		spampF.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spampF);
		
		JSpinner spAV = new JSpinner();
		spAV.setBounds(tfx0, tfy0 + 10 * (compHeight + tfygap), compWidth, compHeight);
		spAV.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spAV);
		
		JSpinner spFNZ = new JSpinner();
		spFNZ.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 10 * (compHeight + tfygap), compWidth, compHeight);
		spFNZ.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spFNZ);
		
		JSpinner spTilt = new JSpinner();
		spTilt.setBounds(tfx0, tfy0 + 11 * (compHeight + tfygap), compWidth, compHeight);
		spTilt.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spTilt);
		
		JSpinner spAspr = new JSpinner();
		spAspr.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 11 * (compHeight + tfygap), compWidth, compHeight);
		spAspr.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spAspr);
		
		JSpinner spSkew = new JSpinner();
		spSkew.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + 11 * (compHeight + tfygap), compWidth, compHeight);
		spSkew.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spSkew);
		
		JSpinner spAVp = new JSpinner();
		spAVp.setBounds(tfx0, tfy0 + 12 * (compHeight + tfygap), compWidth, compHeight);
		spAVp.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spAVp);
		
		JSpinner spFric = new JSpinner();
		spFric.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 12 * (compHeight + tfygap), compWidth, compHeight);
		spFric.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spFric);
		
		JSpinner spFricBP = new JSpinner();
		spFricBP.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + 12 * (compHeight + tfygap), compWidth, compHeight);
		spFricBP.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spFricBP);

		JSpinner spkopen = new JSpinner();
		spkopen.setBounds(tfx0, tfy0 + 13 * (compHeight + tfygap), compWidth, compHeight);
		spkopen.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spkopen);
		
		JSpinner spTurb = new JSpinner();
		spTurb.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 13 * (compHeight + tfygap), compWidth, compHeight);
		spTurb.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		panel_Spect.add(spTurb);
		
		JLabel lblAmpF = new JLabel("% amp - Frame");
		lblAmpF.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + 9 * (compHeight + tfygap) + labelyOffset, 103, labelHeight);
		panel_Spect.add(lblAmpF);
		
		JLabel lblAv = new JLabel("AV");
		lblAv.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 10 * (compHeight + tfygap) + labelyOffset, 44, labelHeight);
		lblAv.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Spect.add(lblAv);


		JLabel lblTilt = new JLabel("Tilt");
		lblTilt.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 11 * (compHeight + tfygap) + labelyOffset, 22, labelHeight);
		lblTilt.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Spect.add(lblTilt);
		
		JLabel lblAvp = new JLabel("AVp");
		lblAvp.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 12 * (compHeight + tfygap) + labelyOffset, 27, labelHeight);
		lblAvp.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Spect.add(lblAvp);
		
		JLabel lblKopen = new JLabel("kopen");
		lblKopen.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 13 * (compHeight + tfygap) + labelyOffset, 44, labelHeight);
		lblKopen.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Spect.add(lblKopen);
		
		JLabel lblFnz = new JLabel("FNZ");
		lblFnz.setHorizontalAlignment(SwingConstants.LEFT);
		lblFnz.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + 10 * (compHeight + tfygap) + labelyOffset, 44, labelHeight);
		panel_Spect.add(lblFnz);
		
		JLabel lblAspr = new JLabel("Aspr");
		lblAspr.setHorizontalAlignment(SwingConstants.LEFT);
		lblAspr.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + 11 * (compHeight + tfygap) + labelyOffset, 44, labelHeight);
		panel_Spect.add(lblAspr);
		
		JLabel lblFric = new JLabel("Fric");
		lblFric.setHorizontalAlignment(SwingConstants.LEFT);
		lblFric.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + 12 * (compHeight + tfygap) + labelyOffset, 44, labelHeight);
		panel_Spect.add(lblFric);
		
		JLabel lblTurb = new JLabel("Turb");
		lblTurb.setHorizontalAlignment(SwingConstants.LEFT);
		lblTurb.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + 13 * (compHeight + tfygap) + labelyOffset, 44, labelHeight);
		panel_Spect.add(lblTurb);
		
		JLabel lblSkew = new JLabel("Skew");
		lblSkew.setHorizontalAlignment(SwingConstants.LEFT);
		lblSkew.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + 11 * (compHeight + tfygap) + labelyOffset, 44, labelHeight);
		panel_Spect.add(lblSkew);
		
		JLabel lblFricbp = new JLabel("FricBP");
		lblFricbp.setHorizontalAlignment(SwingConstants.LEFT);
		lblFricbp.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + 12 * (compHeight + tfygap) + labelyOffset, 44, labelHeight);
		panel_Spect.add(lblFricbp);
		
		// Zoom buttons //
		
		btnZoom = new JButton("Zoom-");
		btnZoom.setBounds(tfx0, 405, 97, compHeight);
		panel_Spect.add(btnZoom);
		
		btnZoom_1 = new JButton("Zoom+");
		btnZoom_1.setBounds(120, 405, 97, compHeight);
		panel_Spect.add(btnZoom_1);
		
		
		// % amp - Sequence parameter at the bottom //
		
		JSpinner spampS = new JSpinner();
		spampS.setModel(new SpinnerNumberModel(0, 0, 596, 1));
		spampS.setBounds(tfx0, 450, compWidth, compHeight);
		panel_Spect.add(spampS);
		
		JLabel lblAmpS = new JLabel("% amp - Sequence");
		lblAmpS.setHorizontalAlignment(SwingConstants.LEFT);
		lblAmpS.setBounds(tfx0 + tfxgap + compWidth, 450, 139, labelHeight);
		panel_Spect.add(lblAmpS);
		
		// A Label that obviously relates to "Amplitude frame", //
		// witch is not implemented.							//
		
		JLabel lblMs = new JLabel("mS");
		lblMs.setBounds(tfx0 + tfxgap + compWidth, 477, 21, labelHeight);
		panel_Spect.add(lblMs);

		//////////////////////////
		//////// Text Tab ////////
		//////////////////////////
		
		JPanel panelText = new JPanel();
		panelText.setAutoscrolls(true);
		tabbedPane.addTab("Text", null, panelText, null);
		
		
		// Input text area:
		
		textAreaIn = new JTextArea();
		textAreaIn.setText("Hello");
		textAreaIn.setLineWrap(true);
		textAreaIn.setWrapStyleWord(true);
		JScrollPane scrollPaneTextAreaIn = new JScrollPane(textAreaIn, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Output text area:
		
		textAreaOut = new JTextArea();
		textAreaOut.setLineWrap(true);
		textAreaOut.setWrapStyleWord(true);
		JScrollPane scrollPaneTextAreaOut = new JScrollPane(textAreaOut, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Command buttons:
		
		btnSpeak = new JButton("");
		btnPause = new JButton("");
		btnPause.setEnabled(false);
		btnStop = new JButton("");
		btnStop.setEnabled(false);
		
		Image play;
		Image pause;
		Image stop;
		Image resume;
		
		try {
			play = ImageIO.read(new File("./src/main/resources/play.png"));
			btnSpeak.setIcon(new ImageIcon(play));
			
			pause = ImageIO.read(new File("./src/main/resources/pause.png"));
			btnPause.setIcon(new ImageIcon(pause));
			
			stop = ImageIO.read(new File("./src/main/resources/stop.png"));
			btnStop.setIcon(new ImageIcon(stop));
			
			resume = ImageIO.read(new File("./src/main/resources/resume.png"));
			
			pauseIcon = new ImageIcon(pause);
			resumeIcon = new ImageIcon(resume);
		} catch (IOException e) {
			logger.warn(e);
		}
		
		btnTranslate = new JButton("Translate");
		btnShowRules = new JButton("Show Rules");
		btnShowIPA = new JButton("Show IPA");

		// Text tab horizontal grouping
		GroupLayout glPanelText = new GroupLayout(panelText);
		glPanelText.setHorizontalGroup(
			glPanelText.createParallelGroup(Alignment.LEADING)
				.addGroup(glPanelText.createSequentialGroup()
					.addContainerGap()
					.addGroup(glPanelText.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPaneTextAreaOut)
						.addGroup(glPanelText.createSequentialGroup()
							.addGroup(glPanelText.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnTranslate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSpeak, Alignment.CENTER))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(glPanelText.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnShowRules, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnPause, Alignment.CENTER))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(glPanelText.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnShowIPA, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnStop, Alignment.CENTER)))
						.addComponent(scrollPaneTextAreaIn))
					.addContainerGap())
		);
		glPanelText.setVerticalGroup(
			glPanelText.createParallelGroup(Alignment.TRAILING)
				.addGroup(glPanelText.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneTextAreaIn, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneTextAreaOut, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
					.addGap(20)
					.addGroup(glPanelText.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTranslate)
						.addComponent(btnShowRules)
						.addComponent(btnShowIPA))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(glPanelText.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSpeak)
						.addComponent(btnPause)
						.addComponent(btnStop))
					.addGap(54))
		);

		panelText.setLayout(glPanelText);
		getContentPane().setLayout(groupLayout);
	}
}