package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

/**
 * @Danny
 * 
 *
 */
public class LividFarm {
/** Main
 * Data
 */
	public static int bucket = 20933,
			orb = 6950, logs = 1511, 
			LADY = 7530,plant = 20704, 
			bunchedplant = 20705, exp = 10,
			othskill = 10;
	/*
	 * XP Boosting rates
	 */
public static int boostedxp = 1;
	/**
	 * 
	 * @param player
	 */
	public static void TakeLogs(Player player) {
		player.sm("You have taken a log.");
		player.getInventory().addItem(logs, 1);
		player.lock(1);
		player.lividfarm = false; //<- Reseter
		player.setNextAnimation(new Animation(832));
	}
	/**
	 *
	 * @param player
	 */
	public static void MakePlants(Player player) {
	    if (player.getInventory().containsItem(bucket, 1)) {
	    player.lock(3);
        player.setNextAnimation(new Animation(4853));
	    player.sm("You have grown livid plant.");
	    player.getInventory().addItem(plant, 1);

        player.setNextAnimation(new Animation(2282));
	    player.getInventory().refresh();
	   }
	 else {
		 
	}
	     if (player.getInventory().containsItem(orb, 1)) {
	 	    player.lock(3);
		    player.sm("You have grown livid plant.");
		    player.getInventory().addItem(plant, Utils.getRandom(3));
		    player.getInventory().refresh();
		    player.setNextAnimation(new Animation(778));
		    player.setNextGraphics(new Graphics(2039));
	    } else {
	    	/*
	    	 * This is supposed to do nothing.
	    	 */
	    }
	}

	public static void bunchPlants(Player player) {
	player.getInventory().deleteItem(plant, 1);
	player.getInventory().addItem(bunchedplant, 1);
	player.getSkills().addXp(Skills.HERBLORE, othskill);
	player.getInventory().refresh();
	player.sm("You have bunched the plant.");
	}
	/**
	 * Orb: Autobunches all the plants
	 * @param player
	 */
	public static void OrbBunch(Player player) {
	if (player.getInventory().containsItem(plant, 27)) {
	player.getInventory().deleteItem(plant, 27);
	player.getInventory().addItem(bunchedplant, 27);
	player.getInventory().refresh();
    player.setNextAnimation(new Animation(778));
    player.getInterfaceManager().closeChatBoxInterface();
    player.setNextGraphics(new Graphics(2039));
	player.sm("You use power of your magical orb, all your plants are bunched.");
	} else {
		player.sm("You must have 27 plants to insta-bunch.");
	}
	}
	/**
	 * 
	 * @param player
	 */
	public static void deposit(Player player) {
	if (player.getInventory().containsItem(bunchedplant, 27)) {
	player.getSkills().addXp(Skills.FARMING, boostedxp);
    player.setNextAnimation(new Animation(780));
	player.getSkills().addXp(Skills.MAGIC, othskill);
	player.getSkills().addXp(Skills.CRAFTING, othskill);
	player.getInventory().deleteItem(bunchedplant, 27);
	player.getInventory().refresh();
	player.lividpoints += 75;
	player.sm("You receive points, you have now: "+player.lividpoints+".");
	player.getDialogueManager().startDialogue("SimpleNPCMessage", LADY, "You're so helpful, "+player.getDisplayName()+". Thank you!");
	} else {
		player.sm("You must have 27 bunched plants to deposit.");
	}
}
	
	/**
	 * 
	 * @param player
	 */
	public static void takemoreLogs(Player player) {
		player.sm("You have taken five logs.");
		player.lock(2);
		player.getInventory().addItem(1511, 5);
		player.lividfarm = false; //<- Reseter
		player.setNextAnimation(new Animation(832));
	}
	
	/**
	 * 
	 * @param player
	 */
	public static void CheckforLogs(Player player) {
		if (player.getInventory().containsItem(logs, 28)) {
			player.getInventory().deleteItem(logs, 28);
			player.getInventory().addItem(bucket, 1);
			player.lividfarm = true;
			player.getDialogueManager().startDialogue("SimpleNPCMessage", LADY, "Thanks for the logs! Now go make me plants, "+player.getDisplayName()+".");
			player.sm("Congratulations! You can now do Livid Farm.");
		} else {
			player.sm("You have to get 28 logs to Lady Servil.");
		}
	}
	/*
	 * Player-owned experience settings, after reaching 80+ farming.
	 */
	public static void setCrafting(Player player) {
		player.lividcraft = true;
		player.sm("You will be gaining now Crafting experience only.");
		player.lividmagic = false;
		player.lividfarming = false;
	}
	public static void setMagic(Player player) {
		player.lividcraft = false;
		player.sm("You will be gaining now Magic experience only.");
		player.lividmagic = true;
		player.lividfarming = false;
	}
	public static void setFarming(Player player) {
		player.lividcraft = false;
		player.sm("You will be gaining now Crafting experience only.");
		player.lividmagic = false;
		player.lividfarming = true;
	}
	/*
	 * Item Points handling
	 */
	 public static void HighLanderSet(Player player) {
		 if (player.lividpoints >= 2500) {
			player.lividpoints -= 2500;
			player.getInterfaceManager().closeChatBoxInterface();
			 player.getInventory().addItem(22693, 1);
			 player.getInventory().addItem(22703, 1);
			 player.getInventory().addItem(22713, 1);
			 player.getInventory().refresh();
				player.sm("Your payment was succesful, current points: "+player.lividpoints+".");
		 } else {
			 player.getInterfaceManager().closeChatBoxInterface();
			 player.sm("You have no enough points. Highlander set costs 2,500 livid points.");
		 }
	 }
	
	 public static void OrbPayment(Player player)  {
		 if (player.lividpoints >= 2800) {
				player.lividpoints -= 2800;
				player.getInterfaceManager().closeChatBoxInterface();
				 player.getInventory().addItem(orb, 1);
				 player.getInventory().refresh();
					player.sm("Your payment was succesful, current points: "+player.lividpoints+".");
			 		 
		 } else {
			 player.getInterfaceManager().closeChatBoxInterface();
			 player.sm("You have no enough points. Highlander set costs 2,500 livid points.");
		 
		 }
	 }
}
