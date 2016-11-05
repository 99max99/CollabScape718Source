package com.rs.game.player.dialogues.edgeville;

import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Oziach extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Aye, 'tis a fair day my friend.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Buy rune platebody or talk about quest.", 
					"I'm not your friend", "Yes, it's a very nice day.","Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				if (player.DS == 0) {
				sendNPCDialogue(npcId, 9827, "I'm sorry but I will not sell you a rune platebody.");
				stage = 11;
				break;
				} else if (player.DS == 1) {
				stage = 2;
				sendNPCDialogue(npcId, 9827, "So, how does thee know I 'ave some?");
				player.DS = 2;
				break;
				} else if (player.DS == 2) {
				stage = 11;
				sendNPCDialogue(npcId, 9827, "You should go speak to the Guild Master now.");
				break;
				} else if (player.DS == 3 || player.DS == 4 || player.DS == 5) {
				stage = 11;
				sendNPCDialogue(npcId, 9827, "Please slay the dragon first."); 
				break;
				} else if (player.DS == 6) {
				if (player.getInventory().containsItem(11279, 1)) {
					player.getInventory().deleteItem(11279, 1);
					player.DS = 7;
					sendNPCDialogue(npcId, 9827, "You are truely a hero, thanks so much!"); 
					player.getInterfaceManager().handleDragonQuestInterface();
					stage = 11;
					break;
				} else {
					sendNPCDialogue(npcId, 9827, "Please I want his head."); 
					stage = 11;
					break;
				}
				} else if (player.DS == 7) {
					stage = 14;
					sendNPCDialogue(npcId, 9827, "That will be 65,000 coins."); 
					break;
				}
			} else if(componentId == OPTION_2) {
				stage = 1;
				sendPlayerDialogue(9827, "I am not your friend.");
				break;
			} else if(componentId == OPTION_3) {
				sendPlayerDialogue(9827, "It's a very nice day indeed.");
				stage = 11;
				break;
			} else if(componentId == OPTION_4) {
				stage = 11;
				break;
			} 
		case 1:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "No need to be so rude.");
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(9827, "The Guildmaster of the Champions' Guild told me.");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Yes, I suppose he would, wouldn't he? He's always",
					"sending you fancy-pants 'heroes' up to bother me.",
					"Telling me I'll give them a quest or sommat like that.");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Well, I'm not just going to let anyone wear my rune",
					"platemail. It's only for heroes. So, leave me alone.");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "I thought you were going to give me a quest.",
					"That's a pity, I'm not a hero.");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(9827, "*Sigh*");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "Slay the dragon of Crandor!");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "All right, I'll give ye a quest. I'll let you wear my rune",
					"platebody if ye...");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "A dragon, that sounds like fun.",
					"I may be a champion, but I'm not up for dragon-killing yet.");
			break;
		case 10:
			stage = 12;
			sendNPCDialogue(npcId, 9827, "Hah, yes, you are a typical reckless adventurer, aren't",
					"you? Now go kill the dragon and get out of my face.");
			break;
		case 11:
			end();
			break;
		case 12:
			stage = 13;
			sendPlayerDialogue(9827, "But how can I defeat the dragon?");
			break;
		case 13:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "Go talk to the Guildmaster in the Champions' Guild. He'll",
					"help you out if yer so keen on doing a quest. I'm not",
					"going to be handholding any adventurers.");
			break;
		case 14:
			stage = 11;
			if (player.getInventory().getCoinsAmount() >= 65000) {
				player.getInventory().removeItemMoneyPouch(new Item(995, 65000));
				player.getInventory().addItem(1127, 1);
				sendPlayerDialogue(9827, "Okay, here you go!");
				break;
			} else {
			sendNPCDialogue(npcId, 9827, "You don't have enough money, come back when you have it.");
			break;
			}
		}
	}

	@Override
	public void finish() {
		
	}
}