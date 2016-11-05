package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;

public class BlurberrysBar extends Dialogue {
	
	private int npcId = 848;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to BlurBerry's Bar, and thats me! aha, I am the creator of the Famous, Fire Toad Blast."},
				IS_NPC, npcId, 9827);
		stage = -1;
	}


	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Blurberry's Bar ",
					"I'd like to try your signature drink",
					"Could I have a regular beer?",
					"Do you get much business up here?",
					"Good bye");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				sendNPCDialogue(
						npcId,
						9827,
						"Sure, one Fire Toad Blast, coming right up!");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendNPCDialogue(
						npcId,
						9827,
						"Yep, sure thing! You can try my Blurberry special for free!");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendNPCDialogue(
						npcId,
						9827,
						"Yes! The Tree Gnome Stronghold is highly populated and I get business all day long.");
				stage = -1;
			} else if (componentId == OPTION_4) {
				
				end();
			}
		}
		 else if (stage == 2) {
			end();
			player.setNextAnimation(new Animation(1327));
			player.applyHit(new Hit(player, 325, HitLook.REGULAR_DAMAGE));
			player.setNextForceTalk(new ForceTalk("GAAH!"));
			player.getPackets().sendGameMessage("Blurberry signs your BarCrawl Card.");
			player.BlurberrysBar = 1;
		} else if (stage == 3) {
			sendPlayerDialogue(9827, "Thanks!");
			player.getInventory().addItem(2028, 1);
			stage = -1;
		}
		}

	

	@Override
	public void finish() {

	}
}