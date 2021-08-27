package fr.robotv2.gts.object;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.EnumInitializeCategory;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.abilities.AbilityBase;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import fr.robotv2.gts.database.database;
import fr.robotv2.gts.ui.itemBuilder;

public class Sell {
	
	private OfflinePlayer owner;
	private UUID itemUUID;
	private double price;
	private Long start;
	private Long end;
	private sellType type;
	
	private boolean isSelled;
	private boolean shown;
	
	private ItemStack item;
	
	public Sell(UUID itemUUID) {
		this.owner = Bukkit.getOfflinePlayer(UUID.fromString(database.get().getString("sell." + itemUUID.toString() + ".owner")));
		this.itemUUID = itemUUID;
		this.price = database.get().getDouble("sell." + itemUUID.toString() + ".price");
		this.start = database.get().getLong("sell." + itemUUID.toString() + ".start");
		this.end = database.get().getLong("sell." + itemUUID.toString() + ".end");
		this.type = sellType.valueOf(database.get().getString("sell." + itemUUID.toString() + ".type"));
		this.isSelled = database.get().getBoolean("sell." + itemUUID.toString() + ".isSelled");
		this.shown = database.get().getBoolean("sell." + itemUUID.toString() + ".shown");		
		this.item = database.get().getItemStack("sell." + itemUUID.toString() + ".item");
	}
	
	public void save() {
		database.get().set("sell." + itemUUID.toString() + ".owner", this.owner.getUniqueId().toString());
		database.get().set("sell." + itemUUID.toString() + ".price", this.price);
		database.get().set("sell." + itemUUID.toString() + ".start", this.start);
		database.get().set("sell." + itemUUID.toString() + ".end", this.end);
		database.get().set("sell." + itemUUID.toString() + ".type", this.type.toString());
		database.get().set("sell." + itemUUID.toString() + ".isSelled", this.isSelled);
		database.get().set("sell." + itemUUID.toString() + ".shown", this.shown);
		database.get().set("sell." + itemUUID.toString() + ".item", this.item);
	}
	
	public void remove() {
		database.get().set("sell." + itemUUID.toString() + ".owner", null);
		database.get().set("sell." + itemUUID.toString() + ".price", null);
		database.get().set("sell." + itemUUID.toString() + ".start", null);
		database.get().set("sell." + itemUUID.toString() + ".end", null);
		database.get().set("sell." + itemUUID.toString() + ".type", null);
		database.get().set("sell." + itemUUID.toString() + ".isSelled", null);
		database.get().set("sell." + itemUUID.toString() + ".shown", null);
		database.get().set("sell." + itemUUID.toString() + ".item", null);
		database.get().set("sell." + itemUUID.toString(), null);
		database.save();
	}
	
	public OfflinePlayer getOwner() {
		return owner;
	}
	
	public void setOwner(OfflinePlayer owner) {
		this.owner = owner;
	}
	
	public UUID getUUID() {
		return itemUUID;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price; 
	}
	
	public Long getStart() {
		return start;
	}
	
	public void setStart(Long start) {
		this.start = start;
	}
	
	//32H
	public Long getEnd() {
		return end;
	}
	
	public void setEnd(Long end) {
		this.end = end;
	}
	
	public sellType getType() {
		return type;
	}
	
	public void setType(sellType type) {
		this.type = type;
	}
	
	public boolean isSelled() {
		return isSelled;
	}
	
	public void setSelled(boolean isSelled) {
		this.isSelled = isSelled;
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void setShown(boolean shown) {
		this.shown = shown;
	}
	
	public ItemStack getItem() {
		switch(type) {
		case ITEM:
			return item;
		case POKEMON:
			return itemBuilder.getPokemonStack(this);
		}
		return null;
	}
	
	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	public Pokemon getPokemon() {
		if(type.equals(sellType.ITEM)) return null;
		
		int level = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.level");
		String nick = database.get().getString("sell." + itemUUID.toString() + ".pokemon.nickname");
		EnumNature nature = EnumNature.natureFromString(database.get().getString("sell." + itemUUID.toString() + ".pokemon.nature"));
		
		EnumSpecies species = EnumSpecies.getFromNameAnyCaseNoTranslate(database.get().getString("sell." + itemUUID.toString() + ".pokemon.type"));
		boolean isShiny = database.get().getBoolean("sell." + itemUUID.toString() + ".pokemon.is-shiny");
		AbilityBase ability = AbilityBase.getAbility(database.get().getString("sell." + itemUUID.toString() + ".pokemon.ability")).get();
		
		Gender gender = Gender.getGender(database.get().getString("sell." + itemUUID.toString() + ".pokemon.gender"));
		
		int evs_attack = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.evs.attack");
		int evs_defence = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.evs.defence");
		int evs_specialAttack = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.evs.specialAttack");
		int evs_specialDefence = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.evs.specialDefence");
		int evs_hp = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.evs.hp");
		int evs_speed = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.evs.speed");
		
		int ivs_attack = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.ivs.attack");
		int ivs_defence = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.ivs.defence");
		int ivs_specialAttack = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.ivs.specialAttack");
		int ivs_specialDefence = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.ivs.specialDefence");
		int ivs_hp = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.ivs.hp");
		int ivs_speed = database.get().getInt("sell." + itemUUID.toString() + ".pokemon.ivs.speed");
		
		Pokemon poke = Pixelmon.pokemonFactory.createDefault(UUID.randomUUID());	
		if(nick != "null") poke.setNickname(nick);
		poke.setNature(nature);
		poke.setSpecies(species);
		poke.setShiny(isShiny);
		poke.setAbility(ability);
		poke.setGender(gender);
		
		poke.initialize(EnumInitializeCategory.INTRINSIC);
		
		poke.getEVs().setStat(StatsType.Attack, evs_attack);
		poke.getEVs().setStat(StatsType.Defence, evs_defence);
		poke.getEVs().setStat(StatsType.SpecialAttack, evs_specialAttack);
		poke.getEVs().setStat(StatsType.SpecialDefence, evs_specialDefence);
		poke.getEVs().setStat(StatsType.HP, evs_hp);
		poke.getEVs().setStat(StatsType.Speed, evs_speed);
		
		poke.getIVs().setStat(StatsType.Attack, ivs_attack);
		poke.getIVs().setStat(StatsType.Defence, ivs_defence);
		poke.getIVs().setStat(StatsType.SpecialAttack, ivs_specialAttack);
		poke.getIVs().setStat(StatsType.SpecialDefence, ivs_specialDefence);
		poke.getIVs().setStat(StatsType.HP, ivs_hp);
		poke.getIVs().setStat(StatsType.Speed, ivs_speed);

		poke.setLevel(level);
		return poke;
	}
}
