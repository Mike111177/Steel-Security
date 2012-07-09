package net.othercraft.steelsecurity.ticketsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.othercraft.steelsecurity.utils.ExtraConfigManager;
import net.othercraft.steelsecurity.utils.SSCmdExe;

public class TicketManager extends SSCmdExe{
    
    private ExtraConfigManager config;
    private Boolean enabled = false;
    private List<Ticket> tickets = new ArrayList<Ticket>();
    private String noperm = ChatColor.RED + "You don't have permission to do this!";
    
    public TicketManager(ExtraConfigManager cm){
	super("TicketManager", false);
	config = cm;
	if (enabled){
	    enabled = true;
	    initiate();
	}
    }
    public Ticket getTicket(int index){
	if (index<1) {
	    return null;
	}
	else if (index>tickets.size()) {
	    return null;
	}
	else {
	    return tickets.get(index-1);
	}
    }

    public void initiate() {
	System.out.println("initiate");
	Set<String> keys = config.getConfig().getConfigurationSection("Tickets").getKeys(false);
	for (String key : keys) System.out.println(key);
	if (keys.size()!=0){
	    for (String indexstring : keys){
		System.out.println(indexstring);
		int num = Integer.parseInt(indexstring);
		Ticket ticket = new Ticket(num, config);
		ticket.load();
		tickets.add(num - 1, ticket);
		System.out.println(num);
	    }
	}
    }
    private Ticket newTicket(){
	return new Ticket(tickets.size() + 1, config);
    }
    private void newTicketCmd(CommandSender sender, String[] segments){
	Ticket newtick = newTicket();
	newtick.setPlayer(sender.getName());
	String message = "";
	Boolean skip = true;
	for (String word : segments){
	    if (!skip){
		message = message + word + " ";
	    }
	    else {
		skip = false;
	    }
	}
	message = message.trim();
	newtick.setMessage(message);
	newtick.open();
	newtick.save();
	tickets.add(newtick);
    }
    @Override
    public boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (args.length==0){
	    if (sender.hasPermission("steelsecurity.commands.ticket")){
		sender.sendMessage(ChatColor.GREEN + "Welcome to steel securitys Ticket Request System.");
		sender.sendMessage(ChatColor.GREEN + "Type /ticket help for a list of commands.");
	    }
	    else {
		sender.sendMessage(noperm);
	    }
	}
	else {
	    if (args[0].equalsIgnoreCase("new")){
		if (sender.hasPermission("steelsecurity.commands.ticket.create")) {
		    if (args.length>1) {
			newTicketCmd(sender, args);
		    }
		    else {
			sender.sendMessage("Invalid Arguments!");
			sender.sendMessage("Please use /ticket new <message>");
		    }
		}
		else {
		    sender.sendMessage(noperm);
		}
	    }
	}
	return true;
    }

}
