package org.espeakng.utils;

import static org.junit.Assert.*;
import org.espeakng.jeditor.utils.*;

import org.junit.Test;

public class CommandUtilitiesTest {
	String errIn = "error in";
	String errOut = "error out";
	
	@Test
	public void executeCmd() {
		CommandUtilities.executeCmd("echo hello");
		assertFalse(CommandUtilities.getLastThread()==null);
	}
	
	
	@Test
	public void handleError() {
		Throwable trow1 = new Throwable("Throw 1");
		Throwable trow2 = new Throwable("Throw 2", trow1);
		
		
		System.out.println(CommandUtilities.handleError(trow2));
		assertFalse(CommandUtilities.getLastThread()==null);
	}
	
	@Test
	public void testWrapLayout() {
	}

	@Test
	public void testWrapLayoutInt() {
		int align = 0;
		System.out.println(align);
	}

	@Test
	public void testWrapLayoutIntIntInt() {
		int align = 0;
		int hgap = 0;
		int vgap = 0;
		System.out.print(align);
		System.out.print(hgap);
		System.out.print(vgap);
	}

//	@Test
//	public void testPreferredLayoutSizeContainer() {
//		Target target = new Target();
//		
//	}

//	@Test
//	public void testMinimumLayoutSizeContainer() {
//		fail("Not yet implemented");
//	}
}
