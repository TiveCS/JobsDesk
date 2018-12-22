package team.creativecode.jobsdesk.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import team.creativecode.jobsdesk.menu.MenuManager;
import team.creativecode.jobsdesk.menu.individual.MainMenu;

public class BasicEvent implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.isCancelled()) {
			if (MenuManager.viewer.containsKey(event.getWhoClicked())) {
				MenuManager menu = MainMenu.viewer.get(event.getWhoClicked());
				menu.action((Player) event.getWhoClicked(), event.getSlot());
				event.setCancelled(true);
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

}
