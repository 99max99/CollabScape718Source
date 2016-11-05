package com.rs.game.player.dialogues.barcrawl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.dialogues.Dialogue;

public class DeadMansChest extends Dialogue {
	
	private int npcId = 735;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Arrrrrgh! Welcome to the infamous Dead Man's Chest, home of the Supergrog you silly Land-Lover!"},
				IS_NPC, npcId, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Dead Man's Chest ",
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
						"Arrrgh! One Supergrog coming right up.");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendNPCDialogue(
						npcId,
						9827,
						"Arrrgh! One keg of beer coming up!");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendNPCDialogue(
						npcId,
						9827,
						"There be lots of pirates around these parts so i'm always open for business, ar ar ar!");
				stage = -1;
			} else if (componentId == OPTION_4) {
				
				end();
			}
		} else if (stage == 2) {
			end();
			player.setNextAnimation(new Animation(1330));
			player.applyHit(new Hit(player, 750, HitLook.REGULAR_DAMAGE));
			player.setNextForceTalk(new ForceTalk("HOLY MOTHER OF SARADOMIN!"));
			player.getPackets().sendGameMessage("The pirate bartender signs your BarCrawl Card.");
			player.DeadMansChest = 1;
		} else if (stage == 3) {
			sendPlayerDialogue(9827, "Thanks!");
			player.getInventory().addItem(3801, 1);
			stage = -1;
		}
		 
	}

	@Override
	public void finish() {

	}
}