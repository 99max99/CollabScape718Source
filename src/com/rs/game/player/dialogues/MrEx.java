package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class MrEx extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		sendOptionsDialogue("Shop Catagories",
				"General Store",
				"Ecto Tokens",
				"Pvp Store",
				"Loyalty",
				"Next Page");
		stage = 0;
	}
	

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 0) {		
			if (componentId == OPTION_1) { 
				ShopsHandler.openShop(player, 55); // general
			} else if (componentId == OPTION_3) { //PK Token Store
				//ShopsHandler.openShop(player, 138);
			} else if (componentId == OPTION_4) {//loyalty
				ShopsHandler.openShop(player, 70);
			} else if (componentId == OPTION_2) {//ecto tokens
				ShopsHandler.openShop(player, 39);
			} else if (componentId == OPTION_5) {//page 2
			sendOptionsDialogue("Shop Catagories",
					"Voting 1",
					"Voting 2",
					"Nevermind");
				stage = 1;
			}
			
			} else if (stage == 1) {
				if (componentId == OPTION_4) { // pet store 1
					ShopsHandler.openShop(player, 71);
				} else if (componentId == OPTION_5) { //pet store 2
					ShopsHandler.openShop(player, 72);
				} else if (componentId == OPTION_1) {//vote 1
					ShopsHandler.openShop(player, 26);
				} else if (componentId == OPTION_2) {//vote 2
					ShopsHandler.openShop(player, 27);
				} else if (componentId == OPTION_5) {
						end();
					}
			}
	}
			@Override
			public void finish() {
			}
	

}
