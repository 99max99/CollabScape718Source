package com.rs.game.player.dialogues;

public class Nulodion extends Dialogue {

	int npcId = 209;

	@Override
	public void start() {
		if (player.completedRailingTask == true && player.spokeToNu == false) {
			sendPlayerDialogue(9827, "Hello! You must be Nulodion, Captain Lawgof has sent me here to collect his ammo mould and Nulodion's notes.");
		} else {
		player.out("Nothing intresting happens.");
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 7;
			
		if (stage == 7) {
			player.getInventory().addItem(4, 1);
			player.getInventory().addItem(3, 1);
			player.out("Nulodion hands you the items, I should now return to Captain Lawgof.");
			player.spokeToNu = true;
				end();
			}
			end();
		}
	}

	@Override
	public void finish() {

	}

}
