package net.othercraft.steelsecurity.commands;

import java.io.IOException;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.PlayerConfigManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class GameModeCmdCatch extends SSCmdExe {
    Main plugin;

    public GameModeCmdCatch(String name, Main instance) {
	super("GameModeCmdCatch", true);// true only if its a listener, false if
					// it isnt
	this.plugin = instance;
    }

    @EventHandler
    public void onGmChangeCmd(PlayerCommandPreprocessEvent event) {
	if (event.getMessage().toLowerCase().startsWith("/gm") || event.getMessage().toLowerCase().startsWith("/gamemode")) {
	    String message = event.getMessage();
	    if (event.getMessage().toLowerCase().startsWith("/gm")) {
		message = message.toLowerCase().replaceAll("/gm", "/sts gamemode");
	    } else if (event.getMessage().toLowerCase().startsWith("/gamemode")) {
		message = message.toLowerCase().replaceAll("/gamemode", "/sts gamemode");
	    }
	    event.setMessage(message);
	}
    }

    public void stsgamemode(CommandSender sender, String[] args) {
	GameMode gm;
	Boolean gmcheck = plugin.getConfig().getBoolean("Offline_GameMode_Changer.Enabled");
	String usage = "/sts gamemode <player> <Game Mode>";
	if (args.length < 3) {
	    sender.sendMessage("Not enough arguments!");
	    sender.sendMessage(usage);
	} else if (args.length > 3) {
	    sender.sendMessage("Too many arguments!");
	    sender.sendMessage(usage);
	} else {
	    Player target = Bukkit.getPlayerExact(args[1]);
	    if (target.isOnline()) {
		gm = decodeGM(args[2], sender);
		if (gmcheck) {
		    if (gm != null) {
			configSet(target, gm, sender);
		    }
		}
		target.setGameMode(gm);
	    } else {
		if (gmcheck) {
		    gm = decodeGM(args[2], sender);
		    if (gm != null) {
			configSet(target, gm, sender);
		    }
		} else {
		    sender.sendMessage("Please set Offline_GameMode_Changer.Enabled to true in the config in order to change the gamemode of an offline player.");
		}
	    }
	}
    }

    private void configSet(Player target, GameMode gm, CommandSender sender) {
	FileConfiguration config = PlayerConfigManager.getConfig(target.getName());
	if (config != null) {
	    config.set("GameMode", gm.getValue());
	    try {
		PlayerConfigManager.saveConfig(config, target.getName());
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	} else {
	    sender.sendMessage("There are no users registered with the name: " + target.getName());
	}
    }

    private GameMode decodeGM(String pregm, CommandSender sender) {
	GameMode gm = null;
	if (pregm.equalsIgnoreCase("creative") || pregm.equalsIgnoreCase("1")) {
	    gm = (GameMode.getByValue(1));
	} else if (pregm.equalsIgnoreCase("survival") || pregm.equalsIgnoreCase("0")) {
	    gm = (GameMode.getByValue(0));
	} else {
	    sender.sendMessage("Unknown game mode: " + pregm);
	}
	return gm;
    }
}