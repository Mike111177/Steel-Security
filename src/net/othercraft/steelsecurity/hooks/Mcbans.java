package net.othercraft.steelsecurity.hooks;

import net.othercraft.steelsecurity.SteelSecurity;

public class Mcbans extends Hook {

    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().getPlugin("mcbans") != null) {
	    SteelSecurity.mcBansEnabled = true;
	} else {
	    SteelSecurity.mcBansEnabled = false;
	}

    }
}
