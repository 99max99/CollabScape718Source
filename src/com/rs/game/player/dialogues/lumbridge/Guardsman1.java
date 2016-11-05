package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Guardsman1 extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Good day.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "Well met, adventurer.");
			break;
		case 0:
			stage = 1;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Tell me about the Lumbridge Guardsmen.", 
					"What is there to do around here?",
					"Tell me about the town of Lumbridge.",
					"What exactly are you guarding?",
					"Goodbye.");
			break;

		case 1:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "I won't pretent that we're an elite fighting force, but we ",
						"know how to work with the castle's defences. That means ",
						"just a few of us can hold a fairly strong defence, if we ",
						"ever need to.");
				stage = 2;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "If you're interested in learning to make leather armour, ",
						"there's a cow field to get hides. Then, you can take ",
						"the hides to a tanner.");
				stage = 2;
				break;
			} else if (componentId == OPTION_3) {
				sendNPCDialogue(npcId, 9827, "If I'm honest, I don't much care for it here. I understand ",
						"why so many do enjoy the lifestyle, but I long for the city ",
						"life. If I do well in my job here, perhaps I could move to ",
						"Varrock, or Falador, and join the guards there.");
				stage = 2;
				break;
			} else if (componentId == OPTION_4) {
				sendNPCDialogue(npcId, 9827, "Peace, happiness, tranquility, that sort of thing. So, let ",
						"me know if you see anything upsetting. Dragons in cellars, ",
						"goblins under beds, that sort of thing.");
				stage = 2;
				break;
			} else if (componentId == OPTION_5) {
				sendNPCDialogue(npcId, 9827, "Bye!");
				stage = 3;
				break;
			}
		case 2:
			sendPlayerDialogue(9827, "Thanks for the information!");
			stage = 3;
			break;
		case 3:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}