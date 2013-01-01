package net.othercraft.steelsecurity.listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.othercraft.steelsecurity.SteelSecurity;
import net.othercraft.steelsecurity.commands.Vanish;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

public final class SpectateManager extends SSCmdExe {

    private transient final Vanish vanishManager;

    public SpectateManager(final SteelSecurity instance,final Vanish vanman) {
	super("SpectateManager", true, instance);
	vanishManager = vanman;
    }

    private transient final Map<String, Boolean> spectators = new HashMap<String, Boolean>();// if someone is spectating someone else
    private transient final Map<String, Boolean> spectatees = new HashMap<String, Boolean>();// if someone is being spectated
    private transient final Map<String, String> spectating = new HashMap<String, String>();// Who a player is spectating
    private transient final Map<String, HashSet<String>> speclist = new HashMap<String, HashSet<String>>();// Who a player is being spectated by.
    private transient final Map<String, Location> origion = new HashMap<String, Location>();// Where a player was before begining spectate
    private transient final Map<String, ItemStack[]> inventory = new HashMap<String, ItemStack[]>();// The players inventory before spectating
    private transient final Map<String, Integer> health = new HashMap<String, Integer>();
    private transient final Map<String, Integer> food = new HashMap<String, Integer>();
    private transient final Map<String, Float> exp = new HashMap<String, Float>();
    private transient final Map<String, Integer> game = new HashMap<String, Integer>();
    private transient final Map<String, Boolean> wasvanished = new HashMap<String, Boolean>();
    private transient final Map<String, Boolean> wasflying = new HashMap<String, Boolean>();
    private transient final Set<String> spectates = new HashSet<String>();// Who is spectating other people

    @EventHandler
    public void onJoin(final PlayerLoginEvent event) {
	final Player player = event.getPlayer();
	spectators.put(player.getName(), false);
	spectatees.put(player.getName(), false);
	speclist.put(player.getName(), new HashSet<String>());
	for (String playername : spectates) {
	    event.getPlayer().hidePlayer(Bukkit.getPlayerExact(playername));
	}
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
	final Player player = event.getPlayer();
	if (spectatees.get(player.getName())) {
	    final HashSet<String> tostops = speclist.get(player.getName());
	    for (String tostop : tostops) {
		stop(Bukkit.getPlayerExact(tostop));
		Bukkit.getPlayerExact(tostop).sendMessage("Spectating ended because player logged off.");
	    }
	}
	if (spectators.get(player.getName())) {
	    stop(player);
	}
	speclist.remove(player.getName());
	spectators.remove(player.getName());
	spectatees.remove(player.getName());
    }

    private void start(final Player tostart,final Player tostarton) {
	final String tostartname = tostart.getName();
	spectates.add(tostart.getName());
	wasvanished.put(tostart.getName(), vanishManager.isPlayerVanished(tostart));
	if (vanishManager.isPlayerVanished(tostart)) {
	    vanishManager.setVanished(tostart, false, false);
	}
	spectators.put(tostartname, true);
	spectating.put(tostartname, tostarton.getName());
	origion.put(tostartname, tostart.getLocation());
	health.put(tostartname, tostart.getHealth());
	food.put(tostartname, tostart.getFoodLevel());
	exp.put(tostartname, tostart.getExp());
	inventory.put(tostartname, tostart.getInventory().getContents());
	game.put(tostartname, tostart.getGameMode().getValue());
	tostart.setGameMode(tostarton.getGameMode());
	tostart.getInventory().setContents(tostarton.getInventory().getContents());
	final HashSet<String> thenew = speclist.get(tostarton.getName());
	thenew.add(tostartname);
	speclist.put(tostarton.getName(), thenew);
	for (Player player : Bukkit.getOnlinePlayers()) {
	    player.hidePlayer(tostart);
	}
	if (isSpectating(tostarton)) {
	    Boolean done = false;
	    Player player = tostarton;
	    Player nextplayer = Bukkit.getPlayerExact(spectating.get(tostartname));
	    while (!done) {
		if (!spectators.get(nextplayer.getName())) {
		    tostart.hidePlayer(nextplayer);
		    done = true;
		} else if (!(nextplayer == tostart)) {
		    tostart.hidePlayer(nextplayer);
		    player = nextplayer;
		    nextplayer = Bukkit.getPlayerExact(spectating.get(player.getName()));
		} else {
		    done = true;
		}
	    }
	}
	if (spectatees.get(tostartname)) {
	    for (String player : speclist.get(tostartname)) {
		restart(Bukkit.getPlayerExact(player));
	    }
	}
	wasflying.put(tostartname, tostart.getAllowFlight());
	spectatees.put(tostarton.getName(), true);
	tostart.setAllowFlight(true);
	tostart.hidePlayer(tostarton);
	tostart.teleport(tostarton);
	gameUpdate(tostarton);
	inventoryUpdate(tostarton);
	healthUpdate(tostarton);
	expUpdate(tostarton);
	foodUpdate(tostarton);
	if (spectators.get(tostarton.getName())) {
	    tostart.sendMessage("Warining:");
	    tostart.sendMessage("The player you are spectating is spectating another player. This may cause laggy or incomplete results.");
	}
	LOG.info(tostartname + " is now spectating " + tostart.getName());
    }

