package interfacePckg;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import dataStructure.PhonemeLoad;

import java.awt.Font;
import java.util.ArrayList;

public class MainWindow extends JFrame {

	public JMenuBar menuBar;
	public static ArrayList<JTextField> tfFreq;
	public static ArrayList<JTextField> tfHeight;
	public static ArrayList<JTextField> tfWidth;
	public static ArrayList<JTextField> tfBw;
	public static ArrayList<JTextField> tfAp;
	public static ArrayList<JTextField> tfBp;
	public JTextField tfmS;
	//TODO grouping of JMenu objects and JMenuItem objects, suggestion is in Language.java
	// menuBar group File
	public JMenu mnNewMenu;
	public JMenuItem mntmNewMenuItem;
	public JMenuItem mntmOpen;
	public JMenuItem mntmSave;
	public JMenuItem mntmSaveAs;
	public JMenuItem mntmClose;
	public JMenuItem mntmCloseAll;
	public JMenuItem mntmQuit;
	// menuBar group Speak
	public JMenu mnNewMenu_1;
	public JMenuItem mntmNewMenuItem_1;
	public JMenuItem mntmShowRules;
	public JMenuItem mntmNewMenuItem_2;
	public JMenuItem mntmNewMenuItem_3;
	public JMenuItem mntmNewMenuItem_4;
	public JMenuItem mntmNewMenuItem_5;
	public JMenuItem mntmNewMenuItem_6;
	// menuBar group Voice
	public JMenu mnNewMenu_2;
	public JMenuItem mntmNewMenuItem_7;
	public JMenuItem mntmNewMenuItem_8;
	public ButtonGroup groupOfVoices;
	public JMenu mnNewMenu_7;
	public JRadioButtonMenuItem rdbtnmntmEnglish;
	public JRadioButtonMenuItem rdbtnmntmRussian;
	public JRadioButtonMenuItem rdbtnmntmLatvian;
	public JRadioButtonMenuItem rdbtnmntmPolish;
	// menuBar group Options
	public JMenu mnNewMenu_3;
	public JMenu mnNewMenu_4;
	public JMenuItem mntmNewMenuItem_9;
	public JMenuItem mntmNewMenuItem_10;
	public JMenuItem mntmNewMenuItem_11;
	public JMenuItem mntmNewMenuItem_12;
	public JMenuItem mntmNewMenuItem_13;
	public JMenu mnNewMenu_8;
	public JMenuItem mntmEnglish;
	public JMenuItem mntmLatvian;
	public JMenuItem mntmRussian;
	public JMenuItem mntmSpeed;
	public OptionsSpeedWindow optionsSpeed;
	public JMenuItem mntmSpeakPunctuation;
	public JMenuItem mntmSpeakCharacters;
	public JMenuItem mntmSpeakCharacterName;
	// menuBar group Tools
	public JMenu mnTools;
	public JMenu mnMakeVowelsChart;
	public JMenuItem mntmFromCompiledPhoneme;
	public JMenuItem mntmNewMenuItem_14;
	public JMenu mnNewMenu_5;
	public JMenuItem mntmNewMenuItem_15;
	public JMenuItem mntmNewMenuItem_16;
	public JMenuItem mntmNewMenuItem_17;
	public JMenuItem mntmNewMenuItem_18;
	public JMenuItem mntmNewMenuItem_19;
	public JMenuItem mntmCountWordFrequencies;
	public JMenuItem mntmTesttemporary;
	// menuBar group Compile
	public JMenu mnCompile;
	public JMenuItem mntmCompileDictionary;
	public JMenuItem mntmCompileDictionarydebug;
	public JMenuItem mntmCompilePhonemeData;
	public JMenuItem mntmCompileAtSample;
	public JMenuItem mntmCompileMbrolaPhonemes;
	public JMenuItem mntmCompileIntonationData;
	public JMenuItem mntmLayoutrulesFile;
	public JMenuItem mntmSortrulesFile;
	// menuBar group Help
	public JMenu mnNewMenu_6;
	public JMenuItem mntmNewMenuItem_20;
	public JMenuItem mntmNewMenuItem_21;

	// eventHalder object
	public EventHandlers eventHandlers;

	public JTextArea textAreaIn;
	public JTextArea textAreaOut;

