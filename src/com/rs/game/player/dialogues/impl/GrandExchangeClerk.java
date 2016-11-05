package com.rs.game.player.dialogues.impl;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class GrandExchangeClerk extends Dialogue {

	@Override
	public void start() {
			sendEntityDialogue(SEND_3_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(2240).name, "Hello sir, I'm one of the staff, I can help you",
					"Exchange items with players all over " + Settings.SERVER_NAME + "." }, IS_NPC, 2240, 9843);
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(278).name, "Would you like to use the Grand exchange?" }, IS_NPC, 2240, 9829);
			stage = 1;
		} else if (stage == 1) {
			sendOptionsDialogue("Select an option", "Use the Grand Exchange", "No thanks");
			stage = 2;
		} else if (stage == 3) {
			end();
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				//player.grandExchange().open();
			} else if (componentId == OPTION_2) {
				end();
			}
		}
	}

	@Override
	public void finish() {

	}

}
