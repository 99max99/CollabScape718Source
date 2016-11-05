package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 * The Feather Dialogue for Lumbridge Sage.
 * 
 * @author Gircat <gircat101@gmail.com>
 * @author Feather RuneScape 2012
 */

public class LumbridgeSage extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Greetings, adventurer. How may I help you?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Who are you?", 
					"Tell me about the town of Lumbridge.",
					"Goodbye.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Who are you?");
				stage = 1;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Tell me about the town of Lumbridge.");
				stage = 4;
				break;
			} else if (componentId == OPTION_3) {
				stage = 3;
				sendPlayerDialogue(9827, "Goodbye.");
				break;
			}

		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "I am Phileas, the Lumbridge Sage. In times past, people came ",
					"from all around to ask me for advice. My renown seems to have ",
					"diminished somewhat in recent years, though. Can I help you with anything?");
			break;
		case 2:
			stage = 100;
			sendPlayerDialogue(9827, "I'm fine for now, thanks.");
			break;
			
		case 3:
			stage = 100;
			sendNPCDialogue(npcId, 9827, "Good adventuring, traveller.");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Lumbridge is one of the older towns in the human-controlled kingdoms. ",
					"It was founded over two hundred years ago towards the end ",
					"of the Fourth Age. It's called Lumbridge because of this ",
					"bridge built over the River Lum.");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "The town is governed by Duke Horacio",
					"who is a good friend of our monarch, King Roald of Misthalin.");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Recently, however, there have been great changes ",
					"due to the Battle of Lumbridge. ");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(9827, "What about the battle?");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "Why, the battle rages even now, on the far side of the castle. ",
					"Saradomin and Zamorak are locked in battle, neither able to gain the upper hand.");
			break;
		case 9:
			stage = 10;
			sendNPCDialogue(npcId, 9827, "Where once was forest, there is a giant crater, ",
					"in which soldiers and creatures fight to the death.");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "And the city of Lumbridge has seen the ill-effects already!",
					"The castle walls themselves have fallen!");
			break;
		case 11:
			stage = 12;
			sendPlayerDialogue(9827, "Is there anything I can do?");
			break;
		case 12:
			stage = 13;
			sendNPCDialogue(npcId, 9827, "You could join the battle if you wish. Both sides are seeking help from any",
					"individual who is willing, and not just for fighting - they",
					"are seeking something known as divine tears.");
			break;
		case 13:
			stage = 14;
			sendNPCDialogue(npcId, 9827, "You can join saradomin by going to the camp at the north of the",
					"battlefield, and Zamorak by going to the south.");
		case 14:
			stage = 100;
			sendPlayerDialogue(9827, "Sounds like I should help, thanks.");
			break;
		case 100:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}