package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.quest.impl.ImpCatcher;

/**
 * Wizard Mizgog - Imp Catcher
 * 
 * @author Ridiculous <knol@outlook.com>
 */

public class WizardMizgog extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.startedImpCatcher == false) {
			sendPlayerDialogue(9827, "Give me a quest!");
		} else if (player.inProgressImpCatcher == true) {
			sendNPCDialogue(npcId, 9827, "So how are you doing finding my beads?");
			stage = 20;
		} else if (player.completedImpCatcher == true) {
			sendNPCDialogue(npcId, 9827,"Sorry, I have no quests at the moment.");
			stage = 10;
		}
	}


	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			sendNPCDialogue(npcId, 9827, "Give me a quest what?");
			stage = 0;
			break;
		case 0:
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Give me a quest please.",
					"Give me a quest or else!",
					"Just stop messing around and give me a quest!");
			stage = 1;
			break;
		case 1:
			if (componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Give me a quest please.");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Give me a quest or else!");
				stage = 15;
			} else if (componentId == OPTION_3) {
				sendPlayerDialogue(9827, "Just stop messing around and give me a quest!");
				stage = 18;
			}
			break;
		case 2:
			sendNPCDialogue(npcId, 9827, "Well seeing as you asked nicely... I could do with some help.");
			stage = 3;
			break;
		case 3:
			sendNPCDialogue(npcId, 9827, "The wizard Grayzag next door decided he didn't like me so he enlisted an army of hunderds of imps.");
			stage = 4;
			break;
		case 4:
			sendNPCDialogue(npcId, 9827, "These imps stole all sorts of things. Most of these I don't really care about, just eggs and balls of string and things..");
			stage = 5;
			break;
		case 5:
			sendNPCDialogue(npcId, 9827, "But they stole my four magical beads, there were a red once, a yellow one, a black one, and a white one.");
			stage = 6;
			break;
		case 6:
			sendNPCDialogue(npcId, 9827, "These imps have now spread our all over the kingdom.",
					"Could you get my beads back for me?");
			stage = 7;
			break;
		case 7:
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"I'll try.",
					"I've better things to do than chase imps.");
			stage = 8;
			break;
		case 8:
			if (componentId == OPTION_1) {
				sendPlayerDialogue(9827, "I'll try.");
				stage = 11;
			} else if (componentId == OPTION_2) {
				sendPlayerDialogue(9827, "I've better things to do than chase imps.");
				stage = 9;
			}
			break;
		case 9:
			sendNPCDialogue(npcId, 9827, "Well if you're not interested in the quests I have to give you, don't waste my time by asking for them.");
			stage = 10;
			break;
		case 10:
			end();
			break;
		case 11:
			sendNPCDialogue(npcId, 9827, "That's great, thank you.");
			ImpCatcher.handleProgressQuest(player);
			stage = 12;
			break;
		case 12:
			end();
			break;
		case 15:
			sendNPCDialogue(npcId, 9827, "Or else what? You'll attack me?");
			stage = 16;
			break;
		case 16:
			sendNPCDialogue(npcId, 9827, "Hahaha!");
			stage = 17;
			break;
		case 17:
			end();
			break;
		case 18:
			sendNPCDialogue(npcId, 9827, "Ah now you're assuming I have one to give.");
			stage = 19;
			break;
		case 19:
			end();
			break;
		case 20:
			if (!player.getInventory().containsItem(1474, 1)
			|| !player.getInventory().containsItem(1470, 1)
			|| !player.getInventory().containsItem(1476, 1)
			|| !player.getInventory().containsItem(1472, 1)) {
			sendPlayerDialogue(9827, "I've not found all of them yet.");
			stage = 21;
		} else if (player.getInventory().containsItem(1474, 1)
				|| player.getInventory().containsItem(1470, 1)
				|| player.getInventory().containsItem(1476, 1)
				|| player.getInventory().containsItem(1472, 1)) {
			sendPlayerDialogue(9827,
					"I've got all four beads. It was hard work I can tell you.");
			stage = 23;
		}
			break;
		case 21:
			sendNPCDialogue(npcId, 9827, "Well get on with. I've lost a white bead, a red bead, a black bead, and a yellow bead. Go kill some imps!");
			stage = 21;
			break;
		case 23:
			sendNPCDialogue(npcId, 9827, "Give them here and I'll check that they really are MY beads, before I give your reward. You'll like it, it's an amulet of accuracy.!");
			stage = 24;
			break;
		case 24:
			sendDialogue("You give four coloured beads to Wizard Mizgog.");
			player.getInventory().deleteItem(1470, 1);
			player.getInventory().deleteItem(1472, 1);
			player.getInventory().deleteItem(1474, 1);
			player.getInventory().deleteItem(1476, 1);
			stage = 25;
			break;
		case 25:
			sendNPCDialogue(npcId, 9827, "Thanks for finding my beads, here is your reward.");
			stage = 26;
			break;
		case 26:
			ImpCatcher.handleQuestComplete(player);
			ImpCatcher.handleQuestCompleteInterface(player);
			end();
			break;
		}
	}
	public void finish() {
	}
}