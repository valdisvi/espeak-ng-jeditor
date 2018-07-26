package org.espeakng.jeditor.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.espeakng.jeditor.data.ProsodyPhoneme;

public class Utilities {
	
	private Utilities() {
		throw new IllegalStateException("Utility class");
	}

	public static List<ProsodyPhoneme> getProsodyData(String data) {
		String[] dataRow = data.split("\\n");
		ArrayList<ProsodyPhoneme> prosodyPhonemes = new ArrayList<>();
		
		for (int i = 0; i < dataRow.length; i++) {
			String[] tempData = dataRow[i].split("\\t");
			
			if (tempData[0].equals("_")) continue;
			
			if (tempData.length == 2)
				prosodyPhonemes.add(new ProsodyPhoneme(tempData[0], Integer.parseInt(tempData[1])));
			
			if (tempData.length == 3) {
        		String[] pitchData = tempData[2].split("\\s+");
				
        		Map<Integer, Integer> frequencies = new TreeMap<>();
        		for (int j = pitchData[0].isEmpty() ? 1 : 0; j < pitchData.length; j += 2) {
        			frequencies.put(Integer.parseInt(pitchData[j]), Integer.parseInt(pitchData[j + 1]));        			
        		}
        		
        		prosodyPhonemes.add(new ProsodyPhoneme(tempData[0], Integer.parseInt(tempData[1]), frequencies));
			}
		}
		
		return prosodyPhonemes;
	}

}
