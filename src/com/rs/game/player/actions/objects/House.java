package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;

/*
 * @Author Danny
 * Handles House Objects
 */

public class House {
	
	public static void SitChairs(Player player,
			final WorldObject object) {
		int emote = 0;
		switch (object.getId()) {
		case 13581:
			emote = 4075;
			break;
		case 13582:
			emote = 4077;
			break;
		case 13583:
			emote = 4079;
			break;
		case 13584:
			emote = 4081;
			break;
		case 13585:
			emote = 4083;
			break;
		case 13586:
			emote = 4085;
			break;
		case 13587:
			emote = 4087;
			break;
		}
		switch (object.getRotation()) {
		case 1:
			emote++;
			break;
		case 4:
			emote++;
			break;
		}
		WorldTile tile = new WorldTile(object.getX(), object.getY(), 0);
		player.setNextWorldTile(tile);
		player.setNextAnimation(new Animation(emote));
	}
	
	public static void SitBenches(Player player,
			final WorldObject object) {
		int emote = 0;
		switch (object.getId()) {
		case 13300:
			emote = 4089;
			break;
		case 13301:
			emote = 4091;
			break;
		case 13302:
			emote = 4093;
			break;
		case 13303:
			emote = 4095;
			break;
		case 13304:
			emote = 4097;
			break;
		case 13305:
			emote = 4099;
			break;
		case 13306:
			emote = 4101;
			break;
		}
		WorldTile tile = new WorldTile(object.getX(), object.getY(), 0);
		player.setNextWorldTile(tile);
		player.setNextAnimation(new Animation(emote));
	}
	
	public static void LightFireplace(Player player,
			final WorldObject object) {
		WorldObject litFireplace = new WorldObject(object.getId() + 1, 10, object.getRotation(), object.getX(),
				object.getY(), 0);
		if (player.getInventory().containsItemToolBelt(590, 1)) {
			player.sm("You light the fireplace.");
			player.setNextAnimation(new Animation(16700));
			World.spawnTemporaryObject(litFireplace, 600000);
		} else {
			player.sm("You need a tinderbox to light this fireplace.");
		}
	}
	
	

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 13581:
		case 13582:
		case 13583:
		case 13584:
		case 13585:
		case 13586:
		case 13587:
		case 13609:
		case 13611:
		case 13613:
		case 13405:
		case 13300:
		case 13301:
		case 13302:
		case 13303:
		case 13304:
		case 13305:
		case 13306:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id >= 13581 && id <= 13587) { //Chairs
			House.SitChairs(player, object); //Method of Action
		}
		if (id >= 13609 && id <= 13613) { //Fireplace
			House.LightFireplace(player, object); //Method of Action
		}
		if (id >= 13300 && id <= 13306) { //Benches
			House.SitBenches(player, object); //Method of Action
		}
	}

}
