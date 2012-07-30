package net.othercraft.steelsecurity.hooks;

import net.othercraft.steelsecurity.SteelSecurity;

public class Spout implements Runnable {

    private final SteelSecurity plugin;

    public Spout(final SteelSecurity plugin) {
	this.plugin = plugin;
    }

    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().getPlugin("Spout") != null) {
	    SteelSecurity.spoutEnabled = true;
	} else {
	    SteelSecurity.spoutEnabled = false;
	}

    }
}
