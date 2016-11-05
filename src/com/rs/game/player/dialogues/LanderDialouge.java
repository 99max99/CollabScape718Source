package com.rs.game.player.dialogues;

import com.rs.Settings;

public class LanderDialouge extends Dialogue {

	@Override
	public void start() {
		sendDialogue("Are you sure you would like to leave the lander?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Yes, get me out of here!", "No, I want to stay.");
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				int i;
				if (player.isPker)
					i = 1;
				else
					i = 0;
				player.setNextWorldTile(Settings.HOME_PLAYER_LOCATION[i]);
				player.getControlerManager().forceStop();
			}
			end();
		}
	}

	@Override
	public void finish() {

	}

}
