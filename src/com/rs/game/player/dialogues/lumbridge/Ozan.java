package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;

public final class Ozan extends Dialogue {
	
	private int npcId;

	@Override
	public void start() {
		sendNPCDialogue(npcId, 9827, 
				"Hello! I'll take you to lumbridge",
				"Lumbridge is a basic small city",
				"It's mostly country, so you better watch out if your from the city.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue("Do you want me to teleport you to our old home?",
					"Yes please", "Stay here.");
		} else if (stage == 0) {
			if (componentId == 1) {
				player.setNextWorldTile(new WorldTile(1346, 5197, 0));
				end();
			}
			end();
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}