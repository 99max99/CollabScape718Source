package com.rs.game.player.content;

import java.io.Serializable;
import java.util.Random;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.player.Player;
import com.rs.utils.Misc;

public class Skillers implements Serializable {
	private static final long serialVersionUID = -8035331642599855251L;
	private transient Player player;
	private SkillerTasks currentTask;
	private int taskAmount;

	
	public enum SkillerTasks { 
		// MINING \\
		 Mine_copper(14, 1, 50, 102, 800, 436 ),
		 Mine_tin(14, 1, 49, 108, 800, 438 ),
		 Mine_iron(14, 15, 51, 106, 800, 440 ),
		 Mine_mithral(14, 55, 52, 86, 800, 447 ),
		 Mine_adamantite(14, 70,  34, 75, 800, 449 ), 
		 Mine_rune(14, 85, 24, 37, 800, 451 ),
		// FIREMAKING \\		 
		 Burn_Logs(11, 1, 50, 100, 800, 1511 ), 
		 Burn_oakLogs(11, 15, 50, 100, 800, 1521 ),
		 Burn_willowLogs(11, 30, 50, 100, 800, 1519 ),
		 Burn_mapleLogs(11, 45,  50, 100, 800, 1517 ),
		 Burn_yewLogs(11, 60, 50, 100, 800, 1515 ),
		 Burn_magicLogs(11, 75, 50, 100, 800, 1513 ), 
		// WOODCUTTING \\ 
		 Cut_logs(8, 1, 50, 100, 800, 1511 ),
		 Cut_oaklogs(8, 15, 50, 100, 800, 1521 ),
		 Cut_wiilowlogs(8, 30, 50, 100, 800, 1519 ),
		 Cut_maplelogs(8, 45, 50, 100, 800, 1517 ), 
		 Cut_yewlogs(8, 60, 50, 100, 800, 1515 ),
		 Cut_magiclogs(8, 75, 50, 100, 800, 1513 ),
		 // COOKING \\
		 Cook_shrimp(7, 1, 50, 100, 800, 315),
		 Cook_lobster(7, 40, 50, 100, 800, 379),
		 Cook_swordfish(7, 45, 50, 100, 800, 373),
		 Cook_shark(7, 80, 50, 100, 800, 385),
		 // FISHING \\	 
		 Fish_shrimp(10, 1, 50, 100, 800, 317),		 
		 Fish_anchovies(10, 15, 50, 100, 800, 321),		 
		 Fish_lobster(10, 40, 50, 100, 800,377),
		 Fish_swordfish(10, 35, 50, 100, 800, 371),		 
		 Fish_shark(10, 76, 15, 35, 800, 383),
		 // SMITHING \\
		 Smith_Bronzebar(13, 1, 45, 104, 800, 2349),
		 Smith_Ironbar(13, 15, 52, 102, 800, 2351),
		 Smith_Steelbar(13, 30, 51, 99, 800, 2353),
		 Smith_Mithbar(13, 50, 57, 102, 800, 2359),
		 Smith_Addybar(13, 70, 46, 82, 800, 2361),
		 Smith_Runebar(13, 85, 9, 16, 800, 2363),
		 // THEIVING \\ 
		 Steal_CraftingStall(17, 1, 45, 104, 800, 4874),
		 Steal_SilkStall(17, 20, 52, 102, 800, 34383),
		 Steal_WineStall(17, 22, 51, 99, 800, 14011),
		 Steal_FurStall(17, 35, 57, 102, 800, 34387),
		 Steal_SpiceStall(17, 65, 46, 100, 800, 34386),
		 Steal_GemStall(17, 75, 50, 100, 800, 34385),
		 Steal_ScimmyStall(17, 80, 50, 100, 800, 4878),
		 
		 
		 
		 
		 ;
		
	
		
		
		private int id;
		private int min;
		private int max;
		private int xp;
		private int levelRequried;
		private int skill;

	
		private SkillerTasks(int skill, int levelRequried, int min, int max, int xp, int id) {
		    this.levelRequried = levelRequried;
		    this.skill = skill;
			this.min = min;
			this.max = max;
			this.id = id;
			this.xp = xp;
		}
		
		public int getId() {
			return id;
		}
		
		public int getMin() {
			return min;
		}
		
		public int getMax() {
			return max;
		}
		
		public int getXp() {
			return xp;
		}
		public int getSkill() {
			return skill;
		}
		public int getlvlRequired() {
			return levelRequried;
		}	
	}
	
	public int getNpcId() {
		return currentTask.getId();
	}
	
	public double addExp() {
		return currentTask.getXp();
	}
	public int addSkill() {
		return currentTask.getSkill();
	}
	
	public SkillerTasks getTask() {
		return currentTask;
	}
	
	public int getAmount() {
		return taskAmount;
	}
	
