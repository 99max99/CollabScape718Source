package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/*
 * @Author Danny
 * Edgeville City
 */

public class Edgeville {
	
	public static void TrapDoor(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(3077, 9893, 0), 1, 2);
	}
	public static void BasementStairs(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3078, 3493, 0));
	}
	public static void LadderClimbingUp(Player player,
			final WorldObject object) {
		
		player.useStairs(828, new WorldTile(player.getX(), player.getY(), (player.getPlane() + 1)), 1, 2);
	}
	public static void LadderClimbingDown(Player player,
			final WorldObject object) {
		
		player.useStairs(828, new WorldTile(player.getX(), player.getY(), (player.getPlane() - 1)), 1, 2);
	}
	public static void CockRoachEntrance(Player player,
			final WorldObject object) {
		
		player.setNextWorldTile(new WorldTile(3082, 4229, 0));
	}
	public static void CockRoachTunnel(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3140, 4230, 2));
	}
	public static void CockRoachTunnelExit(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3079, 4235, 0));
	}
	public static void CockRoachShortcut(Player player,
			final WorldObject object) {
		if (player.cockRoachShortcut == false){
			player.setNextWorldTile(new WorldTile(3077, 3462, 0));
			player.getPackets().sendGameMessage("You may now use the shortcut above for fast access!");
			player.cockRoachShortcut = true;
		} else if(player.cockRoachShortcut == true){
			player.setNextWorldTile(new WorldTile(3077, 3462, 0));
		}
	}
	public static void UseCockRoachShortcut(Player player,
			final WorldObject object) {
		if (player.cockRoachShortcut == false){
			player.getPackets().sendGameMessage("The hole looks too dark to explore.");
		} else if(player.cockRoachShortcut == true){
			player.setNextWorldTile(new WorldTile(3157, 4279, 3));
		}
	}
	public static void CockRoachLever(Player player,
			final WorldObject object) {
		if (player.cockRoachLever == false){
			player.getPackets().sendGameMessage("You hear cogs and gears moving and a distant unlocking sound.");
			player.cockRoachLever = true;
			player.setNextAnimation(new Animation(2140));
		} else if(player.cockRoachLever == true){
			player.getPackets().sendGameMessage("You hear cogs and gears moving and the sound of heavy locks falling into place.");
			player.cockRoachLever = false;
			player.setNextAnimation(new Animation(2140));
		}
	}
	public static void CockRoachLockedDoor(Player player,
			final WorldObject object) {
		if (player.cockRoachLever == false){
			player.getPackets().sendGameMessage("The door seems to be locked by some kind of mechanism.");
		} else if(player.cockRoachLever == true){
				if (object.getX() == 3178 && object.getY() == 4269) {
					player.setNextWorldTile(new WorldTile(3177, 4266, 0));
				}
				if (object.getX() == 3178 && object.getY() == 4266) {
					player.setNextWorldTile(new WorldTile(3177, 4269, 2));
				}
				if (object.getX() == 3141 && object.getY() == 4272) {
					player.setNextWorldTile(new WorldTile(3143, 4270, 0));
				}
				if (object.getX() == 3142 && object.getY() == 4270) {
					player.setNextWorldTile(new WorldTile(3142, 4272, 1));
				}
		}
	}
	public static void HandleCockRoachStairs(Player player,
			final WorldObject object) {
				if (object.getX() == 3147 && object.getY() == 4249) {
					player.setNextWorldTile(new WorldTile(3149, 4251, 2));
				}
				if (object.getX() == 3147 && object.getY() == 4246) {
					player.setNextWorldTile(new WorldTile(3149, 4244, 2));
				}
				if (object.getX() == 3157 && object.getY() == 4246) {
					player.setNextWorldTile(new WorldTile(3157, 4244, 2));
				}
				if (object.getX() == 3157 && object.getY() == 4249) {
					player.setNextWorldTile(new WorldTile(3157, 4251, 2));
				}
				if (object.getX() == 3171 && object.getY() == 4273) {
					player.setNextWorldTile(new WorldTile(3171, 4271, 3));
				}
				if (object.getX() == 3171 && object.getY() == 4272) {
					player.setNextWorldTile(new WorldTile(3174, 4273, 2));
				}
				if (object.getX() == 3149 && object.getY() == 4250) {
					player.setNextWorldTile(new WorldTile(3146, 4249, 1));
				}
				if (object.getX() == 3149 && object.getY() == 4245) {
					player.setNextWorldTile(new WorldTile(3146, 4246, 1));
				}
				if (object.getX() == 3157 && object.getY() == 4250) {
					player.setNextWorldTile(new WorldTile(3160, 4249, 1));
				}
				if (object.getX() == 3157 && object.getY() == 4245) {
					player.setNextWorldTile(new WorldTile(3160, 4246, 1));
				}
				
		}
	public static void TriviaStairsUp(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3083, 3452, 0));
	}
	public static void TriviaStairsDown(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3086, 4247, 0));
	}
	public static void CockRoachExit(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(3074, 3456, 0));
	}
	public static void CrateSearch(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("Your search the crate and find nothing.");
		}
	
	public static void LavaDungeon(Player player,
			final WorldObject object) {
			player.useStairs(827, new WorldTile(3017, 10248, 0), 1, 2);
	}
	public static void CockRoachChest(Player player,
			final WorldObject object) {
			if (player.cockRoachChest == false){
				player.getDialogueManager().startDialogue("SimpleMessage","You open the chest to find a large pile of gold, along with a pair"
				+ " of safety gloves and two antique lamps.");
				 player.getInventory().addItemMoneyPouch(995, 500000);
				 player.getInventory().addItem(12629, 1);
				 player.getInventory().addItem(12628, 2);
			player.cockRoachChest = true;
			} else if (player.cockRoachChest == true){
				player.getDialogueManager().startDialogue("SimpleMessage","The chest appears to be empty.");
			}
		}
	public static void BoxSearch(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("Your search the box and find nothing.");
		}
	public static void JailDoor(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("The door is locked up tight.");
		}
	public static void BookCase(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("You can't find anything that you'd like to read.");
		}
	
	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 12266:
		case 12265:
		case 26983:
		case 37123:
		case 26962:
		case 26961:
		case 37470:
		case 44392:
		case 29603:
		case 29589:
		case 29592:
		case 29656:
		case 29602:
		case 29623:
		case 29729:
		case 29728:
		case 29624:
		case 29736:
		case 29734:
		case 29660:
		case 29664:
		case 29668:
		case 29672:
		case 29671:
		case 29663:
		case 29667:
		case 29659:
		case 29655:
		case 1767:
			return true;
		default:
		return false;
		}
	}

	
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 12266) { //Object ID
			Edgeville.TrapDoor(player, object); //Method of Action
		}
		if (id == 12265) { //Object ID
			Edgeville.BasementStairs(player, object); //Method of Action
		}
		if (id == 26983) { //Object ID
			Edgeville.LadderClimbingDown(player, object); //Method of Action
		}
		if (id == 37123 || id == 26962 || id == 26961) { //Object ID
			Edgeville.CrateSearch(player, object); //Method of Action
		}
		if (id == 37470) { //Object ID
			Edgeville.JailDoor(player, object); //Method of Action
		}
		if (id == 44392) { //Object ID
			Edgeville.BookCase(player, object); //Method of Action
		}
		if (id == 29603) { //Object ID
			Edgeville.CockRoachEntrance(player, object); //Method of Action
		}
		if (id == 29602) { //Object ID
			Edgeville.CockRoachExit(player, object); //Method of Action
		}
		if (id == 29589) { //Object ID
			Edgeville.TriviaStairsUp(player, object); //Method of Action
		}
		if (id == 29592) { //Object ID
			Edgeville.TriviaStairsDown(player, object); //Method of Action
		}
		if (id == 4500) { //Object ID
			Edgeville.CockRoachTunnel(player, object); //Method of Action
		}
		if (id == 29623) { //Object ID
			Edgeville.CockRoachTunnelExit(player, object); //Method of Action
		}
		if (id == 29729) { //Object ID
			Edgeville.CockRoachShortcut(player, object); //Method of Action
		}
		if (id == 29728) { //Object ID
			Edgeville.UseCockRoachShortcut(player, object); //Method of Action
		}
		if (id == 29624) { //Object ID
			Edgeville.CockRoachLockedDoor(player, object); //Method of Action
		}
		if (id == 29736) { //Object ID
			Edgeville.CockRoachLever(player, object); //Method of Action
		}
		if (id == 29734) { //Object ID
			Edgeville.CockRoachChest(player, object); //Method of Action
		}
		if (id == 1767) { //Object ID
			Edgeville.LavaDungeon(player, object); //Method of Action
		}
		if (id == 29656 || id == 29660 || id == 29664 || id == 29668 || id == 29672
			|| id == 29671 || id == 29663 || id == 29667 || id == 29659 || id == 29655) { //Stairs
			Edgeville.HandleCockRoachStairs(player, object); //Method of Action
		}
		
	}

}
