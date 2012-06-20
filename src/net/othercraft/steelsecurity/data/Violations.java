package net.othercraft.steelsecurity.data;

import java.util.HashMap;
import java.util.Map;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.entity.Player;

public class Violations extends SSCmdExe{
	
	Main plugin;

	public Violations(String name, Main instance) {
		super("Violations", true);
		plugin = instance;
	}
	Map<String, Map<Player, Integer>> Violations = new HashMap<String, Map<Player, Integer>>();

	public void startUp(){
		
	}
	public void addOne(String violation, Player player){

	}
	public void subtractOne(String violation, Player player){

	}
	public void setViolation(String violation, Player player, Integer value){
		
	}
	public Integer getViolation(String violation, Player player){
		return null;
	}
	
	

}
