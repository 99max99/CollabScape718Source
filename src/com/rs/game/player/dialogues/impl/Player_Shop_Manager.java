package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since Aug 12, 2013
 */
public class Player_Shop_Manager extends Dialogue {

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9760, "Hello, I am the player shop manager. I handle all interactions with the shops of all players. Now, what would you like?");
	}

	@Override
	public void run(int interfaceId, int option) {
		switch(stage) {
		case -1:
			sendOptionsDialogue("Select an Option", "What are player shops?", "I would like to search...", "I want to manage my shop.");
			stage = 0;
			break;
		case 0:
			switch(option) {
			case OPTION_1:
				sendNPCDialogue(npcId, 9760, "Player shops are shops set up by players themselves. They can add and remove items at any time with me. The player can also set the price of any item in their shop.");
				stage = -1;
				break;
			case OPTION_2:
				sendOptionsDialogue("Select an Option", "Search by item name.", "Search by Store Owner Name.");
				stage = 2;
				break;
			case OPTION_3:
				player.getCustomisedShop().sendOwnShop();
				end();
				break;
			}
			break;
		case 2:
			switch(option) {
			case OPTION_1:
				player.getAttributes().put("searching_item_name", true);
				player.getPackets().sendInputLongTextScript("What item do you want to look for?");
				end();
				break;
			case OPTION_2:
				player.getAttributes().put("searching_shop_playername", true);
				player.getPackets().sendInputNameScript("Whose shop do you want to view?");
				end();
				break;
			}
			break;
		}
	}

	@Override
	public void finish() {

	}

	int npcId;

}
