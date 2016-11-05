package com.rs.game.player.dialogues.impl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public final class Graves extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT, new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Hello there, I can enable or disable gravestones for you.", "would you like me to?" },
				IS_NPC, 3777, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Grave Stones", "Turn Gravestones On", "Turn Gravestones Off");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				end();
				player.getPackets().sendGameMessage("Your gravestone is now <col=00ff12>Enabled</col>.");
			} else if (componentId == OPTION_2) {
				end();
				player.getPackets().sendGameMessage("Your gravestone is now <col=ff0000>Disabled</col>.");
			}
		}
	}

	@Override
	public void finish() {

	}

}