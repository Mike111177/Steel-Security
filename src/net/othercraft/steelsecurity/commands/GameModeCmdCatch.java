package net.othercraft.steelsecurity.commands;

import java.io.IOException;
import java.util.Set;


import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.PlayerConfigManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;
import net.othercraft.steelsecurity.utils.Tools;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public final class GameModeCmdCatch extends SSCmdExe {
    
    private final static String USAGE = "/gm <player> <Game Mode>|/gamemode <player> <Game Mode>";

    public GameModeCmdCatch(final SteelSecurity instance) {
	super("GameModeCmdCatch", true, instance);// true only if its a listener, false if it isnt
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGmChange(final PlayerGameModeChangeEvent event) {
	if (!event.isCancelled()) {
	    if (plugin.getConfig().getBoolean("Offline_GameMode_Changer.Enabled")) {
		if (configSet(event.getPlayer(), event.getNewGameMode())) {
		    plugin.getLogger().info(event.getPlayer().getName() + "'s gamemode has been changed to " + event.getNewGameMode().name() + " in Steel Security's Config files.");
		}
	    }
	}
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGmChangeCmd(final PlayerCommandPreprocessEvent event) {
	String message = event.getMessage();
	if (!event.isCancelled()) {
	    if (message.toLowerCase().startsWith("/gm") || message.toLowerCase().startsWith("/gamemode")) {
		if (message.toLowerCase().startsWith("/gm")) {
		    message = message.toLowerCase().replaceAll("/gm", "/sts gamemode");
		} else if (message.toLowerCase().startsWith("/gamemode")) {
		    message = message.toLowerCase().replaceAll("/gamemode", "/sts gamemode");
		}
		event.setMessage(message);
	    }
	}
    }

    public void stsgamemode(final CommandSender sender, final String[] args) {
	GameMode gameMode;
	String gmn;
	final Boolean gmcheck = plugin.getConfig().getBoolean("Offline_GameMode_Changer.Enabled");
	if (args.length < 3) {
	    sender.sendMessage("Not enough arguments!");
	    sender.sendMessage(USAGE);
	} else if (args.length > 3) {
	    sender.sendMessage("Too many arguments!");
	    sender.sendMessage(USAGE);
	} else {
	    final Player target = decodePlayer(args[1], sender);
	    if (target != null) {
		if (target.isOnline()) {
		    gameMode = decodeGM(args[2]);
		    if (gameMode != null) {
			if (gmcheck) {
			    if (!configSet(target, gameMode)) {
				sender.sendMessage("We could not find a player with that name registered.");
			    }
			}
			gmn = gameMode.name();
			target.setGameMode(gameMode);
			sender.sendMessage(target.getName() + "'s game mode has been set to " + gmn + ".");
		    }
		    else {
			sender.sendMessage("Please set Offline_GameMode_Changer.Enabled to true in the config in order to change the gamemode of an offline player.");
		    }
		} else {
		    if (gmcheck) {
			gameMode = decodeGM(args[2]);
			if (gameMode != null) {
			    gmn = gameMode.name();
			    if (!configSet(target, gameMode)) {
				sender.sendMessage("We could not find a player with that name registered.");
			    } else {
				sender.sendMessage(target.getName() + "'s game mode will be set to " + gmn + " next time they log on.");
			    }
			}
			else {
			    sender.sendMessage("Unkown gamemode: " + args[2]);
			}
		    } else {
			sender.sendMessage("Please set Offline_GameMode_Changer.Enabled to true in the config in order to change the gamemode of an offline player.");
		    }
		}
	    }
	}
    }

    private Boolean configSet(final OfflinePlayer target,final GameMode gameMode) {
	final FileConfiguration config = PlayerConfigManager.getConfig(target.getName());
	Boolean result = false;
	if (config != null) {
	    config.set("GameMode", gameMode.getValue());
	    try {
		PlayerConfigManager.saveConfig(config, target.getName());
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    result = true;
	}
	return result;
    }

    private GameMode decodeGM(final String pregm) {
	if (pregm.equalsIgnoreCase("creative") || pregm.equalsIgnoreCase("1")) {
	    return (GameMode.CREATIVE);
	} else if (pregm.equalsIgnoreCase("survival") || pregm.equalsIgnoreCase("0")) {
	    return (GameMode.SURVIVAL);
	} else if (pregm.equalsIgnoreCase("adventure") || pregm.equalsIgnoreCase("2")){
	    return GameMode.ADVENTURE;
	} else {
	 return null;   
	}
    }

    private Player decodePlayer(final String pname,final CommandSender sender) {
	final Set<Player> list = Tools.safePlayer(pname);
	Player result = null;
	switch (list.size()) {
	case 1:
	    result = (Player) list.toArray()[1];
	case 0:
	    sender.sendMessage("Were sorry, we could not find anyone with the name of " + pname);
	default:
	    sender.sendMessage("We found " + list.size() + " that match your criteria. Please be more specific.");
	    String nlist = "";
	    for (Player scan : list) {
		nlist = (list + ", " + scan.getName());
	    }
	    if (!("").equals(nlist)) {
		nlist = nlist.replaceFirst(",", "");
	    }
	    sender.sendMessage("The players we found are" + nlist + ".");
	}
	return result;
    }
}