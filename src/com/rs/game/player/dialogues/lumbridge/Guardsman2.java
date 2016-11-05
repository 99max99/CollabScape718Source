package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Guardsman2 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Howdy.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "If you're interested in becoming a strong warrior, there ",
					"are plenty of goblins on the east side of the river. I'd ",
					"suggest putting paid to a few of them and, perhaps, one ",
					"day we'll drive them out altogether.");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "It used to be much nicer here, before the goblins overran ",
					"the east side of town. You'd think that the Guardsmen ",
					"would be sent to flush them out, but for every one we ",
					"slay, three more appear in its place.");
			break;

		case 1:
			sendNPCDialogue(npcId, 9827, "I sometimes ask that myself. I just get told to wander ",
					"around here looking out for trouble. I'm really not sure if ",
					"there's much worth guarding right here.");
			stage = 2;
			break;
		case 2:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}