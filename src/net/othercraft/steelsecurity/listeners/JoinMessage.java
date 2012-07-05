package net.othercraft.steelsecurity.listeners;

import java.io.IOException;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinMessage extends SSCmdExe implements Listener {

    public Main plugin;

    public JoinMessage(String name, Main instance) {
	super("JoinMessage", true);// true only if its a listener, false if it
				   // isnt
	this.plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
	try {
	    if (plugin.getConfig().getBoolean("General.Logon_Message_Enabled")) {
		event.getPlayer().sendMessage(ChatColor.GREEN + plugin.getConfig().getString("General.Prefix") + " " + plugin.getConfig().getString("General.Logon_Message"));
	    }
	    Player player = event.getPlayer();
	    if (player.hasPermission("steelsecurity.notifications.update")) {
		double newVersion = plugin.getLatestVersion();
		double currentVersion = plugin.getCurrentVersion();
		if (newVersion > currentVersion) {
		    player.sendMessage(newVersion + " is out! You are running " + currentVersion);
		    player.sendMessage("Update Steel Security at: http://dev.bukkit.org/server-mods/steel-security");
		}
	    } 
	}
	catch (Exception e) {
	    try {
		catchListenerException(e, event.getEventName());
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	}
    }
}
