package org.espeakng.utils;

import org.espeakng.jeditor.utils.ProcessThread;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessThreadTest {
	static ProcessThread pt ;
	
	@BeforeClass
	   public static void beforeClass() {
		String [] args = {"echo", "Hello"};
		pt = new ProcessThread(args);

	   }
	@Test
	public void testRun() {
		String [] args = {"eckho", "Hello"};
		pt = new ProcessThread(args);
	}

}
