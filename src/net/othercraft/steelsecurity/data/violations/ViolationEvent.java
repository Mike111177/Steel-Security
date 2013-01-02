package net.othercraft.steelsecurity.data.violations;

import org.bukkit.entity.Player;

public final class ViolationEvent {

    private final String name;
    private final String type;
    private final Player player;
    private final boolean succus;
    private final int previosNumber;
    private final Integer amount;
    private final int newNumber;

    public ViolationEvent(final String name, 
	    final String type,
	    final Player player,
	    final boolean succus, 
	    final int previosNumber,
	    final Integer amount,
	    final int newNumber) {
	this.name = name;
	this.type = type;
	this.player = player;
	this.succus = succus;
	this.previosNumber = previosNumber;
	this.amount = amount;
	this.newNumber = newNumber;
    }

    public String getViolation() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean wasSuccessFull() {
        return succus;
    }

    public int getPreviosNumber() {
        return previosNumber;
    }

    public Integer getAmount() {
        return amount;
    }

    public int getNewNumber() {
        return newNumber;
    }

}
