package fr.robotv2.gts.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

import fr.robotv2.gts.gts;
import fr.robotv2.gts.util;

public class UImanager implements Listener {

	private gts gts;
	public UImanager(gts gts) {
		this.gts = gts;
	}	
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack current = e.getCurrentItem();
        
        if(e.getCurrentItem() == null) return;
 
        gts.getMenus().values().stream()
        .filter(menu -> inv.getName().equals(menu.getName(player)))
        .forEach(menu -> {
            menu.onClick(player, e.getInventory(), current, e.getRawSlot());
            e.setCancelled(true);
        });
	}
	
    public void addMenu(gui gui){
        gts.getMenus().put(gui.getClass(), gui);
    }
    
    public void open(Player player, Class<? extends gui> gClass){
        
        if(!gts.getMenus().containsKey(gClass)) return;
 
        gui menu = gts.getMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(menu.getHolder(), menu.getSize(), menu.getName(player));
        menu.contents(player, inv);
        
        Bukkit.getScheduler().runTaskLater(gts, () -> {
        	player.openInventory(inv);
        }, 1);
    }
    
    @EventHandler
    public void join(PlayerJoinEvent e) {
    	Pokemon poke = util.takePokemon(e.getPlayer(), 4);
    	
    	Bukkit.getScheduler().runTaskLater(gts, () -> {
    		if(poke != null)
    			util.givePokemon(e.getPlayer(), poke);
    	}, 100);
    }
}
