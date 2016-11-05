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

public class Portal {
	
	public static int BRONZENAILS = 4819;
	public static int IRONNAILS = 4820;
	public static int BLACKNAILS = 4821;
	public static int MITHNAILS = 4822;
	public static int ADAMNAILS = 4823;
	public static int RUNENAILS = 4824;
	public static int WOODENPLANK = 960;
	public static int OAKPLANK = 8778;
	public static int TEAKPLANK = 8780;
	public static int MAHOGANYPLANK = 8782;
	public static int MARBLEBLOCK = 8786;
	public static int HAMMER = 2347;
	
	public static void CheckTeakPortal(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 50) {
			player.getPackets().sendGameMessage("You need a construction level of 50 to build this.");
		} else if (!player.getInventory().containsItem(TEAKPLANK, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 teak planks to build this portal.");
		} else if (!player.getInventory().containsItem(MITHNAILS, 3)) {
			player.getPackets().sendGameMessage("You need atleast 4 mithril nails to build this portal.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this portal.");
		} else {
			player.getInventory().deleteItem(TEAKPLANK, 3);
			player.getInventory().deleteItem(MITHNAILS, 3);
			player.getSkills().addXp(Skills.CONSTRUCTION, 58);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a portal.");
			if (object.getX() == 111 && object.getY() == 105) {
			//player.portal1 = 1;
			//player.portalX1 = boundChuncks[0]*8 + 47;
			//player.portalY1 = boundChuncks[1]*8 + 41;
			//World.spawnObject(new WorldObject(13597, 10, object.getRotation(), player.portalX1, player.portalY1, 0), true);
			} else if (object.getX() == 104 && object.getY() == 105) {
			//player.portal2 = 1;
			//player.portalX2 = boundChuncks[0]*8 + 40;
			//player.portalY2 = boundChuncks[1]*8 + 41;
			//World.spawnObject(new WorldObject(13597, 10, object.getRotation(), player.portalX2, player.portalY2, 0), true);
			} else if (object.getX() == 104 && object.getY() == 89) {
			//player.portal3 = 1;
			//player.portalX3 = boundChuncks[0]*8 + 40;
			//player.portalY3 = boundChuncks[1]*8 + 25;
			//World.spawnObject(new WorldObject(13597, 10, object.getRotation(), player.portalX3, player.portalY3, 0), true);
			}
		}
	}
	
	public static void CheckOakportal(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 65) {
			player.getPackets().sendGameMessage("You need a construction level of 65 to build this.");
		} else if (!player.getInventory().containsItem(MAHOGANYPLANK, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 mahogany planks to build this portal.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 adamant nails to build this portal.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this portal.");
		} else {
			player.getInventory().deleteItem(MAHOGANYPLANK, 3);
			player.getInventory().deleteItem(ADAMNAILS, 3);
			player.getSkills().addXp(Skills.CONSTRUCTION, 720);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a portal.");
			if (object.getX() == 111 && object.getY() == 105) {
			//player.portal1 = 2;
			//player.portalX1 = boundChuncks[0]*8 + 47;
			//player.portalY1 = boundChuncks[1]*8 + 41;
			//World.spawnObject(new WorldObject(13598, 10, object.getRotation(), player.portalX1, player.portalY1, 0), true);
			} else if (object.getX() == 104 && object.getY() == 105) {
			//player.portal2 = 2;
			//player.portalX2 = boundChuncks[0]*8 + 40;
			//player.portalY2 = boundChuncks[1]*8 + 41;
			//World.spawnObject(new WorldObject(13598, 10, object.getRotation(), player.portalX2, player.portalY2, 0), true);
			} else if (object.getX() == 104 && object.getY() == 89) {
			//player.portal3 = 2;
			//player.portalX3 = boundChuncks[0]*8 + 40;
			//player.portalY3 = boundChuncks[1]*8 + 25;
			//World.spawnObject(new WorldObject(13598, 10, object.getRotation(), player.portalX3, player.portalY3, 0), true);
			}
		}
	}
	
	public static void CheckMahoganyportal(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 80) {
			player.getPackets().sendGameMessage("You need a construction level of 80 to build this.");
		} else if (!player.getInventory().containsItem(MARBLEBLOCK, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 marble blocks to build this portal.");
		} else if (!player.getInventory().containsItem(MITHNAILS, 3)) {
			player.getPackets().sendGameMessage("You need atleast 4 rune nails to build this portal.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this portal.");
		} else {
			player.getInventory().deleteItem(MARBLEBLOCK, 3);
			player.getInventory().deleteItem(RUNENAILS, 3);
			player.getSkills().addXp(Skills.CONSTRUCTION, 46);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a portal.");
			if (object.getX() == 111 && object.getY() == 105) {
			//player.portal1 = 3;
			//player.portalX1 = boundChuncks[0]*8 + 47;
			//player.portalY1 = boundChuncks[1]*8 + 41;
			//World.spawnObject(new WorldObject(13599, 10, object.getRotation(), player.portalX1, player.portalY1, 0), true);
			} else if (object.getX() == 104 && object.getY() == 105) {
			//player.portal2 = 3;
			//player.portalX2 = boundChuncks[0]*8 + 40;
			//player.portalY2 = boundChuncks[1]*8 + 41;
			//World.spawnObject(new WorldObject(13599, 10, object.getRotation(), player.portalX2, player.portalY2, 0), true);
			} else if (object.getX() == 104 && object.getY() == 89) {
			//player.portal3 = 3;
			//player.portalX3 = boundChuncks[0]*8 + 40;
			//player.portalY3 = boundChuncks[1]*8 + 25;
			//World.spawnObject(new WorldObject(13599, 10, object.getRotation(), player.portalX3, player.portalY3, 0), true);
			}
		}
	}
}