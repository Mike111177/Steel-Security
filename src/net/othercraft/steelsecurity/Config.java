/**
 * @Class: Config
 * @Author: Mike111177
 * @Purpose: This is a class that will handle every action related to the config
 * @Extends: JavaPlugin
 *  */
package net.othercraft.steelsecurity;

import java.util.Arrays;

import org.bukkit.plugin.java.JavaPlugin;

import net.othercraft.steelsecurity.utils.ExtraConfigManager;
@SuppressWarnings("unused")
public final class Config {

    private final transient JavaPlugin plugin;
    private final transient ExtraConfigManager anticm;
    private final transient ExtraConfigManager log;
    private final transient ExtraConfigManager datac;
    private ExtraConfigManager tickc;

    public Config(final JavaPlugin instance,final ExtraConfigManager anticom,final ExtraConfigManager loge,final ExtraConfigManager datace) {
	plugin = instance;
	anticm = anticom;
	log = loge;
	datac = datace;
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
	plugin.getConfig().addDefault("AntiSpam.Censoring.Allowed_Words", Arrays.asList("Avocado"));
	plugin.getConfig().addDefault("AntiSpam.Censoring.Canceling.Enabled", false);
	plugin.getConfig().addDefault("AntiSpam.Censoring.Canceling.Minimum_Length", 4);
	plugin.getConfig().addDefault("AntiSpam.Censoring.Canceling.Percent", 50);
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