package net.othercraft.steelsecurity.listeners;

import net.othercraft.steelsecurity.proccesses.censoredWordProccess;

import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener {

	public static void onChat(PlayerChatEvent event) {
		censoredWordProccess.onSpeak(event);
		
	}

}
