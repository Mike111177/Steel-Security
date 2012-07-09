package net.othercraft.steelsecurity.ticketsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
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
    private String count = "";
    private TicketMessageProccessor mp = new TicketMessageProccessor();
    
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
	Set<String> keys = config.getConfig().getConfigurationSection("Tickets").getKeys(false);
	Ticket[] list = new Ticket[keys.size()];
	if (keys.size()!=0){
	    for (String indexstring : keys){
		int num = Integer.parseInt(indexstring);
		Ticket ticket = new Ticket(num, config);
		ticket.load();
		list[num - 1] = ticket;
	    }
	    for (Ticket ticket : list){
		tickets.add(ticket);
	    }
	    refreshYamlOrder();
	}

    }
    private void refreshYamlOrder(){
	config.getConfig().set("Tickets", null);
	    for (Ticket tickt : tickets){
		tickt.save();
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
	newtick.registerTime();
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
	    if (args[0].equalsIgnoreCase("list")){
		if (sender.hasPermission("steelsecurity.commands.ticket.list")) {
		    if (args.length==1) {
			for (String line : mp.listTickets(tickets)) 
			    sender.sendMessage(line);
		    }
		    else {
			sender.sendMessage("Invalid Arguments!");
			sender.sendMessage("Please use /ticket list");
		    }
		}
		else {
		    sender.sendMessage(noperm);
		}
	    }
	    if (args[0].equalsIgnoreCase("comment")){
		if (sender.hasPermission("steelsecurity.commands.ticket.comment")) {
		    if (args.length>2) {
			if (!(Integer.parseInt(args[1])>tickets.size())){
			    String message = "";
			    Boolean skip = true;
			    Boolean skip2 = true;
			    for (String word : args){
				if (!skip){
				    message = message + word + " ";
				}
				if (!skip2){
				    skip = false;
				}
				else {
				    skip2 = false;
				}
			    }
			    message = message.trim();
			    if (getTicket(Integer.parseInt(args[1])).getPlayer().equals(Bukkit.getPlayerExact(sender.getName())) 
				    || getTicket(Integer.parseInt(args[1])).getAsignnee().equals(Bukkit.getPlayer(sender.getName())) 
				    && sender.hasPermission("steelsecurity.commands.ticket.comment.assigned") 
				    || sender.hasPermission("steelsecurity.commands.ticket.comment.all")){
				getTicket(Integer.parseInt(args[1])).addComment(message);
				getTicket(Integer.parseInt(args[1])).save();
			    }
			}
			else {
			    sender.sendMessage("There is no ticket with the ID of " + args[1]);
			}
		    }
		    else {
			sender.sendMessage("Invalid Arguments!");
			sender.sendMessage("Please use /ticket comment <ID> <message>");
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
