package org.espeakng.jeditor.utils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;

import org.espeakng.jeditor.gui.MainWindow;

import java.lang.Process;

public class CommandUtilities {

        private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("CommandUtilities");

        public static void main(String[] args, String command) {
                executeCmd(command);

        }

        /**
         * Executes external process with bash interpreter synchronously
         * 
         * @param command
         *            line of the bash script
         * 
         * @return output/error status
         */
        public static String executeCmd(String command) {
                String[] cmd = new String[3];
                cmd[0] = "/bin/bash";
                cmd[1] = "-c";
                cmd[2] = command;
                return executeCmd(cmd);
        }

        /**
         * Executes external process synchronously
         * 
         * @param cmd
         *            array of command parameters
         * @return output+error status as String
         */
        public static String executeCmd(String[] cmd) {
                CommandResult result = executeCmdResult(cmd);
                String error = result.err;
                String output = result.out;
                if (!(output.equals("")))
                        logger.log(Level.FINE, "executeCmd(" + Arrays.toString(cmd) + ")\nOutput message: " + output);
                if (error != "") {
                        error += ("Command output error message: " + error);
                        output += error;
                }
                return output;
        }

        /**
         * Executes external process synchronously
         * 
         * @param cmd
         *            array of command parameters
         * @return CommandOutput with out and err fields
         */
        public static CommandResult executeCmdResult(String[] cmd) {
                String error = "";
                String output = "";
                CommandResult commandOutput = null;
                try {
                        Process pb = createProcess(cmd);
                        output = getOutput(pb);
                        error = getError(pb);
                        pb.waitFor();

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

                return commandOutput;
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

        /**
         * Creates separate (asynchronous) process with passed parameters
         * 
         * @param array
         *            of params
         * @return reference to created process
         */
        public static Process createProcess(String[] params) {
                // A Runtime object has methods for dealing with the OS
                Runtime runtime = Runtime.getRuntime();
                // Process
                Process process = null;
                try {
                        process = runtime.exec(params);
                } catch (Exception e) {
                        handleError(e);
                }
                return process;
        }

        /**
         * @param process
         *            , which is created by createProcess()
         * @return string gathered from the process output
         */
        public static String getOutput(Process process) {
                // See
                // http://web.archive.org/web/20140531042945/https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
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

