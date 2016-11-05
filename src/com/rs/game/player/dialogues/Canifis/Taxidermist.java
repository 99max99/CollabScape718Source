package com.rs.game.player.dialogues.Canifis;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

public class Taxidermist extends Dialogue {

	private int npcId = 4246;
	@Override
	public void start() {
		
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name, "Hello there! I can taxidermise"
								+ "Big Fish and Monster heads to hang on your wall if you choose!"
								+ "Would you like to use my services?" },
						IS_NPC, npcId, 9827);
			stage = -1;
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("SELECT AN OPTION", 
					"Yes",
					"No thank you");
			stage = 1;
			} else if (stage == -2) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "You don't have the right item for this..." },
								IS_NPC, npcId, 9827);
				stage = 2;
			} else if (stage == -3) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "You don't have enough coins to get this taxidermised." },
								IS_NPC, npcId, 9827);
				stage = 2;
			} else if (stage == -4) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								NPCDefinitions.getNPCDefinitions(npcId).name, "Enjoy!" },
								IS_NPC, npcId, 9827);
				stage = -5;
			} else if (stage == 1) {
				if (componentId == OPTION_1) {
					sendPlayerDialogue(9827, "Yes please.");
					stage = 2;
				} else if (componentId == OPTION_2) {
					end();
				
					
				}
			} else if (stage == 2) {
				sendOptionsDialogue("SELECT AN OPTION", 
						"Fish",
						"Monster heads");
				stage = 3;
			} else if (stage == 3) {
				if (componentId == OPTION_1) {
					sendOptionsDialogue("SELECT AN OPTION", 
							"Big Bass - 1,000",
							"Big Swordfish - 2,500",
							"Big Shark - 5,000",
							"None");
					stage = 4;
				} else if (componentId == OPTION_2) {
					sendOptionsDialogue("SELECT AN OPTION", 
							"Crawling Hand - 1,000",
							"Cockatrice head - 2,000",
							"Basilisk Head - 4,000",
							"Kurask Head - 6,000",
							"More");
					stage = 5;
					
				}
			} else if (stage == 4) {
				if (componentId == OPTION_1) {
					if (player.getInventory().containsItem(7989, 1)) {
						if (player.getInventory().getCoinsAmount() >= 1000) {
							player.getInventory().deleteItem(7989, 1);
							player.getInventory().removeItemMoneyPouch(new Item(995, 1000));
							player.getInventory().addItem(7990, 1);
							stage = -4;
						} else {
							stage = -3;
						}
					} else {
						stage = -2;
					}
				} else if (componentId == OPTION_2) {
					if (player.getInventory().containsItem(7991, 1)) {
						if (player.getInventory().getCoinsAmount() >= 2500) {
							player.getInventory().deleteItem(7991, 1);
							player.getInventory().removeItemMoneyPouch(new Item(995, 2500));
							player.getInventory().addItem(7992, 1);
							stage = -4;
						} else {
							stage = -3;
						}
					} else {
						stage = -2;
					}
				} else if (componentId == OPTION_3) {
					if (player.getInventory().containsItem(7993, 1)) {
						if (player.getInventory().getCoinsAmount() >= 5000) {
							player.getInventory().deleteItem(7993, 1);
							player.getInventory().removeItemMoneyPouch(new Item(995, 5000));
							player.getInventory().addItem(7994, 1);
							stage = -4;
						} else {
							stage = -3;
						}
					} else {
						stage = -2;
					}
				} else if (componentId == OPTION_4) {
					end();
				}
			} else if (stage == 5) {
				if (componentId == OPTION_1) {
					if (player.getInventory().containsItem(7975, 1)) {
						if (player.getInventory().getCoinsAmount() >= 1000) {
							player.getInventory().deleteItem(7975, 1);
							player.getInventory().removeItemMoneyPouch(new Item(995, 1000));
							player.getInventory().addItem(7982, 1);
							stage = -4;
						} else {
							stage = -3;
						}
					} else {
						stage = -2;
					}
				} else if (componentId == OPTION_2) {
					
						if (player.getInventory().containsItem(7976, 1)) {
							if (player.getInventory().getCoinsAmount() >= 2000) {
								player.getInventory().deleteItem(7976, 1);
								player.getInventory().removeItemMoneyPouch(new Item(995, 2000));
								player.getInventory().addItem(7983, 1);
								stage = -4;
							} else {
								stage = -3;
							}
						} else {
							stage = -2;
						}
				} else if (componentId == OPTION_3) {
					if (player.getInventory().containsItem(7977, 1)) {
						if (player.getInventory().getCoinsAmount() >= 4000) {
							player.getInventory().deleteItem(7977, 1);
							player.getInventory().removeItemMoneyPouch(new Item(995, 4000));
							player.getInventory().addItem(7984, 1);
							stage = -4;
						} else {
							stage = -3;
						}
					} else {
						stage = -2;
					}
				} else if (componentId == OPTION_4) {
					if (player.getInventory().containsItem(7978, 1)) {
						if (player.getInventory().getCoinsAmount() >= 6000) {
							player.getInventory().deleteItem(7978, 1);
							player.getInventory().removeItemMoneyPouch(new Item(995, 6000));
							player.getInventory().addItem(7985, 1);
							stage = -4;
						} else {
							stage = -3;
						}
					} else {
						stage = -2;
					}
				} else if (componentId == OPTION_5) {
					sendOptionsDialogue("SELECT AN OPTION", 
							"Abyssal Demon Head - 12,000",
							"King Black Dragon Head - 50,000",
							"Kalphite Queen Head - 50,000",
							"None");
					stage = 6;
				}
			} else if (stage == 6) {
				if (componentId == OPTION_1) {
					if (player.getInventory().containsItem(7979, 1)) {
						if (player.getInventory().getCoinsAmount() >= 12000) {
							player.getInventory().deleteItem(7979, 1);
							player.getInventory().removeItemMoneyPouch(new Item(995, 12000));
							player.getInventory().addItem(7986, 1);
							stage = -4;
						} else {
							stage = -3;
						}
					} else {
						stage = -2;
					}
				} else if (componentId == OPTION_2) {
					
						if (player.getInventory().containsItem(7980, 1)) {
							if (player.getInventory().getCoinsAmount() >= 50000) {
								player.getInventory().deleteItem(7980, 1);
								player.getInventory().removeItemMoneyPouch(new Item(995, 50000));
								player.getInventory().addItem(7987, 1);
								stage = -4;
							} else {
								stage = -3;
							}
						} else {
							stage = -2;
						}
				} else if (componentId == OPTION_3) {
					if (player.getInventory().containsItem(7981, 1)) {
						if (player.getInventory().getCoinsAmount() >= 50000) {
							player.getInventory().deleteItem(7981, 1);
							player.getInventory().removeItemMoneyPouch(new Item(995, 50000));
							player.getInventory().addItem(7988, 1);
							stage = -4;
						} else {
							stage = -3;
						}
					} else {
						stage = -2;
					}
				} else if (componentId == OPTION_4) {
					end();
				}
			}
		}
		
		 
	

	@Override
	public void finish() {

	}
}