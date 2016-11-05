package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;

public class RisingSun2 extends Dialogue {
	
	private int npcId = 3217;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to Falador's One and only, Rising Sun Inn, please enjoy your stay! And do try our, Hand of Death Cocktail, made right in front of your eyes!"},
				IS_NPC, npcId, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Rising Sun Inn",
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
						"Haha! Alright, one Hand of Death Cocktail just for you!");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendNPCDialogue(
						npcId,
						9827,
						"Of coarse you can! Lucky for you, it's free today!");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendNPCDialogue(
						npcId,
						9827,
						"We always get knights from the castle comming in here every evening to kick back and relax.");
				stage = -1;
			} else if (componentId == OPTION_4) {
				
				end();
			}
		} else if (stage == 2) {
			end();
			player.setNextAnimation(new Animation(1327));
			player.applyHit(new Hit(player, 700, HitLook.REGULAR_DAMAGE));
			player.setNextForceTalk(new ForceTalk("IT BURNS, IT BURNS!!!!"));
			player.getPackets().sendGameMessage("Kaylee signs your BarCrawl Card.");
			player.RisingSun = 1;
		} else if (stage == 3) {
			sendPlayerDialogue(9827, "Thanks!");
			player.getInventory().addItem(1907, 1);
			stage = -1;
		}
		 
	}

	@Override
	public void finish() {

	}
}