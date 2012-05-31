package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.listeners.ChatFilter;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main instance;
	
	private ChatFilter cf;
	
	public void onEnable(){
		instance = this;
		cf = new ChatFilter(null, this);
	}
	
	public void onDisable(){
		
	}
	
}
