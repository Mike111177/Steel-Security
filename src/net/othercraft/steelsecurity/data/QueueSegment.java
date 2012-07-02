package net.othercraft.steelsecurity.data;

import org.bukkit.entity.Player;

public class QueueSegment {
    
    private int amount, data;
    private String p1, p2, type;
    public QueueSegment(int value, int var, Player player, Player otherplayer, String action){
	amount = value;
	data = var;
	p1 = player.getName();
	p2 = otherplayer.getName();
	type = action;
    }
    public QueueSegment(int value, int var, Player playername, String action){
	amount = value;
	data = var;
	p1 = playername.getName();
	p2 = null;
	type = action;
    }
    public QueueSegment(int var, Player playername, String action){
	amount = 0;
	data = var;
	p1 = playername.getName();
	p2 = null;
	type = action;
    }
    public QueueSegment(int var, String action){
	amount = 0;
	data = var;
	p1 = null;
	p2 = null;
	type = action;
    }
    public QueueSegment(String action){
	amount = 0;
	data = 0;
	p1 = null;
	p2 = null;
	type = action;
    }
    public int getAmount(){
	return amount;
    }
    public int getData(){
	return data;
    }
    public String getFirstPlayer(){
	return p1;
    }
    public String getSecondPlayer(){
	return p2;
    }
    public String getType(){
	return type;
    }

}
