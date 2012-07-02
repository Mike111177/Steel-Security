package net.othercraft.steelsecurity.data;

import java.sql.ResultSet;
import java.util.PriorityQueue;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import lib.PatPeter.SQLibrary.MySQL;

public class DatabaseManager {
    
    private Boolean online;
    
    private PriorityQueue<QueueSegment> queue;
    
    private MySQL sql  = new MySQL(null, "pl_","lego.othercraft.net","3306","othercraftweb_test", "othercraftweb", "B4P3osFl6J");  
    
    public void sqlCheck(){
	sql.open();
	online = sql.checkConnection();
	System.out.println(online);
    }
    public void addqueue(int value, int var, Player player, Player otherplayer, String action){
	queue.add(new QueueSegment(value, var, player, otherplayer, action));
    }
    public void addqueue(int value, int var, Player player, String action){
	queue.add(new QueueSegment(value, var, player, action));
    }
    public void addqueue(int var, Player player, String action){
	queue.add(new QueueSegment(var, player, action));
    }
    public void addqueue(int var, String action){
	queue.add(new QueueSegment(var, action));
    }
    public void addqueue(String action){
	queue.add(new QueueSegment(action));
    }
    private void queRun(){
	if (queue.size()==0){
	    
	}	
    }
}