package com.rs.game.player.dialogues.alkharid;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class FremennikWarrior extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello, would you like to know about the skill Dungeoneering?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, " Sure!", 
					"No thanks.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Please, tell me about this skill.");
				stage = 2;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "No thanks.");
				stage = 1;
				break;
			} 
		case 1:
			end();
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Well to start off, you can get to Daemonheim by sailing with",
					"the Fremennik Sailor who can be found on the bridge close to here.",
					"Once there if you journey north west, you can find the entrences.");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "There are different dungeons for different levels.");
			break;
		case 4:
			stage = 1;
			sendPlayerDialogue(9827, "Gee, thanks!");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}