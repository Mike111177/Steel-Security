package net.othercraft.steelsecurity.antihack.other.derp;

import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class UpsideDown extends SSCmdExe {

	public UpsideDown(String name, Boolean listener) {
		super(name, true);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (event.getPlayer().getLocation().getPitch()==180.0){
			//TODO send warning or give like that upside down effect
		}
	}


}
