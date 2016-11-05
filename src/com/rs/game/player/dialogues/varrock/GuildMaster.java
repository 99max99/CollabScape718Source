package com.rs.game.player.dialogues.varrock;

import com.rs.game.player.dialogues.Dialogue;

/**
 *@Author Danny
 */

public class GuildMaster extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Greetings!");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "What is this place?", 
					"Talk about Quest.", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "What is this place?");
				stage = 1;
				break;
			} else if(componentId == OPTION_2) {
				if (player.DS == 0) {
				sendPlayerDialogue(9827, "Can I have a quest?");
				player.DS = 1;
				stage = 2;
				break;
				} else if (player.DS == 1) {
				sendPlayerDialogue(9827, "What do I need to do again?");
				stage = 5;
				break;
				} else if (player.DS == 2) {
				sendPlayerDialogue(9827, "I talked to Oziach...");
				stage = 8;
				player.DS = 3;
				break;
				} else if (player.DS == 3) {
				sendPlayerDialogue(9827, "I would like to know a bit more of information...");
				stage = 20;
				break;
				} else if (player.DS >= 6) {
				sendPlayerDialogue(9827, "I currently have no more quests for you...");
				stage = 11;
				break;
				}
			} else if(componentId == OPTION_3) {
				stage = 11;
				break;
			} 
		case 1:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "This is the Champions' Guild. Only adventurers who have",
					"proved themselves worthy by gaining influence from",
					"quests are allowed in here.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Aha!");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Yes! A mighty and perilous quest fit only for the most",
					"powerful champions! And, at the end of it, you will earn",
					"the right to wear the legendary rune platebody!");
			break;
		case 4:
			stage = 5;
			sendPlayerDialogue(9827, "So, what is this quest?");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "You'll have to speak to Oziach, the maker of rune	",
					"armour. He sets the quests that champions must",
					"complete in order to wear it.");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "Oziach lives in a hut, by the cliffs to the west of",	
					"Edgeville. He can be a little...odd...sometimes, though.");
			break;
		case 7:
			stage = 11;
			sendPlayerDialogue(9827, "Okay, I can do that!");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue(npcId, 9827, "Oh? What did he tell you to do?");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(9827, "Defeat the dragon of Crandor.");
			break;
		case 10:
			stage = 12;
			sendNPCDialogue(npcId, 9827, "The dragon of Crandor?");
			break;
		case 11:
			end();
			break;
		case 12:
			stage = 13;
			sendPlayerDialogue(9827, "Um, yes...");
			break;
		case 13:
			stage = 14;
			sendNPCDialogue(npcId, 9827, "Goodness, he hasn't given you an easy job, has he?");
			break;
		case 14:
			stage = 15;
			sendPlayerDialogue(9827, "What's so special about this dragon?");
			break;
		case 15:
			stage = 16;
			sendNPCDialogue(npcId, 9827, "Thirty years ago, Crandor was a thriving community",
					"with a great tradition of mages and adventurers. Many",
					"Crandorians even earned the right to be apart of the",
					"Champions' Guild!");
			break;
		case 16:
			stage = 17;
			sendNPCDialogue(npcId, 9827, "One of their adventurers went to far, however. He",
					"descended in to the volcano of the centre of Crandor",
					"and woke the dragon Elvarg.");
			break;
		case 17:
			stage = 18;
			sendNPCDialogue(npcId, 9827, "He must have fought valiantly against the dragon",
					"because they say that, to this day, she has a scar down",
					"her side,");
			break;
		case 18:
			stage = 19;
			sendNPCDialogue(npcId, 9827, "but the dragon still won the fight. She emerged and laid",
					"waste to the whole of Crandor with her fire breath!");
			break;
		case 19:
			stage = 20;
			sendNPCDialogue(npcId, 9827, "If you're serious about taking on Elvarg, first you'll",
					"need to get to Crandor. The island is surrounded by",
					"dangerous reefs, so you'll need a ship capable of getting",
					"through them and a captain that knows the way.");
			break;
		case 20:
			stage = 21;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Where can I find a captain that will sail to Crandor?", 
					"Where can I find the right ship?", "How can I protect myself from the dragon's breath?", "Where can I find the map pieces for Crandor?", "Okay, I'll get going!");
			break;
		case 21:
			if(componentId == OPTION_1) {
				sendPlayerDialogue(9827, "Where can I find a captain that will sail to Crandor?");
				stage = 23;
				break;
			} else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Where can I find the right ship?");
				stage = 24;
				break;
			} else if(componentId == OPTION_3) {
				sendPlayerDialogue(9827, "How can I protect myself from the dragon's breath?");
				stage = 27;
				break;
			} else if(componentId == OPTION_4) {
				sendPlayerDialogue(9827, "Where can I find the map pieces for Crandor?");
				stage = 28;
				break;
			} else if(componentId == OPTION_5) {
				stage = 22;
				sendPlayerDialogue(9827, "Okay I will head on now!");
				break;
			} 
		case 22:
			stage = 11;
			sendNPCDialogue(npcId, 9827, "Goodluck young adventurer!");
			break;
		case 23:
			stage = 20;
			sendNPCDialogue(npcId, 9827, "I would start at Port Sarim, there are plenty of captains",
					"around there that you might be able to convince to",
					"sail you to Crandor.");
			break;
		case 24:
			stage = 25;
			sendNPCDialogue(npcId, 9827, "Even if you find a captian to take you, only a ship",
					"made to the old crandorian design would be able to",
					"navigate through the reefs to the island.");
			break;
		case 25:
			stage = 26;
			sendNPCDialogue(npcId, 9827, "If there's still one in existence, it's probably in Port ",
					"Sarim.");
			break;
		case 26:
			stage = 20;
			sendNPCDialogue(npcId, 9827, "Then, of course, you'll need to find a captain willing to",
					"sail to Crandor, and I'm not sure where you'd find one",
					"of them!");
			break;
		case 27:
			stage = 20;
			sendNPCDialogue(npcId, 9827, "I suggest you talk to the Duke of Lumbridge for an anti-dragon",
					"shield that provides the best protection against dragon fire.",
					"You can find him in his room on the second floor of the",
					"Lumbridge Castle, tell him I sent you to him.");
			break;
		case 28:
			stage = 29;
			sendNPCDialogue(npcId, 9827, "The map pieces can be found in three parts. The following people",
					"have these map pieces: Melzar, Thalzar, and Lozar.");
			break;
		case 29:
			stage = 30;
			sendNPCDialogue(npcId, 9827, "The first map piece can be found inside a chest at the end of",
			"Melzar's Maze, which can be near Rimmington and south of the Crafting Guild.");
			break;
		case 30:
			stage = 31;
			sendNPCDialogue(npcId, 9827, "Here, you will need this key to enter the maze.");
			player.getInventory().addItem(1542, 1);
			break;
		case 31:
			stage = 32;
			sendNPCDialogue(npcId, 9827, "Be warned however, Melzar is insane and he may be agressive.",
					"He guards the key and will not give it to anyone. There are also",
					"rumors of a demon guarding the chest, so bring your best gear.");
			break;
		case 32:
			stage = 33;
			sendNPCDialogue(npcId, 9827, "The second map piece is protected deep in the Dwarven Mines.",
					"I am not sure how to pass the magical barrier guarding it, but you",
					"should speak to the Oracle who can be found on the Ice Mountain.");
			break;
		case 33:
			stage = 20;
			sendNPCDialogue(npcId, 9827, "The third map piece was stolen by a goblin in a raid.",
					"The goblin can be found in the Port Sarim jail, you need to",
					"convince him to had it over to you.");
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}