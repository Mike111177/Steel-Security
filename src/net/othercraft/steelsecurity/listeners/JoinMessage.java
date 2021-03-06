package net.othercraft.steelsecurity.listeners;

import java.io.IOException;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class JoinMessage extends SSCmdExe implements Listener {

    public JoinMessage(final SteelSecurity instance) {
	super("JoinMessage", true, instance);// true only if its a listener, false if it isnt
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
	try {
	    if (plugin.getConfig().getBoolean("General.Logon_Message_Enabled")) {
		event.getPlayer().sendMessage(ChatColor.GREEN + plugin.getConfig().getString("General.Prefix") + " " + plugin.getConfig().getString("General.Logon_Message"));
	    }
	    final Player player = event.getPlayer();
	    if (player.hasPermission("steelsecurity.notifications.update")) {
		final double newVersion = plugin.getLatestVersion();
		final double currentVersion = plugin.getCurrentVersion();
		if (newVersion > currentVersion) {
		    player.sendMessage("Steel Security" + plugin.getLatestVersionName() + " is out!");
		    player.sendMessage("You are running Steel Security " + plugin.getCurrentVersionName());
		    player.sendMessage("Update Steel Security at: http://dev.bukkit.org/server-mods/steel-security");
		}
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
