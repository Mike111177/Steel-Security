package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.commands.Commands;
import net.othercraft.steelsecurity.listeners.Events;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public final Events eve = new Events(this);
	
	private Commands myExecutor;
	
	public void onEnable() {
		myExecutor = new Commands(this);
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(eve, this);
		getCommand("sts").setExecutor(myExecutor);
		getCommand("freezeall").setExecutor(myExecutor);
	}
	
	public void onDisable() {
		
	}

}