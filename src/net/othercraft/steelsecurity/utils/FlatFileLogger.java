package net.othercraft.steelsecurity.utils;

import java.io.File;

public class FlatFileLogger {

    public transient final File logFile;

    public FlatFileLogger(final File folder,final String logname) {
	logFile = new File(folder, logname + ".log");
    }

    public void writeLine(final String line) {
	//TODO write a line
    }

    public void clear() {
	//TODO Clear log

    }
}
