package com.rs.game.player.content;

import java.io.Serializable;
import java.util.Random;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Misc;

public class Deaths implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8035331642599855251L;
	private transient Player player;
	private Tasks currentTask;
	private int taskAmount;
	private int taskMode;
	private int taskWeapon;
	private int killedTasks;
	//public static int[] weapons = { 1319, 1333, 1303, 1215, 11694, 11696, 11698, 11700, 11730, 4587, 1377, 1434, 9185, 4718 };
	//public static int[] ranged = { 4734, 9181, 9183, 9185, 859, 861, 868, 11230, 14684 };
	
	public enum Tasks {
		GLACOR(14301, 15, 20, 140),
		KING_BLACK_DRAGON(50, 15, 40, 100),
		BLINK(12878, 1, 5, 220),
		YK_LAGOR(11872, 5, 20, 350),
		SUNFREET(15222, 1, 5, 280),
		WILDY_WYRM(3334, 1, 5, 220),
		CORPOREAL_BEAST(8133, 1, 3, 300),
		NEX(13447, 1, 2, 1000),
		GENERAL_GRAARDOR(6260, 10, 25, 250),
		COMMANDER_ZILYANA(6247, 10, 25, 250),
		KRIL_TSUTSAROTH(6203, 10, 25, 250),
		KREE_ARRA(6222, 10, 25, 250),
        KALPHITE_QUEEN(1160, 5, 10, 300);
	
		private int id;
		private int min;
		private int max;
		private int xp;

	
		private Tasks(int id, int min, int max, int xp) {
			this.id = id;
			this.min = min;
			this.max = max;
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
		

		
		
	}
	
	public int getNpcId() {
		return currentTask.getId();
	}
	
	public double addExp() {
		return currentTask.getXp();
	}
	
	public Tasks getTask() {
		return currentTask;
	}
	
	public int getAmount() {
		return taskAmount;
	}
	
	public int getWeapon() {
		return taskWeapon;
	}
	
	public int getKilledTasks() {
		return killedTasks;
	}
	
	public String getWeaponName() {
		int i = taskWeapon;
		ItemDefinitions def = ItemDefinitions.getItemDefinitions(i);
		return def.getName();
	}
	
	public String getName() {
		int i = getNpcId();
		NPCDefinitions def = NPCDefinitions.getNPCDefinitions(i);
		return def.getName();
	}
	
	public int getGameMode() {
		return taskMode;
	}
	
    public void setPlayer(Player player) {
    	this.player = player;
    }
    
    public void resetTask() {
    	setCurrentTask(null, 0);
    }
    
    public void completeTask() {
    	this.taskAmount--;
    	player.damount = this.taskAmount;
    	player.sm("You only have "+taskAmount+" left to go to complete your death task!");
    	if(player.DeathTask == 10 && taskAmount <= 0) {
    		player.sm("<col=FF6600>You have successfully completed your Death task as well as 10 others and have receieved 20 Death Task Points.");
    		player.getSkills().addXp(18, 800);
    		player.DeathTask = 0 ;
    		resetTask();
    		player.dhasTask = false;
    		player.damount = 0;
    		player.deathTask = null;
    		return;
    	}
    	if (taskAmount <= 0 && !(player.DeathTask == 10)) {
    		player.sm("<col=FF6600>You have successfully completed your Death task and have receieved 10 Death Task Points.");
    		player.getSkills().addXp(18, 400);
    		player.DeathTask ++ ;
    		resetTask();
    		player.dhasTask = false;
    		player.damount = 0;
    		player.deathTask = null;

    	}
    }
    
    public void setCurrentTask(Tasks task, int amount) {
    	this.currentTask = task;
    	this.taskAmount = amount;
    }
	
	public void getTask(int mode) {
		
		if (currentTask == null && player.dhasTask!=true) {
		    int pick = new Random().nextInt(Tasks.values().length);
			final Tasks task = Tasks.values()[pick];
			int amount = Misc.random(task.getMin(), task.getMax());
			//this.taskMode = mode;
			setCurrentTask(task, amount);
			player.dhasTask=true;
			player.damount = amount;
			player.deathTask = task;
		} else {
			player.sm("You already have a task.");
			setCurrentTask(player.deathTask, player.damount);
		}
		return;
	}

}
