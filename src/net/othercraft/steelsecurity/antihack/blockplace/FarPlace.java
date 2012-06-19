package net.othercraft.steelsecurity.antihack.blockplace;

import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class FarPlace extends SSCmdExe {

	public FarPlace(String name, Boolean listener) {
		super("FarPlace", true);
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
	}
}
