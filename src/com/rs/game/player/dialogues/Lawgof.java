package com.rs.game.player.dialogues;

public class Lawgof extends Dialogue {

	int npcId = 208;
	
	public void completeQuest() {
		if (player.spokeToNu = true && player.fixedRailings == 6 && player.completedDwarfCannonQuest == false && player.getInventory().containsItem(3, 1) && player.getInventory().containsItem(4, 1)) {
			sendPlayerDialogue(9827, "I have the Ammo mould and Notes, Here you go.");
			player.getInventory().deleteItem(3, 1);
			player.completedDwarfCannonQuest = true;
			player.questPoints++;
			player.out("Thank you Young man! You will now be able to use the Dwarf Multi Cannon! Congratulations. For more information read the quest guide for Dwarf Cannon.");
			player.getInterfaceManager().sendInterface(1244);
			player.getPackets().sendIComponentText(1244, 25, "You have completed the Dwarf Cannon Quest!");
			player.getPackets().sendIComponentText(1244, 26, "You are awarded with the ability to use the dwarf cannon.");
			player.getPackets().sendIComponentText(1244, 27, "Quest Points <col=FF0000>"+player.questPoints);
			stage = 8;
		}
	}
	
	public void completedRailings() {
			sendNPCDialogue(npcId, 9827, "Whoa! Thank you very much! I will need one more thing, A dwarf named 'Nulodion' has my Ammo mould and Nulodion's notes, can you go get them then bring them back to me. Thank you.");
		player.completedRailingTask = true;
		stage = 7;
	}

	@Override
	public void start() {
		if (player.fixedRailings >= 6 && player.spokeToNu == false) {
			completedRailings();
		} else if (player.spokeToNu = true && player.fixedRailings >= 6 && player.completedDwarfCannonQuest == false && player.getInventory().containsItem(3, 1) && player.getInventory().containsItem(4, 1)) {
			completeQuest();
		} else {
				sendNPCDialogue(npcId, 9827, "Hello sir! Would you be able to help fix my fence? I have counted that "+ player.fixedRailings +"/6 of the railings are fixed, Can you please fix them? I'm sure we can work out a deal after." );
				}
		if (player.completedDwarfCannonQuest == true) {
			player.out("You have already completed this quest.");
			end();
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue("Would you like to help Captain Lawgof?",
					"Sure thing, hand me the supplies I will need to fix the fence.",
					"No thank you, I'm far to busy.");

		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				if (player.getInventory().getFreeSlots() > 2) {
					sendNPCDialogue(npcId, 9827, "Thank you so much, you will not regret this! Here's the supplies.");
					player.getInventory().addItem(14, 6);
					player.getInventory().addItem(2347, 1);
					player.out("I should fix all the broken fences.");
				} else {
					sendNPCDialogue(npcId, 9827, "It seems like you dont have enough inventory space, you will need 2 free slots..");
				}
				end();
			} else if (componentId == OPTION_2) {
				end();
			} else if (stage == 7) {
				end();
			} else if (stage == 8) {
				sendNPCDialogue(npcId, 9827, "Thank you Young man! You will now be able to use the Dwarf Multi Cannon! Congratulations.");
				player.getInventory().deleteItem(3, 1);
				player.completedDwarfCannonQuest = true;
			}
			end();
		}
	}

	@Override
	public void finish() {

	}

}
