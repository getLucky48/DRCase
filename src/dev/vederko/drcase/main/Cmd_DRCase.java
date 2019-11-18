package dev.vederko.drcase.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Cmd_DRCase implements Listener, CommandExecutor{
	
	@SuppressWarnings("unused")
	private MainClass plugin;
	
	public Cmd_DRCase(MainClass plugin) { this.plugin = plugin; }
	
	ArrayList<String> cooldown = new ArrayList<String>();
	
	private Inventory inv;
	
	String name;
	
	String color;
	
	int border;
	
	Location loc;
	
	SettingsManager settings = SettingsManager.getInstance();
	
	public static String prefix = "§b➣ §f";
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
	
		Location l = new Location(e.getPlayer().getWorld(), -13.5, 89.0, 259.0, (float) -90.0, (float) 0);
		
		e.getPlayer().teleport(l);		
		
	}

	
	public void result(InventoryClickEvent e, Player p, String inventname, String lower, String suffixes [] [], String name, String color) {
		
		if (inventname.toLowerCase().contains(lower)){

			e.setCancelled(true);
			
			if(e.getCurrentItem() == null) return;
			
			if(e.getCurrentItem().getType() == Material.TRIPWIRE_HOOK) {
				
				if(cooldown.contains(p.getName())) {
					
					p.sendMessage(" ");
					p.sendMessage(prefix + ChatColor.WHITE + "Прежде чем открыть кейс снова придется подождать!");
					p.sendMessage(" ");
					
					p.closeInventory();
					
					return;
					 
				}
				
				if(!settings.getDrCase().contains(p.getName() + "." + name)) {
					
					settings.getDrCase().set((p.getName() + "." + name), 0);
					
					settings.saveDrCase();
					
				}
				
				int key = settings.getDrCase().getInt(p.getName() + "." + name);
				
				if(key <= 0) {
					
					p.closeInventory();
					
					p.sendMessage(" ");
					p.sendMessage(prefix + ChatColor.WHITE + "У тебя нет ключа от этого кейса!");
					p.sendMessage(prefix + ChatColor.WHITE + "Купить ключ: §bhttps://drest.trademc.org/");
					p.sendMessage(" ");
					
					return;
					
				}		
				
				settings.getDrCase().set(p.getName() + "." + name, key - 1);
				
				settings.saveDrCase();
				
				Random rand = new Random();
				
				int x = rand.nextInt(26);
				
				p.closeInventory();
				
				String title = "§fОткрываем §" + color + lower + "§f...";
				
				TitleAPI.sendTitle(p, 10, 60, 10, title, "");	
				
				cooldown.add(p.getName());
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.plugin, new Runnable() {

					@Override
					public void run() {
						
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add " + suffixes[1] [x]);
						
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage("§" + color + "➣ §f" + "Игрок " + p.getName() + " выиграл титул §" + color + suffixes[0][x] + "§f, открыв §" + color + lower);
						Bukkit.broadcastMessage("§" + color + "➣ §f" + "§fКупить ключ: §" + color + "https://drest.trademc.org/");
						Bukkit.broadcastMessage(" ");
						
					}					
					
				}, 50);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.plugin, new Runnable() {

					@Override
					public void run() {
						
						cooldown.remove(p.getName());
						
					}					
					
				}, 80);				
				
				return;				
			
			}
							
		}
		
	}
	
	private ItemStack createItemBorder(short beton) {
				
		ItemStack i = new ItemStack(Material.WHITE_CONCRETE);
		
		i.setDurability(beton);
		
		ItemMeta im = i.getItemMeta();
		
		im.setDisplayName(color + name);
		
		List<String> lore = new ArrayList<String>();
		
		lore.add(" ");		
		lore.add(ChatColor.WHITE + "Если у тебя есть " + color + "ключ" + ChatColor.WHITE + " от этого кейса,");
		lore.add(ChatColor.WHITE + "то ты можешь открыть его " + color + "прямо сейчас!");
		lore.add(" ");
		lore.add(ChatColor.WHITE + "Приобрести ключик можно на нашем сайте: ");
		lore.add(color + "https://drest.trademc.org/");
		lore.add(" ");
		
		im.setLore(lore);
		
		i.setItemMeta(im);		
				
		return i;
		
	}	
	
	public void inv_main(Inventory inv, Player p, int border, String arrs) {
		
		inv = Bukkit.getServer().createInventory(null, 9*5, color + name);
		
		for(int i = 0; i < 9; i++) {
			
			if(i % 2 != 0) inv.setItem(i, createItemBorder((short) border));
			else inv.setItem(i, createItemBorder((short) 0));
			
		}
		
		inv.setItem(9, createItemBorder( (short) border));
		
		inv.setItem(17, createItemBorder( (short) border));
		
		inv.setItem(18, createItemBorder( (short) 0));
		
		inv.setItem(26, createItemBorder( (short) 0));
		
		inv.setItem(27, createItemBorder( (short) border));
		
		inv.setItem(35, createItemBorder( (short) border));
		
		
		for(int i = 36; i < 45; i++) {
			
			if(i % 2 != 0) inv.setItem(i, createItemBorder( (short) border));
			else inv.setItem(i, createItemBorder( (short) 0));
			
		}
		
		//22
		
		if(!settings.getDrCase().contains(p.getName() + "." + arrs)) {
			
			settings.getDrCase().set((p.getName() + "." + arrs), 0);
			
			settings.saveDrCase();
			
		}
		
		ItemStack i = new ItemStack(Material.TRIPWIRE_HOOK);
		
		ItemMeta im = i.getItemMeta();
		
		im.setDisplayName(color + name);
		
		List<String> lore = new ArrayList<String>();
				
		String keyss = " ключей";
		
		int keys_i = settings.getDrCase().getInt(p.getName() + "." + arrs);
		
		if((keys_i >= 21) || (keys_i <= 4)) {
			
			if(keys_i % 10 == 4) keyss = " ключа";
			
			if(keys_i % 10 == 3) keyss = " ключа";
			
			if(keys_i % 10 == 2) keyss = " ключа";
			
			if(keys_i % 10 == 1) keyss = " ключ";
			
		}
				
		lore.add(" ");		
		lore.add(ChatColor.WHITE + "Если у тебя есть " + color + "ключ" + ChatColor.WHITE + " от этого кейса,");
		lore.add(ChatColor.WHITE + "то ты можешь открыть его " + color + "прямо сейчас!");
		lore.add(" ");
		lore.add(ChatColor.WHITE + "У тебя " + color + String.valueOf(keys_i) + ChatColor.WHITE + keyss);
		lore.add(" ");
		lore.add(ChatColor.WHITE + "Приобрести ключик можно на нашем сайте: ");
		lore.add(color + "https://drest.trademc.org/");
		lore.add(" ");
		
		im.setLore(lore);
		
		im.addEnchant(Enchantment.KNOCKBACK, 1, true);
		
		i.setItemMeta(im);	
		//
		
		inv.setItem(22, i);
		
		p.openInventory(inv);
		
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		Player p = Bukkit.getPlayer(e.getWhoClicked().getName());
		
		String invname = e.getInventory().getName();

		if (invname.toLowerCase().contains("донат кейс")){
			
			e.setCancelled(true);
			
			if(e.getCurrentItem() == null) return;
			
			if(e.getCurrentItem().getType() == Material.TRIPWIRE_HOOK) {
				
				int key = settings.getDrCase().getInt(p.getName() + "." + "Донат_кейс");
				
				if(key <= 0) {
					
					p.closeInventory();
					
					p.sendMessage(" ");
					p.sendMessage(prefix + ChatColor.WHITE + "У тебя нет ключа от этого кейса!");
					p.sendMessage(prefix + ChatColor.WHITE + "Купить ключ: §bhttps://drest.trademc.org/");
					p.sendMessage(" ");					
					
					return;
					
				}
				
				settings.getDrCase().set(p.getName() + "." + "Донат_кейс", key - 1);
				
				settings.saveDrCase();
				
				String way = settings.getConfig().getConfigurationSection("plugins.cmd_drcase.cases.Донат_кейс.Items").getKeys(false).toString();
				
				way = way.replace("[", "");
				
				way = way.replace("]", "");
				
				way = way.replace(",", "");
				
				String arr [] = way.split(" ");
				
				int size = settings.getConfig().getConfigurationSection("plugins.cmd_drcase.cases.Донат_кейс.Items").getKeys(false).size();				
				
				Random rand = new Random();
				
				int x = rand.nextInt(100);
				
				for(int i = 0; i < size; i++) {
					
					int max = settings.getConfig().getInt("plugins.cmd_drcase.cases.Донат_кейс.Items." + arr[i] + ".max");
					
					int min = settings.getConfig().getInt("plugins.cmd_drcase.cases.Донат_кейс.Items." + arr[i] + ".min");
				
					if((x >= min) && (x <= max)){
						
						p.closeInventory();
						
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " group set " + arr[i]);
						
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage(prefix + "Игрок " + p.getName() + " выиграл " + settings.getConfig().getString("plugins.cmd_drcase.cases.Донат_кейс.Items."
						+ arr[i] + ".name") + ChatColor.WHITE + ", открыв " + settings.getConfig().getString("plugins.cmd_drcase.cases.Донат_кейс.color") + "Донат-кейс!");
						Bukkit.broadcastMessage(prefix + ChatColor.WHITE + "Купить ключ: §bhttps://drest.trademc.org/");
						Bukkit.broadcastMessage(" ");
						
						return;
						
					}
					
				}						
				
			}
			
		}

			String array_dark_gray [] [] = {
				{
				"Шахтёр", "Дровосек", "Рыбак", "Строитель", "Копатель", "Оружейник", "Фермер", "Стилист", "Охотник",
				"Зельевар", "Волшебник", "Принц", "Принцесса", "Лёд", "Огонь", "Рыцарь", "Цапля", "Тихоня",
				"milkyway", "Конфетка", "Яблоко", "Гроб", "Рыбка", "Стул", "Крутой", "Ёршик", "Напалм"
				},
				{
				"drsuffix.shahter", "drsuffix.drovosek", "drsuffix.ribak", "drsuffix.stroitel", "drsuffix.kotatel", "drsuffix.orejeynik", "drsuffix.fermer", "drsuffix.stilist", "drsuffix.ohotnik",
				"drsuffix.zelevar", "drsuffix.volshebnik", "drsuffix.princ", "drsuffix.princessa", "drsuffix.led", "drsuffix.ogon", "drsuffix.ricar", "drsuffix.caplya", "drsuffix.tihonya",
				"drsuffix.milkyway", "drsuffix.konfetka", "drsuffix.yabloko", "drsuffix.grob", "drsuffix.ribka", "drsuffix.stul", "drsuffix.krytoy", "drsuffix.ershik", "drsuffix.napalm"    
				}};
				
			String array_red [] [] = {
				{
				"XBOX360", "PS4", "PC", "GitHub", "Ctrl+Alt+Delete", "Биткоин", "Кодзима", "Пинг", "Ассенизаторщик",
				"Магадан", "Сапог", "Глюк", "Джон", "Боня", "Number\u00A0❶", "¯\\_(ツ)_/¯", "☭\u00A0USSR\u00A0☭", "(ᵔᴥᵔ)",
				"ب_ب", "◉◡◉", "(╥_╥)", "✔", "▄︻̷̿┻̿═━一", "(\u00A0･_･)♡", "✖\u00A0✖", "\u00a7k123456", "^ↀᴥↀ^"
				},
				{
				"drsuffix.XBOX360", "drsuffix.PS4", "drsuffix.PC", "drsuffix.GitHub", "drsuffix.Ctrl+Alt+Delete", "drsuffix.smail1", "drsuffix.smail2", "drsuffix.smail3", "drsuffix.smail4",
				"drsuffix.smail5", "drsuffix.smail6", "drsuffix.smail7", "drsuffix.smail8", "drsuffix.smail9", "drsuffix.smail10", "drsuffix.smail11", "drsuffix.smail12", "drsuffix.smail13",
				"drsuffix.smail14", "drsuffix.smail15", "drsuffix.smail16", "drsuffix.smail17", "drsuffix.smail18", "drsuffix.smail19", "drsuffix.smail20", "drsuffix.smail21", "drsuffix.smail22"    
				}};
				
				/////////////////////////////////////////////// ИГРОВЫЕ  
				
				String array_yellow [] [] = {
				{
				"Ведьмак", "Молчунья", "Солид\u00A0Снейк", "Марио", "Эйден\u00A0Пирс", "Cuphead", "CJ", "Линкольн\u00A0Клей", "Бласковиц",
				"Хлоя\u00A0Прайс", "Senua", "Шепард", "Сталкер", "Ваас", "Джейсон", "Джек\u00A0Джойс", "Crash\u00A0Bandicoot", "Артем",
				"Хан", "Тревор", "Майкл", "Лара\u00A0Крофт", "Дюк\u00A0Нюкем", "Челл", "Саб-Зиро", "Скорпион", "Mad\u00A0Max"
				},
				{
				"drsuffix.vedmak", "drsuffix.molchyniya", "drsuffix.solidsneyk", "drsuffix.mario", "drsuffix.eydenpirs", "drsuffix.cuphead", "drsuffix.cj", "drsuffix.linkolnkrey", "drsuffix.blaskovic",
				"drsuffix.hloyzprays", "drsuffix.senua", "drsuffix.chepard", "drsuffix.stalker", "drsuffix.vaas", "drsuffix.djeyson", "drsuffix.djekjoys", "drsuffix.crashdandicoot", "drsuffix.artem",
				"drsuffix.han", "drsuffix.trevor", "drsuffix.maykl", "drsuffix.larakroft", "drsuffix.dyknykem", "drsuffix.chell", "drsuffix.sabziro", "drsuffix.skorpion", "drsuffix.madmax"    
				}};
				
				/////////////////////////////////////////////// МАГИЧЕСКИЕ  
				
				String array_blue [] [] = {
				{
				"Ведьма", "Бес", "Зелье", "Гоблин", "Жертва", "Руна", "Мана", "Шаман", "Чары",
				"Чародей", "Эльф", "Амулет", "Аура", "Демон", "Дух", "Изгнание", "Печать", "Талисман",
				"Чернокнижник", "Вампир", "Дама", "Черт", "Тыква", "Снадобье", "Крест", "Черный", "Маг"
				},
				{
				"drsuffix.vedma", "drsuffix.bes", "drsuffix.zelbe", "drsuffix.goblin", "drsuffix.jertva", "drsuffix.runa", "drsuffix.mana", "drsuffix.shaman", "drsuffix.chari",
				"drsuffix.charodey", "drsuffix.elbf", "drsuffix.amylet", "drsuffix.ayra", "drsuffix.demon", "drsuffix.dyh", "drsuffix.izhnanie", "drsuffix.pechatb", "drsuffix.talisman",
				"drsuffix.charoknijnik", "drsuffix.vampir", "drsuffix.dama", "drsuffix.chert", "drsuffix.tikva", "drsuffix.snadobe", "drsuffix.krest", "drsuffix.cherniy", "drsuffix.mag"    
				}};
				
				/////////////////////////////////////////////// МОБЫ  
				
				String array_dark_green [] [] = {
				{
				"Мышь", "Блейз", "Паук", "Курица", "Телка", "Крипер", "Ишак", "Петух", "Страж",
				"Эндер", "Конь", "Гаст", "Лама", "Слизняк", "Кошак", "Кабан", "Кролик", "Овца",
				"Шалкер", "Чешуйница", "Скелет", "Зомбяра", "Спрут", "Деревенщина", "Волк", "Свинья", "Медведь"
				},
				{
				"drsuffix.maus", "drsuffix.bleyz", "drsuffix.payk", "drsuffix.chicken", "drsuffix.telka", "drsuffix.creeper", "drsuffix.ichak", "drsuffix.petyh", "drsuffix.straj",
				"drsuffix.enderman", "drsuffix.konb", "drsuffix.gast", "drsuffix.lama", "drsuffix.sliznyak", "drsuffix.koshak", "drsuffix.kaban", "drsuffix.krolik", "drsuffix.ovca",
				"drsuffix.shalker", "drsuffix.cheshuynica", "drsuffix.skelet", "drsuffix.zombyara", "drsuffix.spryt", "drsuffix.derevenchina", "drsuffix.wolf", "drsuffix.pig", "drsuffix.bear"    
				}};
				
				/////////////////////////////////////////////// ОБЫЧНЫЕ
				
				
				
				/////////////////////////////////////////////// РЕДКИЕ  
				
				String array_gray [] [] = {
				{
				"<3", "Интрига", "Посланник", "Надежда", "Тайна", "Птенчик", "Пират", "Метла", "Русалка",
				"Чукча", "Вурдалак", "Маньяк", "Мертвец", "Кнопка", "Училка", "Шкаф", "Кактус", "Музыкант",
				"Щегол", "Укус", "Клоп", "Ученик", "Паскаль", "Код", "Блок", "Пушкин", "Корзина"
				},
				{
				"drsuffix.serdce", "drsuffix.intriga", "drsuffix.poslannik", "drsuffix.nadejda", "drsuffix.tayna", "drsuffix.ptenchik", "drsuffix.pirat", "drsuffix.metla", "drsuffix.rysalka",
				"drsuffix.chykcha", "drsuffix.berdalak", "drsuffix.manyak", "drsuffix.mertvec", "drsuffix.knopka", "drsuffix.ychilka", "drsuffix.chkaf", "drsuffix.kaktys", "drsuffix.myzikant",
				"drsuffix.cegol", "drsuffix.ykys", "drsuffix.klop", "drsuffix.ychenik", "drsuffix.paskal", "drsuffix.kod", "drsuffix.block", "drsuffix.pyshin", "drsuffix.korzina"    
				}};
				
				/////////////////////////////////////////////// ХАЙПОВЫЕ   
				
				String array_purple [] [] = {
				{
				"Тян", "Пиксель", "Хакер", "ДиДжей", "ЧСВ", "Уганда", "Винишко", "Червь", "Мафиози",
				"Титан", "Няшка", "Царь", "Пророк", "Главарь", "Профи", "Антихайп", "Вредина", "Милаха",
				"Тихоня", "Кубач", "Фиаско", "Скипидар", "Гудрон", "ЯЖЕМАТЬ", "Хованский", "Навальный", "Дрын"
				},
				{
				"drsuffix.tyan", "drsuffix.pikcel", "drsuffix.haker", "drsuffix.didjey", "drsuffix.chsv", "drsuffix.yganda", "drsuffix.vinichko", "drsuffix.chervb", "drsuffix.mafiozi",
				"drsuffix.titan", "drsuffix.nyashka", "drsuffix.carb", "drsuffix.prorok", "drsuffix.glavarb", "drsuffix.profi", "drsuffix.antihayp", "drsuffix.vredina", "drsuffix.milaha",
				"drsuffix.tihonya", "drsuffix.kybach", "drsuffix.fiasko", "drsuffix.skipidar", "drsuffix.gydron", "drsuffix.yajemat", "drsuffix.hovanckiy", "drsuffix.novalniy", "drsuffix.drin"    
				}};
				
				/////////////////////////////////////////////// ЧЕМПИОНСКИЕ  
				
				String array_gold [] [] = {
				{
				"Герой", "Ленин", "Сталин", "Политик", "Добро", "Зло", "Система", "Яд", "Теория",
				"Спичка", "Сектор", "Долг", "Сталь", "Череп", "Пульс", "Снайпер", "Война", "Закон",
				"Стихия", "Император", "Кровь", "Ртуть", "Диктатор", "Президент", "Мститель", "Апокалипсис", "Чемпион"
				},
				{
				"drsuffix.geroy", "drsuffix.lenin", "drsuffix.stalin", "drsuffix.politik", "drsuffix.dobro", "drsuffix.zlo", "drsuffix.sistema", "drsuffix.yad", "drsuffix.teoriya",
				"drsuffix.spichka", "drsuffix.sektor", "drsuffix.dolg", "drsuffix.stalb", "drsuffix.cherep", "drsuffix.pylc", "drsuffix.snayper", "drsuffix.voyna", "drsuffix.zakon",
				"drsuffix.stihiya", "drsuffix.imperator", "drsuffix.krovb", "drsuffix.rtytb", "drsuffix.diktator", "drsuffix.prezident", "drsuffix.mstitel", "drsuffix.apokalipsis", "drsuffix.chempion"    
				}};
				
				/////////////////////////////////////////////// ВОЕННЫЕ   
				
				String array_green [] [] = {
				{
				"Портянка", "Каша", "Защитник", "Воин", "Солдат", "Обойма", "Автомат", "Носок", "Тельняшка",
				"Комбат", "Боец", "Капитан", "Победа", "Отвага", "Прапор", "Сержант", "Старшина", "Отбой",
				"Интендант", "Авиация", "Танк", "ПВО", "Калибр", "Матрос", "Пехота", "Пушка", "Наряд"
				},
				{
				"drsuffix.portyanka", "drsuffix.kasha", "drsuffix.zachitnik", "drsuffix.voin", "drsuffix.soldat", "drsuffix.oboyma", "drsuffix.avtomat", "drsuffix.nosok", "drsuffix.telbnyashka",
				"drsuffix.kombat", "drsuffix.boec", "drsuffix.kapitan", "drsuffix.pobeda", "drsuffix.otvaga", "drsuffix.prapor", "drsuffix.serjant", "drsuffix.starshina", "drsuffix.otboy",
				"drsuffix.intendant", "drsuffix.aviaciya", "drsuffix.tank", "drsuffix.pvo", "drsuffix.kalibr", "drsuffix.matros", "drsuffix.pehota", "drsuffix.pyshka", "drsuffix.naryad"    
				}};	

		result(e, p, invname, "обычные титулы", array_dark_gray, "Обычные_титулы", "8");
		result(e, p, invname, "редкие титулы", array_gray, "Редкие_титулы", "7");
		result(e, p, invname, "моб титулы", array_dark_green, "Моб_титулы", "2");
		result(e, p, invname, "военные титулы", array_green, "Военные_титулы", "a");
		result(e, p, invname, "магические титулы", array_blue, "Магические_титулы", "9");
		result(e, p, invname, "хайповые титулы", array_purple, "Хайповые_титулы", "d");
		result(e, p, invname, "чемпионские титулы", array_gold, "Чемпионские_титулы", "6");
		result(e, p, invname, "игровые титулы", array_yellow, "Игровые_титулы", "e");
		result(e, p, invname, "запрещенные титулы", array_red, "Запрещенные_титулы", "c");
		
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		
		Block b = e.getClickedBlock();
		
		if(b == null) return;
		
		loc = new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 1, b.getZ() + 0.5);
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if((b.getType() != Material.BLACK_SHULKER_BOX)
			&& (b.getType() != Material.BLUE_SHULKER_BOX)
			&& (b.getType() != Material.BROWN_SHULKER_BOX)
			&& (b.getType() != Material.CYAN_SHULKER_BOX)
			&& (b.getType() != Material.GRAY_SHULKER_BOX)
			&& (b.getType() != Material.GREEN_SHULKER_BOX)
			&& (b.getType() != Material.LIGHT_BLUE_SHULKER_BOX)
			&& (b.getType() != Material.LIME_SHULKER_BOX)
			&& (b.getType() != Material.MAGENTA_SHULKER_BOX)
			&& (b.getType() != Material.ORANGE_SHULKER_BOX)
			&& (b.getType() != Material.PINK_SHULKER_BOX)
			&& (b.getType() != Material.PURPLE_SHULKER_BOX)
			&& (b.getType() != Material.RED_SHULKER_BOX)
			&& (b.getType() != Material.LIGHT_GRAY_SHULKER_BOX)
			&& (b.getType() != Material.WHITE_SHULKER_BOX)
			&& (b.getType() != Material.YELLOW_SHULKER_BOX)) return;
				
			int x = (int) b.getLocation().getX();
			
			int y = (int) b.getLocation().getY();
			
			int z = (int) b.getLocation().getZ();
			
			String world = b.getLocation().getWorld().getName();	
			
			String way = settings.getConfig().getConfigurationSection("plugins.cmd_drcase.cases").getKeys(false).toString();
			
			way = way.replace("[", "");
			
			way = way.replace("]", "");
			
			way = way.replace(",", "");
			
			String arr [] = way.split(" ");
			
			int size = settings.getConfig().getConfigurationSection("plugins.cmd_drcase.cases").getKeys(false).size();				
			
			for(int i = 0; i < size; i++) {
				
				if(settings.getConfig().contains("plugins.cmd_drcase.cases." + arr[i] + ".location")) {
					
					int x1 = (int) settings.getConfig().getDouble("plugins.cmd_drcase.cases." + arr[i] + ".location.x");
					
					int y1 = (int) settings.getConfig().getDouble("plugins.cmd_drcase.cases." + arr[i] + ".location.y");
					
					int z1 = (int) settings.getConfig().getDouble("plugins.cmd_drcase.cases." + arr[i] + ".location.z");
					
					String world1 = settings.getConfig().getString("plugins.cmd_drcase.cases." + arr[i] + ".location.world");
					
					if((x == x1) && (y == y1) && (z == z1) && (world.toLowerCase().compareToIgnoreCase(world1.toLowerCase()) == 0)) {
						
						color = settings.getConfig().getString("plugins.cmd_drcase.cases." + arr[i] + ".color");
						
						name = settings.getConfig().getString("plugins.cmd_drcase.cases." + arr[i] + ".name");
						
						border = settings.getConfig().getInt("plugins.cmd_drcase.cases." + arr[i] + ".border");
												
						e.setCancelled(true);

						inv_main(inv, e.getPlayer(), border, arr[i]);
						
						return;
						
					}
					
				}
				
			}
			
			return;
			
		}
		
		return;
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 1) {
			
			Player p = Bukkit.getPlayer(sender.getName());
			
			if(p == null) {
				
				sender.sendMessage("Доступно только игрокам!");
				
				return true;
				
			}
			
			if(args[0].compareToIgnoreCase("mykeys") == 0) {
								
				String target = p.getName();
				
				if(!settings.getDrCase().contains(target)) {
					
					sender.sendMessage(" ");
					sender.sendMessage(prefix + "У тебя нет ключей!");
					sender.sendMessage(" ");
					
					return true;
					
				}
				
				String way = settings.getDrCase().getConfigurationSection(target).getKeys(false).toString();
				
				way = way.replace("[", "");
				
				way = way.replace("]", "");
				
				way = way.replace(",", "");
				
				String arr [] = way.split(" ");
				
				int size = settings.getDrCase().getConfigurationSection(target).getKeys(false).size();
				
				sender.sendMessage(" ");
				sender.sendMessage(prefix + "Твои ключики:");
				
				for(int i = 0; i < size; i++)
					
					sender.sendMessage("§b | " + arr[i] + ": " + ChatColor.WHITE + settings.getDrCase().getString(target + "." + arr[i]));				
				
				sender.sendMessage(" ");
				
				return true;
				
			}
			
		}
		
		if(args.length == 2) {
			
			if(!sender.hasPermission("jonyandvederko")) {
				
				sender.sendMessage(" ");				
				sender.sendMessage(prefix + "Недостаточно прав.");
				sender.sendMessage(" ");
				
				return true;
				
			}
			
			Player p = Bukkit.getPlayer(sender.getName());
			
			if(p == null) {
				
				sender.sendMessage("Доступно только игрокам!");
				
				return true;
				
			}
			
			if(args[0].compareToIgnoreCase("create") == 0) {
				
				Block b = p.getTargetBlock(null, 200);
				
				if(b == null) {
					
					p.sendMessage(" ");
					p.sendMessage(prefix + "Ты должен смотреть на блок!");
					p.sendMessage(" ");
					
					return true;
					
				}
				
				if((b.getType() != Material.BLACK_SHULKER_BOX)
				&& (b.getType() != Material.BLUE_SHULKER_BOX)
				&& (b.getType() != Material.BROWN_SHULKER_BOX)
				&& (b.getType() != Material.CYAN_SHULKER_BOX)
				&& (b.getType() != Material.GRAY_SHULKER_BOX)
				&& (b.getType() != Material.GREEN_SHULKER_BOX)
				&& (b.getType() != Material.LIGHT_BLUE_SHULKER_BOX)
				&& (b.getType() != Material.LIME_SHULKER_BOX)
				&& (b.getType() != Material.MAGENTA_SHULKER_BOX)
				&& (b.getType() != Material.ORANGE_SHULKER_BOX)
				&& (b.getType() != Material.PINK_SHULKER_BOX)
				&& (b.getType() != Material.PURPLE_SHULKER_BOX)
				&& (b.getType() != Material.RED_SHULKER_BOX)
				&& (b.getType() != Material.LIGHT_GRAY_SHULKER_BOX)
				&& (b.getType() != Material.WHITE_SHULKER_BOX)
				&& (b.getType() != Material.YELLOW_SHULKER_BOX)) {
					
					p.sendMessage(prefix + "Ты должен смотреть на ящик шалкера!");
					
					return true;
					
				}
				
				if(!settings.getConfig().contains("plugins.cmd_drcase.cases." + args[1])) {
					
					p.sendMessage(" ");
					p.sendMessage(prefix + "Кейса не существует!");
					p.sendMessage(" ");
					
					return true;
					
				}
				
				settings.getConfig().set("plugins.cmd_drcase.cases." + args[1] + ".location.x", b.getLocation().getX());
				settings.getConfig().set("plugins.cmd_drcase.cases." + args[1] + ".location.y", b.getLocation().getY());
				settings.getConfig().set("plugins.cmd_drcase.cases." + args[1] + ".location.z", b.getLocation().getZ());
				settings.getConfig().set("plugins.cmd_drcase.cases." + args[1] + ".location.world", b.getLocation().getWorld().getName());
				settings.saveConfig();
				
				p.sendMessage(" ");
				p.sendMessage(prefix + "Кейс установлен!");
				p.sendMessage(" ");
				
				return true;
				
			}
			
			if(args[0].compareToIgnoreCase("delete") == 0) {
								
				if(!settings.getConfig().contains("plugins.cmd_drcase.cases." + args[1] + ".location")) {
					
					p.sendMessage(" ");
					p.sendMessage(prefix + "Кейса не существует!");
					p.sendMessage(" ");
					
					return true;
					
				}
				
				settings.getConfig().set("plugins.cmd_drcase.cases." + args[1] + ".location", null);
				
				settings.saveConfig();
				
				p.sendMessage(" ");
				p.sendMessage(prefix + "Кейс удален!");
				p.sendMessage(" ");
				
				return true;		
				
			}
			
			if(args[0].compareToIgnoreCase("info") == 0) {
				
				String target = args[1];
				
				if(!settings.getDrCase().contains(target)) {
					
					sender.sendMessage(" ");
					sender.sendMessage(prefix + "Информация об игроке отсутствует!");
					sender.sendMessage(" ");
					
					return true;
					
				}
				
				String way = settings.getDrCase().getConfigurationSection(target).getKeys(false).toString();
				
				way = way.replace("[", "");
				
				way = way.replace("]", "");
				
				way = way.replace(",", "");
				
				String arr [] = way.split(" ");
				
				int size = settings.getDrCase().getConfigurationSection(target).getKeys(false).size();
				
				p.sendMessage(" ");
				sender.sendMessage(prefix + "Информация об игроке " + target + ":");
				
				for(int i = 0; i < size; i++)
					
					sender.sendMessage(ChatColor.AQUA +" | " + arr[i] + ": " + ChatColor.WHITE + settings.getDrCase().getString(target + "." + arr[i]));
				
				sender.sendMessage(" ");
				
				return true;
				
			}
			
		}
		
		if(args.length == 4) {
			
			if(!sender.hasPermission("jonyandvederko")) {
				
				sender.sendMessage(" ");
				sender.sendMessage(prefix + "Недостаточно прав.");
				sender.sendMessage(" ");
				
				return true;
				
			}
			
			if(args[0].compareToIgnoreCase("givekey") == 0) {
				
				String target = args[1];
				
				if(!settings.getConfig().contains("plugins.cmd_drcase.cases." + args[2])) {
					
					sender.sendMessage(" ");
					sender.sendMessage(prefix + "Кейса не существует!");
					sender.sendMessage(" ");
					
					return true;
					
				}
				
				if(!settings.getDrCase().contains(args[1] + "." + args[2])) settings.getDrCase().set(args[1] + "." + args[2], 0);
				
				int keys = settings.getDrCase().getInt(args[1] + "." + args[2]);
				
				int up = 0;
				
				try {
					
					up = Integer.valueOf(args[3]);
					
					settings.getDrCase().set(args[1] + "." + args[2], keys + up);
					
					settings.saveDrCase();	
					
					sender.sendMessage(" ");
					sender.sendMessage(prefix + "Игроку " + target + " было добавлено " + up + " ключей!");
					sender.sendMessage(" ");
					
					return true;
					
				}
				
				catch(NumberFormatException e) {
					
					sender.sendMessage(" ");
					sender.sendMessage(prefix + "Неправильно указано кол-во ключей!");
					sender.sendMessage(" ");
					
					return true;
					
				}

			}
			
			if(args[0].compareToIgnoreCase("setkey") == 0) {
				
				String target = args[1];
							
				if(!settings.getConfig().contains("plugins.cmd_drcase.cases." + args[2])) {
					
					sender.sendMessage(" ");
					sender.sendMessage(prefix + "Кейса не существует!");
					sender.sendMessage(" ");
					
					return true;
					
				}
				
				if(!settings.getDrCase().contains(args[1] + "." + args[2])) settings.getDrCase().set(args[1] + "." + args[2], 0);
				
				int up = 0;
				
				try {
					
					up = Integer.valueOf(args[3]);
					
					settings.getDrCase().set(args[1] + "." + args[2], up);
					
					settings.saveDrCase();	
					
					sender.sendMessage(" ");
					sender.sendMessage(prefix + "Игроку " + target + " было установлено " + up + " ключей!");
					sender.sendMessage(" ");
					
					return true;
					
				}
				
				catch(NumberFormatException e) {
					
					sender.sendMessage(" ");
					sender.sendMessage(prefix + "Неправильно указано кол-во ключей!");
					sender.sendMessage(" ");
					
					return true;
					
				}

			}	
			
		}	
		
		sender.sendMessage(" ");
		sender.sendMessage(prefix + "Помощь по плагину: кейсы");
		sender.sendMessage(" ");
		sender.sendMessage(ChatColor.WHITE + " Установить кейс, глядя на него " + ChatColor.AQUA + "/drc create [название]");
		sender.sendMessage(ChatColor.WHITE + " Удалить кейс " + ChatColor.AQUA + "/drc delete [название]");
		sender.sendMessage(ChatColor.WHITE + " Выдать ключики игроку " + ChatColor.AQUA + "/drc givekey [игрок] [название] [кол-во]");
		sender.sendMessage(ChatColor.WHITE + " Установить ключики игроку " + ChatColor.AQUA + "/drc setkey [игрок] [название] [кол-во]");
		sender.sendMessage(ChatColor.WHITE + " Установить ключики игроку " + ChatColor.AQUA + "/drc info [игрок]");
		sender.sendMessage(ChatColor.WHITE + " Получить информацию о себе " + ChatColor.AQUA + "/drc mykeys");
		sender.sendMessage(" ");
		sender.sendMessage(MainClass.author);
		sender.sendMessage(" ");
		
		return true;
		
	}

}
