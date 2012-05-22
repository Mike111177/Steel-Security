package net.othercraft.steelsecurity.listeners;
 
import java.util.List;
import net.othercraft.steelsecurity.Config;
import net.othercraft.steelsecurity.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
 
public class ChatFilter implements Listener {
	
public Main plugin;
	
	public ChatFilter(Main instance) {	
		plugin = instance;
	}	

	@EventHandler
    public void onPlayerchat(PlayerChatEvent event) {
		String message = event.getMessage();
		if (new Config(plugin).getConfigurationBoolean("AntiSpam.Censoring.Enabled") && event.getPlayer().hasPermission("steelsecurity.antispam.bypasscensor") == false) {
		 List<String> list = new Config(plugin).getConfigurationList("AntiSpam.Censoring.Block_Words");
		 int wordcount = list.size();
		 int wordcounter = 0;
		 while (wordcounter<wordcount) {
			 int lettercount = list.get(wordcounter).toCharArray().length;
			 int lettercounter = 0;
			 String newword = "";
			 String badword = list.get(wordcounter).toString();	 	 
			 while (lettercounter<lettercount) {
				 newword = (newword + "*");
				 lettercounter = lettercounter + 1; 
			 }
			 message = message.replaceAll("(?i)" + badword, newword);
			 wordcounter = wordcounter + 1;  
		 } 
		}
		if (event.getMessage().length()>new Config(plugin).getConfigurationInt("AntiSpam.AntiCaps.Minimum_Length")){
			if (new Config(plugin).getConfigurationBoolean("AntiSpam.AntiCaps.Enabled") && event.getPlayer().hasPermission("steelsecurity.antispam.bypassanticaps") == false) {
				int percent = new Config(plugin).getConfigurationInt("AntiSpam.AntiCaps.Percent");
				int capcount = message.length();
				int capcounter = 0;
				Double uppercase = 0.0;
				Double lowercase = 0.0;
				while (capcounter<capcount) {
					if (message.toCharArray()[capcounter] == message.toLowerCase().toCharArray()[capcounter]) {
						lowercase = lowercase + 1.0;
					}
					else {
						uppercase = uppercase + 1.0;
					}
					capcounter = capcounter + 1;
				}
				double total = uppercase + lowercase;
				double result = uppercase/total;
				percent = percent/100;
				if (percent<result){
					message = message.toLowerCase();
				}
			}
		}
		event.setMessage(message);
	}
}