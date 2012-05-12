package net.othercraft.steelsecurity.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import net.othercraft.steelsecurity.Main;

public class Events implements Listener {

	public Events(Main main) {
		// TODO Auto-generated constructor stub
	}
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		PlayerChatListener.onChat(event);
	}

}
