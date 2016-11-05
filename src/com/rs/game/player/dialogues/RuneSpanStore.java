package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.content.SpanStore;

public class RuneSpanStore extends Dialogue {
	
	public static int INTERFACE_ID = 1273;
	public static int TAB_INTERFACE_ID = 0;

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello " + player.getUsername() + " would you like to " +
						"see the RuneSpan Store?"}, IS_NPC, npcId,
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
			SpanStore.sendShop(player);
			player.getInterfaceManager().closeChatBoxInterface();
			}
			if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			}
	}

	@Override
	public void finish() {

	}

}
