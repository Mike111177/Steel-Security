package net.othercraft.steelsecurity.commands;

import java.util.HashMap;
import java.util.Map;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.listeners.SpectateManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Vanish extends SSCmdExe {

    Main plugin;

    SpectateManager spm;

    public Vanish(String name, Main instance, SpectateManager specman) {
	super("Vanish", true);
	plugin = instance;
	spm = specman;
    }

    Map<String, Boolean> isvanished = new HashMap<String, Boolean>();

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

    public void setVanished(Player player, Boolean o) {
	if (o) {
	    if (!isVanished(player)) {
		start(player);
		isvanished.put(player.getName(), true);
	    }
	} else {
	    if (isVanished(player)) {
		stop(player);
		isvanished.put(player.getName(), false);
	    }
	}
    }

    private void stop(Player tostop) {
	for (Player player : Bukkit.getOnlinePlayers()) {
		player.showPlayer(tostop);
	}
    }

    private void start(Player tostart) {
	for (Player player : Bukkit.getOnlinePlayers()) {
	    if (!player.hasPermission("steelsecurity.commands.vanish.cansee")){
		player.hidePlayer(tostart);
	    }
	}
    }

    public void vmCmd(CommandSender sender, String[] args) {
	Player player = Bukkit.getPlayerExact(sender.getName());
	if (!spm.isSpectating(player)) {
	    setVanished(player, !isVanished(player));
	} else {
	    sender.sendMessage("You can not vanish when you are spectating!");
	}
    }
}