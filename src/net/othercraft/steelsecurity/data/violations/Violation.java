package net.othercraft.steelsecurity.data.violations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class Violation {

	protected final String name;

	protected final Set<IViolationListener> listeners = new HashSet<IViolationListener>();
	protected HashMap<String, Integer> players = new HashMap<String, Integer>();

	protected Violation(final String name){
	    this.name = name;
	}

	protected void engageAll() {
	    for (Player player : Bukkit.getServer().getOnlinePlayers()){
		engage(player);
	    }
	}
	protected void engage(final Player player) {
	    if (!players.containsKey(player.getName())){
		players.put(player.getName(), 0);
	    }
	}
	protected void disengageAll() {
	    players = new HashMap<String, Integer>();
	}
	protected void disengage(final Player player) {
	    players.remove(player);
	}
	protected void add(final Integer amount,final Player player) {
	    boolean succus = false;
	    int previosNumber = 0;
	    int newNumber = 0;
	    if (players.containsKey(player.getName())){
		previosNumber = players.get(player.getName());
		newNumber = previosNumber + amount;
		players.put(player.getName(), newNumber);
		succus = true;
	    }
	    final ViolationEvent event = new ViolationEvent(name, ViolationsManager.ADD, player, succus, previosNumber, amount, newNumber);
	    for (IViolationListener listener : listeners){
		listener.proccessViolation(event);
	    }
	}
	protected void set(final Integer amount,final Player player) {
	    boolean succus = false;
	    int previosNumber = 0;
	    int newNumber = 0;
	    if (players.containsKey(player.getName())){
		previosNumber = players.get(player.getName());
		newNumber = amount;
		players.put(player.getName(), newNumber);
		succus = true;
	    }
	    final ViolationEvent event = new ViolationEvent(name, ViolationsManager.SET, player, succus, previosNumber, amount, newNumber);
	    for (IViolationListener listener : listeners){
		listener.proccessViolation(event);
	    }
	}
	protected void reset(final Player player) {
	    boolean succus = false;
	    int previosNumber = 0;
	    if (players.containsKey(player.getName())){
		previosNumber = players.get(player.getName());
		players.put(player.getName(), 0);
		succus = true;
	    }
	    final ViolationEvent event = new ViolationEvent(name, ViolationsManager.RESET, player, succus, previosNumber, 0, 0);
	    for (IViolationListener listener : listeners){
		listener.proccessViolation(event);
	    }
	}
	protected void subtract(final Integer amount, final Player player){
	    boolean succus = false;
	    int previosNumber = 0;
	    int newNumber = 0;
	    if (players.containsKey(player.getName())){
		previosNumber = players.get(player.getName());
		newNumber = previosNumber - amount;
		players.put(player.getName(), newNumber);
		succus = true;
	    }
	    final ViolationEvent event = new ViolationEvent(name, ViolationsManager.SUBTRACT, player, succus, previosNumber, amount, newNumber);
	    for (IViolationListener listener : listeners){
		listener.proccessViolation(event);
	    }
	}

	public boolean addListener(final IViolationListener listener) {
		return listeners.add(listener);   
	}
}


