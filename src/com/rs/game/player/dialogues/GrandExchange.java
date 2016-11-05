package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.LendingManager;
import com.rs.game.player.Skills;
import com.rs.game.player.content.ItemSets;

public class GrandExchange extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello " + player.getUsername() + " and welcome to the Grand Exchange!", 
						"One of the most advanced Player-to-Player based trading systems any server has."}, IS_NPC, npcId,
				9760);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"I would like to remind you, that this is a player to player based grand exchange.", 
							"Offers are not bought nor sold instantly, you must wait for a player to meet your",
							"offer with atleast the correct amount of higher."}, IS_NPC, npcId,
					9760);
		}
		if (stage == -1) {
			sendOptionsDialogue("Select a Option", "Open Grand Exchange",
					"Check History", "Open Collection Box", "Exchange Item Sets", "Collect Lent Item");
		}
		if (componentId == OPTION_1) {
			player.getGeManager().openGrandExchange();
			//player.sm("This has been temporarily disabled.");
			end();
	  }
			if (componentId == OPTION_2) {
			player.getGeManager().openHistory();
			end();
			}
			if (componentId == OPTION_3) {
			player.getGeManager().openCollectionBox();
			end();
			}
			if (componentId == OPTION_4) {
			ItemSets.openSets(player);
			end();
			}
			if (componentId == OPTION_5) {
			LendingManager.process();
			end();
			}
	}

	@Override
	public void finish() {

	}

}
