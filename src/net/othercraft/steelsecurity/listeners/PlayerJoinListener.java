package net.othercraft.steelsecurity.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.othercraft.steelsecurity.Config;
import net.othercraft.steelsecurity.Main;

public class PlayerJoinListener implements Listener {

	public Main plugin;
	
	public PlayerJoinListener(Main instance) {	
		plugin = instance;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (new Config(plugin).getConfigurationBoolean("General.Logon_Message_Enabled")) {
			event.getPlayer().sendMessage(ChatColor.GREEN + new Config(plugin).getConfigurationString("General.Prefix") + " " + new Config(plugin).getConfigurationString("General.Logon_Message"));
		}
	}
}
