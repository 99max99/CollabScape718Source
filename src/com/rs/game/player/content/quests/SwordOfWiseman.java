package com.rs.game.player.content.quests;

import com.rs.game.player.Player;

/**
 * 
 * @author Plato
 *
 */

public class SwordOfWiseman {

	public static void QuestStart(Player player) {
		player.getDialogueManager().startDialogue("SowQuestStart");
	}

	public static void StartOzan(Player player) {
		player.getDialogueManager().startDialogue("Ozan");
	}
}