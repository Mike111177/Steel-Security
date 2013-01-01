package net.othercraft.steelsecurity.listeners;

import java.io.IOException;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.PlayerConfigManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerConfigListener extends SSCmdExe {

    public PlayerConfigListener(final SteelSecurity instance) {
	super("PlayerConfigListener", true, instance);
    }

    @EventHandler
    public void loadConfig(final PlayerJoinEvent event) {
	try {
	    checkPlayerConfig(event.getPlayer());
	} catch (Exception e) {
	    try {
		catchListenerException(e, event.getEventName());
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	}
    }

    public void checkPlayerConfig(final Player player) {
	final Boolean gmcheck = plugin.getConfig().getBoolean("Offline_GameMode_Changer.Enabled");
	final String playername = player.getName();
	FileConfiguration config = PlayerConfigManager.getConfig(playername);
	if (config == null) {
	    try {
		if (PlayerConfigManager.createConfig(playername)) {
		    config = PlayerConfigManager.getConfig(playername);
		} else {
		    final IOException exeption = new IOException();
		    throw exeption;
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	if (gmcheck) {
	    config.addDefault("GameMode", plugin.getConfig().getInt("Offline_GameMode_Changer.Default_GameMode"));
	}
	config.options().copyDefaults(true);
	try {
	    PlayerConfigManager.saveConfig(config, playername);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	if (gmcheck) {
	    player.setGameMode(GameMode.getByValue(config.getInt("GameMode")));
	}
    }

    public void checkAll() {
	for (Player player : Bukkit.getOnlinePlayers()){
	    checkPlayerConfig(player);
	}
    }

}
