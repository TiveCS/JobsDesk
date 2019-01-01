package team.creativecode.jobsdesk.menu;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import team.creativecode.jobsdesk.Main;
import team.creativecode.jobsdesk.enums.JobsCreateEnum.InputType;

public abstract class MenuManager {
	
	public enum MenuCategory{
		//default
		MAIN, JOBS_LIST, PERSONAL_INFO, LEADERBOARD,
		
		//admin
		CREATE_JOBS;
	}
	
	public static HashMap<Player, MenuManager> viewer = new HashMap<Player, MenuManager>();
	public static HashMap<Player, InputType> input = new HashMap<Player, InputType>();
	
	Main plugin = Main.getPlugin(Main.class);
	Player user;
	MenuCategory category = MenuCategory.MAIN;
	int page = 1, previousPage = 1;
	Inventory menu, previousmenu;
	
	public MenuManager(Player p, MenuCategory category, int page){
		this.user = p;
		this.category = category;
		this.page = page;
	}
	
	public void openMenu() {
		this.updateMenu(this.page);
		this.user.openInventory(this.menu);
		viewer.put(this.user, this);
	}
	
	public void closeMenu() {
		viewer.remove(this.user);
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
	
	public void setPreviousMenu(Inventory inv) {
		this.previousmenu = inv;
	}
	
	public void changeMenu(Inventory nextMenu) {
		this.previousmenu = this.getMenu();
		this.menu = nextMenu;
		this.user.closeInventory();
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				loadMenu(1, getMenu());
				openMenu();		
			}
			
		}, 1l);
		
	}
	
	public abstract void updateMenu(int page);
	public abstract void action(Player executor, int clickedSlot);
	public abstract void loadMenu(int page, Inventory menu);
	
	public int getPage() {
		return this.page;
	}
	
	public Inventory getPreviousMenu() {
		return this.previousmenu;
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
