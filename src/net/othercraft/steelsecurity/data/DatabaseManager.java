package net.othercraft.steelsecurity.data;

import java.sql.ResultSet;
import java.util.PriorityQueue;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lib.PatPeter.SQLibrary.MySQL;

public class DatabaseManager {
    
    private Boolean online;
    
    private Boolean running;
    
    private PriorityQueue<QueueSegment> queue;
    
    private MySQL sql  = new MySQL(null, "pl_","lego.othercraft.net","3306","othercraftweb_test", "othercraftweb", "B4P3osFl6J");  
    
    public void sqlCheck(){
	sql.open();
	online = sql.checkConnection();
	System.out.println(online);
    }
    public void addQueue(QueueSegment seg){
	queue.add(seg);
    }
    private void queRun(){
	if (queue.size()==0){
	    
	}	
    }
}