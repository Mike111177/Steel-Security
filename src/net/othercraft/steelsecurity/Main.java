package net.othercraft.steelsecurity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.othercraft.steelsecurity.commands.Sts;
import net.othercraft.steelsecurity.listeners.ChatFilter;
import net.othercraft.steelsecurity.listeners.JoinMessage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main instance;

	private Sts base;
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;

	@SuppressWarnings("unused")
	private ChatFilter cf;
	@SuppressWarnings("unused")
	private JoinMessage jm;

	public void onEnable(){
		new Config(this).loadConfiguration();
		instance = this;
		listeners();
		commands();
	}
	private void commands() {//register commands here
		base = new Sts("base");
		getCommand("sts").setExecutor(base);
	}
	private void listeners() {//register listeners here
		cf = new ChatFilter(null, this);
		jm = new JoinMessage(null, this);
	}
	public void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(getDataFolder(), "customConfig.yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = getResource("customConfig.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }
	}
	public FileConfiguration getCustomConfig() {
	    if (customConfig == null) {
	        reloadCustomConfig();
	    }
	    return customConfig;
	}
	public void saveCustomConfig() {
	    if (customConfig == null || customConfigFile == null) {
	    return;
	    }
	    try {
	        customConfig.save(customConfigFile);
	    } catch (IOException ex) {
	        Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
	    }
	}
	public void onDisable(){
	}
}