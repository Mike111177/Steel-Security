package net.othercraft.steelsecurity.commands;

import java.util.ArrayList;
import java.util.List;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.listeners.SpectateManager;
import net.othercraft.steelsecurity.utils.PlayerConfigManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;
import net.othercraft.steelsecurity.utils.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public final class Sts extends SSCmdExe {

    private final transient SpectateManager spm;

    private final transient GameModeCmdCatch gmcatch;

    private final transient Vanish vanishManager;

    public Sts(final SteelSecurity instance,final SpectateManager spminstance,final Vanish vanman,final GameModeCmdCatch gmcc) {
	super("Sts", false, instance);
	spm = spminstance;
	vanishManager = vanman;
	gmcatch = gmcc;
    }

    // Defines Chat Colors
    private static final ChatColor RED = ChatColor.RED;
    private static final ChatColor GREEN = ChatColor.GREEN;
    private static final ChatColor YELLOW = ChatColor.YELLOW;
    private static final ChatColor WHITE = ChatColor.WHITE;
    private static final ChatColor BLUE = ChatColor.BLUE;
    // Defines No Permission String
    private static final String NOPERM = (RED + "You don't have permission to do this!");
    private static final String PLAYERONLY = (RED + "This command can only be done by a player!");
    private static final String SPECTATEPERM = "steelsecurity.commands.spectate";

    // Receives command and takes actions.
    private void command(final CommandSender sender,final String[] args) {
	final Boolean isplayer = sender instanceof Player;
	if (args.length == 0) {
	    base(sender);
	} else if (args.length > 0) {
	    if (args[0].equalsIgnoreCase("help")) {
		help(sender, args);
	    } else if (args[0].equalsIgnoreCase("listop")) {
		listOP(sender);
	    } else if (args[0].equalsIgnoreCase("gamemode")) {
		gameMode(sender, args);
	    } else if (args[0].equalsIgnoreCase("checkgm")) {
		checkGM(sender, args);
	    } else if (args[0].equalsIgnoreCase("reload")) {
		reload(sender);
	    } else if (args[0].equalsIgnoreCase("spectate")) {
		spectate(sender, args, isplayer);
	    } else if (args[0].equalsIgnoreCase("spectateoff")) {
		spectateOff(sender, isplayer);
	    } else if (args[0].equalsIgnoreCase("vanish")) {
		vanish(sender, args, isplayer);
	    }
	}
    }

    private void vanish(final CommandSender sender,final String[] args,final Boolean isplayer) {
	if (isplayer) {
	    if (sender.hasPermission("steelsecurity.commands.vanish")) {
		vanishManager.vmCmd(sender, args);
	    } else {
		sender.sendMessage(NOPERM);
	    }
	} else {
	    sender.sendMessage(PLAYERONLY);
	}
    }

    private void spectateOff(final CommandSender sender,final Boolean isplayer) {
	if (isplayer) {
	    if (sender.hasPermission(SPECTATEPERM)) {
		final String[] nargs = { "spectate" };
		spm.specCmd(sender, nargs);
	    } else {
		sender.sendMessage(NOPERM);
	    }
	} else {
	    sender.sendMessage(PLAYERONLY);
	}
    }

    private void spectate(final CommandSender sender,final String[] args,final Boolean isplayer) {
	if (isplayer) {
	    if (sender.hasPermission(SPECTATEPERM)) {
		spm.specCmd(sender, args);
	    } else {
		sender.sendMessage(NOPERM);
	    }
	} else {
	    sender.sendMessage(PLAYERONLY);
	}
    }

    private void reload(final CommandSender sender) {
	if (sender.hasPermission("steelsecurity.commands.reload")) {
	    plugin.reloadConfig();
	    sender.sendMessage(GREEN + plugin.getConfig().getString("General.Prefix") + ": Config Reloaded.");
	} else {
	    sender.sendMessage(NOPERM);
	}
    }

    private void checkGM(final CommandSender sender,final String[] args) {
	if (sender.hasPermission("steelsecurity.commands.checkgm")) {
	    if (args.length == 2) {
		final String target = Bukkit.getServer().getPlayer(args[2]).getName();
		final FileConfiguration config = PlayerConfigManager.getConfig(target);
		final int gameMode = config.getInt("GameMode");
		sender.sendMessage(target + " is in" + GameMode.getByValue(gameMode).name() + "mode.");
	    }
	}
    }

    private void gameMode(final CommandSender sender,final String[] args) {
	if (sender.hasPermission("steelsecurity.commands.gamemode")) {
	    gmcatch.stsgamemode(sender, args);
	} else {
	    sender.sendMessage(NOPERM);
	}
    }

    private void listOP(final CommandSender sender) {
	if (sender.hasPermission("steelsecurity.commands.listop")) {
	    String list = "";
	    int total = 0;
	    int online = 0;
	    for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
		if (player.isOp()) {
		    total = total + 1;
		    if (player.isOnline()) {
			online = online + 1;
			if (list.equals("")) {
			    list = (list + GREEN + player.getName());
			} else {
			    list = (list + WHITE + ", " + GREEN + player.getName());
			}
		    } else {
			if (list.equals("")) {
			    list = (list + RED + player.getName());
			} else {
			    list = (list + WHITE + ", " + RED + player.getName());
			}
		    }
		}
	    }
	    if (list.equals("")) {
		sender.sendMessage(RED + "Sorry, there appears to be no ops on this server!");
	    } else {
		sender.sendMessage(WHITE + "There are " + BLUE + online + WHITE + " out of " + BLUE + total + WHITE + " ops online.");
		sender.sendMessage(list);
	    }
	} else {
	    sender.sendMessage(NOPERM);
	}
    }

    private void help(final CommandSender sender,final String[] args) {
	if (sender.hasPermission("steelsecurity.commands.stshelp")) {
	    if (args.length == 1) {
		p1(sender);
	    } else if (args.length == 2) {
		if (Tools.isSafeNumber(args[1])) {
		    final int page = Integer.parseInt(args[1]);
		    p1(sender, page);
		} else {
		    sender.sendMessage("Page must be a number!");
		}
	    } else {
		sender.sendMessage(RED + "Too many arguments!");
	    }
	} else {
	    sender.sendMessage(NOPERM);
	}
    }

    private void base(final CommandSender sender) {
	if (sender.hasPermission("steelsecurity.commands.sts")) {
	sender.sendMessage(GREEN + "This server is running Steel Security");
	sender.sendMessage(GREEN + "For a list of commands please type /sts help");
	} else {
	sender.sendMessage(NOPERM);
	}
    }

    private void p1(final CommandSender sender) {
	p1(sender, 1);
    }

    private void p1(final CommandSender sender,final int page) {
	List<String> allowcmds = new ArrayList<String>();
	if (sender.hasPermission("steelsecurity.commands.sts")) {
	    allowcmds.add(GREEN + "/sts:" + YELLOW + " Base Command");
	}
	if (sender.hasPermission("steelsecurity.commands.stshelp")) {
	    allowcmds.add(GREEN + "/sts help:" + YELLOW + " Displays this help screen.");
	}
	if (sender.hasPermission(SPECTATEPERM)) {
	    allowcmds.add(GREEN + "/sts spectate:" + YELLOW + " Shows you the world from the eyes of another player.");
	    allowcmds.add(GREEN + "/sts spectateoff:" + YELLOW + " Stops spectating.");
	}
	if (sender.hasPermission("steelsecurity.commands.vanish")) {
	    allowcmds.add(GREEN + "/sts vanish:" + YELLOW + " Makes you disapear.");
	}
	if (sender.hasPermission("steelsecurity.commands.listop")) {
	    allowcmds.add(GREEN + "/sts listop:" + YELLOW + " List ops.");
	}
	if (sender.hasPermission("steelsecurity.commands.checkgm")) {
	    allowcmds.add(GREEN + "/sts checkgm:" + YELLOW + " Checks what Game Mode a player is in.");
	}
	if (sender.hasPermission("steelsecurity.commands.reload")) {
	    allowcmds.add(GREEN + "/sts reload:" + YELLOW + " Reloads config.");
	}
	final int pages = Tools.getPages(allowcmds, 6);
	allowcmds = Tools.getPage(allowcmds, page, 6);
	sender.sendMessage("Displaying page " + page + " of " + pages + ":");
	for (String line : allowcmds) {
	    sender.sendMessage(line);
	}

    }

    @Override
    public boolean handleCommand(final CommandSender sender,final Command cmd,final String label,final String[] args) {
	command(sender, args);
	return true;
    }
}
