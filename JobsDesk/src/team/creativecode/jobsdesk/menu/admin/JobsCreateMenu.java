package team.creativecode.jobsdesk.menu.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import team.creativecode.jobsdesk.ConfigManager;
import team.creativecode.jobsdesk.Main;
import team.creativecode.jobsdesk.enums.JobsCreateEnum.JobsRequirement;
import team.creativecode.jobsdesk.enums.JobsCreateEnum.JobsReward;
import team.creativecode.jobsdesk.manager.JobsCreate;
import team.creativecode.jobsdesk.menu.MenuManager;
import team.creativecode.jobsdesk.util.ItemManager;
import team.creativecode.jobsdesk.util.MsgManager;

public class JobsCreateMenu extends MenuManager {

	public static Main plugin = Main.getPlugin(Main.class);
	public final static File file = new File(plugin.getDataFolder(), "jobs.yml");
	public final static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	public final String inventoryprefix = "Jobs Create > ";
	public HashMap<Player, String> inputPath = new HashMap<Player, String>();
	
	JobsCreate jc;
	String jobsname;
	
	// Mode: MainMenu, PhaseMenu, RewardMenu, ReqPermissionMenu, ReqJobsMenu 
	JobsCreateMode mode;
	
	public enum JobsCreateMode{
		MAIN_MENU,
		INPUT_REWARD,
		INPUT_REQUIREMENT;
	}
	
	public JobsCreateMenu(Player p, MenuCategory category, int page, String jobsname) {
		super(p, category, page);
		this.jc = new JobsCreate(jobsname);
		this.jobsname = jobsname;
		this.mode = JobsCreateMode.MAIN_MENU;
	}
	
	public JobsCreateMenu(Player p, MenuCategory category, int page, JobsCreate jc) {
		super(p, category, page);
		this.jc = jc;
		this.jobsname = jc.getJobsName();
		this.mode = JobsCreateMode.MAIN_MENU;
	}
	
	@Override
	public void updateMenu(int page) {
		try {
			this.getMenu().clear();
		}catch(Exception e) {}
		loadMainMenu(page);
	}		

