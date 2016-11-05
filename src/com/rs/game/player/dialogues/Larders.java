package com.rs.game.player.dialogues;

public class Larders extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Select an Item", "Tea Leaves",
				"Milk", "Eggs", "Flour", "More");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7738, 1);
				player.sm("You take the tea leaves from the larder.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1927, 1);
				player.sm("You take the milk from the larder.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1944, 1);
				player.sm("You take the egg from the larder.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1933, 1);
				player.sm("You take the flour from the larder.");
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select an Item", "Potatoes",
						"Garlic", "Onions", "Cheese", "None");
				stage = 0;
				}
		}
		if (stage == 0) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1942, 1);
				player.sm("You take the potatoe from the larder.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1550, 1);
				player.sm("You take the garlic from the larder.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1957, 1);
				player.sm("You take the onion from the larder.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1985, 1);
				player.sm("You take the cheese from the larder.");
		  } else if (componentId == OPTION_5) {
			  player.getInterfaceManager().closeChatBoxInterface();
				}
		}
	}

	@Override
	public void finish() {

	}

}
