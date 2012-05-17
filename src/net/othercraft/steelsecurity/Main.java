package net.othercraft.steelsecurity;

import java.util.Arrays;
import java.util.List;

import net.othercraft.steelsecurity.commands.Commands;
import net.othercraft.steelsecurity.listeners.PlayerChatListener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
    private final PlayerChatListener pcl = new PlayerChatListener();
    private Commands myExecutor;
	
	public void onEnable() {
		myExecutor = new Commands(this);
		PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(pcl, this);
		getCommand("sts").setExecutor(myExecutor);
		getCommand("freezeall").setExecutor(myExecutor);
		new Config().loadConfiguration();
	}
	     
	public void onDisable() {
	}
}