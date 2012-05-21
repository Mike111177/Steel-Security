package net.othercraft.steelsecurity;

import java.util.Arrays;
import java.util.List;

public class Config {
	
	public Main plugin;
	
	public Config(Main instance) {	
		plugin = instance;
	}	
	
	public void loadConfiguration() {
		plugin.getConfig().addDefault("AntiSpam.Censoring.Enabled", false );
		plugin.getConfig().addDefault("AntiSpam.Censoring.Block_Words", Arrays.asList("Nodus", "Avo", "ICHG"));
		plugin.getConfig().addDefault("AntiSpam.AntiCaps.Enabled", true);
		plugin.getConfig().addDefault("AntiSpam.AntiCaps.Minimum_Length", 4);
		plugin.getConfig().addDefault("AntiSpam.AntiCaps.Percent", 70);
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}	
	@SuppressWarnings("unchecked")
	public List<String> getConfigurationList(String Listpath) {
		return (List<String>) plugin.getConfig().getList(Listpath);
	}
	public String getConfigurationString(String Stringpath){
		return plugin.getConfig().getString(Stringpath);
	}
	public Boolean getConfigurationBoolean(String Booleanpath) {
		return plugin.getConfig().getBoolean(Booleanpath);
	}
	public int getConfigurationInt(String Intpath) {
		return plugin.getConfig().getInt(Intpath);
	}
}