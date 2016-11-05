package com.rs.game.player.dialogues.interfaces;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;

public class MTMediumLevelBosses extends Dialogue {
	
	

	@Override
	public void start() {
		sendOptionsDialogue("Medium Level Bosses",
				"King Black Dragon",
				"Bork",
				"Kalphite Queen",
				"Dagannoth Kings",
				"More");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3067, 10254, 0));
			} else if (componentId == OPTION_2) {
				player.getControlerManager().startControler("BorkControler", 0, null);
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3226, 3109, 0));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2544, 10143, 0));
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Medium Level Bosses  - Pg 2",
						"Tormented Demons",
						"None");
				stage = 1;
			}
		} else if (stage == 1) {
			if (componentId == OPTION_1 ) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2564, 5739, 0));
			} else if (componentId == OPTION_2) {
				end();
			}
		}
		 
	}

	@Override
	public void finish() {

	}
}
