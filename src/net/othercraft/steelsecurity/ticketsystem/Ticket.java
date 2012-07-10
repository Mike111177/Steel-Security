package net.othercraft.steelsecurity.ticketsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class Ticket implements Serializable{
    
    private String origional;
    private String player;
    private String asignnee = null;
    private Boolean open;
    private Long time;
    private int index;
    private List<String> comments = new ArrayList<String>();
    private String key;
    private String location;
    
    public void setMessage(String message){
	this.origional = message;
    }
    public void setPlayer(Player player){
	this.player = player.getName();
    }
    public void setPlayer(OfflinePlayer player){
	this.player = player.getName();
    }
    public void setPlayer(String playername){
	this.player = playername;
    }
    public void setAsignnee(Player player){
	this.asignnee = player.getName();
    }
    public void setAsignnee(OfflinePlayer player){
	this.asignnee = player.getName();
    }
    public void setAsignnee(String playername){
	this.asignnee = playername;
    }
    public String getMessage(){
	return origional;
    }
    public OfflinePlayer getPlayer(){
	return Bukkit.getOfflinePlayer(player);
    }
    public OfflinePlayer getAsignnee(){
	if (asignnee==null){
	    return null;
	}
	return Bukkit.getOfflinePlayer(asignnee);
    }
    public Long getTime(){
	return time;
    }
    public Boolean isPlayerOnline(){
	return Bukkit.getOfflinePlayer(player).isOnline();
    }
    public Boolean isAsignneeOnline(){
	return Bukkit.getOfflinePlayer(asignnee).isOnline();
    }
    public Boolean isOpen(){
	return open;
    }
    public void open(){
	this.open = true;
    }
    public void close(){
	this.open = false;
    }
    public void addComment(String comment){
	comments.add(comment);
    }
    public Integer getIndex(){
	return index;
    }
    public void registerTime(){
	time = System.currentTimeMillis();
    }
    public String getAsignneeName() {
	return asignnee;
    }
    public String getPlayerName() {
	return player;
    }
    public List<String> getComments(){
	return comments;
    }
    public Boolean isAssignned(){
	return asignnee!=null;
    }
    public void setLocation(String loc) {
	this.location = loc;
    }
    public String getLocation() {
	return location;
    }
    public void setIndex(Integer index) {
	this.index = index;
    }
    
    
}
