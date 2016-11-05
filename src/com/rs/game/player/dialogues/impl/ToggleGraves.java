package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;

public class ToggleGraves extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Grave Stones", "Turn Gravestones On", "Turn Gravestones Off");
		stage = 0;
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 0) {
			if (componentId == OPTION_1) {
				end();
				player.getPackets().sendGameMessage("Your gravestone is now <col=00ff12>Enabled</col>.");
			} else if (componentId == OPTION_2) {
				end();
				player.getPackets().sendGameMessage("Your gravestone is now <col=ff0000>Disabled</col>.");
			}
		}
	}

	@Override
	public void finish() {

	}

}
