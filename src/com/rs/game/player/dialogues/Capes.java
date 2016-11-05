package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

/**
 * @author Danny
 */


public class Capes extends Dialogue {

	public Capes() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Pick a Shop.", "Skill Capes", "Skill Hoods", "Achievement Capes", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			player.getInterfaceManager().closeChatBoxInterface();
			ShopsHandler.openShop(player, 52);
		} else if(componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			ShopsHandler.openShop(player, 131);
		} else if(componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			ShopsHandler.openShop(player, 132);
		} else if(componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}