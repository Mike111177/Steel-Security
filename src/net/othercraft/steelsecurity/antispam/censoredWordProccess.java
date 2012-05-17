package net.othercraft.steelsecurity.antispam;


import java.util.List;

import net.othercraft.steelsecurity.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerChatEvent;

public class CensoredWordProccess{
	
	
	public static void onSpeak(PlayerChatEvent event) {
		String request = "Anti_Spam.censoring.blocked_words";
	    List<String> words = Main.findConfigValueList(request); 
	}

}
