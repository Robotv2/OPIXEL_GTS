package fr.robotv2.gts.ui.stock;

import java.util.Arrays;

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

public class mainMenu implements gui {

	@Override
	public String getName(Player player) {
		return util.colorize("&c&lGTS &f>>");
	}

	@Override
	public int getSize() {
		return 27;
	}

	@Override
	public InventoryHolder getHolder() {
		return new holders.MAIN_MENU();
	}

	@Override
	public void contents(Player player, Inventory inv) {
		itemBuilder.setupEmptySlots(inv, getSize());
		
		inv.setItem(10, itemBuilder.getAir());
		inv.setItem(12, itemBuilder.getAir());
		inv.setItem(14, itemBuilder.getAir());
		inv.setItem(16, itemBuilder.getAir());
		
		inv.setItem(11, getMainGtsItem());
		inv.setItem(13, getNavItem());
		inv.setItem(15, getStorageItem());
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		switch(slot) {
		case 11:
			gts.getManager().open(player, chooseMenu.class);
			break;
		case 13:
			gts.getManager().open(player, navMenu.class);
			break;
		case 15:
			gts.getManager().open(player, stockOwn.class);
			break;
		}
	}
	
	public ItemStack getMainGtsItem() {
		ItemStack item = itemBuilder.getHead(23542);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§8> §eMarché");
		im.setLore(Arrays.asList("", "§8| §dIci vous pourrez revendre vos items", "§8| §dou même vos pokémons !", ""));
		
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack getNavItem() {
		ItemStack item = itemBuilder.getHead(32442);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§8> §bNaviguateur");
		im.setLore(Arrays.asList("", "§8| §dIci vous pourrez voir les items", "§8| §det Pokémon en vente !", ""));
		
		item.setItemMeta(im);	
		return item;
	}
	
	public ItemStack getStorageItem() {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta im = item.getItemMeta();
		
		im.setDisplayName("§8> §7Stockage");
		im.setLore(Arrays.asList("", "§8| §dIci vous pourrez récupérer vos offres qui", "§8| §dont expirés ou qui ont été acheté !", ""));
		
		item.setItemMeta(im);
		return item;
	}
}
