package com.rs.game.player.dialogues.christmas;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

/**
 *@Author Danny
 */

public class JackFrost extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Happy holidays! What would you like?");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Teleport me to the snow realm.", 
					"Show me the rewards shop.", "Nevermind.");
			break;
		case 0:
			if(componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2719, 5729, 0));
				player.sm("Welcome to the Snow Realm.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().closeOverlay(true);
				player.getControlerManager().forceStop();
				player.getControlerManager().removeControlerWithoutCheck();
				stage = 3;
				break;
			} else if(componentId == OPTION_2) {
				stage = 3;
				ShopsHandler.openShop(player, 97);
				break;
			} else if(componentId == OPTION_3) {
				stage = 3;
				break;
			} 
		case 3:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}