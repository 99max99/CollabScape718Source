package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/*
 * @Author Danny
 * Port Sarim
 */

public class PortSarim {
	
	public static void PrisonCell(Player player,
			final WorldObject object) {
		player.getPackets().sendGameMessage("The prison door seems to be locked.");
	}
	
	public static void ShipDown(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(player.getX(), player.getY(), 0), 1, 2);
		player.getPackets().sendGameMessage("You climb down into the hull of the ship.");
	}
	
	public static void ShipUp(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(player.getX(), player.getY(), 1), 1, 2);
		player.getPackets().sendGameMessage("You climb up onto the deck of the ship.");
	}
	public static void GrotExit(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2992,3236, 0), 1, 2);
	}
	
	public static void PatchHole(Player player,
			final WorldObject object) {
		if (player.patched) {
			player.getPackets().sendGameMessage("You have already patched this hole!");
		} else {
			if (player.getInventory().containsItem(1539, 90) && player.getInventory().containsItem(960, 3) && player.getInventory().containsItemToolBelt(2347, 1)) {
				player.getInventory().deleteItem(1539, 90);
				player.getInventory().deleteItem(960, 3);
				player.setNextAnimation(new Animation(10102));
				player.getPackets().sendGameMessage("The hole is patched, this ship is now sailable!");
				player.patched = true;
			} else {
				player.getPackets().sendGameMessage("You need a hammer, 90 steel nails, and 3 regular planks to patch this hole.");	
			}
		}
	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 40184:
		case 272:
		case 2590:
		case 25036:
		case 70793:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 40184) { 
			PortSarim.PrisonCell(player, object); 
		}
		if (id == 2590) { 
			PortSarim.ShipDown(player, object); 
		}
		if (id == 272) { 
			PortSarim.ShipUp(player, object); 
		}
		if (id == 25036) { 
			PortSarim.PatchHole(player, object); 
		}
		if (id == 70793) { 
			PortSarim.GrotExit(player, object); 
		}
	}

}
