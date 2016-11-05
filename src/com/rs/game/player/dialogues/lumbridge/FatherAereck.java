package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 * The Feather Dialogue for the Father Aereck.
 * 
 * @author Gircat <gircat101@gmail.com>
 * @author Feather RuneScape 2012
 */

public class FatherAereck extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Can you tell me about the battlefield?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, " Lumbridge battlefield is where Saradomin himself is currently fighting Zamorak!");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "Thank goodness that he is here! If he'd not shown up when he did, we'd all be thralls of Zamorak now!");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "But he came to us from far away in our time of need, and even now he fights evil on Lumbridge Battlefield.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Oh, you must be on your way to Saradomin's war camp in order to help out. It's to the northwest of here.");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "We cannot let evil conquer! Good luck! And may Saradomin be with you!");
			break;
		case 4:
			end();
			break;
			
		}
	}
	public void finish() {
	}

}
