package fr.robotv2.gts.database;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class database {

	private static File database;
	private static FileConfiguration databaseconfig;
	
	
	public static void setup() {
		database = new File(Bukkit.getServer().getPluginManager().getPlugin("OPIXELgts").getDataFolder(), "database.yml");
		if(!database.exists()) {
			try {
				database.createNewFile();
			} catch(IOException e) {
				System.out.println("§cErreur lors de la génération du fichier: database.yml");				
			}
		}
		
		databaseconfig = YamlConfiguration.loadConfiguration(database);
	}
	
	public static FileConfiguration get() {
		return databaseconfig;
	}
	
	public static void save() {
		try {
			databaseconfig.save(database);
		} catch (IOException e) {
			System.out.println("§cErreur lors de la sauvegarde du fichier: database.yml");
		}
	}
	
	public static void reload() {
		databaseconfig = YamlConfiguration.loadConfiguration(database);
	}
}
