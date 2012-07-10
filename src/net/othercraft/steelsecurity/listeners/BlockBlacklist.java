package net.othercraft.steelsecurity.listeners;

import java.util.List;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockBlacklist extends SSCmdExe {

    SteelSecurity plugin;

    public BlockBlacklist(String name, SteelSecurity instance) {
	super("BlockBlacklist", true);
	plugin = instance;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
	if (!event.getPlayer().hasPermission("steelsecurity.bypass.blockblackist") || !plugin.getConfig().getBoolean("Block_Blacklist.Enabled")) {
	    Boolean cancel = false;
	    Material block = event.getBlockPlaced().getType();
	    List<?> config = plugin.getConfig().getStringList("Block_Blacklist.Blocks");
	    String[] list = config.toArray(new String[config.size()]);
	    int count = config.size();
	    int counter = 0;
	    while (count > counter) {
		if (Material.getMaterial(Integer.parseInt(list[counter])) == block)
		    cancel = true;
		counter++;
	    }
	    event.setCancelled(cancel);
	}

    }

}
