/**
 * @Class: ExtraConfigManager
 */
package net.othercraft.steelsecurity.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ExtraConfigManager {

    private FileConfiguration newConfig = null;
    private File configFile = null;
    private File dataFolder = null;

    public ExtraConfigManager(File dataFolder2, String configname) {

	dataFolder = dataFolder2;
	this.configFile = new File(dataFolder, configname + ".yml");
    }
    public FileConfiguration getConfig() {
	if (newConfig == null) {
	    reloadConfig();
	}
	return newConfig;
    }
    private void reloadConfig() {
	newConfig = YamlConfiguration.loadConfiguration(configFile);
    }
    
    public void saveConfig() {
	try {
	    getConfig().save(configFile);
	} catch (IOException ex) {
	    System.out.println("failed");
	}
    }

}
