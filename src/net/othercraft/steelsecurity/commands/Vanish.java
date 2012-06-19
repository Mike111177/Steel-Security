package net.othercraft.steelsecurity.commands;

import java.util.HashMap;
import java.util.Map;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.listeners.SpectateManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Vanish extends SSCmdExe {

	Main plugin;

	SpectateManager spm;

	public Vanish(String name, Main instance, SpectateManager specman) {
		super("Vanish", true);
		plugin = instance;
		spm = specman;
	}

	Map<String, Boolean> isvanished = new HashMap<String, Boolean>();

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		isvanished.put(player.getName(), false);
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		isvanished.remove(player.getName());
	}
	public Boolean isVanished(Player player){
		Boolean result;
		if (isvanished.get(player.getName())!=null){
			result = isvanished.get(player.getName());
		}
		else {
			result = false;
		}
		return result;
	}
	public void setVanished(Player player, Boolean o){
		if (o){
			if (!isVanished(player)){
				start(player);
			}
		}
		else {
			if (isVanished(player)){
				stop(player);
			}
		}
	}
	private void stop(Player player) {
		// TODO Auto-generated method stub

	}
	private void start(Player player) {
		// TODO Auto-generated method stub

	}

}
