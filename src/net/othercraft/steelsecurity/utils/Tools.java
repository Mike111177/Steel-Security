package net.othercraft.steelsecurity.utils;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Tools {

    public static Set<Player> safePlayer(String pname) {
	Set<Player> list = new HashSet<Player>();
	for (Player scan : Bukkit.getServer().getOnlinePlayers()) {
	    if (scan.getName().toLowerCase().startsWith(pname.toLowerCase())) {
		list.add(scan);
	    }
	}
	return list;
    }
    public static Boolean isSafeNumber(String toparse){
	Boolean result;
	try{
	    int i = Integer.parseInt(toparse);
	    result = true;
	}
	catch (NumberFormatException e){
	    result = false;
	}
	return result;
    }
}
