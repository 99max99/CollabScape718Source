package com.rs.game.player.dialogues;

import com.rs.utils.KillStreakRank;
import com.rs.utils.PkRank;


/**
 * @author Danny
 */


public class PkScores extends Dialogue {

	public PkScores() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Chose a HighScore", "Kills-Deaths", "Kill Streaks");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			player.getInterfaceManager().closeChatBoxInterface();
			PkRank.showRanks(player);
		} else if(componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			KillStreakRank.showRanks(player);
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}