	public JButton btnTranslate;
	public JButton btnSpeak;
	public JButton btnNewButton;
	public JButton btnNewButton_1;

	public static JTabbedPane tabbedPaneGraphs;

	public JSpinner spampF;

	public MainWindow() {
		frameInit();
		menuBarInit();
		bodyInit();
		eventHandlers = new EventHandlers(this);
		eventHandlers.initHandlers();
		PhonemeLoad.phonemeListInit();
	}

	public static void main(String[] args) {
		tfFreq = new ArrayList<JTextField>();
		tfHeight = new ArrayList<JTextField>();
		tfWidth = new ArrayList<JTextField>();
		tfBw = new ArrayList<JTextField>();
		tfAp = new ArrayList<JTextField>();
		tfBp = new ArrayList<JTextField>();
		MainWindow mainW = new MainWindow();
		mainW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainW.setTitle("Espeak Jedit");
		mainW.setSize(new Dimension(1000, 600));
		mainW.setVisible(true);
	}

	private void menuBarInit() {
		menuBar = new JMenuBar();

		// Group File
		mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		mntmNewMenuItem = new JMenuItem("Open...");
		mnNewMenu.add(mntmNewMenuItem);

		mntmOpen = new JMenuItem("Open2...");
		mnNewMenu.add(mntmOpen);

		mntmSave = new JMenuItem("Save");
		mntmSave.setVisible(false);
		mnNewMenu.add(mntmSave);

		mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.setVisible(false);
		mnNewMenu.add(mntmSaveAs);

		mntmClose = new JMenuItem("Close");
		mntmClose.setVisible(false);
		mnNewMenu.add(mntmClose);
		
		mntmCloseAll = new JMenuItem("Close all");
		mntmCloseAll.setVisible(false);
		mnNewMenu.add(mntmCloseAll);

		mntmQuit = new JMenuItem("Quit");
		mnNewMenu.add(mntmQuit);

		// Group Speak
		mnNewMenu_1 = new JMenu("Speak");
		menuBar.add(mnNewMenu_1);

		mntmNewMenuItem_1 = new JMenuItem("Translate");
		mnNewMenu_1.add(mntmNewMenuItem_1);

		mntmShowRules = new JMenuItem("Show Rules");
		mnNewMenu_1.add(mntmShowRules);

		mntmNewMenuItem_2 = new JMenuItem("Show IPA");
		mnNewMenu_1.add(mntmNewMenuItem_2);

		mntmNewMenuItem_3 = new JMenuItem("Speak");
		mnNewMenu_1.add(mntmNewMenuItem_3);

		JSeparator separator = new JSeparator();
		mnNewMenu_1.add(separator);

		mntmNewMenuItem_4 = new JMenuItem("Speak file...");
		mnNewMenu_1.add(mntmNewMenuItem_4);

		mntmNewMenuItem_5 = new JMenuItem("Pause");
		mnNewMenu_1.add(mntmNewMenuItem_5);

		mntmNewMenuItem_6 = new JMenuItem("Stop");
		mnNewMenu_1.add(mntmNewMenuItem_6);

		// Group VoiceInterfaceHandlers
		mnNewMenu_2 = new JMenu("Voice");
		menuBar.add(mnNewMenu_2);

		mntmNewMenuItem_7 = new JMenuItem("Select Voice...");
		mnNewMenu_2.add(mntmNewMenuItem_7);

		mntmNewMenuItem_8 = new JMenuItem("Select Voice Variant...");
		mnNewMenu_2.add(mntmNewMenuItem_8);

		JSeparator separator_5 = new JSeparator();
		mnNewMenu_2.add(separator_5);

		mnNewMenu_7 = new JMenu("Select Voice ");
		mnNewMenu_2.add(mnNewMenu_7);

		rdbtnmntmEnglish = new JRadioButtonMenuItem("English");
		rdbtnmntmEnglish.setSelected(true);
		mnNewMenu_7.add(rdbtnmntmEnglish);

		rdbtnmntmRussian = new JRadioButtonMenuItem("Russian");
		mnNewMenu_7.add(rdbtnmntmRussian);

		rdbtnmntmLatvian = new JRadioButtonMenuItem("Latvian");
		mnNewMenu_7.add(rdbtnmntmLatvian);

		rdbtnmntmPolish = new JRadioButtonMenuItem("Polish");
		mnNewMenu_7.add(rdbtnmntmPolish);

		groupOfVoices = new ButtonGroup();
		groupOfVoices.add(rdbtnmntmEnglish);
		groupOfVoices.add(rdbtnmntmRussian);
		groupOfVoices.add(rdbtnmntmLatvian);
		groupOfVoices.add(rdbtnmntmPolish);

		// Group Options
		mnNewMenu_3 = new JMenu("Options");
		menuBar.add(mnNewMenu_3);

		mnNewMenu_4 = new JMenu("Set paths");
		mnNewMenu_3.add(mnNewMenu_4);

		mntmNewMenuItem_9 = new JMenuItem("Master phonemes file...");
		mnNewMenu_4.add(mntmNewMenuItem_9);

		mntmNewMenuItem_10 = new JMenuItem("Phoneme data source...");
		mnNewMenu_4.add(mntmNewMenuItem_10);

		mntmNewMenuItem_11 = new JMenuItem("Dictionary data source...");
		mnNewMenu_4.add(mntmNewMenuItem_11);

		mntmNewMenuItem_12 = new JMenuItem("SyntheInterfaceHandlerssized sound wAV file...");
		mnNewMenu_4.add(mntmNewMenuItem_12);

		JSeparator separator_1 = new JSeparator();
		mnNewMenu_4.add(separator_1);

		mntmNewMenuItem_13 = new JMenuItem("Voice file to modify formant peaks...");
		mnNewMenu_4.add(mntmNewMenuItem_13);

		mnNewMenu_8 = new JMenu("Language");
		mnNewMenu_3.add(mnNewMenu_8);

		mntmEnglish = new JMenuItem("English");
		mnNewMenu_8.add(mntmEnglish);

		mntmLatvian = new JMenuItem("Latvian");
		mnNewMenu_8.add(mntmLatvian);

		mntmRussian = new JMenuItem("Russian");
		mnNewMenu_8.add(mntmRussian);

		mntmSpeed = new JMenuItem("Speed...");
		mnNewMenu_3.add(mntmSpeed);
		optionsSpeed = new OptionsSpeedWindow();

		JSeparator separator_2 = new JSeparator();
		mnNewMenu_3.add(separator_2);

		mntmSpeakPunctuation = new JMenuItem("Speak punctuation");
		mnNewMenu_3.add(mntmSpeakPunctuation);

		mntmSpeakCharacters = new JMenuItem("Speak characters");
		mnNewMenu_3.add(mntmSpeakCharacters);

		mntmSpeakCharacterName = new JMenuItem("Speak character name");
		mnNewMenu_3.add(mntmSpeakCharacterName);

		// Group Tools
		mnTools = new JMenu("Tools");
		menuBar.add(mnTools);

		mnMakeVowelsChart = new JMenu("Make Vowels Chart");
		mnTools.add(mnMakeVowelsChart);

		mntmFromCompiledPhoneme = new JMenuItem("From compiled phoneme data");
		mnMakeVowelsChart.add(mntmFromCompiledPhoneme);

		mntmNewMenuItem_14 = new JMenuItem("From directory of vowel files...");
		mnMakeVowelsChart.add(mntmNewMenuItem_14);

		mnNewMenu_5 = new JMenu("Process Lexicon");
		mnTools.add(mnNewMenu_5);

		mntmNewMenuItem_15 = new JMenuItem("Russian");
		mnNewMenu_5.add(mntmNewMenuItem_15);

		mntmNewMenuItem_16 = new JMenuItem("Bulgarian");
		mnNewMenu_5.add(mntmNewMenuItem_16);

		mntmNewMenuItem_17 = new JMenuItem("German");
		mnNewMenu_5.add(mntmNewMenuItem_17);

		mntmNewMenuItem_18 = new JMenuItem("Italian");
		mnNewMenu_5.add(mntmNewMenuItem_18);

		mntmNewMenuItem_19 = new JMenuItem("Convert file to UTF8...");
		mnTools.add(mntmNewMenuItem_19);

		mntmCountWordFrequencies = new JMenuItem("Count word frequencies...");
		mnTools.add(mntmCountWordFrequencies);

		mntmTesttemporary = new JMenuItem("Test (temporary)");
		mnTools.add(mntmTesttemporary);

		// Group Compile
		mnCompile = new JMenu("Compile");
		menuBar.add(mnCompile);

		mntmCompileDictionary = new JMenuItem("Compile dictionary\"");
		mnCompile.add(mntmCompileDictionary);

		mntmCompileDictionarydebug = new JMenuItem("Compile dictionary (debug)");
		mnCompile.add(mntmCompileDictionarydebug);

		mntmCompilePhonemeData = new JMenuItem("Compile phoneme data 22050HZ");
		mnCompile.add(mntmCompilePhonemeData);

		mntmCompileAtSample = new JMenuItem("Compile at sample rate");
		mnCompile.add(mntmCompileAtSample);

		JSeparator separator_3 = new JSeparator();
		mnCompile.add(separator_3);

		mntmCompileMbrolaPhonemes = new JMenuItem("Compile mbrola phonemes list...");
		mnCompile.add(mntmCompileMbrolaPhonemes);

		mntmCompileIntonationData = new JMenuItem("Compile intonation data");
		mnCompile.add(mntmCompileIntonationData);

		JSeparator separator_4 = new JSeparator();
		mnCompile.add(separator_4);

		mntmLayoutrulesFile = new JMenuItem("Layout '_rules' file");
		mnCompile.add(mntmLayoutrulesFile);

		mntmSortrulesFile = new JMenuItem("Sort '_rules' file");
		mnCompile.add(mntmSortrulesFile);

		// Group Help
		mnNewMenu_6 = new JMenu("Help");
		menuBar.add(mnNewMenu_6);

		mntmNewMenuItem_20 = new JMenuItem("eSpeak Documentation");
		mnNewMenu_6.add(mntmNewMenuItem_20);

		mntmNewMenuItem_21 = new JMenuItem("About");
		mnNewMenu_6.add(mntmNewMenuItem_21);

	}

