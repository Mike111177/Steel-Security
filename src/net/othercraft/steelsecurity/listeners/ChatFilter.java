package net.othercraft.steelsecurity.listeners;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.data.violations.ViolationsManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatFilter extends SSCmdExe {

    private transient final ViolationsManager vio;

    private final transient Map<String, Long> chattimes = new HashMap<String, Long>();// for tracking the speed of chat

    public ChatFilter(final SteelSecurity instance, final ViolationsManager viol) {
	super("ChatFilter", true, instance);// true only if its a listener, false if it isnt
	vio = viol;
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
	try {
	    final int speed = plugin.getConfig().getInt("AntiSpam.AntiFlood.Speed");
	    Boolean spam = true;// if this is true at the end cancel the event
	    final String name = event.getPlayer().getName();// name of the player for use in the hashmap
	    final Long time = System.currentTimeMillis();// current time
	    Long lasttime = chattimes.get(name);
	    if (lasttime == null){
		lasttime = Long.valueOf(time - (speed + 1));
	    }
	    final int check = (time.intValue() - lasttime.intValue());
	    chattimes.put(name, time);// overwrites the old time with the one
	    if (check > speed || !plugin.getConfig().getBoolean("AntiSpam.AntiFlood.Enabled") || !event.getPlayer().hasPermission("steelsecurity.bypass.antiflood")) {
		spam = false;// sets spam to false
	    }
	    String message = event.getMessage();// prepairs message for editing
	    if ((!spam)) {
		if (plugin.getConfig().getBoolean("AntiSpam.Censoring.Enabled") && !event.getPlayer().hasPermission("steelsecurity.bypass.censor")) {
		    final List<String> blist = (List<String>) plugin.getConfig().getList("AntiSpam.Censoring.Block_Words");
		    final List<String> glist = (List<String>) plugin.getConfig().getList("AntiSpam.Censoring.Allowed_Words");
		    final String[] mlist = message.split(" ");
		    String[] nmlist = mlist;
		    String nm = "";
		    int mindex = 0;
		    for (String cword : mlist) {
			Boolean correct = true;
			for (String gword : glist) {
			    if (cword.equalsIgnoreCase(gword)) {
				correct = false;
			    }
			}
			if (correct) {
			    for (String bword : blist) {
				if (cword.toLowerCase().contains(bword.toLowerCase())) {
				    int bindex = 0;
				    String nword = "";
				    while (bindex < bword.length()) {
					nword = nword + "*";
					bindex++;
				    }
				    nmlist[mindex] = mlist[mindex].replaceAll("(?i)" + bword, nword);
				}
			    }
			}
			mindex++;
		    }
		    for (String nnword : nmlist) {
			nm = nm + nnword + " ";
		    }
		    message = nm;
		}
		if (message.length() > plugin.getConfig().getInt("AntiSpam.Censoring.Canceling.Minimum_Length")) {//
		    if (plugin.getConfig().getBoolean("AntiSpam.Censoring.Canceling.Enabled") && !event.getPlayer().hasPermission("steelsecurity.bypass.censor")) {
			double percent = plugin.getConfig().getInt("AntiSpam.Censoring.Canceling.Percent");
			final int capcount = message.length();
			int capcounter = 0;
			Double uppercase = 0.0;
			Double lowercase = 0.0;
			while (capcounter < capcount) {
			    if (message.toCharArray()[capcounter] != ("*").toCharArray()[0]) {
				++lowercase;
			    } else {
				++uppercase;
			    }
			    ++capcounter;
			}
			final double total = uppercase + lowercase;
			final double result = uppercase / total;
			percent = percent / 100;
			if (percent < result) {
			    spam = true;
			}
		    }
		}
		if (message.length() > plugin.getConfig().getInt("AntiSpam.AntiCaps.Minimum_Length")) {//
		    if (plugin.getConfig().getBoolean("AntiSpam.AntiCaps.Enabled") && !event.getPlayer().hasPermission("steelsecurity.bypass.anticaps")) {
			double percent = plugin.getConfig().getInt("AntiSpam.AntiCaps.Percent");
			final int capcount = message.length();
			int capcounter = 0;
			Double uppercase = 0.0;
			Double lowercase = 0.0;
			while (capcounter < capcount) {
			    if (message.toCharArray()[capcounter] == message.toLowerCase().toCharArray()[capcounter]) {
				++lowercase;
			    } else {
				++uppercase;
			    }
			    ++capcounter;
			}
			final double total = uppercase + lowercase;
			final double result = uppercase / total;
			percent = percent / 100;
			if (percent < result) {
			    message = message.toLowerCase();
			}
		    }
		}
		event.setMessage(message);
	    }
	    if (spam) {
		event.setCancelled(true);
	    }
	} catch (Exception e) {
	    try {
		catchListenerException(e, event.getEventName());
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	}
    }

}
