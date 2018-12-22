package team.creativecode.jobsdesk.menu.admin;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import team.creativecode.jobsdesk.menu.MenuManager;

public class JobsCreateMenu extends MenuManager {

	/*Structure Example
	 * 
	 * Jobs:
	 * 		Phases:
	 * 			1:
	 * 				1:
	 * 					type: DELIVER
	 * 					value: item_here
	 * 				2:
	 * 					type: KILL
	 * 					value: PIG-20 #ENTITY-KILL_AMOUNT
	 * 
	 * */
	
	public enum PhaseTypeData{
		TYPE, VALUE, CUSTOM_DESCRIPTION;
	}
	
	public enum PhaseType{
		DELIVER, KILL;
	}
	
	HashMap<Integer, HashMap<Integer, HashMap<PhaseTypeData, Object>>> phases = new HashMap<Integer, HashMap<Integer, HashMap<PhaseTypeData, Object>>>();
	
	public JobsCreateMenu(Player p, MenuCategory category, int page) {
		super(p, category, page);
	}

	@Override
	public void updateMenu(int page) {
		
	}

	@Override
	public void action(Player executor, int clickedSlot) {
		
	}

	@Override
	public void loadMenu(int page, Inventory menu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeMenu() {
		// TODO Auto-generated method stub
		
	}
	
	

}
