package net.othercraft.steelsecurity;

import net.othercraft.steelsecurity.commands.GameModeCmdCatch;
import net.othercraft.steelsecurity.commands.Sts;
import net.othercraft.steelsecurity.commands.Vanish;
import net.othercraft.steelsecurity.listeners.BlockBlacklist;
import net.othercraft.steelsecurity.listeners.ChatFilter;
import net.othercraft.steelsecurity.listeners.JoinMessage;
import net.othercraft.steelsecurity.listeners.LoginLimiter;
import net.othercraft.steelsecurity.listeners.PlayerConfigListener;
import net.othercraft.steelsecurity.listeners.SpectateManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static Main instance;

	private Sts base;
	
	SpectateManager spc;

	@SuppressWarnings("unused")
	private ChatFilter cf;
	@SuppressWarnings("unused")
	private JoinMessage jm;
	@SuppressWarnings("unused")
	private LoginLimiter ll;
	private PlayerConfigListener pcl;
	@SuppressWarnings("unused")
	private GameModeCmdCatch gmcc;
	@SuppressWarnings("unused")
	private BlockBlacklist blbl;
	private SpectateManager spm;
	private Vanish vm;


	public void onEnable(){
		new Config(this).loadConfiguration();
		instance = this;
		registerListeners();
		commands();
		playerChecks();
	}
	private void playerChecks() {
		Player[] players = Bukkit.getOnlinePlayers();
		for (Player player : players) {
			pcl.checkPlayerConfig(player);
		}
		spm.registerAll();
	}
	private void commands() {//register commands here
		base = new Sts("base", this, spm);
		getCommand("sts").setExecutor(base);
	}
	private void registerListeners() {//register listeners here
		cf = new ChatFilter(null, this);
		jm = new JoinMessage(null, this);
		ll = new LoginLimiter(null, this);
		pcl = new PlayerConfigListener(null, this);
		gmcc = new GameModeCmdCatch(null, this);
		blbl = new BlockBlacklist(null, this);
		vm = new Vanish(null, this, spm);
		spm = new SpectateManager(null, this, vm);
	}

	public void onDisable(){
		spm.stopAll();
	}
}