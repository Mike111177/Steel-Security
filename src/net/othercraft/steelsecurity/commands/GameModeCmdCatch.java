package net.othercraft.steelsecurity.commands;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class GameModeCmdCatch extends SSCmdExe {
	Main plugin;
	public GameModeCmdCatch(String name, Main instance) {
		super(name, true);//true only if its a listener, false if it isnt
		this.plugin = instance;
	}
	@EventHandler
	public void onGmChangeCmd(PlayerCommandPreprocessEvent event){
		if (event.getMessage().toLowerCase().startsWith("/gm") || event.getMessage().toLowerCase().startsWith("/gamemode")) {
			event.getPlayer().sendMessage("Please use /sts gamemode instead of this command");
			event.setCancelled(true);
		}
	}
	public static void stsgamemode(CommandSender sender, String[] args) {
		//TODO make this do something lol
	}
}
