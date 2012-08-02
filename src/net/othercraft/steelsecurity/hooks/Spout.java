package net.othercraft.steelsecurity.hooks;

import net.othercraft.steelsecurity.SteelSecurity;

public class Spout extends Hook {

    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().getPlugin("Spout") != null) {
	    SteelSecurity.spoutEnabled = true;
	} else {
	    SteelSecurity.spoutEnabled = false;
	}

    }
}
