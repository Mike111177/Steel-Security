package net.othercraft.steelsecurity.data;

import java.sql.ResultSet;
import java.util.PriorityQueue;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DatabaseManager {
    
    private Boolean online;
    
    private Boolean running;
    
    private PriorityQueue<QueueSegment> queue;
    
    public void addQueue(QueueSegment seg){
	queue.add(seg);
    }
    private void queRun(){
	if (queue.size()==0){
	    
	}	
    }
}