package com.rs.game.player.content.construction;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author Danny
 *
 */

public class Dresser {
	
	public static int WOODENPLANK = 960; 
	public static int IRONNAILS = 4820;
	public static int GLASS = 1775;
	public static int HAMMER = 2347;

	public static void CheckDresser(Player player) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 21) {
			player.getPackets().sendGameMessage("You need a construction level of 21 to build this.");
		} else if (!player.getInventory().containsItem(WOODENPLANK, 1)) {
			player.getPackets().sendGameMessage("You need atleast 1 wooden plank to build this dresser.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 1)) {
			player.getPackets().sendGameMessage("You need atleast 1 iron nail to build this dresser.");
		} else if (!player.getInventory().containsItem(GLASS, 1)) {
			player.getPackets().sendGameMessage("You need atleast 1 molten glass to build this dresser.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this dresser.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 1);
			player.getInventory().deleteItem(IRONNAILS, 1);
			player.getInventory().deleteItem(GLASS, 1);
			player.getSkills().addXp(Skills.CONSTRUCTION, 23);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a dresser.");
		}
	}
}
