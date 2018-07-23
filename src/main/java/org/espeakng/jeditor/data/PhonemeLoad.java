package org.espeakng.jeditor.data;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import org.espeakng.jeditor.gui.MainWindow;
/**
 * This class is used to load phoneme into program and add it to and ArrayList
 */
public class PhonemeLoad {	
	private static ArrayList <Phoneme> phonemeList;
	/**
	 * Creates new Phoneme object passing to constructor given file and adds it to and ArrayList
	 * @param file - file to be opened loaded
	 * @param mainW - Main Window
	 */
	public static void phonemeOpen(File file, MainWindow mainW){
		
		Phoneme newPhoneme=new Phoneme(file);
		phonemeList.add(newPhoneme);
	}
	/**
	 * Initializing ArrayList of phonemes
	 */
	public static void phonemeListInit(){
		phonemeList=new ArrayList<Phoneme>();
	}
	/**
	 * Method used to get Phoneme object, that is being represented by passed jPanel
	 * @param jPanel representing Phoneme
	 * @return Phoneme that is being represented by given jPanel
	 */
	public static Phoneme getSelectedPhoneme(JScrollPane jPanel){
		for(Phoneme phoneme: phonemeList){
			if(phoneme.getGraph().getjPanelOfGraph().equals(jPanel)){
				return phoneme;
			}
		}
		return null;
	}
	/**
	 * Misleading method name, not sure where it is used and why. (maybe should be refactored to different name)
	 * But it loads first frame of given jScrollPane
	 * @param jScrollPane whose frame is to be loaded
	 */
	public static void getPhoneme(JScrollPane jScrollPane){
		for(Phoneme phoneme: phonemeList){
			if(phoneme.getGraph().getjPanelOfGraph().equals(jScrollPane))
				phoneme.loadFirstFrame();
		}
	}
	/**
	 * Zooming in using jScrollPane object as argument
	 * @param jScrollPane to be zoomed in
	 */
	public static void zoomIn(JScrollPane jScrollPane){
		for(Phoneme phoneme: phonemeList){
			if(phoneme.getGraph().getjPanelOfGraph().equals(jScrollPane))
				phoneme.doZoomIn();
		}
	}
	/**
	 * Zooming out using jScrollPane object as argument
	 * @param jScrollPane to be zoomed out
	 */
	public static void zoomOut(JScrollPane jScrollPane){
		for(Phoneme phoneme: phonemeList){
			if(phoneme.getGraph().getjPanelOfGraph().equals(jScrollPane))
				phoneme.doZoomOut();
		}
	}
}