	public String getName() {
		int i = getNpcId();
		ItemDefinitions def = ItemDefinitions.getItemDefinitions(i);
		return def.getName();
	}
	public String getNames() {
		int i = getNpcId();
		ObjectDefinitions def = ObjectDefinitions.getObjectDefinitions(i);
		return def.getName();
	}
	
    public void setPlayer(Player player) {
    	this.player = player;
    }
    
    public void resetTask() {
    	setCurrentTask(null, 0);
    }
    public void typeTask() {
    	if(player.TASKID == 11) {
    		player.sendMessage("<col=FF6600>Your daily task has been reset, and to complete it...");
	    	player.sendMessage("<col=FF6600>You must burn "+player.getSkillersManager().getAmount()+" "+player.getSkillersManager().getName()+".");
	    	player.skillingtask = 11;
	    	} else if(player.TASKID == 14) {
	    		 player.sendMessage("<col=FF6600>Your daily task has been reset, and to complete it...");
	    		 player.sendMessage("<col=FF6600>You must mine "+player.getSkillersManager().getAmount()+" "+player.getSkillersManager().getName()+"'s.");
	    	}else if(player.TASKID == 8) {
	    		player.skillingtask = 8;
	    		player.sendMessage("<col=FF6600>Your daily task has been reset, and to complete it...");
	    		 player.sendMessage("<col=FF6600>You must cut "+player.getSkillersManager().getAmount()+" "+player.getSkillersManager().getName()+".");
	    	}else if(player.TASKID == 7) {
	    		player.sendMessage("<col=FF6600>Your daily task has been reset, and to complete it...");
	    		 player.sendMessage("<col=FF6600>You must cook "+player.getSkillersManager().getAmount()+" "+player.getSkillersManager().getName()+"'s.");
	    	}else if(player.TASKID == 10) {
	    		player.sendMessage("<col=FF6600>Your daily task has been reset, and to complete it...");
	    		 player.sendMessage("<col=FF6600>You must fish "+player.getSkillersManager().getAmount()+" "+player.getSkillersManager().getName()+"'s.");
	    	}else if(player.TASKID == 13) {
	    		player.sendMessage("<col=FF6600>Your daily task has been reset, and to complete it...");
	    		 player.sendMessage("<col=FF6600>You must smelt "+player.getSkillersManager().getAmount()+" "+player.getSkillersManager().getName()+"'s.");
	    	}else if(player.TASKID == 17) {
	    		player.sendMessage("<col=FF6600>Your daily task has been reset, and to complete it...");
	    		 player.sendMessage("<col=FF6600>You must steal "+player.getSkillersManager().getAmount()+" times from a "+player.getSkillersManager().getNames()+".");
	    	}
		
    }
    public void ADDNEWDAILY() {
		if(player.hasdaily == true) {
		player.dailyhasTask = false;
		player.hasdaily = false;
		player.TASKID = -1;
    	player.getSkillersManager().resetTask();
		} else if(player.hasdaily != true){	
    	player.getSkillersManager().getTask(0);
    	player.getSkillersManager().typeTask();
		}
    	if(player.givendaily == true) {
    		player.getSkillersManager().typeTask();
    		player.givendaily = false;
    	}
    }
    public void completeTask() {
    	this.taskAmount--;
    	player.dailyamount = this.taskAmount;
    	if(player.TASKID == 17) {
        	player.sm("<col=FF6600>You only have to steal "+taskAmount+ " times more to complete your daily task!");

    	} else {
    	player.sm("<col=FF6600>You only have "+taskAmount+ " " +player.getSkillersManager().getName()+ "s left to go to complete your daily task!");
    	}
    	if (taskAmount <= 0) {
    		player.sm("<col=FF6600>You have successfully completed your daily task!");
    		player.sm("<col=FF6600>2.5m Gold has been added to your bank, and you have been rewarded extra exp!");
    		player.getBank().addItem(995, 2500000, true);
    		player.getSkills().addXp(addSkill(), addExp());
    		resetTask();
    		player.dailyhasTask = false;
    		player.dailyamount = 0;
    		player.dailyTask = null;
    		player.TASK = -1;
    		player.TASKID = -1;
    	}
    }
    public void setCurrentTask(SkillerTasks task, int amount) {
    	this.currentTask = task;
    	this.taskAmount = amount;
    }
	public void getTask(int mode) {
		
		if (currentTask == null && player.dailyhasTask!=true) {
		    int pick = new Random().nextInt(SkillerTasks.values().length);
			final SkillerTasks task = SkillerTasks.values()[pick];
			int amount = Misc.random(task.getMin(), task.getMax());
			setCurrentTask(task, amount);
			player.dailyhasTask=true;
			player.dailyamount = amount;
			player.dailyTask = task;	
		} else {
			player.sm("You already have a task.");
			setCurrentTask(player.dailyTask, player.dailyamount);
			player.TASK = getNpcId();
			player.TASKID = addSkill();
		}
		return;
	}
}
