package net.othercraft.steelsecurity.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;

public class ChatBlockDeathCmdMoveLogger extends SSCmdExe {
	
	Main plugin;

	public ChatBlockDeathCmdMoveLogger(String name, Main instance) {
		super(name, true);//true only if its a listener, false if it isnt
		this.plugin = instance;
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		
	}
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		
	}
	@EventHandler
	public void onBlockChange(BlockEvent event){
		
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		
	}

}
