package net.othercraft.steelsecurity;

import java.io.IOException;
import java.net.ServerSocket;

import javax.xml.bind.Binder;;

public class Web extends Thread{

    Binder binder;
    private ServerSocket serverSocket;
    
    public void test(){
	try {
	    serverSocket = new ServerSocket(4444);
	} 
	catch (IOException e) {
	    System.out.println("Could not listen on port: 4444");
	    System.exit(-1);
	}
    }
    
}
