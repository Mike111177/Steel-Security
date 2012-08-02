package net.othercraft.steelsecurity.hooks;

import net.othercraft.steelsecurity.SteelSecurity;

public class WorldEdit extends Hook{
    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().getPlugin("WorldEdit") != null) {
	    SteelSecurity.weEnabled = true;
	} else {
	    SteelSecurity.weEnabled = false;
	}

    }
}
