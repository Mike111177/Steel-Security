package net.othercraft.steelsecurity.commands;

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
			//TODO i have an idea to check if another player has a certain permission but im having a hard time getting the variables to be compatible
		}
	}

}
