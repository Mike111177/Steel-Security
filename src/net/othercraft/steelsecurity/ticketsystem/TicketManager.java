package net.othercraft.steelsecurity.ticketsystem;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.othercraft.steelsecurity.utils.SSCmdExe;

public class TicketManager extends SSCmdExe{
    
    private List<Ticket> tickets;
    private String noperm = ChatColor.RED + "You don't have permission to do this!";
    private TicketMessageProccessor mp = new TicketMessageProccessor();
    private File dataFolder = null;
    
    public TicketManager(File datafolder){
	super("TicketManager", false);
	this.dataFolder = datafolder;
	initiate();
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
    private void initiate(){
	loadAll();
    }
    public void loadAll() {
	if (!dataFolder.exists()){
	    dataFolder.mkdir();
	}
	File[] files = dataFolder.listFiles();
	for (File file : files){
	    System.out.println(file.getName());
	}
    }
    public void saveAll() {
	
    }
    public void load(int index){
	
    }
    public void save(int index){
	
    }
    private Ticket newTicket(){
	return new Ticket();
    }
    private void newTicketCmd(CommandSender sender, String[] segments){
	Ticket newtick = newTicket();
	newtick.setPlayer(sender.getName());
	String message = "";
	String loc;
	Boolean skip = true;
	for (String word : segments){
	    if (!skip){
		message = message + word + " ";
	    }
	    else {
		skip = false;
	    }
	}
	if (sender instanceof Player) {
	    Player player = (Player) sender;
	    loc = Math.round(player.getLocation().getX()) + ":" 
		    + Math.round(player.getLocation().getY()) + ":" 
		    + Math.round(player.getLocation().getZ());
	}
	else {
	    loc = "N/A";
	}
	newtick.setLocation(loc);
	message = message.trim();
	newtick.setMessage(message);
	newtick.open();
	newtick.registerTime();
	tickets.add(newtick);
    }
    @Override
    public boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (args.length==0)base(sender);
	else {
	    if (args[0].equalsIgnoreCase("new"))newcmd(sender, args);
	    else if (args[0].equalsIgnoreCase("list"))list(sender, args);
	    else if (args[0].equalsIgnoreCase("view"))view(sender, args);
	    else if (args[0].equalsIgnoreCase("comment"))comment(sender, args);
	    else if (args[0].equalsIgnoreCase("close"))close(sender, args);
	    else if (args[0].equalsIgnoreCase("open")) open(sender, args);
	    else if (args[0].equalsIgnoreCase("help")) help(sender, args);
	    else if (args[0].equalsIgnoreCase("clear")) clear(sender, args);
	    else if (args[0].equalsIgnoreCase("me")) me(sender, args);
	}
	return true;
    }
    private void base(CommandSender sender) {
	if (sender.hasPermission("steelsecurity.commands.ticket")){
	sender.sendMessage(ChatColor.GREEN + "Welcome to Steel Security's Ticket Request System.");
	sender.sendMessage(ChatColor.GREEN + "Type /ticket help for a list of commands.");
	}
	else {
	sender.sendMessage(noperm);
	}
    }
    private void newcmd(CommandSender sender, String[] args) {
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
    private void list(CommandSender sender, String[] args) {
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
    private void view(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.veiw") 
		|| sender.getName().equals(getTicket(Integer.parseInt(args[1])).getPlayerName())) {
	    if (args.length==2) {
		if (!(Integer.parseInt(args[1])>tickets.size())){
		    for (String line : mp.veiwTicket(getTicket(Integer.parseInt(args[1])))) 
			sender.sendMessage(line);
		}
		else {
		    sender.sendMessage("There is no ticket with the ID of " + args[1]);
		}
	    }
	    else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket view ID");
	    }
	}
	else {
	    sender.sendMessage(noperm);
	}
    }
    private void comment(CommandSender sender, String[] args) {
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
		    if (sender.getName().equals(getTicket(Integer.parseInt(args[1])).getPlayerName())
			    || sender.getName().equals(getTicket(Integer.parseInt(args[1])).getAsignneeName())
			    && sender.hasPermission("steelsecurity.commands.ticket.comment.assigned") 
			    || sender.hasPermission("steelsecurity.commands.ticket.comment.all")){
			getTicket(Integer.parseInt(args[1])).addComment(message);
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
    private void close(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.close.assigned") 
		&& sender.getName().equals(getTicket(Integer.parseInt(args[1])).getAsignneeName())
		|| sender.hasPermission("steelsecurity.commands.ticket.close.all")){
	    if (args.length==2) {
		if (!(Integer.parseInt(args[1])>tickets.size())){
		    getTicket(Integer.parseInt(args[1])).close();
		}
		else {
		    sender.sendMessage("There is no ticket with the ID of " + args[1]);
		}

	    }
	    else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket close <ID>");
	    }
	}
	else {
	    sender.sendMessage(noperm);
	}
    }
    private void open(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.open.assigned") 
		&& sender.getName().equals(getTicket(Integer.parseInt(args[1])).getAsignneeName())
		|| sender.hasPermission("steelsecurity.commands.ticket.open.all")){
	    if (args.length==2) {
		if (!(Integer.parseInt(args[1])>tickets.size())){
		    getTicket(Integer.parseInt(args[1])).open();
		}
		else {
		    sender.sendMessage("There is no ticket with the ID of " + args[1]);
		}

	    }
	    else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket open <ID>");
	    }
	}
	else {
	    sender.sendMessage(noperm);
	}
    }
    private void me(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.me")){
	}
	
    }
    private void clear(CommandSender sender, String[] args) {
	// TODO Auto-generated method stub
	
    }
    private void help(CommandSender sender, String[] args) {
	// TODO Auto-generated method stub
	
    }
}
