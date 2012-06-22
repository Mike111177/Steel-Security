package net.othercraft.steelsecurity.listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.commands.Vanish;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;

public class SpectateManager extends SSCmdExe {

    Main plugin;

    Vanish vm;

    public SpectateManager(String name, Main instance, Vanish vanman) {
	super("SpectateManager", true);
	vm = vanman;
	plugin = instance;
    }

    private Map<String, Boolean> spectators = new HashMap<String, Boolean>();// if
									     // some
									     // is
									     // a
									     // spectating
									     // someone
									     // else.
    private Map<String, Boolean> spectatees = new HashMap<String, Boolean>();// if
									     // someone
									     // is
									     // being
									     // spectated.
    private Map<String, String> spectating = new HashMap<String, String>();// Who
									   // a
									   // player
									   // is
									   // spectating.
    private Map<String, HashSet<String>> speclist = new HashMap<String, HashSet<String>>();// Who
											   // a
											   // player
											   // is
											   // being
											   // spectated
											   // by.
    private Map<String, Location> origion = new HashMap<String, Location>();// Where
									    // a
									    // player
									    // was
									    // before
									    // beginning
									    // spectate
    private Map<String, ItemStack[]> inventory = new HashMap<String, ItemStack[]>();// The
										    // players
										    // inventory
										    // before
										    // starting
										    // to
										    // spectate
    private Map<String, Integer> health = new HashMap<String, Integer>();
    private Map<String, Integer> food = new HashMap<String, Integer>();
    private Map<String, Float> exp = new HashMap<String, Float>();
    private Map<String, Integer> game = new HashMap<String, Integer>();
    private Map<String, Boolean> wasvanished = new HashMap<String, Boolean>();
    private Map<String, Boolean> wasflying = new HashMap<String, Boolean>();
    private HashSet<String> spectates = new HashSet<String>();// Who is
							      // specating
							      // other people

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
	Player player = event.getPlayer();
	spectators.put(player.getName(), false);
	spectatees.put(player.getName(), false);
	speclist.put(player.getName(), new HashSet<String>());
	for (String playername : spectates) {
	    event.getPlayer().hidePlayer(Bukkit.getPlayerExact(playername));
	}
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
	Player player = event.getPlayer();
	if (spectatees.get(player.getName())) {
	    HashSet<String> tostops = speclist.get(player.getName());
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

    private void start(Player tostart, Player tostarton) {
	String tostartname = tostart.getName();
	spectates.add(tostart.getName());
	wasvanished.put(tostart.getName(), vm.isVanished(tostart));
	if (vm.isVanished(tostart)) {
	    vm.setVanished(tostart, false);
	}
	spectators.put(tostartname, true);
	spectatees.put(tostarton.getName(), true);
	spectating.put(tostartname, tostarton.getName());
	origion.put(tostartname, tostart.getLocation());
	health.put(tostartname, tostart.getHealth());
	food.put(tostartname, tostart.getFoodLevel());
	exp.put(tostartname, tostart.getExp());
	inventory.put(tostartname, tostart.getInventory().getContents());
	game.put(tostartname, tostart.getGameMode().getValue());
	tostart.setGameMode(tostarton.getGameMode());
	tostart.getInventory().setContents(tostarton.getInventory().getContents());
	HashSet<String> thenew = speclist.get(tostarton.getName());
	thenew.add(tostartname);
	speclist.put(tostarton.getName(), thenew);
	for (Player player : Bukkit.getOnlinePlayers()) {
	    player.hidePlayer(tostart);
	}
	wasflying.put(tostartname, tostart.getAllowFlight());
	tostart.setAllowFlight(true);
	tostart.hidePlayer(tostarton);
	tostart.teleport(tostarton);
	inventoryUpdate(tostarton);
	healthUpdate(tostarton);
	expUpdate(tostarton);
	foodUpdate(tostarton);
	gameUpdate(tostarton);
    }

    private void stop(Player tostop) {
	String tostopname = tostop.getName();
	spectates.remove(tostop);
	Player tostopon = Bukkit.getPlayerExact(spectating.get(tostopname));
	spectators.put(tostopname, false);
	for (Player player : Bukkit.getOnlinePlayers()) {
	    player.showPlayer(tostop);
	}
	tostop.showPlayer(tostopon);
	spectating.remove(tostop);
	HashSet<String> thenew = speclist.get(tostopon.getName());
	thenew.remove(tostop);
	speclist.put(tostopon.getName(), thenew);
	Location loc = origion.get(tostopname);
	tostop.teleport(loc);
	spectatees.put(tostopon.getName(), false);
	origion.remove(tostopname);
	tostop.getInventory().setContents(inventory.get(tostopname));
	inventory.remove(tostopname);
	tostop.setFoodLevel(food.get(tostopname));
	food.remove(tostopname);
	tostop.setHealth(health.get(tostopname));
	health.remove(tostopname);
	tostop.setExp(exp.get(tostopname));
	exp.remove(tostopname);
	tostop.setGameMode(GameMode.getByValue(game.get(tostopname)));
	game.remove(tostopname);
	vm.setVanished(tostop, wasvanished.get(tostopname));
	wasvanished.remove(tostopname);
	tostop.setAllowFlight(wasflying.get(tostopname));
	wasflying.remove(tostopname);
    }

    public void stopAll() {
	for (String player : spectates) {
	    stop(Bukkit.getPlayerExact(player));
	}
    }

    public void specCmd(CommandSender sender, String[] args) {
	Player player = Bukkit.getPlayerExact(sender.getName());
	if (!(args.length > 2)) {
	    if (spectators.get(player.getName())) {
		stop(player);
	    }
	    if (args.length == 2) {
		Player tostarton = Bukkit.getPlayer(args[1]);
		    if (player != tostarton) {
			if (tostarton != null) {
			    if (!spectators.get(tostarton.getName())) {
			    start(player, tostarton);
			    }
			    else {
				sender.sendMessage("Were sorry, you can not spectate someone who is all ready spectating someone else.");
			    }
			} 
			else {
			    sender.sendMessage("We could not find anybody by the name of " + args[1]);
			}
		    } 
		    else {
			sender.sendMessage("You can't spectate your self!");
		    }
	    } else {
		player.sendMessage("Too many arguments!");
		player.sendMessage("Usage: /sts spectate <player>");
	    }
	}
    }

    public void registerAll() {
	for (Player player : Bukkit.getOnlinePlayers()) {
	    spectators.put(player.getName(), false);
	    spectatees.put(player.getName(), false);
	    speclist.put(player.getName(), new HashSet<String>());
	}
    }

    public Boolean isSpectating(Player player) {
	return spectators.get(player.getName());
    }
    public Map<String, String> spectateList(){
	return spectating;
    }

    // Beyond here only apllies to when a player is being spectated
    @EventHandler
    public void onFollow(PlayerMoveEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.getPlayer().teleport(Bukkit.getPlayerExact(spectating.get(event.getPlayer().getName())));
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    for (String playername : speclist.get(event.getPlayer().getName())) {
		Bukkit.getPlayerExact(playername).teleport(event.getPlayer());
	    }
	}
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent event) {
	if (spectatees.get(event.getPlayer().getName())) {
	    for (String playername : speclist.get(event.getPlayer().getName())) {
		Bukkit.getPlayerExact(playername).setVelocity(event.getVelocity());
	    }
	}
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = (Player) event.getPlayer();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = (Player) event.getPlayer();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = (Player) event.getPlayer();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = (Player) event.getPlayer();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
	if (spectators.get(event.getWhoClicked().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getWhoClicked().getName())) {
	    Player player = (Player) event.getWhoClicked();
	    inventoryUpdate(player);
	}
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
	if (event.getTarget() instanceof Player) {
	    Player player = (Player) event.getTarget();
	    if (spectators.get(player.getName())) {
		event.setCancelled(true);
	    }
	}
    }

    @EventHandler
    public void onGmChange(PlayerGameModeChangeEvent event) {
	if (spectators.get(event.getPlayer().getName())) {
	    event.setCancelled(true);
	}
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = event.getPlayer();
	    gameUpdate(player);
	}
    }

    @EventHandler
    public void onLvlChange(PlayerLevelChangeEvent event) {
	if (spectatees.get(event.getPlayer().getName())) {
	    Player player = event.getPlayer();
	    expUpdate(player);
	}
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
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
    public void onHeal(EntityRegainHealthEvent event) {
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
    public void onFoodChange(FoodLevelChangeEvent event) {
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
    public void onDeath(PlayerDeathEvent event) {
	Player player = (Player) event.getEntity();
	if (spectatees.get(player.getName())) {
	    HashSet<String> list = speclist.get(player.getName());
	    for (String playername : list) {
		Bukkit.getPlayerExact(playername).sendMessage("The player you were spectating died. You will continue spectating them once they respawn.");
	    }
	}
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
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
    public void onEat(PlayerLevelChangeEvent event) {
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