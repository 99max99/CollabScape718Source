package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.WorldTile;
import com.rs.game.minigames.CastleWars;
import com.rs.game.player.Skills;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.utils.ShopsHandler;

public class DonatorShops extends Dialogue {

	private int npcId;

	@Override
	public void start() {
	
		sendOptionsDialogue("Donator Shops",
				"Donator Shops", "Exclusive Shops",
				"None");
		stage = 1;

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) { //General Store
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 24);
			} else if (componentId == OPTION_2) {//Combat
				sendOptionsDialogue("Exclusive Shops", "Potion Flasks",
						"Steel Titan");
				stage = 2;
			} else if (componentId == OPTION_3) {//Skilling
				end();
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) { //Flasks
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 10);
			} else if (componentId == OPTION_2) {//Steel titan scrolls
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				ShopsHandler.openShop(player, 22);
			}
		}
			
			
			/**
			 * 
			 */
			
			
		
	}
			@Override
			public void finish() {
			}
	

}