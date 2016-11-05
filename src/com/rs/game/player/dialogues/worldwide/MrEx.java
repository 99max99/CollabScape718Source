package com.rs.game.player.dialogues.worldwide;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class MrEx extends Dialogue {

	private int npcId;
	private boolean isPker;

	@Override
	public void start() {
		npcId = (int) parameters[0];
		isPker = (boolean) parameters[1];
		if (isPker) {
			sendOptionsDialogue("Shop Catagories", "PvP Token Store",
					"Vote Token Store", "PvP Supply Store");
			stage = 0;
		} else {
			sendOptionsDialogue("Shop Catagories", "General Store",
					"Combat", "Skilling", "Consumables",
					"Next Page");
			stage = 1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 0) {
			if (componentId == OPTION_1) { //PvP Token Store
				ShopsHandler.openShop(player, 138);
			} else if (componentId == OPTION_2) {//Vote Token Store
				ShopsHandler.openShop(player, 139);
			} else if (componentId == OPTION_3) {//Supply Store
				ShopsHandler.openShop(player, 140);
			}
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
	} else if (stage == 1) {
			if (componentId == OPTION_1) { //General Store
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 55);
			} else if (componentId == OPTION_2) {//Combat
				sendOptionsDialogue("Combat Shops", "Melee Equipment Store",
						"Melee Weaponry Store", "Magic Equipment Store",
						"Magic Supplies Store", "Next Page");
				stage = 3;
			} else if (componentId == OPTION_3) {//Skilling
				sendOptionsDialogue("Skilling Shops", "Skilling Supplies Store",
						"Runecrafting Supplies Store", "Elite Crafting Store",
						"Herblore Store", "Next Page");
				stage = 5;
			} else if (componentId == OPTION_4) {//Consumables
				sendOptionsDialogue("Consumables Shops",
						"Potions Store",
						"Food Store", "None");
				stage = 7;
			} else if (componentId == OPTION_5) {//page 2
				sendOptionsDialogue("Shop Catagories",
						"Outfits",
						"Summoning", "Points Stores","Previous Page","None");
				stage = 2;
			}
			
			
			/**
			 * 
			 */
			
			
		} else if (stage == 2) { 
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Outfits Shops",
						"Skiller Outfit Store","None");
				stage = 8;
		} else if (componentId == OPTION_2) {
			sendOptionsDialogue("Summoning Shops",
					"Summoning Store 1","Summoning Store 2","Pet Store 1",
					"Pet Store 2","None");
			stage = 9;
		} else if (componentId == OPTION_3) {
			sendOptionsDialogue("Points Shops",
					"Voting Points Store 1","Voting Points Store 2","Trivia Points Store",
					"Forum Points Store","Next Page");
			stage = 10;
		} else if (componentId == OPTION_4) {
				start();
		} else if (componentId == OPTION_5) {
			end();
	}
			
			
			/**
			 * Combat Shops
			 */
			
			
		} else if (stage == 3) {
			if (componentId == OPTION_1) { 
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 63);
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 64);
		} else if (componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 60);
		} else if (componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 76);
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Combat Shops - Page 2", "Ranging Equipment Store",
					"Ranging Supplies Store", 
					"Ecto Token Store","None");
			stage = 4;
		}
			
			/**
			 * Combat Shop 2
			 */
			
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 61);
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 62);
		} else if (componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 39);
		} else if (componentId == OPTION_4) {
			end();
		}
			
			
			/**
			 * Skilling Shops
			 */
			
			
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 58);
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 59);
		} else if (componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 87);
		} else if (componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 57);
		} else if (componentId == OPTION_5) {
			sendOptionsDialogue("Skilling Shops - Page 2", "Fishing Supplies Store",
					"None");
			stage = 6;
		}
			
		} else if (stage == 6) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 73);
		} else if (componentId == OPTION_2) {
			end();
		}
			
			//consumables
		} else if(stage == 7) {
			if(componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 56);
				
			} else if(componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 14);
				
			} else if(componentId == OPTION_3) {
			end();
				
			}
			//Outfits
		} else if(stage == 8) {
			if(componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 65);
				
			} else if(componentId == OPTION_2) {
			
				end();
			} 


			/**
			 * Summoning Shops
			 */
			
			
		} else if (stage == 9) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 74);
		} else if (componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 75);
		} else if (componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 71);
		} else if (componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().closeOverlay(true);
			ShopsHandler.openShop(player, 72);
		} else if (componentId == OPTION_5) {
			end();
		}
			//Points Shops
	} else if(stage == 10) {
			if(componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 26);
			} else if(componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 27);
		} else if(componentId == OPTION_3) {
			player.getDialogueManager().startDialogue("TriviaRewards");
		}  else if(componentId == OPTION_4) {
			player.getDialogueManager().startDialogue("ForumPoint");
		} else if(componentId == OPTION_5) {
			sendOptionsDialogue("Points Shops - Page 2",
					"Loyalty Points Store", "Reward Points Store","None");
			stage = 11;
		}
	
			//Points shops 2
		} else if(stage == 11) {
			if(componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 70);
			} else if(componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 31);
				//player.getPackets().sendGameMessage("You currently Have: "+player.Rewardpoints+" Reward Points.");
			} else if(componentId == OPTION_3) {
				end();
			}
			
		}
	}
			@Override
			public void finish() {
			}
	

}
