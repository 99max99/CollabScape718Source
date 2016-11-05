package com.rs.game.player.dialogues.draynor;

import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Ned1 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Why, hello there, lass. Me friends call me Ned. I was a",
				"man of the sea, it's past me now. Could I be",
				"making or selling you some rope?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "You're a sailor? Could you take me to Crandor?", 
					"Yes, I would like some rope.", "No thanks, Ned, I don't need any.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				if (player.DS == 3) {
					if (player.getInventory().containsItem(1538, 1)) {
						player.getInventory().deleteItem(1538, 1);
						player.DS = 4;
						sendNPCDialogue(npcId, 9827, "Well, I was a sailor. I've not been able to get work at",
								"sea these days, though. They say I'm too old.");
						stage = 1;
					} else {
						sendNPCDialogue(npcId, 9827, "I would need to look at a map first.");
						stage = 11;
					}
				} else if (player.DS <= 2) {
				sendNPCDialogue(npcId, 9827, "I wouldn't dream of it lad.");
				stage = 11;
				} else if (player.DS == 4) {
				sendNPCDialogue(npcId, 9827, "Well, I was a sailor. I've not been able to get work at",
								"sea these days, though. They say I'm too old.");
				stage = 1;
				} else if (player.DS >= 5) {
				sendNPCDialogue(npcId, 9827, "We don't need to sail there again.");
				stage = 1;
				}
				break;
			} else if(componentId == OPTION_2) {
				if (player.getInventory().containsItem(1759, 4)) {
				stage = 11;
				player.getInventory().deleteItem(1759, 4);
				player.getInventory().addItem(954, 1);
				sendNPCDialogue(npcId, 9827, "Thanks for the business!");
				} else if (player.getInventory().getCoinsAmount() >= 15) {
				stage = 11;
				player.getInventory().removeItemMoneyPouch(new Item(995, 15));
				player.getInventory().addItem(954, 1);
				sendNPCDialogue(npcId, 9827, "Thanks for the business!");
				} else {
				stage = 11;
				sendNPCDialogue(npcId, 9827, "You need either 15 coins or 4 balls of wool for a piece of rope.");
				}
				break;
			} else if(componentId == OPTION_3) {
				stage = 11;
				break;
			} 
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "Sorry, where was it you said you wanted to go?");
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(9827, "To the island of Crandor.");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Crandor?");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "But... It would be a chance to sail a ship once more.",
					"I'd sail anywhere if it was a chance to sail again.");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "Then again, no captain in his right mind would sail to",
					"that island.");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(9827, "Ah, you only live once! I'll do it!");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "So, where's your ship?");
			break;
		case 8:
			if (player.boughtship) {
			stage = 10;
			sendPlayerDialogue(9827, "It's the Lady Lumbridge, in Port Sarim.");
			} else {
			stage = 9;
			sendPlayerDialogue(9827, "I don't own a ship.");
			}
			break;
		case 9:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "Come back when you have purchased a ship.");
			break;
		case 10:
			stage = 12;
			sendNPCDialogue(npcId, 9827, "That old pile of junk? Last I heard, she wasn't",
					"seaworthy.");
			break;
		case 11:
			end();
			break;
		case 12:
			if (player.patched) {
			stage = 13;
			sendPlayerDialogue(9827, "I had her fixed up!");
			} else {
			stage = 11;
			sendPlayerDialogue(9827, "I will come back once I've fixed it up.");
			}
			break;
		case 13:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "You did? Excellent! I'll meet you at the ship,",
					"then.");
			player.DS = 5;
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}