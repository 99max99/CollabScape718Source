package com.rs.game.player.actions.farming;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author Xiles
 *
 */
public class Farming {
public static int DIBBLER = 5343;
public static int GUAM = 5291;
public static int GGUAM = 199;
public static final Animation USEDIBBLER = new Animation(2291);

	public static void startSeeding(Player player) {
		if (player.getInventory().containsItem(DIBBLER, 1) && player.getInventory().containsItem(GUAM, 1)) {
			player.lock(4);
			player.getSkills().addXp(Skills.FARMING, 200);
			player.sm("You plant the Guam Seed.");
			player.getInventory().deleteItem(GUAM, 1);
			player.getInventory().addItem(GGUAM, 1);
			player.setNextAnimation(USEDIBBLER);
			player.getInventory().refresh();
		}
		else {
			player.sm("You need a seed dibbler and a guam seed for this.");
		}
	}
}

