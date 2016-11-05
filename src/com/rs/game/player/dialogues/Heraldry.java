package com.rs.game.player.dialogues;

import com.rs.game.item.Item;
import com.rs.game.player.Skills;

public class Heraldry extends Dialogue {
	
	private Item rune;
	private int type;

	@Override
	public void start() {
		rune = (Item) parameters[0];
		if (rune.getId() == 1163)
			type = 1;
		else if (rune.getId() == 1201)
			type = 2;
		else if (rune.getId() == 8790)
			type = 3;
		;
			sendOptionsDialogue("Select a Type", "Arrav",
					"Asgarnia", "Dorgeshuun", "Dragon", "More");
			stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8464, 1);
				else if (type == 2)
					player.getInventory().addItem(8714, 1);
				else if (type == 3)
					player.getInventory().addItem(8650, 1);
				player.sm("You paint your item with a style of Arrav.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8466, 1);
				else if (type == 2)
					player.getInventory().addItem(8716, 1);
				else if (type == 3)
					player.getInventory().addItem(8652, 1);
				player.sm("You paint your item with a style of Asgarnia.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8468, 1);
				else if (type == 2)
					player.getInventory().addItem(8718, 1);
				else if (type == 3)
					player.getInventory().addItem(8654, 1);
				player.sm("You paint your item with a style of Dorgeshuun.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.DS >= 7) {
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8470, 1);
				else if (type == 2)
					player.getInventory().addItem(8720, 1);
				else if (type == 3)
					player.getInventory().addItem(8656, 1);
				player.sm("You paint your item with a style of Dragon.");
				} else {
					player.sm("You must have completed Dragon Slayer in order to use this decoration.");
				}
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select a Type", "Fairy",
						"Guthix", "H.A.M.", "Horse", "More");
				stage = 2;
				}
		}
		else if (stage == 2) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.lostCity >= 1) {
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8472, 1);
				else if (type == 2)
					player.getInventory().addItem(8722, 1);
				else if (type == 3)
					player.getInventory().addItem(8658, 1);
				player.sm("You paint your item with a style of Fairy.");
				} else {
					player.sm("You must have completed Lost City in order to use this decoration.");
				}
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getSkills().getLevel(Skills.PRAYER) >= 70) {
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8474, 1);
				else if (type == 2)
					player.getInventory().addItem(8724, 1);
				else if (type == 3)
					player.getInventory().addItem(8660, 1);
				player.sm("You paint your item with a style of Guthix.");
				} else {
					player.sm("You must have a Prayer level of atleast 70 to use this decoration.");
				}
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8476, 1);
				else if (type == 2)
					player.getInventory().addItem(8726, 1);
				else if (type == 3)
					player.getInventory().addItem(8662, 1);
				player.sm("You paint your item with a style of H.A.M.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(2520, 1)) {
				player.getInventory().deleteItem(rune);
				player.getInventory().deleteItem(2520, 1);
				if (type == 1)
					player.getInventory().addItem(8478, 1);
				else if (type == 2)
					player.getInventory().addItem(8728, 1);
				else if (type == 3)
					player.getInventory().addItem(8664, 1);
				player.sm("You paint your item with a style of Horse.");
			} else {
				player.sm("You must have 5mil in order to use this decoration.");
			}
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select a Type", "Jogre",
						"Kandarin", "Misthalin", "Money", "More");
				stage = 3;
				}
		}
		else if (stage == 3) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8480, 1);
				else if (type == 2)
					player.getInventory().addItem(8730, 1);
				else if (type == 3)
					player.getInventory().addItem(8666, 1);
				player.sm("You paint your item with a style of Jogre.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8482, 1);
				else if (type == 2)
					player.getInventory().addItem(8732, 1);
				else if (type == 3)
					player.getInventory().addItem(8668, 1);
				player.sm("You paint your item with a style of Kandarin.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8484, 1);
				else if (type == 2)
					player.getInventory().addItem(8734, 1);
				else if (type == 3)
					player.getInventory().addItem(8670, 1);
				player.sm("You paint your item with a style of Misthalin.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(995, 5000000)) {
				player.getInventory().deleteItem(rune);
				player.getInventory().removeItemMoneyPouch(995, 5000000);
				if (type == 1)
					player.getInventory().addItem(8486, 1);
				else if (type == 2)
					player.getInventory().addItem(8736, 1);
				else if (type == 3)
					player.getInventory().addItem(8672, 1);
				player.sm("You paint your item with a style of Money.");
				} else {
					player.sm("You must have 5mil in order to use this decoration.");
				}
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select a Type", "Saradomin",
						"Skull", "Varrock", "Zamorak", "None");
				stage = 3;
				}
		}
		else if (stage == 4) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getSkills().getLevel(Skills.PRAYER) >= 70) {
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8488, 1);
				else if (type == 2)
					player.getInventory().addItem(8738, 1);
				else if (type == 3)
					player.getInventory().addItem(8674, 1);
				player.sm("You paint your item with a style of Saradomin.");
			} else {
				player.sm("You must have a Prayer level of atleast 70 to use this decoration.");
			}
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8490, 1);
				else if (type == 2)
					player.getInventory().addItem(8740, 1);
				else if (type == 3)
					player.getInventory().addItem(8676, 1);
				player.sm("You paint your item with a style of Skull.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8492, 1);
				else if (type == 2)
					player.getInventory().addItem(8742, 1);
				else if (type == 3)
					player.getInventory().addItem(8678, 1);
				player.sm("You paint your item with a style of Varrock.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getSkills().getLevel(Skills.PRAYER) >= 70) {
				player.getInventory().deleteItem(rune);
				if (type == 1)
					player.getInventory().addItem(8494, 1);
				else if (type == 2)
					player.getInventory().addItem(8744, 1);
				else if (type == 3)
					player.getInventory().addItem(8680, 1);
				player.sm("You paint your item with a style of Zamorak.");
			} else {
				player.sm("You must have a Prayer level of atleast 70 to use this decoration.");
			}
		  } else if (componentId == OPTION_5) {
			  player.getInterfaceManager().closeChatBoxInterface();
				}
		}
	}

	@Override
	public void finish() {

	}

}
