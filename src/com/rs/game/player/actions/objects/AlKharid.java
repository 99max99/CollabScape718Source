package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/*
 * @Author Danny
 * Al Kharid City
 */

public class AlKharid {
	
	public static void ClimbDownSmokeWell(Player player,
			final WorldObject object) {
		player.getDialogueManager().startDialogue("SmokeDungeonEntrance");
		
	}
	public static void ClimbUpSmokeWell(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(3309, 2962, 0), 1, 2);
	}
	public static void TrapDoor(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(3285, 3168, 1), 1, 2);
	}
	public static void MiningShortcut(Player player,
			final WorldObject object) {
		if (player.getSkills().getLevel(Skills.AGILITY) >= 38) {
			player.setNextAnimation(new Animation(839));
			if (player.getX() == 3295) {
			player.addWalkSteps(3293, 3309, 0, false);
			} else if (player.getX() == 3293) {
			player.addWalkSteps(3295, 3309, 1, false);
			}
		} else {
			player.getPackets().sendGameMessage("You must have an agility level of atleast 38 to use this shortcut.");
		}
	}
	public static void WorkBench(Player player,
			final WorldObject object) {
			player.getPackets().sendGameMessage("How do I work on this thing?...");
	}
	public static void Stairs(Player player,
			final WorldObject object) {
		if (object.getId() == 76478)
		player.useStairs(828, new WorldTile(3290, 3164, 2), 1, 2);
		else if (object.getId() == 76244)
		player.useStairs(828, new WorldTile(3291, 3165, 1), 1, 2);
		else if (object.getId() == 76537)
		player.useStairs(828, new WorldTile(3298, 3157, 0), 1, 2);
		else if (object.getId() == 76538)
		player.useStairs(828, new WorldTile(3300, 3162, 1), 1, 2);
		else if (object.getId() == 76225)
		player.useStairs(828, new WorldTile(3323, 3191, 0), 1, 2);
		else if (object.getId() == 76279)
		player.useStairs(828, new WorldTile(3322, 3195, 1), 1, 2);
		else if (object.getId() == 63583)
		player.useStairs(828, new WorldTile(3449, 3174, 1), 1, 2);
		else if (object.getId() == 63584)
		player.useStairs(828, new WorldTile(3446, 3177, 0), 1, 2);
		else if (object.getId() == 63585)
		player.useStairs(828, new WorldTile(3447, 3179, 2), 1, 2);
		else if (object.getId() == 63586)
		player.useStairs(828, new WorldTile(3451, 3176, 1), 1, 2);
		else if (object.getId() == 63587)
		player.useStairs(828, new WorldTile(3447, 3179, 3), 1, 2);
		else if (object.getId() == 63588)
		player.useStairs(828, new WorldTile(3450, 3175, 2), 1, 2);
	}
	public static void ShantayPass(Player player,
			final WorldObject object) {
		if (object.getX() == player.getX() && object.getY() > player.getY()) {
			player.setRun(false);
			  player.addWalkSteps((player.getX()), (player.getY() + 3), -1, false);
			  
			 } else if (object.getX() == player.getX() && object.getY() < player.getY()) {
					player.setRun(false);
			  player.addWalkSteps((player.getX()), (player.getY() - 3), -1, false);
			 } else {
			  player.getPackets().sendGameMessage("You cannot go through from this angle.");
			  return;
			 }
			}
	public static void ShantayPassWarning(Player player,
			final WorldObject object) {
		player.getDialogueManager().startDialogue("ShantayWarning");
			}
	public static void ShantayBank(Player player,
			final WorldObject object) {
			player.getBank().openBank();
			}
	public static void Bricks(Player player,
			final WorldObject object) {
			player.getPackets().sendGameMessage("Now why would you want to climb bricks when there are stairs next to you?");
			player.getPackets().sendGameMessage("Silly Ezio, this isn't Assassin's Creed.");
			}
	public static void OrnateChest(Player player,
			final WorldObject object) {
		player.setNextAnimation(new Animation(536));
		player.lock(2);
		WorldObject openedChest = new WorldObject(object.getId()+1,
				object.getType(), object.getRotation(), object.getX(),
				object.getY(), object.getPlane());	
		//if (World.removeTemporaryObject(object, 60000, true)) {
		player.faceObject(openedChest);
		World.spawnTemporaryObject(openedChest, 60000, true);
			}
	public static void SearchChest(Player player,
			final WorldObject object) {
	player.getPackets().sendGameMessage("You search the chest but find nothing.");
		}
	public static void Clock(Player player,
			final WorldObject object) {
	player.getPackets().sendGameMessage("I better not mess with this.");
		}
	public static void Stairs2(Player player,
			final WorldObject object) {
	player.getPackets().sendGameMessage("I don't have permission to go down these stairs.");
		}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 36002:
		case 6439:
		case 12774:
		case 76651:
		case 76652:
		case 2693:
		case 75865:
		case 76478:
		case 76244:
		case 76537:
		case 76538:
		case 75919:
		case 76216:
		case 76217:
		case 76225:
		case 76279:
		case 76210:
		case 63199:
		case 63583:
		case 63584:
		case 63585:
		case 63586:
		case 63587:
		case 63588:
		case 63972:
		case 63589:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 36002) { //Smoky well
			AlKharid.ClimbDownSmokeWell(player, object); //Method of Action
		}
		if (id == 6439) { //Smoky well
			AlKharid.ClimbUpSmokeWell(player, object); //Method of Action
		}
		if (id == 12774) { //shantay pass
			AlKharid.ShantayPass(player, object); //Method of Action
		}
		if (id == 76651 || id == 76652) { //shantay pass warning
			AlKharid.ShantayPassWarning(player, object); //Method of Action
		}
		if (id == 2693) { //shantay pass bank chest
			AlKharid.ShantayBank(player, object); //Method of Action
		}
		if (id == 75865) { //Trapdoor
			AlKharid.TrapDoor(player, object); //Method of Action
		}
		if (id == 76478 || id == 76244 || id == 76537 || id == 76538 || id == 76279 || id == 76225
				|| id == 63583 || id == 63584 || id == 63585 || id == 63586 || id == 63587 || id == 63588
				) { //Stairs
			AlKharid.Stairs(player, object); //Method of Action
		}
		if (id == 75919) { //Bricks
			AlKharid.Bricks(player, object); //Method of Action
		}
		if (id == 76216) { //Ornate Chest
			AlKharid.OrnateChest(player, object); //Method of Action
		}
		if (id == 76217) { //Search Chest
			AlKharid.SearchChest(player, object); //Method of Action
		}
		if (id == 76210) { //Mining Shortcut
			AlKharid.MiningShortcut(player, object); //Method of Action
		}
		if (id == 76210) { //Work Bench
			AlKharid.WorkBench(player, object); //Method of Action
		}
		if (id == 63972) { //Operate Clock
			AlKharid.Clock(player, object); //Method of Action
		}
		if (id == 63589) { //Stairs you can't go down
			AlKharid.Stairs2(player, object); //Method of Action
		}
	}

}
