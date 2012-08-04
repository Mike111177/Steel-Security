package net.othercraft.steelsecurity.hooks;

import net.othercraft.steelsecurity.SteelSecurity;

public class WorldEdit extends Hook{
    public WorldEdit(SteelSecurity instance) {
	super(instance);
    }

    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().getPlugin("WorldEdit") != null) {
	    SteelSecurity.weEnabled = true;
	} else {
	    SteelSecurity.weEnabled = false;
	}

    }
}