    private void stop(final Player tostop) {
	final String tostopname = tostop.getName();
	final Player tostopon = Bukkit.getPlayerExact(spectating.get(tostopname));
	for (Player player : Bukkit.getOnlinePlayers()) {
	    player.showPlayer(tostop);
	}
	tostop.showPlayer(tostopon);
	final HashSet<String> thenew = speclist.get(tostopon.getName());
	thenew.remove(tostopname);
	final Location loc = origion.get(tostopname);
	tostop.teleport(loc);
	origion.remove(tostopname);
	tostop.setGameMode(GameMode.getByValue(game.get(tostopname)));
	game.remove(tostopname);
	tostop.getInventory().setContents(inventory.get(tostopname));
	inventory.remove(tostopname);
	tostop.setFoodLevel(food.get(tostopname));
	food.remove(tostopname);
	tostop.setHealth(health.get(tostopname));
	health.remove(tostopname);
	tostop.setExp(exp.get(tostopname));
	exp.remove(tostopname);
	if (wasvanished.get(tostopname)) {
	    vanishManager.setVanished(tostop, true, false);
	}
	wasvanished.remove(tostopname);
	tostop.setAllowFlight(wasflying.get(tostopname));
	wasflying.remove(tostopname);
	if (spectatees.get(tostopname)) {
	    for (String player : speclist.get(tostopname)) {
		restart(Bukkit.getPlayerExact(player));
	    }
	}
	spectators.put(tostopname, false);
	spectates.remove(tostopname);
	spectating.remove(tostopname);
	speclist.put(tostopon.getName(), thenew);
	spectatees.put(tostopon.getName(), false);
	tostop.sendMessage("You are no longer spectating " + tostopon.getName());
	LOG.info(tostopname + " is no longer spectating " + tostopon.getName());
    }

    public void stopAll() {
	for (String player : spectates) {
	    stop(Bukkit.getPlayerExact(player));
	}
    }

    public void restart(final Player torestart) {
	final Player torestarton = Bukkit.getPlayerExact(spectating.get(torestart.getName()));
	stop(torestart);
	start(torestart, torestarton);
    }

    public void specCmd(final CommandSender sender,final String[] args) {
	final Player player = Bukkit.getPlayerExact(sender.getName());
	if (args.length == 2) {
	    if (spectators.get(player.getName())) {
		stop(player);
	    }
	    final Player tostarton = Bukkit.getPlayer(args[1]);
	    if (player != tostarton) {
		if (tostarton != null) {
		    start(player, tostarton);
		    sender.sendMessage("You have begun spectating " + tostarton.getName() + ".");
		} else {
		    sender.sendMessage("We could not find anybody by the name of " + args[1] + ".");
		}
	    } else {
		sender.sendMessage("You can't spectate your self!");
	    }
	} else {
	    player.sendMessage("Invailed Arguments!");
	    player.sendMessage("Usage: /sts spectate <player>");
	}
    }

    public void registerAll() {
	for (Player player : Bukkit.getOnlinePlayers()) {
	    spectators.put(player.getName(), false);
	    spectatees.put(player.getName(), false);
	    speclist.put(player.getName(), new HashSet<String>());
	}
    }

    public Boolean isSpectating(final Player player) {
	return spectators.get(player.getName());
    }

    public Map<String, String> spectateList() {
	return spectating;
    }

    // Beyond here only apllies to when a player is being spectated
    @EventHandler
    public void onFollow(final PlayerMoveEvent event) {
	final Player player = event.getPlayer();
	if (spectators.get(player.getName())) {
	    event.getPlayer().teleport(Bukkit.getPlayerExact(spectating.get(event.getPlayer().getName())));
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    for (String playername : speclist.get(event.getPlayer().getName())) {
		Bukkit.getPlayerExact(playername).setVelocity(event.getPlayer().getVelocity());
		Bukkit.getPlayerExact(playername).teleport(event.getPlayer());
	    }
	}
    }

