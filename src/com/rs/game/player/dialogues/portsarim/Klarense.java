package com.rs.game.player.dialogues.portsarim;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Klarense extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "So, are you interested in buying a ship? Now, I'll be",
				"straight with you: she's not quite seaworthy right now,",
				"but if you purchase her I'll give her some work and ",
				"she'll be the nippiest ship this side of Port Khazard.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Would you take me to Crandor when she's ready?", 
					"I'd like to buy her.", "Ah well, never mind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "Crandor? You're joking, right?");
				stage = 1;
				break;
			} else if(componentId == OPTION_2) {
				if (player.boughtship) {
				stage = 11;
				sendNPCDialogue(npcId, 9827, "You already purchased this ship you fool!");
				} else {
				stage = 5;
				sendNPCDialogue(npcId, 9827, "How does 5,000,000 gold sound? I'll even throw in my",
						"cabin boy, Jenkins, for free! He'll swab the decks and",
						"splice the mainsails for you!");
				}
				break;
			} else if(componentId == OPTION_3) {
				sendPlayerDialogue(9827, "Okay, have a nice day!");
				stage = 11;
				break;
			} 
		case 1:
			stage = 2;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes. Ha ha ha!", 
					"No. I want to go to Crandor.");
			break;
		case 2:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "Hah, that's a good one then.");
				stage = 11;
				break;
			} else if(componentId == OPTION_2) {
				stage = 3;
				sendNPCDialogue(npcId, 9827, "Then you must be crazy.");
				break;
			} 
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "That island is surrounded by reefs that would rip this",
					"ship to shreds. You'd need an experienced captain to",
					"stand a change of getting through.");
			break;
		case 4:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "And, even if I could get to it, there's no way I'm going",
					"any closer to that dragon than I have to. They say it",
					"can destroy whole ships in one bite.");
			break;
		case 5:
			stage = 6;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yep, sounds good.", 
					"I'm not paying that much for a boat!");
			break;
		case 6:
			if(componentId == OPTION_1) {
				if (player.getInventory().containsItem(995, 5000000)) {
					player.getInventory().removeItemMoneyPouch(995, 5000000);
					player.boughtship = true;
					sendNPCDialogue(npcId, 9827, "Okey dokey, she's all yours!");
				} else {
					sendNPCDialogue(npcId, 9827, "Sorry, but it seems like you don't have enough money.");
				}
				stage = 11;
				break;
			} else if(componentId == OPTION_2) {
				stage = 3;
				sendNPCDialogue(npcId, 9827, "Sorry, but I won't go any lower.");
				break;
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