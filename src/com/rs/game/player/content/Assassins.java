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

public class Assassins implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8035331642599855251L;
	private transient Player player;
	private Tasks currentTask;
	private int taskAmount;
	private int taskSpeed;
	private int taskMode;
	private int taskWeapon;
	private int killedTasks;
	public static int[] weapons = { 1319, 1333, 1303, 1215, 11694, 11696, 11698, 11700, 11730, 4587, 1377, 1434, 9185, 4718 };
	public static int[] ranged = { 4734, 9181, 9183, 9185, 859, 861, 868, 11230, 14684 };
	
	public enum Tasks {
		GLACOR(14301, 15, 35, 140, 130),
		KING_BLACK_DRAGON(50, 15, 40, 100, 150),
		BLINK(12878, 10, 30, 220, 280),
		YK_LAGOR(11872, 5, 20, 350, 1200),
		SUNFREET(15222, 5, 20, 280, 360),
		WILDY_WYRM(3334, 15, 30, 220, 400),
		CORPOREAL_BEAST(8133, 10, 20, 300, 660),
		NEX(13447, 1, 5, 1000, 2700),
		GENERAL_GRAARDOR(6260, 10, 25, 250, 300),
		COMMANDER_ZILYANA(6247, 10, 25, 250, 300),
		KRIL_TSUTSAROTH(6203, 10, 25, 250, 300),
		KREE_ARRA(6222, 10, 25, 250, 300);
	
		private int id;
		private int min;
		private int max;
		private int xp;
		private int speed;
	
		private Tasks(int id, int min, int max, int xp, int speed) {
			this.id = id;
			this.min = min;
			this.max = max;
			this.xp = xp;
			this.speed = speed;
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
		
		public int getSpeed() {
			return speed;
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
	
	public int getSpeed() {
		return taskSpeed;
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
    	player.sm("You only have about "+taskAmount+" left to go!");
    	player.getSkills().addAssassinXp(getGameMode(), addExp());
    	if (taskAmount <= 0) {
    		player.sm("You have successfully completed your task and have receieved an Assassin's Favor Token.");
    		player.getSkills().addAssassinXp(0, addExp()*5);
    		player.getInventory().addItem(29920, 1);
    		resetTask();
    	}
    }
    
    public void speedTask() {
    	WorldTasksManager.schedule(new WorldTask() {
    		int loop = taskSpeed;
    		@Override
    		public void run() {
    			if (loop <= 0 || taskAmount <= 0) {
    				stop();
    				player.getInterfaceManager().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 34 : 0, 57);
    				player.getInterfaceManager().closeOverlay(false);
    			}
    			loop--;
    			taskSpeed--;
    			if (taskAmount > 0)
    			player.getPackets().sendIComponentText(57, 1, "You currently have "+taskSpeed+" seconds left to kill "+taskAmount+" "+getName()+".");
    		}
    	}, 0, 1);
    }
    
    public void activateStealthMoves() {
    	player.sm("Your stealth moves ability will last for 30 minutes.");
    	player.used4 = true;
    	player.stealth = true;
    	WorldTasksManager.schedule(new WorldTask() {
    		int loop = 2400;
    		@Override
    		public void run() {
    			if (loop == 1800) {
    				player.sm("Your ability has run out, please let it cool down.");
    				player.stealth = false;
    			} else if (loop == 1) {
    				player.sm("Your ability has cooled down, you may now activate it again.");
    				player.used4 = false;
    			}
    			loop--;
    		}
    	}, 0, 1);
    }
    
    public void activateSwiftSpeed() {
    	player.sm("Your swift speed ability will last for 10 minutes.");
    	player.used3 = true;
    	player.swiftness = true;
    	final int time = player.getSkills().getAssassinLevel(Skills.SWIFT_SPEED)*30;
    	WorldTasksManager.schedule(new WorldTask() {
    		int loop = time;
    		@Override
    		public void run() {
    			if (loop == (time / 2)) {
    				player.sm("Your ability has run out, please let it cool down.");
    				player.swiftness = false;
    			} else if (loop == 1) {
    				player.sm("Your ability has cooled down, you may now activate it again.");
    				player.used3 = false;
    			}
    			loop--;
    		}
    	}, 0, 1);
    }
    
    public void activateFinalBlow() {
    	player.sm("Your final blow ability will last for 10 minutes.");
    	player.used2 = true;
    	player.finalblow = true;
    	WorldTasksManager.schedule(new WorldTask() {
    		int loop = 1200;
    		@Override
    		public void run() {
    			if (loop == 600) {
    				player.sm("Your ability has run out, please let it cool down.");
    				player.finalblow = false;
    			} else if (loop == 1) {
    				player.sm("Your ability has cooled down, you may now activate it again.");
    				player.used2 = false;
    			}
    			loop--;
    		}
    	}, 0, 1);
    }
    
    public void activateCallAssassin() {
    	player.sm("You have successfully called an assassin.");
    	Pouches pouches = Pouches.forId(8422);
    	Summoning.spawnFamiliar(player, pouches);
    	player.used1 = true;
    	WorldTasksManager.schedule(new WorldTask() {
    		int loop = 6000;
    		@Override
    		public void run() {
    			if (loop == 1) {
    				player.sm("Your ability has cooled down, you may now activate it again.");
    				player.used1 = false;
    			}
    			loop--;
    		}
    	}, 0, 1);
    }
    
    public void setCurrentTask(Tasks task, int amount) {
    	this.currentTask = task;
    	this.taskAmount = amount;
    }
	
	public void getTask(int mode) {
		if (currentTask == null) {
		    int pick = new Random().nextInt(Tasks.values().length);
			final Tasks task = Tasks.values()[pick];
			int amount = Misc.random(task.getMin(), task.getMax());
			this.taskMode = mode;
			setCurrentTask(task, amount);
			if (mode == 1) {
				this.taskAmount = taskAmount*5;
			} else if (mode == 2) {
				if (currentTask.getId() == 6222)
					this.taskWeapon = ranged[Misc.random(ranged.length)];
				else
					this.taskWeapon = weapons[Misc.random(weapons.length)];
			} else if (mode == 3) {
				this.taskSpeed = taskAmount*currentTask.getSpeed();
				speedTask();
				player.getInterfaceManager().sendCountDown();
			} else if (mode == 4) {
				this.killedTasks = taskAmount;
			}
		} else {
			player.sm("You already have a task.");
		}
		return;
	}

}
