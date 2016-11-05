package com.rs.game.player.dialogues.alkharid;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class LouieLegs extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hey, wanna buy some armour?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What have you got?", 
					"No, thank you.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				stage = 1;
				sendPlayerDialogue(9827, "What have you got?");
				break;
			} else if(componentId == OPTION_2) {
				stage = 3;
				sendPlayerDialogue(9827, "No, thank you.");
				break;
			}
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "I provide items to help you keep your legs!");
			break;
		case 2:
			end();
			ShopsHandler.openShop(player, 100);
			break;
		case 3:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}