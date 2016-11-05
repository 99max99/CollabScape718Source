package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class MagicInstructor extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Here you go, have some magic starting gear!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			sendPlayerDialogue(9827, "OH WOW THANK YOU!");
			player.getInventory().addItem(554, 10);
			player.getInventory().addItem(555, 10);
			player.getInventory().addItem(556, 10);
			player.getInventory().addItem(557, 10);
			player.getInventory().addItem(558, 10);
			stage = 0;
			break;
		case 0:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}