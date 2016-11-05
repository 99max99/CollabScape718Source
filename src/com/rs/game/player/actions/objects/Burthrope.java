package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/*
 * @Author Danny
 * Burthrope City
 */

public class Burthrope {
	
	public static void ClimbGiant(Player player,
			final WorldObject object) {
		if (player.getSkills().getLevel(Skills.AGILITY) < 41) {
			player.getPackets().sendGameMessage("You must have an agility level of 41 to use this shortcut.");
		} else {
		if (player.getY() == 3594 || player.getY() == 3593) {
			player.addWalkSteps(2876, 3598, -1, false);
		} else if (player.getY() == 3598) {
			player.addWalkSteps(2875, 3594, -1, false);
		}
		}
	}
	
	public static void CaveEntrance(Player player,
			final WorldObject object) {
		if (object.getX() == 2910)
		player.useStairs(828, new WorldTile(2921, 3658, 0), 1, 2);
		else
		player.useStairs(828, new WorldTile(2911, 3636, 0), 1, 2);
		player.getPackets().sendGameMessage("You venture through the caves.");
	}
	
	public static void ClimbOver(Player player,
			final WorldObject object) {
		if (player.getSkills().getLevel(Skills.AGILITY) < 47) {
			player.getPackets().sendGameMessage("You must have an agility level of 47 to use this shortcut.");
		} else {
			if (object.getRotation() == 0 || object.getRotation() == 2) {
				if (player.getY() > object.getY())
					player.addWalkSteps(player.getX(), (player.getY() - 3), -1, false);
				else
					player.addWalkSteps(player.getX(), (player.getY() + 3), -1, false);
			} else if (object.getRotation() == 1 || object.getRotation() == 3) {
				if (player.getX() > object.getX())
					player.addWalkSteps((player.getX() - 3), player.getY(), -1, false);
				else
					player.addWalkSteps((player.getX() + 3), player.getY(), -1, false);
			}
			player.setNextAnimation(new Animation(839));
		}
	}
	
	public static void PushBoulder(Player player,
			final WorldObject object) {
		if (player.getSkills().getLevel(Skills.STRENGTH) < 60) {
			player.getPackets().sendGameMessage("You must have a strength level of 60 to push this boulder.");
		} else {
					player.addWalkSteps(2907, 3713, -1, false);
		}
	}
	
	public static void EnterGodWars(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2882, 5311, 0), 1, 2);
		player.getPackets().sendGameMessage("You enter the Godwars Dungeon.");
	}
	
	public static void ExitGodWars(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2916, 3738, 0), 1, 2);
		player.getPackets().sendGameMessage("You climb up the rope and escape the dungeon.");
	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 2507:
		case 34878:
		case 3748:
		case 35391:
		case 34395:
		case 35390:
		case 26341:
		case 26293:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 34878) { 
			Burthrope.ClimbGiant(player, object); 
		}
		if (id == 3748 || id == 35391) { 
			Burthrope.ClimbOver(player, object); 
		}
		if (id == 34395) { 
			Burthrope.CaveEntrance(player, object); 
		}
		if (id == 35390) { 
			Burthrope.PushBoulder(player, object); 
		}
		if (id == 26341) { 
			Burthrope.EnterGodWars(player, object); 
		}
		if (id == 26293) { 
			Burthrope.ExitGodWars(player, object); 
		}
	}

}
