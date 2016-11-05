package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Victoria extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Pssst.. Hey you!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "What?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "I know how you can make some sweet money. Now listen up.");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "Go on...");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Well there are some stalls at home, in the Lumbridge Courtyard.",
					"You can steal from them, the stalls have a wide variety of things",
					"such as scimitars, runes, crafting supplies, and more! If you want",
					"to turn that into cash, you can sell it to the General Store north of here.");
			break;
		case 3:
			sendPlayerDialogue(9827, "Thanks!");
			stage = 4;
		case 4:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}