package net.othercraft.steelsecurity.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sts {

	public void command(CommandSender sender, String[] split, Player player) {
		if (split.length==0) {
			if (sender.hasPermission("steelsecurity.commands.sts")){
				sender.sendMessage("This server is running Steel Security");
				sender.sendMessage("For a list of commands type /sts help");
			}
		}
		else if (split.length>0) {
			if (split[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("steelsecurity.commands.help")){
				stshelp sh = new stshelp();
				sh.sts(sender, split);
				}
			}
		}
	}
}