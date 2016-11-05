package com.rs.game.player.dialogues.GertrudesCat;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class Fluffs extends Dialogue {

	private int npcId = 7742;
	@Override
	public void start() {
		if (player.gertCat >= 0 && player.gertCat <= 1){
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name, "Hissss!" },
						IS_NPC, npcId, 9827);
			stage = -1;
		} else if (player.gertCat == 2){
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hissss!" },
							IS_NPC, npcId, 9827);
				stage = 1;
		} else if (player.gertCat == 3){
			if (player.getInventory().containsItem(1927, 1)) {
				sendPlayerDialogue(9827, "Lets try this.");
				stage = 4;
			} else {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Hissss!" },
								IS_NPC, npcId, 9827);
				stage  = 3;
			}
		} else if (player.gertCat == 4){
			if (player.getInventory().containsItem(1552, 1)) {
				sendPlayerDialogue(9827, "Lets try this again...");
				stage = 7;
			} else {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Hissss!" },
								IS_NPC, npcId, 9827);
				stage  = 6;
			}
		} else if (player.gertCat == 5){
			if (player.getInventory().containsItem(13236, 1)) {
				sendPlayerDialogue(9827, "Lets try this again...");
				stage = 10;
			} else {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Hissss!" },
								IS_NPC, npcId, 9827);
				stage  = 9;
			}
		} else if (player.gertCat == 6){
			
				player.getPackets().sendGameMessage("Fluffs has agreed to come home. you should report to Gertrude.");
			
			
			
		} else if (player.gertCat == 7){
			
			sendPlayerDialogue(9827, "Hey Fluffs! How's it going?");
			stage = 13;
		
		
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendPlayerDialogue(9827, "Ow! I better leave you alone...");
			stage = -2;
			} else if (stage == 1) {
			
					sendPlayerDialogue(9827, "You must be Fluffs...Ready to go home?");
					stage = 2;
				
			} else if (stage == 2) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "*Hissss*" },
								IS_NPC, npcId, 9827);
				stage = 3;
			
			} else if (stage == 3) {
				
				sendPlayerDialogue(9827, "ow! I guess she's not moving...Maybe she's thirsty?");
				player.gertCat = 3;
				stage = -2;
			
			} else if (stage == 4) {
				
				sendPlayerDialogue(9827, "Alright here's your milk...Now lets go.");
				player.getInventory().deleteItem(1927, 1);
				player.getInventory().addItem(1925, 1);
				player.gertCat = 4;
				stage = 5;
			
			} else if (stage == 5) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "*Hissss*" },
								IS_NPC, npcId, 9827);
				stage = 6;
			
			} else if (stage == 6) {
				
				sendPlayerDialogue(9827, "ow! I guess she's still not moving...Maybe she's Hungry?");
				
				stage = -2;
			
			} else if (stage == 7) {
				
				sendPlayerDialogue(9827, "Alright here's your food...Now lets go.");
				player.getInventory().deleteItem(1552, 1);
				player.gertCat = 5;
				stage = 8;
			
			} else if (stage == 8) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "*Hissss*" },
								IS_NPC, npcId, 9827);
				stage = 9;
			
			} else if (stage == 9) {
				
				sendPlayerDialogue(9827, "ow! I guess she's still not moving...Wait what's that I hear in the near distance?");
				player.getPackets().sendGameMessage("You hear what sounds like kitten purring nearby...");
				
				stage = -2;
			
			} else if (stage == 10) {
				
				sendPlayerDialogue(9827, "I found your kittens!");
				player.getInventory().deleteItem(13236, 1);
				player.gertCat = 6;
				stage = 11;
			
			} else if (stage == 11) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Meow!" },
								IS_NPC, npcId, 9827);
				stage = 12;
			
			} else if (stage == 12) {
				
				sendPlayerDialogue(9827, "Phew! It looks like Fluffs is going to go home now. I should go "
						+ "report to Gertrude about Fluffs coming back!");
				
				stage = -2;
			
			} else if (stage == 13) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Meeoow!" },
								IS_NPC, npcId, 9827);
				stage = 12;
			
			}
		}
		 
	

	@Override
	public void finish() {

	}
}