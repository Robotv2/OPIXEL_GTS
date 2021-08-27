package fr.robotv2.gts.sign;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

import fr.robotv2.gts.gts;
import fr.robotv2.gts.object.sellType;
import fr.robotv2.gts.ui.stock.itemSell;
import fr.robotv2.gts.ui.stock.pokeSell;

public class signUtil {
	
	public static HashMap<Player, sellType> type = new HashMap<>();
	
	public static HashMap<Player, ItemStack> sellingItem = new HashMap<>();
	public static HashMap<Player, Double> priceItem= new HashMap<>();
	
	public static HashMap<Player, Pokemon> sellingPokemon = new HashMap<>();
	public static HashMap<Player, Double> pricePokemon= new HashMap<>();
    
    public static void createAndOpen(Player target) {
        openSign.Menu menu = gts.getFactory().newMenu(Arrays.asList("", "Ecrivez le", "nouveau montant", ""))
                .reopenIfFail(true)
                .response((player, strings) -> {	
            		try {
            			Double current = Double.parseDouble(strings[0]);
            			switch(signUtil.type.get(player)) {
            			case ITEM:
            				signUtil.priceItem.remove(player);
            				signUtil.priceItem.put(player, current);
            				gts.getManager().open(player, itemSell.class);
            				break;
            			case POKEMON:
            				signUtil.pricePokemon.remove(player);
            				signUtil.pricePokemon.put(player, current);
            				gts.getManager().open(player, pokeSell.class);
            				break;
            			}
            			return true;
            		} catch (NumberFormatException x) {
            			return false;
            		}
                });
        menu.open(target);
    }
}
