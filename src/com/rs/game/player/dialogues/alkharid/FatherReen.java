package com.rs.game.player.dialogues.alkharid;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class FatherReen extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello "+player.getUsername()+", would you like some bones for prayer?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Oh joy, sure Father Reen!");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "I got a... nice big one down here...");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "What...");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Just take a little peak...");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "WHAT THE FUCK!?!?!?");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "DON'T RESIST ME!!!!");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "Burn in hell!");
			break;
		case 6:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}