package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.Skills;

public class BrotherJered extends Dialogue {
	
	public static int SKILLCAPE = 9760;
	public static int SKILLHOOD = 9761;
	public static int ONE = 1;

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello " + player.getUsername() + " would you like to " +
						"buy the Prayer Skillcape?"}, IS_NPC, npcId,
				9760);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Select a Option", "Yes please!",
					"No thanks!");
		}
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevelForXp(Skills.PRAYER) < 99) {
				player.getPackets()
						.sendGameMessage(
								"You need a Prayer level of 99.");
				return;
			}
			if (player.getInventory().containsItem(995, 99000)) {
				player.getInventory().removeItemMoneyPouch(995, 99000);
				player.getInventory().refresh();
				player.getInventory().addItem(SKILLCAPE, ONE);
				player.getInventory().addItem(SKILLHOOD, ONE);
		} else {
			player.sm("You need 99,000 gold to buy a skillcape.");
		}
	  }
			if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("You didn't want to buy a skillcape.");
			}
	}

	@Override
	public void finish() {

	}

}
