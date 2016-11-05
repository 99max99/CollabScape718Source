package com.rs.game.player.dialogues.alkharid;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Avan extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "There's a really nice mining spot over here...");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Tell me more about it!");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "It has many mines, one can become rich!",
					"However it is guarded by scorpions, a huge risk.");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "Wow, thanks for the information!");
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