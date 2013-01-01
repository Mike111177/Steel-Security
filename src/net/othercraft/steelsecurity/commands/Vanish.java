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

public final class Vanish extends SSCmdExe {

    private transient SpectateManager spm;
    private static transient final Logger LOG = Logger.getLogger("Minecraft");

    public Vanish(final SteelSecurity instance) {
	super("Vanish", true, instance);
    }

    public void specGet() {
	spm = SteelSecurity.spectateManager;
    }

    private transient final Map<String, Boolean> isvanished = new HashMap<String, Boolean>();

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
	final Player player = event.getPlayer();
	isvanished.put(player.getName(), false);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
	final Player player = event.getPlayer();
	isvanished.remove(player.getName());
    }

    public Boolean isPlayerVanished(final Player player) {
	Boolean result;
	if (isvanished.get(player.getName())==null) {
	    result = false;
	} else {
	    result = isvanished.get(player.getName());
	}
	return result;
    }

    public void setVanished(final Player player,final Boolean finalVanished,final Boolean broadcast) {
	if (finalVanished) {
	    if (!isPlayerVanished(player)) {
		start(player, broadcast);
		isvanished.put(player.getName(), true);
	    }
	} else {
	    if (isPlayerVanished(player)) {
		stop(player, broadcast);
		isvanished.put(player.getName(), false);
	    }
	}
    }

    private void stop(final Player tostop,final Boolean notify) {
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
		LOG.info(tostop.getName() + " is now visable.");
	    } else {
		tostop.sendMessage("You were forced visable either because of a reload or you begun spectating.");
	    }
	}
    }

    private void start(final Player tostart,final Boolean notify) {
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
		LOG.info(tostart.getName() + " has gone invisable.");
	    } else {
		tostart.sendMessage("You have retruned to being invisible.");
	    }
	}
    }

    public void vmCmd(final CommandSender sender,final String[] args) {
	final Player player = Bukkit.getPlayerExact(sender.getName());
	if (spm.isSpectating(player)) {
	    sender.sendMessage("You can not vanish when you are spectating!");
	} else {
	    setVanished(player, !isPlayerVanished(player), true);
	}
    }

    public void stopAll() {
	for (Player player : Bukkit.getOnlinePlayers()){
	    stop(player, false);
	}
    }

    public void registerAll() {
	for (Player player : Bukkit.getOnlinePlayers()){
	    isvanished.put(player.getName(), false);
	}
    }

    @EventHandler
    public void onTarget(final EntityTargetEvent event) {
	if (event.getTarget() instanceof Player) {
	    final Player player = (Player) event.getTarget();
	    if (isvanished.get(player.getName())) {
		event.setCancelled(true);
	    }
	}
    }

}