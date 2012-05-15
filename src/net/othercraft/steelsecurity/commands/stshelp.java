package net.othercraft.steelsecurity.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class stshelp {
	
	ChatColor r = ChatColor.RED;
	ChatColor g = ChatColor.GREEN;
	ChatColor y = ChatColor.YELLOW;

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
			sender.sendMessage(r + "Too many arguments!");
		}
	}

	private void p1(CommandSender sender) {
		if (sender.hasPermission("steelsecurity.commands.sts")){
		sender.sendMessage(g + "/sts:" + y + " Base Command");
		}
		if (sender.hasPermission("steelsecurity.commands.help")){
			sender.sendMessage(g + "/sts help:" + y + " Displays this help screen.");
		}
		if (sender.hasPermission("steelsecurity.commands.checkperm")){
			sender.sendMessage(g + "/sts checkperm:" + y + " Checks a permmision for another player.");
		}
	}	
}