/*Author: Regicidal
 * Date: 1/14/2014
 * Sir Amik Varse's Dialogue
 */
package com.rs.game.player.dialogues.falador;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class SirAmikVarse extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Do you have any news?");
		stage = -1;
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendNPCDialogue(npcId, 9827, "It's a glorious time for the White Knights - Saradomin has returned!");
			}
			if (stage == 0) {
			sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"He is defending us from the evil wizardy of Zamorak west of Lumbridge castle, and he's asked our order for help!" },
					IS_NPC, npcId, 9827);
			stage = 1;
		} else if (stage == 1) {
			sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"So it is the duty of all White Knights to heed the call, and take up arms against the ancient enemy!" },
					IS_NPC, npcId, 9827);
			stage = 2;
		} else if (stage == 2) {
			sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"It's very possible that was after a couple of rounds though...." },
					IS_NPC, npcId, 9827);
			stage = 3;
		} else if (stage == 3) {
			stage = 4;
			sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"By the grace of Saradomin, we shall be victorious!" },
					IS_NPC, npcId, 9827);
			//Quest start TODO
		} else if (stage == 4)
			end();

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}