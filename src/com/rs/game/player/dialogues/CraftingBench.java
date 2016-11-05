package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Skills;

public class CraftingBench extends Dialogue {
	
	public static int STEEL_BAR = 2353;
	public static int PLANK = 960;
	public static int CLOCKWORK = 8792;
	public static int FUR = 948;
	public static int SILK = 950;

	@Override
	public void start() {
		sendOptionsDialogue("Select an Item", "Clockwork",
				"Toy Horsey", "Toy Soldier", "Wooden Cat", "More");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(STEEL_BAR, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 8) {
						player.getInventory().deleteItem(STEEL_BAR, 1);
						player.getInventory().addItem(8792, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 8 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a steel bar in order to make this item.");
				}
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(PLANK, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 10) {
						player.getInventory().deleteItem(PLANK, 1);
						player.getInventory().addItem(2520, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 10 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a plank in order to make this item.");
				}
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(CLOCKWORK, 1) && player.getInventory().containsItem(PLANK, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 13) {
						player.getInventory().deleteItem(CLOCKWORK, 1);
						player.getInventory().deleteItem(PLANK, 1);
						player.getInventory().addItem(7759, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 13 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a clockwork and a plank in order to make this item.");
				}
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(FUR, 1) && player.getInventory().containsItem(PLANK, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 16) {
						player.getInventory().deleteItem(FUR, 1);
						player.getInventory().deleteItem(PLANK, 1);
						player.getInventory().addItem(10891, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 16 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a piece of bear fur and a plank in order to make this item.");
				}
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select an Item", "Toy Doll",
						"Sextant", "Watch", "Clockwork Suit", "None");
				stage = 0;
				}
		}
		if (stage == 0) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(CLOCKWORK, 1) && player.getInventory().containsItem(PLANK, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 18) {
						player.getInventory().deleteItem(CLOCKWORK, 1);
						player.getInventory().deleteItem(PLANK, 1);
						player.getInventory().addItem(7763, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 18 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a clockwork and a plank in order to make this item.");
				}
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(STEEL_BAR, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 23) {
						player.getInventory().deleteItem(STEEL_BAR, 1);
						player.getInventory().addItem(2574, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 23 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a steel bar in order to make this item.");
				}
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(STEEL_BAR, 1) && player.getInventory().containsItem(CLOCKWORK, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 28) {
						player.getInventory().deleteItem(STEEL_BAR, 1);
						player.getInventory().deleteItem(CLOCKWORK, 1);
						player.getInventory().addItem(2575, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 28 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a steel bar and a clockwork in order to make this item.");
				}
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(PLANK, 1) && player.getInventory().containsItem(CLOCKWORK, 1) && player.getInventory().containsItem(SILK, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 30) {
						player.getInventory().deleteItem(PLANK, 1);
						player.getInventory().deleteItem(CLOCKWORK, 1);
						player.getInventory().deleteItem(SILK, 1);
						player.getInventory().addItem(10595, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 30 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a plank, a clockwork, and a piece of silk in order to make this item.");
				}
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select an Item", "Toy Mouse",
						"Clockwork Cat", "None");
				stage = 1;
				}
		}
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(CLOCKWORK, 1) && player.getInventory().containsItem(PLANK, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 33) {
						player.getInventory().deleteItem(CLOCKWORK, 1);
						player.getInventory().deleteItem(PLANK, 1);
						player.getInventory().addItem(7767, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 33 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a clockwork and a plank in order to make this item.");
				}
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				if (player.getInventory().containsItem(CLOCKWORK, 1) && player.getInventory().containsItem(PLANK, 1)) {
					if (player.getSkills().getLevel(Skills.CRAFTING) >= 85) {
						player.getInventory().deleteItem(CLOCKWORK, 1);
						player.getInventory().deleteItem(PLANK, 1);
						player.getInventory().addItem(7771, 1);
						player.setNextAnimation(new Animation(3683));
					} else {
						player.sm("You must have a level of 85 Crafting in order to make this item.");
					}
				} else {
					player.sm("You must have a clockwork and a plank in order to make this item.");
				}
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				}
		}
	}

	@Override
	public void finish() {

	}

}
