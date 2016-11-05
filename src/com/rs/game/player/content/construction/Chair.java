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

public class Chair {
	
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
	
	/**
	 * 
	 * Checks Crude Chair
	 * 
	 */

	public static void CheckCrudeChair(Player player, final WorldObject object, final int[] boundChuncks) {
		if (!player.getInventory().containsItem(WOODENPLANK, 2)) {
			player.getPackets().sendGameMessage("You need atleast two wooden planks to build this crude chair.");
		} else if (!player.getInventory().containsItem(BRONZENAILS, 2)) {
			player.getPackets().sendGameMessage("You need atleast two bronze nails to build this crude chair.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this crude chair.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 2);
			player.getInventory().deleteItem(BRONZENAILS, 2);
			player.getSkills().addXp(Skills.CONSTRUCTION, 6);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a crude chair.");
			if (object.getX() == 106 && object.getY() == 108) {
			player.chair1 = 1;
			player.chairX1 = boundChuncks[0]*8 + 42;
			player.chairY1 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13581, 10, object.getRotation(), player.chairX1, player.chairY1, 0), true);
			} else if (object.getX() == 108 && object.getY() == 107) {
			player.chair2 = 1;
			player.chairX2 = boundChuncks[0]*8 + 44;
			player.chairY2 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13581, 10, object.getRotation(), player.chairX2, player.chairY2, 0), true);
			} else if (object.getX() == 109 && object.getY() == 108) {
			player.chair3 = 1;
			player.chairX3 = boundChuncks[0]*8 + 45;
			player.chairY3 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13581, 10, object.getRotation(), player.chairX3, player.chairY3, 0), true);
			}
		}
	}
	
	/**
	 * 
	 * Checks Wooden Chair
	 * 
	 */
	
	public static void CheckWoodenChair(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 8) {
			player.getPackets().sendGameMessage("You need a construction level of 8 to build this.");
		} else if (!player.getInventory().containsItem(WOODENPLANK, 3)) {
			player.getPackets().sendGameMessage("You need atleast three wooden planks to build this wooden chair.");
		} else if (!player.getInventory().containsItem(BRONZENAILS, 3)) {
			player.getPackets().sendGameMessage("You need atleast three bronze nails to build this wooden chair.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this wooden chair.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 3);
			player.getInventory().deleteItem(BRONZENAILS, 3);
			player.getSkills().addXp(Skills.CONSTRUCTION, 9);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a wooden chair.");
			if (object.getX() == 106 && object.getY() == 108) {
			player.chair1 = 2;
			player.chairX1 = boundChuncks[0]*8 + 42;
			player.chairY1 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13582, 10, object.getRotation(), player.chairX1, player.chairY1, 0), true);
			} else if (object.getX() == 108 && object.getY() == 107) {
			player.chair2 = 2;
			player.chairX2 = boundChuncks[0]*8 + 44;
			player.chairY2 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13582, 10, object.getRotation(), player.chairX2, player.chairY2, 0), true);
			} else if (object.getX() == 109 && object.getY() == 108) {
			player.chair3 = 2;
			player.chairX3 = boundChuncks[0]*8 + 45;
			player.chairY3 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13582, 10, object.getRotation(), player.chairX3, player.chairY3, 0), true);
			}
		}
	}
	
	/**
	 * 
	 * Checks Rocking Chair
	 * 
	 */
	
	public static void CheckRockingChair(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 14) {
			player.getPackets().sendGameMessage("You need a construction level of 14 to build this.");
		} else if (!player.getInventory().containsItem(WOODENPLANK, 3)) {
			player.getPackets().sendGameMessage("You need atleast three wooden planks to build this rocking chair.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 3)) {
			player.getPackets().sendGameMessage("You need atleast three iron nails to build this rocking chair.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this rocking chair.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 3);
			player.getInventory().deleteItem(IRONNAILS, 3);
			player.getSkills().addXp(Skills.CONSTRUCTION, 11);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a rocking chair.");
			if (object.getX() == 106 && object.getY() == 108) {
			player.chair1 = 3;
			player.chairX1 = boundChuncks[0]*8 + 42;
			player.chairY1 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13583, 10, object.getRotation(), player.chairX1, player.chairY1, 0), true);
			} else if (object.getX() == 108 && object.getY() == 107) {
			player.chair2 = 3;
			player.chairX2 = boundChuncks[0]*8 + 44;
			player.chairY2 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13583, 10, object.getRotation(), player.chairX2, player.chairY2, 0), true);
			} else if (object.getX() == 109 && object.getY() == 108) {
			player.chair3 = 3;
			player.chairX3 = boundChuncks[0]*8 + 45;
			player.chairY3 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13583, 10, object.getRotation(), player.chairX3, player.chairY3, 0), true);
			}
		}
	}
	
	/**
	 * 
	 * Checks Oak Chair
	 * 
	 */
	
	public static void CheckOakChair(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 19) {
			player.getPackets().sendGameMessage("You need a construction level of 19 to build this.");
		} else if (!player.getInventory().containsItem(OAKPLANK, 2)) {
			player.getPackets().sendGameMessage("You need atleast two oak planks to build this oak chair.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast four iron nails to build this oak chair.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this oak chair.");
		} else {
			player.getInventory().deleteItem(OAKPLANK, 2);
			player.getInventory().deleteItem(IRONNAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 14);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a oak chair.");
			if (object.getX() == 106 && object.getY() == 108) {
			player.chair1 = 4;
			player.chairX1 = boundChuncks[0]*8 + 42;
			player.chairY1 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13584, 10, object.getRotation(), player.chairX1, player.chairY1, 0), true);
			} else if (object.getX() == 108 && object.getY() == 107) {
			player.chair2 = 4;
			player.chairX2 = boundChuncks[0]*8 + 44;
			player.chairY2 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13584, 10, object.getRotation(), player.chairX2, player.chairY2, 0), true);
			} else if (object.getX() == 109 && object.getY() == 108) {
			player.chair3 = 4;
			player.chairX3 = boundChuncks[0]*8 + 45;
			player.chairY3 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13584, 10, object.getRotation(), player.chairX3, player.chairY3, 0), true);
			}
		}
	}
	
	/**
	 * 
	 * Checks Oak Armchair
	 * 
	 */
	
	public static void CheckOakArmchair(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 26) {
			player.getPackets().sendGameMessage("You need a construction level of 26 to build this.");
		} else if (!player.getInventory().containsItem(OAKPLANK, 3)) {
			player.getPackets().sendGameMessage("You need atleast three oak planks to build this oak armchair.");
		} else if (!player.getInventory().containsItem(BLACKNAILS, 3)) {
			player.getPackets().sendGameMessage("You need atleast three black nails to build this oak armchair.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this oak armchair.");
		} else {
			player.getInventory().deleteItem(OAKPLANK, 3);
			player.getInventory().deleteItem(BLACKNAILS, 3);
			player.getSkills().addXp(Skills.CONSTRUCTION, 17);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a oak armchair.");
			if (object.getX() == 106 && object.getY() == 108) {
			player.chair1 = 5;
			player.chairX1 = boundChuncks[0]*8 + 42;
			player.chairY1 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13585, 10, object.getRotation(), player.chairX1, player.chairY1, 0), true);
			} else if (object.getX() == 108 && object.getY() == 107) {
			player.chair2 = 5;
			player.chairX2 = boundChuncks[0]*8 + 44;
			player.chairY2 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13585, 10, object.getRotation(), player.chairX2, player.chairY2, 0), true);
			} else if (object.getX() == 109 && object.getY() == 108) {
			player.chair3 = 5;
			player.chairX3 = boundChuncks[0]*8 + 45;
			player.chairY3 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13585, 10, object.getRotation(), player.chairX3, player.chairY3, 0), true);
			}
		}
	}
	
	/**
	 * 
	 * Checks Teak Armchair
	 * 
	 */
	
	public static void CheckTeakArmchair(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 35) {
			player.getPackets().sendGameMessage("You need a construction level of 35 to build this.");
		} else if (!player.getInventory().containsItem(TEAKPLANK, 2)) {
			player.getPackets().sendGameMessage("You need atleast two teak planks to build this teak armchair.");
		} else if (!player.getInventory().containsItem(BLACKNAILS, 6)) {
			player.getPackets().sendGameMessage("You need atleast six black nails to build this teak armchair.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this teak armchair.");
		} else {
			player.getInventory().deleteItem(TEAKPLANK, 2);
			player.getInventory().deleteItem(BLACKNAILS, 6);
			player.getSkills().addXp(Skills.CONSTRUCTION, 21);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a teak armchair.");
			if (object.getX() == 106 && object.getY() == 108) {
			player.chair1 = 6;
			player.chairX1 = boundChuncks[0]*8 + 42;
			player.chairY1 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13586, 10, object.getRotation(), player.chairX1, player.chairY1, 0), true);
			} else if (object.getX() == 108 && object.getY() == 107) {
			player.chair2 = 6;
			player.chairX2 = boundChuncks[0]*8 + 44;
			player.chairY2 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13586, 10, object.getRotation(), player.chairX2, player.chairY2, 0), true);
			} else if (object.getX() == 109 && object.getY() == 108) {
			player.chair3 = 6;
			player.chairX3 = boundChuncks[0]*8 + 45;
			player.chairY3 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13586, 10, object.getRotation(), player.chairX3, player.chairY3, 0), true);
			}
		}
	}
	
	/**
	 * 
	 * Checks Mahogany Armchair
	 * 
	 */
	
	public static void CheckMahoganyArmchair(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 50) {
			player.getPackets().sendGameMessage("You need a construction level of 50 to build this.");
		} else if (!player.getInventory().containsItem(MAHOGANYPLANK, 2)) {
			player.getPackets().sendGameMessage("You need atleast two mahogany planks to build this mahogany armchair.");
		} else if (!player.getInventory().containsItem(MITHNAILS, 5)) {
			player.getPackets().sendGameMessage("You need atleast five mithril nails to build this mahogany armchair.");
		} else if (!player.getInventory().containsItemToolBelt(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this mahogany armchair.");
		} else {
			player.getInventory().deleteItem(MAHOGANYPLANK, 2);
			player.getInventory().deleteItem(MITHNAILS, 5);
			player.getSkills().addXp(Skills.CONSTRUCTION, 26);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a mahogany armchair.");
			if (object.getX() == 106 && object.getY() == 108) {
			player.chair1 = 7;
			player.chairX1 = boundChuncks[0]*8 + 42;
			player.chairY1 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13587, 10, object.getRotation(), player.chairX1, player.chairY1, 0), true);
			} else if (object.getX() == 108 && object.getY() == 107) {
			player.chair2 = 7;
			player.chairX2 = boundChuncks[0]*8 + 44;
			player.chairY2 = boundChuncks[1]*8 + 43;
			World.spawnObject(new WorldObject(13587, 10, object.getRotation(), player.chairX2, player.chairY2, 0), true);
			} else if (object.getX() == 109 && object.getY() == 108) {
			player.chair3 = 7;
			player.chairX3 = boundChuncks[0]*8 + 45;
			player.chairY3 = boundChuncks[1]*8 + 44;
			World.spawnObject(new WorldObject(13587, 10, object.getRotation(), player.chairX3, player.chairY3, 0), true);
			}
		}
	}
}