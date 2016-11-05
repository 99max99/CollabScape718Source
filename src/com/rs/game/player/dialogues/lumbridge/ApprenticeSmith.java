package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Misc;

/**
 *@Author Danny
 */

public class ApprenticeSmith extends Dialogue {

	private int npcId;
	public int taskItems[] = {2350, 2352, 2354, 2356, 2358, 437, 439, 441, 443, 445, 453};
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "Hello there!");
	}
	
	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			if (player.smithTask && !player.getInventory().containsItem(player.smithId, player.smithAmount)) {
			stage = 5;
			sendPlayerDialogue(9827, "Hello, what do I need to get again?");
			break;
			} else if (player.smithTask && player.getInventory().containsItem(player.smithId, player.smithAmount)) {
				stage = 7;
				sendNPCDialogue(npcId, 9827, "Ah marvelous, you have my items!");
				break;
			} else if (player.quickWork && !player.smithTask) {
				stage = 3;
				sendNPCDialogue(npcId, 9827, "I have a task for you!");
				player.quickWork = false;
				break;
			} else if (player.quickWork && player.smithTask) {
				sendNPCDialogue(npcId, 9827, "Silly boy, you already have a task!");
				player.quickWork = false;
				stage = 9;
				break;
			} else {
				stage = 0;
			sendPlayerDialogue(9827, "Hello, what are you doing here?");
			break;
			}
		case 0:
				sendNPCDialogue(npcId, 9827, "I am currently an intern at this Smith House, can you do me a favor?");
				stage = 1;
				break;
		case 1:
			stage = 2;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes", 
					"No");
			break;
		case 2:
			if(componentId == OPTION_1) {
				sendNPCDialogue(npcId, 9827, "Okay, now here is your task.");
				stage = 3;
				break;
			} else if(componentId == OPTION_2) {
				sendNPCDialogue(npcId, 9827, "Well then, you are missing out on an opportunity to earn some money.");
				stage = 9;
				break;
			}
		case 3:
			stage = 4;
			player.smithTask = true;
			player.smithAmount = Misc.random(150, 400);
			player.smithId = taskItems[Misc.random(0, 10)];
			Item item = new Item(player.smithId, 1);
			sendNPCDialogue(npcId, 9827, "Can you bring me " + player.smithAmount + " of the item " + item.getName() + ".",
					"When you get these items, please return them to me!");
			break;
		case 4:
			sendPlayerDialogue(9827, "Sure, I will get onto it now!");
			stage = 9;
			break;
		case 5:
			stage = 6;
			Item task = new Item(player.smithId, 1);
			sendNPCDialogue(npcId, 9827, "I asked if you could bring me " + player.smithAmount + " of the item " + task.getName() + ".");
			break;
		case 6:
			sendPlayerDialogue(9827, "Okay, thanks for reminding me!");
			stage = 9;
			break;
		case 7:
			stage = 8;
			player.getInventory().deleteItem(player.smithId, player.smithAmount);
			int amount = Misc.random(2000, 5000);
			player.getInventory().addItem(4278, amount);
			player.smithTask = false;
			sendNPCDialogue(npcId, 9827, "Thank you for your help, take these ecto tokens as a reward.");
			break;
		case 8:
			sendPlayerDialogue(9827, "Thanks a lot!");
			stage = 9;
			break;
		case 9:
			end();
			break;
		}
	}

	@Override
	public void finish() {
		
	}
}