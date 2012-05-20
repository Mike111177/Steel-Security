package net.othercraft.steelsecurity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
	
	public Main plugin;
	
	public Config(Main instance) {	
		plugin = instance;
	}	
	
	public void loadConfiguration() {
		plugin.getConfig().addDefault("AntiSpam.Censoring.Enabled", false );
		plugin.getConfig().addDefault("AntiSpam.Censoring.Block_Words", Arrays.asList("Nodus", "Avo", "ICHG"));
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}	
	public List<String> getConfigurationList(String Listpath) {
		return (List<String>) plugin.getConfig().getList(Listpath);
	}
	public void getConfigurationString(String Stringpath){
		plugin.getConfig().getString(Stringpath);
	}
	public Boolean getConfigurationBoolean(String Booleanpath) {
		return plugin.getConfig().getBoolean(Booleanpath);
	}
	public void getConfigurationInt(String Intpath) {
		plugin.getConfig().getInt(Intpath);
	}
}