	// Initiate frame body
	public void bodyInit() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		
		tabbedPaneGraphs = new JTabbedPane(JTabbedPane.TOP);
        tabbedPaneGraphs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        JScrollPane scrollPane = new JScrollPane(tabbedPaneGraphs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
 
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
                .addGroup(groupLayout.createSequentialGroup()
 
 
 
                    .addGap(12)
                    .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
 
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                        .addComponent(tabbedPane))
                    .addGap(0))
        );
		JPanel panel_Spect = new JPanel();
		panel_Spect.setToolTipText("Spect");
		tabbedPane.addTab("Spect", null, panel_Spect, null);

		JLabel lblFormants = new JLabel("Formants");
		lblFormants.setBounds(6, 6, 67, 15);
		lblFormants.setFont(new Font("Dialog", Font.BOLD, 12));

		JLabel lblHt = new JLabel("Ht");
		lblHt.setBounds(85, 6, 16, 15);
		lblHt.setFont(new Font("Dialog", Font.BOLD, 12));

		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(115, 6, 42, 15);

		JLabel label = new JLabel("0");
		label.setBounds(6, 31, 8, 15);

		JLabel label_1 = new JLabel("1");
		label_1.setBounds(6, 53, 8, 15);

		JLabel label_2 = new JLabel("2");
		label_2.setBounds(6, 75, 8, 15);

		JLabel label_3 = new JLabel("3");
		label_3.setBounds(6, 97, 8, 15);

		JLabel label_4 = new JLabel("4");
		label_4.setBounds(6, 119, 8, 15);

		JLabel label_5 = new JLabel("5");
		label_5.setBounds(6, 141, 8, 15);

		JLabel label_6 = new JLabel("6");
		label_6.setBounds(6, 163, 8, 15);

		JLabel label_7 = new JLabel("7");
		label_7.setBounds(6, 185, 8, 15);

		JLabel lblklatt = new JLabel("(Klatt)");
		lblklatt.setBounds(231, 6, 44, 15);

		JTextField tfFreq0 = new JTextField();
		tfFreq0.setBounds(26, 27, 50, 23);
		tfFreq0.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq0.setColumns(10);
		tfFreq.add(tfFreq0);

		JTextField tfFreq1 = new JTextField();
		tfFreq1.setBounds(26, 49, 50, 23);
		tfFreq1.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq1.setColumns(10);
		tfFreq.add(tfFreq1);

		JTextField tfFreq2 = new JTextField();
		tfFreq2.setBounds(26, 71, 50, 23);
		tfFreq2.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq2.setColumns(10);
		tfFreq.add(tfFreq2);

		JTextField tfFreq3 = new JTextField();
		tfFreq3.setBounds(26, 93, 50, 23);
		tfFreq3.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq3.setColumns(10);
		tfFreq.add(tfFreq3);

		JTextField tfFreq4 = new JTextField();
		tfFreq4.setBounds(26, 115, 50, 23);
		tfFreq4.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq4.setColumns(10);
		tfFreq.add(tfFreq4);

		JTextField tfFreq5 = new JTextField();
		tfFreq5.setBounds(26, 137, 50, 23);
		tfFreq5.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq5.setColumns(10);
		tfFreq.add(tfFreq5);

		JTextField tfFreq6 = new JTextField();
		tfFreq6.setBounds(26, 159, 50, 23);
		tfFreq6.setHorizontalAlignment(SwingConstants.CENTER);
		tfFreq6.setColumns(10);
		tfFreq.add(tfFreq6);

		JTextField tfHeight0 = new JTextField();
		tfHeight0.setBounds(77, 27, 30, 23);
		tfHeight0.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight0.setColumns(10);
		tfHeight.add(tfHeight0);

		JTextField tfHeight1 = new JTextField();
		tfHeight1.setBounds(77, 49, 30, 23);
		tfHeight1.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight1.setColumns(10);
		tfHeight.add(tfHeight1);

		JTextField tfHeight2 = new JTextField();
		tfHeight2.setBounds(77, 71, 30, 23);
		tfHeight2.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight2.setColumns(10);
		tfHeight.add(tfHeight2);

		JTextField tfHeight3 = new JTextField();
		tfHeight3.setBounds(77, 93, 30, 23);
		tfHeight3.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight3.setColumns(10);
		tfHeight.add(tfHeight3);

		JTextField tfHeight4 = new JTextField();
		tfHeight4.setBounds(77, 115, 30, 23);
		tfHeight4.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight4.setColumns(10);
		tfHeight.add(tfHeight4);

		JTextField tfHeight5 = new JTextField();
		tfHeight5.setBounds(77, 137, 30, 23);
		tfHeight5.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight5.setColumns(10);
		tfHeight.add(tfHeight5);

		JTextField tfHeight6 = new JTextField();
		tfHeight6.setBounds(77, 159, 30, 23);
		tfHeight6.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight6.setColumns(10);
		tfHeight.add(tfHeight6);

		JTextField tfHeight7 = new JTextField();
		tfHeight7.setBounds(77, 181, 30, 23);
		tfHeight7.setHorizontalAlignment(SwingConstants.CENTER);
		tfHeight7.setColumns(10);
		tfHeight.add(tfHeight7);

		JTextField tfWidth0 = new JTextField();
		tfWidth0.setBounds(108, 27, 56, 23);
		tfWidth0.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth0.setColumns(10);
		tfWidth.add(tfWidth0);

		JTextField tfWidth1 = new JTextField();
		tfWidth1.setBounds(108, 49, 56, 23);
		tfWidth1.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth1.setColumns(10);
		tfWidth.add(tfWidth1);

		JTextField tfWidth2 = new JTextField();
		tfWidth2.setBounds(108, 71, 56, 23);
		tfWidth2.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth2.setColumns(10);
		tfWidth.add(tfWidth2);

		JTextField tfWidth3 = new JTextField();
		tfWidth3.setBounds(108, 93, 56, 23);
		tfWidth3.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth3.setColumns(10);
		tfWidth.add(tfWidth3);

		JTextField tfWidth4 = new JTextField();
		tfWidth4.setBounds(108, 115, 56, 23);
		tfWidth4.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth4.setColumns(10);
		tfWidth.add(tfWidth4);

		JTextField tfWidth5 = new JTextField();
		tfWidth5.setBounds(108, 137, 56, 23);
		tfWidth5.setHorizontalAlignment(SwingConstants.CENTER);
		tfWidth5.setColumns(10);
		tfWidth.add(tfWidth5);

		JLabel lblBw = new JLabel("Bw");
		lblBw.setBounds(196, 31, 21, 15);
		lblBw.setFont(new Font("Dialog", Font.BOLD, 12));

		JLabel lblAt = new JLabel("Ap");
		lblAt.setBounds(230, 31, 18, 15);
		lblAt.setFont(new Font("Dialog", Font.BOLD, 12));

		JLabel lblBp = new JLabel("Bp");
		lblBp.setBounds(263, 31, 18, 15);
		lblBp.setFont(new Font("Dialog", Font.BOLD, 12));

		JTextField tfBw1 = new JTextField();
		tfBw1.setBounds(190, 45, 33, 23);
		tfBw1.setHorizontalAlignment(SwingConstants.CENTER);
		tfBw1.setColumns(10);
		tfBw.add(tfBw1);

		JTextField tfBw2 = new JTextField();
		tfBw2.setBounds(190, 66, 33, 23);
		tfBw2.setHorizontalAlignment(SwingConstants.CENTER);
		tfBw2.setColumns(10);
		tfBw.add(tfBw2);

		JTextField tfBw3 = new JTextField();
		tfBw3.setBounds(190, 88, 33, 23);
		tfBw3.setHorizontalAlignment(SwingConstants.CENTER);
		tfBw3.setColumns(10);
		tfBw.add(tfBw3);
		
		JTextField tfAp1 = new JTextField();
		tfAp1.setBounds(224, 45, 33, 23);
		tfAp1.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp1.setColumns(10);
		tfAp.add(tfAp1);

		JTextField tfAp2 = new JTextField();
		tfAp2.setBounds(224, 66, 33, 23);
		tfAp2.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp2.setColumns(10);
		tfAp.add(tfAp2);

		JTextField tfAp3 = new JTextField();
		tfAp3.setBounds(224, 88, 33, 23);
		tfAp3.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp3.setColumns(10);
		tfAp.add(tfAp3);

		JTextField tfAp4 = new JTextField();
		tfAp4.setBounds(224, 109, 33, 23);
		tfAp4.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp4.setColumns(10);
		tfAp.add(tfAp4);

		JTextField tfAp5 = new JTextField();
		tfAp5.setBounds(224, 129, 33, 23);
		tfAp5.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp5.setColumns(10);
		tfAp.add(tfAp5);

		JTextField tfAp6 = new JTextField();
		tfAp6.setBounds(224, 149, 33, 23);
		tfAp6.setHorizontalAlignment(SwingConstants.CENTER);
		tfAp6.setColumns(10);
		tfAp.add(tfAp6);

		JTextField tfBp1 = new JTextField();
		tfBp1.setBounds(258, 45, 33, 23);
		tfBp1.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp1.setColumns(10);
		tfBp.add(tfBp1);
		
		JTextField tfBp2 = new JTextField();
		tfBp2.setBounds(258, 66, 33, 23);
		tfBp2.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp2.setColumns(10);
		tfBp.add(tfBp2);

		JTextField tfBp3 = new JTextField();
		tfBp3.setBounds(258, 88, 33, 23);
		tfBp3.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp3.setColumns(10);
		tfBp.add(tfBp3);

		JTextField tfBp4 = new JTextField();
		tfBp4.setBounds(258, 109, 33, 23);
		tfBp4.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp4.setColumns(10);
		tfBp.add(tfBp4);

		JTextField tfBp5 = new JTextField();
		tfBp5.setBounds(258, 129, 33, 23);
		tfBp5.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp5.setColumns(10);
		tfBp.add(tfBp5);
		
		JTextField tfBp6 = new JTextField();
		tfBp6.setBounds(258, 149, 33, 23);
		tfBp6.setHorizontalAlignment(SwingConstants.CENTER);
		tfBp6.setColumns(10);
		tfBp.add(tfBp6);

		tfmS = new JTextField();
		tfmS.setBounds(6, 212, 56, 25);
		tfmS.setHorizontalAlignment(SwingConstants.CENTER);
		tfmS.setColumns(10);

		JLabel lblMs = new JLabel("mS");
		lblMs.setBounds(63, 217, 21, 15);
		spampF = new JSpinner();
		spampF.setBounds(116, 212, 56, 25);
		spampF.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JLabel lblAmp = new JLabel("% amp - Frame");
		lblAmp.setBounds(172, 212, 103, 25);

		JSpinner spAV = new JSpinner();
		spAV.setBounds(6, 243, 56, 25);
		spAV.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spTilt = new JSpinner();
		spTilt.setBounds(6, 274, 56, 25);
		spTilt.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spAVp = new JSpinner();
		spAVp.setBounds(6, 305, 56, 25);
		spAVp.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spkopen = new JSpinner();
		spkopen.setBounds(6, 336, 56, 25);
		spkopen.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spFNZ = new JSpinner();
		spFNZ.setBounds(116, 243, 56, 25);
		spFNZ.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spAspr = new JSpinner();
		spAspr.setBounds(116, 274, 56, 25);
		spAspr.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spFric = new JSpinner();
		spFric.setBounds(116, 305, 56, 25);
		spFric.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spTurb = new JSpinner();
		spTurb.setBounds(116, 336, 56, 25);
		spTurb.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spSkew = new JSpinner();
		spSkew.setBounds(226, 274, 56, 25);
		spSkew.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JSpinner spFricBP = new JSpinner();
		spFricBP.setBounds(226, 305, 56, 25);
		spFricBP.setModel(new SpinnerNumberModel(0, 0, 500, 1));

		JLabel lblTilt = new JLabel("Tilt");
		lblTilt.setBounds(63, 279, 22, 15);
		lblTilt.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblAvp = new JLabel("AVp");
		lblAvp.setBounds(63, 310, 27, 15);
		lblAvp.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Spect.setLayout(null);
		panel_Spect.add(lblFormants);
		panel_Spect.add(label);
		panel_Spect.add(label_1);
		panel_Spect.add(label_2);
		panel_Spect.add(label_3);
		panel_Spect.add(label_4);
		panel_Spect.add(label_5);
		panel_Spect.add(label_6);
		panel_Spect.add(label_7);
		panel_Spect.add(tfFreq0);
		panel_Spect.add(tfFreq1);
		panel_Spect.add(tfFreq2);
		panel_Spect.add(tfFreq3);
		panel_Spect.add(tfFreq4);
		panel_Spect.add(tfFreq5);
		panel_Spect.add(tfFreq6);
		panel_Spect.add(tfHeight7);
		panel_Spect.add(tfHeight6);
		panel_Spect.add(tfHeight5);
		panel_Spect.add(tfWidth5);
		panel_Spect.add(tfHeight4);
		panel_Spect.add(tfWidth4);
		panel_Spect.add(lblHt);
		panel_Spect.add(lblWidth);
		panel_Spect.add(lblklatt);
		panel_Spect.add(tfHeight0);
		panel_Spect.add(tfWidth0);
		panel_Spect.add(tfHeight1);
		panel_Spect.add(tfWidth1);
		panel_Spect.add(tfHeight2);
		panel_Spect.add(tfWidth2);
		panel_Spect.add(tfHeight3);
		panel_Spect.add(tfWidth3);
		panel_Spect.add(tfBw2);
		panel_Spect.add(tfAp2);
		panel_Spect.add(tfBp2);
		panel_Spect.add(tfBw1);
		panel_Spect.add(tfAp1);
		panel_Spect.add(tfBp1);
		panel_Spect.add(lblBw);
		panel_Spect.add(lblAt);
		panel_Spect.add(lblBp);
		panel_Spect.add(tfBw3);
		panel_Spect.add(tfAp4);
		panel_Spect.add(tfBp4);
		panel_Spect.add(tfAp3);
		panel_Spect.add(tfBp3);
		panel_Spect.add(tfAp5);
		panel_Spect.add(tfBp5);
		panel_Spect.add(tfAp6);
		panel_Spect.add(tfBp6);

		JLabel lblAv = new JLabel("AV");
		lblAv.setBounds(63, 247, 44, 15);
		lblAv.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Spect.add(lblAv);
		panel_Spect.add(spSkew);
		panel_Spect.add(spFricBP);
		panel_Spect.add(tfmS);
		panel_Spect.add(lblMs);
		panel_Spect.add(spkopen);
		panel_Spect.add(spTilt);
		panel_Spect.add(lblTilt);
		panel_Spect.add(spAV);
		panel_Spect.add(spAVp);
		panel_Spect.add(lblAvp);
		panel_Spect.add(spampF);
		panel_Spect.add(lblAmp);
		panel_Spect.add(spAspr);
		panel_Spect.add(spFric);
		panel_Spect.add(spFNZ);

		JLabel lblKopen = new JLabel("kopen");
		lblKopen.setBounds(63, 341, 44, 15);
		lblKopen.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Spect.add(lblKopen);
		panel_Spect.add(spTurb);

		JLabel lblFnz = new JLabel("FNZ");
		lblFnz.setHorizontalAlignment(SwingConstants.LEFT);
		lblFnz.setBounds(176, 247, 44, 15);
		panel_Spect.add(lblFnz);

		JLabel lblAspr = new JLabel("Aspr");
		lblAspr.setHorizontalAlignment(SwingConstants.LEFT);
		lblAspr.setBounds(176, 279, 44, 15);
		panel_Spect.add(lblAspr);

		JLabel lblFric = new JLabel("Fric");
		lblFric.setHorizontalAlignment(SwingConstants.LEFT);
		lblFric.setBounds(176, 310, 44, 15);
		panel_Spect.add(lblFric);

		JLabel lblTurb = new JLabel("Turb");
		lblTurb.setHorizontalAlignment(SwingConstants.LEFT);
		lblTurb.setBounds(176, 341, 44, 15);
		panel_Spect.add(lblTurb);

		JLabel lblSkew = new JLabel("Skew");
		lblSkew.setHorizontalAlignment(SwingConstants.LEFT);
		lblSkew.setBounds(284, 279, 44, 15);
		panel_Spect.add(lblSkew);

		JLabel lblFricbp = new JLabel("FricBP");
		lblFricbp.setHorizontalAlignment(SwingConstants.LEFT);
		lblFricbp.setBounds(284, 310, 44, 15);
		panel_Spect.add(lblFricbp);

		JButton btnZoom = new JButton("Zoom-");
		btnZoom.setBounds(12, 405, 97, 25);
		panel_Spect.add(btnZoom);

		JButton btnZoom_1 = new JButton("Zoom+");
		btnZoom_1.setBounds(120, 405, 97, 25);
		panel_Spect.add(btnZoom_1);

		JSpinner spampS = new JSpinner();
		spampS.setModel(new SpinnerNumberModel(0, 0, 596, 1));
		spampS.setBounds(6, 450, 56, 25);
		panel_Spect.add(spampS);

		JLabel lblAmp_1 = new JLabel("% amp - Sequence");
		lblAmp_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblAmp_1.setBounds(62, 450, 139, 25);
		panel_Spect.add(lblAmp_1);

		JLabel label_8 = new JLabel("mS");
		label_8.setBounds(63, 473, 21, 15);
		panel_Spect.add(label_8);

		JPanel panel_text = new JPanel();
		panel_text.setAutoscrolls(true);
		panel_text.setToolTipText("Text");
		tabbedPane.addTab("Text", null, panel_text, null);

		btnTranslate = new JButton("Translate");
		btnSpeak = new JButton("Speak");
		btnNewButton = new JButton("Show Rules");
		btnNewButton_1 = new JButton("Show IPA");

		textAreaIn = new JTextArea();
		textAreaIn.setLineWrap(true);

		JScrollPane scrollPaneTextAreaIn = new JScrollPane(textAreaIn, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		textAreaOut = new JTextArea();
		textAreaOut.setLineWrap(true);
		JScrollPane scrollPaneTextAreaOut = new JScrollPane(textAreaOut, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		GroupLayout gl_panel_text = new GroupLayout(panel_text);
		gl_panel_text.setHorizontalGroup(gl_panel_text.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_text
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_text.createParallelGroup(Alignment.LEADING).addComponent(scrollPaneTextAreaOut)
						.addComponent(scrollPaneTextAreaIn, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_text.createSequentialGroup()
								.addGroup(gl_panel_text.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnTranslate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnNewButton))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_text.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnSpeak, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNewButton_1, Alignment.LEADING))))
				.addContainerGap()));
		gl_panel_text.setVerticalGroup(gl_panel_text.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_text.createSequentialGroup().addContainerGap()
						.addComponent(scrollPaneTextAreaIn, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneTextAreaOut, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE).addGap(20)
						.addGroup(gl_panel_text.createParallelGroup(Alignment.BASELINE).addComponent(btnTranslate)
								.addComponent(btnSpeak))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_text.createParallelGroup(Alignment.BASELINE).addComponent(btnNewButton)
								.addComponent(btnNewButton_1))
						.addGap(54)));

		panel_text.setLayout(gl_panel_text);
		getContentPane().setLayout(groupLayout);
	}
}