package net.othercraft.steelsecurity.commands;

import net.othercraft.steelsecurity.Main;
import net.othercraft.steelsecurity.listeners.SpectateManager;

public class Vanish {
	
	Main plugin;
	
	SpectateManager spm;

	public Vanish(Object object, Main instance, SpectateManager specman) {
		plugin = instance;
		spm = specman;
	}

}
