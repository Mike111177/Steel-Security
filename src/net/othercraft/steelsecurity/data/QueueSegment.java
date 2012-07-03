package net.othercraft.steelsecurity.data;

import org.bukkit.entity.Player;

public class QueueSegment {
    
    private String type;
    
    public QueueSegment(Player player, String beforemessage, String aftermessage, Boolean spam){
	type = "Player Chat";
    }

}
