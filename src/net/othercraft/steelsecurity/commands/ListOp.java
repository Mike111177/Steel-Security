package net.othercraft.steelsecurity.commands;

import net.othercraft.steelsecurity.variablemanagers.PlayerManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListOp {

	public static void listOP(CommandSender sender) {
		 Player[] p = PlayerManager.playerList();
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

}
