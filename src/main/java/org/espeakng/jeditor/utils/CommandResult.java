package org.espeakng.jeditor.utils;


/**
 * This class is used to get structured results from
 * TestUtils.executeCmdResult() method
 */
class CommandResult {
        String out;
        String err;

        public CommandResult(String stdout, String stderr) {
                this.out = stdout;
                this.err = stderr;
        }

        public String out() {
                return out;
        }

        public String err() {
                return err;
        }

}