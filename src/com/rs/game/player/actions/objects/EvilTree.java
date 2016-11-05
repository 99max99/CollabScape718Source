package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Misc;

/*
 * @Author Danny
 * Evil Tree
 */

public class EvilTree {
	
	public static int health;
	public static int rootX2;
	public static int rootY2;
	
	public static void processReward(Player player) {
		if (player.treeDamage <= 0) {
			player.sm("You did not do enough damage to the Evil Tree to recieve a reward.");
		} else {
			int chance = Misc.random(5000 - player.treeDamage);
			if (chance >= 0 && chance <= 10) { //Chance of giving Blisterwood Items
				int blister = Misc.random(6);
				if (blister == 1) 
					player.getInventory().addItem(21580, 1);
				else if (blister == 2)
					player.getInventory().addItem(21581, 100);
				else if (blister == 3)
					player.getInventory().addItem(21582, 1);
			} else 	if (chance >= 11 && chance <= 50) { //Chance of giving LumberJack items
				int lumber = Misc.random(1, 5);
				if (lumber == 1) 
					player.getInventory().addItem(10933, 1);	
				else if (lumber == 2)
					player.getInventory().addItem(10939, 1);	
				else if (lumber == 3)
					player.getInventory().addItem(10940, 1);	
				else if (lumber == 4)
					player.getInventory().addItem(10941, 1);
				else
					player.getInventory().addItem(10945, 1);
			}
			player.getSkills().addXp(Skills.WOODCUTTING, player.treeDamage*3);
			player.getInventory().addItemMoneyPouch(995, Misc.random(5000, 100000)); //Cash Reward always
			player.getInventory().addItem(1514, Misc.random(10, 20)); //Start Randomized Log Rewards
			player.getInventory().addItem(1516, Misc.random(10, 30));
			player.getInventory().addItem(1518, Misc.random(10, 40));
			player.getInventory().addItem(1520, Misc.random(10, 50));
			player.getInventory().addItem(1522, Misc.random(10, 60)); //End Randomized Log Rewards
			player.treeDamage = 0;
		}
	}
	
	public static void rootAttack(final Player player) {
		int attack = Misc.random(7);
		int rootX = player.getX();
		int rootY = player.getY();
		rootX2 = player.getX();
		rootY2 = player.getY();
		final WorldObject movingRoot = new WorldObject(11426,
				10, 0, rootX,
				rootY, 0);
		if (attack == 1) {
			WorldTasksManager.schedule(new WorldTask() {
				int loop = 0;
				@Override
				public void run() {
					if (loop == 0) {
						World.spawnObject(movingRoot, true);
					} else if (loop == 1) {
						player.lock(1);
						if (player.getX() > 2456) {
							player.addWalkSteps(player.getX() + 1, player.getY(), 0, false);
						} else if (player.getX() < 2456) {
							player.addWalkSteps(player.getX() - 1, player.getY(), 0, false);
						} else if (player.getY() > 2835) {
							player.addWalkSteps(player.getX(), player.getY() + 1, 0, false);
						} else if (player.getY() < 2835) {
							player.addWalkSteps(player.getX(), player.getY() - 1, 0, false);
						}
						player.setNextAnimation(new Animation(846));
						stop();
					} 
					loop++;
				}
			}, 0, 1);
		}
	}
	
