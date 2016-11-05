package com.rs.game.player.dialogues.interfaces;

import com.rs.game.player.content.NewsOptions;
import com.rs.game.player.dialogues.Dialogue;




public class News extends Dialogue {

	public News() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What would you like to do?", "News Options",
				"Welcome Messages", "Server Messages", "99 Messages", "Cancel");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("News Options",
						"Enable News", 
						"Disable News");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("New Players",
						"Enable Welcome News", 
						"Disable Welcome News");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendOptionsDialogue("Server Messages",
						"Enable Server Messages", 
						"Disable Server Messages");
				stage = 4;
			} else if (componentId == OPTION_4) {
				sendOptionsDialogue("99 Messages",
						"Enable 99 Messages", 
						"Disable 99 Messages");
				stage = 5;
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You closed the options.");
			}
			
			} else if (stage == 2) {
				if (componentId == OPTION_1) {
					NewsOptions.setNewsPositive(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_2) {
					NewsOptions.setNewsNegative(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_3) {
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (stage == 3) {
				if (componentId == OPTION_1) {
					NewsOptions.TogglePlayerInfo(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_2) {
					NewsOptions.unTogglePlayerInfo(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_3) {
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (stage == 4) {
				if (componentId == OPTION_1) {
					NewsOptions.setMessagesPositive(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_2) {
					NewsOptions.setMessagesNegative(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_3) {
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (stage == 5) {
				if (componentId == OPTION_1) {
					NewsOptions.set99sPositive(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_2) {
					NewsOptions.set99sNegative(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_3) {
					player.getInterfaceManager().closeChatBoxInterface();
				}
			}
		}
		@Override
		public void finish() {
		}
				}
