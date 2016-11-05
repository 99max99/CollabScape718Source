package com.rs.game.player.dialogues.worldwide;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

/**
 *@Author Danny
 */

public class GnomeGlider extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello, would you like to travel on the Gnome Glider?");
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
				end();
				player.getInterfaceManager().sendInterface(138);
				break;
			} else if(componentId == OPTION_2) {
				end();
				break;
			}
		}
	}

	@Override
	public void finish() {
		
	}
}