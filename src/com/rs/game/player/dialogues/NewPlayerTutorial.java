package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;

/**
 * 
 * @author Jordan (Plato)
 *
 */

public class NewPlayerTutorial extends Dialogue {
	
	/**
	 * Starts The Tutorial.
	 */
	
	@Override
	public void start() {
		player.getPackets().sendConfig(1021, 2); //Flashing Icon
		player.getInterfaceManager().closeCombatStyles(); //Disables Combat Tab
		player.getInterfaceManager().closeSkills(); //Disables Skills Tab
		player.getInterfaceManager().closePlayerCustom(); //Disables Quest Tab
		player.getInterfaceManager().closeInventory(); //Disables Inventory Tab
		player.getInterfaceManager().closeEquipment(); //Disables Equipment Tab
		player.getInterfaceManager().closePrayerBook(); //Disables Prayer Tab
		player.getInterfaceManager().closeMagicBook(); //Disables Magic Tab
		player.getInterfaceManager().closeEmotes(); //Disables Emotes Tab
		player.getInterfaceManager().closeSqueal(); //Disables Squeal Tab
		player.getInterfaceManager().closeSettings(); //Disables Settings Tab
		player.getHintIconsManager().removeUnsavedHintIcon();
		
		stage = 1;
		sendDialogue("Guthix Priest",
						"Welcome to Noszscape, " + player.getUsername() + ".",
						"Please click the flashing tab, then click continue.");
		stage = 2;
	}
	
	/**
	 * Second Stage of Tutorial.
	 */

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 2) {
			player.getPackets().sendConfig(1021, 0);
			sendDialogue("This is the Player Support Tab.",
						 "Here, you can sumbit help tickets and report bugs",
						 "to help improve your game experience and Noszscape.");
			stage = 3;
		}
		
		/**
		 * Third Stage of Tutorial.
		 */
		
		else if (stage == 3) {
			sendDialogue("Be sure you only submit a ticket when you",
						 "are in need of any type of help.");
			stage = 4;
		}
		
		/**
		 * Fourth Stage of Tutorial.
		 */
		
		else if (stage == 4) {
			player.getInterfaceManager().sendPlayerCustom();
			player.getPackets().sendConfig(1021, 4);
			sendDialogue("Now, please click on the flashing tab",
						 "then click continue.");
			stage = 5;
		}
		
		/**
		 * Fifth Stage of Tutorial.
		 */
		
		else if (stage == 5) {
			player.getPackets().sendConfig(1021, 0);
			sendDialogue("This is the Player User Panel.",
						 "This is used for character preferences such as",
						 "looks, gender, and other available options.");
			stage = 6;
		}
		
		/**
		 * Sixth Stage of Tutorial.
		 */
		
		else if (stage == 6) {
			sendDialogue("Please use any of the available options",
						 "to your advantage. They are there for your own use.");
			stage = 12;
		}
		
		/**
		 * The Seventh Stage of Tutorial.
		 */
		
		else if (stage == 7) {
			player.getHintIconsManager().addHintIcon(1345, 5190, 0, 100, 0, 0,
					-1, false);
			sendDialogue("Now, please turn the world using your arrow keys",
						 "toward the flashing arrow. This is where the shops",
						 "are located. Use shops to buy and sell your items.");
			stage = 8;
		}
		
		/**
		 * The Eighth Stage of Tutorial.
		 */
		
		else if (stage == 8) {
			player.getHintIconsManager().removeUnsavedHintIcon();
			player.getHintIconsManager().addHintIcon(2982, 3296, 0, 100, 0, 0,
					-1, false);
			sendDialogue("Here are the Bank Booths. Use these to store your",
						 "items for keep. Nobody will ever be able to have access",
						 "to your bank. Please use these to your advantage.");
			stage = 12;
		}
		
		/**
		 * The Ninth Stage of Tutorial.
		 */
		
		else if (stage == 9) {
			sendDialogue("Noszscape wishes the best of luck on your adventure",
						 "and hope you are prepared for the world that you have yet",
						 "to discover. Updates are added every single day!");
			stage = 10;
		}
		
		/**
		 * The Tenth Stage of Tutorial.
		 */
		
		else if (stage == 10) {
			player.getInterfaceManager().sendInterfaces();
			player.closeInterfaces();
			player.starterstage = 2;
			player.getDialogueManager().startDialogue("StarterClass");
		}
		
		/**
		 * The Eleventh Stage of Tutorial.
		 */
		
		else if (stage == 11) {
			player.unlock();
			player.getInterfaceManager().sendInterfaces();
			player.closeInterfaces();
			player.welcomeInterface();
			}
		
		else if (stage == 12) {
			player.getHintIconsManager().removeUnsavedHintIcon();
			sendDialogue("Need access to new items? Go venture to the shops!",
						 "To access this area, use the command ::shops",
						 "We can add more to your request.");
			stage = 13;
		}
		else if (stage == 13) {
			sendDialogue("To start off making money, I suggest you start",
						 "thieving, where you can loot many items",
						 "ranging from crap to valuable.");
			stage = 14;
		}
		else if (stage == 14) {
			sendDialogue("You can sell any of your loots from skilling,",
						 "pvm, pking, or anything to the general store.",
						 "This is how you make your money.");
			stage = 15;
		}
		else if (stage == 15) {
			sendDialogue("To begin training, it is suggested to go",
						 "to the Rock Crabs. To get around Noszscape",
						 "simply use your Crystal Teleport.");
			stage = 9;
		}
		}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
