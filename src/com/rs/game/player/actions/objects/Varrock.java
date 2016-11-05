package com.rs.game.player.actions.objects;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.magic.Magic;

/*
 * @Author Danny
 * Varrock City
 */

public class Varrock {
	
	public static void EssencePortal(Player player,
			final WorldObject object) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3253, 3401, 0));
	}
	
	public static void MiningCart(Player player,
			final WorldObject object) {
		player.getDialogueManager().startDialogue("Conductor", 2180);
	}
	public static void SawMillLadder(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(player.getX(), player.getY(), 0), 1, 2);	
	}
	public static void SawMillHole(Player player,
			final WorldObject object) {
		if (player.getX() == 3295 && player.getY() == 3498)
			player.useStairs(2240, new WorldTile(player.getX() + 1, player.getY(), 0), 1, 2);
		else if (player.getX() == 3296 && player.getY() == 3498)
			player.useStairs(2240, new WorldTile(player.getX() - 1, player.getY(), 0), 1, 2);
		else
			player.getPackets().sendGameMessage("You can't crawl through at this angle...");
	}
	
	public static void BankStairs(Player player,
			final WorldObject object) {
		if (player.getX() == 3188)
			player.useStairs(2240, new WorldTile(3190, 9834, 0), 1, 2);
		else
			player.useStairs(2240, new WorldTile(3188, 3433, 0), 1, 2);
	}
	public static void DoogleBush(Player player,
			final WorldObject object) {
			player.getInventory().addItem(1573, 1);
			player.getPackets().sendGameMessage("You search the bush and find some doogle leaves.");
	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 2507:
		case 28094:
		case 24355:
		case 31149:
		case 31155:
		case 24360:
		case 24365:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 2507) { 
			Varrock.EssencePortal(player, object); 
		}
		if (id == 28094) { 
			Varrock.MiningCart(player, object); 
		}
		if (id == 24355) { 
			Varrock.SawMillLadder(player, object); 
		}
		if (id == 31149) { 
			Varrock.SawMillHole(player, object); 
		}
		if (id == 31155) { 
			Varrock.DoogleBush(player, object); 
		}
		if (id == 24360 || id == 24365) { 
			Varrock.BankStairs(player, object); 
		}
		
	}

}
