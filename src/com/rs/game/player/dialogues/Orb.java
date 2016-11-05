package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

/**
 * @author Danny
 */


public class Orb extends Dialogue {
	

	public Orb() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What would you like to do?", "Save Location (5 Charges)", "Teleport to Location (1 Charge)", "Purchase Charges", "Check Charges", "None");
	}

	@Override
	public void run(int interfaceId, int componentId) {
	 if(stage == 1) {
		if(componentId == OPTION_1) {
			player.getInterfaceManager().closeChatBoxInterface();
			if (player.orbCharges > 5) {
				player.orbCharges -= 5;
				player.orbLocation = new WorldTile(player.getTile());
				player.sm("You have successfully set your location.");
			} else {
				player.sm("You do not have enough charges to teleport to save your location.");
			}
		} else if(componentId == OPTION_2) {
			player.getInterfaceManager().closeChatBoxInterface();
			if (player.orbCharges > 0) {
				if (player.orbLocation == null) {
					player.sm("You currently do not have a location set.");
				} else {
				player.orbCharges--;
				Magic.sendNormalTeleportSpell(player, 0, 0, player.orbLocation);
				player.sm("You teleport to your location.");
				}
			} else {
				player.sm("You do not have enough charges to teleport to this area.");
			}
		} else if(componentId == OPTION_3) {
			player.getInterfaceManager().closeChatBoxInterface();
			if (player.getInventory().containsItem(29980, 250)) {
				player.getInventory().deleteItem(29980, 250);
				player.orbCharges++;
				player.sm("You have successfully purchased a charge.");
			} else {
				player.sm("It costs 250 Loyalty Tokens to purchase a charge.");
			}
		} else if(componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("You currently have "+player.orbCharges+" charges.");
		} else if(componentId == OPTION_5) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}