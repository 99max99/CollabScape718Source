package com.rs.game.player.dialogues;

import com.rs.game.WorldObject;
import com.rs.game.player.content.construction.Seating;

/**
 * @author Danny
 */


public class Benches extends Dialogue {
	
	WorldObject object;
	int[] boundChuncks;

	public Benches() {
	}

	@Override
	public void start() {
		object = (WorldObject) parameters[0];
		boundChuncks = (int[]) parameters[1];
		stage = 1;
		sendOptionsDialogue("Build What?", "Wooden Bench", "Oak Bench", "Carved Oak Bench", "Teak Bench", "More");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			Seating.CheckWoodenBench(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_2) {
			Seating.CheckOakBench(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_3) {
			Seating.CheckCarvedOakBench(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_4) {
			Seating.CheckTeakDiningBench(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_5) {
			stage = 2;
			sendOptionsDialogue("Build What?", "Carved Teak Bench", "Mahogany Bench", "Guilded Bench", "None");
		}
	 } else if(stage == 2) {
			if(componentId == OPTION_1) {
				Seating.CheckCarvedTeakBench(player, object, boundChuncks);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if(componentId == OPTION_2) {
				Seating.CheckMahoganyBench(player, object, boundChuncks);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if(componentId == OPTION_3) {
				Seating.CheckGildedBench(player, object, boundChuncks);
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