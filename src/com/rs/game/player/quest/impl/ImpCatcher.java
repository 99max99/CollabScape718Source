package com.rs.game.player.quest.impl;

import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * Imp Catcher
 * 
 * @author Ridiculous <knol@outlook.com>
 */

public class ImpCatcher {
	
	public static void handleQuestStartInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 3);
		player.getPackets().sendIComponentText(275, 1, "Imp Catcher");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to </col> <col=660000>Wizard Mizgog</col> <col=330099>who is</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=330099>in the</col> <col=660000>Wizard's Tower.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 14, "<col=330099>There aren't any requirements for this quest.</col>");
		for (int i = 15; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	public static void handleProgressQuest(final Player player) {
		player.startedImpCatcher = true;
		player.inProgressImpCatcher = true;
		player.getPackets().sendConfig(160, 1);
		player.getInterfaceManager().sendInterfaces();
		player.getPackets().sendUnlockIComponentOptionSlots(190, 15, 0, 201, 0, 1, 2, 3);
	}
	
	public static void handleProgressQuestInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 10);
		player.getPackets().sendIComponentText(275, 1, "Imp Catcher");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<str><col=330099>I can start this quest by speaking to </col> <col=660000>Wizard Mizgog</col> <col=330099>who is</col>");
		player.getPackets().sendIComponentText(275, 12, "<str><col=330099>in the</col> <col=660000>Wizard's Tower.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 14, "<col=330099>I need to collect some items by killing</col> <col=660000>Imps</col>");
		player.getPackets().sendIComponentText(275, 15, (player.getInventory().containsItem(1474, 1) ? 
				"<str><col=660000>1 Black Bead</col>"
				: "<col=660000>1 Black Bead</col>"));
		player.getPackets().sendIComponentText(275, 16, (player.getInventory().containsItem(1470, 1) ? 
				"<str><col=660000>1 Red Bead</col>"
				: "<col=660000>1 Red Bead</col>"));
		player.getPackets().sendIComponentText(275, 17, (player.getInventory().containsItem(1476, 1) ? 
				"<str><col=660000>1 White Bead</col>"
				: "<col=660000>1 White Bead</col>"));
		player.getPackets().sendIComponentText(275, 18, (player.getInventory().containsItem(1472, 1) ? 
				"<str><col=660000>1 Yellow Bead</col>"
				: "<col=660000>1 Yellow Bead</col>"));	
		for (int i = 19; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	public static void handleQuestComplete(final Player player) {
		player.inProgressImpCatcher = false;
		player.completedImpCatcher = true;
		player.questPoints += 1;
		player.getSkills().addXp(Skills.MAGIC, 875);
		if (player.getInventory().getFreeSlots() < 1) {
			player.getBank().addItem(1478, 1, true);
			player.getPackets().sendGameMessage("You do not have enough inventory space. Your reward has been sent to your bank.");
		} else {
			player.getInventory().addItem(1478, 1);
		}
		player.getPackets().sendConfig(160, 2);
		player.getPackets().sendConfig(101, player.questPoints); // Quest Points
		player.getInterfaceManager().sendInterfaces();
		player.getPackets().sendUnlockIComponentOptionSlots(190, 15, 0, 201, 0, 1, 2, 3);
	}
	
	public static void handleQuestCompletionTabInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 12);
		player.getPackets().sendIComponentText(275, 1, "Imp Catcher");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<str>I've spoken to Wizard Mizgog.</col>");
		player.getPackets().sendIComponentText(275, 12, "");
		player.getPackets().sendIComponentText(275, 13, "<str>I have collected all the beads.</col>");
		player.getPackets().sendIComponentText(275, 14, "");
		player.getPackets().sendIComponentText(275, 15, "<str>Wizard Mizgog thanked me for finding his beads and gave</col>");
		player.getPackets().sendIComponentText(275, 16, "<str>me an Amulet of Accuracy</col>");
		player.getPackets().sendIComponentText(275, 17, "");
		player.getPackets().sendIComponentText(275, 18, "<col=660000>QUEST COMPLETE</col>");
		for (int i = 19; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}	
	
	public static void handleQuestCompleteInterface(final Player player) {
		player.getInterfaceManager().sendInterface(277);
		player.getPackets().sendIComponentText(277, 4, "You have completed Imp Catcher.");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "1 Quest Point");
		player.getPackets().sendIComponentText(277, 11, "875 Magic XP");
		player.getPackets().sendIComponentText(277, 12, "Amulet of accuracy");
		player.getPackets().sendIComponentText(277, 13, "Two spins on the Squeal of Fortune");
		player.getPackets().sendIComponentText(277, 14, "");
		player.getPackets().sendIComponentText(277, 15, "");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 1478, 1);
		player.getPackets().sendGameMessage("The wizard hands you an amulet.");
		player.getPackets().sendGameMessage("Congratulations! You have completed a quest!");
		}
	}