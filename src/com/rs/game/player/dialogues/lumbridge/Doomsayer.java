package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;
/**
 * Begins the dialogue for Doomsayer.
 * @author Gircat <gircat101@gmail.com>
 * Feather - RuneScape 2012
 */
public class Doomsayer extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Dooooom!");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Do you mean the Battle of Lumbridge? Are you telling me I should go and help out by going to join in?");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue(npcId, 9827, "No, why should I be doing that? I'm talking about doooooom here, not some battlefield.");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "Well, everyone else seems to be... um... anyway, you mentioned doom. Where is this doom?");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "All around us! I can feel it in the air, hear it on the wind, smell it...also in the air!");
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Is there anythign we can do about this doom?");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "There is nothing you need to do my friend! I am the Doomsayer, although my real title could be something like the Danger Tutor.");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "Danger Tutor?");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Yes! I roam the world sensing danger.");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, " If I find a dangerous area, then I put up warning signs that will tell you what is so dangerous about that area.");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "If you see the signs often enough, then you can turn them of; by that time you likely known what the area has in store for you.");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "But what If I want to see the warnings again?");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "That's why I'm waiting here!");
			break;
		case 11:
			stage = 12;
			sendNPCDialogue(npcId, 9827, "If you want to see the warning messages again, I can turn them back on for you.");
			break;
		case 12:
			stage = 13;
			sendNPCDialogue(npcId, 9827, "Do you need to turn on any warnings right now?");
			break;
		case 13:
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes I do.", "Not right now.");
			stage = 14;
			break;
		case 14:
			if(componentId == OPTION_1) {
				stage = 100;
				player.getInterfaceManager().sendInterface(583);
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Not right now.");
				stage = 15;
				break;
			}
		case 15:
		stage = 16;
		sendNPCDialogue(npcId, 9827, "Ok, keep an eye out for the mesages though!");
		break;
		case 16:
			stage = 100;
			sendPlayerDialogue(9827, "I will.");
			break;
		case 100:
			end();
			break;
		}
}
	public void finish() {
	}
}
	