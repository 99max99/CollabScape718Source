package com.rs.game.player.dialogues.portsarim;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Betty extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello, would you like to take a look at my magic shop?" },
				IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Sure.",
					"No, thanks.");
			break;
		case 0:
			switch (componentId) {
			case OPTION_1:
				stage = 1;
				sendPlayerDialogue(9827, "Let's see what you've got then.");
				break;
			case OPTION_2:
				stage = 2;
				sendPlayerDialogue(9827, "Sorry, I'm not interested.");
				break;
			}
			break;
		case 1:
			ShopsHandler.openShop(player, 111);
			end();
			break;
		case 2:
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}
