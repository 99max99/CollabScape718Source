package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;
/**
 * Begins the dialogue for Explorer Jack.
 * @author Gircat <gircat101@gmail.com>
 * Feather - RuneScape 2012
 */
public class ExplorerJack extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, " My house! There I was, sat in the living room, having a ",
				"nice cup of tea and minding my own business, and BANG!",
				"My house collapses around me!");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "I went to look - after all I am an explorer - and there, ",
					"right outside my house, I find Saradomin and Zamorak fighting it out! ");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827,
					"Seems that their battle had a rather unfortunate effect on my ",
					"foundations. So, I went back inside and made another cup of tea.");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827,
					"I thought to myself that it'll take more than a world-destroying",
					"battle between two gigantic all-powerful gods to stop me!");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827,
					"So I'm not letting it get me down. ",
					"I'm still a taskmaster and I have a job to do!");
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