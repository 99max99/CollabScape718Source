package com.rs.game.player.dialogues.edgeville;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Oracle extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Greetings traveler, what brings you here?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			if (player.DS == 3) {
			stage = 2;
			sendPlayerDialogue(9827, "I am in search of Thalzar's map piece.");
			} else if (player.DS >= 4) {
			stage = 0;
			sendPlayerDialogue(9827, "I just wanted to thank you for the help.");
			} else {
			stage = 11;
			sendPlayerDialogue(9827, "Nothing in particular.");
			}
			break;
		case 0:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "It was my pleasure, good luck on your quest.");
			break;
		case 1:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "No need to be so rude.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "The map's behind a door below,",
					"but entering is rather tough",
					"This is what you need to know,",
					"You must use the following stuff:");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "First, a drink used by a mage,",
					"Next, some worm string changed to a sheet,",
					"Then, a small crustacean cage,",
					"Last, a bowl that's not seen heat.");
			break;
		case 4:
			stage = 11;
			sendPlayerDialogue(9827, "Thanks for the information!");
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