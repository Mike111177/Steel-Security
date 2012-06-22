/**
 * @Class: Config
 * @Author: Mike111177
 * @Purpose: This is a class that will handle every action related to the config
 * @Extends: JavaPlugin
 *  */
package net.othercraft.steelsecurity;

import java.util.Arrays;

public class Config {

    private Main plugin;

    public Config(Main instance) {
	this.plugin = instance;
    }

    public void loadConfiguration() {
	defaults();
	plugin.getConfig().options().copyDefaults(true);
	plugin.saveConfig();
    }

    private void defaults() { // Defaults
	plugin.getConfig().addDefault("General.Prefix", "[STS]");
	plugin.getConfig().addDefault("General.Logon_Message_Enabled", true);
	plugin.getConfig().addDefault("General.Logon_Message", "Steel Security is running on this server.");
	plugin.getConfig().addDefault("AntiSpam.AntiFlood.Enabled", true);
	plugin.getConfig().addDefault("AntiSpam.AntiFlood.Speed", 250);
	plugin.getConfig().addDefault("AntiSpam.Censoring.Enabled", false);
	plugin.getConfig().addDefault("AntiSpam.Censoring.Block_Words", Arrays.asList("Nodus", "Avo", "ICHG"));
	plugin.getConfig().addDefault("AntiSpam.Censoring.Canceling.Enabled", false);
	plugin.getConfig().addDefault("AntiSpam.Censoring.Canceling.Minimum_Length", 4);
	plugin.getConfig().addDefault("AntiSpam.Censoring.Canceling.Percent", 25);
	plugin.getConfig().addDefault("AntiSpam.AntiCaps.Enabled", true);
	plugin.getConfig().addDefault("AntiSpam.AntiCaps.Minimum_Length", 4);
	plugin.getConfig().addDefault("AntiSpam.AntiCaps.Percent", 70);
	plugin.getConfig().addDefault("Login_Limiter.Enabled", false);
	plugin.getConfig().addDefault("Login_Limiter.Time", 4);
	plugin.getConfig().addDefault("Offline_GameMode_Changer.Enabled", false);
	plugin.getConfig().addDefault("Offline_GameMode_Changer.Default_GameMode", 0);
	plugin.getConfig().addDefault("Block_Blacklist.Enabled", false);
	plugin.getConfig().addDefault("Block_Blacklist.Blocks", Arrays.asList("7", "10", "11"));
    }
}