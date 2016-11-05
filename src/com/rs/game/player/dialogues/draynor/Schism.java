package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;

/**
 * The Feather Dialogue for Miss Schism.
 * 
 * @author Gircat <gircat101@gmail.com>
 * @author Feather RuneScape 2012
 */
public class Schism extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Why are you bothering me?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827,
					"I was just curious about you. You seem a little... drunk.");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827,
					"I don't believe that's any of your business, "
							+ (player.getAppearence().isMale() ? "sir"
									: "ma'am") + "!");
			break;
		case 1:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}
}