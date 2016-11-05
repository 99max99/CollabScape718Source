package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;



public class GnomeGlider {


	public static final WorldTile
		MOUNTAIN = new WorldTile(2846, 3499, 0),
		GRAND_TREE = new WorldTile(2465, 3501, 3),
		CASTLE = new WorldTile(3321, 3427, 0),
		DESERT = new WorldTile(3283, 3213, 0),
		CRASH_ISLAND = new WorldTile(2894, 2730, 0),
		OGRE = new WorldTile(2544, 2970, 0);

		
	public static void handleButtons(final Player player, int componentId) {
		if (componentId == 24) {
			sendGnomeGliderTeleport(player, MOUNTAIN);
		} else if (componentId == 23) {
			sendGnomeGliderTeleport(player, GRAND_TREE);
		} else if (componentId == 25) {
			sendGnomeGliderTeleport(player, CASTLE);
		} else if (componentId == 26) {
			sendGnomeGliderTeleport(player, DESERT);
		} else if (componentId == 27) {
			sendGnomeGliderTeleport(player, OGRE);
		} else if (componentId == 22) {
			sendGnomeGliderTeleport(player, CRASH_ISLAND);
		}

	}

	
	public static void sendGnomeGliderTeleport(final Player player,
			final WorldTile tile) {
		if (!player.getControlerManager().processObjectTeleport(tile))
			return;
		player.setNextWorldTile(tile);
		player.closeInterfaces();
		player.getPackets().sendGameMessage("You travel using the gnome glider.");
	}
	
	

}