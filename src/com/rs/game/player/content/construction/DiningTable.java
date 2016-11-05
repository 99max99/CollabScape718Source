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

public class DiningTable {
	
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
	public static int CLOTHBOLT = 8790;
	public static int GOLDLEAF = 8784;
	public static int MARBLEBLOCK = 8786;
	
	public static void CheckWoodenTable(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 10) {
			player.getPackets().sendGameMessage("You need a construction level of 10 to build this.");
		} else if (!player.getInventory().containsItem(WOODENPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 wooden planks to build this table.");
		} else if (!player.getInventory().containsItem(BRONZENAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 bronze nails to build this table.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this table.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 4);
			player.getInventory().deleteItem(BRONZENAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 10);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a table.");
			if (object.getX() == 98 && object.getY() == 107) {
			player.table1 = 1;
			player.tableX1 = boundChuncks[0]*8 + 34;
			player.tableY1 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13293, 10, object.getRotation(), player.tableX1, player.tableY1, 0), true);
			}
		}
	}
	
	public static void CheckOakTable(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 22) {
			player.getPackets().sendGameMessage("You need a construction level of 22 to build this.");
		} else if (!player.getInventory().containsItem(OAKPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 oak planks to build this table.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 iron nails to build this table.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this table.");
		} else {
			player.getInventory().deleteItem(OAKPLANK, 4);
			player.getInventory().deleteItem(IRONNAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 23);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a table.");
			if (object.getX() == 98 && object.getY() == 107) {
			player.table1 = 2;
			player.tableX1 = boundChuncks[0]*8 + 34;
			player.tableY1 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13294, 10, object.getRotation(), player.tableX1, player.tableY1, 0), true);
			}
		}
	}
	
	public static void CheckCarvedOakTable(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 31) {
			player.getPackets().sendGameMessage("You need a construction level of 31 to build this.");
		} else if (!player.getInventory().containsItem(OAKPLANK, 6)) {
			player.getPackets().sendGameMessage("You need atleast 6 oak planks to build this table.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 6)) {
			player.getPackets().sendGameMessage("You need atleast 6 iron nails to build this table.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this table.");
		} else {
			player.getInventory().deleteItem(OAKPLANK, 6);
			player.getInventory().deleteItem(IRONNAILS, 6);
			player.getSkills().addXp(Skills.CONSTRUCTION, 32);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a table.");
			if (object.getX() == 98 && object.getY() == 107) {
			player.table1 = 3;
			player.tableX1 = boundChuncks[0]*8 + 34;
			player.tableY1 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13295, 10, object.getRotation(), player.tableX1, player.tableY1, 0), true);
			}
		}
	}
	
	public static void CheckTeakTable(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 38) {
			player.getPackets().sendGameMessage("You need a construction level of 38 to build this.");
		} else if (!player.getInventory().containsItem(TEAKPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 teak planks to build this table.");
		} else if (!player.getInventory().containsItem(MITHNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 mithril nails to build this table.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this table.");
		} else {
			player.getInventory().deleteItem(TEAKPLANK, 4);
			player.getInventory().deleteItem(MITHNAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 41);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a table.");
			if (object.getX() == 98 && object.getY() == 107) {
			player.table1 = 4;
			player.tableX1 = boundChuncks[0]*8 + 34;
			player.tableY1 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13296, 10, object.getRotation(), player.tableX1, player.tableY1, 0), true);
			}
		}
	}
	
	public static void CheckCarvedTeakTable(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 45) {
			player.getPackets().sendGameMessage("You need a construction level of 45 to build this.");
		} else if (!player.getInventory().containsItem(TEAKPLANK, 6)) {
			player.getPackets().sendGameMessage("You need atleast 6 teak planks to build this table.");
		} else if (!player.getInventory().containsItem(CLOTHBOLT, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 bolts of cloth to build this table.");
		} else if (!player.getInventory().containsItem(MITHNAILS, 6)) {
			player.getPackets().sendGameMessage("You need atleast 6 mithril nails to build this table.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this table.");
		} else {
			player.getInventory().deleteItem(TEAKPLANK, 6);
			player.getInventory().deleteItem(MITHNAILS, 6);
			player.getInventory().deleteItem(CLOTHBOLT, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 48);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a table.");
			if (object.getX() == 98 && object.getY() == 107) {
			player.table1 = 4;
			player.tableX1 = boundChuncks[0]*8 + 34;
			player.tableY1 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13297, 10, object.getRotation(), player.tableX1, player.tableY1, 0), true);
			}
		}
	}
	
	public static void CheckMahoganyTable(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 52) {
			player.getPackets().sendGameMessage("You need a construction level of 52 to build this.");
		} else if (!player.getInventory().containsItem(MAHOGANYPLANK, 6)) {
			player.getPackets().sendGameMessage("You need atleast 6 mahogany planks to build this table.");
		} else if (!player.getInventory().containsItem(ADAMNAILS, 6)) {
			player.getPackets().sendGameMessage("You need atleast 6 adamant nails to build this table.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this table.");
		} else {
			player.getInventory().deleteItem(MAHOGANYPLANK, 6);
			player.getInventory().deleteItem(ADAMNAILS, 6);
			player.getSkills().addXp(Skills.CONSTRUCTION, 62);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a table.");
			if (object.getX() == 98 && object.getY() == 107) {
			player.table1 = 5;
			player.tableX1 = boundChuncks[0]*8 + 34;
			player.tableY1 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13298, 10, object.getRotation(), player.tableX1, player.tableY1, 0), true);
			}
		}
	}
	
	public static void CheckOpulenttable(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 72) {
			player.getPackets().sendGameMessage("You need a construction level of 72 to build this.");
		} else if (!player.getInventory().containsItem(MAHOGANYPLANK, 6)) {
			player.getPackets().sendGameMessage("You need atleast 6 mahogany planks to build this table.");
		} else if (!player.getInventory().containsItem(CLOTHBOLT, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 bolts of cloth to build this table.");
		} else if (!player.getInventory().containsItem(GOLDLEAF, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 gold leaves to build this table.");
		} else if (!player.getInventory().containsItem(RUNENAILS, 6)) {
			player.getPackets().sendGameMessage("You need atleast 6 rune nails to build this table.");
		} else if (!player.getInventory().containsItem(MARBLEBLOCK, 2)) {
			player.getPackets().sendGameMessage("You need atleast 2 marble blocks to build this table.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this table.");
		} else {
			player.getInventory().deleteItem(MAHOGANYPLANK, 6);
			player.getInventory().deleteItem(RUNENAILS, 6);
			player.getInventory().deleteItem(CLOTHBOLT, 4);
			player.getInventory().deleteItem(GOLDLEAF, 4);
			player.getInventory().deleteItem(MARBLEBLOCK, 2);
			player.getSkills().addXp(Skills.CONSTRUCTION, 95);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a table.");
			if (object.getX() == 98 && object.getY() == 107) {
			player.table1 = 6;
			player.tableX1 = boundChuncks[0]*8 + 34;
			player.tableY1 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13299, 10, object.getRotation(), player.tableX1, player.tableY1, 0), true);
			}
		}
	}
}