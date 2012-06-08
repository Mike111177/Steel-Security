package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.commands.Sts;
import net.othercraft.steelsecurity.listeners.ChatFilter;
import net.othercraft.steelsecurity.listeners.JoinMessage;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main instance;

	private Sts base;
	
	private ChatFilter cf;
	private JoinMessage jm;

	public void onEnable(){
		new Config(this).loadConfiguration();
		instance = this;
		registerListeners();
		commands();
	}
	private void commands() {//register commands here
		base = new Sts("base");
		getCommand("sts").setExecutor(base);
	}
	private void registerListeners() {//register listeners here
		PluginManager pm = Bukkit.getPluginManager();
		cf = new ChatFilter(null, this);
		jm = new JoinMessage(null, this);
		pm.registerEvents(cf, this);
		pm.registerEvents(jm, this);
	}
	
	public void onDisable(){
	}
}