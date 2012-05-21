package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.commands.Commands;
import net.othercraft.steelsecurity.listeners.PlayerChatListener;
import net.othercraft.steelsecurity.listeners.PlayerJoinListener;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
    private final PlayerChatListener pcl = new PlayerChatListener(this);
    private final PlayerJoinListener pll = new PlayerJoinListener(this);
    private Commands myExecutor;
	
	public void onEnable() {
		myExecutor = new Commands(this);
		PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(pcl, this);
        pm.registerEvents(pll, this);
		getCommand("sts").setExecutor(myExecutor);
		new Config(this).loadConfiguration();
	}
	     
	public void onDisable() {
	}
}