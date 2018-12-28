package team.creativecode.jobsdesk.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.creativecode.jobsdesk.ConfigManager;
import team.creativecode.jobsdesk.Main;
import team.creativecode.jobsdesk.enums.JobsCreateEnum.Objective;
import team.creativecode.jobsdesk.enums.JobsCreateEnum.ObjectiveData;
import team.creativecode.jobsdesk.enums.JobsCreateEnum.ObjectiveType;
import team.creativecode.jobsdesk.util.MsgManager;

public class JobsCreate {
	
	/*Structure Example
	 * 
	 * JobsName:
	 * 		Repeat: <true/false>
	 * 		Repeat-Cooldown: <timespan> #example: 3d 2h 24m 30s ##output: cooldown 3 day 2 hours 24 minutes and 30 second
	 * 		Requirement:
	 * 			Permission:
	 * 				- requirement.permission.1
	 * 				- requirement.permission.2
	 * 			Money:
	 * 				amount: <double>
	 * 				take: <boolean>
	 * 			Jobs:
	 * 				- JobsName_1
	 * 				- JobsName_2
	 * 			Level:
	 * 				amount: <integer>
	 * 				take: <boolean>
	 * 		Phases:
	 * 			<Phase number>:
	 * 				enable: <boolean>
	 * 				<Objective Number>:
	 * 					objective: DELIVER
	 * 					objective-type: ITEM
	 * 					item-material: material_here 
	 * 					item-amount: 12 #use integer here
	 * 					item-name: item_name_here
	 * 					item-enchantment: ENCHANT-LEVEL, ENCHANT2-LEVEL, ...
	 * 					item-lore: "lore line 1||lore line 2 was here!!!"
	 * 					item: all in one item data #can use this or above
	 * 				2:
	 * 					objective: KILL
	 * 					objective-type: ENTITY
	 * 					entity-type: PIG
	 * 					entity-display-name: 'display name here'
	 * 					entity-kill-amount: 20 #use integer here
	 * 		Reward:
	 * 			Money: <money amount>
	 * 	 		Level: <exp level>
	 * 			Permission: 
	 * 				- permission.reward.1
	 * 				- permission.reward.2
	 * 			Item: 
	 * 				1:<ItemStack 1>
	 * 				2:<ItemStack 2>

	 * 					
	 * */
	
	
	public static Main plugin = Main.getPlugin(Main.class);
	public static File file = new File(plugin.getDataFolder(), "jobs.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	Player creator = null;
	String jobsname;
	String repeatcooldown = "1d 0h 0m 0s";
	boolean repeat = false;
	
	public JobsCreate(String jobsname, Player creator) {
		if (!file.exists()) {
			ConfigManager.createFile(file.getPath(), file.getName());
		}
		this.jobsname = jobsname;
		this.creator = creator;
	}
	
	public JobsCreate(String jobsname) {
		if (!file.exists()) {
			ConfigManager.createFile(file.getPath(), file.getName());
		}
		this.jobsname = jobsname;
	}

	public void register() {
		
		try {
			ConfigManager.init(file, this.jobsname + ".Creator", this.creator.getUniqueId().toString());
		}catch(Exception e) {}
		
		ConfigManager.init(file, this.jobsname + ".Repeat", this.repeat);
		ConfigManager.init(file, this.jobsname + ".Repeat-Cooldown", this.repeatcooldown);
		
		if (getPhaseSize() == 0) {
			addEmptyPhase();
		}
	}
	
	public void update() {
		try {
			ConfigManager.input(file, this.jobsname + ".Creator", this.creator.getUniqueId().toString());
		}catch(Exception e) {}
		
		ConfigManager.input(file, this.jobsname + ".Repeat", this.repeat);
		ConfigManager.input(file, this.jobsname + ".Repeat-Cooldown", this.repeatcooldown);
		
		if (getPhaseSize() == 0) {
			addEmptyPhase();
		}
	}
	
	//------------------------------------------------------------------------
	// objective manager
	public void addObjective(int phasenumber, int objectivenumber, ObjectiveType type, Objective obj, HashMap<ObjectiveData, Object> data) {
		if (checkAllowedObjective(type, obj) == true) {
			System.out.println(type.getObjectiveData());
		}else {
			if (!getCreator().equals(null)) {
				MsgManager.sendTranslateMessage(this.creator, ChatColor.translateAlternateColorCodes('&', "&cCould not add objective &a" + obj.toString() + " &cwith &b" + type.toString()));
			}
		}
	}
	
	//------------------------------------------------------------------------
	// phase manager
	
	public void addEmptyPhase() {
		int size = 0;
		try {
			size = config.getConfigurationSection(this.jobsname + ".Phases").getKeys(false).size();
		}catch(Exception e) {}
		
		ConfigManager.input(file, this.jobsname + ".Phases." + (size + 1) + ".enable", false);
	}
	
	//------------------------------------------------------------------------
	// reward setup
	
	public void deleteRewardItem(int index) {
		setRewardItem(null, index);
	}
	
	public void deleteRewardPermission(int index) {
		List<String> list = new ArrayList<String>(config.getStringList(this.jobsname + ".Rewards.Permissions"));
		if (!list.get(index - 1).isEmpty()) {
			list.remove(index - 1);
		}
		setRewardPermission(list);
	}
	
	public void addRewardItem(ItemStack item) {
		String ind = "" + (config.getConfigurationSection(this.jobsname + ".Rewards.Items").getKeys(false).size() + 1);
		ConfigManager.input(file, this.jobsname + ".Rewards.Items." + ind, item);
	}
	
	public void addRewardPermission(String perm) {
		List<String> list = new ArrayList<String>(config.getStringList(this.jobsname + ".Rewards.Permissions"));
		list.add(perm);
		ConfigManager.input(file, this.jobsname + ".Rewards.Permissions", list);
	}
	
	public void setRewardItem(ItemStack item, int index) {
		ConfigManager.input(file, this.jobsname + ".Rewards.Items." + index, item);
	}
	
	public void setRewardMoney(double value) {
		ConfigManager.input(file, this.jobsname + ".Rewards.Money", value);
	}
	
	public void setRewardPermission(List<String> value) {
		ConfigManager.input(file, this.jobsname + ".Rewards.Permissions", value);
	}
	
	public void setRewardLevel(int value) {
		ConfigManager.input(file, this.jobsname + ".Rewards.Level", value);
	}
	
	//------------------------------------------------------------------------
	
	
	
	// requirement setup
	public void setRequiredJobs(List<String> jobs) {
		ConfigManager.input(file, this.jobsname + ".Requirement.Jobs", jobs);
	}
	
	public void setRequiredLevel(int level, boolean take) {
		ConfigManager.input(file, this.jobsname + ".Requirement.Level.amount", level);
		ConfigManager.input(file, this.jobsname + ".Requirement.Level.take", take);
	}
	
	public void setRequiredMoney(double amount, boolean take) {
		ConfigManager.input(file, this.jobsname + ".Requirement.Money.amount", amount);
		ConfigManager.input(file, this.jobsname + ".Requirement.Money.take", take);
	}
	
	public void setRequiredPermission(List<String> permissions) {
		ConfigManager.input(file, this.jobsname + ".Requirement.Permissions", permissions);
	}
	
	public void addRequiredPermission(String permission) {
		List<String> list = config.getStringList(this.jobsname + ".Requirement.Permissions");
		list.add(permission);
		setRequiredPermission(list);
	}
	
	public void deleteRequiredPermission(int index) {
		List<String> list = config.getStringList(this.jobsname + ".Requirement.Permissions");
		if (!list.get(index).isEmpty()) {
			list.remove(index);
		}
		setRequiredPermission(list);
	}
	
	//------------------------------------------------------------------------
	// Setter basic
	
	public void setCreator(Player p) {
		this.creator = p;
	}
	
	//------------------------------------------------------------------------
	// Checker
	
	public boolean checkAllowedObjective(ObjectiveType type, Objective obj) {
		for (Objective o : type.getAllowedObjective()) {
			if (obj.equals(o)) {
				return true;
			}
		}
		return false;
	}
	
	//------------------------------------------------------------------------
	// getter
	
	public Player getCreator() {
		return this.creator;
	}
	
	public int getPhaseSize() {
		if (ConfigManager.contains(file, this.jobsname + ".Phases")) {
			return config.getConfigurationSection(this.jobsname + ".Phases").getKeys(false).size();
		}else {
			return 0;
		}
	}
	
	public int getObjectiveSize(int phasenumber) {
		if (ConfigManager.contains(file, this.jobsname + ".Phases." + phasenumber)) {
			return config.getConfigurationSection(this.jobsname + ".Phases." + phasenumber).getKeys(false).size();
		}else {
			return 0;
		}
	}
	
	public String getRepeatCooldownRaw() {
		return this.repeatcooldown;
	}
	
	public String getJobsName() {
		return this.jobsname;
	}
}

