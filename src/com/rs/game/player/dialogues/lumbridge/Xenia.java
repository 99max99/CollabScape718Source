package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Xenia extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hey there...");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "I have some pointers I can share with you for the Prayer skill,",
					"are you interested in hearing them?");
			break;
		case 0:
			stage = 1;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Sure", 
					"No Thanks");
			break;
		case 1:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "Do you see the altar in the Lumbridge Church?");
				stage = 2;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "Okay, that is your loss...");
				stage = 5;
				break;
			} 
		case 2:
			stage = 3;
			sendPlayerDialogue(9827, "Yeah, the one just north of us?");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Yup, that one. If you use your bones on",
					"the altar, you will get about double the exp",
					"you would normally get burying bones.");
			break;
		case 4:
			stage = 5;
			sendPlayerDialogue(9827, "Thanks for the information!");
			break;
		case 5:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}