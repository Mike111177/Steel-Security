package net.othercraft.steelsecurity.listeners;

import java.util.logging.Logger;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class ConsoleCommandMessage extends SSCmdExe {
  
    private static transient final Logger LOG = Logger.getLogger("Minecraft");

    public ConsoleCommandMessage(final SteelSecurity instance) {
	super("ConsoleCommandMessage", true, instance);
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
	if (event.getMessage().toLowerCase().startsWith("/sts")) {
	    LOG.info(event.getPlayer().getName() + ": " + event.getMessage());
	}
    }
}