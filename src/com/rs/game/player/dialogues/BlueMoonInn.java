package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;

public class BlueMoonInn extends Dialogue {
	
	private int npcId = 3218;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to Varrock's Blue Moon Inn, you won't find a better Inn anywhere else! Consider trying Uncle Humphrey's Gutrot, our custom made Ale!"},
				IS_NPC, npcId, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Blue Moon Inn",
					"I'd like to try your signature drink",
					"Could I have a regular beer?",
					"Do you get much business over here?",
					"Good bye");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				sendNPCDialogue(
						npcId,
						9827,
						"Alright! One Uncle Humphrey's Gutrot, coming up!");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendNPCDialogue(
						npcId,
						9827,
						"Sure thing friend! It's on the house");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendNPCDialogue(
						npcId,
						9827,
						"Heck yeah! Varrock is an expanding city and there are always new faces showing up every day@");
				stage = -1;
			} else if (componentId == OPTION_4) {
				
				end();
			}
		} else if (stage == 2) {
			end();
			player.setNextAnimation(new Animation(1327));
			player.applyHit(new Hit(player, 100, HitLook.REGULAR_DAMAGE));
			player.setNextForceTalk(new ForceTalk("*glug*"));
			player.getPackets().sendGameMessage("Tina signs your BarCrawl Card.");
			player.BlueMoonInn = 1;
		} else if (stage == 3) {
			sendPlayerDialogue(9827, "Thanks!");
			player.getInventory().addItem(1917, 1);
			stage = -1;
		}
		 
	}

	@Override
	public void finish() {

	}
}