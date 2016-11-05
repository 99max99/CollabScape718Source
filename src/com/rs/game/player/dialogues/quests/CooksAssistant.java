package com.rs.game.player.dialogues.quests;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * Handles all of the methods needed for the Cook's Assistant Quest.
 * @author Gircat <gircat101@gmail.com>
 * @author _Jordan / Apollo <citellumrsps@gmail.com>
 * @author Feather RuneScape 2012 Remake
 */
public class CooksAssistant {
	
	/**
	 * The interface before the player has started the quest.
	 * 
	 * @param player
	 */
	public static void handleQuestStartInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 3);
		player.getPackets().sendIComponentText(275, 1, "Cook's Assistant");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<col=330099>I can start this quest by speaking to the</col> <col=660000>cook</col> <col=330099>in the</col>");
		player.getPackets().sendIComponentText(275, 12, "<col=660000>kitchen</col> <col=330099>on the ground floor of</col> <col=660000>Lumbridge Castle.</col>");
		for (int i = 13; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	/**
	 * Sends the configuration to the quest tab and sets Cook's Assistant into progress.
	 * 
	 * @param player
	 */
	public static void handleProgressQuest(final Player player) {
		player.startedCooksAssistant = true;
		player.inProgressCooksAssistant = true;
		player.getPackets().sendConfig(29, 1);
		player.getInterfaceManager().sendInterfaces();
		player.getPackets().sendUnlockIComponentOptionSlots(190, 15, 0, 201, 0, 1, 2, 3);
	}
	
	/**
	 * The interface during the quest when the player gathers the ingredients.
	 * 
	 * @param player
	 */
	public static void handleProgressQuestInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 10);
		player.getPackets().sendIComponentText(275, 1, "Cook's Assistant");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<str><col=330099>I can start this quest by speaking to the</col> <col=660000>cook</col> <col=330099>in the</col>");
		player.getPackets().sendIComponentText(275, 12, "<str><col=660000>kitchen</col> <col=330099>on the ground floor of</col> <col=660000>Lumbridge Castle.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 14, "<col=330099>It's the</col> <col=660000>Duke of Lumbridge's</col> <col=330099>birthday and I have to help</col>");
		player.getPackets().sendIComponentText(275, 15, "<col=330099>his</col> <col=660000>Cook</col> <col=330099>make him a</col> <col=660000>birthday cake.</col> <col=330099>To do this I need to</col>");
		player.getPackets().sendIComponentText(275, 16, "<col=330099>bring him the following ingredients:</col>");
		player.getPackets().sendIComponentText(275, 17, (player.getInventory().containsItem(1927, 1) ? 
				"<str><col=330099>I have found a</col> <col=660000>bucket of milk</col> <col=330099>to give the cook.</col>"
				: "<col=330099>I need a</col> <col=660000>bucket of milk</col> <col=330099>to give the cook.</col>"));
		player.getPackets().sendIComponentText(275, 18, (player.getInventory().containsItem(1933, 1) ? 
				"<str><col=330099>I have found a</col> <col=660000>pot of flour</col> <col=330099>to give the cook.</col>"
				: "<col=330099>I need a</col> <col=660000>pot of flour</col> <col=330099>to give the cook.</col>"));
		player.getPackets().sendIComponentText(275, 19, (player.getInventory().containsItem(1944, 1) ? 
				"<str><col=330099>I have found an</col> <col=660000>egg</col> <col=330099>to give the cook.</col>"
				: "<col=330099>I need to find an</col> <col=660000>egg</col> <col=330099>to give the cook.</col>"));
		for (int i = 20; i < 300; i++) {
			player.getPackets().sendIComponentText(275, i, "");
		}
	}
	
	/**
	 * Handles the reward the player gets when completing the quest.
	 * 
	 * @param player
	 */
	public static void handleQuestComplete(final Player player) {
		player.inProgressCooksAssistant = false;
		player.completedCooksAssistantQuest = true;
		player.questPoints += 1;
		player.getSkills().addXp(Skills.COOKING, 300);
		if (player.getInventory().getFreeSlots() < 2) {
			player.getBank().addItem(328, 20, true);
			player.getPackets().sendGameMessage("You do not have enough inventory space. Your reward has been sent to your bank.");
		} else {
			player.getInventory().addItemMoneyPouch(995, 500);
			player.getInventory().addItem(328, 20);
		}
		player.getPackets().sendConfig(29, 2);
		player.getPackets().sendConfig(101, player.questPoints); // Quest Points
		player.getInterfaceManager().sendInterfaces();
		player.getPackets().sendUnlockIComponentOptionSlots(190, 15, 0, 201, 0, 1, 2, 3);
	}
	
	/**
	 * The interface shown after the player has completed the quest.
	 * 
	 * @param player
	 */
	public static void handleQuestCompletionTabInterface(final Player player) {
		player.getInterfaceManager().sendInterface(275);
		player.getPackets().sendRunScript(1207, 12);
		player.getPackets().sendIComponentText(275, 1, "Cook's Assistant");
		player.getPackets().sendIComponentText(275, 10, "");
		player.getPackets().sendIComponentText(275, 11, "<str><col=330099>I can start this quest by speaking to the</col> <col=660000>cook</col> <col=330099>in the</col>");
		player.getPackets().sendIComponentText(275, 12, "<str><col=660000>kitchen</col> <col=330099>on the ground floor of</col> <col=660000>Lumbridge Castle.</col>");
		player.getPackets().sendIComponentText(275, 13, "");
		player.getPackets().sendIComponentText(275, 14, "<str><col=330099>It's the</col> <col=660000>Duke of Lumbridge's</col> <col=330099>birthday and I have to help</col>");
		player.getPackets().sendIComponentText(275, 15, "<str><col=330099>his</col> <col=660000>Cook</col> <col=330099>make him a</col> <col=660000>birthday cake.</col> <col=330099>To do this I need to</col>");
		player.getPackets().sendIComponentText(275, 16, "<str><col=330099>bring him the following ingredients:</col>");
		player.getPackets().sendIComponentText(275, 17, "<str><col=330099>I have found a</col> <col=660000>bucket of milk</col><col=330099> to give the cook.</col>");
		player.getPackets().sendIComponentText(275, 18, "<str><col=330099>I have found a</col> <col=660000>pot of flour</col> <col=330099>to give the cook.</col>");
		player.getPackets().sendIComponentText(275, 19, "<str><col=330099>I have found an</col> <col=660000>egg</col> <col=330099>to give the cook.</col>");
		player.getPackets().sendIComponentText(275, 20, "");
		player.getPackets().sendIComponentText(275, 21, "<col=660000>QUEST COMPLETE</col>");
	}
	
	/**
	 * The interface as the player gets the reward for completion of the quest.
	 * 
	 * @param player
	 */
	public static void handleQuestCompleteInterface(final Player player) {
		player.getInterfaceManager().sendInterface(277);
		player.getPackets().sendIComponentText(277, 4, "You have completed Cook's Assistant.");
		player.getPackets().sendIComponentText(277, 7, "" + player.questPoints);
		player.getPackets().sendIComponentText(277, 9, "You are awarded:");
		player.getPackets().sendIComponentText(277, 10, "1 Quest Point");
		player.getPackets().sendIComponentText(277, 11, "300 Cooking XP");
		player.getPackets().sendIComponentText(277, 12, "500 Coins");
		player.getPackets().sendIComponentText(277, 13, "20 Sardines");
		player.getPackets().sendIComponentText(277, 14, "Access to the cook's range");
		player.getPackets().sendIComponentText(277, 15, "Two spins on the Squeal of Fortune");
		player.getPackets().sendIComponentText(277, 16, "");
		player.getPackets().sendIComponentText(277, 17, "");
		player.getPackets().sendItemOnIComponent(277, 5, 1891, 1);
		player.getPackets().sendGameMessage("Congratulations! You have completed the Cooks Assistant quest!");
	}
	
	/**
	 * Handles the cow milking session of the quest.
	 * 
	 * @param player
	 */
	public static void handleCowMilking(final Player player) {
		if (player.getInventory().containsItem(1925, 1)
				&& player.inProgressCooksAssistant == true) {
			player.lock(8);
			player.setNextAnimation(new Animation(2305));
			player.getInventory().deleteItem(1925, 1);
			player.getInventory().addItem(1927, 1);
		} else if (!player.getInventory().containsItem(3727, 1)
				&& player.inProgressCooksAssistant == true) {
			player.getPackets().sendGameMessage("You need an empty bucket to milk this cow.");
		} else if (player.getInventory().containsItem(3727, 1)
				&& player.inProgressCooksAssistant == false) {
			player.getPackets().sendGameMessage("You have no reason to milk the prized cow of the farm.");
		}
	}

}
