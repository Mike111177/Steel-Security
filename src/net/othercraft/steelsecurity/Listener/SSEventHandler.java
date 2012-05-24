package net.othercraft.steelsecurity.Listener;

import java.util.HashSet;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class SSEventHandler{

	protected static SSEventHandler el;
	private final HashSet<EventListener> listenerHandlers = new HashSet<EventListener>();

	public SSEventHandler(){
		el = this;
		for(EventPriority priority : EventPriority.values()){
			listenerHandlers.add(new EventListener(priority));
		}
	}
	
	public void execute(EventPriority priority, Event event) {
		if(event instanceof PlayerMoveEvent){//debug
			System.out.print("Player move event registered!");//debug
			System.out.print("Event priority: "+priority.name());//debug
		}//debug
		if(priority == EventPriority.LOWEST) lowest(event);
		if(priority == EventPriority.LOW) low(event);
		if(priority == EventPriority.NORMAL) normal(event);
		if(priority == EventPriority.HIGH) high(event);
		if(priority == EventPriority.HIGHEST) highest(event);
		if(priority == EventPriority.MONITOR) monitor(event);
	}

	public void lowest(Event event){
	}
	public void low(Event event){
		
	}
	public void normal(Event event){
		
	}
	public void high(Event event){
		
	}
	public void highest(Event event){
		
	}
	public void monitor(Event event){
		
	}
}
