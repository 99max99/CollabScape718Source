package com.rs.game.player.content;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;

public class LavaFlowMines {
	
	static WorldTile INSIDE = new WorldTile(2177, 5664, 0);
	
	public static void Entering(Player player) {
		player.stopAll();
		player.setNextWorldTile(INSIDE);
		player.getDialogueManager().startDialogue("SimpleNPCMessage", 13395, "Welcome to the mines " +player.getDisplayName() + "!");
		
	}
	
}
