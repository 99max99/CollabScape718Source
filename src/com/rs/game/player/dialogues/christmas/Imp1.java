package com.rs.game.player.dialogues.christmas;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Imp1 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "FOOD!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Excuse me?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "FOOD! FOOD! I need food! Do you happen to have any?!?");
			break;
		case 1:
			stage = 2;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Why do you need food?", 
					"Sorry no I don't");
			break;
		case 2:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "The Elves! The elves! They need it for the Christmas Feast or Christmas may never come...",
						"So back to my first question... Do you have any food?!? ");
				stage = 4;
				break;
			} else if(componentId == OPTION_2) {
				stage = 3;
				break;
			}
		case 3:
			end();
			break;
		case 4:
			stage = 5;
			sendPlayerDialogue(9827, "Actually as a matter of fact I do... lots. What kind do you need?");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "They are very specific with their food choices... *panicy laugh*",
					"Do you happen to have any Gielinor Games food?");
			break;
		case 6:
			stage = 7;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "As a matter of fact I do.", 
					"No I don't.");
			break;
		case 7:
			if(componentId == OPTION_1) {
				if (player.getInventory().containsItem(24543, 1)) {
				sendNPCDialogue(npcId, 9827, "Wow! How did you know I would need that food?!?");
				stage = 8;
				} else {
				sendNPCDialogue(npcId, 9827, "Grr... You lied to me! You don't have the food!");
				stage = 3;
				}
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "No, can you repeat it again please?");
				stage = 4;
				break;
			}
		case 8:
			stage = 9;
			sendPlayerDialogue(9827, "Santa asked me to find you and give you this specific food.");
			break;
		case 9:
			stage = 10;
			sendNPCDialogue(npcId, 9827, "Wow Santa himself?!?! You must be pretty important to have Santa ask you to ",
					"deliever this to me... He really must know everything...");
			break;
		case 10:
			stage = 11;
			sendPlayerDialogue(9827, "Yes he really does.");
			break;
		case 11:
			stage = 12;
			sendPlayerDialogue(9827, "Now take this and deliver it to the North Pole as fast as you can!");
			player.getInventory().deleteItem(24543, 1);
			player.christmas = 3;
			break;
		case 12:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Okay... okay! I will!");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}