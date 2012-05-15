package net.othercraft.steelsecurity.commands;

import org.bukkit.command.CommandSender;

public class sts {

	public void command(CommandSender sender, String[] split) {
		if (split.length==0) {
			if (sender.hasPermission("steelsecurity.commands.sts")){
				sender.sendMessage("This server is running Steel Security");
				sender.sendMessage("For a list of commands please type /sts help");
			}
		}
		else if (split.length>0) {
			if (split[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("steelsecurity.commands.help")){
				stshelp sh = new stshelp();
				sh.sts(sender, split);
				}
			}
			if (split[0].equalsIgnoreCase("checkperm")){
				if (sender.hasPermission("steelsecurity.commands.checkperm")) {
					CheckPerm.checker(sender, split);
				}
			}
		}
	}
}