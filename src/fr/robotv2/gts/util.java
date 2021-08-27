package fr.robotv2.gts;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

import fr.robotv2.gts.database.database;
import fr.robotv2.gts.object.Sell;
import fr.robotv2.gts.object.sellType;
import fr.robotv2.gts.ui.timeComparator;

public class util {
	
	public static HashMap<Player, Integer> page = new HashMap<>();

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(util.colorize(message));
	}
	
	public static Sell getSell(UUID itemUUID) {
		return new Sell(itemUUID);
	}
	
	public static List<Sell> getSells() {
		List<Sell> result = new ArrayList<>();
		try {
			for(String uuidStr : database.get().getConfigurationSection("sell.").getKeys(false)) {
				UUID uuid = UUID.fromString(uuidStr);
				result.add(getSell(uuid));
			}
			return result;
		} catch(NullPointerException e) {
			return result;
		}
	}
	
	public static List<Sell> getSells(OfflinePlayer player) {
		List<Sell> result = new ArrayList<>();
		getSells().stream().filter(s -> s.getOwner().equals(player)).forEach(s -> result.add(s));
		return result;
	}
	
	public static void createItemSell(Player player, double price, ItemStack current) {
		if(getTotalSell(player) + 1 > getLimit(player)) {
			util.sendMessage(player, "Vous avez trop de choses en vente.");
			return;
		}
		UUID itemUUID = UUID.randomUUID();
		Long start = Instant.now().toEpochMilli();
		Long end = start + 129600000L;
		
		database.get().set("sell." + itemUUID.toString() + ".owner", player.getUniqueId().toString());
		database.get().set("sell." + itemUUID.toString() + ".price", price);
		database.get().set("sell." + itemUUID.toString() + ".start", start);
		database.get().set("sell." + itemUUID.toString() + ".end", end);
		database.get().set("sell." + itemUUID.toString() + ".type", sellType.ITEM.toString());
		database.get().set("sell." + itemUUID.toString() + ".isSelled", false);
		database.get().set("sell." + itemUUID.toString() + ".shown", true);
		database.get().set("sell." + itemUUID.toString() + ".item", current);
		database.save();
		
		util.sendMessage(player, "&7Vous venez de mettre en ventre &f" + String.valueOf(current.getAmount()) + "&8X &f" + current.getType().toString().toLowerCase() + 
				" &7pour &e" + String.valueOf(price) + "$");
		player.getInventory().removeItem(current);
	}
	
	public static void createPokemonSell(Player player, double price, Pokemon current) {
		if(getTotalSell(player) + 1 > getLimit(player)) {
			util.sendMessage(player, "Vous avez trop de choses en vente.");
			return;
		}
		UUID itemUUID = UUID.randomUUID();
		Long start = Instant.now().toEpochMilli();
		Long end = start + 129600000L;
		
		database.get().set("sell." + itemUUID.toString() + ".owner", player.getUniqueId().toString());
		database.get().set("sell." + itemUUID.toString() + ".price", price);
		database.get().set("sell." + itemUUID.toString() + ".start", start);
		database.get().set("sell." + itemUUID.toString() + ".end", end);
		database.get().set("sell." + itemUUID.toString() + ".type", sellType.POKEMON.toString());
		database.get().set("sell." + itemUUID.toString() + ".isSelled", false);
		database.get().set("sell." + itemUUID.toString() + ".shown", true);
		util.setPokemon(current, itemUUID);
		database.save();
		
		util.sendMessage(player, "&7Vous venez de mettre en ventre &f" + String.valueOf(1) + "&8X &f" + current.getSpecies().getLocalizedName() + 
				" &7pour &e" + String.valueOf(price) + "$");
	}
	
	public static UUID getUUID(ItemStack current) {
		List<String> lore = current.getItemMeta().getLore();
		
		String id = lore.get(lore.size() - 1);
		id = id.replace("ID: ", "");
		id = ChatColor.stripColor(id);
		
		UUID uuid = UUID.fromString(id);
		return uuid;
	}
	
	public static int getLimit(Player player) {
		int result = 7;
		for(PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
			if(!perm.getPermission().startsWith("gts.limit.")) continue;
			
			int newResult = Integer.parseInt(perm.getPermission().replace("gts.limit.", ""));
			if(newResult > result) {result = newResult;
			}
		}
		return result;
	}
	
	public static int getTotalSell(Player player) {
		return getSells(player).size();
	}
	
    public static List<Sell> unformat(List<Sell> initial, int page) {
        if(initial == null || initial.isEmpty()) return initial;
        
        Collections.sort(initial, new timeComparator());
        if(initial.size() < 36) return initial;

        for(int i=0; i<=36*page; i++) {
        	initial.remove(i);
        }
        return initial;
    }
    
    public static void givePokemon(Player player, Pokemon pokemon) {	
        PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player.getUniqueId());
        storage.add(pokemon);
        util.sendMessage(player, "Vous avez reçu un " + pokemon.getSpecies().getLocalizedName());
    }
    
    public static Pokemon takePokemon(Player player, int slot) {
    	PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player.getUniqueId());
    	Pokemon poke = storage.get(slot);
    	
    	if(poke == null) return null;
    	
    	storage.set(slot, null);
    	util.sendMessage(player, "Votre " + poke.getSpecies().getPokemonName() + " vous a été enlevé.");
    	return poke;
    }
    
    public static PlayerPartyStorage getPlayerStorage(Player player) {
    	return Pixelmon.storageManager.getParty(player.getUniqueId());
    }
    
	@SuppressWarnings("deprecation")
	public static void setPokemon(Pokemon pokemon, UUID sellUUID) {
		database.get().set("sell." + sellUUID.toString() + ".pokemon.level", pokemon.getLevel());
		database.get().set("sell." + sellUUID.toString() + ".pokemon.nickname", pokemon.getNickname());
		database.get().set("sell." + sellUUID.toString() + ".pokemon.nature", pokemon.getNature().toString());
		database.get().set("sell." + sellUUID.toString() + ".pokemon.type", pokemon.getSpecies().toString());
		database.get().set("sell." + sellUUID.toString() + ".pokemon.is-shiny", pokemon.isShiny());
		database.get().set("sell." + sellUUID.toString() + ".pokemon.ability", pokemon.getAbilityName());
		database.get().set("sell." + sellUUID.toString() + ".pokemon.gender", pokemon.getGender().toString());
		
		database.get().set("sell." + sellUUID.toString() + ".pokemon.evs.attack", pokemon.getEVs().attack);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.evs.defence", pokemon.getEVs().defence);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.evs.specialAttack", pokemon.getEVs().specialAttack);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.evs.specialDefence", pokemon.getEVs().specialDefence);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.evs.hp", pokemon.getEVs().hp);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.evs.speed", pokemon.getEVs().speed);
		
		database.get().set("sell." + sellUUID.toString() + ".pokemon.ivs.attack", pokemon.getIVs().attack);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.ivs.defence", pokemon.getIVs().defence);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.ivs.specialAttack", pokemon.getIVs().specialAttack);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.ivs.specialDefence", pokemon.getIVs().specialDefence);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.ivs.hp", pokemon.getIVs().hp);
		database.get().set("sell." + sellUUID.toString() + ".pokemon.ivs.speed", pokemon.getIVs().speed);
		
		database.save();
	}
}
