package team.creativecode.jobsdesk.enums;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import team.creativecode.jobsdesk.util.ItemManager;

public class JobsCreateEnum {
	
	public enum InputType{
		DECIMAL(new String[] {"&7Please input Decimal number...", "&7type &cCancel &7to cancel"}),
		INTEGER(new String[] {"&7Please input Integer number...", "&7type &cCancel &7to cancel"}),
		STRING(new String[] {"&7Please input Text...", "&7type &bDone &7to finish", "&7type &cCancel &7to cancel"}),
		LIST(new String[] {"&7Please input Text...", "&7type &bDone &7to finish", "&7type &cCancel &7to cancel"}),
		ITEM(new String[] {"&&Please prepare Item on main hand...", "&7type &aAdd &7to add item", "&7type &bDone &7to finish", "&7type &cCancel &7to cancel"});
		
		String[] msg;
		
		InputType(String[] message){
			this.msg = message;
		}
		
		public String[] getMessages() {
			return this.msg;
		}
	}
	
	public enum JobsRequirement{
		MONEY(Material.EMERALD, InputType.DECIMAL),
		LEVEL(Material.EXP_BOTTLE, InputType.INTEGER),
		PERMISSION(Material.SIGN, InputType.LIST),
		JOBS(Material.FISHING_ROD, InputType.LIST);
		
		ItemStack icon;
		InputType type;
		JobsRequirement(Material mat, InputType type){
			this.icon = ItemManager.createItem(mat, "&8[&cReq.&8] &f" + (this.toString().substring(0, 1) + this.toString().toLowerCase().substring(1)), new ArrayList<String>());
			this.type = type;
		}
		
		public ItemStack getIcon() {
			return this.icon;
		}
		
		public InputType getInputType() {
			return this.type;
		}
	}
	
	public enum JobsReward{
		MONEY(Material.EMERALD, InputType.DECIMAL),
		LEVEL(Material.EXP_BOTTLE, InputType.INTEGER),
		PERMISSION(Material.SIGN, InputType.LIST),
		ITEMS(Material.CHEST, InputType.ITEM);
		
		ItemStack icon;
		InputType type;
		JobsReward(Material mat, InputType type){
			this.icon = ItemManager.createItem(mat, "&8[&aReward&8] &f" + (this.toString().substring(0, 1) + this.toString().toLowerCase().substring(1)), new ArrayList<String>());
			this.type = type;
		}
		
		public ItemStack getIcon() {
			return this.icon;
		}
		
		public InputType getInputType() {
			return this.type;
		}
	}
	
	public enum PhaseDefaultAttribute {
		ENABLE(false);
		
		Object value;
		PhaseDefaultAttribute(Object obj){
			this.value = obj;
		}
		
		public Object getValue() {
			return this.value;
		}
	}
	
	public enum Objective{
		DELIVER, KILL;
	}
	
	public enum ObjectiveData{
		// Main data
		OBJECTIVE, OBJECTIVE_TYPE,
		
		// Item data
		ITEM, ITEM_MATERIAL, ITEM_NAME, ITEM_AMOUNT, ITEM_ENCHANT, ITEM_LORE,
		
		// Entity data
		ENTITY_TYPE, ENTITY_DISPLAY_NAME, ENTITY_KILL_AMOUNT;
		;
	}
	
	public enum ObjectiveType{
		
		ENTITY(new Objective[] {Objective.KILL}),
		ITEM(new Objective[] {Objective.DELIVER});
		
		
		//-------------------------------
		Objective[] allowedObjective;
		List<ObjectiveData> objectiveData = new ArrayList<ObjectiveData>();
		ObjectiveType(Objective[] allowed){
			this.allowedObjective = allowed;
			addMainObjectiveData();
			
			for (ObjectiveData dat : ObjectiveData.values()) {
				try {
					if (dat.toString().startsWith(this.toString())) {
						this.objectiveData.add(dat);
					}
				}catch(Exception e) {e.printStackTrace();}
			}
		}
		
		void addMainObjectiveData() {
			this.objectiveData.add(ObjectiveData.OBJECTIVE);
			this.objectiveData.add(ObjectiveData.OBJECTIVE_TYPE);
		}
		
		public List<ObjectiveData> getObjectiveData() {
			return this.objectiveData;
		}
		
		public Objective[] getAllowedObjective() {
			return this.allowedObjective;
		}
	}

}
