package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.dialogues.quests.CooksAssistant;

/**
 * The Feather Dialogue for the Lumbridge Cook.
 * 
 * @author Gircat <gircat101@gmail.com>
 * @author _Jordan / Apollo <citellumrsps@gmail.com>
 * @author Feather RuneScape 2012 Remake
 */

public class LumbridgeCook extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.startedCooksAssistant == false) {
			sendNPCDialogue(npcId, 9827, "What am I to do?");
			stage = 24;
		} else if (player.inProgressCooksAssistant == true) {
			sendNPCDialogue(npcId, 9827, "How are you getting on with finding the ingredients?");
			stage = 41;
		} else if (player.startedCooksAssistant == true) {
			sendNPCDialogue(npcId, 9827,
					"Hi there, "
							+ (player.getAppearence().isMale() ? "sir"
									: "ma'am") + "!",
					"What can I help you with today?");
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Do you have any other quests for me?",
					"I am getting strong and mighty.", "I keep on dying.",
					"Can I use your range?",
					"Can you tell me anything about that chest in the basement?");
			stage = 0;
			break;
		case 0:
			if (componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Do you have any other quests for me?");
				stage = 1;
			} else if (componentId == OPTION_2) {
				sendPlayerDialogue(9827, "I am getting strong and mighty.");
				stage = 7;
			} else if (componentId == OPTION_3) {
				sendPlayerDialogue(9827, "I keep on dying.");
				stage = 9;
			} else if (componentId == OPTION_4) {
				sendPlayerDialogue(9827, "Can I use your range?");
				stage = 11;
			} else if (componentId == OPTION_5) {
				sendPlayerDialogue(9827,
						"Can you tell me anything about that chest in the basement?");
				stage = 19;
			}
			break;

		case 1:
			sendPlayerDialogue(9827, "That last one was fun!");
			stage = 2;
			break;
		case 2:
			sendNPCDialogue(npcId, 9827, "Ooh dear, yes I do.");
			stage = 3;
			break;
		case 3:
			sendNPCDialogue(npcId, 9827,
					"It's the Duke of Lumbridge's birthday today, and",
					"I need to bake him a cake!");
			stage = 4;
			break;
		case 4:
			sendNPCDialogue(npcId, 9827,
					"I need you to bring me some eggs, some flour, ",
					"some milk and a chocolate bar...");
			stage = 5;
			break;
		case 5:
			sendNPCDialogue(npcId, 9827,
					"Nah, not really, I'm just messing with you! ",
					"Thanks for all your help, I know I can count on you",
					"again in the future!");
			stage = 6;
			break;
		case 6:
			end();
			break;
		case 7:
			sendNPCDialogue(npcId, 9827, "Glad to hear it.");
			stage = 8;
			break;
		case 8:
			end();
			break;
		case 9:
			sendNPCDialogue(npcId, 9827,
					"Ah, well, at least you keep coming back to life too!");
			stage = 10;
			break;
		case 10:
			end();
			break;
		case 11:
			sendNPCDialogue(npcId, 9827,
					"Go ahead! It's a very good range; it's better than ",
					"most other ranges.");
			stage = 12;
			break;
		case 12:
			sendNPCDialogue(npcId, 9827,
					"It's called the Cook-o-Matic 25 and it uses a ",
					"combination of state-of-the-art temperature ",
					"regulation and magic.");
			stage = 13;
			break;
		case 13:
			sendPlayerDialogue(9827,
					"Will it mean my food will burn less often?");
			stage = 14;
			break;
		case 14:
			sendNPCDialogue(npcId, 9827,
					"As long as the food is fairly easy to cook in ",
					"the first place!");
			stage = 15;
			break;
		case 15:
			sendNPCDialogue(npcId, 9827,
					"Here, take this manual. It should tell you ",
					"everything you need to know about this range.");
			stage = 16;
			break;
		case 16:
			sendEntityDialogue(SEND_1_TEXT_CHAT, new String[] {
					"Check your inventory",
					"-- The cook hands you a manuel. --" }, IS_ITEM, 15411, 1);
			player.getInventory().addItem(15411, 1);
			player.getPackets().sendConfig(1021, 101);
			stage = 17;
			break;
		case 17:
			sendPlayerDialogue(9827, "Thanks!");
			player.getPackets().sendConfig(1021, 0); // unflash
			stage = 18;
			break;
		case 18:
			end();
			break;
		case 19:
			sendPlayerDialogue(9827,
					"So that chest in the basement that suddenly ",
					"appeared along with the Culinaromancer...");
			stage = 20;
			break;
		case 20:
			sendPlayerDialogue(9827, "Can you tell me anything about it?");
			stage = 21;
			break;
		case 21:
			sendNPCDialogue(npcId, 9827,
					"You mean you didn't check it out yet?");
			stage = 22;
			break;
		case 22:
			sendNPCDialogue(npcId, 9827,
					"I really think you should, it seems to be some kind of ",
					"magical cooking chest, I found a bunch of food in it ",
					"earlier, along with a bunch of weird looking kitchen ",
					"equipment and some snazzy gloves!");
			stage = 23;
			break;
		case 23:
			end();
			break;
		case 24:
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What's wrong?",
					"Can you make me a cake?", "You don't look very happy.",
					"Nice hat!");
			stage = 25;
			break;
		case 25:
			if (componentId == OPTION_1) {
				sendPlayerDialogue(9827, "What's wrong?");
				stage = 26;
			} else if (componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Can you make me a cake?");
				stage = 27;
			} else if (componentId == OPTION_3) {
				sendPlayerDialogue(9827, "You don't look very happy.");
				stage = 28;
			} else if (componentId == OPTION_4) {
				sendPlayerDialogue(9827, "Nice hat!");
				stage = 29;
			}
			break;
		case 26:
			sendNPCDialogue(
					npcId,
					9827,
					"Oh dear, oh dear. I'm in a terrible mess! It's the Duke's",
					"birthday today, and I should be making him a lovely big",
					"birthday cake using special ingredients...");
			stage = 30;
			break;
		case 27:
			sendNPCDialogue(npcId, 9827,
					"Oh I don't have time to make you a cake, can't you see I'm a bit busy?");
			stage = 31;
			break;
		case 28:
			sendNPCDialogue(npcId, 9827,
					"I know, I've been busy trying to figure out what I should do.");
			stage = 31;
			break;
		case 29:
			sendNPCDialogue(npcId, 9827,
					"I don't have time for your pesky compliments!");
			stage = 31;
			break;
		case 31:
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What's wrong?",
					"Nevermind");
			stage = 32;
			break;
		case 32:
			if (componentId == OPTION_1) {
				sendPlayerDialogue(9827, "What's wrong?");
				stage = 26;
			} else if (componentId == OPTION_2) {
				end();
			}
			break;
		case 30:
			sendNPCDialogue(
					npcId,
					9827,
					"but I've forgotten to get the ingredients. I'll never get",
					"them in time now. He'll sack me! Whatever will I do? I have",
					"four children and a goat to look after. Would you help me?",
					"Please?");
			stage = 33;
			break;
		case 33:
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"I'm always happy to help a cook in distress.",
					"I can't right now. Maybe later.");
			stage = 34;
			break;
		case 34:
			if (componentId == OPTION_1) {
				sendPlayerDialogue(9827,
						"I'm always happy to help a cook in distress.");
				stage = 35;
			} else if (componentId == OPTION_2) {
				sendPlayerDialogue(9827, "I can't right now. Maybe later.");
				stage = 36;
			}
			break;
		case 36:
			end();
			break;
		case 35:
			sendNPCDialogue(
					npcId,
					9827,
					"Oh, thank you, thank you. I must tell you that this is no ordinary",
					"cake, though - only the best ingredients will do! I need a super",
					"large egg, top-quality milk and some extra fine flour.");
			CooksAssistant.handleProgressQuest(player);
			stage = 37;
			break;
		case 37:
			sendPlayerDialogue(9827, "Where can I find those, then?");
			stage = 38;
			break;
		case 38:
			sendNPCDialogue(
					npcId,
					9827,
					"That's the problem: I don't exactly know. I usually send my assistant",
					"to get them for me but he quit. I've marked some places on your world",
					"map in red. You might want to consider investigating them.");
			stage = 39;
			break;
		case 39:
			sendPlayerDialogue(9827,
					"That's all the information I need. Thanks!");
			stage = 40;
			break;
		case 40:
			end();
			break;
		case 41:
			if (player.getInventory().containsItem(1933, 1) == false
					|| player.getInventory().containsItem(1927, 1) == false
					|| player.getInventory().containsItem(1944, 1) == false) {
				sendPlayerDialogue(9827,
						"I'm still working on it.");
				stage = 42;
			} else if (player.getInventory().containsItem(1933, 1)
					&& player.getInventory().containsItem(1927, 1)
					&& player.getInventory().containsItem(1944, 1)) {
				sendPlayerDialogue(9827,
						"Here's some top-quality milk.");
				player.getInventory().deleteItem(1927, 1);
				stage = 43;
			}
			break;
		case 42:
			end();
			break;
		case 43:
			sendPlayerDialogue(9827,
					"Here's the extra fine flour.");
			player.getInventory().deleteItem(1933, 1);
			stage = 44;
			break;
		case 44:
			sendPlayerDialogue(9827,
					"Here's the egg you needed.");
			player.getInventory().deleteItem(1944, 1);
			stage = 45;
			break;
		case 45:
			sendNPCDialogue(
					npcId,
					9827,
					"You've brought me everything I need! I am saved! Thank you!");
			stage = 46;
			break;
		case 46:
			sendPlayerDialogue(9827,
					"So, do I get to go to the Duke's party?");
			stage = 47;
			break;
		case 47:
			sendNPCDialogue(
					npcId,
					9827,
					"I'm afraid not. Only the big cheeses get to dine with the Duke.");
			stage = 48;
			break;
		case 48:
			sendPlayerDialogue(9827,
					"Well, maybe one day, I'll be important enough to sit at the Duke's table.");
			stage = 49;
			break;
		case 49:
			sendNPCDialogue(
					npcId,
					9827,
					"Maybe, but I won't be holding my breath.");
			stage = 50;
			break;
		case 50:
			end();
			CooksAssistant.handleQuestComplete(player);
			CooksAssistant.handleQuestCompleteInterface(player);
			break;
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
