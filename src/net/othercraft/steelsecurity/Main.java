package net.othercraft.steelsecurity;

import java.util.Arrays;
import java.util.List;

import net.othercraft.steelsecurity.commands.Commands;
import net.othercraft.steelsecurity.listeners.Events;
import net.othercraft.steelsecurity.listeners.PlayerChatListener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	FileConfiguration config = this.getConfig();
    private PlayerChatListener cl;
    private Commands myExecutor;
	
	public void onEnable() {
		myExecutor = new Commands(this);
		PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Events(this, cl), this);
		getCommand("sts").setExecutor(myExecutor);
		getCommand("freezeall").setExecutor(myExecutor);
		loadConfiguration();
	}
	     
	public void onDisable() {
		
	}
	public void loadConfiguration(){
		 config.addDefault("Anti_Spam.censoring.enabled", false );
		 config.addDefault("Anti_Spam.censoring.blocked_words", Arrays.asList("Avo","ICHG","Nodus"));
	     getConfig().options().copyDefaults(true);
	     saveConfig();
	}
	public void configReload(){
		reloadConfig();
	}
	public List<String> findConfigValueList(String request) {
		List cvalue = getConfig().getList(request);
		return cvalue;
	}
}