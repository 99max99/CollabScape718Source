package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class Donie extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello there, can I help you?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Are there any quests I can do here?", 
					"What's happened here lately?", "Can I buy your stick?", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "Well right now, no, I have no quests available,",
						"however you can come and speak to me later and I may have something.");
				stage = 14;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "Noszscape is striving to be unique, and they want to",
						"make the entire world interactable. We are starting with the F2P region.",
						"We are planning on adding every object, dialogue, absolutely everything",
						"so that there is nothing that doesn't work.");
				stage = 1;
				break;
			} else if(componentId == OPTION_3) {
				sendNPCDialogue(npcId, 9827, "It's not a stick! I'll have you know it's a very powerful staff!");
				stage = 3;
				break;
			} else if(componentId == OPTION_4) {
				stage = 14;
				break;
			} 
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "This will make the server more friendly to every player.",
					"And the reason we are working on the F2P region first is because it is the",
					"area that everyone is most familiar with. Now new players will easily be",
					"accustomed and know what to do when they join the server.");
			break;
		case 2:
			sendNPCDialogue(npcId, 9827, "This server will be a hybrid of a RuneScape Pre-EOC remake and",
					"a private server, so that players have the feel of RuneScape at it's best time",
					"while still having unique features a private server has.");
			stage = 14;
			break;
		case 3:
			stage = 4;
			sendPlayerDialogue(9827, "Really? Show me what it can do!");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Um..It's a bit low on power at the moment..");
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(9827, "It's a stick isn't it?");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "..Ok it's a stick.. But only while I save up for a staff. ",
					"Zaff in Varrock square sells them in his shop.");
			break;
		case 7:
			sendPlayerDialogue(9827, "Well good luck with that.");
			stage = 8;
			break;
		case 8:
			end();
			break;
			
		}
	}

	@Override
	public void finish() {
		
	}
}