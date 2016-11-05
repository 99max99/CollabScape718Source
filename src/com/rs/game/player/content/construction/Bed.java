package com.rs.game.player.content.construction;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author Danny
 *
 */

public class Bed {
	
	public static int WOODENPLANK = 960; 
	public static int BRONZENAILS = 4819;
	public static int CLOTH = 8790;
	public static int HAMMER = 2347;

	public static void CheckBed(Player player) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 20) {
			player.getPackets().sendGameMessage("You need a construction level of 20 to build this.");
		} else if (!player.getInventory().containsItem(WOODENPLANK, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 wooden planks to build this wooden bed.");
		} else if (!player.getInventory().containsItem(BRONZENAILS, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 bronze nails to build this wooden bed.");
		} else if (!player.getInventory().containsItem(CLOTH, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 bolts of cloth to build this wooden bed.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this wooden bed.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 3);
			player.getInventory().deleteItem(BRONZENAILS, 3);
			player.getInventory().deleteItem(CLOTH, 3);
			player.getSkills().addXp(Skills.CONSTRUCTION, 22);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a wooden bed.");
		}
	}
}