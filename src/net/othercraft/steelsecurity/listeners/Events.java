package net.othercraft.steelsecurity.listeners;
 
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
 
import net.othercraft.steelsecurity.Main;
 
public class Events implements Listener {
    Main plugin;
    private PlayerChatListener chatListener;
   
    public Events(Main instance, PlayerChatListener chatListener) {
        plugin=instance;
        this.chatListener = chatListener;
    }
}