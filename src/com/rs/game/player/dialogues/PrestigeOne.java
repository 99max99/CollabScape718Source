package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.ForceTalk;
import com.rs.utils.ShopsHandler;

public class PrestigeOne extends Dialogue {

	private int npcId = 2262;

	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						"Prestige Master",
						"Hello "
								+ player.getDisplayName()
								+ ". Would you like to learn about the Prestige system?" },
				IS_NPC, 2262, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Prestige System ", "Sure, tell me about it.",
					"I'd like to the see the Master Prestige Shop.",
					"I'd like to check my Prestige tokens please.",
					"I would like to recieve my prestige title please.");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				sendNPCDialogue(
						npcId,
						9827,
						"The prestige system allows you to reset all of your skills. You will be rewarded with one prestige token for every time you prestige. You need 2181 total level to prestige. For every prestige you will gain one new user title.");
				stage = 2;
			} else if (componentId == OPTION_2) {
				if (player.prestigeTokens >= 1) {
					ShopsHandler.openShop(player, 69);
				} else {
					player.getPackets().sendGameMessage(
							"You have prestiged to use this shop.");
				}
				end();
			} else if (componentId == OPTION_3) {
				player.getPackets().sendGameMessage(
						"I Currently have: " + player.prestigeTokens
								+ " prestige tokens.");
				player.setNextForceTalk(new ForceTalk("I Currently have: "
						+ player.prestigeTokens + " prestige tokens."));
				end();
			} else if (componentId == OPTION_4) {
				if (player.prestigeTokens == 1) {
					player.getAppearence().setTitle(676);
				} else if (player.prestigeTokens == 2) {
					player.getAppearence().setTitle(6575);
				} else if (player.prestigeTokens == 3) {
					player.getAppearence().setTitle(1657);
				} else if (player.prestigeTokens == 4) {
					player.getAppearence().setTitle(345);
				} else if (player.prestigeTokens == 5) {
					player.getAppearence().setTitle(1231242);
				} else {
					player.getPackets().sendGameMessage(
							"You need to have prestiged to use this.");
				}
				end();
			}
		} else if (stage == 2) {
			sendPlayerDialogue(9827, "Wow! Sounds amazing!");
			stage = 3;
		} else if (stage == 3) {
			sendNPCDialogue(
					npcId,
					9827,
					"That's because it is! When you prestige for the first time, all of your stats will be reset. Although, once you gain 99 in every level again, only your combat stats will be reset the next time you prestige.");
			stage = 4;
		} else if (stage == 4) {
			sendOptionsDialogue("Would you like to prestige?", "Yes!",
					"No thanks.");
			stage = 5;
		} else if (stage == 5) {
			if (componentId == OPTION_1)
				player.prestige();
			if (!player.isPrestige1()) {
				player.getPackets().sendGameMessage(
						"You need atleast 99 in every skill to prestige.");
			} else if (player.isPrestige1()) {
				if (player.prestigeTokens == 0) {
					player.setCompletedPrestigeOne();
				} else {
					player.setCompletedPrestige2();
				}
			} else if (componentId == OPTION_2) {
				end();
			}
			end();
		}
	}

	@Override
	public void finish() {

	}
}