	@Override
	public void action(Player executor, int clickedSlot) {
		ItemStack item = new ItemStack(Material.AIR);
		try {
			if (!this.getMenu().getItem(clickedSlot).getType().equals(Material.AIR)) {
				item = this.getMenu().getItem(clickedSlot);
			}
		}catch(Exception e) {}
		//String title = this.getMenu().getTitle();
		
		if (!item.getType().equals(Material.AIR)) {
			if (item.getItemMeta().getDisplayName().equalsIgnoreCase(JobsRequirement.LEVEL.getIcon().getItemMeta().getDisplayName())) {
				this.getPlayer().closeInventory();
				viewer.put(this.getPlayer(), this);
				inputPath.put(this.getPlayer(), JobsRequirement.LEVEL.name());
				input.put(this.getPlayer(), JobsRequirement.LEVEL.getInputType());
			}
		}else {
			this.getMenu().setItem(clickedSlot, new ItemStack(Material.BARRIER));
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

				@Override
				public void run() {
					getMenu().setItem(clickedSlot, new ItemStack(Material.AIR));					
				}
				
			}, 1l);
		}
	}

	@Override
	public void loadMenu(int page, Inventory menu) {
		updateMenu(page);
	}
	
	public void loadMainMenu(int page) {
		this.setMenu(Bukkit.createInventory(null, 6*9, inventoryprefix + jobsname));
		
		List<String> lore = new ArrayList<String>();
		int num = ((page*7) - 7) + 1;
		int numrew = ((page*15) - 15);
		int numreq = JobsRequirement.values().length - 1;
		boolean checked = false, checkedrew = false;
		List<String> rewards = new ArrayList<String>();
		if (ConfigManager.contains(file, this.jobsname + ".Rewards")) {
			rewards = new ArrayList<String>(config.getConfigurationSection(this.jobsname + ".Rewards").getKeys(false));
		}
		
		for (int coloumn = 0; coloumn < this.getMenu().getSize() / 9; coloumn++) {
			for (int row = 0; row < 9; row++) {
				lore.clear();
				int slot = ((coloumn*9) + row);
				
				// Phase
				if (coloumn == 0 && (row > 0 && row < 8)) {
					if (ConfigManager.contains(file, this.jobsname + ".Phases." + num)) {
						lore.add(" ");
						lore.add("&3Contains &b" + this.getJobsCreateData().getObjectiveSize(num) + " &3Objective&7(&bs&7)");
						this.getMenu().setItem(slot, ItemManager.createItem(Material.BOOK, "&7Phase #" + num, lore));
						num++;
					}else {
						if (checked == true) {
							this.getMenu().setItem(slot, new ItemStack(Material.AIR));
						}else {
							this.getMenu().setItem(slot, ItemManager.createItem(Material.BOOK_AND_QUILL, "&fAdd Phase #" + num, lore));
							checked = true;
						}
					}
				}
				// Reward
				else if (coloumn > 1 && coloumn < 5 && (row > 3 && row < 9)) {
					if (ConfigManager.contains(file, this.jobsname + ".Rewards")) {
						if (rewards.size() >= numrew + 1) {
							Object value = "";
							JobsReward jr = JobsReward.valueOf(rewards.get(numrew).toUpperCase());
							if (!rewards.get(numrew).equalsIgnoreCase("ITEM")) {
								lore.add(" ");
								switch(jr) {
								case LEVEL:
									value = this.jc.getRewardNumber(jr);
									lore.add("&8- &6Level &e" + value);
									break;
								case MONEY:
									value = this.jc.getRewardNumber(jr);
									lore.add("&8- &b$" + value);
									break;
								default:
									value = this.jc.getRewardList(jr);
									MsgManager.insertList(lore, MsgManager.convertObjectToList(value), "&8- &f");
									break;
								}
								this.getMenu().setItem(slot, ItemManager.setLore(JobsReward.valueOf(rewards.get(numrew).toUpperCase()).getIcon(), lore));
								numrew++;
							}
						}else {
							if (checkedrew == true) {
								this.getMenu().setItem(slot, new ItemStack(Material.AIR));
							}else {
								this.getMenu().setItem(slot, ItemManager.createItem(Material.ENDER_CHEST, "&dAdd Reward", lore));
								checkedrew = true;
							}
						}
					}else {
						if (checkedrew == true) {
							this.getMenu().setItem(slot, new ItemStack(Material.AIR));
						}else {
							this.getMenu().setItem(slot, ItemManager.createItem(Material.ENDER_CHEST, "&dAdd Reward", lore));
							checkedrew = true;
						}
					}
				}
				
				// Requirement
				else if (coloumn > 1 && coloumn < 5 && (row > 0 && row < 3)) {
					if (numreq >= 0) {
						String req = JobsRequirement.values()[numreq].toString().substring(0, 1) + JobsRequirement.values()[numreq].toString().substring(1).toLowerCase();
						String path = this.jobsname + ".Requirement." + req;
						lore.add(" ");
						switch(req) {
						case("Money"):
							lore.add("&8- &b$" + config.getDouble(path + ".amount"));
							lore.add("&8- &3Take Money&8: &f" + (config.getBoolean(path + ".take") ? "&cYes" : "&aNo"));
							break;
						case("Level"):
							lore.add("&8- &6Level &e" + config.getInt(path + ".amount"));
							lore.add("&8- &3Take Level&8: &f" + (config.getBoolean(path + ".take") ? "&cYes" : "&aNo"));
							break;
						default:
							if (config.getStringList(path).size() > 0) {
								for (String l : config.getStringList(path)) {
									lore.add("&7- &f" + l);
								}
							}else {
								lore.add("&8- &7No Requirement");
							}
							break;
						}
						this.getMenu().setItem(slot, ItemManager.setLore(JobsRequirement.valueOf(req.toUpperCase()).getIcon(), lore));
						numreq--;
					}else {
						this.getMenu().setItem(slot, new ItemStack(Material.AIR));
					}
				}
				
				else {
					this.getMenu().setItem(slot, ItemManager.createItem(Material.STAINED_GLASS_PANE, 1, (short) 15, " ", lore));
				}
			}
		}
	}
	
	// config data getter

	// built in data getter
	public JobsCreateMode getMode(){
		return this.mode;
	}
	public String getPrefix() {
		return inventoryprefix;
	}
	public String getJobsName() {
		return this.jobsname;
	}
	
	public JobsCreate getJobsCreateData() {
		return this.jc;
	}
}
