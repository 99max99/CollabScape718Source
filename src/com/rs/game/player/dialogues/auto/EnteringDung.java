package com.rs.game.player.dialogues.auto;

import com.rs.game.player.actions.Summoning;
import com.rs.game.player.content.pet.Pets;
import com.rs.game.player.controlers.dung.DungLobby;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

/**
 * 
 * @author Jae
 * 
 * Handles leaving the instance.
 *
 */

public class EnteringDung extends Dialogue {
	
	public EnteringDung() {
	}

	@Override
	//compilee
	public void start() {
		stage = 1;
		sendOptionsDialogue("Would you like to enter the lobby?", "Yes please.",
				"No thanks.", "Lobby information.", 
				"How many tokens do I have?");

	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getBank().depositAllEquipment(false);
				player.getBank().depositAllInventory(false);
				if(player.getFamiliar() != null || player.getPet() != null || Summoning.hasPouch(player) || Pets.hasPet(player)) {
					player.getDialogueManager().startDialogue("SimpleNPCMessage", 1, "I SAID NO FAMILIARS OR PETS IN THIS GAME!");
					return;
				}
				player.getControlerManager().startControler(
						"RuneDungLobby", 1);
				player.sm("Dungeoneering has been disabled for a few hours.");
				stage= 2;
			} else if (componentId == OPTION_2) {
					stage = 2;	
			} else if (componentId == OPTION_3) {
				player.getDialogueManager().startDialogue("SimpleMessage", "There are currently " 
			+ DungLobby.getLobby().getPlayerSize() + " players in the lobby.");
				stage = 1;
			} else if (componentId == OPTION_4) {
				player.getDialogueManager().startDialogue("SimpleMessage", "You currently have " 
			+ player.getDungTokens() + " tokens.");
				stage = 1;
			}
		} 
		if (stage == 2) {
			end();
			player.getInterfaceManager().closeChatBoxInterface();
		}
}



	@Override
	public void finish() {

	}

}