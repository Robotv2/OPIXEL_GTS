package fr.robotv2.gts.ui.stock;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.robotv2.gts.gts;
import fr.robotv2.gts.util;
import fr.robotv2.gts.ui.gui;
import fr.robotv2.gts.ui.holders;
import fr.robotv2.gts.ui.itemBuilder;

public class chooseMenu implements gui{

	@Override
	public String getName(Player player) {
		return util.colorize("&fObjet &7ou &fPokemon &7?");
	}

	@Override
	public int getSize() {
		return 36;
	}

	@Override
	public InventoryHolder getHolder() {
		return new holders.CHOOSE_MENU();
	}

	@Override
	public void contents(Player player, Inventory inv) {
		itemBuilder.setupEmptySlots(inv, getSize());
		
		inv.setItem(10, itemBuilder.getAir());
		inv.setItem(12, itemBuilder.getAir());
		inv.setItem(13, itemBuilder.getAir());
		inv.setItem(14, itemBuilder.getAir());
		inv.setItem(16, itemBuilder.getAir());
		
		inv.setItem(28, itemBuilder.getAir());
		inv.setItem(29, itemBuilder.getAir());
		inv.setItem(30, itemBuilder.getAir());
		inv.setItem(32, itemBuilder.getAir());
		inv.setItem(33, itemBuilder.getAir());
		inv.setItem(34, itemBuilder.getAir());
		
		inv.setItem(31, itemBuilder.getClose());
		
		inv.setItem(11, getItem());
		inv.setItem(15, getPokemon());
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		switch(slot) {
		case 11:
			gts.getManager().open(player, itemSell.class);
			break;
		case 15:
			gts.getManager().open(player, pokeSell.class);
			break;
		case 31:
			gts.getManager().open(player, mainMenu.class);
			break;
		}
	}
	
	public ItemStack getItem() {
		ItemStack item = new ItemStack(Material.DIAMOND);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(util.colorize("&8> &7Pour vendre un objet"));
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack getPokemon() {
		ItemStack item = new ItemStack(Material.valueOf("PIXELMON_GS_BALL"));
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName(util.colorize("&8> &7Pour vendre un pokemon"));
		item.setItemMeta(im);
		return item;
	}
 
}
