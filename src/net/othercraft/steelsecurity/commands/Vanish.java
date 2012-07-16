package net.othercraft.steelsecurity.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.listeners.SpectateManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Vanish extends SSCmdExe {

    SteelSecurity plugin;

    SpectateManager spm;
    Logger log;

    public Vanish(String name, SteelSecurity instance, Logger log) {
	super("Vanish", true);
	plugin = instance;
	this.log = log;
    }

    public void specGet() {
	spm = plugin.spm;
    }

    private Map<String, Boolean> isvanished = new HashMap<String, Boolean>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
	Player player = event.getPlayer();
	isvanished.put(player.getName(), false);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
	Player player = event.getPlayer();
	isvanished.remove(player.getName());
    }

    public Boolean isVanished(Player player) {
	Boolean result;
	if (isvanished.get(player.getName()) != null) {
	    result = isvanished.get(player.getName());
	} else {
	    result = false;
	}
	return result;
    }

    public void setVanished(Player player, Boolean o, Boolean broadcast) {
	if (o) {
	    if (!isVanished(player)) {
		start(player, broadcast);
		isvanished.put(player.getName(), true);
	    }
	} else {
	    if (isVanished(player)) {
		stop(player, broadcast);
		isvanished.put(player.getName(), false);
	    }
	}
    }

    private void stop(Player tostop, Boolean notify) {
	if (isvanished.get(tostop.getName())) {
	    for (Player player : Bukkit.getOnlinePlayers()) {
		if (!player.hasPermission("steelsecurity.commands.vanish.cansee")) {
		    player.showPlayer(tostop);
		} else if (!player.getName().equals(tostop.getName()) && notify) {
		    player.sendMessage(tostop.getName() + " is visable.");
		}
	    }
	    if (notify) {
		tostop.sendMessage("You are now visable.");
		log.info(tostop.getName() + " is now visable.");
	    } else {
		tostop.sendMessage("You were forced visable either because of a reload or you begun spectating.");
	    }
	}
    }

    private void start(Player tostart, Boolean notify) {
	if (!isvanished.get(tostart.getName())) {
	    for (Player player : Bukkit.getOnlinePlayers()) {
		if (!player.hasPermission("steelsecurity.commands.vanish.cansee")) {
		    player.hidePlayer(tostart);
		} else if (!player.getName().equals(tostart.getName()) && notify) {
		    player.sendMessage(tostart.getName() + " has disapeared.");
		}
	    }
	    if (notify) {
		tostart.sendMessage("You are now invisable.");
		log.info(tostart.getName() + " has gone invisable.");
	    } else {
		tostart.sendMessage("You have retruned to being invisible.");
	    }
	}
    }

    public void vmCmd(CommandSender sender, String[] args) {
	Player player = Bukkit.getPlayerExact(sender.getName());
	if (!spm.isSpectating(player)) {
	    setVanished(player, !isVanished(player), true);
	} else {
	    sender.sendMessage("You can not vanish when you are spectating!");
	}
    }

    public void stopAll() {
	for (Player player : Bukkit.getOnlinePlayers())
	    stop(player, false);
    }

    public void registerAll() {
	for (Player player : Bukkit.getOnlinePlayers())
	    isvanished.put(player.getName(), false);
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
	if (event.getTarget() instanceof Player) {
	    Player p = (Player) event.getTarget();
	    if (isvanished.get(p.getName())) {
		event.setCancelled(true);
	    }
	}
    }

}