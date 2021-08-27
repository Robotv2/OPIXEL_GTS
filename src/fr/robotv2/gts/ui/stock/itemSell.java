package fr.robotv2.gts.ui.stock;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.robotv2.gts.gts;
import fr.robotv2.gts.util;
import fr.robotv2.gts.object.sellType;
import fr.robotv2.gts.sign.signUtil;
import fr.robotv2.gts.ui.gui;
import fr.robotv2.gts.ui.holders;
import fr.robotv2.gts.ui.itemBuilder;

public class itemSell implements gui {

	private ItemStack black_green_glass;
	private ItemStack green_glass;
	private ItemStack finish;
	
	public Double DEFAULT = 500.0;
	
	@Override
	public String getName(Player player) {
		return util.colorize("&7Création de vente... (OBJET)");
	}

	@Override
	public int getSize() {
		return 45;
	}

	@Override
	public InventoryHolder getHolder() {
		return new holders.SELL_ITEM();
	}

	@Override
	public void contents(Player player, Inventory inv) {
		itemBuilder.setupEmptySlots(inv, getSize());
		
		inv.setItem(3, getBlackGreenGlass());
		inv.setItem(4, getBlackGreenGlass());
		inv.setItem(5, getBlackGreenGlass());
		inv.setItem(12, getBlackGreenGlass());
		inv.setItem(14, getBlackGreenGlass());
		inv.setItem(21, getBlackGreenGlass());
		inv.setItem(22, getBlackGreenGlass());
		inv.setItem(23, getBlackGreenGlass());
		
		inv.setItem(10, getGreenGlass());
		inv.setItem(11, getGreenGlass());
		inv.setItem(15, getGreenGlass());
		inv.setItem(16, getGreenGlass());
		
		boolean has = signUtil.sellingItem.containsKey(player);
		ItemStack what = (has ? signUtil.sellingItem.get(player) : new ItemStack(Material.BARRIER));
		inv.setItem(13, what);
		
		actualizePriceItem(player, inv);
		
		inv.setItem(36, itemBuilder.getClose());
		inv.setItem(44, getFinishItem());
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		if(slot > 44 && current.getType() != Material.AIR) {
			inv.setItem(13, current);
			signUtil.sellingItem.put(player, current);
			return;
		}
		
		switch(slot) {
		case 36:
			gts.getManager().open(player, chooseMenu.class);
			break;
		case 44:
			if(signUtil.sellingItem.get(player) == null) {
				util.sendMessage(player, "&cVous n'avez mis aucun item en vente.");
				return;
			}
			Double price = (signUtil.pricePokemon.containsKey(player) ? signUtil.pricePokemon.get(player) : DEFAULT);
			util.createItemSell(player, price, signUtil.sellingItem.get(player));
			
			signUtil.sellingItem.remove(player);			
			signUtil.priceItem.remove(player);
			
			actualizePriceItem(player, inv);
			inv.setItem(13, new ItemStack(Material.BARRIER));
			break;
		case 40:
			signUtil.type.put(player, sellType.ITEM);
			signUtil.createAndOpen(player);
		}
	}
	
	public ItemStack getBlackGreenGlass() {
		if(black_green_glass == null) {
	        ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
	        ItemMeta decometa = deco.getItemMeta();
	        decometa.setDisplayName("§8");
	        deco.setItemMeta(decometa);
	        black_green_glass = deco;
		}
        return black_green_glass;
	}
	
	public ItemStack getGreenGlass() {
		if(green_glass == null) {
	        ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
	        ItemMeta decometa = deco.getItemMeta();
	        decometa.setDisplayName("§8");
	        deco.setItemMeta(decometa);
	        green_glass = deco;
		}
        return green_glass;
	}
	
	public ItemStack getFinishItem() {
		if(finish == null) {
	        ItemStack deco = new ItemStack(Material.CONCRETE, 1, (short) 5);
	        ItemMeta decometa = deco.getItemMeta();
	        decometa.setDisplayName("§aCliquez ici pour lancer la vente");
	        deco.setItemMeta(decometa);
	        finish = deco;
		}
        return finish;
	}
	
	public void actualizePriceItem(Player player, Inventory inv) {
		ItemStack it = itemBuilder.getHead(40476);
		ItemMeta im = it.getItemMeta();
		double result = (signUtil.priceItem.containsKey(player) ? signUtil.priceItem.get(player) : DEFAULT);
		im.setDisplayName("§7Prix actuellement: §e" + String.valueOf(result) + "$");
		it.setItemMeta(im);
		
		inv.setItem(40, it);
	}
}
