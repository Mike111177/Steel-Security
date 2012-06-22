package net.othercraft.steelsecurity.utils;

import java.io.File;
import java.io.IOException;

import net.othercraft.steelsecurity.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class PlayerConfigManager {

	public static FileConfiguration getConfig(String playername) {
		File directory = new File(Main.instance.getDataFolder()
				+ File.separator + "Players");
		if (!directory.isDirectory()) {
			return null;// the directory doesnt exist, so the file doesnt exist.
						// make sure to check null on the other side of the
						// method and catch if the config doesnt exist!
		}
		File file = new File(directory, playername + ".yml");
		if (!file.exists()) {
			return null;// the file doesnt exist. Again, make sure to null check
						// on the other end.
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config;
	}

	public static boolean createConfig(String playername) throws IOException {
		File directory = new File(Main.instance.getDataFolder()
				+ File.separator + "Players");
		if (!directory.isDirectory()) {
			directory.mkdirs();
		}
		File file = new File(directory, playername + ".yml");
		if (file.exists()) {
			return false;// return false to indicate that the file already
							// exists
		}
		file.createNewFile();
		return true; // config file successfully created
	}

	public static void saveConfig(FileConfiguration config, String playername)
			throws IOException {
		File file = new File(Main.instance.getDataFolder() + File.separator
				+ "Players", playername + ".yml");
		if (!file.exists()) {
			file.createNewFile();
		}
		config.save(file);
	}

}