package com.rs.game.player.actions.objects;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.magic.Magic;

/*
 * @Author Danny
 * Varrock City
 */

public class Zanaris {
	
	public static void EssencePortal(Player player,
			final WorldObject object) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3253, 3401, 0));
	}
	
	public static void MiningCart(Player player,
			final WorldObject object) {
		player.getDialogueManager().startDialogue("Conductor", 2180);
	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 2507:
		case 28094:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 2507) { 
			Zanaris.EssencePortal(player, object); 
		}
		if (id == 28094) { 
			Zanaris.MiningCart(player, object); 
		}
		
	}

}
