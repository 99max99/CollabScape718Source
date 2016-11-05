package com.rs.game.player.dialogues;

public class WeaponsRack extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Select an Item", "Boxing Gloves (Red)",
				"Boxing Gloves (Blue)", "Wooden Sword", "Wooden Shield", "Pugal Stick");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7671, 1);
				player.sm("You take the boxing gloves the shelf.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7673, 1);
				player.sm("You take the boxing gloves from the shelf.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7675, 1);
				player.sm("You take the wooden sword from the shelf.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7676, 1);
				player.sm("You take the wooden shield from the shelf.");
		  } else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(7679, 1);
				player.sm("You take the pugel stick from the shelf.");
		  }
		}
	}

	@Override
	public void finish() {

	}

}
