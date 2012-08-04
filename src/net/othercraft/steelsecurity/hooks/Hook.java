package net.othercraft.steelsecurity.hooks;

import net.othercraft.steelsecurity.SteelSecurity;

public class Hook implements Runnable {
    
    public static SteelSecurity plugin;
    public Hook(SteelSecurity instance){
	plugin = instance;
    }
    @Override
    public void run() {
	//Use this to operate the hook
	
    }
}
