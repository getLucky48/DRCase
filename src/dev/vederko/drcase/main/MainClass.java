package dev.vederko.drcase.main;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin{
	
	public static MainClass plugin;
	
	public static String prefix = "§7[§cD§eR§f§7] §f";
	
	public static String author = " §fАвтор плагина: §3getLucky48 §a[vk.com/getLucky48]";
	
	public static ArrayList<String> cooldown = new ArrayList<String>();
	
	public void onEnable() {
		
		plugin = this;	
		
		File config = new File(getDataFolder() + File.separator + "config.yml");
		
		if(!config.exists()) {
			
			getLogger().info("Creating new config file...");
			
			getConfig().options().copyDefaults(true);
			
			saveDefaultConfig();
			
		}
		
		SettingsManager settings = SettingsManager.getInstance();
		
		settings.setup(this);
				
		getCommand("drcase").setExecutor(new Cmd_DRCase(this));
		
		Bukkit.getPluginManager().registerEvents(new Cmd_DRCase(this), this);
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[DRCase] ENABLED :)");
		
	}
	
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[DRCase] DISABLED :c");
		
	}	
}
