package com.rs.game.player.content;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

/**
 * 
 * @author Xdragonlordx
 *
 */
public class MaxedUser {
public static int MAXCAPE = 20767;
public static int ONE = 1;
public static int MAXHOOD = 20768;
public static int COMPLETIONISTCAPE = 20770; 
public static int COMPLETIONISTHOOD = 20771; 

public static void CheckCompletionist(Player player) {
	if (player.getSkills().getLevelForXp(Skills.ATTACK) >= 99
	&& player.getSkills().getLevelForXp(Skills.STRENGTH) >= 99
	&& player.getSkills().getLevelForXp(Skills.DEFENCE) >= 99
	&& player.getSkills().getLevelForXp(Skills.CONSTRUCTION) >= 99
	&& player.getSkills().getLevelForXp(Skills.HITPOINTS) >= 99
	&& player.getSkills().getLevelForXp(Skills.RANGE) >= 99
	&& player.getSkills().getLevelForXp(Skills.MAGIC) >= 99
	&& player.getSkills().getLevelForXp(Skills.PRAYER) >= 99
	&& player.getSkills().getLevelForXp(Skills.SUMMONING) >= 99
	&& player.isCompletionist == 0) {
		player.isCompletionist = 1;
		World.sendWorldMessage(
				Utils.formatPlayerNameForDisplay(player.getUsername())
						+ "<col=800000> Has just achieved the Completionists cape!"
						+ "</col> ", false);
		player.sm("Congrulations, you've achieved the Completionists cape!");
		player.getInventory().addItem(COMPLETIONISTCAPE, ONE);
		player.getInventory().addItem(COMPLETIONISTHOOD, ONE);
	}
	else {
		//player.isCompletionist = 0; //<-- Added this for safety reasons.
	}
	}

	public static void CheckMaxed(Player player) {
		if (player.getSkills().getLevelForXp(Skills.ATTACK) >= 99
		&& player.getSkills().getLevelForXp(Skills.STRENGTH) >= 99
		&& player.getSkills().getLevelForXp(Skills.DEFENCE) >= 99
		&& player.getSkills().getLevelForXp(Skills.CONSTRUCTION) >= 99
		&& player.getSkills().getLevelForXp(Skills.HITPOINTS) >= 99
		&& player.getSkills().getLevelForXp(Skills.RANGE) >= 99
		&& player.getSkills().getLevelForXp(Skills.MAGIC) >= 99
		&& player.getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FISHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.AGILITY) >= 99
		&& player.getSkills().getLevelForXp(Skills.COOKING) >= 99
		&& player.getSkills().getLevelForXp(Skills.PRAYER) >= 99
		&& player.getSkills().getLevelForXp(Skills.THIEVING) >= 99
		&& player.getSkills().getLevelForXp(Skills.DUNGEONEERING) >= 99
		&& player.getSkills().getLevelForXp(Skills.MINING) >= 99
		&& player.getSkills().getLevelForXp(Skills.SMITHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.SUMMONING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FARMING) >= 99
		&& player.getSkills().getLevelForXp(Skills.DUNGEONEERING) >= 99
		&& player.getSkills().getLevelForXp(Skills.HUNTER) >= 99
		&& player.getSkills().getLevelForXp(Skills.SLAYER) >= 99
		&& player.getSkills().getLevelForXp(Skills.CRAFTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FIREMAKING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FLETCHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.HERBLORE) >= 99
		&& player.isMaxed == 0) {
			player.isMaxed = 1;
			World.sendWorldMessage(
					Utils.formatPlayerNameForDisplay(player.getUsername())
							+ "<col=800000> Has just claimed his very first Max cape."
							+ "</col> ", false);
			player.sm("Congrulations, you've achieved the Max cape!");
			player.getInventory().addItem(MAXCAPE, ONE);
			player.getInventory().addItem(MAXHOOD, ONE);
		}
		else {
			//player.isMaxed = 0; //<-- Added this for safety reasons.
			//player.sm("There is currently nothing for you.");
		}
		}
		
}