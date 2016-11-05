package com.rs.game.player.dialogues;


import com.rs.game.player.actions.SlayerPortal;

public class SummonSlayerportal extends Dialogue {

	@Override
	public void start() {

		sendDialogue("Would you like to be summoned to you slayer task?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;

			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Yes, I need to be there ASAP!", "No thanks.");
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				//player.getInventory().deleteItem(995, 100000);
				//player.getMoneyPouch().getCoinsAmount();
				SlayerPortal.HandleObject(player,  null);
				end();
			} else if (componentId == OPTION_2) {
				sendDialogue("Okay let me know where you are ready!.");
				end();
				} 
					
			} else if(stage == 2) {
			end();
		}
	}

	@Override
	public void finish() {

	}

}