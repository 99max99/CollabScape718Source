package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class MillieMiller extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Can you teach me how to dougie?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "No I cannot, but I can teach you how to make flour.");
			break;
		case 0:
			stage = 1;
			sendPlayerDialogue(9827, "Please teach me!");
				break;
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "Okay, first you need to go to the top of the mill",
					"and place your grain into the grain hopper. Once you do",
					"that activate the controls beside the hopper. You can then",
					"fill up your pot with flour at the bottom of the mill.");
			break;
		case 2:
			sendPlayerDialogue(9827, "Thanks so much!");
			stage = 3;
			break;
		case 3:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}