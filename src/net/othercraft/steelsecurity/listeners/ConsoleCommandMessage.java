package net.othercraft.steelsecurity.listeners;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

public class ConsoleCommandMessage extends SSCmdExe {
    
    Main plugin;
    Logger log;

    public ConsoleCommandMessage(String name, Main instance, Logger log) {
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