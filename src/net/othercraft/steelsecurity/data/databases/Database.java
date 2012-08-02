package net.othercraft.steelsecurity.data.databases;

public class Database {
    
    private final DatabaseType type;
    
    public Database(DatabaseType type){
	this.type = type;
    }
    public DatabaseType getType() {
	return type;
    }
    //TODO get this to work...

}
