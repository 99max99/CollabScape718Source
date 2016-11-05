package com.rs.game.player.dialogues;

public class HouseTeleport extends Dialogue {

	public HouseTeleport() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Teleport to your house?", "Yes",
				"No");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getControlerManager().startControler("HouseControler");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	}

@Override
public void finish() {
	}
}