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
		plugin.getConfig().options().copyDefaults(true); // NOTE: You do not have to use "plugin." if the class extends the java plugin
		plugin.saveConfig();
	}	
	public List<?> getConfigurationList(String Listpath) {
		List<?> list = plugin.getConfig().getList(Listpath);
		return list;
	}
	public void getConfigurationString(String Stringpath){
		plugin.getConfig().getString(Stringpath);
	}
	public void getConfigurationBoolean(String Booleanpath) {
		plugin.getConfig().getBoolean(Booleanpath);
	}
	public void getConfigurationInt(String Intpath) {
		plugin.getConfig().getInt(Intpath);
	}
}