package com.rs.game.player.dialogues.swordofwiseman;


import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.player.dialogues.Dialogue;


public class SowQuestStart extends Dialogue {

	int npcId;
	TutorialIsland controler;
	
	public static final int KING = 670;

	@Override
	public void start() {
		if (player.SOWQUEST == 0) {
		sendEntityDialogue(
				SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(KING).name,
						""+ player.getDisplayName() +" I need your help!",
						"I have seem to lost my one true love!",
						"Please help me find my dear, dear, sword!" }, IS_NPC,
						KING, 9765);
	}
}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendPlayerDialogue(9790,
					"Why should I help you find your stupid sword?");
		} else if (stage == 0) {
			stage = 1;
			sendNPCDialogue(KING, 9765, "It's the sword that protects Noszscape",
					"and its land, if we don't find this sword we are all doomed!");
		} else if (stage == 1) {
			stage = 2;
			sendPlayerDialogue(9827,
					"Do I get a reward for finding this sword?");
		} else if (stage == 2) {
			stage = 3;
			sendNPCDialogue(KING, 9765, "Yes, you will get a reward I promise you!",
					"Just help me find this sword, last time I saw it it was",
					"In some Drawers at Catherby.");
		} else if (stage == 3) {
			stage = 4;
			sendPlayerDialogue(9845,
					"Okay, I will go look!");
			player.getInterfaceManager().closeChatBoxInterface();
			player.SOWQUEST += 1;
			end();
			}
		}

	@Override
	public void finish() {

	}
}
