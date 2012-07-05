package net.othercraft.steelsecurity.ticketsystem;

import net.othercraft.steelsecurity.utils.ExtraConfigManager;

public class TicketManager {
    
    ExtraConfigManager config;
    Boolean enabled = false;
    
    public TicketManager(ExtraConfigManager cm){
	config = cm;
	if (enabled){
	    enabled = true;
	    initiate();
	}
    }

    private void initiate() {
	// TODO Auto-generated method stub
	
    }
    

}
