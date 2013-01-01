package net.othercraft.steelsecurity.antihack.other.derp;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.data.Violations;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public final class UpsideDown extends SSCmdExe {

    private final transient Violations vio;

    public UpsideDown(final Violations viol, final SteelSecurity instance) {
	super("UpsideDown", true, instance);
	vio = viol;
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
	if (event.getPlayer().getLocation().getPitch() == 180.0) {
	    vio.addDerp(event.getPlayer());
	}
    }
}
