package com.rs.game.player.dialogues;

import com.rs.game.WorldObject;
import com.rs.game.player.content.construction.Chair;
import com.rs.game.player.content.construction.DiningTable;

/**
 * @author Danny
 */


public class Diningtables extends Dialogue {
	
	WorldObject object;
	int[] boundChuncks;

	public Diningtables() {
	}

	@Override
	public void start() {
		object = (WorldObject) parameters[0];
		boundChuncks = (int[]) parameters[1];
		stage = 1;
		sendOptionsDialogue("Build What?", "Wooden Table", "Oak Table", "Oak Carved Table", "Teak Table", "More");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			DiningTable.CheckWoodenTable(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_2) {
			DiningTable.CheckOakTable(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_3) {
			DiningTable.CheckCarvedOakTable(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_4) {
			DiningTable.CheckTeakTable(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_5) {
			stage = 2;
			sendOptionsDialogue("Build What?", "Teak Carved Table", "Mahogany Table", "Opulent Table", "None");
		}
	 } else if(stage == 2) {
			if(componentId == OPTION_1) {
				DiningTable.CheckCarvedTeakTable(player, object, boundChuncks);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if(componentId == OPTION_2) {
				DiningTable.CheckMahoganyTable(player, object, boundChuncks);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if(componentId == OPTION_3) {
				DiningTable.CheckOpulenttable(player, object, boundChuncks);
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