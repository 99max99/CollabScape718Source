package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;
/**
 * Begins the dialogue for Border Guards.
 * @author Gircat <gircat101@gmail.com>
 * Feather - RuneScape 2012
 */
public class BorderGuard extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendPlayerDialogue(9827, "Can I come through this gate?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendNPCDialogue(npcId, 9827, "You must pay a fee of 10 gold coins to pass.");
			break;
		case 0:
			stage = 1;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Sure, I'll pay.",
					"I'll walk around.");
			break;
		case 1:
			if (componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Sure, I'll pay.");
				stage = 2;
				break;
			} else if (componentId == OPTION_2) {
				sendPlayerDialogue(9827, "I'll walk around.");
				stage = 4;
				break;
			}
		case 2:
			stage = 5;
			sendNPCDialogue(npcId, 9827, "Sounds great! Let me see if you have 10 gold coins.");
			stage = 6;
			break;
		case 6:
			if (player.getInventory().containsItem(995, 10) == false) {
				sendNPCDialogue(npcId, 9827,
						"You don't have enough gold coins to pass!");
				player.getPackets().sendGameMessage("You do not have enough coins to pass through the gate.");
				stage = 3;
				break;
			} else if (player.getInventory().containsItem(995, 10))
					sendNPCDialogue(npcId, 9827,
							"Slendid. You may now pass through the gate.");
					player.getInventory().removeItemMoneyPouch(995, 10);
					stage = 3;
			break;
		case 3:
			end();
			break;
		case 4:
			sendNPCDialogue(npcId, 9827, "Come back to me if you wish to pay the fee instead.");
			stage = 3;
			break;
					
		}
					
	}

	@Override
	public void finish() {

	}
}
