package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;

public class GETutor extends Dialogue {

	@Override
	public void start() {
		sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(6521).name, "Hello there" }, IS_NPC, 6521, ASKING_FACE);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (player.tutored) {
				sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(6521).name, "Would you like me to teleport you to the", " Grand exchange?" }, IS_NPC, 6521,
						ASKING_FACE);
				stage = 1;
			} else {
				sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(6521).name, "Would you like me to show you how to use the Grand exchange?" }, IS_NPC, 6521,
						ASKING_FACE);
				stage = 2;
			}
		} else if (stage == 2) {
			sendOptionsDialogue("Select an Option", "Yes", "No");
			stage = 3;
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(6521).name,
						"Very Simple... Once you place an offer it completes instantly, although there is a twist..." }, IS_NPC, 6521, ASKING_FACE);
				stage = 4;
			} else {
				end();
			}
		} else if (stage == 4) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(6521).name,
							"Not ALL items are Instant-Complete, for all skilling items and high level gear, your offer is sent to a huge database and when another player is offering the item you're buying/selling, the clerks complete your offer for you." },
					IS_NPC, 6521, ASKING_FACE);
			stage = 5;
		} else if (stage == 5) {
			sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(6521).name,
					"Also, when you search an item, it can take up to 5 seconds to complete your search on the dialogue box." }, IS_NPC, 6521, ASKING_FACE);
			stage = 6;
		} else if (stage == 6) {
			end();
			player.tutored = true;
		} else if (stage == 1) {
			sendOptionsDialogue("Select an Option", "Yes", "No");
			stage = 7;
		} else if (stage == 7) {
			if (componentId == OPTION_1) {
				end();
				player.setNextWorldTile(new WorldTile(3165, 3459, 0));
			} else if (componentId == OPTION_2) {
				end();
			}
		}
	}

	@Override
	public void finish() {

	}

}
