package team.creativecode.jobsdesk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import team.creativecode.jobsdesk.cmds.JobsDeskAdminCmd;
import team.creativecode.jobsdesk.cmds.JobsDeskCmd;
import team.creativecode.jobsdesk.events.BasicEvent;
import team.creativecode.jobsdesk.manager.JobsCreate;

public class Main extends JavaPlugin {
	
	public static Economy economy = null;
	
	public void onEnable() {
		
		loadConfig();
		
		if (!setupEconomy()) {
			System.out.println("[JobsDesk] Failed to hook with Vault");
			this.getServer().getPluginManager().disablePlugin(this);
		}else {
			System.out.println("[JobsDesk] Success to hook with Vault");
		}
		
		loadCmds();
		loadEvents();
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		ConfigManager.createFile(JobsCreate.file);
	}
	
	public void loadEvents() {
		getServer().getPluginManager().registerEvents(new BasicEvent(), this);
	}
	
	public void loadCmds() {
		this.getCommand("jobsdesk").setExecutor(new JobsDeskCmd());
		this.getCommand("jobsdeskadmin").setExecutor(new JobsDeskAdminCmd());
	}
	
	private boolean setupEconomy() {
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		
		economy = rsp.getProvider();
		return economy != null;
	}

}
