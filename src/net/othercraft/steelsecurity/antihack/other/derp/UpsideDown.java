package net.othercraft.steelsecurity.antihack.other.derp;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.data.Violations;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class UpsideDown extends SSCmdExe {

    Main plugin;

    Violations vio;

    public UpsideDown(String name, Main instance, Violations viol) {
	super("UpsideDown", true);
	plugin = instance;
	vio = viol;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
	if (event.getPlayer().getLocation().getPitch() == 180.0) {
	    vio.addDerp(event.getPlayer());
	}
    }
}
