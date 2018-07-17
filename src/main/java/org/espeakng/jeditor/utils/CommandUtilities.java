package org.espeakng.jeditor.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.espeakng.jeditor.gui.MainWindow;

import java.io.IOException;
import java.lang.Process;

public class CommandUtilities {

        private static Logger logger = Logger.getLogger(CommandUtilities.class.getName());
        private static List<Thread> threadsList;
        
        /**
         * Executes external process with bash interpreter synchronously
         * 
         * @param command
         *            line of the bash script
         * 
         * @return indexOfThread
         */
        public static int executeCmd(String command) {
                String[] cmd = new String[3];
                cmd[0] = "/bin/bash";
                cmd[1] = "-c";
                cmd[2] = command;
            
                ProcessThread pt = new ProcessThread(cmd);
                
                if (threadsList == null) {
                	threadsList = new ArrayList<>();
                }
                
                Thread t = new Thread(pt);
                threadsList.add(t);
               
                t.start();
                
                return threadsList.indexOf(t);
        }

        /**
         * Executes external process synchronously
         * 
         * @param cmd
         *            array of command parameters
         * @return CommandOutput with out and err fields
         */
        public static void executeCmd(String[] cmd) {
                String error = "";
                String output = "";
                CommandResult commandOutput = null;
                try {
                        

                        if (!(output.equals("")))
                                logger.log(Level.FINE, "executeCmd(" + Arrays.toString(cmd) + ")\nOutput message: " + output);
                        else
                                logger.log(Level.FINE, "executeCmd(" + Arrays.toString(cmd) + ") executed successfully");

                        if (error != "")
                                logger.log(Level.SEVERE, error);

                        commandOutput = new CommandResult(output, error);
                } catch (Throwable e) {
                        handleError(e);
                }
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
                logger.log(Level.SEVERE, message);
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
                // http://web.archive.org/web/20140531042945/ https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
                try (Scanner s = new Scanner(process.getInputStream()).useDelimiter("\\A")) {
                        return s.hasNext() ? s.next() : "";
                }
        }

        /**
         * @param process
         *            , which is created by createProcess()
         * @return string gathered from the process error output
         */
        public static String getError(Process process) {
                try (Scanner s = new Scanner(process.getErrorStream()).useDelimiter("\\A")) {
                        return s.hasNext() ? s.next() : "";
                }
        }
}

