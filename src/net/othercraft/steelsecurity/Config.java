package net.othercraft.steelsecurity;

import java.util.Arrays;
import java.util.List;

public class Config {
	
	public Main plugin;
	
	public Config(Main instance) {	
		plugin = instance;
	}	
	
	public void loadConfiguration() {
		defaults();
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
	}	
	private void defaults() { //Defaults
		plugin.getConfig().addDefault("General.Prefix", "[STS]");
		plugin.getConfig().addDefault("General.Logon_Message_Enabled", true);
		plugin.getConfig().addDefault("General.Logon_Message", "Steel Security is running on this server.");
		plugin.getConfig().addDefault("AntiSpam.Censoring.Enabled", false );
		plugin.getConfig().addDefault("AntiSpam.Censoring.Block_Words", Arrays.asList("Nodus", "Avo", "ICHG"));
		plugin.getConfig().addDefault("AntiSpam.AntiCaps.Enabled", true);
		plugin.getConfig().addDefault("AntiSpam.AntiCaps.Minimum_Length", 4);
		plugin.getConfig().addDefault("AntiSpam.AntiCaps.Percent", 70);
	}

	@SuppressWarnings("unchecked")
	public List<String> getConfigurationList(String Listpath) {//Gets a list
		return (List<String>) plugin.getConfig().getList(Listpath);
	}
	public String getConfigurationString(String Stringpath){//Gets a string
		return plugin.getConfig().getString(Stringpath);
	}
	public Boolean getConfigurationBoolean(String Booleanpath) {//gets a boolean
		return plugin.getConfig().getBoolean(Booleanpath);
	}
	public int getConfigurationInt(String Intpath) {//gets an int
		return plugin.getConfig().getInt(Intpath);
	}
	public void reloadConfiguration() {
		plugin.reloadConfig();
	}
}