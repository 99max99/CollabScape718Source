package com.rs.game.player.dialogues;

import com.rs.game.player.actions.farming.Herbs;


public class HerbSeeds extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What would you like to plant?", "Guam Seeds",
				"Irit Seeds", "Kwuarm Seeds", "Snapdragon Seeds",
				"Next Page");
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				Herbs.CheckGuam(player);
				player.lock(1);
				player.closeInterfaces();
			} else if (componentId == OPTION_2) {
				Herbs.CheckIrit(player);
				player.lock(1);
				player.closeInterfaces();
			} else if (componentId == OPTION_3) {
				Herbs.CheckKwuarm(player);
				player.lock(1);
				player.closeInterfaces();
			} else if (componentId == OPTION_4) {
				Herbs.CheckSnapdragon(player);
				player.lock(1);
				player.closeInterfaces();
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("What would you like to plant?",
						"Dwarf Weed Seeds", "None", "Cancel");
				stage = 2;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				Herbs.CheckDwarfweed(player);
				player.lock(1);
				player.closeInterfaces();
			} else if (componentId == OPTION_2) {
				//Chair.CheckTeakArmchair(player);
				player.lock(1);
				player.closeInterfaces();
			} else if (componentId == OPTION_3) {
				player.closeInterfaces();
			}
		}
	}

	@Override
	public void finish() {
	}
}