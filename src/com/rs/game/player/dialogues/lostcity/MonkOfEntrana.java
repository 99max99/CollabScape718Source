package com.rs.game.player.dialogues.lostcity;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.WorldTile;


public class MonkOfEntrana extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello, Would you like to travel to Entrana");
		}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes Please.", "No Thanks.");
			break;
		case 0:
			if(componentId == OPTION_2) {
				stage = 1;
				sendPlayerDialogue(9827, "No Thanks.");
			}else {
				stage = 2;
				sendPlayerDialogue(9827, "Yes Please");
			}
			break;
		case 1:
			stage = -2;
			sendNPCDialogue(npcId, 9827, "Have A Great Day.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "To travel to Entrana you will need to be checked for any weapons or equipment.");
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Sure go ahead, I have nothing to hide", "Your not touching me!");
			break;
		case 4:
			if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Your not touching me!");
				end();
			}else {
				stage = 5;
				sendPlayerDialogue(9827, "Sure go ahead, I have nothing to hide");
			}
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "<col=FF0000>Your Inventory will be emptied so bank your items if you wish to travel to Entrana.<col>");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Second reminder, YOUR INVENTORY WILL BE BANKED!");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "We will supply you wil runes once you arrive. Good luck.");
			break;
		case 8:
			stage = 9;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "I'd like to go to Entrana now", "I need to go bank my items", "I'm off bye!");
			break;
		case 9:
			if(componentId == OPTION_3) {
				stage = 1;
				sendPlayerDialogue(9827, "I'm off bye!");
			}else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "I need to go bank my items");
				end();
			}else {
				stage = 10;
				sendPlayerDialogue(9827, "I'd like to go to Entrana now");
			}
			break;
		case 10:
			if(player.getEquipment().wearingArmour()) {
				stage = -2;
				sendNPCDialogue(npcId, 9827, "You have items on you! COME BACK WHEN YOU DONT HAVE ANY ITEMS ON YOU!");
			}else{
				stage = 11;
				sendNPCDialogue(npcId, 9827, "Have A Great Day Sir.");
				player.spokeToMonk = true;
			}
			break;
		case 11:
			stage = 12;
			sendPlayerDialogue(9827, "Thank You.");
			break;
		case 12:
			player.getBank().depositAllInventory(false);
			player.setNextWorldTile(new WorldTile(2834, 3335, player.getPlane()));
			player.spokeToMonk = true;
			if (player.recievedRunes == false) {
			player.getInventory().addItem(560, 200);
			player.getInventory().addItem(555, 200);
			player.getInventory().addItem(565, 200);
			player.getInventory().addItem(562, 200);
			player.getInventory().addItem(556, 200);
			player.getInventory().addItem(554, 200);
			player.getInventory().addItem(557, 200);
			player.getInventory().addItem(558, 200);
			player.recievedRunes = true;
			}
			end();
			break;
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}
