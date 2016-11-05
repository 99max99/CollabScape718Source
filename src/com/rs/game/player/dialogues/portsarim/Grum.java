package com.rs.game.player.dialogues.portsarim;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Grum extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Would you like to buy or sell some gold jewellery?" },
				IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes, please.",
					"No, I'm not that rich.");
			break;
		case 0:
			switch (componentId) {
			case OPTION_1:
				stage = 1;
				sendPlayerDialogue(9827, "Yes, please.");
				break;
			case OPTION_2:
				stage = 2;
				sendPlayerDialogue(9827, "No, I'm not that rich.");
				break;
			}
			break;
		case 1:
			ShopsHandler.openShop(player, 109);
			end();
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827,
					"Get out, then! We don't want any riff-raff in here.");
			break;
		case 3:
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}

}
