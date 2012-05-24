package net.othercraft.steelsecurity.Listener;

import net.othercraft.steelsecurity.Main;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public class EventListener implements Listener,EventExecutor{

	private final EventPriority priority;

	public EventListener(EventPriority priority){
		this.priority = priority;
		Bukkit.getPluginManager().registerEvent(Event.class, this, priority, this, Main.instance, false);//CorrieKay: this line is throwing some sort of exception. We may not be able to use events like this, but im damn sure gonna try.
	}

	@Override
	public void execute(Listener listener, Event event) throws EventException {
		SSEventHandler.el.execute(priority,event);
	}

}
