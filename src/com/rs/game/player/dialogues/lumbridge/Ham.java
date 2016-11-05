package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Ham extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Welcome brother, how can I help you?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What is this organization?", 
					"What are you up to?", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "We are the Humans Against Monsters cult.");
				stage = 1;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "I am waiting on orders from Sigmund.");
				stage = 4;
				break;
			} else if(componentId == OPTION_3) {
				stage = 4;
				break;
			} 
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "What is the purpose of this group?");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "We are here to rid the world of Noszscape from the monsters",
					"that inhabit and spoil the society. We must get rid of all",
					"monsters. Death to the monsters!");
			break;
		case 3:
			sendPlayerDialogue(9827, "Death to the monsters!");
			stage = 4;
			break;
		case 4:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}