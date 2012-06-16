package net.othercraft.steelsecurity.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.othercraft.steelsecurity.utils.SSCmdExe;

public class SpectateManager extends SSCmdExe {

	public SpectateManager(String name, Boolean listener) {
		super("SpectateManager", true);
	}
	Map<Player, Boolean> spectators = new HashMap<Player, Boolean>();//if some is a spectating someone else.
	Map<Player, Boolean> spectatees = new HashMap<Player, Boolean>();//if someone is being spectated.
	Map<Player, Player> spectating = new HashMap<Player, Player>();//Who a player is spectating.
	Map<Player, Player[]> speclist = new HashMap<Player, Player[]>();//Who a player is being spectated by.
	Map<Player, Location> origion = new HashMap<Player, Location>();//Where a player was before beginning spectate
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		spectators.put(player, false);
		spectatees.put(player, false);
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (spectatees.get(player)){
			Player[] tostops = speclist.get(player);
			for (Player tostop : tostops) {
				stop(tostop);
			}
			speclist.remove(player);
		}
		spectators.remove(player);
		spectatees.remove(player);
	}
	private void start(Player tostart, Player tostarton) {
		spectators.put(tostart, true);
		spectatees.put(tostarton, true);
		spectating.put(tostart, tostarton);
		origion.put(tostart, tostart.getLocation());
		if (speclist.get(tostarton)!=null) {
			Player[] before = speclist.get(tostarton);
			Player[] after = new Player[before.length + 1];
			for(int i = 0; i < before.length; i++) after[i] = before[i];
			after[before.length] = tostart;
			speclist.put(tostarton, after);
		}
		else {
			Player[] list = null;
			list[1] = tostart;
			speclist.put(tostarton, list);
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			tostart.hidePlayer(player);
		}
		tostarton.hidePlayer(tostart);
	}
	private void stop(Player tostop) {
		
	}
	public void stopall(Boolean state) {
		
	}
	public void speccmd() {
		
	}
	public Player getSpectatee(Player player){
		return spectating.get(player);
	}
	public Boolean isBeingSpectated(Player player){
		return spectatees.get(player);
	}
	public Boolean isSpectating(Player player){
		return spectators.get(player);
	}
	public Player[] getSpectators(Player player){
		return speclist.get(player);
	}

}
