package net.othercraft.steelsecurity.listeners;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

public class ConsoleCommandMessage extends SSCmdExe {
    
    SteelSecurity plugin;
    Logger log;

    public ConsoleCommandMessage(String name, SteelSecurity instance, Logger log) {
	super("ConsoleCommandMessage", true);
	plugin = instance;
	this.log = log;
    }
    
    
    
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
	if (event.getMessage().toLowerCase().startsWith("/sts")){
	    log.info(event.getPlayer().getName() + ": " + event.getMessage());
	}
    }    
}