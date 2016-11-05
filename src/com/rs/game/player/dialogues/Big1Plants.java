package com.rs.game.player.dialogues;

import com.rs.game.WorldObject;
import com.rs.game.player.content.construction.Plants;

/**
 * @author Danny
 */


public class Big1Plants extends Dialogue {
	
	WorldObject object;
	int[] boundChuncks;

	public Big1Plants() {
	}

	@Override
	public void start() {
		object = (WorldObject) parameters[0];
		boundChuncks = (int[]) parameters[1];
		stage = 1;
		sendOptionsDialogue("Plant What?", "Fern", "Bush", "Tall Plant", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			Plants.CheckBigFern(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_2) {
			Plants.CheckSmallBush(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_3) {
			Plants.CheckTallPlant(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}