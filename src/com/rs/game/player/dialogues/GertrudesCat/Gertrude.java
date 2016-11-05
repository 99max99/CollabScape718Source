package com.rs.game.player.dialogues.GertrudesCat;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class Gertrude extends Dialogue {

	private int npcId = 780;
	@Override
	public void start() {
		if (player.gertCat == 0){
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name, "Oh dear, or dear! What am I to do?" },
						IS_NPC, npcId, 9827);
			stage = -1;
		} else if (player.gertCat >= 1 && player.gertCat <= 5){
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again!"
									+ "" },
							IS_NPC, npcId, 9827);
				stage = 6;
		} else if (player.gertCat == 6){
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Fluffs has returned to me! Thank you so much! Is there anyway I"
									+ " Can repay you?"
									+ "" },
							IS_NPC, npcId, 9827);
				stage = 9;
		} else if (player.gertCat == 7){
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again! I hope your travels are going well." },
							IS_NPC, npcId, 9827);
				stage = -2;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("SELECT AN OPTION", 
					"What's wrong?",
					"I must be going now.");
			stage = 1;
			} else if (stage == 1) {
				if (componentId == OPTION_1) {
					sendPlayerDialogue(9827, "What's wrong?");
					stage = 2;
				} else if (componentId == OPTION_2) {
					end();
				
					
				}
			} else if (stage == 2) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "My poor cat Fluffs has gone missing! I DON'T KNOW WHERE SHE COULD BE."
										+ "Could you please help me kind adventurer?" },
								IS_NPC, npcId, 9827);
				stage = 3;
			} else if (stage == 3) {
				sendOptionsDialogue("SELECT AN OPTION", 
						"Sure I'd love to help!",
						"Sorry, I don't have time.");
				stage = 4;
			} else if (stage == 4) {
				if (componentId == OPTION_1) {
					sendPlayerDialogue(9827, "Sure, i'd love to help! What do you need me to do?");
					stage = 5;
				} else if (componentId == OPTION_2) {
					end();
				
					
				}
			} else if (stage == 5) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "I need you to find my poor Fluffy!"
										+ "I'm sorry that I don't know where she could have gone..But maybe my sons do!"
										+ "Their name's are Shilop and Wilough, they should be near varrock Square" },
								IS_NPC, npcId, 9827);
				player.gertCat = 1;
				stage = -2;
			} else if (stage == 6) {
				sendPlayerDialogue(9827, "Hey. Is there anything you can tell me about Fluffy that could help?");
				stage = 7;
			} else if (stage == 7) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "She's a very timid cat. She really likes to wonder around"
										+ "everywhere in the area. Her favorite food is doogle sardine and she always"
										+ "loves a good bucket of milk!" },
								IS_NPC, npcId, 9827);
				stage = 8;
			} else if (stage == 8) {
				sendPlayerDialogue(9827, "Thank you Gertrude!");
				stage = -2;
			} else if (stage == 9) {
				sendPlayerDialogue(9827, "Well a reward might be nice.");
				stage = 10;
			} else if (stage == 10) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Of coarse! Here you go!" },
								IS_NPC, npcId, 9827);
				stage = 11;
			} else if (stage == 11) {
				end();
				player.getInterfaceManager().handleGertrudesCatFinish();
				player.getInventory().addItem(1897, 1);
				player.getInventory().addItem(2003, 1);
				player.questPoints += 1;
				player.completedGertCat = true;
				player.gertCat = 7;
			}
		}
		 
	

	@Override
	public void finish() {

	}
}
