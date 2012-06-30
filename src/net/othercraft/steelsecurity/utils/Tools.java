package net.othercraft.steelsecurity.utils;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Tools {

    public static Player[] safePlayer(String pname) {
	Set<Player> list = new HashSet<Player>();
	for (Player scan : Bukkit.getServer().getOnlinePlayers()) {
	    if (scan.getName().startsWith(pname)) {
		list.add(scan);
	    }
	}
	Player[] toreturn = (Player[]) list.toArray();
	return toreturn;
    }
}
