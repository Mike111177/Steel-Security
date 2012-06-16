package net.othercraft.steelsecurity.listeners;

import java.util.HashMap;
import java.util.HashSet;
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
	Map<Player, HashSet<Player>> speclist = new HashMap<Player, HashSet<Player>>();//Who a player is being spectated by.
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
			HashSet<Player> tostops = speclist.get(player);
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
			HashSet<Player> thenew = new HashSet<Player>();
			thenew.add(tostart);
			speclist.put(tostarton, thenew);
		}
		else {
			HashSet<Player> thenew = speclist.get(tostarton);
			thenew.add(tostart);
			speclist.put(tostarton, thenew);
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			tostart.hidePlayer(player);
		}
		tostarton.hidePlayer(tostart);
	}
	private void stop(Player tostop) {
		Player tostopon = spectating.get(tostop);
		spectators.put(tostop, false);
		for (Player player : Bukkit.getOnlinePlayers()) {
			tostop.showPlayer(player);
		}
		tostopon.showPlayer(tostop);
		spectating.remove(tostop);
		tostop.teleport(origion.get(tostop));
		origion.remove(tostop);
		if (speclist.get(tostopon).size()==1){
			speclist.remove(tostopon);
		}
		else {
			HashSet<Player> thenew = speclist.get(tostopon);
			thenew.remove(tostop);
			speclist.put(tostopon, thenew);
		}
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
	public HashSet<Player> getSpectators(Player player){
		return speclist.get(player);
	}

}
