package com.rs.game.player.dialogues.portsarim;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Brian extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
				"So, are you selling something?", "'Ello.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			switch (componentId) {
			case OPTION_1:
				stage = 0;
				sendPlayerDialogue(9827, "So, are you selling something?");
				break;
			case OPTION_2:
				stage = 2;
				sendPlayerDialogue(9827, "'Ello.");
				break;
			}
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827,
					"Yep, take a look at these great axes.");
			break;
		case 1:
			end();
			ShopsHandler.openShop(player, 107);
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "'Ello.");
			break;
		case 3:
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
