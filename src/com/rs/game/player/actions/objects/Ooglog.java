package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Misc;

/*
 * @Author Danny
 * Ooglog
 */

public class Ooglog {
	
	public static void Passage(final Player player,
			final WorldObject object) {
		if (player.getSkills().getLevel(Skills.AGILITY) >= 29) {
			WorldTasksManager.schedule(new WorldTask() {
				boolean secondloop;

				@Override
				public void run() {
					if (!secondloop) {
						secondloop = true;
						player.getAppearence().setRenderEmote(155);
						if (player.getY() == 2869) {
							player.addWalkSteps(2596, 2871, 0, false);
							} else if (player.getY() == 2871) {
							player.addWalkSteps(2596, 2869, 0, false);
							}
						player.sm("You squeeze through the passage...");
					} else {
						player.getAppearence().setRenderEmote(-1);
						player.getPackets().sendGameMessage(
								"... and make it safely to the other side.", true);
						stop();
					}
				}
			}, 0, 1);
		} else {
			player.getPackets().sendGameMessage("You must have an agility level of atleast 29 to use this shortcut.");
		}
	}
	
	public static void Baths(final Player player,
			final WorldObject object) {
		if (object.getX() == player.getX()) {
			if (player.getY() > object.getY())
				player.addWalkSteps(player.getX(), (player.getY() - 3), -1, false);
			else
				player.addWalkSteps(player.getX(), (player.getY() + 3), -1, false);
		} else if (object.getY() == player.getY()) {
			if (player.getX() > object.getX())
				player.addWalkSteps((player.getX() - 3), player.getY(), -1, false);
			else
				player.addWalkSteps((player.getX() + 3), player.getY(), -1, false);
		}
		player.setNextAnimation(new Animation(839));
		if (!player.inBath) {
			player.inBath = true;
			player.getAppearence().setRenderEmote(846);
			if (object.getId() == 25097 || object.getId() == 25098) {
				WorldTasksManager.schedule(new WorldTask() {
					int loop;

					@Override
					public void run() {
						if (loop == 0) {
							player.getPackets().sendGameMessage("You step into the Bandos Spa...");
						} else if (loop == 1) {
							player.getPackets().sendGameMessage("You feel the favour of Bandos wash over you...");
						} else if (loop == 2) {
							player.getPackets().sendGameMessage("For the next hour, the Bandos faction in the God Wars Dungeon will not be agressive towards you.");
							player.bandosBath = true;
						} else if (loop == 3602) {
							player.getPackets().sendGameMessage("The effect of the Bandos Spa has worn off...");
							player.bandosBath = false;
							stop();
						} 
						loop++;
					}
				}, 0, 1);
			} else if (object.getId() == 29018) {
				WorldTasksManager.schedule(new WorldTask() {
					int loop;

					@Override
					public void run() {
						if (loop == 0) {
							player.getPackets().sendGameMessage("You step into the Sulfur Spa...");
						} else if (loop == 1) {
							player.getPackets().sendGameMessage("You feel serene after your rest. Your Prayer points have been restored...");
						} else if (loop == 2) {
							player.getPackets().sendGameMessage("Your prayer has been restored by 110%.");
							int bonus = player.getSkills().getLevelForXp(Skills.PRAYER);
							if (player.getPrayer().getPrayerpoints() < ((player.getSkills()
									.getLevelForXp(Skills.PRAYER) * 10) + bonus)) {
							player.getPrayer().setPrayerpoints(player.getPrayer().getPrayerpoints() + bonus);
							player.getPrayer().refreshPrayerPoints();
							}
							stop();
						} 
						loop++;
					}
				}, 0, 1);
			} else if (object.getId() == 29031 || object.getId() == 29032) {
				WorldTasksManager.schedule(new WorldTask() {
					int loop;
					int minutes = Misc.random(10, 25);
					@Override
					public void run() {
						if (loop == 0) {
							player.getPackets().sendGameMessage("You step into the Salt-Water Spa...");
						} else if (loop == 1) {
							player.getPackets().sendGameMessage("You feel energised and hastened after your relaxing soak...");
						} else if (loop == 2) {
							player.getPackets().sendGameMessage("For the next "+minutes+" minutes, your run energy will be unlimited.");
							player.runBath = true;
						} else if (loop == (minutes*60)) {	
							player.runBath = false;
							stop();
						} 
						loop++;
					}
				}, 0, 1);
			} else if (object.getId() == 29045 || object.getId() == 29044) {
				WorldTasksManager.schedule(new WorldTask() {
					int loop;
					int minutes = Misc.random(1, 15);
					@Override
					public void run() {
						if (loop == 0) {
							player.getPackets().sendGameMessage("You step into the Thermal Spa...");
						} else if (loop == 1) {
							player.getPackets().sendGameMessage("You feel restored and invigorated...");
						} else if (loop == 2) {
							int bonus = (int) (player.getMaxHitpoints() / 10);
							player.getPackets().sendGameMessage("For the next "+minutes+" minutes, you are protected from poison and your hitpoints have been boosted.");
							player.healthBath = true;
							if (player.getHitpoints() < (player.getMaxHitpoints() + bonus)) {
							player.setHitpoints(player.getMaxHitpoints() + bonus);
							player.refreshHitPoints();
							}
						} else if (loop == (minutes*60)) {	
							player.healthBath = false;
							stop();
						} 
						loop++;
					}
				}, 0, 1);
			} else if (object.getId() == 29004) {
				WorldTasksManager.schedule(new WorldTask() {
					int loop;
					@Override
					public void run() {
						if (loop == 0) {
							player.getPackets().sendGameMessage("You step into the Mud Spa...");
						} else if (loop == 1) {
							player.getPackets().sendGameMessage("After a bath in such luxuriant mud, you feel your Hunter skills are extra keen...");
						} else if (loop == 2) {
							int bonus = (int) (player.getSkills().getLevelForXp(Skills.HUNTER) / 10);
							player.getPackets().sendGameMessage("Your hunter skills have been increased temporarily.");
							if (player.getSkills().getLevelForXp(Skills.HUNTER) < (player.getSkills().getLevelForXp(Skills.HUNTER) + bonus)) {
							player.getSkills().set(Skills.HUNTER, (player.getSkills().getLevel(Skills.HUNTER) + bonus));
							}
							stop();
						}
						loop++;
					}
				}, 0, 1);
			}
		} else {
			player.inBath = false;
			player.getAppearence().setRenderEmote(-1);
		}
	}
	
