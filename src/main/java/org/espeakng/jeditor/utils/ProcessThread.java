package org.espeakng.jeditor.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessThread implements Runnable {

	private String[] command;
	public static boolean stop = false;
	
	public ProcessThread(String[] command) {
		this.command = command;
	}

	@Override
	public void run() {
		try {
			Process pb = Runtime.getRuntime().exec(command);
			pb.waitFor();
			
			String output = CommandUtilities.getOutput(pb);
			String error = CommandUtilities.getError(pb);
	        
			Logger logger = CommandUtilities.getLogger();
			
			if (!(output.equals("")))
                logger.log(Level.FINE, "executeCmd(" + Arrays.toString(command) + ")\nOutput message: " + output);
	        else
	                logger.log(Level.FINE, "executeCmd(" + Arrays.toString(command) + ") executed successfully");
	
	        if (error != "")
	                logger.log(Level.SEVERE, error);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
