package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.Skills;
import com.rs.utils.ShopsHandler;

public class EstateAgent extends Dialogue {
	
	public static int SKILLCAPE = 9748;
	public static int SKILLHOOD = 9749;
	public static int ONE = 1;

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello " + player.getUsername() + "how can I help you?"}, IS_NPC, npcId,
				9760);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Select a Option", "May I purchase a house?",
					"Can I see your construction shop?");
			stage = 1;
		} else if (stage == 1) {
		if (componentId == OPTION_1) {
			if (player.hasHouse) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name,
								"You already own a house!"}, IS_NPC, npcId,
						9760);
				player.getInterfaceManager().closeChatBoxInterface();
			} else {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name,
								"That will be 500,000gp."}, IS_NPC, npcId,
						9760);
				stage = 2; 
			}
	  } else if (componentId == OPTION_2) {
		  ShopsHandler.openShop(player, 83);
			}
		} else if (stage == 2) {
			sendOptionsDialogue("Select a Option", "That's a deal!",
					"No way!");
			stage = 3;
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(995, 500000)) {
					sendEntityDialogue(
							SEND_2_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name,
									"Congratulations, you now own a house!"}, IS_NPC, npcId,
							9760);
					player.getInventory().removeItemMoneyPouch(995, 500000);
					player.hasHouse = true;
				} else {
					sendEntityDialogue(
							SEND_2_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name,
									"I'm sorry but you do not have enough money."}, IS_NPC, npcId,
							9760);
				}
		  } else if (componentId == OPTION_2) {
			  sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name,
								"You are truely missing out."}, IS_NPC, npcId,
						9760);
				}
		}
	}

	@Override
	public void finish() {

	}

}
