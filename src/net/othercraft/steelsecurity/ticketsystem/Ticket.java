package net.othercraft.steelsecurity.ticketsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.othercraft.steelsecurity.utils.ExtraConfigManager;


public class Ticket {
    
    private ExtraConfigManager config;
    
    private String origional;
    private String player;
    private String asignnee = null;
    private Boolean open;
    private Long time;
    private int index;
    private List<String> comments = new ArrayList<String>();
    private String key;
    
    public Ticket(int index, ExtraConfigManager config){
	this.index = index;
	this.config = config;
	this.key = "Tickets." + index + "";
	time = System.currentTimeMillis();
    }
    
    public void load() {
	open = config.getConfig().getBoolean(key + ".open");
	player = config.getConfig().getString(key + ".player");
	asignnee = config.getConfig().getString(key + ".asignnee");
	origional = config.getConfig().getString(key + ".message");
	time = config.getConfig().getLong(key + ".time");
	comments = config.getConfig().getStringList(key + ".comments");
    }
    public void save(){
	config.getConfig().set(key + ".open", open);
	config.getConfig().set(key + ".player", player);
	if (asignnee!=null){
	    config.getConfig().set(key + ".asignnee", asignnee);
	}
	config.getConfig().set(key + ".message", origional);
	config.getConfig().set(key + ".time", time);
	if (comments.size() != 0){
	    config.getConfig().set(key + ".comments", comments);
	}
	config.saveConfig();
    }
    public void setMessage(String message){
	this.origional = message;
    }
    public void setPlayer(Player player){
	this.player = player.getName();
    }
    public void setPlayer(String playername){
	this.player = playername;
    }
    public void setAsignnee(Player player){
	this.asignnee = player.getName();
    }
    public void setAsignnee(String playername){
	this.asignnee = playername;
    }
    public String getMessage(String message){
	return origional;
    }
    public OfflinePlayer getPlayer(){
	return Bukkit.getOfflinePlayer(player);
    }
    public OfflinePlayer getAsignnee(){
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
    public void delete() {
	config.getConfig().set(key, null);
	config.saveConfig();
    }
}
