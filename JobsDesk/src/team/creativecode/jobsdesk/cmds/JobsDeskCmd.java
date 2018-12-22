package team.creativecode.jobsdesk.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.creativecode.jobsdesk.menu.MenuManager;
import team.creativecode.jobsdesk.menu.MenuManager.MenuCategory;
import team.creativecode.jobsdesk.menu.individual.MainMenu;

public class JobsDeskCmd implements CommandExecutor {

	public void help(Player p, int page, String label) {
		label = label.equals(null) ? "&3/jobsdesk" : "&3/" + label;
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eJobsDesk help page &f" + page + " &8/ &71"));
		p.sendMessage(" ");
		switch(page) {
		case 1:
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', label + " &bjoin &f[Text] &8- &7Join a jobdesk"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', label + " &bquit &f[Text] &8- &7Quit a jobdesk"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', label + " &binfo &8- &7Open your personal info"));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', label + " &blist &f[Number] &8- &7Open menu of listed jobs"));
			break;
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("jobsdesk")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equals("?"))) {
					help(p, 1, label);
					return true;
				}
				
				if (args.length == 0) {
					MenuManager mm = new MainMenu(p, MenuCategory.MAIN, 1);
					mm.loadMenu(1, mm.getMenu());
					mm.openMenu();
					return true;
				}
				
			}
		}
		
		
		return false;
	}
	
	

}
