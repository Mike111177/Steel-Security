package net.othercraft.steelsecurity.listeners;

import java.util.List;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public final class BlockBlacklist extends SSCmdExe {

    public BlockBlacklist(final SteelSecurity instance) {
	super("BlockBlacklist", true, instance);
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
	if (!event.getPlayer().hasPermission("steelsecurity.bypass.blockblackist") || !plugin.getConfig().getBoolean("Block_Blacklist.Enabled")) {
	    Boolean cancel = false;
	    final Material block = event.getBlockPlaced().getType();
	    final List<?> config = plugin.getConfig().getStringList("Block_Blacklist.Blocks");
	    final String[] list = config.toArray(new String[config.size()]);
	    final int count = config.size();
	    int counter = 0;
	    while (count > counter) {
		if (Material.getMaterial(Integer.parseInt(list[counter])) == block){
		    cancel = true;
		}
		counter++;
	    }
	    event.setCancelled(cancel);
	}

    }

}
