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
import java.util.logging.Logger;

import net.othercraft.steelsecurity.utils.SSCmdExe;
import net.othercraft.steelsecurity.utils.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicketManager extends SSCmdExe {

    private Logger log;
    private List<Ticket> tickets;
    private String noperm = ChatColor.RED + "You don't have permission to do this!";
    private TicketMessageProccessor mp = new TicketMessageProccessor(this);
    private File dataFolder = null;

    /**
     * @param datafolder
     *            The folder where tickets will be stored.
     */
    public TicketManager(File datafolder, Logger log) {
	super("TicketManager", false);
	this.dataFolder = datafolder;
	initiate();
	this.log = log;
    }

    /**
     * @param index
     *            The ID of the ticket you want to recieve
     * @return If a ticket with the ID exist, the ticket, else null
     */
    public Ticket getTicket(int index) {
	if (index < 1) {
	    return null;
	} else {
	    for (Ticket tick : tickets) {
		if (tick.getIndex() == index)
		    return tick;
	    }
	    return null;
	}
    }

    /**
     * @return The next availible ticket ID
     */
    public Integer getNextFreeIndex() {
	if (tickets.size() > 0) {
	    int index = 0;
	    for (Ticket tick : tickets) {
		if (tick.getIndex() > index) {
		    index = tick.getIndex();
		}
	    }
	    index++;
	    return index;
	} else {
	    return 1;
	}
    }

    private void initiate() {
	loadAll();
	saveAll();
    }

    /**
     * resets the list of tickets and loads them from the directory
     */
    public void loadAll() {
	tickets = new ArrayList<Ticket>();
	if (!dataFolder.exists()) {
	    dataFolder.mkdir();
	}
	File[] files = dataFolder.listFiles();
	for (File file : files) {
	    System.out.println(file.getName());
	    System.out.println(file.getPath());
	    if (file.getName().endsWith(".tick")) {
		try {
		    FileInputStream fin = new FileInputStream(file);
		    ObjectInputStream in = new ObjectInputStream(fin);
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

    /**
     * Saves all of the tickets
     */
    public void saveAll() {
	if (!dataFolder.exists()) {
	    dataFolder.mkdir();
	}
	for (File oldfile : dataFolder.listFiles()) {
	    oldfile.delete();
	}
	for (Ticket newfile : tickets) {
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

    /**
     * @return A brand new ticket with a generated ID
     */
    private Ticket newTicket() {
	Ticket tick = new Ticket();
	tick.setIndex(getNextFreeIndex());
	return tick;
    }

    private void newTicketCmd(CommandSender sender, String[] segments) {
	Ticket newtick = newTicket();
	newtick.setPlayer(sender.getName());
	String message = "";
	String loc;
	Boolean skip = true;
	for (String word : segments) {
	    if (!skip) {
		message = message + word + " ";
	    } else {
		skip = false;
	    }
	}
	if (sender instanceof Player) {
	    Player player = (Player) sender;
	    loc = Math.round(player.getLocation().getX()) + ":" + Math.round(player.getLocation().getY()) + ":" + Math.round(player.getLocation().getZ());
	} else {
	    loc = "N/A";
	}
	newtick.setLocation(loc);
	message = message.trim();
	newtick.setMessage(message);
	newtick.open();
	newtick.registerTime();
	tickets.add(newtick);
	sender.sendMessage("You have successfully created a new ticket.");
	for (String line : mp.veiwTicket(newtick, 1))
	    sender.sendMessage(line);
	for (Player player : Bukkit.getOnlinePlayers()) {
	    if (player.hasPermission("steelsecurity.notifications.ticket.new") && !player.getName().equals(sender.getName())) {
		sender.sendMessage(sender.getName() + " has made a new ticket.");
	    }
	}
	log.info(sender.getName() + " has made a new ticket.");
    }

    @Override
    public boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (args.length == 0)
	    base(sender);
	else {
	    Boolean save = false;
	    if (args[0].equalsIgnoreCase("new")) {
		newcmd(sender, args);
		save = true;
	    } else if (args[0].equalsIgnoreCase("list"))
		list(sender, args);
	    else if (args[0].equalsIgnoreCase("claim")) {
		claim(sender, args);
		save = true;
	    } else if (args[0].equalsIgnoreCase("assign")) {
		assign(sender, args);
		save = true;
	    } else if (args[0].equalsIgnoreCase("view"))
		view(sender, args);
	    else if (args[0].equalsIgnoreCase("comment")) {
		comment(sender, args);
		save = true;
	    } else if (args[0].equalsIgnoreCase("close")) {
		close(sender, args);
		save = true;
	    } else if (args[0].equalsIgnoreCase("open")) {
		open(sender, args);
		save = true;
	    } else if (args[0].equalsIgnoreCase("help"))
		help(sender, args);
	    else if (args[0].equalsIgnoreCase("delete"))
		delete(sender, args);
	    else if (args[0].equalsIgnoreCase("deleteall"))
		deleteAll(sender, args);
	    else if (args[0].equalsIgnoreCase("me"))
		me(sender, args);
	    else {
		sender.sendMessage("Unkown command subcommand.");
		sender.sendMessage("For a list of subcommands for /ticket");
		sender.sendMessage("Please type /ticket help");
	    }
	    if (save) {
		saveAll();
	    }
	}
	return true;
    }

    private void claim(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.claim")) {
	    if (args.length == 2) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			Player p = (Player) sender;
			getTicket(Integer.parseInt(args[1])).setAsignnee(p);
			p.sendMessage("You have been assigned to ticket #" + args[1]);
		    } else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket claim <ID>");
		}
	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket claim <ID>");
	    }
	} else {
	    sender.sendMessage(noperm);
	}

    }

    private void assign(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.assign")) {
	    if (args.length == 3) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[2]).getPlayer();
			getTicket(Integer.parseInt(args[1])).setAsignnee(p);
			if (p.isOnline()){
			    p.getPlayer().sendMessage("You have been assigned to ticket #" + args[1]);
			}
			sender.sendMessage(p.getName() + " is now assigned to ticket #" + args[1]);
		    } else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket assign <ID> <player>");
		}
	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket assign <ID> <player>");
	    }
	} else {
	    sender.sendMessage(noperm);
	}

    }

    private void base(CommandSender sender) {
	if (sender.hasPermission("steelsecurity.commands.ticket")) {
	    sender.sendMessage(ChatColor.GREEN + "Welcome to Steel Security's Ticket Request System.");
	    sender.sendMessage(ChatColor.GREEN + "Type /ticket help for a list of commands.");
	} else {
	    sender.sendMessage(noperm);
	}
    }

    private void newcmd(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.create")) {
	    if (args.length > 1) {
		newTicketCmd(sender, args);

	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket new <message>");
	    }
	} else {
	    sender.sendMessage(noperm);
	}
    }

    private void list(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.list")) {
	    if (args.length == 1) {
		for (String line : mp.listTickets(1))
		    sender.sendMessage(line);
	    } else if (args.length == 2) {
		if (Tools.isSafeNumber(args[1])) {
		    for (String line : mp.listTickets(Integer.parseInt(args[1])))
			sender.sendMessage(line);
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The page must be a number!");
		    sender.sendMessage("Please use /ticket list (page)");
		}
	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket list (page)");
	    }
	} else {
	    sender.sendMessage(noperm);
	}
    }

    private void view(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.veiw") || sender.getName().equals(getTicket(Integer.parseInt(args[1])).getPlayerName())) {
	    if (args.length == 3) {
		if (Tools.isSafeNumber(args[1])) {
		    if (Tools.isSafeNumber(args[2])) {
			if (Integer.parseInt(args[2]) > 0) {
			    if (getTicket(Integer.parseInt(args[1])) != null) {
				    sender.sendMessage(mp.veiwTicket(getTicket(Integer.parseInt(args[1])), Integer.parseInt(args[2])));
			    } else {
				sender.sendMessage("There is no ticket with the ID of " + args[1]);
			    }
			} else {
			    sender.sendMessage("Invalid Arguments!");
			    sender.sendMessage("The ticket page must be a number greater than 0!");
			    sender.sendMessage("Please use /ticket view <ID> (page)");
			}
		    } else {
			sender.sendMessage("Invalid Arguments!");
			sender.sendMessage("The ticket page must be a number!");
			sender.sendMessage("Please use /ticket view <ID> (page)");
		    }
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket view <ID> (page)");
		}
	    } else if (args.length == 2) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			    sender.sendMessage(mp.veiwTicket(getTicket(Integer.parseInt(args[1])), 1));
		    } else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket view <ID> (page)");
		}
	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket view <ID> (page)");
	    }
	} else {
	    sender.sendMessage(noperm);
	}
    }

    private void comment(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.comment") || Tools.isSafeNumber(args[1]) && sender.getName().equals(getTicket(Integer.parseInt(args[1])).getPlayerName())) {
	    if (args.length > 2) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			String message = "";
			Boolean skip = true;
			Boolean skip2 = true;
			for (String word : args) {
			    if (!skip) {
				message = message + word + " ";
			    }
			    if (!skip2) {
				skip = false;
			    } else {
				skip2 = false;
			    }
			}
			message = message.trim();
			    Ticket t = getTicket(Integer.parseInt(args[1]));
			    t.addComment("(" + sender.getName() + ") " + message);
			    for (Player p : Bukkit.getOnlinePlayers()){
				Boolean send = false;
				if (p.hasPermission("steelsecurity.notifications.ticket.comment")) send = true;
				if (t.isAssignned()){
				    if (t.getAsignneeName().equals(p.getName())) send = true;
				}
				if (t.getPlayerName().equals(p.getName())) send = true;
				if (p.getName().equals(sender.getName())) send = false;
				if (send) p.sendMessage(sender.getName() + " just left a comment on ticket #" + t.getIndex());
			    }
		    } else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket comment <ID> <message>");
		}
	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket comment <ID> <message>");
	    }
	} else {
	    sender.sendMessage(noperm);
	}
    }

    private void close(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.close.assigned") && sender.getName().equals(getTicket(Integer.parseInt(args[1])).getAsignneeName()) || sender.hasPermission("steelsecurity.commands.ticket.close.all")) {
	    if (args.length == 2) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			Ticket t = getTicket(Integer.parseInt(args[1]));
			t.close();
			sender.sendMessage("You have just closed ticket #" + t.getIndex());
			for (Player p : Bukkit.getOnlinePlayers()){
				Boolean send = false;
				if (p.hasPermission("steelsecurity.notifications.ticket.close")) send = true;
				if (t.isAssignned()){
				    if (t.getAsignneeName().equals(p.getName())) send = true;
				}
				if (t.getPlayerName().equals(p.getName())) send = true;
				if (p.getName().equals(sender.getName())) send = false;
				if (send) p.sendMessage(sender.getName() + " just closed ticket #" + t.getIndex());
			    }
		    } else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket close <ID>");
		}
	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket close <ID>");
	    }
	} else {
	    sender.sendMessage(noperm);
	}
    }

    private void open(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.open.assigned") && sender.getName().equals(getTicket(Integer.parseInt(args[1])).getAsignneeName()) || sender.hasPermission("steelsecurity.commands.ticket.open.all")) {
	    if (args.length == 2) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			Ticket t = getTicket(Integer.parseInt(args[1]));
			t.open();
			sender.sendMessage("You have just opened ticket #" + t.getIndex());
			for (Player p : Bukkit.getOnlinePlayers()){
				Boolean send = false;
				if (p.hasPermission("steelsecurity.notifications.ticket.open")) send = true;
				if (t.isAssignned()){
				    if (t.getAsignneeName().equals(p.getName())) send = true;
				}
				if (t.getPlayerName().equals(p.getName())) send = true;
				if (p.getName().equals(sender.getName())) send = false;
				if (send) p.sendMessage(sender.getName() + " just opened ticket #" + t.getIndex());
			    }
		    } else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket open <ID>");
		}

	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket open <ID>");
	    }
	} else {
	    sender.sendMessage(noperm);
	}
    }

    private void me(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.me")) {
	}

    }

    private void delete(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.delete")) {
	    if (args.length == 2) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			delete(Integer.parseInt(args[1]));
			sender.sendMessage("You have deleted ticket #" + Integer.parseInt(args[1]));
		    } else {
			sender.sendMessage("There is no ticket with the ID of " + args[1]);
		    }
		} else {
		    sender.sendMessage("Invalid Arguments!");
		    sender.sendMessage("The ticket ID must be a number!");
		    sender.sendMessage("Please use /ticket delete <ID>");
		}
	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket delete <ID>");
	    }
	} else {
	    sender.sendMessage(noperm);
	}

    }

    private void deleteAll(CommandSender sender, String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.delete")) {
	    if (args.length == 1) {
		deleteAll();
		sender.sendMessage("All tickets have been deleted.");
	    } else {
		sender.sendMessage("Invalid Arguments!");
		sender.sendMessage("Please use /ticket deleteall");
	    }
	} else {
	    sender.sendMessage(noperm);
	}

    }

    private void delete(int index) {
	Ticket todelete = null;
	for (Ticket tick : tickets) {
	    if (tick.getIndex() == index) {
		todelete = tick;
	    }
	}
	if (todelete != null) {
	    tickets.remove(todelete);
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

    protected List<Ticket> getTickets() {
	return tickets;
    }
}
