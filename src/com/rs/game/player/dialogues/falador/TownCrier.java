/*Author: Regicidal
 * Date: 1/14/2014
 * Town Crier's Dialogue
 */
package com.rs.game.player.dialogues.falador;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class TownCrier extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Greetings traveler, what brings you here?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Who are you?",
					"Where am I?", "How do I become a player moderator?", "Nevermind..");
		} else if (stage == 0) {
			if (componentId == 2) {
				stage = 2;
				sendPlayerDialogue(9827, "May I ask who you are?");
			} else if (componentId == 3) {
				stage = 3;
				sendPlayerDialogue(9827,
						"What is this place?");
			} else if (componentId == 4) {
				stage = 5;
				sendPlayerDialogue(9827,
						"How do I become a player moderator?");
			} else if (componentId == 5) {
				end();
			}
		} else if (stage == 2) {
			sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"I'm the town crier, the only knowledgeable person about what this place actually is..." },
					IS_NPC, npcId, 9827);
			stage = -1;
		} else if (stage == 3) {
			sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"We are in a game developed by Miles called Noszscape, atleast that's what the bartender told me." },
					IS_NPC, npcId, 9827);
			stage = 4;
		} else if (stage == 4) {
			stage = -1;
			sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"It's very possible that was after a couple of rounds though...." },
					IS_NPC, npcId, 9827);
		} else if (stage == 5) {
			stage = -1;
			sendEntityDialogue(
					SEND_1_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name,
							"You can become a moderator by eximplifying the idea of a good player. Active, helpful, and respectable amongst others." },
					IS_NPC, npcId, 9827);
		} else
			end();

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}