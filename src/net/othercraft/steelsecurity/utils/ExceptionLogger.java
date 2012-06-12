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

	public void commandException(Exception e, String directoryString, CommandSender s, Command c, String[] args) throws IOException{
		createFile(directoryString, "Exceptions.log");
		Logger logger = Logger.getLogger(directoryString);
		String date = Calendar.getInstance().getTime().toString();
		logger.log(Level.SEVERE, date + " Command " + cmdToString(c, args) + " used by " + s.getName() + " generated an exception." , e);
	}

	public void listenerException(Exception e, String name, String path) throws IOException{
		createFile(path, "Exceptions.log");
		Logger logger = Logger.getLogger(path);
		String date = Calendar.getInstance().getTime().toString();
		logger.log(Level.SEVERE, date + " Event " + name + " generated an exception.", e);
	}

	public void createFile(String directoryName, String fileName) throws IOException {
		File dir = new File(directoryName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(directoryName + "/" + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public String cmdToString(Command c, String[] args) {
		String command = "";
		command += "/" + c.getName() + " ";
		if (args != null) {
			for (int i = 0; i < args.length; i ++) {
				command += args[i] + " ";
			}
		}
		return command;
	}
}
