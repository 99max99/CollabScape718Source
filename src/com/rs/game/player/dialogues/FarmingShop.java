package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

/**
 * @author Danny
 */


public class FarmingShop extends Dialogue {

	public FarmingShop() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Farming Shops", "Farming Tools", "Fruit/Wood Trees", "Allotment/Herb/Flower", "Hop/Bush/Misc", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			ShopsHandler.openShop(player, 133);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_2) {
			ShopsHandler.openShop(player, 134);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_3) {
			ShopsHandler.openShop(player, 135);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_4) {
			ShopsHandler.openShop(player, 136);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_5) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}