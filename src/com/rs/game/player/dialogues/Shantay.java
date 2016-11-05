package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.utils.ShopsHandler;

public class Shantay extends Dialogue {
	
	
	private int npcId = 836;
	@Override
	public void start() {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name, "Hello, effendi." },
						IS_NPC, npcId, 9827);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Please read the billboard poster before going into the"
									+ "desert. It'll give you details on the dangers you can face." },
							IS_NPC, npcId, 9827);
				stage = 1;
			}
			else if (stage == 1) {
			sendOptionsDialogue("SELECT AN OPTION", 
					"What is this place?",
					"Can I see what you have to sell, please?",
					"I must be going.");
			stage = 2;
		}
			else if (stage == 2) {
					if (componentId == OPTION_1) {
						sendPlayerDialogue(9827, "What is this place?");
						stage = 3;
					} else if (componentId == OPTION_2) {
						sendPlayerDialogue(9827, "Can I see what you have to sell, please?");
						stage = 23;
					
						
					} else if (componentId == OPTION_3) {
						sendPlayerDialogue(9827, "I must be going.");
						stage = 19;
					}
				} else if (stage == 3) {
					sendEntityDialogue(
							SEND_2_TEXT_CHAT,
							new String[] {
									NPCDefinitions.getNPCDefinitions(npcId).name, "This is Shanty Pass. I guard this area with my men. I am"
											+ " Responsible for keeping this pass open and repaired." },
									IS_NPC, npcId, 9827);
					stage = 4;
				} else if (stage == 4) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "My men and I prevent outlaws from getting out of the"
												+ " desert. And we stop the inexperienced from a dry death in"
												+ " the sands. Which would you say you were?" },
										IS_NPC, npcId, 9827);
						stage = 5;
					} else if (stage == 5) {
						sendOptionsDialogue("SELECT AN OPTION", 
								"I am definitely an outlow, prepare to die!",
								"I am a little inexperienced",
								"Er, neither. I'm an adventurer.");
						stage = 6;
					} else if (stage == 6) {
						if (componentId == OPTION_1) {
							sendPlayerDialogue(9827, "I am definitely an outlow, prepare to die!");
							stage = 7;
						} else if (componentId == OPTION_2) {
							sendPlayerDialogue(9827, "I am a little inexperienced");
							stage = 15;
						
							
						} else if (componentId == OPTION_3) {
							sendPlayerDialogue(9827, "Er, neither. I'm an adventurer.");
							stage = 20;
						}
					} else if (stage == 7) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "Haha, very funny..."
												+ " Guards, arrest him!" },
										IS_NPC, npcId, 9827);
						stage = 8;
					} else if (stage == 8) {
						player.getPackets().sendGameMessage("Shantay's guards seize you and lock you up.");
						player.setNextWorldTile(new WorldTile(3299, 3123, 0));
						
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "That's how we treat troublemakers around here. Now, if"
												+ " you pay a fine I will release you. Otherwise you will be"
												+ " deported to a maximum security jail in Port Sarim."
												+ " Choose!" },
										IS_NPC, npcId, 9827);
						
						stage = 9;
					} else if (stage == 9) {
						sendOptionsDialogue("WHAT WOULD YOU LIKE TO SAY?", 
								"I'll pay.",
								"Deport me to Port Sarim!",
								"I'll just stay in here, thanks.");
						stage = 10;
					} else if (stage == 10) {
						if (componentId == OPTION_1) {
							sendPlayerDialogue(9827, "I'll pay.");
							stage = 11;
						} else if (componentId == OPTION_2) {
							sendPlayerDialogue(9827, "Deport me to Port Sarim!");
							stage = 12;
						
							
						} else if (componentId == OPTION_3) {
							sendPlayerDialogue(9827, "I'll just stay in here, thanks.");
							stage = 14;
						}
					} else if (stage == 11) {
						if (player.getInventory().containsItem(995, 5))
						player.getDialogueManager().startDialogue("SimpleMessage","Shantay takes 5 coins and unlocks the door.");
						else
							player.getDialogueManager().startDialogue("SimpleMessage","You don't have 5 coins so Shantay lets you off with a warning.");
						
					} else if (stage == 12) {
						
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "I hope this teaches you a lesson!" },
										IS_NPC, npcId, 9827);
						end();
						
						stage = 13;
					} else if (stage == 13) {
						player.setNextWorldTile(new WorldTile(3014, 3180, 0));
					} else if (stage == 14) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "Ah, it is good that you are willing to spend time reflecting"
												+ " on your misdeeds." },
										IS_NPC, npcId, 9827);
						end();
					} else if (stage == 15) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "Can I recommend that you take at least one full"
												+ " waterskin into the desert. The sun is very hot, and"
												+ " without water to drink you may die of thirst." },
										IS_NPC, npcId, 9827);
						stage = 16;
					} else if (stage == 16) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "I would suggest bringing a knife along too. If your"
												+ " waterskins are running dry, you can cut the cacti you see"
												+ " to replenish them." },
										IS_NPC, npcId, 9827);
						stage = 17;
					} else if (stage == 17) {
						sendOptionsDialogue("SELECT AN OPTION", 
								"Can I see what you have to sell, please?",
								"I must be going.");
						stage = 18;
					} else if (stage == 18) {
						if (componentId == OPTION_1) {
							sendPlayerDialogue(9827, "Can I see what you have to sell, please?");
							stage = 23;
						} else if (componentId == OPTION_2) {
							sendPlayerDialogue(9827, "I must be going.");
							stage = 19;
						
						}
					} else if (stage == 19) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "So long..." },
										IS_NPC, npcId, 9827);
						end();
					} else if (stage == 20) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "Good, I have just the thing for the desert adventurer:"
												+ " cloths that will keep you cool in the heart of the sun." },
										IS_NPC, npcId, 9827);
						stage = 21;
					} else if (stage == 21) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "I also sell waterskins, which will help you survive from"
												+ " dying of thirst." },
										IS_NPC, npcId, 9827);
						stage = 22;
					} else if (stage == 22) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "And you are free to use the bank chest here. It is unwise"
												+ " to travel the desert wearing thick and heavy armour - you"
												+ " will only dehydrate faster." },
										IS_NPC, npcId, 9827);
						stage = 17;
					} else if (stage == 23) {
						sendEntityDialogue(
								SEND_2_TEXT_CHAT,
								new String[] {
										NPCDefinitions.getNPCDefinitions(npcId).name, "Absolutely, effendi!" },
										IS_NPC, npcId, 9827);
						end();
						stage = 24;
					} else if (stage == 24) {
						ShopsHandler.openShop(player, 96);
					}
		
		
	}
		 
	

	@Override
	public void finish() {

	}
}
