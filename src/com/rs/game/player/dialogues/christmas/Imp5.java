package com.rs.game.player.dialogues.christmas;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Imp5 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Oh NO! what ever shall we do.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Whats the matter little guy?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "Christmas is not comming this Year!");
			break;
		case 1:
			stage = 2;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "No worries.", 
					"That sucks.");
			break;
		case 2:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Not to fret little friend! Santa told me himself, Christmas is still on its way!");
				stage = 5;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Yea... That's unfortunate.");
				stage = 4;
				break;
			}
		case 3:
			end();
			break;
		case 4:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "WHAAAAAAA!!");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "REALLY!? Do you really mean it! Theres going to be Christmas this year after all!");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(9827, "Yes I do, now dry those tears and brighten that smile. Good Ol' Saint Nick is on his way!");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "Woohoo yes!");
			player.christmas = 7;
			player.getInventory().deleteItem(4597, 1);
				break;
		case 8:
			stage = 9;
			sendPlayerDialogue(9827, "Now there is no time to waist. Help spread the word, to all the Elves as fast as you can!");
			break;
		case 9:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Yes Sir!");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}