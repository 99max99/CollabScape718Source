package com.rs.game.player.dialogues.varrock;

import com.rs.game.player.dialogues.Dialogue;

public class Aubury extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = 5913;
		if (player.RM == 2 && player.getInventory().containsItem(290, 1)) {
			sendNPCDialogue(npcId, 9827, "Do you want to buy some runes?");
			stage = -1;
		} else if (player.RM == 3) {
			sendNPCDialogue(npcId, 9827, "Oh thanks, they were research notes.");
			stage = 8;
		} else {
			sendNPCDialogue(npcId, 9827, "I am very busy, please leave.");
			stage = 4;
		}

	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			sendNPCDialogue(npcId, 9827, "I have been sent here with a package for you. It's from", "the head wizard at the Wizard's Tower.");
			stage = 1;
			break;
		case 1:
			sendNPCDialogue(npcId, 9827, "Really? But... surely he can't have...? Please, let me",
					"have it, it must be extremely important for him to have sent a stranger.");
			stage = 2;
			break;
		case 2:
			sendNPCDialogue(npcId, 9827, "The duke of Lumbridge sent me to find him. I have",
					"this weird talisman he found. He said the head wizard", "would be interested in it.");
			stage = 3;
			break;
		case 3:
			sendNPCDialogue(npcId, 9827, "Did he now? HmmmMMMMMmmmmm.", "Well that IS interesting. Hand it over then adventurer,",
					"let me see what all the hubbub about it is.", "Just some amulet I'll wager.");
			stage = 5;
			break;
		case 4:
			end();
			break;
		case 5:
			sendDialogue("You hand Aubury the research package.");
			player.getInventory().deleteItem(290, 1);
			stage = 6;
			break;
		case 6:
			sendNPCDialogue(npcId, 9827, "This.. this is incredible! Please, give me a few seconds to examine it.");
			stage = 7;
			break;
		case 7:
			player.RM = 3;
			stage = 4;
			break;
		case 8:
			end();
			player.RM = 4;
			player.getInterfaceManager().sendRuneComplete();
			break;
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
