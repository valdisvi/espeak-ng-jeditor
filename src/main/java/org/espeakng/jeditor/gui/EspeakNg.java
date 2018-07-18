package org.espeakng.jeditor.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;

import org.apache.log4j.Logger;

/**
 * The class utilizes functionality of espeak-ng program (which is run on
 * terminal) to either sound the text typed in the upper text area on "Text"
 * tab, or output required information to the lower text area on the same tab, -
 * according to command (see makeAction() method description down below).
 * 
 * Please see command buttons in MainWindow class (btnTranslate, btnSpeak,
 * btnShowRules, btnShowIPA, as well as mntmSpeakPunctuation and
 * mntmSpeakCharacters). These are assigned corresponding commands. As example,
 * choosing "Speak characters" menu item under the "Options" menu yields
 * "speakBySymbol" command.
 * 
 */

public class EspeakNg {
	private static Logger logger = Logger.getLogger(EspeakNg.class.getName());
	private MainWindow mainW;

	/** This file will hold the text typed in "Text" tab upper text area. */
	private File fileInput;

	/**
	 * This file will hold the text to be output in "Text" tab bottom text area.
	 * espeak-ng itself will provide output to this file.
	 */
	private File fileOutput;

	/**
	 * @wbp.parser.entryPoint
	 */
	
	/**
	 * Saves chosen voice variant
	 */
	private static String voiceVariant = "";
	
	public EspeakNg(MainWindow mainW) {
		this.mainW = mainW;
	}

	/**
	 * This method performs required action accordingly to passed command (see list below) and
	 * the text typed in the upper text area on the "Text" tab in the main
	 * interface window of the program.
	 * This method utilizes the espeak-ng program's functionality.
	 * 
	 * List of accepted commands: "translate", "showRules", "showIpa", "speak",
	 * "speakPunctuation", "speakBySymbol".
	 * 
	 * @param String command
	 */

	public void makeAction(String command) {

		// Create file and write to it text typed in upper text area on "Text"
		// tab:
		createFileInput(getText(command));

		// The following three commands do not require file for output (but the
		// rest of commands DO require output file):
		if (!(command.equals("speakPunc") || command.equals("speakBySymbol")|| command.equals("speakCharName") || command.equals("speak"))) {
			createFileOutput();
		}
		
		// Command for espeak-ng is constructed as required and executed on terminal:
		makeRunTimeAction(getRunTimeCommand(command));
		
		// No need for source file any more:
		fileInput.delete();
		
		// Read the espeak-ng written text fiel, and write this text to lower text area on "Text" tab:
		if (!(command.equals("speakPunc") || command.equals("speakBySymbol") || command.equals("speakCharName")   || command.equals("speak"))) {
			readOutputFile();
		}
	}

	/**
	 * This method constructs the command string for espeak-ng program (this
	 * command is further entered on terminal to run espeak-ng).
	 * 
	 * @param command ("translate", "speak", "showRules" etc.)
	 * @return command for espeak-ng. 
	 */
	public String getRunTimeCommand(String command) {

		String runTimeCommand = "";
		// Get pronunciation rules:
		String voice = getVoiceFromSelection();
		// Get value of speech speed:
		int speedVoice = mainW.optionsSpeed.getSpinnerValue();

		switch (command) {

		case "translate":
			runTimeCommand = "espeak-ng -q -v" + voice + " -x --phonout=" + fileOutput.getAbsolutePath() + " -f "
					+ fileInput.getAbsolutePath();
			break;
		case "showRules":
			runTimeCommand = "espeak-ng -q -v" + voice + " -X --phonout=" + fileOutput.getAbsolutePath() + " -f "
					+ fileInput.getAbsolutePath();
					 
			break;
		case "showIpa":
			runTimeCommand = "espeak-ng -q --ipa --phonout=" + fileOutput.getAbsolutePath() + " -f "
					+ fileInput.getAbsolutePath();
			
			break;
		case "speakPunc":
			runTimeCommand = "espeak-ng -v" + voice + " -s" + speedVoice + " --punct=',.;?<>@#$%^&*()\""
					+ mainW.textAreaIn.getText() + "\"";
			System.out.println(runTimeCommand);
			break;
		case "speakCharName":
			runTimeCommand = "espeak-ng -v" + voice + " -s" + speedVoice+ " --punct=',;?<>@#.$%^&*()\""
					+ mainW.textAreaIn.getText() + "\"";
			System.out.println(runTimeCommand);
			break;
		case "speakBySymbol":
			runTimeCommand = "espeak-ng -v" + voice + " -s" + speedVoice + " --punct=',;?<>@#.$%^&*()\""
					+ mainW.textAreaIn.getText() + "\"";
			System.out.println(runTimeCommand);
			break;
		default:
			break;
		}
		return runTimeCommand;
	}

