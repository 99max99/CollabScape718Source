package com.rs.game.player.dialogues.relekka;

import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;

public class DwarvenBoatman extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Want me to take you back to the mines?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes, please take me.", "What, on your ship! No thanks!");
			break;
		case 0:
			if(componentId == OPTION_2) {
				stage = 1;
				sendPlayerDialogue(9827, "What, on your ship! No thanks!");
			} else {
				stage = 2;
				sendPlayerDialogue(9827, "Yes, please take me.");
			}
			break;
		case 1:
			end();
			break;
		case 2:
			end();
			player.useStairs(-1, new WorldTile(2838, 10127, 0), 3, 4);
			break;
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}