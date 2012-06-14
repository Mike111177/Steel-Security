package net.othercraft.steelsecurity.commands;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class GameModeCmdCatch extends SSCmdExe {
	Main plugin;
	public GameModeCmdCatch(String name, Main instance) {
		super("GameModeCmdCatch", true);//true only if its a listener, false if it isnt
		this.plugin = instance;
	}
	@EventHandler
	public void onGmChangeCmd(PlayerCommandPreprocessEvent event){
		if (event.getMessage().toLowerCase().startsWith("/gm") || event.getMessage().toLowerCase().startsWith("/gamemode")) {
			event.getPlayer().sendMessage("Please use /sts gamemode instead of this command");
			event.setCancelled(true);
		}
	}
	public void stsgamemode(CommandSender sender, String[] args) {
		Boolean gmcheck = plugin.getConfig().getBoolean("Offline_GameMode_Changer.Enabled");
		String usage = "/sts gamemode <player> <Game Mode>";
		if (args.length<3){
			sender.sendMessage("Not enough arguments!");
			sender.sendMessage(usage);
		}
		else if (args.length>3) {
			sender.sendMessage("Too many arguments!");
			sender.sendMessage(usage);
		}
		else {
			Player target = Bukkit.getPlayer(args[1]);
			if (target.isOnline()){
				switch (args[1]){
				case "1":
					target.setGameMode(GameMode.getByValue(1));
					break;
				case "0":
					target.setGameMode(GameMode.getByValue(0));
					break;
				case "(?i)creative":
					target.setGameMode(GameMode.getByValue(1));
					break;
				case "(?i)survival":
					target.setGameMode(GameMode.getByValue(0));
					break;
				default:
					sender.sendMessage("Unknown game mode: " + args[1]);
					break;
				}
			}
			else {
				
			}

		}
	}
}
