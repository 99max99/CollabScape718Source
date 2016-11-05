package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.ForceTalk;
import com.rs.utils.ShopsHandler;

public class OttoGodblessed extends Dialogue {

	private int npcId = 2725;

	@Override
	public void start() {
		if (player.barCrawl == 0) {
		sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name, "Hello there! I am Otto, welcome "
								+ "to my grotto." },
						IS_NPC, npcId, 9827);
		stage = -1;
		} else if (player.barCrawl == 1 && player.barCrawlCompleted == false) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again, could I help "
									+ "you with something?" },
							IS_NPC, npcId, 9827);
			stage = 8;
		} else if (player.barCrawl == 1 && player.barCrawlCompleted == true) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again, could I help "
									+ "you with something?" },
							IS_NPC, npcId, 9827);
			stage = 11;
		} else if (player.barCrawl == 2) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again, could I help "
									+ "you with something?" },
							IS_NPC, npcId, 9827);
			stage = 17;
		} else if (player.barCrawl == 3 && !(player.getInventory().containsItem(11329, 100) && player.getInventory().containsItem(11331, 100) && player.getInventory().containsItem(11333, 100))) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again, could I help "
									+ "you with something?" },
							IS_NPC, npcId, 9827);
			stage = 20;
		} else if (player.barCrawl == 3 && (player.getInventory().containsItem(11329, 100) && player.getInventory().containsItem(11331, 100) && player.getInventory().containsItem(11333, 100))) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again, could I help "
									+ "you with something?" },
							IS_NPC, npcId, 9827);
			stage = 22;
		} else if (player.barCrawl == 4) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again, could I help "
									+ "you with something?" },
							IS_NPC, npcId, 9827);
			stage = 25;
		} else if (player.barCrawl == 5) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							NPCDefinitions.getNPCDefinitions(npcId).name, "Hello again, could I help "
									+ "you with something?" },
							IS_NPC, npcId, 9827);
			stage = 29;
		}
	}//end of grotto introduction
	
	

	//everything above is done

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Otto", 
					"Why are you all the way over here?",
					"Is there anything you can teach me?",
					"Can I use your fishing spots and Anvil?",
					"Goodbye.");
			stage = 1;
		} else if (stage == 1) {
			if (componentId == OPTION_1) {
				sendNPCDialogue(
						npcId,
						9827,
						"I choose to live my life around nature to avoid all the barbaric matter in my home town.");
				stage = -1;
			} else if (componentId == OPTION_2) {
				sendNPCDialogue(
						npcId,
						9827,
						"As a matter of fact, yes I could teach you some of my own barbarian techniques such as fishing, smithing, firemaking and more! But I have a task for you to complete first.");
				stage = 2;
			
				
			} else if (componentId == OPTION_3) {
				sendPlayerDialogue(9827, "May I use your anvil and fishing hotspot?");
				stage = 7;
			} else if (componentId == OPTION_4) {
				sendPlayerDialogue(9827, "Goodbye.");
				stage = 100;
			}
		} else if (stage == 2) {
			sendPlayerDialogue(9827, "What do you need me to do?");
			stage = 3;
		} else if (stage == 3) {
			sendNPCDialogue(
					npcId,
					9827,
					"I need you to go around CollabScape and fill out this BarCrawl card for my brother over at the agility course. It shouldn't take too long.");
			stage = 4;
		} else if (stage == 4) {
			sendOptionsDialogue("Help out Otto?", "Yes",
					"No thanks.");
			stage = 5;
		} else if (stage == 5) {
			if (componentId == OPTION_1){
				sendPlayerDialogue(9827, "I'd love to help you out! Just tell me the details");
				stage = 6;
				
			} else if (componentId == OPTION_2) {
				end();
			}
		} else if (stage == 6) {
			player.getInventory().addItem(455, 1);
				sendNPCDialogue(
						npcId,
						9827,
						"Alright! I need you to go to various bars around CollabScape and drink their signature Ale. Then fill out the card. Once the card is filled out, come back to me and I will teach you some new skills!");
				
				player.barCrawl = 1;
			stage = 8;
				
				
				} else if (stage == 7) {
					sendNPCDialogue(
							npcId,
							9827,
							"I might let you use them if you do something for me first.");
					stage = -1;
					} else if (stage == 8) {
						sendOptionsDialogue("Otto", 
								"What Am I supposed to be doing again?",
								"I have lost my BarCrawl Card...",
								"Where are the pubs located?",
								"Goodbye.");
						stage = 9;
					} else if (stage == 9) {
						if (componentId == OPTION_1) {
							sendNPCDialogue(
									npcId,
									9827,
									"You have to go to Pubs around CollabScape and fill out the BarCrawl Card by drinking the beer that they give you. Once you have completed that, come back to me.");
							stage = 8;
						} else if (componentId == OPTION_2) {
							sendPlayerDialogue(9827, "Erm..I have lost the card...");
							stage = 10;
						
							
						} else if (componentId == OPTION_3) {
							sendNPCDialogue(
									npcId,
									9827,
									"The bars are located all around CollabScape! But if I told you the exact locations, it wouldn't be as fun!");
							
							stage = 8;
						} else if (componentId == OPTION_4) {
							sendPlayerDialogue(9827, "Goodbye.");
							end();
						}
					} else if (stage == 10) {
							sendNPCDialogue(
									npcId,
									9827,
									"Don't worry, I have a spare!");
							player.getInventory().addItem(455, 1);
							stage = 8;
							} else if (stage == 11) {
								sendOptionsDialogue("Otto", 
										"I have completed the BarCrawl Card!",
										"Goodbye.");
								stage = 12;
							} else if (stage == 12) {
								if (componentId == OPTION_1) {
									sendPlayerDialogue(9827, "I have filed out the BarCrawl Card completely! Will you teach me now?");
									stage = 13;
								} else if (componentId == OPTION_2) {
									sendPlayerDialogue(9827, "Goodbye.");
									end();
								
									
								}
							} else if (stage == 13) {
									if (player.getInventory().containsItem(455, 1)) {
									sendNPCDialogue(
											npcId,
											9827,
											"Impressive! I will take that now, thank you. I will now teach you how to skill like a barbarian!");
									player.getInventory().deleteItem(455, 1);
									stage = 16;
									} else if (!player.getInventory().containsItem(455, 1)) {
										sendNPCDialogue(
												npcId,
												9827,
												"You don't seem to have the card with you...Do you need another one?");
										stage = 14;
									}
							} else if (stage == 14) {
										sendOptionsDialogue("Otto", 
												"Yes",
												"No");
										stage = 15;
									} else if (stage == 15) {
										if (componentId == OPTION_1) {
											sendNPCDialogue(
													npcId,
													9827,
													"Here you go!, don't lose this one.");
											player.getInventory().addItem(455, 1);
											stage = 16;
										} else if (componentId == OPTION_2) {
											sendPlayerDialogue(9827, "No I don't");
											end();
										
											
										}
									} else if (stage == 16) {
										sendNPCDialogue(
												npcId,
												9827,
												"Now, what would you like to learn?");
										stage = 17;
										player.barCrawl = 2;
								} else if (stage == 17) {
									sendOptionsDialogue("What should Otto Teach you?", 
											"Barbarian Fishing",
											"Barbarian Pyre Shipping",
											"Barbarian Smithing",
											"Barbarian FireMaking",
											"Barbarian Herblore");
									stage = 18;
								} else if (stage == 18 ) {
									if (componentId == OPTION_1) {
										sendNPCDialogue(
												npcId,
												9827,
												"Ah yes, the noble art of Barbarian Fishing. Take my special Fishing Rod. You may now fish at the spots outside my house. Bring me 100 of each noted fish, that is your first task.");
										player.getInventory().addItem(11323, 1);
										player.barCrawl = 3;
										stage = 19;
									} else if (componentId == OPTION_2) {
										sendNPCDialogue(
												npcId,
												9827,
												"You are not ready to learn this skill yet.");
										stage = 17;
									} else if (componentId == OPTION_3) {
										sendNPCDialogue(
												npcId,
												9827,
												"You are not ready to learn this skill yet.");
										stage = 17;
										
										
									} else if (componentId == OPTION_4) {
										sendNPCDialogue(
												npcId,
												9827,
												"You are not ready to learn this skill yet.");
										stage = 17;
										
									} else if (componentId == OPTION_5) {
										sendNPCDialogue(
												npcId,
												9827,
												"You are not ready to learn this skill yet.");
										stage = 17;
									
										
									}
								} else if (stage == 19) {
									end();
								} else if (stage == 20) {
									sendOptionsDialogue("Otto", 
											"What did you want me to do?",
											"I've lost my rod...",
											"Can I use your anvil?",
											"Goodbye.");
									stage = 21;
									
								} else if (stage == 21 ) {
									if (componentId == OPTION_1) {
										sendNPCDialogue(
												npcId,
												9827,
												"You need to bring me 100 of each leaping fish, noted. I will give you your next task then.");
								
										stage = 20;
									} else if (componentId == OPTION_2) {
										sendNPCDialogue(
												npcId,
												9827,
												"There is a crate by the fishing spots that contains spare rods and some feathers.");
										stage = 20;
									} else if (componentId == OPTION_3) {
										sendNPCDialogue(
												npcId,
												9827,
												"I will let you use my anvil when you finish a task for me.");
										stage = 20;
										
										
									} else if (componentId == OPTION_4) {
										end();
										
									}
								} else if (stage == 22) {
									sendOptionsDialogue("Otto", 
											"I have the fish!",
											"Goodbye");
									stage = 23;
									
								} else if (stage == 23 ) {
									if (componentId == OPTION_1) {
										sendPlayerDialogue(9827, "I have the fish you asked for.");
										stage = 24;
									} else if (componentId == OPTION_2) {
										end();
									}
								} else if (stage == 24) {
									sendNPCDialogue(
											npcId,
											9827,
											"Thank you! I'll take those now.");
									player.getInventory().deleteItem(11329, 100);
			                        player.getInventory().deleteItem(11331, 100);
			                        player.getInventory().deleteItem(11333, 100);
									stage = 25;
									player.barCrawl = 4;
									
							} else if (stage == 25) {
								sendOptionsDialogue("What should Otto Teach you?", 
										"Barbarian Pyre Shipping",
										"Barbarian Smithing",
										"Barbarian FireMaking",
										"Barbarian Herblore");
								stage = 26;
								
							} else if (stage == 26 ) {
								if (componentId == OPTION_1) {
									sendNPCDialogue(
											npcId,
											9827,
											"Barbarian Pyre Shipping eh? It's a sacred art that has been passed down through generations.");
							
									stage = 27;
								} else if (componentId == OPTION_2) {
									sendNPCDialogue(
											npcId,
											9827,
											"You aren't ready to learn this skill yet.");
									stage = 25;
								} else if (componentId == OPTION_3) {
									sendNPCDialogue(
											npcId,
											9827,
											"You aren't ready to learn this skill yet.");
									stage = 25;
								} else if (componentId == OPTION_4) {
									sendNPCDialogue(
											npcId,
											9827,
											"You aren't ready to learn this skill yet.");
									stage = 25;
									
								}
							} else if (stage == 27) {
								sendNPCDialogue(
										npcId,
										9827,
										"Basically, you have to kill Mithril dragons in the ancient cavern which is just north-east of here in a whirlpool. Once you have chewed bones, you may use them on a pyre site.");
								stage = 28;
								
						} else if (stage == 28) {
							sendNPCDialogue(
									npcId,
									9827,
									"To do this, you will need a hatchet, some logs and a tinderbox. I will let you go into the cavern but be careful! You must provide your own Anti-Dragon shield. Bring me some chewed bones and I'll gve you your next task.");
							
							player.barCrawl = 5;
							stage = 19;
					} else if (stage == 29) {
						sendOptionsDialogue("Otto", 
								"What do I have to do again?",
								"Good bye.");
						stage = 30;
						
					} else if (stage == 30) {
						if (componentId == OPTION_1) {
							sendNPCDialogue(
									npcId,
									9827,
									"You must go in the ancient cavern and kill Mithril dragons until you obtain chewed bones, bring me some chewed bones and I will give you your next task.");
					
							stage = 29;
						} else if (componentId == OPTION_2) {
							end();
						}
					}
						
						
			
		
	
								}//ends void

							
					
		
					
		
		
	
	@Override
	public void finish() {

	}
}