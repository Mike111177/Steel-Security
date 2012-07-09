package net.othercraft.steelsecurity.ticketsystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class TicketMessageProccessor {
    
    public String[] listTickets(List<Ticket> tickets){
	int counter = 0;
	int ticketcount = 0;
	List<Ticket> touse = new ArrayList<Ticket>();
	for (Ticket ticket : tickets){
	    if (ticket.isOpen()){
		ticketcount++;
		if (counter<5){
		    touse.add(ticket);
		    counter++;
		}
	    }
	    
	}
	int display = touse.size();
	String[] list = new String[1 + display];
	list[0] = ChatColor.BLUE + "There are " + ChatColor.GOLD + ticketcount + ChatColor.BLUE + " open tickets left.";
	int linecounter = 1;
	for (Ticket ticket : touse){
	    String index = ChatColor.AQUA + "" + ticket.getIndex();
	    String player;
	    if (ticket.isPlayerOnline()){
		player = ChatColor.GREEN + "(" + ticket.getPlayer().getName() + ")";
	    }
	    else {
		player = ChatColor.RED + "(" + ticket.getPlayer().getName() + ")";
	    }
	    String message = ChatColor.YELLOW + ticket.getMessage();
	    String line = "#" + index + ": " + player + " " + message;
	    if (line.length()>52){
		line = line.substring(0, 52);
		line = line + "...";
	    }
	    list[linecounter] = line;
	    linecounter++;
	}
	return list;


    }

}
