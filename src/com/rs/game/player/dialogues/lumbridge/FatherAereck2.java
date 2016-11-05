package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

public class FatherAereck2 extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "have you got rid of the ghost yet?" );
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendPlayerDialogue( 9827, " I had to talk with Father Urhney. He has given me",
					"a funny amulet for talking to ghosts.");
			
		} else if (stage == 0) {
			sendNPCDialogue(npcId, 9827, "I always wondered what that amulet was. Well,",
					"I hope it's useful. Tell me when you get rid of the ghost.");
		} else
			end();
	}

	@Override
	public void finish() {

	}

}