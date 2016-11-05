package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class JimmyChisel extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Those darn cults.....");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Why are you here?", 
					"How can we escape?", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "The H.A.M. cult is trying to recruit me,",
						"however I refuse to join them.");
				stage = 1;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "You need to pick the lock on the door,",
						"although I have no knowledge on those things.");
				stage = 1;
				break;
			} else if(componentId == OPTION_3) {
				stage = 1;
				break;
			} 
		case 1:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}