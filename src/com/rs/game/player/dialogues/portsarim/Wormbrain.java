package com.rs.game.player.dialogues.portsarim;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Wormbrain extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "What do YOU want?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Talk about map piece.", 
					"Why are you in here?", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				if (player.DS <= 2) {
				sendNPCDialogue(npcId, 9827, "I have never heard of such a thing.");
				stage = 11;
				break;
				} else if (player.DS == 3) {
				stage = 1;
				sendNPCDialogue(npcId, 9827, "So, what about it?");
				break;
				} else if (player.DS >= 4) {
				stage = 11;
				sendNPCDialogue(npcId, 9827, "YOU ALREADY HAVE THE MAP!");
				break;
				}
			} else if(componentId == OPTION_2) {
				stage = 11;
				sendNPCDialogue(npcId, 9827, "That is none of your business...");
				break;
			} else if(componentId == OPTION_3) {
				stage = 11;
				break;
			} 
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "I heard you have this map piece, may I have it?");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Sure, for 1,000,000gp.");
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "No way!", 
					"Fine!");
			break;
		case 4:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "Well then, no map for you!");
				stage = 11;
				break;
			} else if(componentId == OPTION_2) {
				if (player.getInventory().containsItem(995, 1000000)) {
					player.getInventory().removeItemMoneyPouch(995, 1000000);
					player.getInventory().addItem(1537, 1);
				sendNPCDialogue(npcId, 9827, "Pleasure doing business with you.");
				stage = 11;
				break;
				} else {
				stage = 11;
				sendNPCDialogue(npcId, 9827, "You don't have the money!");
				break;
				}
			} 
		case 11:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}