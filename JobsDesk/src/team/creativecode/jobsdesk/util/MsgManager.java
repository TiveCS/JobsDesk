package team.creativecode.jobsdesk.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TranslatableComponent;

public class MsgManager {

	public static TranslatableComponent translate(String text) {
		TranslatableComponent com = new TranslatableComponent(ChatColor.translateAlternateColorCodes('&', text));
		return com;
	}
	
	public static void sendTranslateMessage(Player p, String text) {
		p.spigot().sendMessage(translate(text));
	}
	
}
