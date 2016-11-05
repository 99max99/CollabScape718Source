package com.rs.game.player.dialogues.worldwide;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;

public class FreakyForester extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Welcome to my forester event," }, IS_NPC, npcId, 9827);
	}


	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (player.gotreward == 1) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You may exit through the portal" },
					IS_NPC, npcId, 9827);
			stage = 3;
			} else {
			sendPlayerDialogue(SEND_2_OPTIONS, "What would you like to say?", "I found pheasant meat.",
					"What am i doing here?");
			stage = 1;
			}
		} else if (stage == 1) {
			if (componentId == 1) {
			if (player.getInventory().containsItem(6178, 1)) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You brought me my meat.", "Please accept this reward.." },
					IS_NPC, npcId, 9827);
			player.getInventory().deleteItem(6178, 1);
			player.getInventory().addItem(6199, 1);
			player.gotreward = 1;
			stage = 2;
			} else {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You do not have a pheasant meat.", "kill a pheasant to get some." },
					IS_NPC, npcId, 9827);
					stage = 3;
				}
			} else if (componentId == 2) {
			sendEntityDialogue(SEND_2_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "Hi i need some raw pheasant meat", "Bring it to me and I will release you." },
					IS_NPC, npcId, 9827);
			stage = 3;
			}
		} else if (stage == 2) {
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name, "You may exit through the portal" },
					IS_NPC, npcId, 9827);
			stage = 3;
		} else if (stage == 3) {
			end();

	}
}

	private void teleportPlayer(int x, int y, int z) {
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();
	}

	@Override
	public void finish() {

	}

}