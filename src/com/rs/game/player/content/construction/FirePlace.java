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

public class FirePlace {
	
	public static int CLAY = 1761;
	public static int HAMMER = 2347;
	public static int MARBLEBLOCK = 8786;
	public static int LIMESTONE = 3420;
	
	public static void CheckClayFirePlace(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 3) {
			player.getPackets().sendGameMessage("You need a construction level of 3 to build this.");
		} else if (!player.getInventory().containsItem(CLAY, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 soft clay to build this fireplace.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this fireplace.");
		} else {
			player.getInventory().deleteItem(CLAY, 3);
			player.getSkills().addXp(Skills.CONSTRUCTION, 2);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a fireplace.");
			if (object.getX() == 107 && object.getY() == 111) {
			player.fireplace1 = 1;
			player.fireplaceX1 = boundChuncks[0]*8 + 43;
			player.fireplaceY1 = boundChuncks[1]*8 + 47;
			World.spawnObject(new WorldObject(13609, 10, object.getRotation(), player.fireplaceX1, player.fireplaceY1, 0), true);
			} else if (object.getX() == 109 && object.getY() == 111) {
			player.fireplace2 = 1;
			player.fireplaceX2 = boundChuncks[0]*8 + 35;
			player.fireplaceY2 = boundChuncks[1]*8 + 47;
			World.spawnObject(new WorldObject(13609, 10, object.getRotation(), player.fireplaceX2, player.fireplaceY2, 0), true);
			} else if (object.getX() == 119 && object.getY() == 99) {
			player.fireplace3 = 1;
			player.fireplaceX3 = boundChuncks[0]*8 + 55;
			player.fireplaceY3 = boundChuncks[1]*8 + 35;
			World.spawnObject(new WorldObject(13609, 10, object.getRotation(), player.fireplaceX3, player.fireplaceY3, 0), true);
			}
		}
	}
	
	public static void CheckStoneFirePlace(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 33) {
			player.getPackets().sendGameMessage("You need a construction level of 33 to build this.");
		} else if (!player.getInventory().containsItem(LIMESTONE, 2)) {
			player.getPackets().sendGameMessage("You need atleast 2 limestone bricks to build this fireplace.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this fireplace.");
		} else {
			player.getInventory().deleteItem(LIMESTONE, 2);
			player.getSkills().addXp(Skills.CONSTRUCTION, 24);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a fireplace.");
			if (object.getX() == 107 && object.getY() == 111) {
			player.fireplace2 = 2;
			player.fireplaceX1 = boundChuncks[0]*8 + 43;
			player.fireplaceY1 = boundChuncks[1]*8 + 47;
			World.spawnObject(new WorldObject(13611, 10, object.getRotation(), player.fireplaceX1, player.fireplaceY1, 0), true);
			} else if (object.getX() == 99 && object.getY() == 111) {
			player.fireplace3 = 2;
			player.fireplaceX2 = boundChuncks[0]*8 + 35;
			player.fireplaceY2 = boundChuncks[1]*8 + 47;
			World.spawnObject(new WorldObject(13611, 10, object.getRotation(), player.fireplaceX2, player.fireplaceY2, 0), true);
			} else if (object.getX() == 119 && object.getY() == 99) {
			player.fireplace3 = 2;
			player.fireplaceX3 = boundChuncks[0]*8 + 55;
			player.fireplaceY3 = boundChuncks[1]*8 + 35;
			World.spawnObject(new WorldObject(13611, 10, object.getRotation(), player.fireplaceX3, player.fireplaceY3, 0), true);
			}
		}
	}
	
	public static void CheckMarbleFirePlace(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 63) {
			player.getPackets().sendGameMessage("You need a construction level of 63 to build this.");
		} else if (!player.getInventory().containsItem(MARBLEBLOCK, 1)) {
			player.getPackets().sendGameMessage("You need atleast 1 marble block to build this fireplace.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this fireplace.");
		} else {
			player.getInventory().deleteItem(MARBLEBLOCK, 1);
			player.getSkills().addXp(Skills.CONSTRUCTION, 84);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a fireplace.");
			if (object.getX() == 107 && object.getY() == 111) {
			player.fireplace1 = 3;
			player.fireplaceX1 = boundChuncks[0]*8 + 43;
			player.fireplaceY1 = boundChuncks[1]*8 + 47;
			World.spawnObject(new WorldObject(13613, 10, object.getRotation(), player.fireplaceX1, player.fireplaceY1, 0), true);
			} else if (object.getX() == 99 && object.getY() == 111) {
			player.fireplace2 = 3;
			player.fireplaceX2 = boundChuncks[0]*8 + 35;
			player.fireplaceY2 = boundChuncks[1]*8 + 47;
			World.spawnObject(new WorldObject(13613, 10, object.getRotation(), player.fireplaceX2, player.fireplaceY2, 0), true);
			} else if (object.getX() == 119 && object.getY() == 99) {
			player.fireplace3 = 3;
			player.fireplaceX3 = boundChuncks[0]*8 + 55;
			player.fireplaceY3 = boundChuncks[1]*8 + 35;
			World.spawnObject(new WorldObject(13613, 10, object.getRotation(), player.fireplaceX3, player.fireplaceY3, 0), true);
			}
		}
	}
}