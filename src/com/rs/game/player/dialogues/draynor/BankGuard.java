package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class BankGuard extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Patch, patch, patch the wall...");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Why are you patching this wall?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "The bank was broken into by a bad guy.");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "WHAT?");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Yeah, the bank was robbed and now my job is to tape the wall back together,",
					"using the power of duct tape.");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Okay well have fun with that...");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "TAPE TAPE TAPE THE WALL!");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "Little nut job...");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}