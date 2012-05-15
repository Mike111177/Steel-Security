package net.othercraft.steelsecurity.listeners;

import net.othercraft.steelsecurity.antispam.*;

import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener {

	public static void onChat(PlayerChatEvent event) {
		AntiFlood.onSpeak(event);
		AntiCaps.onSpeak(event);
		CensoredWordProccess.onSpeak(event);
		
	}

}
