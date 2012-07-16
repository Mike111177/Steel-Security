package net.othercraft.steelsecurity.utils;

import java.io.File;

public class FlatFileLogger {

    private File logFile = null;
    private File dataFolder = null;

    public FlatFileLogger(File folder, String logname) {
	dataFolder = folder;
	logFile = new File(dataFolder, logname + ".log");
    }

    public void writeLine(String line) {

    }

    public void clear() {

    }
}
