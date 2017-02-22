package dataStructure;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import interfacePckg.MainWindow;

public class PhonemeLoad {
	private static ArrayList <Phoneme> phonemeList;
	
	public static void phonemeOpen(File file, MainWindow mainW){
		
		Phoneme newPhoneme=new Phoneme(file);
		phonemeList.add(newPhoneme);
		//outputPhonemes();
	}
	
	public static void phonemeListInit(){
		phonemeList=new ArrayList<Phoneme>();
	}
	
	public static void getPhoneme(JScrollPane jScrollPane){
		for(Phoneme phoneme: phonemeList){
			if(phoneme.getGraph().getjPanelOfGraph().equals(jScrollPane))
				phoneme.loadFirstFrame();
		}
	}
	public static void zoomIn(JScrollPane jPanel){
		for(Phoneme phoneme: phonemeList){
			if(phoneme.getGraph().getjPanelOfGraph().equals(jPanel))
				phoneme.doZoomIn();
		}
	}
	public static void zoomOut(JScrollPane jScrollPane){
		for(Phoneme phoneme: phonemeList){
			if(phoneme.getGraph().getjPanelOfGraph().equals(jScrollPane))
				phoneme.doZoomOut();
		}
	}
	
//	public static void outputPhonemes(){
//		System.out.println(phonemeList.size());
//	}
	
}
