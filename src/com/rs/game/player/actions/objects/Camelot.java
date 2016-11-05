package com.rs.game.player.actions.objects;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/*
 * @Author Danny
 * Varrock City
 */

public class Camelot {
	
	public static void WhiteStairsDown(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2821, 9882, 0), 1, 2);
	}
	
	public static void WhiteStairsUp(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2820, 3486, 0), 1, 2);
	}
	
	public static void TrainCart(Player player,
			final WorldObject object) {
		player.getDialogueManager().startDialogue("Conductor", 2180);
	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 55:
		case 56:
		case 7030:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 55) { 
			Camelot.WhiteStairsDown(player, object); 
		}
		if (id == 56) { 
			Camelot.WhiteStairsUp(player, object); 
		}
		if (id == 7030) { 
			Camelot.TrainCart(player, object); 
		}
	}

}
