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

public class Seating {
	
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
	public static int CLOTHBOLT = 8790;
	public static int GOLDLEAF = 8784;
	public static int MARBLEBLOCK = 8786;
	public static int HAMMER = 2347;
	
	/**
	 * 
	 * Wooden Bench
	 * 
	 */

	public static void CheckWoodenBench(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 10) {
			player.getPackets().sendGameMessage("You need a construction level of 10 to build this.");
		} else if (!player.getInventory().containsItem(WOODENPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 wooden planks to build this.");
		} else if (!player.getInventory().containsItem(BRONZENAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 bronze nails to build this.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bench.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 4);
			player.getInventory().deleteItem(BRONZENAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 11);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bench.");
			if (object.getX() == 98 && object.getY() == 106) {
				player.bench1 = 1;
				player.benchX1 = boundChuncks[0]*8 + 34;
				player.benchY1 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13300, 10, object.getRotation(), player.benchX1, player.benchY1, 0), true);
				} else if (object.getX() == 99 && object.getY() == 106) {
				player.bench2 = 1;
				player.benchX2 = boundChuncks[0]*8 + 35;
				player.benchY2 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13300, 10, object.getRotation(), player.benchX2, player.benchY2, 0), true);
				} else if (object.getX() == 100 && object.getY() == 106) {
				player.bench3 = 1;
				player.benchX3 = boundChuncks[0]*8 + 36;
				player.benchY3 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13300, 10, object.getRotation(), player.benchX3, player.benchY3, 0), true);
				} else if (object.getX() == 101 && object.getY() == 106) {
				player.bench4 = 1;
				player.benchX4 = boundChuncks[0]*8 + 37;
				player.benchY4 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13300, 10, object.getRotation(), player.benchX4, player.benchY4, 0), true);
				} else if (object.getX() == 98 && object.getY() == 109) {
				player.bench5 = 1;
				player.benchX5 = boundChuncks[0]*8 + 34;
				player.benchY5 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13300, 10, object.getRotation(), player.benchX5, player.benchY5, 0), true);
				} else if (object.getX() == 99 && object.getY() == 109) {
				player.bench6 = 1;
				player.benchX6 = boundChuncks[0]*8 + 35;
				player.benchY6 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13300, 10, object.getRotation(), player.benchX6, player.benchY6, 0), true);
				} else if (object.getX() == 100 && object.getY() == 109) {
				player.bench7 = 1;
				player.benchX7 = boundChuncks[0]*8 + 36;
				player.benchY7 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13300, 10, object.getRotation(), player.benchX7, player.benchY7, 0), true);
				} else if (object.getX() == 101 && object.getY() == 109) {
				player.bench8 = 1;
				player.benchX8 = boundChuncks[0]*8 + 37;
				player.benchY8 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13300, 10, object.getRotation(), player.benchX8, player.benchY8, 0), true);
				}
		}
	}
	public static void CheckOakBench(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 22) {
			player.getPackets().sendGameMessage("You need a construction level of 22 to build this.");
		} else if (!player.getInventory().containsItem(OAKPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 oak planks to build this.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 iron nails to build this.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bench.");
		} else {
			player.getInventory().deleteItem(OAKPLANK, 4);
			player.getInventory().deleteItem(IRONNAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 24);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bench.");
			if (object.getX() == 98 && object.getY() == 106) {
				player.bench1 = 2;
				player.benchX1 = boundChuncks[0]*8 + 34;
				player.benchY1 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13301, 10, object.getRotation(), player.benchX1, player.benchY1, 0), true);
				} else if (object.getX() == 99 && object.getY() == 106) {
				player.bench2 = 2;
				player.benchX2 = boundChuncks[0]*8 + 35;
				player.benchY2 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13301, 10, object.getRotation(), player.benchX2, player.benchY2, 0), true);
				} else if (object.getX() == 100 && object.getY() == 106) {
				player.bench3 = 2;
				player.benchX3 = boundChuncks[0]*8 + 36;
				player.benchY3 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13301, 10, object.getRotation(), player.benchX3, player.benchY3, 0), true);
				} else if (object.getX() == 101 && object.getY() == 106) {
				player.bench4 = 2;
				player.benchX4 = boundChuncks[0]*8 + 37;
				player.benchY4 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13301, 10, object.getRotation(), player.benchX4, player.benchY4, 0), true);
				} else if (object.getX() == 98 && object.getY() == 109) {
				player.bench5 = 2;
				player.benchX5 = boundChuncks[0]*8 + 34;
				player.benchY5 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13301, 10, object.getRotation(), player.benchX5, player.benchY5, 0), true);
				} else if (object.getX() == 99 && object.getY() == 109) {
				player.bench6 = 2;
				player.benchX6 = boundChuncks[0]*8 + 35;
				player.benchY6 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13301, 10, object.getRotation(), player.benchX6, player.benchY6, 0), true);
				} else if (object.getX() == 100 && object.getY() == 109) {
				player.bench7 = 2;
				player.benchX7 = boundChuncks[0]*8 + 36;
				player.benchY7 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13301, 10, object.getRotation(), player.benchX7, player.benchY7, 0), true);
				} else if (object.getX() == 101 && object.getY() == 109) {
				player.bench8 = 2;
				player.benchX8 = boundChuncks[0]*8 + 37;
				player.benchY8 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13301, 10, object.getRotation(), player.benchX8, player.benchY8, 0), true);
				}
		}
	}
	public static void CheckCarvedOakBench(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 31) {
			player.getPackets().sendGameMessage("You need a construction level of 31 to build this.");
		} else if (!player.getInventory().containsItem(OAKPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 oak planks to build this.");
		} else if (!player.getInventory().containsItem(IRONNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 iron nails to build this.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bench.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 4);
			player.getInventory().deleteItem(BRONZENAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 34);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bench.");
			if (object.getX() == 98 && object.getY() == 106) {
				player.bench1 = 3;
				player.benchX1 = boundChuncks[0]*8 + 34;
				player.benchY1 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13302, 10, object.getRotation(), player.benchX1, player.benchY1, 0), true);
				} else if (object.getX() == 99 && object.getY() == 106) {
				player.bench2 = 3;
				player.benchX2 = boundChuncks[0]*8 + 35;
				player.benchY2 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13302, 10, object.getRotation(), player.benchX2, player.benchY2, 0), true);
				} else if (object.getX() == 100 && object.getY() == 106) {
				player.bench3 = 3;
				player.benchX3 = boundChuncks[0]*8 + 36;
				player.benchY3 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13302, 10, object.getRotation(), player.benchX3, player.benchY3, 0), true);
				} else if (object.getX() == 101 && object.getY() == 106) {
				player.bench4 = 3;
				player.benchX4 = boundChuncks[0]*8 + 37;
				player.benchY4 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13302, 10, object.getRotation(), player.benchX4, player.benchY4, 0), true);
				} else if (object.getX() == 98 && object.getY() == 109) {
				player.bench5 = 3;
				player.benchX5 = boundChuncks[0]*8 + 34;
				player.benchY5 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13302, 10, object.getRotation(), player.benchX5, player.benchY5, 0), true);
				} else if (object.getX() == 99 && object.getY() == 109) {
				player.bench6 = 3;
				player.benchX6 = boundChuncks[0]*8 + 35;
				player.benchY6 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13302, 10, object.getRotation(), player.benchX6, player.benchY6, 0), true);
				} else if (object.getX() == 100 && object.getY() == 109) {
				player.bench7 = 3;
				player.benchX7 = boundChuncks[0]*8 + 36;
				player.benchY7 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13302, 10, object.getRotation(), player.benchX7, player.benchY7, 0), true);
				} else if (object.getX() == 101 && object.getY() == 109) {
				player.bench8 = 3;
				player.benchX8 = boundChuncks[0]*8 + 37;
				player.benchY8 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13302, 10, object.getRotation(), player.benchX8, player.benchY8, 0), true);
				}
		}
	}
	public static void CheckTeakDiningBench(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 38) {
			player.getPackets().sendGameMessage("You need a construction level of 38 to build this.");
		} else if (!player.getInventory().containsItem(TEAKPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 teak planks to build this.");
		} else if (!player.getInventory().containsItem(MITHNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 mithril nails to build this.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bench.");
		} else {
			player.getInventory().deleteItem(TEAKPLANK, 4);
			player.getInventory().deleteItem(MITHNAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 42);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bench.");
			if (object.getX() == 98 && object.getY() == 106) {
				player.bench1 = 4;
				player.benchX1 = boundChuncks[0]*8 + 34;
				player.benchY1 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13303, 10, object.getRotation(), player.benchX1, player.benchY1, 0), true);
				} else if (object.getX() == 99 && object.getY() == 106) {
				player.bench2 = 4;
				player.benchX2 = boundChuncks[0]*8 + 35;
				player.benchY2 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13303, 10, object.getRotation(), player.benchX2, player.benchY2, 0), true);
				} else if (object.getX() == 100 && object.getY() == 106) {
				player.bench3 = 4;
				player.benchX3 = boundChuncks[0]*8 + 36;
				player.benchY3 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13303, 10, object.getRotation(), player.benchX3, player.benchY3, 0), true);
				} else if (object.getX() == 101 && object.getY() == 106) {
				player.bench4 = 4;
				player.benchX4 = boundChuncks[0]*8 + 37;
				player.benchY4 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13303, 10, object.getRotation(), player.benchX4, player.benchY4, 0), true);
				} else if (object.getX() == 98 && object.getY() == 109) {
				player.bench5 = 4;
				player.benchX5 = boundChuncks[0]*8 + 34;
				player.benchY5 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13303, 10, object.getRotation(), player.benchX5, player.benchY5, 0), true);
				} else if (object.getX() == 99 && object.getY() == 109) {
				player.bench6 = 4;
				player.benchX6 = boundChuncks[0]*8 + 35;
				player.benchY6 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13303, 10, object.getRotation(), player.benchX6, player.benchY6, 0), true);
				} else if (object.getX() == 100 && object.getY() == 109) {
				player.bench7 = 4;
				player.benchX7 = boundChuncks[0]*8 + 36;
				player.benchY7 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13303, 10, object.getRotation(), player.benchX7, player.benchY7, 0), true);
				} else if (object.getX() == 101 && object.getY() == 109) {
				player.bench8 = 4;
				player.benchX8 = boundChuncks[0]*8 + 37;
				player.benchY8 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13303, 10, object.getRotation(), player.benchX8, player.benchY8, 0), true);
				}
		}
	}
	public static void CheckCarvedTeakBench(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 44) {
			player.getPackets().sendGameMessage("You need a construction level of 44 to build this.");
		} else if (!player.getInventory().containsItem(TEAKPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 teak planks to build this.");
		} else if (!player.getInventory().containsItem(MITHNAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 mithril nails to build this.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bench.");
		} else {
			player.getInventory().deleteItem(TEAKPLANK, 4);
			player.getInventory().deleteItem(MITHNAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 47);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bench.");
			if (object.getX() == 98 && object.getY() == 106) {
				player.bench1 = 5;
				player.benchX1 = boundChuncks[0]*8 + 34;
				player.benchY1 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13304, 10, object.getRotation(), player.benchX1, player.benchY1, 0), true);
				} else if (object.getX() == 99 && object.getY() == 106) {
				player.bench2 = 5;
				player.benchX2 = boundChuncks[0]*8 + 35;
				player.benchY2 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13304, 10, object.getRotation(), player.benchX2, player.benchY2, 0), true);
				} else if (object.getX() == 100 && object.getY() == 106) {
				player.bench3 = 5;
				player.benchX3 = boundChuncks[0]*8 + 36;
				player.benchY3 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13304, 10, object.getRotation(), player.benchX3, player.benchY3, 0), true);
				} else if (object.getX() == 101 && object.getY() == 106) {
				player.bench4 = 5;
				player.benchX4 = boundChuncks[0]*8 + 37;
				player.benchY4 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13304, 10, object.getRotation(), player.benchX4, player.benchY4, 0), true);
				} else if (object.getX() == 98 && object.getY() == 109) {
				player.bench5 = 5;
				player.benchX5 = boundChuncks[0]*8 + 34;
				player.benchY5 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13304, 10, object.getRotation(), player.benchX5, player.benchY5, 0), true);
				} else if (object.getX() == 99 && object.getY() == 109) {
				player.bench6 = 5;
				player.benchX6 = boundChuncks[0]*8 + 35;
				player.benchY6 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13304, 10, object.getRotation(), player.benchX6, player.benchY6, 0), true);
				} else if (object.getX() == 100 && object.getY() == 109) {
				player.bench7 = 5;
				player.benchX7 = boundChuncks[0]*8 + 36;
				player.benchY7 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13304, 10, object.getRotation(), player.benchX7, player.benchY7, 0), true);
				} else if (object.getX() == 101 && object.getY() == 109) {
				player.bench8 = 5;
				player.benchX8 = boundChuncks[0]*8 + 37;
				player.benchY8 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13304, 10, object.getRotation(), player.benchX8, player.benchY8, 0), true);
				}
		}
	}
	public static void CheckMahoganyBench(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 52) {
			player.getPackets().sendGameMessage("You need a construction level of 52 to build this.");
		} else if (!player.getInventory().containsItem(MAHOGANYPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 mahogany planks to build this.");
		} else if (!player.getInventory().containsItem(RUNENAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 rune nails to build this.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bench.");
		} else {
			player.getInventory().deleteItem(MAHOGANYPLANK, 4);
			player.getInventory().deleteItem(RUNENAILS, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 56);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bench.");
			if (object.getX() == 98 && object.getY() == 106) {
				player.bench1 = 6;
				player.benchX1 = boundChuncks[0]*8 + 34;
				player.benchY1 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13305, 10, object.getRotation(), player.benchX1, player.benchY1, 0), true);
				} else if (object.getX() == 99 && object.getY() == 106) {
				player.bench2 = 6;
				player.benchX2 = boundChuncks[0]*8 + 35;
				player.benchY2 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13305, 10, object.getRotation(), player.benchX2, player.benchY2, 0), true);
				} else if (object.getX() == 100 && object.getY() == 106) {
				player.bench3 = 6;
				player.benchX3 = boundChuncks[0]*8 + 36;
				player.benchY3 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13305, 10, object.getRotation(), player.benchX3, player.benchY3, 0), true);
				} else if (object.getX() == 101 && object.getY() == 106) {
				player.bench4 = 6;
				player.benchX4 = boundChuncks[0]*8 + 37;
				player.benchY4 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13305, 10, object.getRotation(), player.benchX4, player.benchY4, 0), true);
				} else if (object.getX() == 98 && object.getY() == 109) {
				player.bench5 = 6;
				player.benchX5 = boundChuncks[0]*8 + 34;
				player.benchY5 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13305, 10, object.getRotation(), player.benchX5, player.benchY5, 0), true);
				} else if (object.getX() == 99 && object.getY() == 109) {
				player.bench6 = 6;
				player.benchX6 = boundChuncks[0]*8 + 35;
				player.benchY6 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13305, 10, object.getRotation(), player.benchX6, player.benchY6, 0), true);
				} else if (object.getX() == 100 && object.getY() == 109) {
				player.bench7 = 6;
				player.benchX7 = boundChuncks[0]*8 + 36;
				player.benchY7 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13305, 10, object.getRotation(), player.benchX7, player.benchY7, 0), true);
				} else if (object.getX() == 101 && object.getY() == 109) {
				player.bench8 = 6;
				player.benchX8 = boundChuncks[0]*8 + 37;
				player.benchY8 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13305, 10, object.getRotation(), player.benchX8, player.benchY8, 0), true);
				}
		}
	}
	public static void CheckGildedBench(Player player, final WorldObject object, final int[] boundChuncks) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) < 61) {
			player.getPackets().sendGameMessage("You need a construction level of 61 to build this.");
		} else if (!player.getInventory().containsItem(MAHOGANYPLANK, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 mahogany planks to build this.");
		} else if (!player.getInventory().containsItem(GOLDLEAF, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 gold leaves to build this.");
		} else if (!player.getInventory().containsItem(RUNENAILS, 4)) {
			player.getPackets().sendGameMessage("You need atleast 4 rune nails to build this.");
		} else if (!player.getInventory().containsItem(HAMMER, 1)) {
			player.getPackets().sendGameMessage("You need a hammer to build this bench.");
		} else {
			player.getInventory().deleteItem(WOODENPLANK, 4);
			player.getInventory().deleteItem(RUNENAILS, 4);
			player.getInventory().deleteItem(GOLDLEAF, 4);
			player.getSkills().addXp(Skills.CONSTRUCTION, 69);
			player.setNextAnimation(new Animation(898));
			player.getPackets().sendGameMessage("You successfully build a bench.");
			if (object.getX() == 98 && object.getY() == 106) {
				player.bench1 = 7;
				player.benchX1 = boundChuncks[0]*8 + 34;
				player.benchY1 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13306, 10, object.getRotation(), player.benchX1, player.benchY1, 0), true);
				} else if (object.getX() == 99 && object.getY() == 106) {
				player.bench2 = 7;
				player.benchX2 = boundChuncks[0]*8 + 35;
				player.benchY2 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13306, 10, object.getRotation(), player.benchX2, player.benchY2, 0), true);
				} else if (object.getX() == 100 && object.getY() == 106) {
				player.bench3 = 7;
				player.benchX3 = boundChuncks[0]*8 + 36;
				player.benchY3 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13306, 10, object.getRotation(), player.benchX3, player.benchY3, 0), true);
				} else if (object.getX() == 101 && object.getY() == 106) {
				player.bench4 = 7;
				player.benchX4 = boundChuncks[0]*8 + 37;
				player.benchY4 = boundChuncks[1]*8 + 42;
				World.spawnObject(new WorldObject(13306, 10, object.getRotation(), player.benchX4, player.benchY4, 0), true);
				} else if (object.getX() == 98 && object.getY() == 109) {
				player.bench5 = 7;
				player.benchX5 = boundChuncks[0]*8 + 34;
				player.benchY5 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13306, 10, object.getRotation(), player.benchX5, player.benchY5, 0), true);
				} else if (object.getX() == 99 && object.getY() == 109) {
				player.bench6 = 7;
				player.benchX6 = boundChuncks[0]*8 + 35;
				player.benchY6 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13306, 10, object.getRotation(), player.benchX6, player.benchY6, 0), true);
				} else if (object.getX() == 100 && object.getY() == 109) {
				player.bench7 = 7;
				player.benchX7 = boundChuncks[0]*8 + 36;
				player.benchY7 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13306, 10, object.getRotation(), player.benchX7, player.benchY7, 0), true);
				} else if (object.getX() == 101 && object.getY() == 109) {
				player.bench8 = 7;
				player.benchX8 = boundChuncks[0]*8 + 37;
				player.benchY8 = boundChuncks[1]*8 + 45;
				World.spawnObject(new WorldObject(13306, 10, object.getRotation(), player.benchX8, player.benchY8, 0), true);
				}
		}
	}

}