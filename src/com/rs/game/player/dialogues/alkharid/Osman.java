package com.rs.game.player.dialogues.alkharid;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Osman extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "PLEASE HELP ME!");
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
			sendNPCDialogue(npcId, 9827, "MY BODY IS GOING SHWOOPDY LOOPY!");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "I'm not a doctor, sorry...");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "WHAT THE HELL IS GOING ON!?!?!");
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