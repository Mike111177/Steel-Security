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

public class Sts extends SSCmdExe {

    SteelSecurity plugin;

    SpectateManager spm;
    
    GameModeCmdCatch gmcatch;

    Vanish vm;

    public Sts(String name, SteelSecurity instance, SpectateManager spminstance, Vanish vanman, GameModeCmdCatch gmcc) {
	super("Sts", false);
	plugin = instance;
	spm = spminstance;
	vm = vanman;
	gmcatch = gmcc;
    }

    // Defines Chat Colors
    private static final ChatColor r = ChatColor.RED;
    private static final ChatColor g = ChatColor.GREEN;
    private static final ChatColor y = ChatColor.YELLOW;
    private static final ChatColor w = ChatColor.WHITE;
    private static final ChatColor b = ChatColor.BLUE;
    // Defines No Permission String
    private static final String noperm = (r + "You don't have permission to do this!");
    private static final String playeronly = (r + "This command can only be done by a player!");

    // Receives command and takes actions.
    private void command(CommandSender sender, String[] args) {
	Boolean isplayer = sender instanceof Player;
	if (args.length == 0) {
	    if (sender.hasPermission("steelsecurity.commands.sts")) {
		sender.sendMessage(g + "This server is running Steel Security");
		sender.sendMessage(g + "For a list of commands please type /sts help");
	    } else {
		sender.sendMessage(noperm);
	    }
	} else if (args.length > 0) {
	    if (args[0].equalsIgnoreCase("help")) {
		if (sender.hasPermission("steelsecurity.commands.stshelp")) {
		    if (args.length == 1) {
			p1(sender);
		    } else if (args.length == 2) {
			if (Tools.isSafeNumber(args[1])) {
			    int page = Integer.parseInt(args[1]);
			    p1(sender, page);
			}
			else {
			    sender.sendMessage("Page must be a number!");
			}
		    } else {
			sender.sendMessage(r + "Too many arguments!");
		    }
		} else {
		    sender.sendMessage(noperm);
		}
	    }
	    else if (args[0].equalsIgnoreCase("listop")) {
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
				    list = (list + g + player.getName());
				} else {
				    list = (list + w + ", " + g + player.getName());
				}
			    } else {
				if (list.equals("")) {
				    list = (list + r + player.getName());
				} else {
				    list = (list + w + ", " + r + player.getName());
				}
			    }
			}
		    }
		    if (list.equals("")) {
			sender.sendMessage(r + "Sorry, there appears to be no ops on this server!");
		    } else {
			sender.sendMessage(w + "There are " + b + online + w + " out of " + b + total + w + " ops online.");
			sender.sendMessage(list);
		    }
		} else {
		    sender.sendMessage(noperm);
		}
	    }
	    else if (args[0].equalsIgnoreCase("gamemode")) {
		if (sender.hasPermission("steelsecurity.commands.gamemode")) {
		    gmcatch.stsgamemode(sender, args);
		} else {
		    sender.sendMessage(noperm);
		}
	    }
	    else if (args[0].equalsIgnoreCase("checkgm")) {
		if (sender.hasPermission("steelsecurity.commands.checkgm")) {
		    if (args.length == 2) {
			String target = Bukkit.getServer().getPlayer(args[2]).getName();
			FileConfiguration config = PlayerConfigManager.getConfig(target);
			int gm = config.getInt("GameMode");
			sender.sendMessage(target + " is in" + GameMode.getByValue(gm).name() + "mode.");
		    }
		}
	    }
	    else if (args[0].equalsIgnoreCase("reload")) {
		if (sender.hasPermission("steelsecurity.commands.reload")) {
		    plugin.reloadConfig();
		    sender.sendMessage(g + plugin.getConfig().getString("General.Prefix") + ": Config Reloaded.");
		} else {
		    sender.sendMessage(noperm);
		}
	    }
	    else if (args[0].equalsIgnoreCase("spectate")) {
		if (isplayer) {
		    if (sender.hasPermission("steelsecurity.commands.spectate")) {
			spm.specCmd(sender, args);
		    } else {
			sender.sendMessage(noperm);
		    }
		} else {
		    sender.sendMessage(playeronly);
		}
	    }
	    else if (args[0].equalsIgnoreCase("spectateoff")) {
		if (isplayer) {
		    if (sender.hasPermission("steelsecurity.commands.spectate")) {
			String[] nargs = { "spectate" };
			spm.specCmd(sender, nargs);
		    } else {
			sender.sendMessage(noperm);
		    }
		} else {
		    sender.sendMessage(playeronly);
		}
	    }
	    else if (args[0].equalsIgnoreCase("vanish")) {
		if (isplayer) {
		    if (sender.hasPermission("steelsecurity.commands.vanish")) {
			vm.vmCmd(sender, args);
		    } else {
			sender.sendMessage(noperm);
		    }
		} else {
		    sender.sendMessage(playeronly);
		}
	    }
	}
    }
    
    private void p1(CommandSender sender){
	p1(sender, 1);
    }
    private void p1(CommandSender sender, int page) {
	List<String> allowcmds = new ArrayList<String>();
	if (sender.hasPermission("steelsecurity.commands.sts")) {
	    allowcmds.add(g + "/sts:" + y + " Base Command");
	}
	if (sender.hasPermission("steelsecurity.commands.stshelp")) {
	    allowcmds.add(g + "/sts help:" + y + " Displays this help screen.");
	}
	if (sender.hasPermission("steelsecurity.commands.spectate")) {
	    allowcmds.add(g + "/sts spectate:" + y + " Shows you the world from the eyes of another player.");
	}
	if (sender.hasPermission("steelsecurity.commands.spectate")) {
	    allowcmds.add(g + "/sts spectateoff:" + y + " Stops spectating.");
	}
	if (sender.hasPermission("steelsecurity.commands.vanish")) {
	    allowcmds.add(g + "/sts vanish:" + y + " Makes you disapear.");
	}
	if (sender.hasPermission("steelsecurity.commands.listop")) {
	    allowcmds.add(g + "/sts listop:" + y + " List ops.");
	}
	if (sender.hasPermission("steelsecurity.commands.gamemode")) {
	    allowcmds.add(g + "/sts gamemode:" + y + " Changes the gamemode of another player.");
	}
	if (sender.hasPermission("steelsecurity.commands.checkgm")) {
	    allowcmds.add(g + "/sts checkgm:" + y + " Checks what Game Mode a player is in.");
	}
	if (sender.hasPermission("steelsecurity.commands.reload")) {
	    allowcmds.add(g + "/sts reload:" + y + " Reloads config.");
	}
	int pages = Tools.getPages(allowcmds, 6);
	allowcmds = Tools.getPage(allowcmds, page, 6);
	sender.sendMessage("Displaying page " + page + " of " + pages + ":");
	for (String line : allowcmds){
	    sender.sendMessage(line);
	}
	
    }

    @Override
    public boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
	command(sender, args);
	return true;
    }
}
