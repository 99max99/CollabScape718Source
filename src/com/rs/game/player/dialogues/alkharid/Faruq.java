package com.rs.game.player.dialogues.alkharid;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Faruq extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Oh noes, oh noes!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "What's wrong?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "All my toys are broken, they won't work!");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "That's a shame...");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "I TOLD YOU MY SHOP IS CLOSED!!!");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "What?");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "GET OUT OF MY HOUSE!!!!");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "But we are outside!");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "RAAAAAAAAAAAAAWWWWWWWWRRRRRR!!!!!!!!!");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "HOLY SHIT THIS GUY IS NUTS!");
			break;
		case 8:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}