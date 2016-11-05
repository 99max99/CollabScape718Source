package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Barrows;

/*
 * @Author Danny
 * Varrock City
 */

public class BarrowsObjects {
	
	public static void Spades(Player player,
			final WorldObject object) {
		player.setNextAnimation(new Animation(830));
		if (Barrows.digIntoGrave(player))
			return;
	}
	
	

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 66115:
		case 66116:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 66115 || id == 66116) { 
			BarrowsObjects.Spades(player, object); 
		}
	}

}