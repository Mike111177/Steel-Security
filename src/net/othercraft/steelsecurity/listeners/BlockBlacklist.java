package net.othercraft.steelsecurity.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

public class BlockBlacklist extends SSCmdExe {
	
	Main plugin;

	public BlockBlacklist(String name, Main instance) {
		super("BlockBlacklist", true);
		plugin = instance;
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!event.getPlayer().hasPermission("steelsecurity.bypass.blockblackist") || !plugin.getConfig().getBoolean("Block_Blacklist.Enabled")) {
			
		}
		
	}
	

}
