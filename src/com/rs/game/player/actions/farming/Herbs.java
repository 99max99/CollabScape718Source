package com.rs.game.player.actions.farming;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author Plato
 *
 */

public class Herbs {
	
	public static int DIBBLER = 5343;
	public static int GUAM = 5291; //Cost 5k 
	public static int GGUAM = 199;
	public static int IRIT = 5297; //Cost 10k
	public static int GIRIT = 209;
	public static int KWUARM = 5299; //Cost 20k
	public static int GKWUARM = 213;
	public static int SNAPDRAGON = 5300; //Cost 50k
	public static int GSNAPDRAGON = 3051;
	public static int DWARFWEED = 5303; //Cost 150k
	public static int GDWARFWEED = 217;
	public static final Animation USEDIBBLER = new Animation(2291);
	
	/**
	 * 
	 * Checks Crude Chair
	 * 
	 */

	public static void CheckGuam(Player player) {
		if (!player.getInventory().containsItem(DIBBLER, 1)) {
			player.getPackets().sendGameMessage("You need a seed dibbler to plant this.");
		} else if (!player.getInventory().containsItem(GUAM, 1)) {
			player.getPackets().sendGameMessage("You need a guam seed to plant.");
		} else {
			player.getInventory().deleteItem(GUAM, 1);
			player.getInventory().addItem(GGUAM, 1);
			player.getSkills().addXp(Skills.FARMING, 25.5);
			player.setNextAnimation(USEDIBBLER);
			player.getPackets().sendGameMessage("You successfully plant the Guam.");
		}
	}
 
	public static void CheckIrit(Player player) {
		if (!player.getInventory().containsItem(DIBBLER, 1)) {
			player.getPackets().sendGameMessage("You need a seed dibbler to plant this.");
		} else if (!player.getInventory().containsItem(IRIT, 1)) {
			player.getPackets().sendGameMessage("You need a irit seed to plant.");
		} else {
			player.getInventory().deleteItem(IRIT, 1);
			player.getInventory().addItem(GIRIT, 1);
			player.getSkills().addXp(Skills.FARMING, 35.5);
			player.setNextAnimation(USEDIBBLER);
			player.getPackets().sendGameMessage("You successfully plant the Irit.");
		}
	}
	
	public static void CheckKwuarm(Player player) {
		if (!player.getInventory().containsItem(DIBBLER, 1)) {
			player.getPackets().sendGameMessage("You need a seed dibbler to plant this.");
		} else if (!player.getInventory().containsItem(KWUARM, 1)) {
			player.getPackets().sendGameMessage("You need a kwuarm seed to plant.");
		} else {
			player.getInventory().deleteItem(KWUARM, 1);
			player.getInventory().addItem(GKWUARM, 1);
			player.getSkills().addXp(Skills.FARMING, 49.5);
			player.setNextAnimation(USEDIBBLER);
			player.getPackets().sendGameMessage("You successfully plant the Kwuarm.");
		}
	}	
	
	public static void CheckSnapdragon(Player player) {
		if (!player.getInventory().containsItem(DIBBLER, 1)) {
			player.getPackets().sendGameMessage("You need a seed dibbler to plant this.");
		} else if (!player.getInventory().containsItem(SNAPDRAGON, 1)) {
			player.getPackets().sendGameMessage("You need a snapdragon seed to plant.");
		} else {
			player.getInventory().deleteItem(SNAPDRAGON, 1);
			player.getInventory().addItem(GSNAPDRAGON, 1);
			player.getSkills().addXp(Skills.FARMING, 85.5);
			player.setNextAnimation(USEDIBBLER);
			player.getPackets().sendGameMessage("You successfully plant the Spandragon.");
		}
	}	
	
	public static void CheckDwarfweed(Player player) {
		if (!player.getInventory().containsItem(DIBBLER, 1)) {
			player.getPackets().sendGameMessage("You need a seed dibbler to plant this.");
		} else if (!player.getInventory().containsItem(DWARFWEED, 1)) {
			player.getPackets().sendGameMessage("You need a dwarf weed seed to plant.");
		} else {
			player.getInventory().deleteItem(DWARFWEED, 1);
			player.getInventory().addItem(GDWARFWEED, 1);
			player.getSkills().addXp(Skills.FARMING, 115.5);
			player.setNextAnimation(USEDIBBLER);
			player.getPackets().sendGameMessage("You successfully plant the Dwarf Weed.");
		}
	}	
}