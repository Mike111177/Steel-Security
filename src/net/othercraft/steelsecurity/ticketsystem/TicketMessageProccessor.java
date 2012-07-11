package net.othercraft.steelsecurity.ticketsystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.othercraft.steelsecurity.utils.Tools;

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
	    String index = ticket.getIndex().toString();
	    String player;
	    if (ticket.isPlayerOnline()){
		player = ChatColor.GREEN  + ticket.getPlayer().getName() + ChatColor.YELLOW + ":";
	    }
	    else {
		player = ChatColor.RED + ticket.getPlayer().getName() + ChatColor.YELLOW + ":";
	    }
	    String message = ChatColor.YELLOW + ticket.getMessage();
	    String line = ChatColor.GOLD + "#" + index + " " + player + " " + message;
	    if (line.length()>52){
		line = line.substring(0, 52);
		line = line + "...";
	    }
	    list[linecounter] = line;
	    linecounter++;
	}
	return list;


    }
    public String[] veiwTicket(Ticket ticket, Integer page){
	String sep = ChatColor.GRAY + " ";
	ChatColor b = ChatColor.DARK_GREEN;
	List<String> comments = ticket.getComments();
	String player;
	String assginee;
	String open;
	if (ticket.getPlayer().isOnline()){
	    player = ChatColor.GREEN + ticket.getPlayerName();
	}
	else {
	    player = ChatColor.RED + ticket.getPlayerName();
	}
	if (!(ticket.getAsignneeName()==(null))){
	    if (ticket.isAsignneeOnline()){
		assginee = ChatColor.GREEN + ticket.getAsignneeName();
	    }
	    else {
		assginee = ChatColor.RED + ticket.getAsignneeName();
	    }
	}
	else {
	    assginee = ChatColor.RED + "None";
	}
	if (ticket.isOpen()){
	    open = ChatColor.GREEN + "Open";
	}
	else {
	    open = ChatColor.RED + "Closed";
	}
	int pagenum = page;
	int pagenum2 = Tools.getPages(comments, 5);
	if (pagenum2==0)pagenum = pagenum2;
	String date = new Date(ticket.getTime()).toString().replace(" EDT", "");
	List<String> postproccess = Tools.getPage(comments, page, 5);
	String[] list = new String[5 + postproccess.size()];
	list[0] = sep + b + "Ticket: " + ChatColor.GOLD + "#" + ticket.getIndex() + sep;
	list[1] = sep + b + "Player: "+ player + sep + b + "Assignee: " + assginee + sep + b + "Status: " + open + sep ;
	list[2] = sep + b + "Date: " + ChatColor.YELLOW + date + sep + b + "Location: " + ChatColor.YELLOW + ticket.getLocation() + sep;
	list[3] = sep + b + "Problem: " + ChatColor.YELLOW + ticket.getMessage();
	list[4] = sep + ChatColor.GRAY + "Showing page " + pagenum + " out of " + pagenum2 + ".";
	int counter = 5;
	for (String comment : postproccess){
	    list[counter] = sep + "- " + comment;
	    counter++;   
	}
	return list;
    }
}