package net.othercraft.steelsecurity.hooks;

import org.bukkit.plugin.java.JavaPlugin;

import net.othercraft.steelsecurity.SteelSecurity;

public class Mcbans extends Hook {

    public Mcbans(JavaPlugin instance) {
	super(instance);
    }

    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().getPlugin("mcbans") != null) {
	    SteelSecurity.mcBansEnabled = true;
	} else {
	    SteelSecurity.mcBansEnabled = false;
	}

    }
}
