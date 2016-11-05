package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

public class Ghost extends Dialogue {

	int npcId;

	@Override
	public void start() {
		sendPlayerDialogue( 9827, "Hello, ghost, how are you?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendNPCDialogue(457, 9827, "Not very good, actually" );
			stage = 1;
		} else if (stage == 1) {
			sendPlayerDialogue( 9827, "Whats wrong?");
			stage = 2;
		} else if (stage == 2) {
			sendNPCDialogue(457, 9827, "Did you just understand what i said?" );
			stage = 3;
		} else if (stage == 3) {
			sendPlayerDialogue( 9827, "Yep. Now, tell me what the problem is.");
			stage = 4;
		} else if (stage == 4) {
			sendNPCDialogue(457, 9827, "Wow! This is incredible! I didn't expect anyone ","" +
					"to ever understand me!");
			stage = 5;
		} else if (stage == 5) {
			sendPlayerDialogue( 9827, "Okay, okay I can understand you.");
			stage = 7;
		} else if (stage == 7) {
			stage = 8;
			sendPlayerDialogue( 9827, "But have you any idea why you're doomed to be a ghost?");
		} else if (stage == 8) {
			sendNPCDialogue(457, 9827, "Well, to be honest , I'm not sure, ",
					"I should think it's becuse i've lost my head.");
			stage = 9;
			player.getPackets().sendGameMessage("You are making progress");
			player.RG = 4;
		} else if (stage == 9) {
			sendPlayerDialogue( 9827, "What? I can see your head perfectly fine. Well, see ",
					"through it at least.");
			stage = 10;
		} else if (stage == 10) {
			sendNPCDialogue(457, 9827, "No, no I mean from my REAL body. If you look in my coffin you'll ",
					"see my corpse is without its skull. Last thing I remeber I was being",
					"attacked by a warlock while I was mining. It was at the mine just",
					"south of the graveyard.");
			stage = 11;
		} else if (stage == 11) {
			sendPlayerDialogue( 9827, "Okay. I'll try to get your skull back for you so you",
					"can rest in peace.");
				end();
			
				} else if (stage == 21) {
					switch (componentId) {
					case 1:
						break;
					}
				} else {
					end();
				}
		}

			public void finish() {
			}
}