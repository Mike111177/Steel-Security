package net.othercraft.steelsecurity.hooks;

import org.bukkit.plugin.java.JavaPlugin;

import net.othercraft.steelsecurity.SteelSecurity;

public class Spout extends Hook {

    public Spout(JavaPlugin instance) {
	super(instance);
    }

    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().isPluginEnabled("Spout")) {
	    SteelSecurity.spoutEnabled = true;
	} else {
	    SteelSecurity.spoutEnabled = false;
	}

    }
}
