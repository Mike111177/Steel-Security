package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.Listener.SSEventHandler;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main instance;
	
	@SuppressWarnings("unused")
	private SSEventHandler eventHandler;
	
	public void onEnable(){
		instance = this;
		eventHandler = new SSEventHandler();
	}
	
	public void onDisable(){
		
	}
	
}
