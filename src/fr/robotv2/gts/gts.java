package fr.robotv2.gts;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

import fr.robotv2.gts.commands.gtsCommand;
import fr.robotv2.gts.database.database;
import fr.robotv2.gts.sign.openSign;
import fr.robotv2.gts.ui.UImanager;
import fr.robotv2.gts.ui.gui;
import fr.robotv2.gts.ui.stock.chooseMenu;
import fr.robotv2.gts.ui.stock.itemSell;
import fr.robotv2.gts.ui.stock.mainMenu;
import fr.robotv2.gts.ui.stock.navMenu;
import fr.robotv2.gts.ui.stock.pokeSell;
import fr.robotv2.gts.ui.stock.stockOwn;
import net.milkbowl.vault.economy.Economy;

public class gts extends JavaPlugin {
	
	public static UImanager manager;
	public static openSign openSign;
	
	private Map<Class<? extends gui>, gui> menus;
	public Pokemon pokemon;
	
	public static Economy eco;
	
	@Override
	public void onEnable() {
		registerClasses();
		registerListeners();
		registerCommands();
		loadGUI();
		
		if(!setupEconomy()) {
			getLogger().warning("VAULT N'EST PAS INSTALLE.");
			getLogger().warning("LE PLUGIN NE PEUT PAS FONCTIONNER SANS VAULT.");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		openSign = new openSign(this);
	}
	
	@Override
	public void onDisable() {
	}
	
	public static UImanager getManager() {
		return manager;
	}
	
	public Map<Class<? extends gui>, gui> getMenus() {
		return menus;
	}
	
	public static Economy getEco() {
		return eco;
	}
	
	public void loadGUI() {
		menus = new HashMap<>();
		manager.addMenu(new mainMenu());
		manager.addMenu(new itemSell());
		manager.addMenu(new stockOwn());
		manager.addMenu(new navMenu());
		manager.addMenu(new chooseMenu());
		manager.addMenu(new pokeSell());
	}
		
	public void registerClasses() {
		database.setup();
		manager = new UImanager(this);
	}
	
	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new UImanager(this), this);
	}
	
	public void registerCommands() {
		getCommand("gts").setExecutor(new gtsCommand());
	}
	
	
	public boolean setupEconomy() {
		try {
			RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(Economy.class);
			if(economy != null)
				eco = economy.getProvider();
			return(eco != null);
		} catch (NoClassDefFoundError e) {
			return false;
		}

	}
	
	public static openSign getFactory() {
		return openSign;
	}

}
