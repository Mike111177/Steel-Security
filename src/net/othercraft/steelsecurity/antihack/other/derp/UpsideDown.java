package net.othercraft.steelsecurity.antihack.other.derp;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.data.violations.ViolationsManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public final class UpsideDown extends SSCmdExe {

    private final transient ViolationsManager vio;

    public UpsideDown(final ViolationsManager viol, final SteelSecurity instance) {
	super("UpsideDown", true, instance);
	vio = viol;
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
	if (event.getPlayer().getLocation().getPitch() == 180.0) {
	    vio.add("DERP", event.getPlayer());
	}
    }
}
