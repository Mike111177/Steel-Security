package net.othercraft.steelsecurity.data;

import java.util.HashMap;
import java.util.Map;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Violations extends SSCmdExe{

	Main plugin;

	public Violations(String name, Main instance) {
		super("Violations", true);
		plugin = instance;
	}
	private Map<String, Integer> flood = new HashMap<String, Integer>();
	private Map<String, Integer> derp = new HashMap<String, Integer>();

	public void addFlood(Player player){
		int n = flood.get(player.getName());
		n++;
		flood.put(player.getName(), n);
	}
	public void subtractFlood(Player player){
		int n = flood.get(player.getName());
		if (n>0){
			n--;
		}
		flood.put(player.getName(), n);
	}
	public void setFlood(Player player, Integer value){
		if (value>=0){
			flood.put(player.getName(), value);
		}
	}
	public Integer getFlood(Player player){
		return flood.get(player.getName());
	}
	public void addDerp(Player player){
		int n = derp.get(player.getName());
		n++;
		derp.put(player.getName(), n);
	}
	public void subtractDerp(Player player){
		int n = derp.get(player.getName());
		if (n>0){
			n--;
		}
		derp.put(player.getName(), n);
	}
	public void setDerp(Player player, Integer value){
		if (value>=0){
			derp.put(player.getName(), value);
		}
	}
	public Integer getDerp(Player player){
		return derp.get(player.getName());
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		engage(event.getPlayer());
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		disengage(event.getPlayer());
	}
	public void engage(Player player){
		flood.put(player.getName(), 0);
		derp.put(player.getName(), 0);
	}
	private void disengage(Player player) {
		flood.remove(player.getName());
		derp.remove(player.getName());
	}
	public void engageAll(){
		for (Player player : Bukkit.getOnlinePlayers()){
			engage(player);
		}
	}
}
