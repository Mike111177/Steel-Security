package net.othercraft.steelsecurity.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import net.othercraft.steelsecurity.Main;

public class Events implements Listener {
	Main plugin;
	private PlayerChatListener chatListener;
	 
	public Events(Main instance) {
	plugin=instance;
	}
	public Events(PlayerChatListener chatListener) {
	this.chatListener = chatListener;
	}
	public
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
	chatListener.onChat(event);
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		PlayerMovementListener.onMove(event);
	}

}
