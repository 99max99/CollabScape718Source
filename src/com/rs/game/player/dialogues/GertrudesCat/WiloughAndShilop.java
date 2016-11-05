package com.rs.game.player.dialogues.GertrudesCat;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

public class WiloughAndShilop extends Dialogue {

	private int npcId = 781;
	private int npcId2 = 783;
	@Override
	public void start() {
		if (player.gertCat == 0){
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name, "Hey Mister! What do you want?" },
						IS_NPC, npcId, 9827);
			stage = -1;
		} else if (player.gertCat == 1){
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello Mister! How can we help you?"
									+ "" },
							IS_NPC, npcId, 9827);
				stage = 1;
		} else if (player.gertCat >= 2 && player.gertCat <= 6){
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hey again Mister! Do you need anything?" },
							IS_NPC, npcId2, 9827);
				stage = 12;
		} else if (player.gertCat == 7){
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Go away Mister. We're busy spending your money. Hehehe." },
							IS_NPC, npcId, 9827);
				stage = -2;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendPlayerDialogue(9827, "Nothing. Just looking around.");
			stage = -2;
			} else if (stage == 1) {
			
					sendPlayerDialogue(9827, "Hi there, are you kids um..Shilop and Wilough?");
					stage = 2;
				
			} else if (stage == 2) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Maybe..What's it to ya?" },
								IS_NPC, npcId2, 9827);
				stage = 3;
			
			} else if (stage == 3) {
				
				sendOptionsDialogue("SELECT AN OPTION", 
						"Gertrude Sent me...",
						"Nevermind....");
				stage = 4;
			
			} else if (stage == 4) {
			
				if (componentId == OPTION_1) {
					sendPlayerDialogue(9827, "Gertrude sent me to find her cat Fluffs. She said her boys might be able to help me.");
					stage = 5;
				} else if (componentId == OPTION_2) {
					end();
				
					
				}	
			
			} else if (stage == 5) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Hehe...Well in that case. I'm Wilough." },
								IS_NPC, npcId2, 9827);
				stage = 6;
			
			} else if (stage == 6) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "And i'm Shilop!" },
								IS_NPC, npcId, 9827);
				stage = 7;
			
			} else if (stage == 7) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Now..You said you were looking for our cat eh? "
										+ "Well I'll tell you what....If you can swing us..Oh..Say...10m, we'll gladly "
										+ "help you out!" },
								IS_NPC, npcId, 9827);
				stage = 8;
			
			} else if (stage == 8) {
				
				sendOptionsDialogue("SELECT AN OPTION", 
						"10m?!?! That's outragious",
						"Okay. That seems perfectly reasonable.");
				stage = 9;
			
			} else if (stage == 9) {
				if (componentId == OPTION_1) {
					sendPlayerDialogue(9827, "10m?!? That's outragious! You must be insane to think i'd pay that.");
					stage = 10;
				} else if (componentId == OPTION_2) {
					sendPlayerDialogue(9827, "Okay. That seems like a perfectly reasonable deal I'd say.");
				
					stage = 11;
				}
				
			} else if (stage == 10) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Oh come on Mister! Time's are tough. "
										+ "A couple of boys have gotta make a living you know? But if you insist. "
										+ "I guess we don't help you on your adventure. Hehehe." },
								IS_NPC, npcId, 9827);
				stage = -2;
			
			} else if (stage == 11) {
				if (player.getInventory().getCoinsAmount() >= 10000000) {
				
					player.getInventory().removeItemMoneyPouch(new Item(995, 10000000));
					sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Ahaha thank you sucker! err..I mean mister!"
										+ "Alright. We have a secret hideout east of varrock in a lumber yard. We were"
										+ "playing with Fluffs over there but I guess we..Forgot Fluffs there. Good luck, hehehe." },
								IS_NPC, npcId2, 9827);
					player.gertCat = 2;
				stage = -2;
				} else {
					sendEntityDialogue(
							SEND_2_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name, "Hey...Come back when you have the cash."
											+ " Then we'll talk." },
									IS_NPC, npcId, 9827);
					stage = -2;	
				}
			
			} else if (stage == 12) {
				
					sendPlayerDialogue(9827, "Hey. What can you tell me about Fluffs?");
					stage = 13;
				
				
			} else if (stage == 13) {
				
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Right. Fluffs should be in the Lumberyard"
										+ " just east of varrock. We don't really know much about her... You could ask"
										+ " Gertrude about Fluffs if you really need to. She would know more about her than we would." },
								IS_NPC, npcId2, 9827);
					stage = -2;
			
			}
		}
		 
	

	@Override
	public void finish() {

	}
}