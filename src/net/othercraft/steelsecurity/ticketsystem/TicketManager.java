package net.othercraft.steelsecurity.ticketsystem;

import java.util.ArrayList;
import java.util.List;

import net.othercraft.steelsecurity.utils.ExtraConfigManager;

public class TicketManager {
    
    ExtraConfigManager config;
    Boolean enabled = false;
    List<Ticket> tickets = new ArrayList<Ticket>();
    
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
