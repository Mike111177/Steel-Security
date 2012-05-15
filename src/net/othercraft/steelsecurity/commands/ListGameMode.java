package net.othercraft.steelsecurity.commands;

import net.othercraft.steelsecurity.variablemanagers.PlayerManager;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListGameMode {

	public static void listgm(CommandSender sender) {
		 Player[] p = PlayerManager.playerList();
		 int count = p.length;
		 int counter = 0;
		 while (counter<count) {
			Player r = p[counter];
			GameMode g = r.getGameMode();
			sender.sendMessage(r.getName() + ": " + g);
			counter = counter + 1;	 
		 }
		
	}

}
