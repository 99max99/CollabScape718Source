package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;

/**
 * @author Viper Quest: Ernest the Chicken
 */
public class Veronica extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello player.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1: 
			if (player.EC == 0) {
				sendNPCDialogue(npcId, 9827, "Can you help me? I'm in a terrible spot of", "trouble.");
				stage = 0;
			} else if (player.EC == 1) {
				sendNPCDialogue(npcId, 9827, "Did you find him?");
				stage = 6;
			} else if (player.EC == 2) {
				sendNPCDialogue(npcId, 9827, "Did you find him?");
				stage = 6;
			} else {
				sendNPCDialogue(npcId, 9827, "Thanks for helping me find my fiancè!");
				stage = 9;
			}
			break;
		case 0:
			sendPlayerDialogue(9827, "Aah, sounds like a quest! I'll help!");
			stage = 1;
			break;
		case 1:
			sendNPCDialogue(npcId, 9827, "Yes yes, I suppose it is a quest. My fiance Ernest and", "I came upon this house");
			stage = 2;
			break;
		case 2:
			sendNPCDialogue(npcId, 9827, "Seeing as we were a little lost Ernest decided to go in", "and ask for directions.");
			stage = 3;
			break;
		case 3:
			sendNPCDialogue(npcId, 9827, "That was an hour ago. That house looks spooky, can", "you go and see if you can find him for me?");
			stage = 4;
			break;
		case 4:
			sendPlayerDialogue(9827, "Ok, I'll see what I can do.");
			stage = 5;
			break;
		case 5:
			sendNPCDialogue(npcId, 9827, "Thank you, thank you. I'm very grateful.");
			player.EC = 1;
			player.reset();
			stage = 9;
			break;
		case 6:
			sendPlayerDialogue(9827, "No, not yet.");
			stage = 9;
			break;
		case 8:
			sendPlayerDialogue(9827, "No problem.");
			stage = 9;
			break;
		case 9:
			end();
			break;
		}
	}

	@Override
	public void finish() {
	}

}
