package fr.robotv2.gts.ui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface gui {
	public String getName(Player player);
	public int getSize();
	public InventoryHolder getHolder();
	public void contents(Player player, Inventory inv);
	public void onClick(Player player, Inventory inv, ItemStack current, int slot);
}
