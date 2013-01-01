package net.othercraft.steelsecurity.ticketsystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.utils.SSCmdExe;
import net.othercraft.steelsecurity.utils.Tools;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TicketManager extends SSCmdExe {
    private List<Ticket> tickets;
    private static final String NOPERM = ChatColor.RED + "You don't have permission to do this!";
    private final TicketMessageProccessor mp = new TicketMessageProccessor(this);
    private final File dataFolder;
    private static final ChatColor GREEN = ChatColor.GREEN;
    private static final ChatColor YELLOW = ChatColor.YELLOW;
    private static final ChatColor RED = ChatColor.RED;

    /**
     * @param datafolder
     *            The folder where tickets will be stored.
     */
    public TicketManager(File datafolder, SteelSecurity instance) {
	super("TicketManager", false, instance);
	this.dataFolder = datafolder;
	initiate();
    }

    /**
     * @param index
     *            The ID of the ticket you want to recieve
     * @return If a ticket with the ID exist, the ticket, else null
     */
    public Ticket getTicket(final int index) {
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
	    if (file.getName().endsWith(".tick")) {
		Ticket ticket = null;
		ticket = (Ticket) Tools.grabObject(file);
		if (ticket!=null){
		    tickets.add(ticket);
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
	    Tools.saveObject(tosave, newfile);
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

    private void newTicketCmd(final CommandSender sender,final String[] segments) {
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
	    final Player player = (Player) sender;
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
	LOG.info(sender.getName() + " has made a new ticket.");
    }

    @Override
    public boolean handleCommand(final CommandSender sender,final Command cmd,final String label,final String[] args) {
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
	    } else if (args[0].equalsIgnoreCase("help")) {
		if (sender.hasPermission("steelsecurity.commands.ticket.help")) {
		    if (args.length == 1) {
			help(sender);
		    } else if (args.length == 2) {
			if (Tools.isSafeNumber(args[1])) {
			    help(sender, Integer.parseInt(args[1]));
			} else {
			    sender.sendMessage("Page must be a number!");
			}
		    } else {
			sender.sendMessage(RED + "Too many arguments!");
		    }
		} else {
		    sender.sendMessage(NOPERM);
		}
	    }

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

    private void claim(final CommandSender sender,final String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.claim")) {
	    if (args.length == 2) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			final Player p = (Player) sender;
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
	    sender.sendMessage(NOPERM);
	}

    }

    private void assign(final CommandSender sender,final String[] args) {
	if (sender.hasPermission("steelsecurity.commands.ticket.assign")) {
	    if (args.length == 3) {
		if (Tools.isSafeNumber(args[1])) {
		    if (getTicket(Integer.parseInt(args[1])) != null) {
			final OfflinePlayer p = Bukkit.getOfflinePlayer(args[2]).getPlayer();
			getTicket(Integer.parseInt(args[1])).setAsignnee(p);
			if (p.isOnline()) {
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
	    sender.sendMessage(NOPERM);
	}

    }

    private void base(final CommandSender sender) {
	if (sender.hasPermission("steelsecurity.commands.ticket")) {
	    sender.sendMessage(ChatColor.GREEN + "Welcome to Steel Security's Ticket Request System.");
	    sender.sendMessage(ChatColor.GREEN + "Type /ticket help for a list of commands.");
	} else {
	    sender.sendMessage(NOPERM);
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
	    sender.sendMessage(NOPERM);
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
	    sender.sendMessage(NOPERM);
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
	    sender.sendMessage(NOPERM);
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
			for (Player p : Bukkit.getOnlinePlayers()) {
			    Boolean send = false;
			    if (p.hasPermission("steelsecurity.notifications.ticket.comment"))
				send = true;
			    if (t.isAssignned()) {
				if (t.getAsignneeName().equals(p.getName()))
				    send = true;
			    }
			    if (t.getPlayerName().equals(p.getName()))
				send = true;
			    if (p.getName().equals(sender.getName()))
				send = false;
			    if (send)
				p.sendMessage(sender.getName() + " just left a comment on ticket #" + t.getIndex());
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
	    sender.sendMessage(NOPERM);
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
			for (Player p : Bukkit.getOnlinePlayers()) {
			    Boolean send = false;
			    if (p.hasPermission("steelsecurity.notifications.ticket.close"))
				send = true;
			    if (t.isAssignned()) {
				if (t.getAsignneeName().equals(p.getName()))
				    send = true;
			    }
			    if (t.getPlayerName().equals(p.getName()))
				send = true;
			    if (p.getName().equals(sender.getName()))
				send = false;
			    if (send)
				p.sendMessage(sender.getName() + " just closed ticket #" + t.getIndex());
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
	    sender.sendMessage(NOPERM);
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
			for (Player p : Bukkit.getOnlinePlayers()) {
			    Boolean send = false;
			    if (p.hasPermission("steelsecurity.notifications.ticket.open"))
				send = true;
			    if (t.isAssignned()) {
				if (t.getAsignneeName().equals(p.getName()))
				    send = true;
			    }
			    if (t.getPlayerName().equals(p.getName()))
				send = true;
			    if (p.getName().equals(sender.getName()))
				send = false;
			    if (send)
				p.sendMessage(sender.getName() + " just opened ticket #" + t.getIndex());
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
	    sender.sendMessage(NOPERM);
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
	    sender.sendMessage(NOPERM);
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
	    sender.sendMessage(NOPERM);
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

    private void help(CommandSender sender) {
	help(sender, 1);
    }

    private void help(CommandSender sender, int page) {
	List<String> allowcmds = new ArrayList<String>();
	if (sender.hasPermission("steelsecurity.commands.ticket")) {
	    allowcmds.add(GREEN + "/ticket:" + YELLOW + " Base Command");
	}
	if (sender.hasPermission("steelsecurity.commands.ticket.help")) {
	    allowcmds.add(GREEN + "/ticket help:" + YELLOW + " Displays this help screen.");
	}
	if (sender.hasPermission("steelsecurity.commands.ticket.create")) {
	    allowcmds.add(GREEN + "/ticket new:" + YELLOW + " Creates a new ticket.");
	}
	if (sender.hasPermission("steelsecurity.commands.ticket.open.assigned") || sender.hasPermission("steelsecurity.commands.ticket.open.all")) {
	    allowcmds.add(GREEN + "/ticket open:" + YELLOW + " Re-opens a ticket");
	}
	allowcmds.add(GREEN + "/ticket close:" + YELLOW + " Closes a ticket.");
	if (sender.hasPermission("steelsecurity.commands.ticket.delete")) {
	    allowcmds.add(GREEN + "/ticket delete:" + YELLOW + " Deletes a ticket.");
	    allowcmds.add(GREEN + "/ticket deleteall:" + YELLOW + " Deletes all tickets.");
	}
	if (sender.hasPermission("steelsecurity.commands.ticket.comment")) {
	    allowcmds.add(GREEN + "/ticket comment:" + YELLOW + " Leaves a comment on a ticket.");
	}
	if (sender.hasPermission("steelsecurity.commands.ticket.claim")) {
	    allowcmds.add(GREEN + "/ticket claim:" + YELLOW + " Claims a ticket for yourself.");
	}
	if (sender.hasPermission("steelsecurity.commands.ticket.list")) {
	    allowcmds.add(GREEN + "/ticket list:" + YELLOW + " List the tickets.");
	}
	if (sender.hasPermission("steelsecurity.commands.ticket.view")) {
	    allowcmds.add(GREEN + "/ticket view:" + YELLOW + " Veiw a ticket.");
	}
	if (sender.hasPermission("steelsecurity.commands.ticket.assign")) {
	    allowcmds.add(GREEN + "/ticket assign:" + YELLOW + " Assign a ticket to a player.");
	}
	int pages = Tools.getPages(allowcmds, 6);
	allowcmds = Tools.getPage(allowcmds, page, 6);
	sender.sendMessage("Displaying page " + page + " of " + pages + ":");
	for (String line : allowcmds) {
	    sender.sendMessage(line);
	}
    }

    protected List<Ticket> getTickets() {
	return tickets;
    }
}
