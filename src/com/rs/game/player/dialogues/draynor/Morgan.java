package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Morgan extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Greetings traveler.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Talk about Quest.", 
					"Who are you?", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				if (player.VS == 0) {
				sendPlayerDialogue(9827, "I am in search of a quest, would you happen to have one?");
				stage = 1;
				break;
				} else if (player.VS >= 1 && player.VS <= 3) {
				sendNPCDialogue(npcId, 9827, "Have you slain the vampyre yet?");
				stage = 8;
				break;
				} else if (player.VS == 4) {
				stage = 10;
				sendPlayerDialogue(9827, "The vampyre has been slain.");
				break;
				} else if (player.VS == 5) {
				stage = 11;
				sendNPCDialogue(npcId, 9827, "Currently I have no quests for you...");
				break;
				}
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Who are you?");
				stage = 7;
				break;
			} else if(componentId == OPTION_3) {
				stage = 11;
				break;
			} 
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "Yes, infact! You see on my recent missionary",
					"to Draynor village I have been troubled by a",
					"vampyre who has been haunting the village.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Would you be able to help me kill this vampyre?");
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes", 
					"No");
			break;
		case 4:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "I would be glad to help out a brother!");
				player.VS = 1;
				stage = 5;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "I'm sorry but I'm a bit preocuppied right now.");
				stage = 11;
				break;
			}
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "Thank you so much! Now I have little knowledge of slaying vampyres,",
					"there is a man though who has a profound knowledge of these creates.",
					"His name is Dr. Harlow and he can be found in the Blue Moon Inn ",
					"which is located in Varrock.");
			break;
		case 6:
			sendPlayerDialogue(9827, "I am on that, I will do whatever I can!");
			stage = 11;
			break;
		case 7:
			sendNPCDialogue(npcId, 9827, "I am a missionary here to spread the word about Guthix",
					"to the citizens of Draynor Village.");
			stage = 11;
			break;
		case 8:
			sendPlayerDialogue(9827, "No, I have not yet.");
			stage = 9;
			break;
		case 9:
			sendNPCDialogue(npcId, 9827, "Please do hurry, people are dying by the minute.");
			stage = 11;
			break;
		case 10:
			sendNPCDialogue(npcId, 9827, "Thank you so much!");
			player.getInterfaceManager().handleVampyreQuestInterface();
			player.VS = 5;
			stage = 11;
			break;
		case 11:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}