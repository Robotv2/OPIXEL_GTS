package fr.robotv2.gts.ui;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;

import fr.robotv2.gts.object.Sell;
import fr.robotv2.gts.object.sellType;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.enums.CategoryEnum;

public class itemBuilder {
	
	private static ItemStack empty;
	private static ItemStack close;
	private static ItemStack air;
	
	public static HeadDatabaseAPI api = new HeadDatabaseAPI();
	
	public static void setupEmptySlots(Inventory inv, int row) {	
		for(int i=0; i<=row-1; i++) {
			inv.setItem(i, getEmptySlots());
		}
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getPlayerHead(String player) {
		String headID = api.addHead(CategoryEnum.HUMANS, player, Bukkit.getOfflinePlayer(player).getUniqueId());
		ItemStack playerHead = api.getItemHead(headID);
		api.removeHead(headID);
		return playerHead;
	}
	
	public static ItemStack getHead(int id) {
		return api.getItemHead(String.valueOf(id));
	}
	
	public static ItemStack getAir() {
        if(air == null) {
            ItemStack item = new ItemStack(Material.AIR);
            air = item;
        }
        return air;
	}
	
    public static ItemStack getEmptySlots() {
        if(empty == null) {
            ItemStack deco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
            ItemMeta decometa = deco.getItemMeta();
            decometa.setDisplayName("§8");
            deco.setItemMeta(decometa);
            empty = deco;
        }
        return empty;
    }
    
    public static ItemStack getClose() {
        if(close == null) {
            ItemStack deco = new ItemStack(Material.BARRIER);
            ItemMeta decometa = deco.getItemMeta();
            decometa.setDisplayName("§cCliquez ici pour revenir en arrière.");
            deco.setItemMeta(decometa);
            close = deco;
        }
        return close;
    }
    
    public static ItemStack getStack(Sell sell) {
    	if(sell.getType() == sellType.POKEMON) return getPokemonStack(sell);
    	ItemStack it = sell.getItem().clone();
    	ItemMeta im = it.getItemMeta();
    	im.setLore(Arrays.asList(
    			"",
    			"§e§lINFORMATIONS",
    			"  §fPrix: §e" + String.valueOf(sell.getPrice()),
    			"  §fPropriétaire: §c" + sell.getOwner().getName(),
    			"  §fType: §d" + sell.getType().toString().toLowerCase(), 
    			"",
    			"§fAjouté le " + getLocalDate(sell.getStart()).toString(),
    			"§8ID: " + sell.getUUID().toString()));
    	it.setItemMeta(im);
    	return it;
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack getPokemonStack(Sell sell) {
    	ItemStack it = new ItemStack(Material.valueOf("PIXELMON_POKE_BALL"));
    	ItemMeta im = it.getItemMeta();
    	Pokemon poke = sell.getPokemon();
    	
    	EVStore ev = poke.getEVs();
    	String evs = "§e" + ev.attack + "§8/§e" + ev.defence + "§8/§e" + ev.specialAttack + "§8/§e" + ev.specialDefence + "§8/§e" + ev.hp + "§8/§e" + ev.speed;
    	
    	IVStore iv = poke.getIVs();
    	String ivs = "§e" + iv.attack + "§8/§e" + iv.defence + "§8/§e" + iv.specialAttack + "§8/§e" + iv.specialDefence + "§8/§e" + iv.hp + "§8/§e" + iv.speed;
    	
    	String isShiny = (poke.isShiny() ? "§e§LSHINY" : "");
    	im.setDisplayName("§9" + poke.getSpecies().getLocalizedName() + " §8| §bLvl " + String.valueOf(poke.getLevel()) + isShiny);
    	im.setLore(Arrays.asList(
    			"",
    			"§e§lINFORMATIONS",
    			"  §fPrix: §e" + String.valueOf(sell.getPrice()),
    			"  §fPropriétaire: §c" + sell.getOwner().getName(),
    			"  §fType: §d" + sell.getType().toString().toLowerCase(), 
    			"",
    			"§e§lPOKEMON",
    			"  §7Genre: §e" + poke.getGender().toString().toLowerCase(),
    			"  §7Nature: §e" + poke.getNature().toString().toLowerCase(),
    			"  §7Talent: §e" + poke.getAbilityName(),
    			"",
    			"§e§lSTATS",
    			" §7EVs: " + evs,
    			" §7IVs: " + ivs,
    			"",
    			"§fAjouté le " + getLocalDate(sell.getStart()).toString(),
    			"§8ID: " + sell.getUUID().toString()));
    	
    	it.setItemMeta(im);
    	return it;
    }
    
    public static LocalDate getLocalDate(Long time) {
    	LocalDate date =
    		    Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    	return date;
    }
}
