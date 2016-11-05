package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;

public class JollyBoarInn extends Dialogue {
	
	private int npcId = 731;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to the Jolly Boar Inn, home of the Olde Suspiciouse"},
				IS_NPC, npcId, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Jolly Boar Inn ",
					"I'd like to try your signature drink",
					"Could I have a regular beer?",
					"Do you get much business out here?",
					"Good bye");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				sendNPCDialogue(
						npcId,
						9827,
						"Sure, one Olde Suspiciouse, coming right up!");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendNPCDialogue(
						npcId,
						9827,
						"Yep, sure thing! It's on the house!");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendNPCDialogue(
						npcId,
						9827,
						"Not lately, due to the opening of the Blue Moon inn, right in town.");
				stage = -1;
			} else if (componentId == OPTION_4) {
				
				end();
			}
		} else if (stage == 2) {
			end();
			player.setNextAnimation(new Animation(1327));
			player.applyHit(new Hit(player, 250, HitLook.REGULAR_DAMAGE));
			player.setNextForceTalk(new ForceTalk("OOF!"));
			player.getPackets().sendGameMessage("The bartender signs your BarCrawl Card.");
			player.JollyBoarInn = 1;
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