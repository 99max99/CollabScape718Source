package com.rs.game.player.actions.objects;

import com.rs.game.WorldObject;
import com.rs.game.player.Player;

public class DwarfCannon {
	
	public static void HandleRailing1(Player player,
			final WorldObject object) {
		if (player.getInventory().containsItem(14, 1) && player.getInventory().containsItemToolBelt(2347, 1) && player.fixedRailing1 == false) {
		player.getPackets().sendConfigByFile(2245, 1);
		player.fixedRailings++;
		player.out("You have fixed "+player.fixedRailings+" railing.");
		player.fixedRailing1 = true;
		player.getInventory().deleteItem(14, 1);
		} else {
			player.out("You need a hammer and a railing to fix this.");
		}
	}

	public static void HandleRailing2(Player player, final WorldObject object) {
		if (player.getInventory().containsItem(14, 1) && player.getInventory().containsItemToolBelt(2347, 1) && player.fixedRailing2 == false) {
		player.getPackets().sendConfigByFile(2244, 1);
		player.fixedRailings++;
		player.out("You have fixed "+player.fixedRailings+" railings.");
		player.fixedRailing2 = true;
		player.getInventory().deleteItem(14, 1);
		} else {
			player.out("You need a hammer and a railing to fix this.");
		}
	}

	public static void HandleRailing3(Player player, final WorldObject object) {
		if (player.getInventory().containsItem(14, 1) && player.getInventory().containsItemToolBelt(2347, 1) && player.fixedRailing3 == false) {
		player.getPackets().sendConfigByFile(2243, 1);
		player.fixedRailings++;
		player.out("You have fixed "+player.fixedRailings+" railings.");
		player.fixedRailing3 = true;
		player.getInventory().deleteItem(14, 1);
		} else {
			player.out("You need a hammer and a railing to fix this.");
		}
	}

	public static void HandleRailing4(Player player, final WorldObject object) {
		if (player.getInventory().containsItem(14, 1) && player.getInventory().containsItemToolBelt(2347, 1) && player.fixedRailing4 == false) {
		player.getPackets().sendConfigByFile(2242, 1);
		player.fixedRailings++;
		player.out("You have fixed "+player.fixedRailings+" railings.");
		player.fixedRailing4 = true;
		player.getInventory().deleteItem(14, 1);
		} else {
			player.out("You need a hammer and a railing to fix this.");
		}
	}

	public static void HandleRailing5(Player player, final WorldObject object) {
		if (player.getInventory().containsItem(14, 1) && player.getInventory().containsItemToolBelt(2347, 1) && player.fixedRailing5 == false) {
		player.getPackets().sendConfigByFile(2241, 1);
		player.fixedRailings++;
		player.out("You have fixed "+player.fixedRailings+" railings.");
		player.fixedRailing5 = true;
		player.getInventory().deleteItem(14, 1);
		} else {
			player.out("You need a hammer and a railing to fix this.");
		}
	}

	public static void HandleRailing6(Player player, final WorldObject object) {
		if (player.getInventory().containsItem(14, 1) && player.getInventory().containsItemToolBelt(2347, 1) && player.fixedRailing6 == false) {
		player.getPackets().sendConfigByFile(2240, 1);
		player.fixedRailings++;
		player.out("You have fixed "+player.fixedRailings+" railings.");
		player.fixedRailing6 = true;
		player.getInventory().deleteItem(14, 1);
		} else {
			player.out("You need a hammer and a railing to fix this.");
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 15595) {
			DwarfCannon.HandleRailing1(player, object);
		}
		if (id == 15594) {
			DwarfCannon.HandleRailing2(player, object);
		}
		if (id == 15593) {
			DwarfCannon.HandleRailing3(player, object);
		}
		if (id == 15592) {
			DwarfCannon.HandleRailing4(player, object);
		}
		if (id == 15591) {
			DwarfCannon.HandleRailing5(player, object);
		}
		if (id == 15590) {
			DwarfCannon.HandleRailing6(player, object);
		}
	}

}
