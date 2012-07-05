package net.othercraft.steelsecurity.data.databases;

import java.util.PriorityQueue;


public class DatabaseManager {
    
    private Boolean online;
    
    private Boolean running;
    
    private PriorityQueue<QueueSegment> queue;
    
    public void addQueue(QueueSegment seg){
	queue.add(seg);
    }
    private void queRun(){
	if (queue.size()==0){
	    
	}	
    }
}