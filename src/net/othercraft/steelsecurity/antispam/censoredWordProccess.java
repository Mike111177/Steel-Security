package net.othercraft.steelsecurity.antispam;


import java.util.List;

import net.othercraft.steelsecurity.Main;

import org.bukkit.event.player.PlayerChatEvent;

public class CensoredWordProccess{
	
	private Main plugin;
	public CensoredWordProccess(Main plugin) {
	this.plugin = plugin;
	}
	
	public void onSpeak(PlayerChatEvent event) {
		String request = "Anti_Spam.censoring.blocked_words";
	    List<String> words = plugin.findConfigValueList(request); 
	}

}
