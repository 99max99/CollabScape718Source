package com.rs.game.player.dialogues.alkharid;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.content.PartyRoom;
import com.rs.game.player.dialogues.Dialogue;

public class ShantayWarning extends Dialogue {

	@Override
	public void start() {
		player.getDialogueManager().startDialogue("SimpleMessage","The warning signs in front of the huge stone gate state:");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			player.getDialogueManager().startDialogue("SimpleMessage","<col=D11919>The Kharidian Desert is a VERY dangerous place. Beware of high"
					+ "temperatures, sandstorms, quicksand, bandits, slavers, kalphites,"
					+ "monkeys, crocodiles, and acts of vengeful goddesses bent on the total"
					+ "destruction of all life in the desert.");
			stage = 1;
		} else if (stage == 1) {
			player.getDialogueManager().startDialogue("SimpleMessage","No responsibility is taken by Shantay if anything bad should happen to you"
					+ "under any circumstances whatsoever.");

		}
		 
	}

	@Override
	public void finish() {

	}
}