	public static void Machine(final Player player,
			final WorldObject object) {
		if (player.getInventory().containsItem(23194, 1)) {
			WorldTasksManager.schedule(new WorldTask() {
				boolean secondloop;

				@Override
				public void run() {
					if (!secondloop) {
						secondloop = true;
						player.sm("You place the red sandstone in the machine...");
						for (int i = 0; i <= 28; i++) {
							makeGlass(player);
						}
						player.setNextAnimation(new Animation(881));
					} else {
						player.getPackets().sendGameMessage(
								"... and create some robust glass.", true);
						stop();
					}
				}
			}, 0, 1);
		} else {
			player.getPackets().sendGameMessage("You need some red sandstone to make robust glass.");
		}
	}
	
	public static void makeGlass(final Player player) {
		if (player.getInventory().containsItem(23194, 1)) {
			player.getInventory().deleteItem(23194, 1);
			player.getInventory().addItem(23193, 1);
		}	
	}
	
	
	
	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 29004:
		case 29057:
		case 29058:
		case 29018:
		case 29031:
		case 29032:
		case 29044:
		case 29045:
		case 29099:
		case 67968:
		return true;
		default:
		return false;
		}
	}

	
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 29004 || id == 29057 || id == 29058 || id == 29018 || id == 29031 || id == 29032 || id == 29044 || id == 29045) { //Object ID
			Ooglog.Baths(player, object); //Method of Action
		}
		if (id == 29099) { //Object ID
			Ooglog.Passage(player, object); //Method of Action
		}
	}

}
