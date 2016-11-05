package com.rs.game.player.dialogues;

import com.rs.game.player.Player;

public class ChangeElement extends Dialogue {
	
	private int itemId;
	public static int AIR = 1381;
	public static int WATER = 1383;
	public static int EARTH = 1385;
	public static int FIRE = 1387;
	
	public static boolean isStaff(int item) {
		if (item == AIR || item == WATER || item == EARTH || item == FIRE)
			return true;
		else
			return false;
	}
	
	public static boolean hasStaff(Player player) {
		if (player.getInventory().containsItem(AIR, 1) || player.getInventory().containsItem(WATER, 1)
				 || player.getInventory().containsItem(EARTH, 1) || player.getInventory().containsItem(FIRE, 1))
			return true;
		else
			return false;
	}

	@Override
	public void start() {
		itemId = (Integer) parameters[0];
		sendOptionsDialogue("Select an Element", "Air",
				"Water", "Fire", "Earth");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
			if (player.getInventory().containsItem(556, 100) && hasStaff(player)) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(556, 100);
				player.getInventory().deleteItem(itemId, 1);
				player.getInventory().addItem(AIR, 1);
				player.sm("You successfully change your staff into an air staff!");
			} else {
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You must have 100 of the element's runes and an elemental staff in your inventory.");
			}
		  } else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(555, 100) && hasStaff(player)) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.getInventory().deleteItem(555, 100);
					player.getInventory().deleteItem(itemId, 1);
					player.getInventory().addItem(WATER, 1);
					player.sm("You successfully change your staff into a water staff!");
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("You must have 100 of the element's runes and an elemental staff in your inventory.");
				}
		  } else if (componentId == OPTION_3) {
				if (player.getInventory().containsItem(554, 100) && hasStaff(player)) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.getInventory().deleteItem(554, 100);
					player.getInventory().deleteItem(itemId, 1);
					player.getInventory().addItem(FIRE, 1);
					player.sm("You successfully change your staff into a fire staff!");
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("You must have 100 of the element's runes and an elemental staff in your inventory.");
				}
		  } else if (componentId == OPTION_4) {
				if (player.getInventory().containsItem(557, 100) && hasStaff(player)) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.getInventory().deleteItem(557, 100);
					player.getInventory().deleteItem(itemId, 1);
					player.getInventory().addItem(EARTH, 1);
					player.sm("You successfully change your staff into an earth staff!");
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("You must have 100 of the element's runes and an elemental staff in your inventory.");
				}
				}
		}
	}

	@Override
	public void finish() {

	}

}
