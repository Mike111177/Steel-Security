package net.othercraft.steelsecurity.antihack.blockplace;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public final class FarPlace extends SSCmdExe {

    public FarPlace(final SteelSecurity instance) {
	super("FarPlace", true, instance);
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
	//TODO Far place check
    }
}
