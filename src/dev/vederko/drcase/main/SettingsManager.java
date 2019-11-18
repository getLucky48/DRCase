package dev.vederko.drcase.main;

import java.io.File;
import java.io.IOException;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {
	
        private SettingsManager() { }
       
        static SettingsManager instance = new SettingsManager();
       
        public static SettingsManager getInstance() {
                return instance;
        }
       
        Plugin p;
       
        FileConfiguration config;
        
        FileConfiguration data;
        
        FileConfiguration drcase;
        
        File cfile;
        
        File dfile;        
        
        File drcaseFile;
        
        public void setup(Plugin p) {
        	
                cfile = new File(p.getDataFolder(), "config.yml");
                
                config = p.getConfig();
               
                if (!p.getDataFolder().exists()) {
                	
                        p.getDataFolder().mkdir();
                        
                }
                
    	        dfile = new File(p.getDataFolder(), "data.yml");
    	        
    	        if (!dfile.exists()) {
    	                try {
    	                        dfile.createNewFile();
    	                }
    	                catch (IOException e) {
    	                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml !");
    	                }
    	        }
    	       
    	        data = YamlConfiguration.loadConfiguration(dfile);   
             
                
    	        
	   	       
	   	       	drcaseFile = new File(p.getDataFolder(), "drcase.yml");
		        
	   	        if (!drcaseFile.exists()) {
	   	                try {
	   	                        drcaseFile.createNewFile();
	   	                }
	   	                catch (IOException e) {
	   	                        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create drcase.yml !");
	   	                }
	   	        }
	   	       
	   	       drcase = YamlConfiguration.loadConfiguration(drcaseFile); 
                
        }
       
        public FileConfiguration getData() 			{return data;}
        
        public FileConfiguration getConfig() 		{return config;}
                
        public FileConfiguration getDrCase() 			{return drcase;}
       
        public void saveConfig() {
        	
            try {config.save(cfile);}
            
            catch (IOException e) {Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");}
            
        }
        
        public void saveData() {
        	
        	try {data.save(dfile);}
        	
            catch (IOException e) {Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");}
        	
        }
                
        public void saveDrCase() {
        	
        	try {drcase.save(drcaseFile);}
        	
            catch (IOException e) {Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save drcase.yml!");}
        	
        }       
        public void reloadConfig() {config = YamlConfiguration.loadConfiguration(cfile);}
        
        public void reloadData() {data = YamlConfiguration.loadConfiguration(dfile);}
              
        public void reloadDrCase() {drcase = YamlConfiguration.loadConfiguration(drcaseFile);} 
       
        public PluginDescriptionFile getDesc() {return p.getDescription();}
        
}