package org.espeakng.jeditor.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;

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

	/*
	 * TODO See bodyInit() method for exact tasks to do.
	 * 
	 */
	private static final long serialVersionUID = 6548939748883665055L;
	// some containers.
	public JMenuBar menuBar;
	public static JTabbedPane tabbedPaneGraphs;	
	
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
	public JMenuItem mntmCountWordFrequencies;
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
	public int labelHeight = 15;
	public int tfx0 = 20; // horizontal starting position
	public int tfxgap = 1; // horizontal interval
	public int tfy0 = 27; // vertical starting position
	public int tfygap = 1; // vertical interval
	public int compWidth = 56;
	public int compHeight = 23;
	public int labelyOffset = compHeight / 4; // vertical offset from the top of the text field/spinner
	
	// some components
	public static ArrayList<JTextField> tfFreq;
	public static ArrayList<JTextField> tfHeight;
	public static ArrayList<JTextField> tfWidth;
	public static ArrayList<JTextField> tfBw;
	public static ArrayList<JTextField> tfAp;
	public static ArrayList<JTextField> tfBp;
	public static JTextField tfmS;
	public static JSpinner spampF;
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
		tfFreq = new ArrayList<JTextField>();
		tfHeight = new ArrayList<JTextField>();
		tfWidth = new ArrayList<JTextField>();
		tfBw = new ArrayList<JTextField>();
		tfAp = new ArrayList<JTextField>();
		tfBp = new ArrayList<JTextField>();
		
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
			mainW.setIconImage(ImageIO.read(new File("./docs/images/lips.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainW.setVisible(true);
		mainW.setUp();
	}
	
	/**
	 * FIXME
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
		}}
	

	
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
		FileOutputStream fos = new FileOutputStream(fileName);
		
		int read;
		try{
			while ((read = io.read()) != -1) {
				fos.write(read);
			}
		} catch(NullPointerException e){
			logger.warn("There is no libespeakservice.so file in .lib folder!");
			logger.warn(e);
		}
		
		
		fos.close();
	}
	
	/**
	 * This method creates menu bar which contain
	 * following menus: File, Speak, Voice, Options, Tools,
	 * Compile and Help. All necessary menu items and
	 * separators for each of mentioned menus are created as well.
	 */
	
	private void menuBarInit() {
		menuBar = new JMenuBar();
		
		////////////////
		// File group //
		////////////////
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);

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
		menuBar.add(mnSpeak);

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
		menuBar.add(mnVoice);

		//mntmSelectVoice = new JMenuItem("Select Voice...");
		//mnVoice.add(mntmSelectVoice);

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

		rdbtnmntmRussian = new JRadioButtonMenuItem("Russian");
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
		menuBar.add(mnOptions);

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

		mntmRussian = new JMenuItem("Russian");
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
		menuBar.add(mnTools);

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

		mntmPLRussian = new JMenuItem("Russian");
		mnProcessLexicon.add(mntmPLRussian);

		mntmCountWordFrequencies = new JMenuItem("Count word frequencies...");
		mnTools.add(mntmCountWordFrequencies);

		///////////////////
		// Compile group //
		///////////////////
		
		mnCompile = new JMenu("Compile");
		menuBar.add(mnCompile);

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
		menuBar.add(mnHelp);

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
		
	    clMI.addActionListener(
		new ActionListener(){
					public void actionPerformed(ActionEvent e){
						MainWindow.tabbedPaneGraphs.remove(MainWindow.tabbedPaneGraphs.getSelectedComponent());
					}});
	    clalMI.addActionListener(
	    		new ActionListener(){
	    					public void actionPerformed(ActionEvent e){
	    						MainWindow.tabbedPaneGraphs.removeAll();
	    					}});
	    
	    pmenu.add(openMI);
	    pmenu.add(exportMI);
	    pmenu.add(clMI);
	    pmenu.add(clalMI);
	    pmenu.add(quitMI);
	    addMouseListener(
	    		new MouseAdapter(){
	    			public void mouseReleased(MouseEvent e){
	    				if(e.getButton() == MouseEvent.BUTTON3)
	    					pmenu.show(e.getComponent(),e.getX(),e.getY());
	    			}}
	    		);}

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
		
		tabbedPaneGraphs = new JTabbedPane(JTabbedPane.TOP);
        tabbedPaneGraphs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPaneGraphs.setComponentPopupMenu(pmenu);
        JScrollPane scrollPane = new JScrollPane(tabbedPaneGraphs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        InputMap  actionMap = (InputMap) UIManager.getDefaults().get("ScrollPane.ancestorInputMap");
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
            }});
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
            }});
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
            }});
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
            }});
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0), new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
            }});
        actionMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0), new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
            }});
        
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        			.addGap(35))
        		.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
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
		lblFreq.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_Spect.add(lblFreq);

		JLabel lblHt = new JLabel("Height");
		lblHt.setBounds(80, 6, 50, labelHeight);
		lblHt.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_Spect.add(lblHt);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(135, 6, 42, labelHeight);
		panel_Spect.add(lblWidth);
		
		JLabel lblklatt = new JLabel("(Klatt)");
		lblklatt.setBounds(231, 6, 44, labelHeight);
		panel_Spect.add(lblklatt);
		
		JLabel label_0 = new JLabel("0");
		label_0.setBounds(6, 31, 8, labelHeight);
		panel_Spect.add(label_0);
		
		JLabel label_1 = new JLabel("1");
		label_1.setBounds(6, 53, 8, labelHeight);
		panel_Spect.add(label_1);
		
		JLabel label_2 = new JLabel("2");
		label_2.setBounds(6, 75, 8, labelHeight);
		panel_Spect.add(label_2);
		
		JLabel label_3 = new JLabel("3");
		label_3.setBounds(6, 97, 8, labelHeight);
		panel_Spect.add(label_3);
		
		JLabel label_4 = new JLabel("4");
		label_4.setBounds(6, 119, 8, labelHeight);
		panel_Spect.add(label_4);
		
		JLabel label_5 = new JLabel("5");
		label_5.setBounds(6, 141, 8, labelHeight);
		panel_Spect.add(label_5);
		
		JLabel label_6 = new JLabel("6");
		label_6.setBounds(6, 163, 8, labelHeight);
		panel_Spect.add(label_6);
		
		JLabel label_7 = new JLabel("7");
		label_7.setBounds(6, 185, 8, labelHeight);
		panel_Spect.add(label_7);
		
		JTextField tfFreq0 = new JTextField();
		tfFreq0.setBounds(tfx0, tfy0, compWidth, compHeight);
		tfFreq0.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq0.setColumns(10);
		tfFreq.add(tfFreq0);
		panel_Spect.add(tfFreq0);
		
		JTextField tfFreq1 = new JTextField();
		tfFreq1.setBounds(tfx0, tfy0 + compHeight + tfygap, compWidth, compHeight);
		tfFreq1.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq1.setColumns(10);
		tfFreq.add(tfFreq1);
		panel_Spect.add(tfFreq1);
		
		JTextField tfFreq2 = new JTextField();
		tfFreq2.setBounds(tfx0, tfy0 + 2 * (compHeight + tfygap), compWidth, compHeight);
		tfFreq2.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq2.setColumns(10);
		tfFreq.add(tfFreq2);
		panel_Spect.add(tfFreq2);
		
		JTextField tfFreq3 = new JTextField();
		tfFreq3.setBounds(tfx0, tfy0 + 3 * (compHeight + tfygap), compWidth, compHeight);
		tfFreq3.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq3.setColumns(10);
		tfFreq.add(tfFreq3);
		panel_Spect.add(tfFreq3);
		
		JTextField tfFreq4 = new JTextField();
		tfFreq4.setBounds(tfx0, tfy0 + 4 * (compHeight + tfygap), compWidth, compHeight);
		tfFreq4.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq4.setColumns(10);
		tfFreq.add(tfFreq4);
		panel_Spect.add(tfFreq4);
		
		JTextField tfFreq5 = new JTextField();
		tfFreq5.setBounds(tfx0, tfy0 + 5 * (compHeight + tfygap), compWidth, compHeight);
		tfFreq5.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq5.setColumns(10);
		tfFreq.add(tfFreq5);
		panel_Spect.add(tfFreq5);
		
		JTextField tfFreq6 = new JTextField();
		tfFreq6.setBounds(tfx0, tfy0 + 6 * (compHeight + tfygap), compWidth, compHeight);
		tfFreq6.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq6.setColumns(10);
		tfFreq.add(tfFreq6);
		panel_Spect.add(tfFreq6);
		
		JTextField tfHeight0 = new JTextField();
		tfHeight0.setBounds(tfx0 + tfxgap + compWidth, tfy0, compWidth, compHeight);
		tfHeight0.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight0.setColumns(10);
		tfHeight.add(tfHeight0);
		panel_Spect.add(tfHeight0);
		
		JTextField tfHeight1 = new JTextField();
		tfHeight1.setBounds(tfx0 + tfxgap + compWidth, tfy0 + compHeight + tfygap, compWidth, compHeight);
		tfHeight1.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight1.setColumns(10);
		tfHeight.add(tfHeight1);
		panel_Spect.add(tfHeight1);
		
		JTextField tfHeight2 = new JTextField();
		tfHeight2.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 2 * (compHeight + tfygap), compWidth, compHeight);
		tfHeight2.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight2.setColumns(10);
		tfHeight.add(tfHeight2);
		panel_Spect.add(tfHeight2);
		
		JTextField tfHeight3 = new JTextField();
		tfHeight3.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 3 * (compHeight + tfygap), compWidth, compHeight);
		tfHeight3.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight3.setColumns(10);
		tfHeight.add(tfHeight3);
		panel_Spect.add(tfHeight3);
		
		JTextField tfHeight4 = new JTextField();
		tfHeight4.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 4 * (compHeight + tfygap), compWidth, compHeight);
		tfHeight4.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight4.setColumns(10);
		tfHeight.add(tfHeight4);
		panel_Spect.add(tfHeight4);
		
		JTextField tfHeight5 = new JTextField();
		tfHeight5.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 5 * (compHeight + tfygap), compWidth, compHeight);
		tfHeight5.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight5.setColumns(10);
		tfHeight.add(tfHeight5);
		panel_Spect.add(tfHeight5);
		
		JTextField tfHeight6 = new JTextField();
		tfHeight6.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 6 * (compHeight + tfygap), compWidth, compHeight);
		tfHeight6.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight6.setColumns(10);
		tfHeight.add(tfHeight6);
		panel_Spect.add(tfHeight6);
		
		JTextField tfHeight7 = new JTextField();
		tfHeight7.setBounds(tfx0 + tfxgap + compWidth, tfy0 + 7 * (compHeight + tfygap), compWidth, compHeight);
		tfHeight7.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight7.setColumns(10);
		tfHeight.add(tfHeight7);
		panel_Spect.add(tfHeight7);
		
		JTextField tfWidth0 = new JTextField();
		tfWidth0.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0, compWidth, compHeight);
		tfWidth0.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth0.setColumns(10);
		tfWidth.add(tfWidth0);
		panel_Spect.add(tfWidth0);
		
		JTextField tfWidth1 = new JTextField();
		tfWidth1.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + compHeight + tfygap, compWidth, compHeight);
		tfWidth1.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth1.setColumns(10);
		tfWidth.add(tfWidth1);
		panel_Spect.add(tfWidth1);
		
		JTextField tfWidth2 = new JTextField();
		tfWidth2.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 2 * (compHeight + tfygap), compWidth, compHeight);
		tfWidth2.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth2.setColumns(10);
		tfWidth.add(tfWidth2);
		panel_Spect.add(tfWidth2);
		
		JTextField tfWidth3 = new JTextField();
		tfWidth3.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 3 * (compHeight + tfygap), compWidth, compHeight);
		tfWidth3.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth3.setColumns(10);
		tfWidth.add(tfWidth3);
		panel_Spect.add(tfWidth3);
		
		JTextField tfWidth4 = new JTextField();
		tfWidth4.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 4 * (compHeight + tfygap), compWidth, compHeight);
		tfWidth4.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth4.setColumns(10);
		tfWidth.add(tfWidth4);
		panel_Spect.add(tfWidth4);
		
		JTextField tfWidth5 = new JTextField();
		tfWidth5.setBounds(tfx0 + 2 * (tfxgap + compWidth), tfy0 + 5 * (compHeight + tfygap), compWidth, compHeight);
		tfWidth5.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth5.setColumns(10);
		tfWidth.add(tfWidth5);
		panel_Spect.add(tfWidth5);
		
		/////////////////////////////////////////////
		// Klatt synthesis text fields with labels //
		/////////////////////////////////////////////
		
		JLabel lblBw = new JLabel("Bw");
		lblBw.setBounds(196, 31, 21, labelHeight);
		lblBw.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_Spect.add(lblBw);
		
		JLabel lblAp = new JLabel("Ap");
		lblAp.setBounds(247, 31, 18, labelHeight);
		lblAp.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_Spect.add(lblAp);
		
		JLabel lblBp = new JLabel("Bp");
		lblBp.setBounds(304, 31, 18, labelHeight);
		lblBp.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_Spect.add(lblBp);
		
		JTextField tfBw1 = new JTextField();
		tfBw1.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + compHeight + tfygap, compWidth, compHeight);
		tfBw1.setHorizontalAlignment(SwingConstants.CENTER);
		tfBw1.setColumns(10);
		tfBw.add(tfBw1);
		panel_Spect.add(tfBw1);
		
		JTextField tfBw2 = new JTextField();
		tfBw2.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + 2 * (compHeight + tfygap), compWidth, compHeight);
		tfBw2.setHorizontalAlignment(SwingConstants.CENTER);
		tfBw2.setColumns(10);
		tfBw.add(tfBw2);
		panel_Spect.add(tfBw2);
		
		JTextField tfBw3 = new JTextField();
		tfBw3.setBounds(tfx0 + 3 * (tfxgap + compWidth), tfy0 + 3 * (compHeight + tfygap), compWidth, compHeight);
		tfBw3.setHorizontalAlignment(SwingConstants.CENTER);
		tfBw3.setColumns(10);
		tfBw.add(tfBw3);
		panel_Spect.add(tfBw3);
		
		JTextField tfAp1 = new JTextField();
		tfAp1.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + compHeight + tfygap, compWidth, compHeight);
		tfAp1.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp1.setColumns(10);
		tfAp.add(tfAp1);
		panel_Spect.add(tfAp1);
		
		JTextField tfAp2 = new JTextField();
		tfAp2.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + 2 * (compHeight + tfygap), compWidth, compHeight);
		tfAp2.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp2.setColumns(10);
		tfAp.add(tfAp2);
		panel_Spect.add(tfAp2);
		
		JTextField tfAp3 = new JTextField();
		tfAp3.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + 3 * (compHeight + tfygap), compWidth, compHeight);
		tfAp3.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp3.setColumns(10);
		tfAp.add(tfAp3);
		panel_Spect.add(tfAp3);
		
		JTextField tfAp4 = new JTextField();
		tfAp4.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + 4 * (compHeight + tfygap), compWidth, compHeight);
		tfAp4.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp4.setColumns(10);
		tfAp.add(tfAp4);
		panel_Spect.add(tfAp4);
		
		JTextField tfAp5 = new JTextField();
		tfAp5.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + 5 * (compHeight + tfygap), compWidth, compHeight);
		tfAp5.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp5.setColumns(10);
		tfAp.add(tfAp5);
		panel_Spect.add(tfAp5);
		
		JTextField tfAp6 = new JTextField();
		tfAp6.setBounds(tfx0 + 4 * (tfxgap + compWidth), tfy0 + 6 * (compHeight + tfygap), compWidth, compHeight);
		tfAp6.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp6.setColumns(10);
		tfAp.add(tfAp6);
		panel_Spect.add(tfAp6);
		
		JTextField tfBp1 = new JTextField();
		tfBp1.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + compHeight + tfygap, compWidth, compHeight);
		tfBp1.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp1.setColumns(10);
		tfBp.add(tfBp1);
		panel_Spect.add(tfBp1);
		
		JTextField tfBp2 = new JTextField();
		tfBp2.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + 2 * (compHeight + tfygap), compWidth, compHeight);
		tfBp2.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp2.setColumns(10);
		tfBp.add(tfBp2);
		panel_Spect.add(tfBp2);
		
		JTextField tfBp3 = new JTextField();
		tfBp3.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + 3 * (compHeight + tfygap), compWidth, compHeight);
		tfBp3.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp3.setColumns(10);
		tfBp.add(tfBp3);
		panel_Spect.add(tfBp3);
		
		JTextField tfBp4 = new JTextField();
		tfBp4.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + 4 * (compHeight + tfygap), compWidth, compHeight);
		tfBp4.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp4.setColumns(10);
		tfBp.add(tfBp4);
		panel_Spect.add(tfBp4);
		
		JTextField tfBp5 = new JTextField();
		tfBp5.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + 5 * (compHeight + tfygap), compWidth, compHeight);
		tfBp5.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp5.setColumns(10);
		tfBp.add(tfBp5);
		panel_Spect.add(tfBp5);
		
		JTextField tfBp6 = new JTextField();
		tfBp6.setBounds(tfx0 + 5 * (tfxgap + compWidth), tfy0 + 6 * (compHeight + tfygap), compWidth, compHeight);
		tfBp6.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp6.setColumns(10);
		tfBp.add(tfBp6);
		panel_Spect.add(tfBp6);

		// mS text field & label //
		
		tfmS = new JTextField();
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
		
		spampF = new JSpinner();
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
		
		JPanel panel_text = new JPanel();
		panel_text.setAutoscrolls(true);
		tabbedPane.addTab("Text", null, panel_text, null);
		
		
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
		
		Image play, pause, stop, resume;
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
			e.printStackTrace();
		}
		
		btnTranslate = new JButton("Translate");
		btnShowRules = new JButton("Show Rules");
		btnShowIPA = new JButton("Show IPA");

		// Text tab horizontal grouping
		
		GroupLayout gl_panel_text = new GroupLayout(panel_text);
		gl_panel_text.setHorizontalGroup(
			gl_panel_text.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_text.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_text.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPaneTextAreaOut)
						.addGroup(gl_panel_text.createSequentialGroup()
							.addGroup(gl_panel_text.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnTranslate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSpeak, Alignment.CENTER))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_text.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnShowRules, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnPause, Alignment.CENTER))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_text.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnShowIPA, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnStop, Alignment.CENTER)))
						.addComponent(scrollPaneTextAreaIn))
					.addContainerGap())
		);
		gl_panel_text.setVerticalGroup(
			gl_panel_text.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_text.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneTextAreaIn, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneTextAreaOut, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
					.addGap(20)
					.addGroup(gl_panel_text.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTranslate)
						.addComponent(btnShowRules)
						.addComponent(btnShowIPA))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_text.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSpeak)
						.addComponent(btnPause)
						.addComponent(btnStop))
					.addGap(54))
		);

		panel_text.setLayout(gl_panel_text);
		getContentPane().setLayout(groupLayout);
	}
}
