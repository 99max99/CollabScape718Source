package com.rs.game.player.dialogues.alkharid;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class AliMorrisane extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Oh hello there.. would you like to see my shop?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes, please.", 
					"No, thank you.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				end();
				ShopsHandler.openShop(player, 99);
				break;
			} else if(componentId == OPTION_2) {
				stage = 1;
				break;
			}
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "No, thank you.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Eh, suit yourself.");
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