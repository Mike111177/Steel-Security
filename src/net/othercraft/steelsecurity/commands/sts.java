package net.othercraft.steelsecurity.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sts {
	
	String noperm = (ChatColor.RED + "You don't have permission to do this!");

	public void command(CommandSender sender, String[] split, Player player) {
		if (split.length==0) {
			if (sender.hasPermission("steelsecurity.commands.sts")){
				sender.sendMessage("This server is running Steel Security");
				sender.sendMessage("For a list of commands please type /sts help");
			}
			else {
			sender.sendMessage(noperm);				
			}
		}
		else if (split.length>0) {
			if (split[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("steelsecurity.commands.help")){
				stshelp sh = new stshelp();
				sh.sts(sender, split);
				}
				else {
					sender.sendMessage(noperm);
				}
			}
			if (split[0].equalsIgnoreCase("checkperm")){
				if (sender.hasPermission("steelsecurity.commands.checkperm")) {
					CheckPerm.checker(sender, split);
				}
				else {
					sender.sendMessage(noperm);
				}
			}
			if (split[0].equalsIgnoreCase("listgm")){
				if (sender.hasPermission("steelsecurity.commands.listgm")) {
					ListGameMode.listgm(sender, split);
				}
				else {
					sender.sendMessage(noperm);
		}
	}
			if (split[0].equalsIgnoreCase("listop")){
				if (sender.hasPermission("steelsecurity.commands.listop")) {
					ListOp.listOP(sender);
				}
				else {
					sender.sendMessage(noperm);
				}
			}
		}
	}	
}