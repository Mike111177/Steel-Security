package net.othercraft.steelsecurity.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.othercraft.steelsecurity.Main;

public class Commands implements CommandExecutor{

	public Commands(Main main) {
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		if (cmd.getName() == "sts"){
			sts s = new sts();
			s.command(sender);
		}
		return false;
	}

}