    @EventHandler
    public void onVelocity(final PlayerVelocityEvent event) {
	if (spectatees.get(event.getPlayer().getName())) {
	    for (String playername : speclist.get(event.getPlayer().getName())) {
		Bukkit.getPlayerExact(playername).setVelocity(event.getVelocity());
	    }
	}
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = (Player) event.getPlayer();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = (Player) event.getPlayer();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = (Player) event.getPlayer();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onPickup(final PlayerPickupItemEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = (Player) event.getPlayer();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
	if (spectators.get(event.getWhoClicked().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getWhoClicked().getName())) {
	    Player player = (Player) event.getWhoClicked();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onTarget(final EntityTargetEvent event) {
	if (event.getTarget() instanceof Player) {
	    Player player = (Player) event.getTarget();
	    if (spectators.get(player.getName())) {
		event.setCancelled(true);
	    }
	}
    }

    @EventHandler
    public void onGmChange(final PlayerGameModeChangeEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	    game.put(event.getPlayer().getName(), event.getNewGameMode().getValue());
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = event.getPlayer();
	    gameUpdate(player);
	}
    }

    @EventHandler
    public void onLvlChange(final PlayerLevelChangeEvent event) {
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = event.getPlayer();
	    expUpdate(player);
	}
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent event) {
	if (event.getEntity() instanceof Player) {
	    Player player = (Player) event.getEntity();
	    if (spectators.get(player.getName())) {
		event.setCancelled(true);
	    }
	    if (spectatees.get(player.getName())) {
		healthUpdate(player);
	    }
	}
    }

    @EventHandler
    public void onHeal(final EntityRegainHealthEvent event) {
	if (event.getEntity() instanceof Player) {
	    Player player = (Player) event.getEntity();
	    if (spectators.get(player.getName())) {
		event.setCancelled(true);
	    }
	    if (spectatees.get(player.getName())) {
		healthUpdate(player);
	    }
	}
    }

    @EventHandler
    public void onFoodChange(final FoodLevelChangeEvent event) {
	if (event.getEntity() instanceof Player) {
	    Player player = (Player) event.getEntity();
	    if (spectators.get(player.getName())) {
		event.setCancelled(true);
	    }
	    if (spectatees.get(player.getName())) {
		foodUpdate(player);
	    }
	}
    }

    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
	Player player = (Player) event.getEntity();
	if (spectatees.get(player.getName())) {
	    HashSet<String> list = speclist.get(player.getName());
	    for (String playername : list) {
		Bukkit.getPlayerExact(playername).sendMessage("The player you were spectating died. You will continue spectating them once they respawn.");
	    }
	}
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
	Player player = event.getPlayer();
	if (spectatees.get(player.getName())) {
	    inventoryUpdate(player);
	    healthUpdate(player);
	    expUpdate(player);
	    foodUpdate(player);
	    gameUpdate(player);
	    locUpdate(player);
	}
    }

    @EventHandler
    public void onEat(final PlayerLevelChangeEvent event) {
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = event.getPlayer();
	    expUpdate(player);
	}
    }

    private void locUpdate(final Player player) {
	final HashSet<String> list = speclist.get(player.getName());
	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	    public void run() {
		for (String playername : list) {
		    Bukkit.getPlayerExact(playername).teleport(player);
		}
	    }
	}, 1L);
    }

    private void inventoryUpdate(final Player player) {
	final HashSet<String> list = speclist.get(player.getName());
	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	    public void run() {
		for (String playername : list) {
		    Bukkit.getPlayerExact(playername).getInventory().setContents(player.getInventory().getContents());
		}
	    }
	}, 1L);
    }

    private void gameUpdate(final Player player) {
	final HashSet<String> list = speclist.get(player.getName());
	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	    public void run() {
		for (String playername : list) {
		    Bukkit.getPlayerExact(playername).setGameMode(player.getGameMode());
		}
	    }
	}, 1L);
    }

    private void foodUpdate(final Player player) {
	final HashSet<String> list = speclist.get(player.getName());
	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	    public void run() {
		for (String playername : list) {
		    Bukkit.getPlayerExact(playername).setFoodLevel(player.getFoodLevel());
		}
	    }
	}, 1L);
    }

    private void expUpdate(final Player player) {
	final HashSet<String> list = speclist.get(player.getName());
	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	    public void run() {
		for (String playername : list) {
		    Bukkit.getPlayerExact(playername).setExp(player.getExp());
		}
	    }
	}, 1L);
    }

    private void healthUpdate(final Player player) {
	final HashSet<String> list = speclist.get(player.getName());
	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	    public void run() {
		int newhealth = player.getHealth();
		if (newhealth < 1) {
		    newhealth = 1;
		}
		for (String playername : list) {
		    Bukkit.getPlayerExact(playername).setHealth(newhealth);
		}
	    }
	}, 1L);
    }
}