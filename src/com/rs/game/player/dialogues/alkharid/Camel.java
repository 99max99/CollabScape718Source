package com.rs.game.player.dialogues.alkharid;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;

public class Camel extends Dialogue {
	
	private static final String[] MESSAGES = {
		"The camel turns its head and glares at you.",
		"The camel spits at you, and you jump back hurriedly.",
		"The camel tries to stamp on your foot, but you pull it back quickly."};
	
	private static final String[] DIALOGUE = {
		"If I go near that camel, It'll probably bite my hand off.",
		"I wonder if that camel has fleas...",
		"Mmm... Looks like that camel would make a nice kebab."};

	@Override
	public void start() {
		sendPlayerDialogue(9827,
				DIALOGUE[Utils.random(DIALOGUE.length)]);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
		default:
			player.getPackets().sendGameMessage(MESSAGES[Utils.random(MESSAGES.length)]);
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}
