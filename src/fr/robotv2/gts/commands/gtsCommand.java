package fr.robotv2.gts.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.robotv2.gts.gts;
import fr.robotv2.gts.ui.stock.mainMenu;

public class gtsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player) sender;
		gts.getManager().open(player, mainMenu.class);
		return true;
	}

}
