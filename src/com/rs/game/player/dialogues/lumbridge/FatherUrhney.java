package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

public class FatherUrhney extends Dialogue {

	int npcId;

	@Override
	public void start() {
		sendNPCDialogue(456, 9827, "Go away! I'm meditating!" );
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue("What would you like to say?",
					"Well, that's friendly.",
					"Okay,okay sheesh, what a grouch.",
					"Father Aereck sent me to talk to you.");
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				stage = 2;
				sendPlayerDialogue( 9827, "well, that's friendly" );
			} else if (componentId == OPTION_2) {
				stage = 25;
				sendPlayerDialogue( 9827, "Okay,okay sheesh, what a grouch." );
			} else if (componentId == OPTION_3) {
				stage = 4;
				sendPlayerDialogue( 9827, "Father Aereck sent me to talk to you.	" );
			} else
				end();
					//option 1
		} else if (stage == 2) { 
			stage = 25;
			sendNPCDialogue(456, 9827, "I said go away!" );
					//option end
			
			
			
		//option 2
		} else if (stage == 4) {
			stage = 5;
			sendNPCDialogue(456, 9827, "I suppose i'd better talk to you then.",
					"What has he got himself into this time?");
			//why are you down here ? option end!
		} else if (stage == 5) {
			stage = 6;
			sendPlayerDialogue( 9827, "A ghost is haunthing his graveyard." );
		} else if (stage == 6) {
			stage = 7;
			sendNPCDialogue(456, 9827, "OH, the silly old fool." );
		} else if (stage == 7) {
			stage = 8;
			sendNPCDialogue(456, 9827, "I leave town for five months and he's already haveing problems." );
		} else if (stage == 8) {
			stage = 9;
			sendNPCDialogue(456, 9827, "Well, I can't go back and exorcise it,",
					"I vowed not to leave this place untill",
					"I've spent a full two years praying and meditating.");
		} else if (stage == 9) {
			stage = 10;
			sendNPCDialogue(456, 9827, "I'll tell you what I can do, though - take this amulet." );	
			player.getInventory().addItem(552, 1);
			player.RG = 3;
			
		} else if (stage == 10) {
			stage = 11;
			sendNPCDialogue(456, 9827, "It is a ghostspeack amulet." );
		} else if (stage == 11) {
			stage = 12;
			sendNPCDialogue(456, 9827, "It's called that because, when you wear it, you can speak",
					"ghosts, many ghosts are doomed to remain in this world",
					"because they have some important task left uncompleted.");	
		} else if (stage == 12) {
					stage = 25;
					sendPlayerDialogue( 9827, "Thank you, I'll give it a try." );	
			
			//option endings
		} else if (stage == 12) {
			stage = 25;
			sendPlayerDialogue(9827, "Umm okay then.");
		} else if (stage == 13) {
			stage = 25;
			sendPlayerDialogue(9827, "Umm okay, thanks i guess?");
		} else if (stage == 25) {
			end();
		//options endings end
		} else
			end();
	}

	@Override
	public void finish() {

	}

}