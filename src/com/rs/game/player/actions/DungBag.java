package com.rs.game.player.actions;

import com.rs.game.player.Player;

public class DungBag {
	
	
	public static void fillGemBag(final Player player) {
		if (player.gembagspace < 100) {
		if (player.getInventory().containsItem(1621, 1)) {
			player.getInventory().deleteItem(1621, 1);
			player.emeralds++;
		} else if (player.getInventory().containsItem(1623, 1)) {
			player.getInventory().deleteItem(1623, 1);
			player.sapphires++;
		} else if (player.getInventory().containsItem(1619, 1)) {
			player.getInventory().deleteItem(1619, 1);
			player.rubies++;
		} else if (player.getInventory().containsItem(1617, 1)) {
			player.getInventory().deleteItem(1617, 1);
			player.diamonds++;
		}
		player.gembagspace++;
		} else {
			player.sm("Your gem bag is too full to carry anymore uncut gems.");
		}
	}

}
