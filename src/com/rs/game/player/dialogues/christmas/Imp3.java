package com.rs.game.player.dialogues.christmas;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Imp3 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Oh dear... Oh my!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Are you okay?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "The reindeer is sick and needs medicine so it can make ",
					"its annual travel across the world for Christmas!");
			break;
		case 1:
			if(player.getInventory().containsItem(195, 1)) {
				sendPlayerDialogue(9827, "I have some right here! ");
				stage = 2;
				break;
			} else {
				sendPlayerDialogue(9827, "I'm sorry, I don't have this potion.");
				stage = 3;
				break;
			}
		case 2:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Oh! Do be careful with that! Just don't drop it! Please.");
			break;
		case 3:
			end();
			break;
		case 4:
			stage = 5;
			sendPlayerDialogue(9827, "I won't.");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "Yes! Thank you so much! Now the reindeer will be able to ",
					"recover before Christmas! Here take this present... Merry Christmas!");
			player.getInventory().deleteItem(195, 1);
			player.getInventory().addItem(6542, 1);
			player.christmas = 5;
			break;
		case 6:
			stage = 3;
			sendPlayerDialogue(9827, "Wow! Thanks!");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}