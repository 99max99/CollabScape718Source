package com.rs.game.player.actions.objects;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/*
 * @Author Danny
 * Varrock City
 */

public class TutIsland {
	
	public static void DungeonEntrance(Player player,
			final WorldObject object) {
		if (object.getX() == 3088 && object.getY() == 3119) {
			player.useStairs(828, new WorldTile(3088, 9520, 0), 1, 2);
			
		}
		if (object.getX() == 3111 && object.getY() == 3126) {
			player.useStairs(828, new WorldTile(3111, 9525, 0), 1, 2);
		}
	}
	public static void DungeonExit(Player player,
			final WorldObject object) {
		if (object.getX() == 3088 && object.getY() == 9519) {
			player.useStairs(828, new WorldTile(3088, 3120, 0), 1, 2);
		}
		if (object.getX() == 3111 && object.getY() == 9526) {
			player.useStairs(828, new WorldTile(3111, 3125, 0), 1, 2);
		}
	}
	public static void DrawersMagicTut(Player player,
			final WorldObject object) {
		final WorldObject drawers = new WorldObject(37012,
		10, 2, 3143,
		3088, 1);	
		World.spawnTemporaryObject(drawers, 30000, true);
	}
	
	

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 3028:
		case 3029:
		case 3030:
		case 3031:
		case 37011:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 3029 || id == 3031) { 
			TutIsland.DungeonEntrance(player, object); 
		}
		if (id == 3028 || id == 3030) { 
			TutIsland.DungeonEntrance(player, object); 
		}
		if (id == 37011) { 
			TutIsland.DrawersMagicTut(player, object); 
		}
	}

}