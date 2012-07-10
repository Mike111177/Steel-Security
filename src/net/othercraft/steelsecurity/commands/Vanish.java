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
    public void specGet(){
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
	    if (!player.hasPermission("steelsecurity.commands.vanish.cansee")) {
		player.showPlayer(tostop);
	    }
	    else {
		player.sendMessage(tostop.getName() + " is visable.");
	    }
	}
	tostop.sendMessage("You are now visable.");
	log.info(tostop.getName() + " is now visable.");
    }

    private void start(Player tostart) {
	for (Player player : Bukkit.getOnlinePlayers()) {
	    if (!player.hasPermission("steelsecurity.commands.vanish.cansee")) {
		player.hidePlayer(tostart);
	    }
	    else {
		player.sendMessage(tostart.getName() + " has disapeared.");
	    }
	}
	tostart.sendMessage("You are now invisable.");
	log.info(tostart.getName() + " has gone invisable.");
    }

    public void vmCmd(CommandSender sender, String[] args) {
	Player player = Bukkit.getPlayerExact(sender.getName());
	if (!spm.isSpectating(player)) {
	    setVanished(player, !isVanished(player));
	} else {
	    sender.sendMessage("You can not vanish when you are spectating!");
	}
    }
    public void stopAll(){
	for (Player player : Bukkit.getOnlinePlayers()) stop(player);
    }
    public void registerAll() {
	for (Player player : Bukkit.getOnlinePlayers()) isvanished.put(player.getName(), false);
    }
    @EventHandler
    public void onTarget(EntityTargetEvent event){
	if (event.getTarget() instanceof Player){
	    Player p = (Player) event.getTarget();
	    if (isvanished.get(p.getName())){
		event.setCancelled(true);
	    }
	}
    }
    
}