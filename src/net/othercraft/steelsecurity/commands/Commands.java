package net.othercraft.steelsecurity.commands;

import net.othercraft.steelsecurity.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	public Commands(Main main) {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2,
			String[] split) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (cmd.getName().equalsIgnoreCase("sts")) {
			Sts s = new Sts();
			s.command(sender, split, player);
			return true;
		}
		return false;
	}

}
