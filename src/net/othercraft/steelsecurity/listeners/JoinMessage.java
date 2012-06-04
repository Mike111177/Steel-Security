package net.othercraft.steelsecurity.listeners;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinMessage extends SSCmdExe implements Listener {

	public Main plugin;

	public JoinMessage(String name, Main instance) {
		super(name, true);//true only if its a listener, false if it isnt
		this.plugin = instance;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		try {
			if (plugin.getConfig().getBoolean("General.Logon_Message_Enabled")) {
				event.getPlayer().sendMessage(ChatColor.GREEN + plugin.getConfig().getString("General.Prefix") + " " + plugin.getConfig().getString("General.Logon_Message"));
			}
		}
		catch (Exception e) {
			catchListenerException(e);
		}
	}
}
