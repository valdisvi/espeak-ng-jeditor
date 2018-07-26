package org.espeakng.jeditor.data;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
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
		 
	  //    g = new Graph("Graph", frameList);
	   }
	 @Test
	 public void test(){

	 }
	


}
