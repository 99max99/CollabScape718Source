package com.rs.game.player.dialogues.barcrawl;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class RustyAnchorBar extends Dialogue {
	
	private int npcId = 734;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to the Rusty Anchor, the greatest pub around! Please consider trying out signature drink, Black Skull Ale!"},
				IS_NPC, npcId, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Rusty Anchor",
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
						"One Black Skull Ale, I hope you enjoy!");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendNPCDialogue(
						npcId,
						9827,
						"Yes! And Ahab said he would pick up everyone's tab today so have as much as you like!");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendNPCDialogue(
						npcId,
						9827,
						"Every Sailor comes in here to enjoy a nice beer or two.");
				stage = -1;
			} else if (componentId == OPTION_4) {
				
				end();
			}
		} else if (stage == 2) {
			end();
			player.setNextAnimation(new Animation(1330));
			player.applyHit(new Hit(player, 450, HitLook.REGULAR_DAMAGE));
			player.setNextForceTalk(new ForceTalk("Ohhhhhh Myyaaayy Goooaawwwd"));
			player.getPackets().sendGameMessage("The Bartender signs your BarCrawl Card.");
			player.RustyAnchor = 1;
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