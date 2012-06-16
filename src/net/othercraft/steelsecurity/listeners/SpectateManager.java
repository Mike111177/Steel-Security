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
	Map<Player, Player> spectating = new HashMap<Player, Player>();
	Map<Player, Player[]> speclist = new HashMap<Player, Player[]>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (spectatees.get(player)){
			Player[] tostops = speclist.get(player);
			for (Player tostop : tostops) {
				stop(tostop);
			}
		}
		spectators.remove(player);
		spectatees.remove(player);
		speclist.remove(player);
	}
	private void start() {
		
	}
	private void stop(Player tostop) {
		
	}
	public void stopall(Boolean state) {
		
	}
	public void speccmd() {
		
	}

}
