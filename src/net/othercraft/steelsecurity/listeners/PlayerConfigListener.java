package net.othercraft.steelsecurity.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.utils.SSCmdExe;


public class PlayerConfigListener extends SSCmdExe {

		public Main plugin;

		public PlayerConfigListener(String name, Main instance) {
			super("PlayerConfigListener", true);//true only if its a listener, false if it isnt
			this.plugin = instance;
		}
		@EventHandler
		public void loadConfig(PlayerJoinEvent event){
			
		}


}
