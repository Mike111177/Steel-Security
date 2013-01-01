package net.othercraft.steelsecurity.hooks;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Hook implements Runnable {
    
    public static JavaPlugin plugin;
    public Hook(JavaPlugin instance){
	plugin = instance;
    }
    @Override
    public abstract void run();
}
