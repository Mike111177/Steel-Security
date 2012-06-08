package net.othercraft.steelsecurity.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

public class PlayerConfigManager extends SSCmdExe {
	
	protected String stringPathToDir = "players";
	
	public Main plugin;
	
	public PlayerConfigManager(String name, Main instance) {
		super(name, true);
		this.plugin = instance;
	}
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		
	}
	@EventHandler
	public void onLogOff(PlayerQuitEvent event) {
		
	}
	public void loadPlayerConfig(String playername) {
		
	}
	public FileConfiguration getConfig(String playername){
		  File directory = new File(stringPathToDir);
		  if(!directory.isDirectory()){
		    return null;//the directory doesnt exist, so the file doesnt exist. make sure to check null on the other side of the method and catch if the config doesnt exist!
		  }
		  File file = new File(directory, playername+".yml");
		  if(!file.exists()){
		    return null;//the file doesnt exist. Again, make sure to null check on the other end.
		  }
		  FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		  return config;
		}
	public boolean createConfig(String playername) throws IOException{
		  File directory = new File(stringPathToDir);
		  if(!directory.isDirectory()){
		    directory.mkdirs();
		  }
		  File file = new File(directory, playername+".yml");
		  if(file.exists()){
		    return false;//return false to indicate that the file already exists
		  }
		  file.createNewFile();
		  return true; //config file successfully created
		}
	public void saveConfig(FileConfiguration config, String playername) throws IOException{
		  File file = new File(stringPathToDir, playername+".yml");
		  if(!file.exists()){
		    file.createNewFile();
		  }
		  config.save(file);
		}

}