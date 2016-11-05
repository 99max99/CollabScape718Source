package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Lachtopher extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello, do you spare me 10,000 coins?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes", 
					"No");
			break;
		case 0:
			if(componentId == OPTION_1) {
				if (player.getInventory().containsItem(995, 10000)) {
				player.getInventory().removeItemMoneyPouch(995, 10000);
				sendNPCDialogue(npcId, 9827, "Thank you, now bye-bye!");
				} else {
					sendNPCDialogue(npcId, 9827, "Your poor self has no money to spare, die!");
				}
				stage = 1;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "You are a cruel person, go die in a hole.");
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