package net.othercraft.steelsecurity;

import java.util.logging.Logger;

import net.othercraft.steelsecurity.commands.Commands;
import net.othercraft.steelsecurity.listeners.Events;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private Logger log = Logger.getLogger("Minecraft");
	
	public final Events eve = new Events(this);
	
	private Commands myExecutor;
	
	public void onEnable() {
		myExecutor = new Commands(this);
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(eve, this);
		this.logMessage("Enabled.");
	}
	
	public void onDisable() {
		this.logMessage("Disabled.");
		
	}
	
	public void logMessage(String msg) {
		PluginDescriptionFile pdFile = this.getDescription();
		this.log.info("[" + pdFile.getName() + "] " + msg); 
	}

}