	/**
	 * This method reads the contents of output file written by espeak-ng, and
	 * WRITES it to the lower text area of "Text" tab as well.
	 */
	private void readOutputFile() {
		try {
			BufferedReader bufferedReader;
			bufferedReader = new BufferedReader(new FileReader(fileOutput));

			String line = "";
			mainW.textAreaOut.setText(line);
			while ((line = bufferedReader.readLine()) != null) {
				mainW.textAreaOut.append(line + "\n");
			}

			bufferedReader.close();
			fileOutput.delete();
		} catch (FileNotFoundException e) {
			logger.warn(e);;
		} catch (IOException e) {
			logger.warn(e);
		}
	}

	/**
	 * This method makes an action in terminal according to the passed command.
	 * Depending on command ("translate", "speak", "show rules"...) espeak-ng
	 * program will either sound entered text or write something to output text
	 * area.
	 * 
	 * @param String
	 *            runTimeCommand
	 */
	private void makeRunTimeAction(String runTimeCommand) {

		Runtime rt = Runtime.getRuntime();
		try {
			Process p = rt.exec(runTimeCommand);
			p.waitFor();
		} catch (IOException e) {
			logger.warn(e);
		} catch (InterruptedException e) {
			logger.warn(e);
		}

	}

	/**
	 * This method creates file to write, and writes passed string to it. The
	 * file is used hold the text typed in the upper text area of "Text" tab.
	 * Further this file will be passed to espaek-ng program on terminal as a
	 * source.
	 */
	private void createFileInput(String text) {

		try {
			fileInput = new File("MyFile.txt");
			FileWriter fileWriter = new FileWriter(fileInput);
			fileWriter.write(text);
			fileWriter.close();

		} catch (IOException e) {
			logger.warn(e);
		}

	}

	/**
	 * This method creates file to store text to be output to lower text area on
	 * "Text" tab on program's main interface. espeak-ng program itself will
	 * provide the text for this file.
	 */
	private void createFileOutput() {
		fileOutput = new File("testOut.txt");
	}

	/**
	 * This method receives text typed in upper text area on "Text" tab in
	 * program's main interface, and returns it as string.
	 * 
	 * @param command
	 * @return String text
	 */
	public String getText(String command) {

		String text = mainW.textAreaIn.getText();
		// speakBySymbol - it means that we need to pronounce each word by letters
		if (command.equals("speakBySymbol")) {
			// A non-whitespace character
			Pattern noSpaces = Pattern.compile("\\s+");
			Matcher m = noSpaces.matcher(mainW.textAreaIn.getText());
			// after every character put space
			text = m.replaceAll("").replaceAll(".(?!$)", "$0 ");
			return text;
		} 
		
		if (command.equals("speakCharName")) {
			StringBuilder c = new StringBuilder();
		    
		    for (int i = 0; i < text.length(); i++) {
		        String character = String.valueOf(text.charAt(i));
		    	
		        // if text matches vowels and next character is not space then put space
		        if (character.matches("[AEIOUYaeiouy]+") && (text.charAt(i+1) != ' ')) {
		            c.append(text.charAt(i)+' ');
		        } else {
		            c.append(text.charAt(i));
		        }
		    }
		    
		    return c.toString();
		}
		
		if (command.equals("speakPunc")) {
		    StringBuilder sb = new StringBuilder();
		    String character;
		    
		    for (int i = 0; i < text.length(); i++) {
		    	character = String.valueOf(text.charAt(i));
		    	
		        if (character.matches("[a-zA-Z]+")) {
		            sb.append(' ');
		        } else {
		            sb.append(character);
		        }
		    }
		    
		    return sb.toString();
		}
		
		return text;
	}
	/**
	 * This method sets pronunciation rules depending on chosen voice (Voice ->
	 * Select Voice). It returns string "en", "ru", "lv" or "pl".
	 * 
	 */
	public String getVoiceFromSelection() {
		String text = "";

		// Get voice from selection
		// Voice -> Select Voice
		for (Enumeration<AbstractButton> buttons = mainW.groupOfVoices.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				text = button.getText();
				break;
			}
		}

		String voice = "en";
		if (text.equals("Russian"))
			voice = "ru";
		if (text.equals("Latvian"))
			voice = "lv";
		if (text.equals("Polish"))
			voice = "pl";

		if(!voiceVariant.equals(""))
			voice +="+"+voiceVariant;
		return voice;
	}

	public String getVoiceVariant() {
		return voiceVariant;
	}

	public void setVoiceVariant(String voiceVariant) {
		EspeakNg.voiceVariant = voiceVariant;
	}
	

}
