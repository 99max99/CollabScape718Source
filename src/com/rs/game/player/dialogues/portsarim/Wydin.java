package com.rs.game.player.dialogues.portsarim;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

/**
 *  Feather 2012 RuneScape Remake
 * @author Gircat <gircat101@gmail.com>
 *
 */

public class Wydin extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(
				SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to me food store! Would you like to buy anything?" },
				IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes please.",
					"No, thank you.", "What can you recommend?");
			break;
		case 0:
			switch (componentId) {
			case OPTION_1:
				stage = 1;
				sendPlayerDialogue(9827, "Yes please.");
				break;
			case OPTION_2:
				stage = 2;
				sendPlayerDialogue(9827, "No, thank you.");
				break;
			case OPTION_3:
				stage = 3;
				sendPlayerDialogue(9827, "What can you recommend?");
				break;
			}
			break;
		case 1:
			ShopsHandler.openShop(player, 110);
			end();
			break;
		case 2:
			stage = 10;
			sendPlayerDialogue(9827, "No, thank you.");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "What can you recommend?");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(
					npcId,
					9827,
					"We have this really exotic fruit all the way from Karamja. It's called a banana.");
			break;
		case 5:
			stage = 6;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Hmm, I think I'll try one.",
					"I don't like the sound of that.");
			break;
		case 6:
			switch (componentId) {
			case OPTION_1:
				stage = 7;
				sendPlayerDialogue(9827, "Hmm, I think I'll try one.");
				break;
			case OPTION_2:
				stage = 9;
				sendPlayerDialogue(9827, "I don't like the sound of that.");
				break;
			}
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "Hmm, I think I'll try one.");
			player.getInventory().addItem(1963, 1);
			break;
		case 8:
			stage = 1;
			sendNPCDialogue(npcId, 9827,
					"Great. You might as well take a look at the rest of mywares as well.");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "I don't like the sound of that.");
			break;
		case 10:
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}
