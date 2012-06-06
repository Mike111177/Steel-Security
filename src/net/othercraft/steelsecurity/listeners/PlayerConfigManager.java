package net.othercraft.steelsecurity.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

public class PlayerConfigManager extends SSCmdExe {
	
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
	public void loadPlayerConfig(Player player) {
		
	}

}