	public static void ChopTree(final Player player,
			final WorldObject object) {
		if (!player.isChopping) {
		rootAttack(player);
		if (hasAxe(player)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			int emote = emoteId(player);
			@Override
			public void run() {
				if (health <= 0)
					stop();
				if (loop == 0) {
					player.isChopping = true;
					player.getPackets().sendGameMessage("You chop away some of the evil tree.");
					player.setNextAnimation(new Animation(emote));
					health--;
					player.treeDamage++;
					player.totalTreeDamage++;
				} else if (loop == 2) {
					player.getPackets().sendGameMessage("You chop away some of the evil tree.");
					player.setNextAnimation(new Animation(emote));
					health--;
					player.treeDamage++;
					player.totalTreeDamage++;
				} else if (loop == 4) {
					player.getPackets().sendGameMessage("You chop away some of the evil tree.");
					player.setNextAnimation(new Animation(emote));
					health--;
					player.treeDamage++;
					player.totalTreeDamage++;
				} else if (loop == 6) {
					player.getPackets().sendGameMessage("You chop away some of the evil tree.");
					player.setNextAnimation(new Animation(emote));
					health--;
					player.treeDamage++;
					player.totalTreeDamage++;
				} else if (loop == 8) {
					player.isChopping = false;
					player.getPackets().sendGameMessage("You chop away some of the evil tree.");
					player.setNextAnimation(new Animation(emote));
					health--;
					player.treeDamage++;
					player.totalTreeDamage++;
					stop();
				} 
				loop++;
			}
		}, 0, 1);
		} else {
			player.sm("You need an axe to chop this Evil Tree down.");
		}
		} else {
			player.sm("You are already chopping the evil tree.");
		}
	}
	
	public static void chopRoot(final Player player,
			final WorldObject object) {
		final WorldObject movingRoot = new WorldObject(11426,
				10, 0, object.getX(),
				object.getY(), 0);
		if (!player.isRooting) {
		if (hasAxe(player)) {
		player.isRooting = true;
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			int emote = emoteId(player);
			@Override
			public void run() {
				int amount = Misc.random(1,5);
				if (loop == 0) {
					player.getPackets().sendGameMessage("You begin to chop the root.");
					player.setNextAnimation(new Animation(emote));
					player.getInventory().addItem(14666, amount);
				} else if (loop == 2) {
					player.getPackets().sendGameMessage("The root begins to weaken.");
					player.setNextAnimation(new Animation(emote));
					player.getInventory().addItem(14666, amount);
				} else if (loop == 4) {
					player.getPackets().sendGameMessage("The root begins to give way.");
					player.setNextAnimation(new Animation(emote));
					player.getInventory().addItem(14666, amount);
				} else if (loop == 6) {
					player.getPackets().sendGameMessage("The root shrivels up.");
					player.setNextAnimation(new Animation(emote));
					player.getInventory().addItem(14666, amount);
				} else if (loop == 8) {
					player.getPackets().sendGameMessage("You successfully chop down the root.");
					World.removeObject(movingRoot, true);
					World.deleteObject(new WorldTile(object.getX(), object.getY(), 0));
					World.deleteObject(object.getX(), object.getY(), true);
					player.getInventory().addItem(14666, amount);
					player.isRooting = false;
					stop();
				} 
				loop++;
			}
		}, 0, 1);
		} else {
			player.sm("You need an axe to chop this Evil Tree down.");
		}
		} else {
			player.sm("You are already chopping this root.");
		}
	}
	
	public static void LightTree(final Player player,
			final WorldObject object) {
		if (!player.isLighting) {
		if (player.getInventory().containsItemToolBelt(590, 1)) {
			if (player.getInventory().containsItem(14666, 1)) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop = 0;
			@Override
			public void run() {
				if (health <= 0)
					stop();
				if (loop == 0) {
					player.isLighting = true;
					player.getPackets().sendGameMessage("You begin to set the evil tree on fire...");
					player.setNextAnimation(new Animation(16700));
				} else if (loop == 1) {
					player.getPackets().sendGameMessage("You successfully catch a tree on fire!");
				} else if (loop == 2) {
					player.getPackets().sendGameMessage("The fire continues to burn on the evil tree.");
					player.getInventory().deleteItem(14666, 1);
					health--;
					health--;
					health--;
					player.treeDamage++;
					player.treeDamage++;
					player.treeDamage++;
					player.totalTreeDamage++;
					player.totalTreeDamage++;
					player.totalTreeDamage++;
				} else if (loop == 3) {
					player.isLighting = false;
					player.getPackets().sendGameMessage("You have run out of kindel to fuel the fire.");
					stop();
				} 
				if (player.getInventory().containsItem(14666, 1)) {
					loop = 1;
				}
				loop++;
			}
		}, 0, 1);
			} else {
				player.sm("You need some evil tree kindling in order to light this on fire.");
			}
		} else {
			player.sm("You need a tinderbox to burn the Evil Tree down.");
		}
		} else {
			player.sm("You are already lighting this evil tree on fire.");
		}
	}
	
	public static void InspectTree(Player player,
			final WorldObject object) {
		player.sm("The tree's health is currently at "+health+", you have dealt "+player.treeDamage+" damage to the Evil Tree.");
	}
	
	public static int emoteId(Player player) {
		int emote = -1;
		int level = player.getSkills().getLevel(8);
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId != -1) {
			switch (weaponId) {
			case 6739: // dragon axe
				if (level >= 61)
					emote = 2846;
				break;
			case 1359: // rune axe
				if (level >= 41)
					emote = 867;
				break;
			case 1357: // adam axe
				if (level >= 31)
					emote = 869;
				break;
			case 1355: // mit axe
				if (level >= 21)
					emote = 871;
				break;
			case 1361: // black axe
				if (level >= 11)
					emote = 873;
				break;
			case 1353: // steel axe
				if (level >= 6)
					emote = 875;
				break;
			case 1349: // iron axe
				emote = 877;
				break;
			case 1351: // bronze axe
				emote = 879;
				break;
			case 13661: // Inferno adze
				if (level >= 61)
					emote = 10251;
				break;
			}
		}
		if (player.getInventory().containsOneItem(6739)) {
			if (level >= 61)
				emote = 2846;
		}
		if (player.getInventory().containsOneItem(1359)) {
			if (level >= 41)
				emote = 867;
		}
		if (player.getInventory().containsOneItem(1357)) {
			if (level >= 31)
				emote = 869;
		}
		if (player.getInventory().containsOneItem(1355)) {
			if (level >= 21)
				emote = 871;
		}
		if (player.getInventory().containsOneItem(1361)) {
			if (level >= 11)
				emote = 873;
		}
		if (player.getInventory().containsOneItem(1353)) {
			if (level >= 6)
				emote = 875;
		}
		if (player.getInventory().containsOneItem(1349)) {
			emote = 877;
		}
		if (player.getInventory().containsOneItem(1351)) {
			emote = 879;
		}
		if (player.getInventory().containsOneItem(13661)) {
			if (level >= 61)
				emote = 10251;
		}
		return emote;
	}
	
	private static boolean hasAxe(Player player) {
		if (player.getInventory().containsOneItem(1351, 1349, 1353, 1355, 1357,
				1361, 1359, 6739, 13661))
			return true;
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId == -1)
			return false;
		switch (weaponId) {
		case 1351:// Bronze Axe
		case 1349:// Iron Axe
		case 1353:// Steel Axe
		case 1361:// Black Axe
		case 1355:// Mithril Axe
		case 1357:// Adamant Axe
		case 1359:// Rune Axe
		case 6739:// Dragon Axe
		case 13661: // Inferno adze
			return true;
		default:
			return false;
		}

	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 11922:
		case 11426:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object, int option) {
		final int id = object.getId();
		if (id == 11922) { 
			if (option == 1)
			EvilTree.ChopTree(player, object); 
			else if (option == 2)
			EvilTree.LightTree(player, object);
			else if (option == 3)
			EvilTree.InspectTree(player, object);
		}
		if (id == 11426) {
			EvilTree.chopRoot(player, object);
		}
		
	}

}
