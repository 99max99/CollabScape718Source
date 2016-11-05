package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.Skills;
import com.rs.utils.ShopsHandler;

public class DragonInn extends Dialogue {
	
	private int npcId = 739;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello there! Welcome to the Dragon Inn! Please consider trying our home drink, the Fire Brandy! It packs quite a punch."},
				IS_NPC, npcId, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Dragon Inn ",
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
						"Alrighty! One Fire Brandy coming up!");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendNPCDialogue(
						npcId,
						9827,
						"Of coarse! It's on the house.");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendNPCDialogue(
						npcId,
						9827,
						"Not really..Everybody in town suddently disapeared and the only business I get is from people like you passing through town.");
				stage = -1;
			} else if (componentId == OPTION_4) {
				
				end();
			}
		} else if (stage == 2) {
			end();
			player.setNextAnimation(new Animation(1327));
			player.applyHit(new Hit(player, 120, HitLook.REGULAR_DAMAGE));
			player.setNextForceTalk(new ForceTalk("Ow!"));
			player.getPackets().sendGameMessage("The Bartender happily signs your BarCrawl Card!");
			player.DragonInn = 1;
		} else if (stage == 3) {
			sendPlayerDialogue(9827, "Thanks!");
			player.getInventory().addItem(3803, 1);
			stage = -1;
		}
		 
	}

	@Override
	public void finish() {

	}
}