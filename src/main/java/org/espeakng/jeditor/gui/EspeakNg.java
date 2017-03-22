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

public class EspeakNg {

	private MainWindow mainW;
	private File fileInput;
	private File fileOutput;

	/**
	 * @wbp.parser.entryPoint
	 */
	public EspeakNg(MainWindow mainW) {
		this.mainW = mainW;
	}

	public void makeAction(String command) {

		//create file input and write text into it
		createFileInput(getText(command));

		//for this 3 command we don't need output file
		if (!(command.equals("speakPunctuation") || command
				.equals("speakBySymbol") ||  command
				.equals("speak")))
			createFileOutput();

		makeRunTimeAction(getRunTimeCommand(command));
		fileInput.delete();
		
		if (!(command.equals("speakPunctuation") || command
				.equals("speakBySymbol") ||  command
				.equals("speak")))
			readOutputFile();

	}

	//each command has own command line
	private String getRunTimeCommand(String command) {

		String runTimeCommand = "";
		String voice = getVoiceFromSelection();
		int speedVoice = mainW.optionsSpeed.getSpinnerValue();

		switch (command) {

		case "translate":
			runTimeCommand = "espeak-ng -q -v" + voice + " -x --phonout="
					+ fileOutput.getAbsolutePath() + " -f "
					+ fileInput.getAbsolutePath();
			break;
		case "speak":
			runTimeCommand = "espeak-ng -v" + voice + " -s" + speedVoice
					+ " -f " + fileInput.getAbsolutePath();
			break;
		case "showRules":
			runTimeCommand = "espeak-ng -q -v" + voice + " -X --phonout="
					+ fileOutput.getAbsolutePath() + " -f "
					+ fileInput.getAbsolutePath();
			break;
		case "showIpa":
			runTimeCommand = "espeak-ng -q --ipa --phonout="
					+ fileOutput.getAbsolutePath() + " -f "
					+ fileInput.getAbsolutePath();
			break;
		case "speakPunctuation":
			// --punct='<characters>' ,where <characters> is
			// symbols which espeaker won't ignore
			runTimeCommand = "espeak-ng -v" + voice + " -s" + speedVoice
					+ " --punct=',.;?<>@#$%^&*()' \""
					+ mainW.textAreaIn.getText() + "\"";
			break;
		case "speakBySymbol":
			runTimeCommand = "espeak-ng -v" + voice + " -s" + speedVoice
					+ " -g 12 -f " + fileInput.getAbsolutePath();
			break;
		default:
			break;
		}
		return runTimeCommand;
	}

	//read out file and write it to textAreaOut
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//make an action in terminal according to the passing command
	private void makeRunTimeAction(String runTimeCommand) {

		Runtime rt = Runtime.getRuntime();
		try {
			Process p = rt.exec(runTimeCommand);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	//create file input and write text into it
	private void createFileInput(String text) {

		try {

			fileInput = new File("MyFile.txt");
			FileWriter fileWriter = new FileWriter(fileInput);
			fileWriter.write(text);
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createFileOutput() {
		fileOutput = new File("testOut.txt");
	}

	//get text from main interface
	private String getText(String command) {

		String text = "";
		//speakBySymbol - it's means that we need to pronounce each word by letters
		if (command.equals("speakBySymbol")) {
			// A non-whitespace character
			Pattern noSpaces = Pattern.compile("\\s+");
			Matcher m = noSpaces.matcher(mainW.textAreaIn.getText());
			// after every character put space
			text = m.replaceAll("").replaceAll(".(?!$)", "$0 ");
		} else {
			//get full
			text = mainW.textAreaIn.getText();
		}
		return text;
	}

	//set the pronunciation rules 
	private String getVoiceFromSelection() {

		String text = "";
		
		// get voice from selection
		for (Enumeration<AbstractButton> buttons = mainW.groupOfVoices
				.getElements(); buttons.hasMoreElements();) {
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

		return voice;
	}

}
