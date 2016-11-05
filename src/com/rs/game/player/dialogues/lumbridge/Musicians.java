package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.actions.Listen;
import com.rs.game.player.dialogues.Dialogue;

/**
 * The Feather Dialogue for Musicians.
 * 
 * @author Gircat <gircat101@gmail.com>
 * @author Feather RuneScape 2012
 */

public class Musicians extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello, "+ player.getDisplayName() +"!",
				"Would you like to hear some of the finest music",
				"that is being played around the lands of CollabScape?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes please!", "No thank you.");
			break;
		case 0:
			if(componentId == OPTION_2) {
				stage = 1;
				sendPlayerDialogue(9827, "No thank you.");
			}else {
				stage = 2;
				sendPlayerDialogue(9827, "Yes please!");
			}
			break;
		case 1:
			end();
			break;
		case 2:
			player.stopAll();
			player.getActionManager().setAction(new Listen());
			end();
			break;
		}
	}
	
	
	
	
	
	
	
	
	
//	@Override
//	public void run(int interfaceId, int componentId) {
//		switch(stage) {
//		case -1:
//			stage = 0;
//			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes please.", "No thank you.");
//			break;
//		case 0:
//			if(componentId == OPTION_2) {
//				stage = 1;
//				sendPlayerDialogue(9827, "No thank you.");
//			}else if (componentId == OPTION_1) {
//				stage = 2;
//				sendPlayerDialogue(9827, "Yes please.");
//			}
//			break;
//			}
//		}
	@Override
	public void finish() {
		
	}
}
