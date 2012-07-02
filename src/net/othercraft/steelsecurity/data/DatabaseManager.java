package net.othercraft.steelsecurity.data;

import lib.PatPeter.SQLibrary.MySQL;

public class DatabaseManager {
    
    private Boolean online;
    
    private MySQL sql  = new MySQL(null, "pl_","lego.othercraft.net","3306","othercraftweb_test", "othercraftweb", "B4P3osFl6J");  
    
    public void sqlCheck(){
	sql.open();
	online = sql.checkConnection();
	System.out.println(online);
    }

}
