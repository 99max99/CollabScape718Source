package com.rs.game.player.dialogues.worldwide;

import com.rs.game.player.dialogues.Dialogue;

public class Animations extends Dialogue {

	public Animations() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What you would like to change?", "Animation Settings",
				"Teleport Settings");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Animation Settings",
						"Cooking Animations.",
						"Mining Animations.",
						"Fletching Animations.",
						"Nevermind.");
				stage = 13;
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Teleport Options",
						"Gnome Copter",
						"Demon",
						"Sky Jump",
						"Unicorn",
						"Magic Poof");
				stage = 5;
			}
			
			/**
			 * Options
			 */
			
		} else if (stage == 13) {
				if (componentId == OPTION_1) { 
					sendOptionsDialogue("Cooking Animations",
							"I want to use the new cooking animations.", "I want to use the old mining animations.",
						 "Cancel.");
					stage = 18;
				} else if (componentId == OPTION_2) {
					sendOptionsDialogue("Mining Animations",
							"I want to use new mining animations.", "I want to use old mining animations.",
						 "Cancel.");
					stage = 19;
				} else if (componentId == OPTION_3) {
					sendOptionsDialogue("Fletching Animations",
							"I want to use new fletching animations.", "I want to use old fletching animations.",
						 "Cancel.");
					stage = 20;
				}  else if (componentId == OPTION_4) {
					player.getInterfaceManager().closeChatBoxInterface();
				}	

		
		/**
		 * Text
		 */
		
	} else if (stage == 20) {
				if (componentId == OPTION_1) {
					player.KarateFletching = true;
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("<col=008000>Game will display now new fletching animations.");
				} else if (componentId == OPTION_2) { 
					player.KarateFletching = false;
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("<col=008000>Game will display you old fletching animations.");
				}  else if (componentId == OPTION_3) {
					player.getInterfaceManager().closeChatBoxInterface();
				}	

	} else if (stage == 19) {
					if (componentId == OPTION_1) {
						player.ChillBlastMining = true;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display now new mining animations.");
					} else if (componentId == OPTION_2) { 
						player.ChillBlastMining = false;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display you old mining animations.");
					}  else if (componentId == OPTION_3) {
						player.getInterfaceManager().closeChatBoxInterface();
					}	
	
	} else if (stage == 18) {
					if (componentId == OPTION_1) {
						player.SamuraiCooking = true;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display now new cooking animations.");
					} else if (componentId == OPTION_2) {
						player.SamuraiCooking = false;
						player.getInterfaceManager().closeChatBoxInterface();
						player.sm("<col=008000>Game will display you old cooking animations.");
					}  else if (componentId == OPTION_3) {
						player.getInterfaceManager().closeChatBoxInterface();
			}
					
	} else if (stage == 5) {
		if (componentId == OPTION_1) {
			player.Ass = false;
			player.Gnome = true;
			player.Demon = false;
			player.Pony = false;
			player.SuperJump = false;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Gnome Copter teleport.");
		} else if (componentId == OPTION_2) { 
			player.Ass = false;
			player.Gnome = false;
			player.Demon = true;
			player.Pony = false;
			player.SuperJump = false;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Demon teleport.");
		}  else if (componentId == OPTION_3) {
			player.Ass = false;
			player.Gnome = false;
			player.Demon = false;
			player.Pony = false;
			player.SuperJump = true;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Sky Jump teleport.");
			player.getInterfaceManager().closeChatBoxInterface();
		} else if (componentId == OPTION_4) {
			player.Ass = false;
			player.Gnome = false;
			player.Demon = false;
			player.Pony = true;
			player.SuperJump = false;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Unicorn teleport.");
		} else if (componentId == OPTION_5) {
			player.Ass = true;
			player.Gnome = false;
			player.Demon = false;
			player.Pony = false;
			player.SuperJump = false;
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("<col=008000>Your teleport will now show Magic Poof teleport.");
			}
		}
	}

@Override
public void finish() {
	}
}