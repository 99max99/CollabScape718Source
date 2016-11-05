package com.rs.game.player.dialogues.worldwide;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;

/**
 * Handles QuizMaster Game.
 * 
 * @author Demon Dylan
 *
 */

public class QuizMaster extends Dialogue {

	private int npcId;
	private int wrong = 0;

	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello, and Welcome to the game show!" }, IS_NPC, npcId, 9827);

		}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
		sendEntityDialogue(SEND_2_TEXT_CHAT,
			new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
				"The rules are simple answer all 5 questions correct",
					"and you win a prize, answer 3 wrong you lose" }, IS_NPC, npcId, 9827);
		stage = 1;
		} else if (stage == 1) {
			sendPlayerDialogue(SEND_2_OPTIONS, "What Level do you need to weild Dragon", "Definantly 40!",
					"Defiantly 60!");
		stage = 2;
		} else if (stage == 2) {
		if (componentId == 1) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Incorrect!" }, IS_NPC, npcId, 9827);
			wrong++;
			stage = 3;
			} else if (componentId == 2) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Correct!" }, IS_NPC, npcId, 9827);
			stage = 3;
			}
		} else if (stage == 3) {
			sendPlayerDialogue(SEND_2_OPTIONS, "Which of the following is flame resistant", "Anti-Dragonfire Shield!",
					"Rune Beserker Shield!");
		stage = 4;
		} else if (stage == 4) {
			if (componentId == 1) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Correct!" }, IS_NPC, npcId, 9827);
			stage = 5;
			} else if (componentId == 2) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Incorrect!" }, IS_NPC, npcId, 9827);
			wrong++;
			stage = 5;
			}
		} else if (stage == 5) {
			sendPlayerDialogue(SEND_2_OPTIONS, "Which of the following restores more health", "Salmon!",
					"Shrimp!");
		stage = 6;
		} else if (stage == 6) {
			if (componentId == 1) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Correct!" }, IS_NPC, npcId, 9827);
			stage = 7;
			} else if (componentId == 2) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Incorrect!" }, IS_NPC, npcId, 9827);
			wrong++;
			stage = 7;
			}
		} else if (stage == 7) {
			if (wrong == 3) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"3 Wrong You have Lost!" }, IS_NPC, npcId, 9827);
				stage = 13;
		} else {
			sendPlayerDialogue(SEND_2_OPTIONS, "What attack level is required to weild a godsword", "85!",
					"75!");
		stage = 8;
			}
		} else if (stage == 8) {
			if (componentId == 1) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Correct!" }, IS_NPC, npcId, 9827);
			stage = 9;
			} else if (componentId == 2) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Incorrect!" }, IS_NPC, npcId, 9827);
			wrong++;
			stage = 9;
			}
		} else if (stage == 9) {
			if (wrong == 3) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"3 Wrong You have Lost!" }, IS_NPC, npcId, 9827);
				stage = 13;
		} else {
			sendPlayerDialogue(SEND_2_OPTIONS, "Who is the creator of Noszscape", "Jason!",
					"Nosz!");
		stage = 10;
			}
		} else if (stage == 10) {
			if (componentId == 1) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Incorrect!" }, IS_NPC, npcId, 9827);
			wrong++;
			stage = 11;
			} else if (componentId == 2) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Correct!" }, IS_NPC, npcId, 9827);
			stage = 11;
			}
		} else if (stage == 11) {
			if (wrong == 3) {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"3 Wrong You have Lost!" }, IS_NPC, npcId, 9827);
				stage = 13;
		} else {
		sendEntityDialogue(SEND_2_TEXT_CHAT,
			new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
				"You win please accept this reward and come back soon!",
					"Thanks for playing" }, IS_NPC, npcId, 9827);
		stage = 12;
		}
		} else if (stage == 12) {
		winner();
		} else if (stage == 13) {
		loser();
	}
}

	public void loser() {
	player.getControlerManager().getControler().removeControler();
	teleportPlayer(player.tX, player.tY, player.tH);
	wrong = 0;
	}
	public void winner() {
	player.getControlerManager().getControler().removeControler();
	teleportPlayer(player.tX, player.tY, player.tH);
	wrong = 0;
	player.getInventory().addItem(6199, 1);
	}

  	private void teleportPlayer(int x, int y, int z) {

		Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(x, y, z),
 new int[0]);

		player.stopAll();
    	}


	@Override
	public void finish() {

	}

}