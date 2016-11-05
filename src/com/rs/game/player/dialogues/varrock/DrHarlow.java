package com.rs.game.player.dialogues.varrock;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class DrHarlow extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "*hic* What do I can for you?.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			if (player.VS == 1) {
			stage = 0;
			sendPlayerDialogue(9827, "I want to know how to slay a vampyre, there",
					"is one haunting and killing in Draynor Village.");
			break;
			} else if (player.VS == 2) {
			if (player.getInventory().containsItem(1917, 1)) {
				player.getInventory().deleteItem(1917, 1);
				sendPlayerDialogue(9827, "Here is your beer.");	
				player.VS = 3;
				stage = 2;
				break;
			} else {
				sendPlayerDialogue(9827, "What did you want again?");
				stage = 1;
				break;
			}
			} else if (player.VS == 0) {
			stage = 11;
			sendPlayerDialogue(9827, "Go home! Your drunk!");
			break;
			} else {
			stage = 4;
			sendPlayerDialogue(9827, "What do I need to do again?");	
			break;
			}
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "*hic* Would you get minding to me a beer?");
			player.VS = 2;
			break;
		case 1:
			stage = 11;
			sendPlayerDialogue(9827, "Um... Okay?");	
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Ohhhhh geez thanksss!");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Now can you tell me how to kill this foul creature?");	
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Well first.. uhhh... you see.. You will need a to find the",
					"vampyre and kill him with whatever weapons you uhh.. have...",
					"Then you will need items these the stake and this stake hammer",
					"to finish off the vampyre.");
			player.getInventory().addItem(1549, 1);
			player.getInventory().addItem(15417, 1);
			break;
		case 5:
			stage = 11;
			sendPlayerDialogue(9827, "Thanks, will do!");
			break;
		case 11:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}