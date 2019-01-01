package team.creativecode.jobsdesk.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.Material;
import team.creativecode.jobsdesk.enums.JobsCreateEnum.InputType;
import team.creativecode.jobsdesk.menu.MenuManager;
import team.creativecode.jobsdesk.menu.admin.JobsCreateMenu;
import team.creativecode.jobsdesk.menu.admin.JobsCreateMenu.JobsCreateMode;
import team.creativecode.jobsdesk.menu.individual.MainMenu;
import team.creativecode.jobsdesk.util.MsgManager;

public class BasicEvent implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.isCancelled()) {
			if (MenuManager.viewer.containsKey(event.getWhoClicked())) {
				MenuManager menu = MainMenu.viewer.get(event.getWhoClicked());
				if (event.getClickedInventory().equals(menu.getMenu())) {
					menu.action((Player) event.getWhoClicked(), event.getSlot());
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (MenuManager.viewer.containsKey(event.getPlayer())) {
			MenuManager mm = MenuManager.viewer.get(event.getPlayer());
			if (mm.getMenu().equals(event.getInventory())) {
				mm.closeMenu();
			}
		}
	}
	
	@SuppressWarnings("unused")
	@EventHandler
	public void onChatInput(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		String msg = event.getMessage();
		
		if (MenuManager.input.containsKey(p) && MenuManager.viewer.containsKey(p)) {
			if (MenuManager.viewer.get(p) instanceof JobsCreateMenu) {
				InputType input = MenuManager.input.get(p);
				JobsCreateMenu mm = (JobsCreateMenu) MenuManager.viewer.get(p);
				event.setCancelled(true);
				String inputPath = mm.inputPath.get(p).substring(0, 1) + mm.inputPath.get(p).toLowerCase().substring(1);
				String inputType = mm.getMode().name();
				inputType = inputType.replace("INPUT_", "");
				inputType = inputType.substring(0, 1) + inputType.substring(1).toLowerCase();
				
				boolean hasValue = false;
				int integer = 0;
				double decimal = 0.0;
				String text = "";
				List<String> list = new ArrayList<String>();
				List<ItemStack> item = new ArrayList<ItemStack>();
				
				if (msg.equalsIgnoreCase("Done")) {
					for (ItemStack it : item) {
						if (mm.getMode().equals(JobsCreateMode.INPUT_REWARD)) {
							mm.getJobsCreateData().addRewardItem(it);
						}
					}
					for (String lit : list) {
						if (mm.getMode().equals(JobsCreateMode.INPUT_REQUIREMENT)) {
							if (inputPath.equalsIgnoreCase("Permission")) {
								mm.getJobsCreateData().addRequiredPermission(lit);
							}else if (inputPath.equalsIgnoreCase("Jobs")) {
								mm.getJobsCreateData().addRequiredJobs(lit);
							}
						}else if (mm.getMode().equals(JobsCreateMode.INPUT_REWARD)) {
							if (inputPath.equalsIgnoreCase("Permission")) {
								mm.getJobsCreateData().addRewardPermission(lit);
							}
						}
					}
					
					if (inputPath.equalsIgnoreCase("Level")) {
						if (mm.getMode().equals(JobsCreateMode.INPUT_REQUIREMENT)) {
							mm.getJobsCreateData().setRequiredLevel(integer, false);
						}else if (mm.getMode().equals(JobsCreateMode.INPUT_REWARD)) {
							mm.getJobsCreateData().setRewardLevel(integer);
						}
					}
					
					if (inputPath.equalsIgnoreCase("Money")) {
						if (mm.getMode().equals(JobsCreateMode.INPUT_REQUIREMENT)) {
							mm.getJobsCreateData().setRequiredMoney(decimal, false);
						}else if (mm.getMode().equals(JobsCreateMode.INPUT_REWARD)) {
							mm.getJobsCreateData().setRewardMoney(decimal);
						}
					}
					mm.openMenu();
					return;
				}else if (msg.equalsIgnoreCase("Cancel")) {
					mm.openMenu();
					return;
				}else if (msg.equalsIgnoreCase("Add")) {
					switch(input) {
					case ITEM:
						if (!p.getInventory().getItemInMainHand().getType().toString().equals(Material.AIR.toString())) {
							item.add(p.getInventory().getItemInMainHand());
						}else {
							MsgManager.sendTranslateMessage(p, "&cCannot set item to Air");
						}
						break;
					default:
						break;
					}
				}
				else {
					try {
						switch(input) {
						case DECIMAL:
							decimal = Double.parseDouble(msg);
							break;
						case INTEGER:
							integer = Integer.parseInt(msg);
							break;
						case LIST:
							list.add(ChatColor.translateAlternateColorCodes('&', msg));
							break;
						case STRING:
							text = ChatColor.translateAlternateColorCodes('&', msg);
							break;
						default:
							break;
						}
						hasValue = true;
					}catch(Exception e) {
						MsgManager.sendTranslateMessage(p, "&cPlease input the correct value...");
					}
				}
			}
		}
	}
	

}
