package net.othercraft.steelsecurity.data.databases;

import java.util.PriorityQueue;


public class DatabaseManager {
    
    private PriorityQueue<QueueSegment> queue = new PriorityQueue<QueueSegment>();
    
    public void addQueue(QueueSegment seg){
	queue.add(seg);
    }
}