package com.rs.game.player.dialogues;

import com.rs.game.WorldObject;
import com.rs.game.player.content.construction.Rug;

/**
 * @author Danny
 */


public class Rugs extends Dialogue {
	
	WorldObject object;
	int[] boundChuncks;

	public Rugs() {
	}

	@Override
	public void start() {
		object = (WorldObject) parameters[0];
		boundChuncks = (int[]) parameters[1];
		stage = 1;
		sendOptionsDialogue("Build What?", "Brown Rug", "Regular Rug", "Opelent Rug", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			Rug.CheckBrownRug(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_2) {
			Rug.CheckRug(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_3) {
			Rug.CheckOpulentRug(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}