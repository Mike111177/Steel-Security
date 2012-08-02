package net.othercraft.steelsecurity.hooks;

import net.othercraft.steelsecurity.SteelSecurity;

public class Vault extends Hook {

    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
	    SteelSecurity.vaultEnabled = true;
	} else {
	    SteelSecurity.vaultEnabled = false;
	}

    }
}
