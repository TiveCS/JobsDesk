package team.creativecode.jobsdesk.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.creativecode.jobsdesk.enums.JobsCreateEnum.Objective;
import team.creativecode.jobsdesk.enums.JobsCreateEnum.ObjectiveType;
import team.creativecode.jobsdesk.manager.JobsCreate;
import team.creativecode.jobsdesk.menu.MenuManager;
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
						JobsCreate jc = new JobsCreate(args[1]);
						MenuManager mm = new JobsCreateMenu(p, MenuCategory.CREATE_JOBS, 1, jc);
						jc.setCreator((Player) sender);
						jc.register();
						jc.addObjective(1, 1, ObjectiveType.ITEM, Objective.DELIVER, null);
						mm.openMenu();
						return true;
					}
				}
			}
		}
		return false;
	}

}
