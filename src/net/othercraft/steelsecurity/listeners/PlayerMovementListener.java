package net.othercraft.steelsecurity.listeners;

import net.othercraft.steelsecurity.antihackprocedures.AntiSpeed;

import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovementListener {

	public static void onMove(PlayerMoveEvent event) {
		AntiSpeed.Move(event);		
	}

}
