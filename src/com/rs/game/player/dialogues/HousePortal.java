package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

/**
 * @author Danny
 */


public class HousePortal extends Dialogue {
	

	public HousePortal() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What would you like to do?", "Enter Your House", "Enter Friend's House", "Construction Shop", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			if (player.hasHouse) {
			player.getHouse().enterMyHouse();
			player.getInterfaceManager().closeChatBoxInterface();
			} else {
			player.sm("You must puchase a house from the Estate Agent first!");
			player.getInterfaceManager().closeChatBoxInterface();
			}
		} else if(componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getPackets().sendInputNameScript("Enter the name of your friend.");
			player.getAttributes().put("joining_house", true);
			player.sm("This feature is temporarily disabled.");
		} else if(componentId == OPTION_3) {
			ShopsHandler.openShop(player, 83);
		} else if(componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}