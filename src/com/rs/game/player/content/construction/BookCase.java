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

public class BookCase {
	
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
	public static int HAMMER = 2347;
	
	public static void CheckWoodenBookCase(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 4) {
			player.getPackets().sendGameMessage("You need a construction level of 4 to build this.");
		} else if (!player.getInventory().containsItem(WOODENPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 wooden planks to build this bookcase.");
		} else if (!player.getInventory().containsItem(BRONZENAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 bronze nails to build this bookcase.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bookcase.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 4);
			player.getInventory().deleteItem(BRONZENAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 5);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bookcase.");
			if (object.getX() == 111 && object.getY() == 105) {
			player.bookcase1 = 1;
			player.bookcaseX1 = boundChuncks[0]*8 + 47;
			player.bookcaseY1 = boundChuncks[1]*8 + 41;
			World.spawnObject(new WorldObject(13597, 10, object.getRotation(), player.bookcaseX1, player.bookcaseY1, 0), true);
			} else if (object.getX() == 104 && object.getY() == 105) {
			player.bookcase2 = 1;
			player.bookcaseX2 = boundChuncks[0]*8 + 40;
			player.bookcaseY2 = boundChuncks[1]*8 + 41;
			World.spawnObject(new WorldObject(13597, 10, object.getRotation(), player.bookcaseX2, player.bookcaseY2, 0), true);
			} else if (object.getX() == 104 && object.getY() == 89) {
			player.bookcase3 = 1;
			player.bookcaseX3 = boundChuncks[0]*8 + 40;
			player.bookcaseY3 = boundChuncks[1]*8 + 25;
			World.spawnObject(new WorldObject(13597, 10, object.getRotation(), player.bookcaseX3, player.bookcaseY3, 0), true);
			} else if (object.getX() == 96 && object.getY() == 92) {
			player.bookcase4 = 1;
			player.bookcaseX4 = boundChuncks[0]*8 + 32;
			player.bookcaseY4 = boundChuncks[1]*8 + 28;
			World.spawnObject(new WorldObject(13597, 10, object.getRotation(), player.bookcaseX4, player.bookcaseY4, 0), true);
			} else if (object.getX() == 96 && object.getY() == 91) {
			player.bookcase5 = 1;
			player.bookcaseX5 = boundChuncks[0]*8 + 32;
			player.bookcaseY5 = boundChuncks[1]*8 + 27;
			World.spawnObject(new WorldObject(13597, 10, object.getRotation(), player.bookcaseX5, player.bookcaseY5, 0), true);
			}
		}
	}
	
	public static void CheckOakBookCase(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 29) {
			player.getPackets().sendGameMessage("You need a construction level of 29 to build this.");
		} else if (!player.getInventory().containsItem(OAKPLANK, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 oak planks to build this bookcase.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 iron nails to build this bookcase.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bookcase.");
		} else {
			player.getInventory().deleteItem(OAKPLANK, 3);
			player.getInventory().deleteItem(IRONNAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 31);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bookcase.");
			if (object.getX() == 111 && object.getY() == 105) {
			player.bookcase1 = 2;
			player.bookcaseX1 = boundChuncks[0]*8 + 47;
			player.bookcaseY1 = boundChuncks[1]*8 + 41;
			World.spawnObject(new WorldObject(13598, 10, object.getRotation(), player.bookcaseX1, player.bookcaseY1, 0), true);
			} else if (object.getX() == 104 && object.getY() == 105) {
			player.bookcase2 = 2;
			player.bookcaseX2 = boundChuncks[0]*8 + 40;
			player.bookcaseY2 = boundChuncks[1]*8 + 41;
			World.spawnObject(new WorldObject(13598, 10, object.getRotation(), player.bookcaseX2, player.bookcaseY2, 0), true);
			} else if (object.getX() == 104 && object.getY() == 89) {
			player.bookcase3 = 2;
			player.bookcaseX3 = boundChuncks[0]*8 + 40;
			player.bookcaseY3 = boundChuncks[1]*8 + 25;
			World.spawnObject(new WorldObject(13598, 10, object.getRotation(), player.bookcaseX3, player.bookcaseY3, 0), true);
			} else if (object.getX() == 96 && object.getY() == 92) {
			player.bookcase4 = 2;
			player.bookcaseX4 = boundChuncks[0]*8 + 32;
			player.bookcaseY4 = boundChuncks[1]*8 + 28;
			World.spawnObject(new WorldObject(13598, 10, object.getRotation(), player.bookcaseX4, player.bookcaseY4, 0), true);
			} else if (object.getX() == 96 && object.getY() == 91) {
			player.bookcase5 = 2;
			player.bookcaseX5 = boundChuncks[0]*8 + 32;
			player.bookcaseY5 = boundChuncks[1]*8 + 27;
			World.spawnObject(new WorldObject(13598, 10, object.getRotation(), player.bookcaseX5, player.bookcaseY5, 0), true);
			}
		}
	}
	
	public static void CheckMahoganyBookCase(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 40) {
			player.getPackets().sendGameMessage("You need a construction level of 40 to build this.");
		} else if (!player.getInventory().containsItem(MAHOGANYPLANK, 3)) {
			player.getPackets().sendGameMessage("You need atleast 3 mahogany planks to build this bookcase.");
		} else if (!player.getInventory().containsItem(MITHNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 mithril nails to build this bookcase.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bookcase.");
		} else {
			player.getInventory().deleteItem(MAHOGANYPLANK, 3);
			player.getInventory().deleteItem(MITHNAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 46);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bookcase.");
			if (object.getX() == 111 && object.getY() == 105) {
			player.bookcase1 = 3;
			player.bookcaseX1 = boundChuncks[0]*8 + 47;
			player.bookcaseY1 = boundChuncks[1]*8 + 41;
			World.spawnObject(new WorldObject(13599, 10, object.getRotation(), player.bookcaseX1, player.bookcaseY1, 0), true);
			} else if (object.getX() == 104 && object.getY() == 105) {
			player.bookcase2 = 3;
			player.bookcaseX2 = boundChuncks[0]*8 + 40;
			player.bookcaseY2 = boundChuncks[1]*8 + 41;
			World.spawnObject(new WorldObject(13599, 10, object.getRotation(), player.bookcaseX2, player.bookcaseY2, 0), true);
			} else if (object.getX() == 104 && object.getY() == 89) {
			player.bookcase3 = 3;
			player.bookcaseX3 = boundChuncks[0]*8 + 40;
			player.bookcaseY3 = boundChuncks[1]*8 + 25;
			World.spawnObject(new WorldObject(13599, 10, object.getRotation(), player.bookcaseX3, player.bookcaseY3, 0), true);
			} else if (object.getX() == 96 && object.getY() == 92) {
			player.bookcase4 = 3;
			player.bookcaseX4 = boundChuncks[0]*8 + 32;
			player.bookcaseY4 = boundChuncks[1]*8 + 28;
			World.spawnObject(new WorldObject(13599, 10, object.getRotation(), player.bookcaseX4, player.bookcaseY4, 0), true);
			} else if (object.getX() == 96 && object.getY() == 91) {
			player.bookcase5 = 3;
			player.bookcaseX5 = boundChuncks[0]*8 + 32;
			player.bookcaseY5 = boundChuncks[1]*8 + 27;
			World.spawnObject(new WorldObject(13599, 10, object.getRotation(), player.bookcaseX5, player.bookcaseY5, 0), true);
			}
		}
	}
}