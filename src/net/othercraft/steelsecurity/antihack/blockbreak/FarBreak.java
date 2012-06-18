package net.othercraft.steelsecurity.antihack.blockbreak;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import net.othercraft.steelsecurity.utils.SSCmdExe;

public class FarBreak extends SSCmdExe {

	public FarBreak(String name, Boolean listener) {
		super("FarBreak", true);
		// TODO Auto-generated constructor stub
	}
	@EventHandler
	public void onPlace(BlockBreakEvent event){
	}
}
