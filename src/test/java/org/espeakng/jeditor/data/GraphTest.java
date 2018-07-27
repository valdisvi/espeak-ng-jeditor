package org.espeakng.jeditor.data;


import java.awt.Graphics;
import java.util.ArrayList;

import org.espeakng.jeditor.jni.SpectFrame;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class GraphTest {
	static ArrayList<Frame> frameList;
	static Graph g ;
	static Graphics graphics;
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);
	
	
	
	 @BeforeClass
	   public static void beforeClass() {
		 SpectFrame frames = new SpectFrame();
		 frameList = new ArrayList<Frame>();
		 frameList.add(new Frame());
		 frameList.add(new Frame());
		 
		 frameList.get(0).frameLoader(frames, 1, 10.2, 3);
	   }
	 @Test
	 public void test(){

	 }
	


}
