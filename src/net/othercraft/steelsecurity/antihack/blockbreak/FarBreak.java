package net.othercraft.steelsecurity.antihack.blockbreak;

import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class FarBreak extends SSCmdExe {

	public FarBreak(String name, Boolean listener) {
		super("FarBreak", true);
	}
	@EventHandler
	public void onPlace(BlockBreakEvent event){
	}
}
