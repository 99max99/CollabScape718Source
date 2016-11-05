package com.rs.game.player.dialogues.alkharid;

import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class SilkTrader extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Do you want to buy any fine silks?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, " How much are they?", 
					"No, silk doesn't suit me.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "How much are they?");
				stage = 2;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "No, silk doesn't suit me.");
				stage = 1;
				break;
			} 
		case 1:
			end();
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "3gp.");
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, " No, that's too much for me.", 
					"Okay, that sounds good.");
			break;
		case 4:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "No, that's too much for me.");
				stage = 6;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Okay, that sounds good.");
				stage = 5;
				break;
			} 
		case 5:
			if (player.getInventory().getCoinsAmount() >= 3) {
				player.getInventory().removeItemMoneyPouch(new Item(995, 3));
				player.getInventory().addItem(950, 1);
				stage = 1;
				sendNPCDialogue(npcId, 9827, "Nice doing business with you!");
				break;
			} else {
				stage = 1;
				sendNPCDialogue(npcId, 9827, "SCAMMER! HELP HE/SHE/IT TRY TO SCAM ME!");
				break;
			}
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "2gp and that's as low as I'll go.");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "I'm not selling it for any less. You'll only go and sell it in",
					"Varrock for a profit.");
			break;
		case 8:
			stage = 9;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "2gp sounds good.", 
					"No, really, I don't want it.");
		case 9:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "2gp sounds good.");
				stage = 10;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "No, really, I don't want it.");
				stage = 1;
				break;
			} 
			break;
		case 10:
			if (player.getInventory().getCoinsAmount() >= 2) {
				player.getInventory().removeItemMoneyPouch(new Item(995, 2));
				player.getInventory().addItem(950, 1);
				stage = 1;
				sendNPCDialogue(npcId, 9827, "Nice doing business with you!");
				break;
			} else {
				stage = 1;
				sendNPCDialogue(npcId, 9827, "SCAMMER! HELP HE/SHE/IT TRY TO SCAM ME!");
				break;
			}
		case 11:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "Okay, but that's the best price you're going to get.");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}