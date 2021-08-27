package fr.robotv2.gts.ui.stock;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

import fr.robotv2.gts.gts;
import fr.robotv2.gts.util;
import fr.robotv2.gts.object.sellType;
import fr.robotv2.gts.sign.signUtil;
import fr.robotv2.gts.ui.gui;
import fr.robotv2.gts.ui.holders;
import fr.robotv2.gts.ui.itemBuilder;

public class pokeSell implements gui {

	private ItemStack black_green_glass;
	private ItemStack green_glass;
	private ItemStack finish;
	
	private ItemStack slot1;
	private ItemStack slot2;
	private ItemStack slot3;
	private ItemStack slot4;
	private ItemStack slot5;
	private ItemStack slot6;
	
	public HashMap<Player, Integer> pokeSlot = new HashMap<>();
	
	public Double DEFAULT = 500.0;
	
	@Override
	public String getName(Player player) {
		return util.colorize("&7Création de vente... (POKEMON)");
	}

	@Override
	public int getSize() {
		return 45;
	}

	@Override
	public InventoryHolder getHolder() {
		return new holders.SELL_POKEMON();
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
		
		boolean has = signUtil.sellingPokemon.containsKey(player);
		ItemStack what = (has ? this.pokeStack(signUtil.sellingPokemon.get(player)) : new ItemStack(Material.BARRIER));
		inv.setItem(13, what);
		
		inv.setItem(36, itemBuilder.getClose());
		inv.setItem(44, getFinishItem());
		
		inv.setItem(28, get1());
		inv.setItem(29, get2());
		inv.setItem(30, get3());
		
		actualizePriceItem(player, inv);
		
		inv.setItem(32, get4());
		inv.setItem(33, get5());
		inv.setItem(34, get6());
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		ItemStack it;
		Pokemon poke;
		switch(slot) {
		case 28:
			poke = getPokemonFromSlot(player, 1);
			it = this.pokeStack(poke);
			
			if(it == null) return;
			
			inv.setItem(13, it);
			signUtil.sellingPokemon.put(player, poke);
			
			pokeSlot.remove(player);
			pokeSlot.put(player, 0);
			break;
		case 29:
			poke = getPokemonFromSlot(player, 2);
			it = this.pokeStack(getPokemonFromSlot(player, 2));
			
			if(it == null) return;
			
			inv.setItem(13, it);
			signUtil.sellingPokemon.put(player, poke);
			
			pokeSlot.remove(player);
			pokeSlot.put(player, 1);
			break;
		case 30:
			poke = getPokemonFromSlot(player, 3);
			it = this.pokeStack(getPokemonFromSlot(player, 3));
			
			if(it == null) return;
			
			inv.setItem(13, it);
			signUtil.sellingPokemon.put(player, poke);
			
			pokeSlot.remove(player);
			pokeSlot.put(player, 2);
			break;
		case 32:
			poke = getPokemonFromSlot(player, 4);
			it = this.pokeStack(getPokemonFromSlot(player, 4));
			
			if(it == null) return;
			
			inv.setItem(13, it);
			signUtil.sellingPokemon.put(player, poke);

			pokeSlot.remove(player);
			pokeSlot.put(player, 3);
			break;
		case 33:
			poke = getPokemonFromSlot(player, 5);
			it = this.pokeStack(getPokemonFromSlot(player, 5));
			
			if(it == null) return;
			
			inv.setItem(13, it);
			signUtil.sellingPokemon.put(player, poke);
			
			pokeSlot.remove(player);
			pokeSlot.put(player, 4);
			break;
		case 34:
			poke = getPokemonFromSlot(player, 6);
			it = this.pokeStack(getPokemonFromSlot(player, 6));
			
			if(it == null) return;
			
			inv.setItem(13, it);
			signUtil.sellingPokemon.put(player, poke);
			
			pokeSlot.remove(player);
			pokeSlot.put(player, 5);
			break;
			
		case 44:
			if(signUtil.sellingPokemon.get(player) == null) {
				util.sendMessage(player, "&cVous n'avez mis aucun item en vente.");
				return;
			}
			if(!signUtil.sellingPokemon.get(player).getHeldItemAsItemHeld().toString().contains("NoItem")) {
				util.sendMessage(player, "&cVous ne pouvez pas vendre un pokemon qui tient un objet.");
				return;
			}
			if(util.getPlayerStorage(player).getTeam().size() == 1) {
				util.sendMessage(player, "&cVous devez toujours avoir au moins un pokémon dans votre inventaire.");
				return;
			}
			
			Double price = (signUtil.pricePokemon.containsKey(player) ? signUtil.pricePokemon.get(player) : DEFAULT);
			util.createPokemonSell(player, price, signUtil.sellingPokemon.get(player));
			util.takePokemon(player, pokeSlot.get(player));
			
			signUtil.sellingPokemon.remove(player);			
			signUtil.pricePokemon.remove(player);
			
			inv.setItem(13, new ItemStack(Material.BARRIER));
			break;
		case 36:
			gts.getManager().open(player, chooseMenu.class);
			break;
		case 40:
			signUtil.type.put(player, sellType.POKEMON);
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
	
	public ItemStack get1() {
		if(slot1 == null) {
			ItemStack item = itemBuilder.getHead(8813);
			ItemMeta im = item.getItemMeta();
			
			im.setDisplayName("§8> §eVendre le pokémon slot 1");
			
			item.setItemMeta(im);
			slot1 = item;
		}
		return slot1;
	}
	
	public ItemStack get2() {
		if(slot2 == null) {
			ItemStack item = itemBuilder.getHead(8812);
			ItemMeta im = item.getItemMeta();
			
			im.setDisplayName("§8> §eVendre le pokémon slot 2");
			
			item.setItemMeta(im);
			slot2 = item;
		}
		return slot2;
	}
	
	public ItemStack get3() {
		if(slot3 == null) {
			ItemStack item = itemBuilder.getHead(8811);
			ItemMeta im = item.getItemMeta();
			
			im.setDisplayName("§8> §eVendre le pokémon slot 3");
			
			item.setItemMeta(im);
			slot3 = item;
		}
		return slot3;
	}
	
	public ItemStack get4() {
		if(slot4 == null) {
			ItemStack item = itemBuilder.getHead(8810);
			ItemMeta im = item.getItemMeta();
			
			im.setDisplayName("§8> §eVendre le pokémon slot 4");
			
			item.setItemMeta(im);
			slot4 = item;
		}
		return slot4;
	}
	
	public ItemStack get5() {
		if(slot5 == null) {
			ItemStack item = itemBuilder.getHead(8809);
			ItemMeta im = item.getItemMeta();
			
			im.setDisplayName("§8> §eVendre le pokémon slot 5");
			
			item.setItemMeta(im);
			slot5 = item;
		}
		return slot5;
	}
	
	public ItemStack get6() {
		if(slot6 == null) {
			ItemStack item = itemBuilder.getHead(8808);
			ItemMeta im = item.getItemMeta();
			
			im.setDisplayName("§8> §eVendre le pokémon slot 6");
			
			item.setItemMeta(im);
			slot6 = item;
		}
		return slot6;
	}
	
	public void actualizePriceItem(Player player, Inventory inv) {
		ItemStack it = itemBuilder.getHead(40476);
		ItemMeta im = it.getItemMeta();
		double result = (signUtil.pricePokemon.containsKey(player) ? signUtil.pricePokemon.get(player) : DEFAULT);
		im.setDisplayName("§7Prix actuellement: §e" + String.valueOf(result) + "$");
		it.setItemMeta(im);
		
		inv.setItem(40, it);
	}
	
	public Pokemon getPokemonFromSlot(Player player, int slot) {
    	PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player.getUniqueId());
    	Pokemon poke = storage.get(slot - 1);
    	return poke;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack pokeStack(Pokemon poke) {
		if(poke == null) return null;
    	ItemStack it = new ItemStack(Material.valueOf("PIXELMON_POKE_BALL"));
    	ItemMeta im = it.getItemMeta();
    	EVStore ev = poke.getEVs();
    	String evs = "§e" + ev.attack + "§8/§e" + ev.defence + "§8/§e" + ev.specialAttack + "§8/§e" + ev.specialDefence + "§8/§e" + ev.hp + "§8/§e" + ev.speed;
    	
    	IVStore iv = poke.getIVs();
    	String ivs = "§e" + iv.attack + "§8/§e" + iv.defence + "§8/§e" + iv.specialAttack + "§8/§e" + iv.specialDefence + "§8/§e" + iv.hp + "§8/§e" + iv.speed;
    	
    	String isShiny = (poke.isShiny() ? "§e§LSHINY" : "");
    	im.setDisplayName("§9" + poke.getSpecies().getLocalizedName() + " §8| §bLvl " + String.valueOf(poke.getLevel()) + isShiny);
    	im.setLore(Arrays.asList(
    			"",
    			"§e§lPOKEMON",
      			"  §7Genre: §e" + poke.getGender().toString().toLowerCase(),
    			"  §7Nature: §e" + poke.getNature().toString().toLowerCase(),
    			"  §7Talent: §e" + poke.getAbilityName(),
    			"",
    			"§e§lSTATS",
    			" §7EVs: " + evs,
    			" §7IVs: " + ivs,
    			""));
    	it.setItemMeta(im);
    	return it;
	}

}
