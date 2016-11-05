package com.rs.game.player.dialogues.worldwide;

import com.rs.game.player.dialogues.Dialogue;

public class SwapSpellBook extends Dialogue {

	@Override
	public void start() {
		sendOptionsDialogue("Change spellbooks",
				"Normal", "Ancients", "Lunar");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
		if (componentId == OPTION_1) {
			
				sendDialogue("Swapped to normal Spellbook");
				player.getCombatDefinitions().setSpellBook(0);
			
				return;
		}	else if (componentId == OPTION_2) {
			
			sendDialogue("Swapped to Ancient Spellbook");
			player.getCombatDefinitions().setSpellBook(1);
		
			
			}	else if (componentId == OPTION_3) {
				
				sendDialogue("Swapped to Lunar Spellbook");
				player.getCombatDefinitions().setSpellBook(2);
			
				
				}
	}
		
			
}

	@Override
	public void finish() {

	}

}
