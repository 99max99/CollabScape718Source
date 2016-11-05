package com.rs.game.player.dialogues.christmas;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Imp2 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Hello!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "Mm yes, good day sire!");
			break;
		case 0:
			stage = 1;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "I was sent by Santa.", 
					"Wrong imp.");
			break;
		case 1:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Actually as a matter of fact I do... lots. What kind do you need?");
				stage = 4;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Oh sorry, got the wrong imp.");
				stage = 2;
				break;
			}
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Mmm, yes quite! No worries friend!");
			break;
		case 3:
			end();
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Oh why yes I do....  Ah ha, Here it is!");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "May I have it please, I must hurry!");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Did I give any insinuation that I wasn't giving it to you? ",
					"You really must calm down young squire, it belittles you to be seen in such a ",
					"non-laudable and visceral state of being. Please becareful with this potion for ",
					"it is very important to the Reindeers health if they are to fly on Christmas Eve.");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "Okay then, umm.. May I please have the potion, my kind sir?");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "Why of course my good man, here you go.");
			player.getInventory().addItem(195, 1);
			player.christmas = 4;
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "Okay I'll try to get there as safe and as fast as possible!");
			break;
		case 10:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Cheerio!!");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}