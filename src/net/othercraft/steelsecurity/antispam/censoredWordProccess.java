package net.othercraft.steelsecurity.antispam;


import java.util.List;

import net.othercraft.steelsecurity.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerChatEvent;

public class CensoredWordProccess{
	
	private Main plugin;
	// the line below makes me add void
	public CensoredWordProccess(Main plugin) {
	this.plugin = plugin;
	}
	
	public void onSpeak(PlayerChatEvent event) {
		String request = "Anti_Spam.censoring.blocked_words";
		//then this make me go to the main class and change findConfigValueList to static
	    List<String> words = plugin.findConfigValueList(request); 
	}

}
