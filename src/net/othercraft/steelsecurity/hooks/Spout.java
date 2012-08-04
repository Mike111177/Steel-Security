package net.othercraft.steelsecurity.hooks;

import net.othercraft.steelsecurity.SteelSecurity;

public class Spout extends Hook {

    public Spout(SteelSecurity instance) {
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
