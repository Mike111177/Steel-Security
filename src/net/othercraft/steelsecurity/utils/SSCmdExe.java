/**
 * @Class: SSCmdExe
 * @Author: CorrieKay
 * @Purpose: This is an abstract class used as a superclass to all command executors.
 * @Usage: none. Extend any command executor as this, and youre good to go.
 * @Implements: CommandExecutor: utilizes the command executor for onCommand.
 * @onCommand: onCommand will be the super method for handling events. It will have a try/catch block surrounding the subclass method "handleCommand()"
 */

package net.othercraft.steelsecurity.utils;

import net.othercraft.steelsecurity.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public abstract class SSCmdExe implements CommandExecutor, Listener, SSCmdExeInterface{
	
	public final String name;
	
	/**
	 * @Constructor
	 * @param name This is the name of the class. whenever exceptions are logged, it will be in a folder named after the class.
	 * @param listener If the subclass requires it to be registered as a listener, this needs to be true, or else your events wont be utilized.
	 */
	public SSCmdExe(String name, Boolean listener){
		this.name = name;
		if(listener){
			Bukkit.getPluginManager().registerEvents(this,Main.instance);
		}
	}
	
	@Override //This is our supermethod for dealing with commands. We dont touch this.
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		try {
			boolean command = handleCommand(sender, cmd, label, args);
			return command;
		} catch (Exception e) {
			sender.sendMessage("[SteelSecurity]: There was an unhandled internal exception caught when trying to perform this command. Please contact an administrator and notify them at the earliest convenience.");
			//TODO log
			return false;
		}
	}
	/**
	 * Handles commands in the cmd executor subclasses. Please use @Override
	 * 
	 * @param sender sender of the command
	 * @param cmd the command
	 * @param label the commandLabel
	 * @param args the arguments
	 * @return return true if the command was successful, false if there was an error.
	 */
	public boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args){
		return true;
	}
	public void catchListenerException(Exception e){
		//TODO log exceptions and create logger.
	}
}
