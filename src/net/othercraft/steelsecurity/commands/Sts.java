package net.othercraft.steelsecurity.commands;

import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sts extends SSCmdExe implements CommandExecutor {
	public Sts(String name, Boolean listener) {
		super(name, listener);
		// TODO Auto-generated constructor stub
	}
	//Defines Chat Colors
	ChatColor r = ChatColor.RED;
	ChatColor g = ChatColor.GREEN;
	ChatColor y = ChatColor.YELLOW;
	//Defines No Permission String
	String noperm = (r + "You don't have permission to do this!");
	//Receives command and takes actions.
	public void command(CommandSender sender, String[] args) {
		if (args.length==0) {
			if (sender.hasPermission("steelsecurity.commands.sts")){
				sender.sendMessage("This server is running Steel Security");
				sender.sendMessage("For a list of commands please type /sts help");
			}
			else {
				sender.sendMessage(noperm);				
			}
		}
		else if (args.length>0) {
			if (args[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("steelsecurity.commands.help")){
					if (args.length==1){
						p1(sender);
					}
					else if (args.length==2){
						if (args[1].equalsIgnoreCase("1")){
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
			if (args[0].equalsIgnoreCase("checkperm")){//TODO stack error on check for an offline player.
				if (sender.hasPermission("steelsecurity.commands.checkperm")) {
					if (args.length<3) {
						sender.sendMessage("Not enough arguments!");
						sender.sendMessage("Usage: /sts checkperm <player> <permission>");
					}
					else if (args.length>3) {
						sender.sendMessage("Too many arguments!");
						sender.sendMessage("Usage: /sts checkperm <player> <permission>");
					}
					else {
						String targetname = args[1];
						Player target = Bukkit.getPlayer(targetname);
						String perm = args[2];
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
			if (args[0].equalsIgnoreCase("listgm")){//TODO add a way to include offline players
				if (sender.hasPermission("steelsecurity.commands.listgm")) {
					Player[] p = Bukkit.getOnlinePlayers();
					int count = p.length;
					int counter = 0;
					while (counter<count) {
						Player r = p[counter];
						GameMode g = r.getGameMode();
						if (args.length==2) {
							if (args[1].equalsIgnoreCase(g.toString())){
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
			if (args[0].equalsIgnoreCase("listop")){
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
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
		command(sender, args);
		return true;
	}
}
