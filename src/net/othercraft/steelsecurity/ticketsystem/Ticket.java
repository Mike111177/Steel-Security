package net.othercraft.steelsecurity.ticketsystem;

import java.util.List;

import net.othercraft.steelsecurity.utils.ExtraConfigManager;


public class Ticket {
    
    private ExtraConfigManager config;
    
    private String origional;
    private String player;
    private String asignnee;
    private Boolean open;
    private Long time;
    private int index;
    private List<String> comments;
    private String key;
    
    public Boolean Ticket(int index, ExtraConfigManager config){
	this.index = index;
	this.config = config;
	this.key = "Tickets." + index + ".";
	return open;
    }
    
    public void loadFromConfig() {
    }
    public void saveToConfig(){
	
    }

}
