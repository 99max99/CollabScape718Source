package com.rs.game.player.dialogues;

public class ToolStore extends Dialogue {
	
	private int tool;

	@Override
	public void start() {
		tool = (int) parameters[0];
		if (tool == 1) {
			sendOptionsDialogue("Select an Item", "Saw",
					"Hammer", "Chisel", "Sheer", "None");
			stage = 1;
		} else if (tool == 2) {
			sendOptionsDialogue("Select an Item", "Bucket",
					"Knife", "Spade", "Tinderbox", "None");
			stage = 2;
		} else if (tool == 3) {
			sendOptionsDialogue("Select an Item", "Brown Apron",
					"Glassblowing Pipe", "Needle", "None");
			stage = 3;
		} else if (tool == 4) {
			sendOptionsDialogue("Select an Item", "Amulet Mould",
					"Necklace Mould", "Ring Mould", "Holy Symbol Mould", "More");
			stage = 4;
		} else if (tool == 5) {
			sendOptionsDialogue("Select an Item", "Rake",
					"Spade", "Trowel", "Seed Dibber", "More");
			stage = 5;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(8794, 1);
				player.sm("You take the saw from the tool mount.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(2347, 1);
				player.sm("You take the hammer from the tool mount.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1755, 1);
				player.sm("You take the chisel from the tool mount.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1735, 1);
				player.sm("You take the sheer from the tool mount.");
		  } else if (componentId == OPTION_5) {
			  player.getInterfaceManager().closeChatBoxInterface();
				}
		}
		if (stage == 2) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1925, 1);
				player.sm("You take the bucket from the tool mount.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(946, 1);
				player.sm("You take the knife from the tool mount.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(952, 1);
				player.sm("You take the spade from the tool mount.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(590, 1);
				player.sm("You take the tinderbox from the tool mount.");
		  } else if (componentId == OPTION_5) {
			  player.getInterfaceManager().closeChatBoxInterface();
				}
		}
		if (stage == 3) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1757, 1);
				player.sm("You take the brown apron from the tool mount.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1785, 1);
				player.sm("You take the glassblowing pipe from the tool mount.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1733, 1);
				player.sm("You take the needle from the tool mount.");
		  } else if (componentId == OPTION_4) {
			  player.getInterfaceManager().closeChatBoxInterface();
				}
		}
		if (stage == 4) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1595, 1);
				player.sm("You take the amulet mould from the tool mount.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1597, 1);
				player.sm("You take the necklace mould from the tool mount.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1592, 1);
				player.sm("You take the ring mould from the tool mount.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(1599, 1);
				player.sm("You take the holy symbol mould from the tool mount.");
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select an Item", "Bracelet Mould",
						"Tiara Mould", "None");
				stage = 6;
				}
		}
		if (stage == 5) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(5341, 1);
				player.sm("You take the rake from the tool mount.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(952, 1);
				player.sm("You take the spade from the tool mount.");
		  } else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(5325, 1);
				player.sm("You take the trowel from the tool mount.");
		  } else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(5343, 1);
				player.sm("You take the seed dibber from the tool mount.");
		  } else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select an Item", "Watering Can",
						"Secateurs", "None");
				stage = 7;
				}
		}
		if (stage == 6) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(11065, 1);
				player.sm("You take the bracelet mould from the tool mount.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(5523, 1);
				player.sm("You take the tiara mould from the tool mount.");
		  } else if (componentId == OPTION_3) {
			  player.getInterfaceManager().closeChatBoxInterface();
				}
		}
		if (stage == 7) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(5331, 1);
				player.sm("You take the watering can from the tool mount.");
		  } else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(5329, 1);
				player.sm("You take the secateurs from the tool mount.");
		  } else if (componentId == OPTION_3) {
			  player.getInterfaceManager().closeChatBoxInterface();
				}
		}
	}

	@Override
	public void finish() {

	}

}
