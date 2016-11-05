package com.rs.game.player.dialogues.christmas;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Imp4 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Hey there!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "G'day mate! What brings you to Edgeville; ",
					"come to fight some other humans, eh?");
			break;
		case 0:
			stage = 1;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "I have something.", 
					"Nothing.");
			break;
		case 1:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "I have the Example Present right here for you!");
				stage = 4;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Nothing never mind me, mate?");
				stage = 2;
				break;
			}
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "What a tosser.");
			break;
		case 3:
			end();
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "What Example present?");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "The one you must deliver to the elves.");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Why would I do that? Sounds better if I kept it for my self, eh?");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "No this must be delivered to the elves, they need it to teach the new elves how to make the toys!");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "Oh, I thought you where giving me a gift, well I am slightly sad now. ",
					"Well this shal be delivered to the elves as soon as possible!");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "Are you alright there?");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "Good as gold, she'll be right!");
			break;
		case 11:
			stage = 12;
			sendNPCDialogue(npcId, 9827, "You must find the last imp and give him this note and it will notify him that ",
					"Christmas is still on and everyone can start to prepare for it!");
			player.christmas = 6;
			player.getInventory().deleteItem(6542, 1);
			player.getInventory().addItem(4597, 1);
			break;
		case 12:
			stage = 13;
			sendPlayerDialogue(9827, "Okay I shal go and find him now!");
			break;
		case 13:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "At godspeed man!");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}