package fr.robotv2.gts.ui.stock;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

import fr.robotv2.gts.gts;
import fr.robotv2.gts.util;
import fr.robotv2.gts.object.Sell;
import fr.robotv2.gts.ui.gui;
import fr.robotv2.gts.ui.holders;
import fr.robotv2.gts.ui.itemBuilder;

public class stockOwn implements gui {

	public ItemStack white_glass;
	public ItemStack green_glass;
	
	@Override
	public String getName(Player player) {
		return util.colorize("&8> &fVos items.");
	}

	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public InventoryHolder getHolder() {
		return new holders.STOCK_OWN();
	}

	@Override
	public void contents(Player player, Inventory inv) {
		inv.setItem(7, itemBuilder.getEmptySlots());
		inv.setItem(16, itemBuilder.getEmptySlots());
		inv.setItem(25, itemBuilder.getEmptySlots());
		inv.setItem(34, itemBuilder.getEmptySlots());
		
		inv.setItem(36, itemBuilder.getEmptySlots());
		inv.setItem(37, itemBuilder.getEmptySlots());
		inv.setItem(38, itemBuilder.getEmptySlots());
		inv.setItem(39, itemBuilder.getEmptySlots());
		inv.setItem(40, itemBuilder.getEmptySlots());
		inv.setItem(41, itemBuilder.getEmptySlots());
		inv.setItem(42, itemBuilder.getEmptySlots());
		inv.setItem(43, itemBuilder.getEmptySlots());
		
		inv.setItem(45, itemBuilder.getClose());
	
		inv.setItem(8, getWhiteGlass());
		inv.setItem(17, getWhiteGlass());
		inv.setItem(26, getWhiteGlass());
		inv.setItem(35, getWhiteGlass());
		inv.setItem(44, getWhiteGlass());
		inv.setItem(46, getWhiteGlass());
		inv.setItem(47, getWhiteGlass());
		inv.setItem(48, getWhiteGlass());
		inv.setItem(49, getWhiteGlass());
		inv.setItem(50, getWhiteGlass());
		inv.setItem(51, getWhiteGlass());
		inv.setItem(52, getWhiteGlass());
		
		inv.setItem(53, getGreenGlass());
		
		
		int count = 0;
		for(Sell sell : util.getSells(player)) {
			switch(count) {
			case 7:
			case 16:
			case 25:
			case 34:
				count = count + 2;
			default:
				inv.setItem(count, itemBuilder.getStack(sell));
				count++;
			}

		}
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		switch(slot) {
		case 45:
			gts.getManager().open(player, mainMenu.class);
			return;
			
		case 53:
			claimAll(player);
			return;
		}
		
		if(current.getType() == Material.STAINED_GLASS_PANE || current.getType() == Material.AIR) return;
		this.claim(player, current);
		gts.getManager().open(player, stockOwn.class);
	}
	
	public ItemStack getWhiteGlass() {
		if(white_glass == null) {
		        ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
		        ItemMeta decometa = deco.getItemMeta();
		        decometa.setDisplayName("§8");
		        deco.setItemMeta(decometa);
		        white_glass = deco;
			}
	        return white_glass;
		}
	
	public ItemStack getGreenGlass() {
		if(green_glass == null) {
	        ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
	        ItemMeta decometa = deco.getItemMeta();
	        decometa.setDisplayName("§8");
	        deco.setItemMeta(decometa);
	        green_glass = deco;
		}
        return green_glass;
	}
	
	public void claim(Player player, ItemStack current) {
		UUID uuid = util.getUUID(current);
		Sell sell = util.getSell(uuid);
		
		switch(sell.getType()) {
		case ITEM:
	 		gts.getManager().open(player, stockOwn.class);
			player.getInventory().addItem(sell.getItem());	
			util.sendMessage(player, "&7Vous venez de récupérer votre item.");
			break;
		case POKEMON:
			Pokemon poke = sell.getPokemon();
			util.givePokemon(player, poke);
			util.sendMessage(player, "&7Vous venez de récupérer votre pokémon.");
			break;
		}
		sell.remove();
	}
	
	public void claimAll(Player player) {
		for(Sell sell : util.getSells(player)) {
			switch(sell.getType()) {
			case ITEM:
				player.getInventory().addItem(sell.getItem());	
				break;
			case POKEMON:
				Pokemon poke = sell.getPokemon();
				util.givePokemon(player, poke);
				break;
			}
			sell.remove();
		}
		gts.getManager().open(player, stockOwn.class);
	}

}
