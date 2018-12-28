package team.creativecode.jobsdesk.menu.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import team.creativecode.jobsdesk.Main;
import team.creativecode.jobsdesk.manager.JobsCreate;
import team.creativecode.jobsdesk.menu.MenuManager;
import team.creativecode.jobsdesk.util.ItemManager;

public class JobsCreateMenu extends MenuManager {

	public static Main plugin = Main.getPlugin(Main.class);
	public final static File file = new File(plugin.getDataFolder(), "jobs.yml");
	public final static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	public final static String inventoryprefix = "Jobs Create > ";
	
	JobsCreate jc;
	String jobsname;
	
	public JobsCreateMenu(Player p, MenuCategory category, int page, String jobsname) {
		super(p, category, page);
		this.jc = new JobsCreate(jobsname);
		this.jobsname = jobsname;
		this.setMenu(Bukkit.createInventory(null, 6*9, inventoryprefix + jobsname));
	}
	
	public JobsCreateMenu(Player p, MenuCategory category, int page, JobsCreate jc) {
		super(p, category, page);
		this.jc = jc;
		this.jobsname = jc.getJobsName();
		this.setMenu(Bukkit.createInventory(null, 6*9, inventoryprefix + jobsname));
	}
	
	@Override
	public void updateMenu(int page) {
		
		List<String> lore = new ArrayList<String>();
		for (int i = 0; i < this.getMenu().getSize(); i++) {
			this.getMenu().setItem(i, ItemManager.createItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), " ", lore));
		}
	}

	@Override
	public void action(Player executor, int clickedSlot) {
		
	}

	@Override
	public void loadMenu(int page, Inventory menu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeMenu() {
		// TODO Auto-generated method stub
		
	}
	
	
	// config data getter

	// built in data getter
	public String getJobsName() {
		return this.jobsname;
	}
	
	public JobsCreate getJobsCreateData() {
		return this.jc;
	}
}
