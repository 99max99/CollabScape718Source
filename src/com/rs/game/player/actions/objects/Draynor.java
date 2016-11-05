package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.decoders.handlers.ObjectHandler;
import com.rs.utils.Misc;
import com.rs.utils.Utils;

/*
 * @Author Danny
 * Draynor Village
 */

public class Draynor {
	
	public static void UnderGround(Player player,
			final WorldObject object) {
		if (object.getX() == 3118 && object.getY() == 3244) {
		player.useStairs(827, new WorldTile(3118, 9643, 0), 1, 2);
		} else if (object.getX() == 3084 && object.getY() == 3272){
		player.useStairs(827, new WorldTile(3085, 9672, 0), 1, 2);
		}
	}
	public static void UnderGroundExit(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(3117, 3244, 0), 1, 2);
	}
	public static void openManorDoor(Player player,
			final WorldObject object) {
		if (isInsideMansion(player)) {
			player.getPackets().sendGameMessage("It's locked!");
		} else {
			ObjectHandler.handleDoor(player, object, 300);
			player.setNextWorldTile(new WorldTile(3109, 3354, 0));
			player.getPackets().sendGameMessage("As you aproach the door you get sucked in.");
		}
	}
	public static void playPiano(Player player,
			final WorldObject object) {
			player.getPackets().sendGameMessage("You attempt to play the piano, but just suck so badly.");
	}
	public static void UnderGroundSecondExit(Player player,
			final WorldObject object) {
		if (object.getX() == 3117 && object.getY() == 9754) {
			player.useStairs(828, new WorldTile(3093, 3361, 0), 1, 2);	
		} else if (object.getX() == 3103 && object.getY() == 9576) {
			player.useStairs(828, new WorldTile(3104, 3161, 0), 1, 2);	
		} else {
			player.useStairs(828, new WorldTile(3084, 3273, 0), 1, 2);
		}
	}
	public static void TrapDoor(Player player,
			final WorldObject object) {
		Item oilCan = new Item(277);
		World.addGroundItem(oilCan, new WorldTile(3104, 9753, 0), player, true, 180);
		player.useStairs(828, new WorldTile(3117, 9753, 0), 1, 2);
	}
	public static void VampyreStairs(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(3116, 3355, 0), 1, 2);
	}
	public static void Coffin(Player player,
			final WorldObject object) {
		if (player.VS == 3) {
		if (player.getInventory().containsItem(1549, 1) && player.getInventory().containsItem(15417, 1)) {
		World.spawnNPC(9356, new WorldTile(3079, 9786, 0), -1, true);
		player.getPackets().sendGameMessage("The Count of Draynor Manor has appeared!");
		} else {
		player.getPackets().sendGameMessage("I should get my stake and stake hammer first...");
		}
		} else if (player.VS == 4) {
		player.getPackets().sendGameMessage("Maybe I should report back to Morgan.");
		} else {
		player.getPackets().sendGameMessage("I don't think I should mess with this coffin.");	
		}
	}
	public static void Pottery(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("Pottery is not available on CollabScape yet...");
	}
	public static void Skulls(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("I am not a member of the Skulls, I cannot enter here...");
	}
	public static void Candle(Player player,
			final WorldObject object) {
		player.addWalkSteps(3096, 3359, 1, false);
		player.getPackets().sendGameMessage("The wall moves and you get through it, your like a fucking ghost!");
	}
	public static void Lever(Player player,
			final WorldObject object) {
		player.addWalkSteps(3097, 3358, 1, false);
		player.getPackets().sendGameMessage("The wall moves and you get through it, your like a fucking ghost!");
	}
	public static void climbManor(Player player,
			final WorldObject object) {
			player.setNextWorldTile(new WorldTile(3109, 3366, 1));
			player.getPackets().sendGameMessage("You climb up the staircase.");
	}
	public static void downManor(Player player,
			final WorldObject object) {
			player.setNextWorldTile(new WorldTile(3109, 3361, 0));
			player.getPackets().sendGameMessage("You climb down the staircase.");
	}
	public static void stairsBasement(Player player,
			final WorldObject object) {
			player.setNextWorldTile(new WorldTile(3080, 9776, 0));
			player.getPackets().sendGameMessage("You climb down the staircase.");
	}
	public static void downWizard(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(3104, 9576, 0), 1, 2);
	}
	public static void ProfDoor(Player player,
			final WorldObject object) {
		Item fishFood = new Item(272);
		Item poison = new Item(273);
		Item spade = new Item(952);
		if ((object.getX() == 3108 && object.getY() == 3364)) {
			if (player.getX() == 3108) {
				ObjectHandler.handleDoor(player, object, 3000);
			} else {
			player.getDialogueManager().startDialogue("ProfessorOddenstein");
			}
		} else {
			ObjectHandler.handleDoor(player, object, 3000);
			if (!player.getInventory().containsItem(272, 1) && player.EC == 2 && player.fishfood == false) {
				player.fishfood = true;
				World.addGroundItem(fishFood, new WorldTile(3108, 3356, 1), player, true, 180);
			}
			if (!player.getInventory().containsItem(273, 1) && player.EC == 2 && player.poison == false) {
				player.poison = true;
				World.addGroundItem(poison, new WorldTile(3098, 3366, 0), player, true, 180);
			}
			if (!player.getInventory().containsItem(952, 1) && player.EC == 2 && player.spade == false) {
				player.spade = true;
				World.addGroundItem(spade, new WorldTile(3121, 3362, 0), player, true, 180);
			}
		}
	}
	public static void SearchFountain(final Player player,
			final WorldObject object) {
		if (player.EC == 2 && player.getInventory().containsItem(274, 1)) {
			if (player.EC == 2) {
				if (player.getBoneDelay() > Utils.currentTimeMillis())
					return;
				player.addBoneDelay(5000);
				WorldTasksManager.schedule(new WorldTask() {
					int loop;

					@Override
					public void run() {
						if (loop == 0) {
							player.getPackets().sendGameMessage("You pour the poisoned fish food into the fountain.");
							player.setNextAnimation(new Animation(833));
						} else if (loop == 1) {
							player.getPackets().sendGameMessage("The piranhas start eating the food...");
						} else if (loop == 3) {
							player.getDialogueManager().startDialogue("PressureGauge");
							stop();
						} 
						loop++;
					}
				}, 0, 1);
			} else {
				player.getPackets().sendGameMessage("Why would I look in there?");
			}
		} else {
			player.getPackets().sendGameMessage("Why would I look in there?");
		}
	}
	public static void UnlockSmallDoor(Player player,
			final WorldObject object) {
		Item rubberTube = new Item(276);
		if (player.getInventory().containsItem(275, 1) && !insideSkeletonRoom(player)) {
			boolean there = insideSkeletonRoom(player);
			player.getPackets().sendGameMessage("You unlock the door and step " + (there ? "outside." : "inside."));
			if (player.rubberTube == false && player.EC == 2) {
				World.addGroundItem(rubberTube, new WorldTile(3111, 3367, 0), player, true, 180);
				player.rubberTube = true;
			}
			player.setNextWorldTile(new WorldTile(3107, 3368, 0));
		} else if (player.getInventory().containsItem(275, 1) && insideSkeletonRoom(player)) {
			boolean there = insideSkeletonRoom(player);
			player.getPackets().sendGameMessage("You unlock the door and step " + (there ? "outside." : "inside."));
			player.setNextWorldTile(new WorldTile(3107, 3369, 0));
		} else {
			player.getPackets().sendGameMessage("This door is locked.");
		}
	}
	public static void studyMap(Player player,
			final WorldObject object) {
		// world map open
		player.getPackets().sendWindowsPane(755, 0);
		int posHash = player.getX() << 14 | player.getY();
		player.getPackets().sendGlobalConfig(622, posHash); // map open
		// center
		// pos
		player.getPackets().sendGlobalConfig(674, posHash); // player
		// position
	}
	public static void taunt(Player player,
			final WorldObject object) {
		String taunts[] = {"You fucking slut!", "You're a little piece of shit!", "Rot in your cage faggot!", "My dick is bigger than yours!", "COME FIGHT ME BRAH!", "Yo mamma!"};
		int i = Misc.random(5);
		player.setNextForceTalk(new ForceTalk(taunts[i]));
		player.setNextAnimation(new Animation(859));
	}
	public static void Compost(Player player,
			final WorldObject object) {
	if (player.EC == 2 && player.key == false && player.getInventory().containsItem(952, 1)) {
		player.getPackets().sendGameMessage("You dig the compost and find a small key.");
		player.setNextAnimation(new Animation(830));
		player.getInventory().addItem(275, 1);
		player.key = true;
	} else if (player.key == true && player.getInventory().containsItem(952, 1)) {
		player.setNextAnimation(new Animation(830));
		player.getPackets().sendGameMessage("You dig the compost and find nothing.");
	} else {
		player.getPackets().sendGameMessage("You need a spade to dig.");
	}
	}
	public static boolean insideSkeletonRoom(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return (destX > 3104 && destY < 3369 && destX < 3113 && destY > 3365);
	}
	
	public static boolean isInsideMansion(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return ((destX >= 3090 && destY <= 3363 && destX <= 3126 && destY >= 3354) || (destX >= 3097 && destY <= 3373 && destX <= 3119
				&& destY >= 3364));
	}
	public static void Stairs(Player player,
			final WorldObject object) {
		if (object.getId() == 2347) {
			if (object.getX() == 3100 && object.getY() == 3255)
				player.useStairs(828, new WorldTile(3102, 3255, 1), 1, 2);
			else if (object.getX() == 3091 && object.getY() == 3251)
				player.useStairs(828, new WorldTile(3093, 3251, 1), 1, 2);
			else if (object.getX() == 3084 && object.getY() == 3262)
				player.useStairs(828, new WorldTile(3083, 3262, 1), 1, 2);
			else if (object.getX() == 3100 && object.getY() == 3266)
				player.useStairs(828, new WorldTile(3102, 3266, 1), 1, 2);
			else if (object.getX() == 3098 && object.getY() == 3281)
				player.useStairs(828, new WorldTile(3097, 3281, 1), 1, 2);
			else if (object.getX() == 3092 && object.getY() == 3281)
				player.useStairs(828, new WorldTile(3094, 3281, 1), 1, 2);
		} else if (object.getId() == 2348) {
			if (object.getX() == 3100 && object.getY() == 3255)
				player.useStairs(828, new WorldTile(3099, 3255, 0), 1, 2);
			else if (object.getX() == 3091 && object.getY() == 3251)
				player.useStairs(828, new WorldTile(3090, 3251, 0), 1, 2);
			else if (object.getX() == 3084 && object.getY() == 3262)
				player.useStairs(828, new WorldTile(3086, 3262, 0), 1, 2);
			else if (object.getX() == 3100 && object.getY() == 3266)
				player.useStairs(828, new WorldTile(3099, 3266, 0), 1, 2);
			else if (object.getX() == 3098 && object.getY() == 3281)
				player.useStairs(828, new WorldTile(3100, 3281, 0), 1, 2);
			else if (object.getX() == 3092 && object.getY() == 3281)
				player.useStairs(828, new WorldTile(3091, 3281, 0), 1, 2);
		}
	}
	
	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 6435:
		case 26518:
		case 32015:
		case 2347:
		case 2348:
		case 3200:
		case 3198:
		case 75853:
		case 47404:
		case 133:
		case 164:
		case 158:
		case 160:
		case 131:
		case 47364:
		case 47657:
		case 152:
		case 47512:
		case 47643:
		case 153:
		case 682:
		case 37668:
		case 38315:
		case 2147:
		return true;
		default:
		return false;
		}
	}

	
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 6435) { //Object ID
			Draynor.UnderGround(player, object); //Method of Action
		}
		if (id == 26518) { //Object ID
			Draynor.UnderGroundExit(player, object); //Method of Action
		}
		if (id == 32015) { //Object ID
			Draynor.UnderGroundSecondExit(player, object); //Method of Action
		}
		if (id == 2347 || id == 2348) { 
			Draynor.Stairs(player, object);
		}
		if (id == 3200 || id == 3198) { 
			Draynor.Pottery(player, object);
		}
		if (id == 75853) { 
			Draynor.Skulls(player, object);
		}
		if (id == 47404) { 
			Draynor.Candle(player, object);
		}
		if (id == 133) { 
			Draynor.TrapDoor(player, object);
		}
		if (id == 164) { 
			Draynor.VampyreStairs(player, object);
		}
		if (id == 158) { 
			Draynor.Coffin(player, object);
		}
		if (id == 160) { 
			Draynor.Lever(player, object);
		}
		if (id == 131) { 
			Draynor.UnlockSmallDoor(player, object);
		}
		if (id == 47364) { 
			Draynor.climbManor(player, object);
		}
		if (id == 47657) { 
			Draynor.downManor(player, object);
		}
		if (id == 47421 || id == 47424) { 
			Draynor.openManorDoor(player, object);
		}
		if (id == 152) { 
			Draynor.Compost(player, object);
		}
		if (id == 153) { 
			Draynor.SearchFountain(player, object);
		}
		if (id == 47512) { 
			Draynor.ProfDoor(player, object);
		}
		if (id == 47643) {
			Draynor.stairsBasement(player, object);
		}
		if (id == 682) {
			Draynor.playPiano(player, object);
		}
		if (id == 37668) {
			Draynor.taunt(player, object);
		}
		if (id == 31385) {
			Draynor.studyMap(player, object);
		}
		if (id == 2147) {
			Draynor.downWizard(player, object);
		}
	}

}
