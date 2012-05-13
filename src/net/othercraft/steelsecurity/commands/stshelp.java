package net.othercraft.steelsecurity.commands;

import org.bukkit.command.CommandSender;

public class stshelp {

	public void sts(CommandSender sender, String[] split) {
		if (split.length==1){
			p1(sender);
		}
		else if (split.length==2){
			if (split[1].equalsIgnoreCase("1")){
				p1(sender);
			}
		}
		else {
			sender.sendMessage("Too many arguments!");
		}
	}

	private void p1(CommandSender sender) {
		if (sender.hasPermission("steelsecurity.commands.sts")){
		sender.sendMessage("/sts: Base Command");
		}
		if (sender.hasPermission("steelsecurity.commands.help")){
			sender.sendMessage("/sts help: Displays this help screen.");
		}
	}	
}