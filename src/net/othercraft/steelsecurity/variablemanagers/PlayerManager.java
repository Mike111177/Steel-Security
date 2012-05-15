package net.othercraft.steelsecurity.variablemanagers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerManager {
	
	public static Player[] playerList(){
		Player[] pli = Bukkit.getOnlinePlayers();
		return pli;
	}

}
