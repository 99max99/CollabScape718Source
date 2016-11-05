package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;

public class Sedridor extends Dialogue {

	private int npcId = 300;

	@Override
	public void start() {
		if (player.RM == 1 && player.getInventory().containsItem(1438, 1)) {
			sendNPCDialogue(npcId, 9827, "Welcome adventurer, to the world renowned", "Wizard's Tower. How may I help you?");
		} else {
			sendNPCDialogue(npcId, 9827, "I am very busy, please leave.");
			stage = 4;
		}

	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			sendNPCDialogue(npcId, 9827, "I'm looking for the head wizard.");
			stage = 1;
			break;
		case 1:
			sendNPCDialogue(npcId, 9827, "Oh, you are, are you?", "and just why would you be doing that?");
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
			sendDialogue("You hand over the talisman.");
			player.getInventory().deleteItem(1438, 1);
			stage = 6;
			break;
		case 6:
			sendNPCDialogue(npcId, 9827, "Wow! This is... incredible!");
			stage = 7;
			break;
		case 7:
			sendNPCDialogue(npcId, 9827, "The owner couldn't be bothered to write out the rest, please take this parcel to Aubury in varrock.");
			player.getInventory().addItem(290, 1);
			player.RM = 2;
			stage = 4;
			break;
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
