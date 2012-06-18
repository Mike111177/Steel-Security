package net.othercraft.steelsecurity.antihack.blockplace;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import net.othercraft.steelsecurity.utils.SSCmdExe;

public class FarPlace extends SSCmdExe {

	public FarPlace(String name, Boolean listener) {
		super("FarPlace", true);
		// TODO Auto-generated constructor stub
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
	}
}
