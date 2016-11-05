package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class DukeHoracio extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Greetings. Welcome to my castle.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "I seek a shield that will protect me from dragonbreath.",
					"Talk about a quest.",
					"Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "A knight going on a dragon quest, hmm? What dragon ",
						"do you intend to slay?");
				stage = 1;
				break;
			} else if(componentId == OPTION_2) {
				stage = 15;
				sendPlayerDialogue(9827, "Have you any quests for me?");
				stage = 1;
				break;
			} else if(componentId == OPTION_3) {
				stage = 14;
				break;
			} 
		case 1:
			stage = 2;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Elvarg, the dragon of Crandor island!", 
					"Oh, no dragon in particular.");
			break;
		case 2:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "Elvarg? Are you sure?");
				stage = 3;
				break;
			} else if(componentId == OPTION_2) {
				stage = 13;
				break;
			} 
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes", 
					"No");
			break;
		case 4:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "Well, you're a braver adventurer than I!");
				stage = 5;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "I'd better leave that dragon alone. ",
						"So, are you going to give me the shield or not?");
				stage = 10;
				break;
			} 
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "Why is everyone so scared of this dragon?");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Back in my father's day, Crandor was an important ",
					"city-state. Politically, it was as important as Falador or ",
					"Varrock and its ships traded with every port.");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "But, one day when I was little, all contact was lost. The ",
					"trading ships and the diplomatic envoys just stopped ",
					"coming.");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "I remember my father being very scared. He posted ",
					"lookouts on the roof to warn if the dragon was ",
					"approaching. All the city rulers were worried that ",
					"Elvarg would devastate the whole continent.");
			break;
		case 9:
			stage = 10;
			sendNPCDialogue(npcId, 9827, "If you really think you're up to it then perhaps you ",
					"are the one who can kill this dragon.");
			break;
		case 10:
			stage = 11;
			sendEntityDialogue(SEND_1_TEXT_CHAT, new String[] {
					"The Duke hands you a heavy orange shield." }, IS_ITEM, 1540, 1);
			player.getInventory().addItem(1540, 1);
			break;	
		case 11:
			stage = 12;
			sendNPCDialogue(npcId, 9827, "Take care out there. If you kill it...");
			break;
		case 12:
			sendNPCDialogue(npcId, 9827, "If you kill it, for Saradomin's sake make sure it's really dead!");
			stage = 14;
			break;
		case 13:
			stage = 11;
			sendPlayerDialogue(9827, "Oh, no dragon in particular. I just feel like killing a ",
					"dragon.");
			break;
		case 14:
			end();
			break;
		case 15:
			sendNPCDialogue(npcId, 9827, "Well, it's not really a quest but I recently discovered", "this strange talisman.");
			stage = 16;
			break;
		case 16:
			sendNPCDialogue(npcId, 9827, "It seems to be mystical and I have never seen anything",
					"like it before. Would you take it to the head wizard at");
			stage = 17;
			break;
		case 17:
			sendNPCDialogue(npcId, 9827, "the Wizard's Tower for me? It's just south-west of here and should not take you very long at all. I would be", "awfully grateful.");
			stage = 18;
			break;
		case 18:
			sendNPCDialogue(npcId, 9827, "Thank you very much stranger. I am sure the head",
					"wizard will reward you for such an interesting find.");
			stage = 19;
			break;
		case 19:
			player.getDialogueManager().startDialogue("SimpleMessage", "The duke hands you an <col=00a8ec>air talisman</col>.");
			player.RM = 1;
			player.getInventory().addItem(1438, 1);
			stage = 14;
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}