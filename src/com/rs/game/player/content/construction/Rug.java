package com.rs.game.player.content.construction;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * 
 * @author Danny
 *
 */

public class Rug {
	
	public static int CLOTH = 8790;
	public static int HAMMER = 2347;
	public static int GOLDLEAF = 4692;
	
	/**
	 * 
	 * Checks Brown Rug
	 * 
	 */
	
	public static void CheckBrownRug(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 2) {
			player.getPackets().sendGameMessage("You need a construction level of 2 to build this.");
		} else if (!player.getInventory().containsItem(CLOTH, 2)) {
			player.getPackets().sendGameMessage("You need atleast two bolts of cloth to build this brown rug.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this brown rug.");
		} else {
			player.getInventory().deleteItem(CLOTH, 2);
			player.getSkills().addXp(Skills.CONSTRUCTION, 7);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a brown rug.");
			player.rug1 = 1;
			player.rugX1 = boundChuncks[0]*8 + 40;
			player.rugY1 = boundChuncks[1]*8 + 40;
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 1, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 2, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 3, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13588, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 3, 0), true);
		}
	}
	
	/**
	 * 
	 * Checks Rug
	 * 
	 */
	
	public static void CheckRug(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 13) {
			player.getPackets().sendGameMessage("You need a construction level of 13 to build this.");
		} else if (!player.getInventory().containsItem(CLOTH, 4)) {
			player.getPackets().sendGameMessage("You need atleast four bolts of cloth to build this rug.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this rug.");
		} else {
			player.getInventory().deleteItem(CLOTH, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 10);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a rug.");
			player.rug1 = 2;
			player.rugX1 = boundChuncks[0]*8 + 40;
			player.rugY1 = boundChuncks[1]*8 + 40;
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 1, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 2, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 3, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13591, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 3, 0), true);
		}
	}
	
	/**
	 * 
	 * Checks Opulent Rug
	 * 
	 */
	
	public static void CheckOpulentRug(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 65) {
			player.getPackets().sendGameMessage("You need a construction level of 65 to build this.");
		} else if (!player.getInventory().containsItem(CLOTH, 4)) {
			player.getPackets().sendGameMessage("You need atleast four bolts of cloth to build this opulent rug.");
		} else if (!player.getInventory().containsItem(GOLDLEAF, 1)) {
			player.getPackets().sendGameMessage("You need atleast one gold leaf to build this opulent rug.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this opulent rug.");
		} else {
			player.getInventory().deleteItem(CLOTH, 4);
			player.getInventory().deleteItem(GOLDLEAF, 1);
			player.getSkills().addXp(Skills.CONSTRUCTION, 38);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a opulent rug.");
			player.rug1 = 3;
			player.rugX1 = boundChuncks[0]*8 + 40;
			player.rugY1 = boundChuncks[1]*8 + 40;
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 1, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 1, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 2, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 2, player.rugY1 + 3, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 3, player.rugY1, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 1, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 2, 0), true);
			World.spawnObject(new WorldObject(13594, 10, object.getRotation(), player.rugX1 + 3, player.rugY1 + 3, 0), true);
		}
	}
}