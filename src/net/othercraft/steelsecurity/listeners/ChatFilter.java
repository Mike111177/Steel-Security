package net.othercraft.steelsecurity.listeners;

import java.util.List;

import net.othercraft.steelsecurity.Config;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatFilter extends JavaPlugin implements Listener {
	
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
	try{
		String message = event.getMessage();
		if (getConfig().getBoolean("AntiSpam.Censoring.Enabled") && event.getPlayer().hasPermission("steelsecurity.antispam.bypasscensor") == false) {
		 String[] list = (String[]) getConfig().getList("AntiSpam.Censoring.Block_Words").toArray();
		 int wordcount = list.length;
		 int wordcounter = 0;
		 while (wordcounter<wordcount) {
			 int lettercount = list[wordcounter].toCharArray().length;
			 int lettercounter = 0;
			 String newword = "";
			 String badword = list[wordcounter].toString();	 	 
			 while (lettercounter<lettercount) {
				 newword = (newword + "*");
				 lettercounter = lettercounter + 1; 
			 }
			 message = message.replaceAll("(?i)" + badword, newword);
			 wordcounter = wordcounter + 1;  
		 } 
		}
		if (event.getMessage().length()>getConfig().getInt("AntiSpam.AntiCaps.Minimum_Length")){
			if (getConfig().getBoolean("AntiSpam.AntiCaps.Enabled") && event.getPlayer().hasPermission("steelsecurity.antispam.bypassanticaps") == false) {
				int percent = getConfig().getInt("AntiSpam.AntiCaps.Percent");
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
	} catch(Exception e){//note, catch GENERIC exception
		catchListenerException(e);
	}
	}

}
