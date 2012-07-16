package net.othercraft.steelsecurity.ticketsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    private String origional;
    private String player;
    private String asignnee = null;
    private Boolean open;
    private Long time;
    private int index;
    private List<String> comments = new ArrayList<String>(0);
    private String location;

    /**
     * @param message
     *            The message to set
     */
    public void setMessage(String message) {
	this.origional = message;
    }

    /**
     * @param player
     *            the player to set as the creator of the ticket.
     */
    public void setPlayer(Player player) {
	this.player = player.getName();
    }

    /**
     * @param player
     *            the offline player to set as the creator of the ticket.
     */
    public void setPlayer(OfflinePlayer player) {
	this.player = player.getName();
    }

    /**
     * @param playername
     *            the name of the player to set as the creator of the ticket
     */
    public void setPlayer(String playername) {
	this.player = playername;
    }

    /**
     * @param player
     *            the player to assign the ticket to
     */
    public void setAsignnee(Player player) {
	this.asignnee = player.getName();
    }

    /**
     * @param player
     *            the offline player to assign the ticket to
     */
    public void setAsignnee(OfflinePlayer player) {
	this.asignnee = player.getName();
    }

    /**
     * @param playername
     *            the name of the player to assign the ticket to
     */
    public void setAsignnee(String playername) {
	this.asignnee = playername;
    }

    /**
     * @return the message of the ticket
     */
    public String getMessage() {
	return origional;
    }

    /**
     * @return the creator of the ticket
     */
    public OfflinePlayer getPlayer() {
	return Bukkit.getOfflinePlayer(player);
    }

    /**
     * @return the player that the ticket is assigned to
     */
    public OfflinePlayer getAsignnee() {
	if (asignnee == null) {
	    return null;
	}
	return Bukkit.getOfflinePlayer(asignnee);
    }

    /**
     * @return the time in milliseconds that the ticket was made
     */
    public Long getTime() {
	return time;
    }

    /**
     * @return weather or not the creator of the ticket is online.
     */
    public Boolean isPlayerOnline() {
	return Bukkit.getOfflinePlayer(player).isOnline();
    }

    /**
     * @return weather or not the person who is assigned to is online
     */
    public Boolean isAsignneeOnline() {
	return Bukkit.getOfflinePlayer(asignnee).isOnline();
    }

    /**
     * @return weather or not the ticket is open
     */
    public Boolean isOpen() {
	return open;
    }

    /**
     * Opens the ticket
     */
    public void open() {
	this.open = true;
    }

    /**
     * Closes the ticket
     */
    public void close() {
	this.open = false;
    }

    /**
     * Adds a comment to the list of commetns in the ticket
     * 
     * @param comment
     *            the comment message
     */
    public void addComment(String comment) {
	comments.add("#" + (comments.size() + 1) + " " + comment);
    }

    /**
     * @return the index of the ticket
     */
    public Integer getIndex() {
	return index;
    }

    /**
     * Saves the ticket creation time as the current time
     */
    public void registerTime() {
	time = System.currentTimeMillis();
    }

    /**
     * @return the name of the player who is assigned to the ticket
     */
    public String getAsignneeName() {
	return asignnee;
    }

    /**
     * @return the name of the player who created the ticket
     */
    public String getPlayerName() {
	return player;
    }

    /**
     * @return the list of comments in the ticket
     */
    public List<String> getComments() {
	return comments;
    }

    /**
     * @return weather or not this ticket is assigned to someone
     */
    public Boolean isAssignned() {
	return asignnee != null;
    }

    /**
     * Sets the location at which the ticket was made
     * 
     * @param loc
     *            the string that represents the location at which the ticket was made
     */
    public void setLocation(String loc) {
	this.location = loc;
    }

    /**
     * @return the string that represents the location at which the ticket was made
     */
    public String getLocation() {
	return location;
    }

    /**
     * Sets the index of the ticket
     * 
     * @param index
     *            the new index
     */
    public void setIndex(Integer index) {
	this.index = index;
    }
}
