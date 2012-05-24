/**
 * @Class: ExceptionLogger
 * @Author: CorrieKay
 * @Purpose: This class will handle all logging of exceptions, and anything else we need it for
 * @Usage: This is a non instantiated class. All methods are used staticly
 * @Exceptions: To log an exception, call the correct method and fill out the parameters accordingly.
 */

package net.othercraft.steelsecurity.utils;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class ExceptionLogger {

	public static void commandException(Exception e, String directoryString, CommandSender s, Command c, String[] args){
		File directory = new File(directoryString);
		if(!directory.exists()){
			directory.mkdirs();
		}
	}
	public static void listenerException(){
		
	}
}
