package com.rs.game.player.dialogues.portsarim;

import com.rs.game.player.dialogues.Dialogue;

public class StankyMorgan extends Dialogue {
	
	public int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Arrgh  YOU AR C-U-T-E! ;)");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Who're you?" );
		break;
		case 0:
			stage = -2;
			sendNPCDialogue(npcId, 9827, "They call me Morgan in these parts, you can call me a dream com true. ;) ",
				"For a cost.. 50,000 Gold Pieces and you can take me for the night!",
					"What do ye' say sexy!? ;)" );
		break;
		case 1:
			stage = -4;
			sendPlayerDialogue(9827, "Uhhhm no thankss... you're stanky gurrrl!" );
		break;
		default:
			end();
		break;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}