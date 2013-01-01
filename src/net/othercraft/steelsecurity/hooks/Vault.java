package net.othercraft.steelsecurity.hooks;

import org.bukkit.plugin.java.JavaPlugin;

import net.othercraft.steelsecurity.SteelSecurity;

public class Vault extends Hook {

    public Vault(JavaPlugin instance) {
	super(instance);
    }

    @Override
    public void run() {
	if (plugin.getServer().getPluginManager().getPlugin("Vault") != null) {
	    SteelSecurity.vaultEnabled = true;
	} else {
	    SteelSecurity.vaultEnabled = false;
	}

    }
}
