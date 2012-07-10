package net.othercraft.steelsecurity.listeners;

import java.util.HashMap;
import java.util.Map;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class LoginLimiter extends SSCmdExe {

    public SteelSecurity plugin;

    private Map<String, Long> logintimes = new HashMap<String, Long>();// for
								       // tracking
								       // the
								       // speed
								       // of
								       // chat

    public LoginLimiter(String name, SteelSecurity instance) {
	super("LoginLimiter", true);// true only if its a listener, false if it
				    // isnt
	this.plugin = instance;
    }

    @EventHandler
    public void onPlayerlogon(PlayerLoginEvent event) {
	if (plugin.getConfig().getBoolean("Login_Limiter.Enabled") && !event.getPlayer().hasPermission("steelsecurity.bypass.loginlimiter")) {
	    int seconds = plugin.getConfig().getInt("Login_Limiter.Time");
	    int speed = seconds * 1000;
	    String name = event.getPlayer().getName();// name of the player for
						      // use in the hashmap
	    Long time = System.currentTimeMillis();// current time
	    Long lasttime = logintimes.get(name);// last time the player has
						 // logged on
	    if (lasttime == null)
		lasttime = Long.valueOf(time - (speed + 1));// default if the
							    // player hasn't
							    // logged on yet
	    int check = (time.intValue() - lasttime.intValue());// used to
								// compare to
								// the
								// configured
								// speed
	    if (speed > check) {
		event.disallow(Result.KICK_OTHER, "Logged in to quickly! Please wait " + seconds + " seconds before logging in!");
	    } else {
		logintimes.put(name, time);// overwrites the old time with the
					   // new one
	    }
	}
    }
}