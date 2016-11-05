package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;

public class ShantayGuard extends Dialogue {
	
	
	private int npcId = 837;
	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name, "Go talk to Shantay. I'm on duty and I don't have time to"
								+ " talk to the likes of you!" },
						IS_NPC, npcId, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			player.getDialogueManager().startDialogue("SimpleMessage","The guard seems quite bad tempered, probably from having to wear heavy"
					+ " armour in this intense heat.");
			}
		}
		 
	

	@Override
	public void finish() {

	}
}
