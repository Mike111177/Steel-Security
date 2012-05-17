package net.othercraft.steelsecurity.listeners;

import net.othercraft.steelsecurity.antispam.*;

import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener {

	public void onChat(PlayerChatEvent event) {
		CensoredWordProccess.onSpeak(event);
		
	}

}
