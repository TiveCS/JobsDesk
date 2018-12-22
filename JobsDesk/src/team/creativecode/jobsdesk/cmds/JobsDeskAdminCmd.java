package team.creativecode.jobsdesk.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.creativecode.jobsdesk.menu.MenuManager.MenuCategory;
import team.creativecode.jobsdesk.menu.admin.JobsCreateMenu;

public class JobsDeskAdminCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("jobsdeskadmin")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("create") && args[1].length() > 0) {
						JobsCreateMenu mm = new JobsCreateMenu(p, MenuCategory.CREATE_JOBS, 1);
						mm.setMenu(Bukkit.createInventory(null, 5*9, "Jobs Create > " + args[1]));
						mm.updateMenu(1);
						return true;
					}
				}
			}
		}
		return false;
	}

}
