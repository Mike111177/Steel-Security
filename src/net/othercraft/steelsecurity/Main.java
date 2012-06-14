package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.commands.GameModeCmdCatch;
import net.othercraft.steelsecurity.commands.Sts;
import net.othercraft.steelsecurity.listeners.ChatFilter;
import net.othercraft.steelsecurity.listeners.JoinMessage;
import net.othercraft.steelsecurity.listeners.LoginLimiter;
import net.othercraft.steelsecurity.listeners.PlayerConfigListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main instance;

	private Sts base;

	@SuppressWarnings("unused")
	private ChatFilter cf;
	@SuppressWarnings("unused")
	private JoinMessage jm;
	@SuppressWarnings("unused")
	private LoginLimiter ll;
	@SuppressWarnings("unused")
	private PlayerConfigListener pcl;
	@SuppressWarnings("unused")
	private GameModeCmdCatch gmcc;


	public void onEnable(){
		new Config(this).loadConfiguration();
		instance = this;
		registerListeners();
		commands();
	}
	private void commands() {//register commands here
		base = new Sts("base", this);
		getCommand("sts").setExecutor(base);
	}
	private void registerListeners() {//register listeners here
		cf = new ChatFilter(null, this);
		jm = new JoinMessage(null, this);
		ll = new LoginLimiter(null, this);
		pcl = new PlayerConfigListener(null, this);
		gmcc = new GameModeCmdCatch(null, this);
	}

	public void onDisable(){
	}
}