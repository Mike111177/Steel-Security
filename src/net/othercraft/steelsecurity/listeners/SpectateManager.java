package net.othercraft.steelsecurity.listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.commands.Vanish;
import net.othercraft.steelsecurity.utils.SSCmdExe;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
	Map<String, Boolean> spectators = new HashMap<String, Boolean>();//if some is a spectating someone else.
	Map<String, Boolean> spectatees = new HashMap<String, Boolean>();//if someone is being spectated.
	Map<String, String> spectating = new HashMap<String, String>();//Who a player is spectating.
	Map<String, HashSet<String>> speclist = new HashMap<String, HashSet<String>>();//Who a player is being spectated by.
	Map<String, Location> origion = new HashMap<String, Location>();//Where a player was before beginning spectate
	Map<String, ItemStack[]> inventory = new HashMap<String, ItemStack[]>();//The players inventory before starting to spectate
	Map<String, Integer> health = new HashMap<String, Integer>();
	Map<String, Integer> food = new HashMap<String, Integer>();
	Map<String, Integer> exp = new HashMap<String, Integer>();
	Map<String, Boolean> wasvanished = new HashMap<String, Boolean>();
	HashSet<String> spectates = new HashSet<String>();//Who is specating other people

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		spectators.put(player.getName(), false);
		spectatees.put(player.getName(), false);
		speclist.put(player.getName(), new HashSet<String>());
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (spectatees.get(player.getName())){
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
		spectates.add(tostart.getName());
		wasvanished.put(tostart.getName(), vm.isVanished(tostart));
		if (vm.isVanished(tostart)){
			vm.setVanished(tostart, false);
		}
		spectators.put(tostart.getName(), true);
		spectatees.put(tostarton.getName(), true);
		spectating.put(tostart.getName(), tostarton.getName());
		origion.put(tostart.getName(), tostart.getLocation());
		inventory.put(tostart.getName(), tostart.getInventory().getContents());
		tostart.getInventory().setContents(tostarton.getInventory().getContents());
		HashSet<String> thenew = speclist.get(tostarton.getName());
		thenew.add(tostart.getName());
		speclist.put(tostarton.getName(), thenew);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.hidePlayer(tostart);
		}
		tostart.hidePlayer(tostarton);
		tostart.teleport(tostarton);
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
		vm.setVanished(tostop, wasvanished.get(tostopname));
		wasvanished.remove(tostopname);
	}
	public void stopAll() {
		for (String player : spectates) {
			stop(Bukkit.getPlayerExact(player));
		}
	}
	public void specCmd(CommandSender sender, String[] args) {
		Player player = Bukkit.getPlayerExact(sender.getName());
		if (!(args.length>2)){
			if (spectators.get(player.getName())) {
				stop(player);
			}
			if (args.length==2) {
				Player tostarton = Bukkit.getPlayer(args[1]);
				if (player!=tostarton){
					if (tostarton!=null) {
						start(player, tostarton);
					}
					else {
						sender.sendMessage("We could not find anybody by the name of " + args[1]);
					}
				}
				else {
					sender.sendMessage("You can't spectate your self!");
				}
			}
		}
		else {
			player.sendMessage("Too many arguments!");
			player.sendMessage("Usage: /sts spectate <player>");
		}
	}
	public void registerAll() {
		for (Player player : Bukkit.getOnlinePlayers()){
			spectators.put(player.getName(), false);
			spectatees.put(player.getName(), false);
			speclist.put(player.getName(), new HashSet<String>());
		}
	}
	public Boolean isSpectating(Player player){
		return spectators.get(player.getName());
	}
	//Beyond here only apllies to when a player is being spectated
	@EventHandler
	public void onFollow(PlayerMoveEvent event){
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
	public void onVelocity(PlayerVelocityEvent event){
		if (spectatees.get(event.getPlayer().getName())) {
			for (String playername : speclist.get(event.getPlayer().getName())) {
				Bukkit.getPlayerExact(playername).setVelocity(event.getVelocity());
			}
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent event){
		if (spectators.get(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
		if (spectatees.get(event.getPlayer().getName())){
			for (String playername : speclist.get(event.getPlayer().getName())) {
				Bukkit.getPlayerExact(playername).getInventory().setContents(event.getPlayer().getInventory().getContents());
			}
		}
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		if (spectators.get(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
		if (spectatees.get(event.getPlayer().getName())){
			for (String playername : speclist.get(event.getPlayer().getName())) {
				Bukkit.getPlayerExact(playername).getInventory().setContents(event.getPlayer().getInventory().getContents());
			}
		}
	}
	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		if (spectators.get(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
		if (spectatees.get(event.getPlayer().getName())){
			for (String playername : speclist.get(event.getPlayer().getName())) {
				Bukkit.getPlayerExact(playername).getInventory().setContents(event.getPlayer().getInventory().getContents());
			}
		}
	}
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event){
		if (spectators.get(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
		if (spectatees.get(event.getPlayer().getName())){
			for (String playername : speclist.get(event.getPlayer().getName())) {
				Bukkit.getPlayerExact(playername).getInventory().setContents(event.getPlayer().getInventory().getContents());
			}
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if (spectators.get(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if (spectators.get(event.getWhoClicked().getName())) {
			event.setCancelled(true);
		}
		if (spectatees.get(event.getWhoClicked().getName())){
			for (String playername : speclist.get(event.getWhoClicked().getName())) {
				Bukkit.getPlayerExact(playername).getInventory().setContents(event.getWhoClicked().getInventory().getContents());
			}
		}
	}
	@EventHandler
	public void onTarget(EntityTargetEvent event){
		if (event.getTarget() instanceof Player){
			Player player = (Player) event.getTarget();
			if (spectators.get(player.getName())) {
			}
		}
	}
}