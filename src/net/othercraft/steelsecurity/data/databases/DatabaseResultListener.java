package net.othercraft.steelsecurity.data.databases;

import java.sql.ResultSet;
import java.util.HashMap;

import net.othercraft.steelsecurity.utils.SSCmdExe;

public abstract class DatabaseResultListener extends SSCmdExe {
    
    private Boolean enabled;
    
    private long nextID = 0;
    private Database database;
    private HashMap<Long, ResultAction> queue = new HashMap<Long, ResultAction>();
    public DatabaseResultListener(String name, Boolean listener, Database database, Boolean databaseEnabled) {
	super(name, listener);
	this.database = database;
	this.setEnabled(databaseEnabled);
    }
    public void result(ResultSet result, long id) {
	ResultAction action = queue.get(id);
	queue.remove(id);
	action.result = result;
	action.run();
    }
    public void addToQueue(String query, ResultAction runnable) {
	nextID++;
	QueueSegment segment = new QueueSegment(nextID, this, query);
	queue.put(segment.getId(), runnable);
	database.addToQueue(segment);
    }
    public Boolean getEnabled() {
	return enabled;
    }
    public void setEnabled(Boolean enabled) {
	this.enabled = enabled;
    }
}
class QueueSegment {
    
    private final long ID;
    private final DatabaseResultListener resultListener;
    private final String query;
    
    public QueueSegment(long id, DatabaseResultListener resultListener, String query){
	this.ID = id;
	this.resultListener = resultListener;
	this.query = query;
    }

    public long getId() {
	return ID;
    }

    public DatabaseResultListener getResultListener() {
	return resultListener;
    }

    public String getQuery() {
	return query;
    }
}