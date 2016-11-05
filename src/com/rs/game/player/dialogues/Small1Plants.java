package com.rs.game.player.dialogues;

import com.rs.game.WorldObject;
import com.rs.game.player.content.construction.Plants;

/**
 * @author Danny
 */


public class Small1Plants extends Dialogue {
	
	WorldObject object;
	int[] boundChuncks;

	public Small1Plants() {
	}

	@Override
	public void start() {
		object = (WorldObject) parameters[0];
		boundChuncks = (int[]) parameters[1];
		stage = 1;
		sendOptionsDialogue("Plant What?", "Plant", "Small Fern", "Fern", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			Plants.CheckPlant(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_2) {
			Plants.CheckSmallFern(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_3) {
			Plants.CheckFern(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}