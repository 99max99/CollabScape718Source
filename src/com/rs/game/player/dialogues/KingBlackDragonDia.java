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

public class KingBlackDragonDia extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		sendOptionsDialogue("Pick an option.",
				"Teleport to Kbd.",
				"Start your own private session.",
				"Join an existing session.");
		stage = 0;
	}
	

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 0) {		
			if (componentId == OPTION_1) {  // normal public kbd
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();		
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2273, 4681, 0));
				player.sendMessage("Welcome to the King Black Dragon.");
			} else if (componentId == OPTION_2) { //private session
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().startControler("KingBlackDragon");
			} else if (componentId == OPTION_3) {//join session
				//player.getControlerManager().startControler(key, parameters);
				player.getPackets().sendInputNameScript("Enter the name of your friend.");
				player.getAttributes().put("joining_friend_kbd", true);
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
