package fr.robotv2.gts.ui.stock;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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

public class navMenu implements gui {

	private ItemStack white_glass;
	
	private ItemStack arrow_left;
	private ItemStack arrow_right;
	
	@Override
	public String getName(Player player) {
		return util.colorize("&8> &bNaviguateur");
	}

	@Override
	public int getSize() {
		return 54;
	}

	@Override
	public InventoryHolder getHolder() {
		return new holders.NAV_MENU();
	}

	@Override
	public void contents(Player player, Inventory inv) {
		if(!util.page.containsKey(player)) util.page.put(player, 1);
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
		inv.setItem(47, itemBuilder.getEmptySlots());
		inv.setItem(51, itemBuilder.getEmptySlots());
		
		inv.setItem(45, itemBuilder.getClose());
	
		inv.setItem(8, getWhiteGlass());
		inv.setItem(17, getWhiteGlass());
		inv.setItem(26, getWhiteGlass());
		inv.setItem(35, getWhiteGlass());
		inv.setItem(44, getWhiteGlass());
		inv.setItem(46, getWhiteGlass());
		inv.setItem(52, getWhiteGlass());	
		inv.setItem(53, getWhiteGlass());
		
		inv.setItem(48, getArrowLeft());
		inv.setItem(49, getPage(player));
		inv.setItem(50, getArrowRight());
		
		int count = 0;
		List<Sell> list = util.unformat(util.getSells(), util.page.get(player) - 1);
		for(Sell sell : list) {
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
		}
		
		if(current.getType() == Material.STAINED_GLASS_PANE || current.getType() == Material.AIR || current.getType() == Material.SKULL) return;
		this.buy(player, current);
		this.contents(player, inv);
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
	
	public ItemStack getArrowLeft() {
		if(arrow_left == null) {
	        ItemStack deco = itemBuilder.getHead(7827);
	        ItemMeta decometa = deco.getItemMeta();
	        decometa.setDisplayName("§8Pour revenir à la page précédente.");
	        deco.setItemMeta(decometa);
	        arrow_left = deco;
		}
        return arrow_left;
	}
	
	public ItemStack getArrowRight() {
		if(arrow_right == null) {
	        ItemStack deco = itemBuilder.getHead(7826);
	        ItemMeta decometa = deco.getItemMeta();
	        decometa.setDisplayName("§8Pour aller à la page suivante.");
	        deco.setItemMeta(decometa);
	        arrow_right = deco;
		}
        return arrow_right;
	}
	
	public ItemStack getPage(Player player) {
		int pageInt = util.page.get(player);
		
		ItemStack deco = new ItemStack(Material.PAPER);
	    ItemMeta decometa = deco.getItemMeta();
	    decometa.setDisplayName("§8Page " + String.valueOf(pageInt));
	    deco.setItemMeta(decometa);
	    return deco;
	}
	
	public void buy(Player player, ItemStack current) {
		UUID uuid = util.getUUID(current);
		Sell sell = util.getSell(uuid);
		
		if(sell.getItem() == null) {
			util.sendMessage(player, "&cCette objet n'est plus disponible.");
			gts.getManager().open(player, navMenu.class);
			return;
		}
		
		if(gts.getEco().getBalance(player) >= sell.getPrice()) {
			
			OfflinePlayer owner = sell.getOwner();
			gts.getEco().depositPlayer(owner, sell.getPrice());
			if(owner.getPlayer() != null) {
				util.sendMessage(owner.getPlayer(), "&7Un joueur vient de vous acheter un objet / pokémon d'une valeur de &e" + String.valueOf(sell.getPrice()));
			}	
			gts.getEco().withdrawPlayer(player, sell.getPrice());
			
			switch(sell.getType()) {
				case ITEM:		
					player.getInventory().addItem(sell.getItem());
					util.sendMessage(player, "&7Vous venez d'acheter un item.");
					break;
				case POKEMON:
					Pokemon poke = sell.getPokemon();
					util.givePokemon(player, poke);
					util.sendMessage(player, "&7Vous venez d'acheter un pokémon.");
					break;
			}
			sell.remove();
			gts.getManager().open(player, navMenu.class);
		} else {
			util.sendMessage(player, "&cVous n'avez pas assez d'argent pour acheter ce pokémon / item.");
			player.closeInventory();
		}
		
		
	}

}
