package com.rs.game.player.dialogues;

public class Shelves extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Select an Item", "Kettle",
				"Tea Pot", "Porcelain Cup", "Beer Glass", "More");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7688, 1);
				player.sm("You take the kettle from the shelf.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7702, 1);
				player.sm("You take the tea pot from the shelf.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7732, 1);
				player.sm("You take the porcelain cup from the shelf.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1919, 1);
				player.sm("You take the beer glass from the shelf.");
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select an Item", "Bowl",
						"Pie Dish", "Empty Pot", "Chef Hat", "None");
				stage = 0;
				}
		}
		if (stage == 0) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1923, 1);
				player.sm("You take the bowl from the shelf.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(2313, 1);
				player.sm("You take the pie dish from the shelf.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1931, 1);
				player.sm("You take the empty pot from the shelf.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1949, 1);
				player.sm("You take the chef hat from the shelf.");
		  } else if (componentId == OPTION_5) {
			  player.getInterfaceManager().closeChatBoxInterface();
				}
		}
	}

	@Override
	public void finish() {

	}

}
