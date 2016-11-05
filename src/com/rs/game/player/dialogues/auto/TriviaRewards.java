package com.rs.game.player.dialogues.auto;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;

/**
 * 
 * @author Oracle
 * 
 * Handles Trivia rewards.
 *
 */
public class TriviaRewards extends Dialogue {


	@Override
	public void start() {
		sendOptionsDialogue("Select an Option", "How many points do I have?", "I would like to exchange my points.", "How do I get points?", "I would like to access my bank account.", "Never mind.");
		stage = -1;
		return;
	}

	@Override
	public void run(int interfaceId, int option) {
		switch(stage) {
		case -1:
			sendOptionsDialogue("Select an Option", "How many points do I have?", "I would like to exchange my points.", "How do I get points?", "I would like to access my bank account.", "Never mind.");
			stage = 0;
			break;
		case 0:
			switch(option) {
			case OPTION_1:
				sendDialogue("You currently have " + player.getTriviaPoints() + " Trivia points.");
				stage = -1;
				break;
			case OPTION_2:
				sendOptionsDialogue("Trivia Rewards - Page 1", "Ring of Wealth (750 pts.)", "Spin Ticket (20 pts.)", "10 Thousand Coins (500 pts.)" , "Barrows Gloves (500 pts.)", "Never mind.");
				stage = 4;
				break;
			case OPTION_3:
				sendDialogue("Simple, type ::answer when a trivia question appears!");
				stage = -1;
				break;
			case OPTION_4:
				player.getBank().openBank();
				player.getInterfaceManager().closeChatBoxInterface();
				break;
			case OPTION_5:
				player.getInterfaceManager().closeChatBoxInterface();
				break;
			}
			break;
		case 3:
			end();
			break;
		case 4:
			switch(option) {
		case OPTION_1:
				if (player.getTriviaPoints() >= 750) {
				player.getBank().addItem(2572, 1, true);
				player.setTriviaPoints(player.getTriviaPoints()
						- 750);
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().sendChatBoxInterface(1189);
				player.getPackets().sendItemOnIComponent(1189, 1, 2572, 1);
				player.getPackets().sendIComponentText(1189, 4, "The item has been added to your bank.");
				stage = 3;
				}else{
				sendDialogue("You need 750 points to buy this item." );
				stage = 3;
				}
				break;
		case OPTION_2:
			if (player.getTriviaPoints() >= 20) {
			player.getBank().addItem(24154, 1, true);
			player.setTriviaPoints(player.getTriviaPoints()
					- 20);
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().sendChatBoxInterface(1189);
			player.getPackets().sendItemOnIComponent(1189, 1, 24154, 1);
			player.getPackets().sendIComponentText(1189, 4, "The items have been added to your bank.");
			stage = 3;
			}else{
			sendDialogue("You need 20 points to buy this item." );
			stage = 3;
			}
			break;
		case OPTION_3:
			if (player.getTriviaPoints() >= 500) {
			player.getBank().addItem(995, 10000, true);
			player.setTriviaPoints(player.getTriviaPoints()
					- 500);
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().sendChatBoxInterface(1189);
			player.getPackets().sendItemOnIComponent(1189, 1, 995, 1);
			player.getPackets().sendIComponentText(1189, 4, "The item has been added to your bank.");
			stage = 3;
			}else{
			sendDialogue("You need 500 points to buy this item." );
			stage = 3;
			}
			break;
		case OPTION_4:
			if (player.getTriviaPoints() >= 500) {
			player.getBank().addItem(7462, 2, true);
			player.setTriviaPoints(player.getTriviaPoints()
					- 500);
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().sendChatBoxInterface(1189);
			player.getPackets().sendItemOnIComponent(1189, 1, 7462, 1);
			player.getPackets().sendIComponentText(1189, 4, "The items have been added to your bank.");
			stage = 3;
			}else{
			sendDialogue("You need 500 points to buy this item." );
			stage = 3;
			}
			break;
		case OPTION_5:
			player.getInterfaceManager().closeChatBoxInterface();
			break;
		default:
			end();
			break;
		}}}

	@Override
	public void finish() {

	}

}
