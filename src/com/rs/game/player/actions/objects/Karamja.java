package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.net.decoders.handlers.ObjectHandler;

public class Karamja {

	public static void JogreEscape(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2822, 3119, 0), 1, 2);
	}
	public static void JogreEntrance(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2826, 9521, 0), 1, 2);
	}
	public static void JogreFungusWalls(Player player,
			final WorldObject object) {
		 player.getInventory().addItem(1533, 1);
		 player.getPackets().sendGameMessage("You find a dirty herb.");
	}
	public static void TzhaarEntrance(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(4667, 5060, 0));
	}
	public static void TzhaarExit(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(2845, 3170, 0));
	}
	public static void KaramjaDungeonEntrance(Player player,
			final WorldObject object) {
		player.useStairs(827, new WorldTile(2856, 9570, 0), 1, 2);
	}
	public static void KaramjaDungeonExit(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2855, 3169, 0), 1, 2);
	}
	public static void CrandorEntrance(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2833, 9656, 0), 1, 2);
	}
	public static void CrandorExit(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2834, 3258, 0), 1, 2);
	}
	public static void ClimbRocks(Player player,
			final WorldObject object) {
		if (object.getX() == player.getX()) {
			if (player.getY() > object.getY())
				player.addWalkSteps(player.getX(), (player.getY() - 2), -1, false);
			else
				player.addWalkSteps(player.getX(), (player.getY() + 2), -1, false);
		} else if (object.getY() == player.getY()) {
			if (player.getX() > object.getX())
				player.addWalkSteps((player.getX() - 2), player.getY(), -1, false);
			else
				player.addWalkSteps((player.getX() + 2), player.getY(), -1, false);
		}
		player.setNextAnimation(new Animation(839));
	}
	public static void Wall(Player player,
			final WorldObject object) {
		if (player.DS >= 5) {
		ObjectHandler.handleDoor(player, object, 3000);
		} else {
		player.getPackets().sendGameMessage("This shortcut is accessible after significant progress in Dragon Slayer");
		}
	}
	public static void BrimEntrance(Player player,
			final WorldObject object) {
		if (player.brim) {
			player.useStairs(827, new WorldTile(2710, 9564, 0), 1, 2);
		} else {
		player.getPackets().sendGameMessage("You must talk to Saniboch for access to this dungeon.");
		}
	}
	public static void BrimExit(Player player,
			final WorldObject object) {
			player.useStairs(827, new WorldTile(2744, 3152, 0), 1, 2);
	}
	public static void JadinkoEntrance(Player player,
			final WorldObject object) {
			player.useStairs(827, new WorldTile(3011, 9275, 0), 1, 2);
	}
	public static void JadinkoEntrance2(Player player,
			final WorldObject object) {
			player.useStairs(827, new WorldTile(3024, 9226, 0), 1, 2);
	}
	public static void JadinkoExit(Player player,
			final WorldObject object) {
			player.useStairs(827, new WorldTile(2948, 2955, 0), 1, 2);
	}
	public static void JadinkoExit2(Player player,
			final WorldObject object) {
			player.useStairs(827, new WorldTile(2948, 2884, 0), 1, 2);
	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 2585:
		case 2584: 
		case 32106: 
		case 25154:
		case 68134: 
		case 68135: 
		case 25213:
		case 492: 
		case 1764: 
		case 2606:
		case 25161:
		case 5083:
		case 77421:
		case 12328:
		case 12327:
		case 12321:
			return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 2584) { //Jogre dungeon Entrance
			Karamja.JogreEntrance(player, object); 
		}
		if (id == 2585) { //Jogre dungeon escape
			Karamja.JogreEscape(player, object); 
		}
		if (id == 32106) { //Jogre Dungeon Grimy rogue's purse
			Karamja.JogreFungusWalls(player, object); 
		}
		if (id == 68134) { //tzhaar city entrance
			Karamja.TzhaarEntrance(player, object); 
		}
		if (id == 68135) { //tzhaar city entrance
			Karamja.TzhaarExit(player, object); 
		}
		if (id == 492) { //Karamja dungeon entrance
			Karamja.KaramjaDungeonEntrance(player, object); 
		}
		if (id == 1764) { //Karamja dungeon Exit
			Karamja.KaramjaDungeonExit(player, object); 
		}
		if (id == 25154) { //Crandor dungeon Entrance
			Karamja.CrandorEntrance(player, object); 
		}
		if (id == 25153) { //Crandor dungeon Exit
			Karamja.CrandorExit(player, object); 
		}
		if (id == 2606) { //Wall
			Karamja.Wall(player, object); 
		}
		if (id == 25161) { //ClimbOverRocks
			Karamja.ClimbRocks(player, object); 
		}
		if (id == 5083) { 
			Karamja.BrimEntrance(player, object); 
		}
		if (id == 77421) { 
			Karamja.BrimExit(player, object); 
		}
		if (id == 12328) { 
			Karamja.JadinkoEntrance(player, object); 
		}
		if (id == 12327) { 
			Karamja.JadinkoExit(player, object); 
		}
		if (id == 12321) { 
			Karamja.JadinkoExit2(player, object); 
		}
	}

}
