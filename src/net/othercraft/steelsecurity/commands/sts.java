package net.othercraft.steelsecurity.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sts {
	//Defines Chat Colors
	ChatColor r = ChatColor.RED;
	ChatColor g = ChatColor.GREEN;
	ChatColor y = ChatColor.YELLOW;
	//Defines No Permission String
	String noperm = (r + "You don't have permission to do this!");
    //Receives command and takes actions.
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
				else {
					sender.sendMessage(noperm);
				}
			}
			if (split[0].equalsIgnoreCase("checkperm")){//TODO stack error on check for an offline player.
				if (sender.hasPermission("steelsecurity.commands.checkperm")) {
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
							sender.sendMessage(g + target.getName() + " has the permission " + perm);
						}
						else {
							sender.sendMessage(r + target.getName() + " does not have the permission " + perm);
						}    
					}
				}
				else {
					sender.sendMessage(noperm);
				}
			}
			if (split[0].equalsIgnoreCase("listgm")){//TODO add a way to include offline players
				if (sender.hasPermission("steelsecurity.commands.listgm")) {
					Player[] p = Bukkit.getOnlinePlayers();
					 int count = p.length;
					 int counter = 0;
					 while (counter<count) {
						Player r = p[counter];
						GameMode g = r.getGameMode();
						if (split.length==2) {
							if (split[1].equalsIgnoreCase(g.toString())){
						sender.sendMessage(r.getName() + ": " + g);
							}
						}
						else {
							sender.sendMessage(r.getName() + ": " + g);
						}
						counter = counter + 1;	 
					 }
				}
				else {
					sender.sendMessage(noperm);
		}
	}
			if (split[0].equalsIgnoreCase("listop")){
				if (sender.hasPermission("steelsecurity.commands.listop")) {//TODO add a way to include offline players
					Player[] p = Bukkit.getOnlinePlayers();
					 int count = p.length;
					 int counter = 0;
					 while (counter<count) {
						Player r = p[counter];
						if (r.isOp()) {
						sender.sendMessage(r.getName());
						counter = counter + 1;	 
						}
					 }
				}
				else {
					sender.sendMessage(noperm);
				}
			}
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
		if (sender.hasPermission("steelsecurity.commands.listgm")){
			sender.sendMessage(g + "/sts listgm:" + y + " List online players with their gamemode.");
		}
		if (sender.hasPermission("steelsecurity.commands.listop")){
			sender.sendMessage(g + "/sts listop:" + y + " List online ops.");
		}	
	}
}
//TODO add a way to change the game mode of someone that is offline.