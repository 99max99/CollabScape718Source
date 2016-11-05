package com.rs.game.player.content.farming;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;

public class CompostBin {

	public static int weeds;
	static boolean full;
	public static boolean supercompost;

	public static void wrongItems(Player player) {
		if (supercompost == false && weeds < 20) {
		player.getDialogueManager().startDialogue("SimpleMessage", "You can only empty weeds into the compost bin.");
		return;
		}
		if (supercompost == false && weeds == 20) {
		player.getDialogueManager().startDialogue("SimpleMessage", "You need to use a bucket on the bin the gather the compost.");
		return;
		}
		if (supercompost == true && weeds == 20) {
		player.getDialogueManager().startDialogue("SimpleMessage", "You need to use a bucket on the bin the gather the super compost.");
		return;
		}
	}
	
	public static void checkBin(Player player) {
		if (weeds == 20 && supercompost == true) {
			player.getDialogueManager().startDialogue("SimpleMessage", "The compost bin is full, use a bucket on the bin to collect the super compost.");
			return;
		}
		if (weeds == 20 && supercompost == false) {
			player.getDialogueManager().startDialogue("SimpleMessage", "The compost bin is full, either add super compost to it or use a bucket on the bin.");
			return;
		}
		if (weeds < 20) {
			player.getDialogueManager().startDialogue("SimpleMessage", "There are " + weeds + "/20 weeds in the compost bin.");
			return;
		}
	}

	public static void handleSuperCompost(Player player, Item item) {
		if (weeds < 20) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You have to have a full bin before you can add that.");
			return;
		}
		if (weeds == 20 && supercompost == true) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You have already added your compost potion to the bin.");
			return;
		}
		if (weeds == 20 && item.getId() == 6470 && supercompost == false) {
			player.getPackets().sendConfigByFile(741, 30);
			player.getInventory().deleteItem(6470, 1);
			player.getInventory().addItem(6472, 1);
			supercompost = true;
			player.getDialogueManager().startDialogue("SimpleMessage", "You add the compost potion and make super compost.");
			return;
		}
		if (weeds == 20 && item.getId() == 6472 && supercompost == false) {
			player.getPackets().sendConfigByFile(741, 30);
			player.getInventory().deleteItem(6472, 1);
			player.getInventory().addItem(6474, 1);
			supercompost = true;
			player.getDialogueManager().startDialogue("SimpleMessage", "You add the compost potion and make super compost.");
			return;
		}
		if (weeds == 20 && item.getId() == 6474 && supercompost == false) {
			player.getPackets().sendConfigByFile(741, 30);
			player.getInventory().deleteItem(6474, 1);
			player.getInventory().addItem(6476, 1);
			supercompost = true;
			player.getDialogueManager().startDialogue("SimpleMessage", "You add the compost potion and make super compost.");
			return;
		}
		if (weeds == 20 && item.getId() == 6476 && supercompost == false) {
			player.getPackets().sendConfigByFile(741, 30);
			player.getInventory().deleteItem(6476, 1);
			supercompost = true;
			player.getDialogueManager().startDialogue("SimpleMessage", "You add the compost potion and make super compost.");
			return;
		}
	}

	public static void handleCompost(Player player) {
		if (full == false) {
			player.getDialogueManager().startDialogue("SimpleMessage", "The compost bin is not full. (" + weeds + "/20)");
		}
		if (full == true && supercompost == false) {
			player.setNextAnimation(new Animation(2292));
			player.getInventory().deleteItem(1925, 1);
			player.getInventory().addItem(6032, 1);
			player.getPackets().sendConfigByFile(741, 0);
			weeds = 0;
			player.getDialogueManager().startDialogue("SimpleMessage", "You gather the broken down weeds, you received compost.");
		}
		if (full == true && supercompost == true) {
			player.setNextAnimation(new Animation(2292));
			player.getInventory().deleteItem(1925, 1);
			player.getInventory().addItem(6034, 1);
			player.getPackets().sendConfigByFile(741, 0);
			supercompost = false;
			weeds = 0;
			player.getDialogueManager().startDialogue("SimpleMessage", "You gather the broken down weeds, you received super compost.");
		}
	}

	public static void handleBin (Player player) {
		if (full == true) {
			player.getDialogueManager().startDialogue("SimpleMessage", "The compost bin is full.");
		}
		if (weeds == 19) {
			player.setNextAnimation(new Animation(2292));
			player.getInventory().deleteItem(6055, 1);
			weeds += 1;
			player.getPackets().sendGameMessage("You empty your weeds into the compost bin. (" + weeds + "/20)");
			player.getPackets().sendConfigByFile(741, 15);
			full = true;
		}
		if (weeds >= 9 && weeds <=18) {
			player.setNextAnimation(new Animation(2292));
			player.getInventory().deleteItem(6055, 1);
			weeds += 1;
			player.getPackets().sendGameMessage("You empty your weeds into the compost bin. (" + weeds + "/20)");
			player.getPackets().sendConfigByFile(741, 1);
		}
		if (weeds >= 0 && weeds <= 8) {
			player.setNextAnimation(new Animation(2292));
			player.getInventory().deleteItem(6055, 1);
			weeds += 1;
			player.getPackets().sendGameMessage("You empty your weeds into the compost bin. (" + weeds + "/20)");
			player.getPackets().sendConfigByFile(741, 0);
		}
	}

}