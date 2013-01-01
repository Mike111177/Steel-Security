package net.othercraft.steelsecurity.antihack.blockbreak;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public final class FarBreak extends SSCmdExe {

    public FarBreak(final SteelSecurity instance) {
	super("FarBreak", true, instance);
    }
    
    /**
     * For tracking placment of blocks
     * @param event the placement of a block
     */

    @EventHandler
    public void onPlace(final BlockBreakEvent event) {
	//TODO FarBreak antihack
    }
}
