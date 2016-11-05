package com.rs.game.player.dialogues.draynor;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Fisherman extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Oh! Hello there.");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue("What would you like to ask about?", "Ask about fishing.", 
					"Ask for a teleport.", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Can you tell me where I can start of fishing?");
				stage = 2;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Can I have a teleport to Catherby?");
				stage = 1;
				break;
			} else if(componentId == OPTION_3) {
				stage = 11;
				break;
			} 
		case 1:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "Sure thing!");
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2854, 3430, 0));
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Yeah, a good place to start off is Draynor Village,",
					"where you can fish shrimp, anchovies, and more.");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Then move on to Barbarian Village and",
					"fly fish salmon and trout, it's fast exp.");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "If you want money, you can fish lobsters, swordfish, and tuna,",
					"but it is slow exp so if you want the 99 I don't recommend it.");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "For the ultimate exp, I suggest you do Barbarian Training so that",
					"you can do heavy rod fishing by the Baxtorian Falls for the maximum exp gain.");
			break;
		case 6:
			sendPlayerDialogue(9827, "Thanks a lot!");
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