package com.rs.game.player.dialogues.tutorial;

import com.rs.game.player.dialogues.Dialogue;


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
		switch(stage) {
		case 2:
			player.getPackets().sendConfig(1021, 0);
			sendDialogue("This is the Player Support Tab.",
						 "Here, you can sumbit help tickets and report bugs",
						 "to help improve your game experience and Noszscape.");
			stage = 3;
		break;
		
		/**
		 * Third Stage of Tutorial.
		 */
		
		case 3:
			sendDialogue("Be sure you only submit a ticket when you",
						 "are in need of any type of help.");
			stage = 4;
		break;
		
		/**
		 * Fourth Stage of Tutorial.
		 */
		
		case 4:
			player.getInterfaceManager().sendPlayerCustom();
			player.getPackets().sendConfig(1021, 4);
			sendDialogue("Now, please click on the flashing tab",
						 "then click continue.");
			stage = 5;
		break;
		
		/**
		 * Fifth Stage of Tutorial.
		 */
		
		case 5:
			player.getPackets().sendConfig(1021, 0);
			sendDialogue("This is the Player User Panel.",
						 "This is used for character preferences such as",
						 "looks, gender, and other available options.");
			stage = 6;
		break;
		
		/**
		 * Sixth Stage of Tutorial.
		 */
		
		case 6:
			sendDialogue("Please use any of the available options",
						 "to your advantage. They are there for your own use.");
			stage = 12;
		break;
		
		/**
		 * The Seventh Stage of Tutorial.
		 */
		
		case 7:
			player.getHintIconsManager().addHintIcon(1345, 5190, 0, 100, 0, 0,
					-1, false);
			sendDialogue("Now, please turn the world using your arrow keys",
						 "toward the flashing arrow. This is where the shops",
						 "are located. Use shops to buy and sell your items.");
			stage = 8;
		break;
		
		/**
		 * The Eighth Stage of Tutorial.
		 */
		
		case 8:
			player.getHintIconsManager().removeUnsavedHintIcon();
			player.getHintIconsManager().addHintIcon(2982, 3296, 0, 100, 0, 0,
					-1, false);
			sendDialogue("Here are the Bank Booths. Use these to store your",
						 "items for keep. Nobody will ever be able to have access",
						 "to your bank. Please use these to your advantage.");
			stage = 12;
		break;
		
		/**
		 * The Ninth Stage of Tutorial.
		 */
		
		case 9:
			sendDialogue("Noszscape wishes the best of luck on your adventure",
						 "and hope you are prepared for the world that you have yet",
						 "to discover. Updates are added every single day!");
			stage = 10;
		break;
		
		/**
		 * The Tenth Stage of Tutorial.
		 */
		
		case 10:
			player.getInterfaceManager().sendInterfaces();
			player.closeInterfaces();
			player.starterstage = 2;
			stage = 16;
			player.getDialogueManager().startDialogue("StarterClass");
		break;
		
		/**
		 * The Eleventh Stage of Tutorial.
		 */
		
		case 11:
			player.unlock();
			player.getInterfaceManager().sendInterfaces();
			player.closeInterfaces();
			player.welcomeInterface();
		break;
		
		case 12:
			player.getHintIconsManager().removeUnsavedHintIcon();
			sendDialogue("Need access to new items? Go venture to the shops!",
						 "To access this area, use the command ::shops",
						 "We can add more to your request.");
			stage = 13;
		break;
		
		case 13:
			sendDialogue("To start off making money, I suggest you start",
						 "thieving, where you can loot many items",
						 "ranging from crap to valuable.");
			stage = 14;
		break;
		
		case 14:
			sendDialogue("You can sell any of your loots from skilling,",
						 "pvm, pking, or anything to the general store.",
						 "This is how you make your money.");
			stage = 15;
		break;
		
		case 15:
			sendDialogue("To begin training, it is suggested to go",
						 "to the Rock Crabs. To get around Noszscape",
						 "simply use your Crystal Teleport.");
			stage = 9;
		break;
		
		case 16:
			end();
		break;
		}
		}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
