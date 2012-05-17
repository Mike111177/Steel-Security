package net.othercraft.steelsecurity.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckPerm {

	public static void checker(CommandSender sender, String[] split) {
		if (split.length<3) {
			sender.sendMessage("Not enough arguments!");
			sender.sendMessage("Usage: /sts checkperm <player> <permission>");
		}
		else if (split.length>3) {
			sender.sendMessage("Too many arguments!");
			sender.sendMessage("Usage: /sts checkperm <player> <permission>");
		}
		else {
			String targetname = split[1];
	        Player target = Bukkit.getPlayer(targetname);
	        String perm = split[2];
			if (target.hasPermission(perm)) {
				sender.sendMessage(ChatColor.GREEN + target.getName() + " has the permission " + perm);
			}
			else {
				sender.sendMessage(ChatColor.RED + target.getName() + " does not have the permission " + perm);
			
			}    
		}
	}	
}
//TODO There is a stack error when checking for a player that is not online.