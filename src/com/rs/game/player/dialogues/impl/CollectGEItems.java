package com.rs.game.player.dialogues.impl;

import com.rs.game.player.dialogues.Dialogue;
//import com.rs.game.player.content.GrandExchange.Offer;

public class CollectGEItems extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Select an Option", "Take Items", "Return");
		stage = 0;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 0) {
			/*if (componentId == OPTION_1) {
				Offer offer = (Offer) parameters[0];
				if (offer == null)
					return;

				// TODO need to make this again from scratch.
			} else if (componentId == OPTION_2) {
				end();
			}*/
		}
	}

	@Override
	public void finish() {

	}

}
