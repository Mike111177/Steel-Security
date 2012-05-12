package net.othercraft.steelsecurity.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import net.othercraft.steelsecurity.Main;

public class Events implements Listener {
	Main plugin;

	public Events(Main instance) {
		plugin=instance;
	}
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		PlayerChatListener.onChat(event);
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		PlayerMovementListener.onMove(event);
	}

}
