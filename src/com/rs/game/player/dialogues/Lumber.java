package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.player.actions.objects.EvilTree;

public class Lumber extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (World.treeEvent) {
			if (World.killedTree) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name,
								"Thanks for helping me defeat this foul creature!", "Please take this reward for helping me!" },
						IS_NPC, npcId, 9827);
				stage = 1;
			} else {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name,
								"Please, you must help me defeat this Evil Tree!" },
						IS_NPC, npcId, 9827);
				stage = 0;
			}
		} else {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Howdy, what are you doing around here?" },
				IS_NPC, npcId, 9827);
		stage = -1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(9827, "Nothing much, just traveling around...");
			break;
		case 0:
			end();
			break;
		case 1:
			stage = 0;
			EvilTree.processReward(player);
			sendPlayerDialogue(9827, "Thank you!");
			break;
		}
	}

	@Override
	public void finish() {

	}

}
