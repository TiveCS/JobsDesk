package team.creativecode.jobsdesk.menu;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import team.creativecode.jobsdesk.Main;

public abstract class MenuManager {
	
	public static HashMap<Player, MenuManager> viewer = new HashMap<Player, MenuManager>();
	
	public enum MenuCategory{
		//default
		MAIN, JOBS_LIST, PERSONAL_INFO, LEADERBOARD,
		
		//admin
		CREATE_JOBS;
	}
	
	Main plugin = Main.getPlugin(Main.class);
	Player user;
	MenuCategory category = MenuCategory.MAIN;
	int page = 1, previousPage = 1;
	Inventory menu;
	
	public MenuManager(Player p, MenuCategory category, int page){
		this.user = p;
		this.category = category;
		this.page = page;
	}
	
	public void openMenu() {
		this.user.openInventory(this.menu);
	}
	
	public void setPage(int pg) {
		this.previousPage = this.page;
		this.page = pg;
	}
	
	public void setMenu(Inventory inv) {
		this.menu = inv;
	}
	
	public void setCategory(MenuCategory cat) {
		this.category = cat;
	}
	
	public abstract void updateMenu(int page);
	public abstract void action(Player executor, int clickedSlot);
	public abstract void loadMenu(int page, Inventory menu);
	public abstract void closeMenu();
	
	public int getPage() {
		return this.page;
	}
	
	public Inventory getMenu() {
		return this.menu;
	}
	
	public MenuCategory getCategory() {
		return this.category;
	}
	
	public Player getPlayer() {
		return this.user;
	}

}
