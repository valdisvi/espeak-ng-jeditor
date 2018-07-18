package org.espeakng.jeditor.utils;

import java.util.Arrays;
import java.util.Scanner;
import java.lang.Process;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CommandUtilities {

        private static Logger logger = Logger.getLogger(CommandUtilities.class.getName());
        private static Thread lastThread;
        /**
         * Executes external process with bash interpreter synchronously
         * 
         * @param command
         *            line of the bash script
         * 
         * @return indexOfThread
         */
        public static void executeCmd(String command) {
                String[] cmd = new String[3];
                cmd[0] = "/bin/bash";
                cmd[1] = "-c";
                cmd[2] = command;
            
                ProcessThread pt = new ProcessThread(cmd);
                
                Thread t = new Thread(pt);
                lastThread = t;
                t.start();
                
        }
        
        public static Thread getLastThread() {
        	return lastThread;
        }
        
        /**
         * Handles test without failing it
         * 
         * @param e
         *            — passed Throwable
         * @return — custom message
         */
        public static String handleError(Throwable e) {
                String message = "\n" + e.getClass().getName();
                String msg = e.getMessage();
                
                if (msg != null)
                        message += ", message: " + e.getMessage();
                
                Throwable cause = e.getCause();
                while (cause != null) {
                        message = message + "\ncause: " + cause.getClass().getName();
                        msg = cause.getMessage();
                        
                        if (msg != null)
                                message += ", message: " + msg;
                        
                        cause = cause.getCause();
                }
                
                message = message + "\nStack trace:\n" + stackTraceToString(e);
                logger.log(Level.FATAL, message);
                
                return message;
        }

        /**
         * @param t
         *            — Throwable
         * @return String of stack trace in separate lines
         */
        public static String stackTraceToString(Throwable t) {
                return Arrays.toString(t.getStackTrace()).replaceAll(", ", "\n");
        }
        
	     /**
	      * Get logger instance.
	      * 
	      * @return Logger
	      */
        public static Logger getLogger() {
        	return logger;
        }

        /**
         * @param process
         *            , which is created by createProcess()
         * @return string gathered from the process output
         */
        public static String getOutput(Process process) {
            // See
            // http://web.archive.org/web/20140531042945/https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
    		try (Scanner s = new Scanner(process.getInputStream())) {
				s.useDelimiter("\\A");
				
                return s.hasNext() ? s.next() : "";
            }
        }

        /**
         * @param process
         *            , which is created by createProcess()
         * @return string gathered from the process error output
         */
        public static String getError(Process process) {
            try (Scanner s = new Scanner(process.getErrorStream())) {
                s.useDelimiter("\\A");
                
            	return s.hasNext() ? s.next() : "";
            }
        }
}

