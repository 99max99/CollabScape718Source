package com.rs.game.player.dialogues;

import com.rs.game.WorldObject;
import com.rs.game.player.content.construction.BookCase;
import com.rs.game.player.content.construction.FirePlace;

/**
 * @author Danny
 */


public class Bookcases extends Dialogue {
	
	WorldObject object;
	int[] boundChuncks;

	public Bookcases() {
	}

	@Override
	public void start() {
		object = (WorldObject) parameters[0];
		boundChuncks = (int[]) parameters[1];
		stage = 1;
		sendOptionsDialogue("Build What?", "Wooden Bookcase", "Oak Bookcase", "Mahogany Bookcase", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			BookCase.CheckWoodenBookCase(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_2) {
			BookCase.CheckOakBookCase(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		} else if(componentId == OPTION_3) {
			BookCase.CheckMahoganyBookCase(player, object, boundChuncks);
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}