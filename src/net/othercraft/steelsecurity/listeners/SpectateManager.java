package net.othercraft.steelsecurity.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.othercraft.steelsecurity.utils.SSCmdExe;

public class SpectateManager extends SSCmdExe {

	public SpectateManager(String name, Boolean listener) {
		super("SpectateManager", true);
	}
	Map<Player, Boolean> spectators = new HashMap<Player, Boolean>();
	Map<Player, Boolean> spectatees = new HashMap<Player, Boolean>();
	Map<Player, Player[]> speclist = new HashMap<Player, Player[]>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
	}
	private void start() {
		
	}
	private void stop() {
		
	}
	public void stopall(Boolean state) {
		
	}
	public void speccmd() {
		
	}

}
