package com.rs.game.player.content.construction;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author Danny
 *
 */

public class Wardrobe {
	
	public static int WOODENPLANK = 960; 
	public static int BRONZENAILS = 4819;
	public static int HAMMER = 2347;
	
	public static void CheckWardrobe(Player player) {
		
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 20) {
			player.getPackets().sendGameMessage("You need a construction level of 20 to build this.");
		} else if (!player.getInventory().containsItem(WOODENPLANK, 2)) {
			player.getPackets().sendGameMessage("You need atleast 2 wooden planks to build this wardrobe.");
		} else if (!player.getInventory().containsItem(BRONZENAILS, 2)) {
			player.getPackets().sendGameMessage("You need atleast 2 bronze nails to build this wardrobe.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this wardrobe");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 2);
			player.getInventory().deleteItem(BRONZENAILS, 2);
			player.getSkills().addXp(Skills.CONSTRUCTION, 22);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a wardrobe.");
		}
		
	}

}
