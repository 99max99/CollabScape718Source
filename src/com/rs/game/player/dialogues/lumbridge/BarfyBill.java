package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class BarfyBill extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Oh! Hello there.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Who are you?", 
					"Can you teach me about canoeing?", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "My name is Ex Sea Captain Barfy Bill.");
				stage = 1;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "It's really quite simple. Just walk down to that tree on the bank and chop it down.");
				stage = 9;
				break;
			} else if(componentId == OPTION_3) {
				stage = 11;
				break;
			} 
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "Ex Sea captain?");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Yeah, I bought a lovly ship and was planning to make a fortune running her as a merchant vessel.");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Why are you not still sailing?");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Chronic Sea sickness. My first, and only, voyage was spent dry heaving over the rails.",
					"If I had known about the sea sickness I could saved myself a lot of money.");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "What are you up to now then?");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Well my ship had a little fire related problem. Fortunately it was well insured.",
					"Anyway, I don't have to work anymore so ive taken to canoeing on the river.",
					"I don't get river sick.",
					"Would you like to know how to make a canoe?");
			break;
		case 7:
			stage = 8;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes", 
					"No");
			break;
		case 8:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "It's really quite simple. Just walk down to that tree on the bank and chop it down.");
				stage = 9;
				break;
			} else if(componentId == OPTION_2) {
				stage = 11;
				break;
			}
		case 9:
			stage = 10;
			sendNPCDialogue(npcId, 9827, "When you have done that you can shape the log further with your hatchet to make a canoe.",
					"Hoo! You look like you know which end of a hatchet is which!",
					"You can easily build one of those Wakas. Be carful if you travel into the Wilderness though.",
					"I've heard tell of great evil in that blasted wasteland.");
			break;
		case 10:
			sendPlayerDialogue(9827, "Thanks for the warrning Bil.");
			stage = 11;
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