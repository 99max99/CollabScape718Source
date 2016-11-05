package com.rs.game.player.dialogues;

import com.rs.game.WorldObject;
import com.rs.game.player.content.construction.Chair;

/**
 * @author Danny
 */


public class Chairs extends Dialogue {
	
	WorldObject object;
	int[] boundChuncks;

	public Chairs() {
	}

	@Override
	public void start() {
		object = (WorldObject) parameters[0];
		boundChuncks = (int[]) parameters[1];
		stage = 1;
		sendOptionsDialogue("Build What?", "Crude Chair", "Wooden Chair", "Rocking Chair", "Oak Chair", "More");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			Chair.CheckCrudeChair(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_2) {
			Chair.CheckWoodenChair(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_3) {
			Chair.CheckRockingChair(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_4) {
			Chair.CheckOakChair(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_5) {
			stage = 2;
			sendOptionsDialogue("Build What?", "Oak Armchair", "Teak Armchair", "Mahogany Armchair", "None");
		}
	 } else if(stage == 2) {
			if(componentId == OPTION_1) {
				Chair.CheckOakArmchair(player, object, boundChuncks);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if(componentId == OPTION_2) {
				Chair.CheckTeakArmchair(player, object, boundChuncks);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if(componentId == OPTION_3) {
				Chair.CheckMahoganyArmchair(player, object, boundChuncks);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if(componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		 }
		
	}

	@Override
	public void finish() {
		
	}
	
}