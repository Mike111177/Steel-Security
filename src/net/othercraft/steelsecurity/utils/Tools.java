package net.othercraft.steelsecurity.utils;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Tools {
    /**
     * 
     * @param pname
     * The string to use
     * @return
     * A list of players whos name start with pname and are online.
     */
    public static Set<Player> safePlayer(String pname) {
	Set<Player> list = new HashSet<Player>();
	for (Player scan : Bukkit.getServer().getOnlinePlayers()) {
	    if (scan.getName().toLowerCase().startsWith(pname.toLowerCase())) {
		list.add(scan);
	    }
	}
	return list;
    }
    /**
     * Test if a String is safe to parse to an Integer.
     * @param toparse
     * the string to test
     * @return
     * weather or not the string is safe to parse into and integer
     */
    
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
