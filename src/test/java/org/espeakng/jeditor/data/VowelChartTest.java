package org.espeakng.jeditor.data;

import static org.junit.Assert.*;

import java.awt.Color;

import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class VowelChartTest {
	
	static VowelChart chart;
	
	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);
	
	@BeforeClass
	   public static void beforeClass() {
		ArrayList<Vowel> list = new ArrayList<Vowel>();
		list.add(new Vowel("a", 1,2,3,4.0,5,6,7.0));
		list.add(new Vowel("b", 8,9,10,11.2,12,13,14.5));
		chart = new VowelChart(list);
	   }
	
	@Test
	public void testGetMaxFrequency(){
		assertEquals(11.2, chart.getMaxFrequency(),0.1);
	}
	@Test
	public void  testGetMinFrequency(){
		assertEquals(4, chart.getMinFrequency(),0.1);
	}
	@Test
	public void testFreqCoeff(){
		assertEquals(0.5, VowelChart.freqCoeff(15, 5,  10), 0.0001);
	}
	@Test
	public void testForColor(){
		Color color = new Color(0, 240, 255);
		assertEquals(color, chart.forColor(15));
		
		color = new Color(255, 220, 0);
		assertEquals(color, chart.forColor(5));
	}
	

	
	
}
