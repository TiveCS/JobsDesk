package team.creativecode.jobsdesk.util;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {
	
	public static ItemStack createItem(Material mat, int amount, short data, String displayname, List<String> lore) {
		ItemStack item = new ItemStack(mat, amount, data);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
		for (int i = 0; i < lore.size(); i++) {
			lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack createItem(ItemStack item, ItemMeta meta) {
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createItem(ItemStack item, String displayname, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
		for (int i = 0; i < lore.size(); i++) {
			lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack createItem(Material mat, String displayname, List<String> lore) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
		for (int i = 0; i < lore.size(); i++) {
			lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}

}
