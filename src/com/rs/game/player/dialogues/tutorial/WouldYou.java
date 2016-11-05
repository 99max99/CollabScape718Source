package com.rs.game.player.dialogues.tutorial;

import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;


public class WouldYou extends Dialogue {
	
	public WouldYou() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Would you like to go to Noszscape?", "Yes Please", "No Thanks");
	}


	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case 1:
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2852, 2960, 0));
				player.sm("<col=FF0000>Welcome to the Noszscape!");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if(componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
			break;
		 }
			
		}

	@Override
	public void finish() {

	}
}
