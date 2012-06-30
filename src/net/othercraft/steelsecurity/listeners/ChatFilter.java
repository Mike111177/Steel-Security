package net.othercraft.steelsecurity.listeners;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.data.Violations;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatFilter extends SSCmdExe {

    public Main plugin;

    public Violations vio;

    private Map<String, Long> chattimes = new HashMap<String, Long>();// for
								      // tracking
								      // the
								      // speed
								      // of
								      // chat

    public ChatFilter(String name, Main instance, Violations viol) {
	super("ChatFilter", true);// true only if its a listener, false if it
				  // isnt
	plugin = instance;
	vio = viol;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
	try {
	    int speed = plugin.getConfig().getInt("AntiSpam.AntiFlood.Speed");
	    Boolean spam = true;// if this is true at the end cancel the event
	    String name = event.getPlayer().getName();// name of the player for use in the hashmap
	    Long time = System.currentTimeMillis();// current time
	    Long lasttime = chattimes.get(name);
	    if (lasttime == null)
		lasttime = Long.valueOf(time - (speed + 1));
	    int check = (time.intValue() - lasttime.intValue());
	    chattimes.put(name, time);// overwrites the old time with the new
				      // one
	    if (check > speed || !plugin.getConfig().getBoolean("AntiSpam.AntiFlood.Enabled") || !event.getPlayer().hasPermission("steelsecurity.bypass.antiflood")) {
		spam = false;// sets spam to false
	    }
	    String message = event.getMessage();// prepairs message for editing
	    if ((!spam)) {
		if (plugin.getConfig().getBoolean("AntiSpam.Censoring.Enabled") && !event.getPlayer().hasPermission("steelsecurity.bypass.censor")) {
		    @SuppressWarnings("unchecked")
		    List<String> blist = (List<String>) plugin.getConfig().getList("AntiSpam.Censoring.Block_Words");
		    @SuppressWarnings("unchecked")
		    List<String> glist = (List<String>) plugin.getConfig().getList("AntiSpam.Censoring.Allowed_Words");
		    String[] mlist = message.split(" ");
		    String[] nmlist = message.split(" ");
		    String nm = "";
		    for (String bword : blist) {
			if (message.toLowerCase().contains(bword.toLowerCase())) {
			    int bindexer = 0;
			    String nword = "";
			    while (bindexer < bword.length()) {
				nword = (nword + "*");
				bindexer++;
			    }
			    int mindex = 0;
			    for (String mword : mlist) {
				for (String gword : glist) {
				    if (!gword.equalsIgnoreCase(mword)) {
					nmlist[mindex] = nmlist[mindex].replaceAll("(?i)" + bword, nword);
				    }
				    mindex++;
				}

			    }
			    for (String nnword : nmlist) {
				nm = nm + nnword + " ";
			    }
			}
		    }
		    if (!nm.equals("")) {
			message = nm;
		    }
		    event.setMessage(message);
		}
		if (event.getMessage().length() > plugin.getConfig().getInt("AntiSpam.Censoring.Canceling.Minimum_Length")) {//
		    if (plugin.getConfig().getBoolean("AntiSpam.Censoring.Canceling.Enabled") && !event.getPlayer().hasPermission("steelsecurity.bypass.censor")) {
			double percent = plugin.getConfig().getInt("AntiSpam.Censoring.Canceling.Percent");
			int capcount = message.length();
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
			double total = uppercase + lowercase;
			double result = uppercase / total;
			percent = percent / 100;
			if (percent < result) {
			    spam = true;
			}
		    }
		}
		if (event.getMessage().length() > plugin.getConfig().getInt("AntiSpam.AntiCaps.Minimum_Length")) {//
		    if (plugin.getConfig().getBoolean("AntiSpam.AntiCaps.Enabled") && !event.getPlayer().hasPermission("steelsecurity.bypass.anticaps")) {
			double percent = plugin.getConfig().getInt("AntiSpam.AntiCaps.Percent");
			int capcount = message.length();
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
			double total = uppercase + lowercase;
			double result = uppercase / total;
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
