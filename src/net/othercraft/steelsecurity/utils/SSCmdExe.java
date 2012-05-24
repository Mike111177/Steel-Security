/**
 * @Class: SSCmdExe
 * @Author: CorrieKay
 * @Purpose: This is an abstract class used as a superclass to all command executors.
 * @Usage: none. Extend any command executor as this, and youre good to go.
 * @Implements: CommandExecutor: utilizes the command executor for onCommand.
 * @onCommand: onCommand will be the super method for handling events. It will have a try/catch block surrounding the subclass method "handleCommand()"
 * @Events: each event will be declared and ran in its own subclass. It is up to us as the developers to remember to run our try/catch blocks.
 * @Catching: exceptions will be caught in events by calling and passing the exception into the "catchListenerException(Exception)" method.
 */

package net.othercraft.steelsecurity.utils;

import net.othercraft.steelsecurity.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public abstract class SSCmdExe implements CommandExecutor, Listener{
	
	/**
	 * @Constructor
	 * @param listener If the subclass requires it to be registered as a listener, this needs to be true, or else your events wont be utilized.
	 */
	public SSCmdExe(Boolean listener){
		if(listener){
			Bukkit.getPluginManager().registerEvents(this,Main.instance);
		}
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		//TODO surround with try/catch logger block
		boolean command = handleCommand(sender,cmd,label,args);
		return command;
		//TODO surround with try/catch logger block
	}
	public boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args){
		return true;
	}
	public void catchListenerException(Exception e){
		//TODO log exceptions and create logger.
	}
}
