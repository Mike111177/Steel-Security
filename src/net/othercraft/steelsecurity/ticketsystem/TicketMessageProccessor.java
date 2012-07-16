package net.othercraft.steelsecurity.ticketsystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.othercraft.steelsecurity.utils.Tools;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public class TicketMessageProccessor {

    private TicketManager tickm;

    public TicketMessageProccessor(TicketManager tickm) {
	this.tickm = tickm;
    }

    public String[] listTickets(int page) {
	int counter = 2;
	List<Ticket> prepro = tickm.getTickets();
	List<Ticket> postpro = new ArrayList<Ticket>(0);
	for (Ticket tick : prepro) {
	    if (tick.isOpen()) {
		postpro.add(tick);
	    }
	}
	List<String> touse = ticketsList(postpro);
	int ticketcount = touse.size();
	int pages = Tools.getPages(touse, 5);
	touse = Tools.getPage(touse, page, 5);
	int display = touse.size();
	String[] list = new String[2 + display];
	list[0] = ChatColor.BLUE + "There are " + ChatColor.GOLD + ticketcount + ChatColor.BLUE + " open tickets left.";
	list[1] = ChatColor.GRAY + "Showing page " + page + " out of " + pages + ".";
	for (String line : touse) {
	    list[counter] = line;
	    counter++;
	}
	return list;
    }

    public String[] veiwTicket(Ticket ticket, Integer page) {
	String sep = ChatColor.GRAY + " ";
	ChatColor b = ChatColor.DARK_GREEN;
	List<String> comments = ticket.getComments();
	String player;
	String assginee;
	String open;
	if (ticket.getPlayer().isOnline()) {
	    player = ChatColor.GREEN + ticket.getPlayerName();
	} else {
	    player = ChatColor.RED + ticket.getPlayerName();
	}
	if (!(ticket.getAsignneeName() == (null))) {
	    if (ticket.isAsignneeOnline()) {
		assginee = ChatColor.GREEN + ticket.getAsignneeName();
	    } else {
		assginee = ChatColor.RED + ticket.getAsignneeName();
	    }
	} else {
	    assginee = ChatColor.RED + "None";
	}
	if (ticket.isOpen()) {
	    open = ChatColor.GREEN + "Open";
	} else {
	    open = ChatColor.RED + "Closed";
	}
	Boolean showcomments = true;
	String pagemessage = "";
	int lines;
	int pagenum = page;
	int pagenum2 = Tools.getPages(comments, 5);
	if (pagenum2 == 0) {
	    lines = 4;
	    showcomments = false;
	} else {
	    lines = 5;
	    pagemessage = sep + ChatColor.GRAY + "Showing page " + pagenum + " out of " + pagenum2 + ".";
	}
	String date = new Date(ticket.getTime()).toString().replace(" EDT", "");
	List<String> postproccess = Tools.getPage(comments, page, 5);
	String[] list = new String[lines + postproccess.size()];
	list[0] = sep + b + "Ticket: " + ChatColor.GOLD + "#" + ticket.getIndex() + sep;
	list[1] = sep + b + "Player: " + player + sep + b + "Assignee: " + assginee + sep + b + "Status: " + open + sep;
	list[2] = sep + b + "Date: " + ChatColor.YELLOW + date + sep + b + "Location: " + ChatColor.YELLOW + ticket.getLocation() + sep;
	list[3] = sep + b + "Problem: " + ChatColor.YELLOW + ticket.getMessage();
	if (showcomments) {
	    list[4] = pagemessage;
	    int counter = 5;
	    for (String comment : postproccess) {
		list[counter] = sep + "- " + comment;
		counter++;
	    }
	}
	return list;
    }

    public List<String> selfList(OfflinePlayer player) {
	List<Ticket> own = new ArrayList<Ticket>(0);
	List<Ticket> assigned = new ArrayList<Ticket>(0);
	String name = player.getName();
	for (Ticket tick : tickm.getTickets()) {
	    if (tick.getAsignneeName().equals(name)) {
		assigned.add(tick);
	    }
	    if (tick.getPlayerName().equals(name)) {
		own.add(tick);
	    }
	}
	return null;
    }

    private List<String> ticketsList(List<Ticket> touse, int page, int pagel) {
	List<String> list = new ArrayList<String>();
	for (Ticket ticket : touse) {
	    String index = ticket.getIndex().toString();
	    String player;
	    if (ticket.isPlayerOnline()) {
		player = ChatColor.GREEN + ticket.getPlayer().getName() + ChatColor.YELLOW + ":";
	    } else {
		player = ChatColor.RED + ticket.getPlayer().getName() + ChatColor.YELLOW + ":";
	    }
	    String message = ChatColor.YELLOW + ticket.getMessage();
	    String line = ChatColor.GOLD + "#" + index + " " + player + " " + message;
	    if (line.length() > 52) {
		line = line.substring(0, 52);
		line = line + "...";
	    }
	    list.add(line);
	}
	list = Tools.getPage(list, page, pagel);
	return list;
    }

    private List<String> ticketsList(List<Ticket> touse) {
	List<String> list = new ArrayList<String>();
	for (Ticket ticket : touse) {
	    String index = ticket.getIndex().toString();
	    String player;
	    if (ticket.isPlayerOnline()) {
		player = ChatColor.GREEN + ticket.getPlayer().getName() + ChatColor.YELLOW + ":";
	    } else {
		player = ChatColor.RED + ticket.getPlayer().getName() + ChatColor.YELLOW + ":";
	    }
	    String message = ChatColor.YELLOW + ticket.getMessage();
	    String line = ChatColor.GOLD + "#" + index + " " + player + " " + message;
	    if (line.length() > 52) {
		line = line.substring(0, 52);
		line = line + "...";
	    }
	    list.add(line);
	}
	return list;
    }

}
