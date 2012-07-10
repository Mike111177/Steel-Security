package net.othercraft.steelsecurity.ticketsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.othercraft.steelsecurity.utils.SSCmdExe;
import net.othercraft.steelsecurity.utils.Tools;

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
	else {
	    for (Ticket tick : tickets){
		if (tick.getIndex()==index)return tick;
	    }
	    return null;
	}
    }
    public Integer getNextFreeIndex() {
	if (tickets.size()>0){
	    int index = 0;
	    for (Ticket tick : tickets){
		if (tick.getIndex()>index){
		    index = tick.getIndex();
		}
	    }
	    index++;
	    return index;
	}
	else {
	    return 1;
	}
    }
    private void initiate(){
	loadAll();
	saveAll();
    }
    public void loadAll() {
	tickets = new ArrayList<Ticket>();
	if (!dataFolder.exists()){
	    dataFolder.mkdir();
	}
	File[] files = dataFolder.listFiles();
	for (File file : files){
	    System.out.println(file.getName());
	    System.out.println(file.getPath());
	    if (file.getName().endsWith(".tick")) {
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream (fin); 
			Ticket ticket = (Ticket) in.readObject();
			tickets.add(ticket);
			in.close();
		    } catch (FileNotFoundException e) {
			e.printStackTrace();
		    } catch (IOException e) {
			e.printStackTrace();
		    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		    }
	    }
	}
    }
    public void saveAll() {
	if (!dataFolder.exists()){
	    dataFolder.mkdir();
	}
	for (File oldfile : dataFolder.listFiles()) {
	    oldfile.delete();
	}
	for (Ticket newfile : tickets){
	    File tosave = new File(dataFolder, newfile.getIndex() + ".tick");
	    try {
		FileOutputStream fout = new FileOutputStream(tosave);
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(newfile);
		out.close();
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
    private Ticket newTicket(){
	Ticket tick = new Ticket();
	tick.setIndex(getNextFreeIndex());
	return tick;
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
	    Boolean save = false;
	    if (args[0].equalsIgnoreCase("new")){
		newcmd(sender, args);
		save = true;
	    }
	    else if (args[0].equalsIgnoreCase("list"))list(sender, args);
	    else if (args[0].equalsIgnoreCase("view"))view(sender, args);
	    else if (args[0].equalsIgnoreCase("comment")){
		comment(sender, args);
		save = true;
	    }
	    else if (args[0].equalsIgnoreCase("close")){
		close(sender, args);
		save = true;
	    }
	    else if (args[0].equalsIgnoreCase("open")) {
		open(sender, args);
		save = true;
	    }
	    else if (args[0].equalsIgnoreCase("help")) help(sender, args);
	    else if (args[0].equalsIgnoreCase("delete")) delete(sender, args);
	    else if (args[0].equalsIgnoreCase("deleteall")) deleteAll(sender, args);
	    else if (args[0].equalsIgnoreCase("me")) me(sender, args);
	    if (save){
		saveAll();
	    }
	    else {
		sender.sendMessage("Unkown command subcommand.");
		sender.sendMessage("For a list of subcommands for /ticket");
		sender.sendMessage("Please type /ticket help");
	    }
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
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1]))!=null){
			for (String line : mp.veiwTicket(getTicket(Integer.parseInt(args[1])))) 
			    sender.sendMessage(line);
		    }
		    else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		}
		else{
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket view <ID>");
		}
	    }
	    else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket view <ID>");
	    }
	}
	else {
	    sender.sendMessage(noperm);
	}
    }
    private void comment(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.comment")) {
	    if (args.length>2) {
		if (Tools.isSafeNumber(args[1])){
		    if (getTicket(Integer.parseInt(args[1]))!=null){
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
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket comment <ID> <message>");
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
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1]))!=null){
			getTicket(Integer.parseInt(args[1])).close();
		    }
		    else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		}
		else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket close <ID>");
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
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1]))!=null){
			getTicket(Integer.parseInt(args[1])).open();
		    }
		    else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		}
		else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket open <ID>");
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
    private void delete(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.delete")) {
	    if (args.length==2) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1]))!=null){
			delete(Integer.parseInt(args[1]));
		    }
		    else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		}
		else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket delete <ID>");
		}
	    }
	    else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket delete <ID>");
	    }
	}
	else {
	    sender.sendMessage(noperm);
	}
	
    }
    private void deleteAll(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.deleteall")) {
	    if (args.length==2) {
			deleteAll();
	    }
	    else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket deleteall");
	    }
	}
	else {
	    sender.sendMessage(noperm);
	}
	
    }
    private void delete(int index) {
	for (Ticket tick : tickets){
	    if (tick.getIndex() == index){
		tickets.remove(tick);
	    }
	}
	saveAll();
	loadAll();
    }
    private void deleteAll() {
	tickets = new ArrayList<Ticket>();
	saveAll();
	loadAll();
    }
    private void help(CommandSender sender, String[] args) {
	// TODO Auto-generated method stub
	
    }
}
