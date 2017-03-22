package org.espeakng.jeditor.jni;

import java.io.File;

import org.espeakng.jeditor.data.Phoneme;
import org.espeakng.jeditor.data.PhonemeLoad;
import org.espeakng.jeditor.data.PhonemeSave;
import org.junit.Test;

public class LoadSavePhonemesTest {
	@Test
	public void TestLoad() {
		PhonemeSave ps = new PhonemeSave();
		PhonemeLoad pl = new PhonemeLoad();

		File inFile = new File("/home/student/workspace/espeak-ng/phsource/vowel/0");
		if (!inFile.exists())
			System.out.println("input file doesn't exist.");
		else
			System.out.println("input file exists.");
		System.out.println("input file name is " + inFile.getName());

		Phoneme p = new Phoneme(inFile);

		/*
		 * CONSTRUCTOR
		 * Phoneme(String type, int file_format, int name_length, int n, int
		 * amplitude, int max_y, String fileName, ArrayList<Frame> frameList)
		 */

		// Phoneme p = new Phoneme("t_type", 0, 0, 0, 156, 99, "t_fileName", new ArrayList<Frame>());

		File outFile = new File("/home/student/Saved_phoneme");

		if (!outFile.exists())
			System.out.println("File doesn't exist.");
		else
			System.out.println("File exists.");

		// outFile.createNewFile();

		PhonemeSave.saveToDirectory(p, outFile);

	}

}
