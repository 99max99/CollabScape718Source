package com.rs.game.player.dialogues.christmas;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Santa1 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Hello Santa!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			if (player.christmas == 0 || player.christmas == 1) {
			stage = 18;
			sendPlayerDialogue(9827, "Why are you here so early?");
			break;
			} else if (player.christmas == 7) {
				stage = 16;
				sendNPCDialogue(npcId, 9827, "Oh thank you mighty adventurer! I hope the Imps didn't give you to much trouble!");
			break;
			} else {
			stage = 13;
			sendNPCDialogue(npcId, 9827, "Do you need your instructions again?");
			break;
			}
		case 0:
			stage = 1;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Sure! What's wrong?", 
					"I'm far to busy.", "Nevermind.");
			break;
		case 1:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Sure santa! What seems to be the problem?");
				player.christmas = 1;
				stage = 4;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Nah I'am far to busy to help you.");
				stage = 3;
				break;
			} else if(componentId == OPTION_3) {
				stage = 3;
				break;
			} 
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Christmas is doomed with out your help! DOOMED I SAY!!");
			break;
		case 3:
			end();
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "I must find my Snow Imps, they are all scatered all over Noszscape,",
					"and with out them the Christmas feast and Christmas is ruined! ");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "They are to do a demenstration of a toy to the elves so they can teach the ",
					"new elves how to make the toys and other important tasks. You must help me ",
					"find these imps before it is to late and Chirstmas is cancelled!");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(9827, "What feast; I never heard of a Christmas feast, and where can I find these imps?");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "Well first you must take this food (hands him/her the games food) and you ",
					"must take it to the imp in the white wolf mountians region, in which you tell",
					"him he must deliever it to the north pole for the Christmas feast.");
			player.getInventory().addItem(24543, 1);
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "After you give the food to him, go to the second snow imp in Varrok. ",
					"You will need to tell him to find the some of the missing elves so there ",
					"is still time to make all the presents for Christmas, and ask him about ",
					"a potion to help heal the sick reindeer.");
			break;
		case 9:
			stage = 10;
			sendNPCDialogue(npcId, 9827, "Once you have the potion take it to the third snow imp, which can be found ",
					"at a petstore tending to the animals there, though I am not sure which ",
					"one it is. He should give you a example present.");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "Once you have the example present you need to deliver it to the fourth snow imp,",
					"which can be found at the smithery in Edgeville. He will need to take the ",
					"present to the elves to help them teach the new elves how ",
					"to make the toys for Christmas!");
			break;
		case 11:
			stage = 12;
			sendNPCDialogue(npcId, 9827, "And after all of that you must find the fith snow imp, which is found some where ",
					"underground in Lumbridge, litterly any where underground in Lumbridge. He is ",
					"needed to be told that he is to give the all clear for everyone to get prepaired; ",
					"for Christmas is back ON, and we need to get done with everything as soon as possible!");
			break;
		case 12:
			stage = 13;
			sendNPCDialogue(npcId, 9827, "Did you get all of that?");
			break;
		case 13:
			stage = 14;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes", 
					"No");
			break;
		case 14:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Yes Santa, I will get to it right away!");
				player.christmas = 2;
				stage = 15;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "No, can you repeat it again please?");
				stage = 4;
				break;
			}
		case 15:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Please hurry, we havn't much time to spare! I greatly appreciate your help!");
			break;
		case 16:
			stage = 17;
			sendPlayerDialogue(9827, "Oh they didn't give me to much trouble, just glad I was able to help make Christmas happen!");
			break;
		case 17:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "You may now have access to the snow realm. Here we are trying to fight",
					"an army of the ice creatures who are attempting to take over our homeland.",
					"If you help kill these creatures you can redeem some rewards by talking to Jack Frost.");
			player.snowrealm = true;
			break;
		case 18:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "I'm here because I need help or else Christmas won't be happening this year!",
					"Can you please help me out?");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}