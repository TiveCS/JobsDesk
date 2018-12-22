package team.creativecode.jobsdesk.menu.individual;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import team.creativecode.jobsdesk.menu.MenuManager;
import team.creativecode.jobsdesk.util.ItemManager;

public class MainMenu extends MenuManager {

	public MainMenu(Player p, MenuCategory category, int page) {
		super(p, category, page);
		Inventory inv = Bukkit.createInventory(null, 3*9, ChatColor.translateAlternateColorCodes('&', "&1Main Menu"));
		this.setMenu(inv);
	}

	@Override
	public void updateMenu(int page) {
		if (page != this.getPage()) {
			Inventory inv = Bukkit.createInventory(null, 3*9, ChatColor.translateAlternateColorCodes('&', "&1Main Menu"));
			loadMenu(page, inv);
		}
		
		ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta bordermeta = border.getItemMeta();
		bordermeta.setDisplayName(" ");
		for (int slot = 0; slot < this.getMenu().getSize(); slot++) {
			this.getMenu().setItem(slot, border);
		}
		
		List<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add("&fNeed help or guide to use JobsDesk?");
		lore.add("&fDon't worry about it! in this page,");
		lore.add("&fYou will learn about JobsDesk here!");
		this.getMenu().setItem(16, ItemManager.createItem(Material.KNOWLEDGE_BOOK, "&9&lGuide", lore));
		lore.clear();
		
		lore.add(" ");
		lore.add("&fSearching for works or being bored?");
		lore.add("&fYou can find some Jobs Desk here");
		lore.add("&fAnd exchange your result with rewards!");
		this.getMenu().setItem(14, ItemManager.createItem(Material.IRON_AXE, "&6&lJobs Desk", lore));
		lore.clear();
		
		lore.add(" ");
		lore.add("&fCheck your personal information");
		lore.add("&fhere. It contains work statistic, ");
		lore.add("&funclaimed rewards and other");
		this.getMenu().setItem(12, ItemManager.createItem(Material.BOOK_AND_QUILL, "&2&lPersonal Info", lore));
		lore.clear();
		
		lore.add(" ");
		lore.add("&fMake sure your statistic is");
		lore.add("&fon the top of the server!");
		lore.add("&fand you will get bonus things.");
		this.getMenu().setItem(10, ItemManager.createItem(Material.SIGN, "&3&lLeaderboard", lore));
		lore.clear();
		
	}

	@Override
	public void action(Player executor, int clickedSlot) {
		switch(clickedSlot) {
		case 10:
			//Leaderboard
			break;
		case 12:
			//Personal info
			break;
		case 14:
			//Jobs desk menu
			break;
		case 16:
			//Guide
			break;
		}
	}
	
	@Override
	public void loadMenu(int page, Inventory menu) {
		updateMenu(page);
		this.setMenu(menu);
	}

	@Override
	public void closeMenu() {
		
	}

}
