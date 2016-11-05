package com.rs.game.player.dialogues;

import com.rs.game.World;


public class KillStreak extends Dialogue {

	int npcId = 6537;

	@Override
	public void start() {
		sendNPCDialogue(npcId, 9827, "Hello "+player.getDisplayName()+", Do you need some information about the Killstreak System?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"I'd like to claim my reward.", "I'll come back later.");
			break;
		case 0:
			if (componentId == OPTION_2) {
				stage = 1;
				sendPlayerDialogue(9827, "I'll come back later.");
			} else {
				stage = 2;
				sendPlayerDialogue(9827, "I'd like to claim my reward.");
			}
			break;
		case 1:
			stage = -2;
			sendNPCDialogue(npcId, 9827,
					"Don't forget, you can only claim a reward if you are on a 5-10-15-20 Killstreak!");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(
					npcId,
					9827,
					"You must have a 5-10-15 or 20 Killstreak in order to claim your reward. You can check your numbers on the Task Tab.");
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"I got one of those numbers!",
					"I don't got any of those numbers.");
			break;
		case 4:
			if (componentId == OPTION_1) {
				if (player.Killstreak == 5) {
            					player.getInventory().addItemMoneyPouch(995, 10000);
						player.Killstreak = 0;
						World.sendWorldMessage("<img=7><col=ff0000>News: "+player.getDisplayName()+" has claimed the 5th Killstreak Reward.", false);
					}
            				else if (player.Killstreak == 10) {
            					player.getInventory().addItemMoneyPouch(995, 20000);
						player.Killstreak = 0;
						World.sendWorldMessage("<img=7><col=ff0000>News: "+player.getDisplayName()+" has claimed the 10th Killstreak Reward.", false);
					}
            				else if (player.Killstreak == 15) {
            					player.getInventory().addItemMoneyPouch(995, 30000);
						player.Killstreak = 0;
						World.sendWorldMessage("<img=7><col=ff0000>News: "+player.getDisplayName()+" has claimed the 15th Killstreak Reward.", false);
					}
            				else if (player.Killstreak == 20) {
            					player.getInventory().addItemMoneyPouch(995, 40000);
						player.Killstreak = 0;
						World.sendWorldMessage("<img=7><col=ff0000>News: "+player.getDisplayName()+" has claimed the 20th Killstreak Reward.", false);
					}
				end();
			} else {
				end();
			}
			break;
		}
	}

	@Override
	public void finish() {

	}

}