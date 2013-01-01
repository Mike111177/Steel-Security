/**
 * @Class: ExceptionLogger
 * @Author: CorrieKay and r0306
 * @Purpose: This class will handle all logging of exceptions, and anything else we need it for
 * @Usage: This is a non instantiated class. All methods are used staticly
 * @Exceptions: To log an exception, call the correct method and fill out the parameters accordingly.
 */

package net.othercraft.steelsecurity.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class ExceptionLogger {

    public void commandException(final Exception e,final String directoryString,final CommandSender s,final Command c,final String[] args) throws IOException {
	createFile(directoryString, "Exceptions.log");
	final Logger logger = Logger.getLogger(directoryString);
	final String date = Calendar.getInstance().getTime().toString();
	logger.log(Level.SEVERE, date + " Command " + cmdToString(c, args) + " used by " + s.getName() + " generated an exception.", e);
    }

    public void listenerException(final Exception e,final String name,final String path) throws IOException {
	createFile(path, "Exceptions.log");
	final Logger logger = Logger.getLogger(path);
	final String date = Calendar.getInstance().getTime().toString();
	logger.log(Level.SEVERE, date + " Event " + name + " generated an exception.", e);
    }

    public void createFile(final String directoryName,final String fileName) throws IOException {
	final File dir = new File(directoryName);
	if (!dir.exists()) {
	    dir.mkdirs();
	}
	final File file = new File(directoryName + "/" + fileName);
	if (!file.exists()) {
	    file.createNewFile();
	}
    }

    public String cmdToString(final Command c,final String[] args) {
	String command = "";
	command += "/" + c.getName() + " ";
	if (args != null) {
	    for (int i = 0; i < args.length; i++) {
		command += args[i] + " ";
	    }
	}
	return command;
    }
}
