package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.commands.Commands;
import net.othercraft.steelsecurity.listeners.ChatFilter;
import net.othercraft.steelsecurity.listeners.LoginMessage;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
    private final ChatFilter pcl = new ChatFilter(this);
    private final LoginMessage pll = new LoginMessage(this);
    private final Config cfg = new Config(this);
    private Commands myExecutor;
	
	public void onEnable() {
		commandExecutors();
        registerEvents();
		cfg.loadConfiguration();
	}   
	private void commandExecutors() {
		myExecutor = new Commands(this);
		getCommand("sts").setExecutor(myExecutor);
	}
	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(pcl, this);
        pm.registerEvents(pll, this);	
	}
	public void onDisable() {
	}
}