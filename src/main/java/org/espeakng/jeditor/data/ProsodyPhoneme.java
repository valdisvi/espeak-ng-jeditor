package org.espeakng.jeditor.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProsodyPhoneme implements Serializable {

	private static final long serialVersionUID = -8750162848955429411L;
	private String name;
	private int duration;
	private Map<Integer, Integer> frequencies;
	
	/**
	 * @param name
	 * @param duration
	 * @param frequencies
	 */
	public ProsodyPhoneme(String name, int duration, Map<Integer, Integer> frequencies) {
		this.name = name;
		this.duration = duration;
		this.frequencies = frequencies;
	}
	
	/**
	 * @param name
	 * @param duration
	 */
	public ProsodyPhoneme(String name, int duration) {
		this.name = name;
		this.duration = duration;
		this.frequencies = new HashMap<>();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Map<Integer, Integer> getFrequencies() {
		return frequencies;
	}

	public void setFrequencies(Map<Integer, Integer> frequencies) {
		this.frequencies = frequencies;
	}

}
