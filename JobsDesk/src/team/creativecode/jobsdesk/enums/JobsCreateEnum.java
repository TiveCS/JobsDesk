package team.creativecode.jobsdesk.enums;

import java.util.ArrayList;
import java.util.List;

public class JobsCreateEnum {
	
	public enum JobsRequirement{
		MONEY, PERMISSION, LEVEL, JOBS;
	}
	
	public enum JobsReward{
		MONEY, LEVEL, PERMISSION, ITEM;
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
