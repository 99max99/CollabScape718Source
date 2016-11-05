package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;
/**
 * Begins the dialogue for Hans.
 * @author Gircat <gircat101@gmail.com>
 * Feather - RuneScape 2012
 */
public class Hans extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			sendPlayerDialogue(9827, "I wondered what happened to the castle?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "It did take a battering recently. If you've not noticed already, ",
					"there is a battle going on just outside between Saradomin and Zamorak!");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "Never in all my years did I expect to see this happen to the castle!");
			break;
		case 1:
			end();
			break;
		}
	}
	public void finish() {
	}
}
