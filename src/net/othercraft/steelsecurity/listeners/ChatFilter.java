package net.othercraft.steelsecurity.listeners;

import java.util.List;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatFilter extends SSCmdExe implements Listener {

	public Main plugin;

	public ChatFilter(String name, Main instance) {
		super(name, true);//true only if its a listener, false if it isnt
		this.plugin = instance;
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		try {
			String message = event.getMessage();
			if (plugin.getConfig().getBoolean("AntiSpam.Censoring.Enabled") && event.getPlayer().hasPermission("steelsecurity.antispam.bypasscensor") == false) {
				@SuppressWarnings("unchecked")
				List<String> list =  (List<String>) plugin.getConfig().getList("AntiSpam.Censoring.Block_Words");
				int wordcount = list.size();
				int wordcounter = 0;
				while (wordcounter<wordcount) {
					String newword;
					String badword = list.get(wordcounter);	 	
					int lettercount = list.get(wordcounter).toCharArray().length;
					int lettercounter = 0;
					newword = "";
					while (lettercounter < lettercount) {
						newword = (newword + "*");
						lettercounter = lettercounter + 1;
					}
					message = message.replaceAll("(?i)" + badword, newword);
					wordcounter = wordcounter + 1;  
				}	
			} 

			if (event.getMessage().length()>plugin.getConfig().getInt("AntiSpam.AntiCaps.Minimum_Length")){
				if (plugin.getConfig().getBoolean("AntiSpam.AntiCaps.Enabled") && event.getPlayer().hasPermission("steelsecurity.antispam.bypassanticaps") == false) {
					double percent = plugin.getConfig().getInt("AntiSpam.AntiCaps.Percent");
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
						System.out.println(lowercase + ":" + uppercase);
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
		catch (Exception e){
			catchListenerException(e);
		}
	}

}
