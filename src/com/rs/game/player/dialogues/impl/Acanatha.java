package com.rs.game.player.dialogues.impl;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2012-10-27
 */
public class Acanatha extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9840, "WELCOME TO DARK INVASION!");
	}

	@Override
	public void run(int interfaceId, int option) {
		switch(stage) {
		case -1:
			sendOptionsDialogue("Select an Option", "How many points do I have?", "I would like to exchange my points.", "Tell me more.", "Enter the instance.");
			stage = 0;
			break;
		case 0:
			switch(option) {
			case OPTION_1:
				sendNPCDialogue(npcId, 9808, "You currently have " + player.getPlayerData().getInvasionPoints() + " invasion points.");
				stage = -1;
				break;
			case OPTION_2:
				player.getInterfaceManager().closeChatBoxInterface();
				player.closeInterfaces();
				player.getInterfaceManager().sendInvasionShop();
				//sendOptionsDialogue("Dark Invasion rewards", "Void knight top (100 pts.)", "Void knight bottom (100 pts.)", "Void knight gloves (100 pts.)" , "100x Stardust (125 pts.)", "Go back...");
	//			stage = 4;
				break;
			case OPTION_3:
				sendNPCDialogue(npcId, 9840, "There's nothing really to explain... Just kill those zombie children when they come eat you.");
				stage = -1;
				break;
			case OPTION_4:
				player.getControlerManager().startControler("DarkInvasion");
				stage = 3;
				player.getInterfaceManager().closeChatBoxInterface();
				break;
			}
			break;
		case 69:
			if (player.getEquipment().getAmuletId() != -1 
			|| player.getEquipment().getBootsId() != -1
			|| player.getEquipment().getCapeId() != -1
			|| player.getEquipment().getGlovesId() != -1
			|| player.getEquipment().getHatId() != -1
			|| player.getEquipment().getShieldId() != -1
			|| player.getEquipment().getRingId() != -1) {
		player.getDialogueManager().startDialogue("SimpleMessage", "You can only have a weapon and ammo equipped.");
		player.getControlerManager().removeControlerWithoutCheck();
		return;
			} else {
		player.getControlerManager().startControler("DarkInvasion");
			break;
			}
		case 3:
			end();
			break;
		case 4:
			switch(option) {
		case OPTION_1: //8839 - void knight top
				if (player.getPlayerData().getInvasionPoints() >= 100) {
				player.getInventory().addItem(8839, 1);
				player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints()
						- 100);
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInterfaceManager().sendChatBoxInterface(1189);
				player.getPackets().sendItemOnIComponent(1189, 1, 8839, 1);
				player.getPackets().sendIComponentText(1189, 4, "Purchase successful.");
				for (Player players : World.getPlayers()) {
					if (players == null)
						continue;
					players.getPackets()
							.sendGameMessage(
									"<col=ff8c38><img=7>[Dark Invasion]: "
											+ player.getDisplayName()
											+ " has obtained a Void knight top."); }
				stage = 3;
				}else{
				sendNPCDialogue(npcId, 9827, "You need 100 points to buy this item." );
				stage = 3;
				}
				break;
		case OPTION_2: //8840 - void knight robe
			if (player.getPlayerData().getInvasionPoints() >= 100) {
			player.getInventory().addItem(8840, 1);
			player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints()
					- 100);
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().sendChatBoxInterface(1189);
			player.getPackets().sendItemOnIComponent(1189, 1, 8840, 1);
			player.getPackets().sendIComponentText(1189, 4, "Purchase successful.");
			for (Player players : World.getPlayers()) {
				if (players == null)
					continue;
				players.getPackets()
						.sendGameMessage(
								"<col=ff8c38><img=7>[Dark Invasion]: "
										+ player.getDisplayName()
										+ " has obtained a Void knight robe."); }
			stage = 3;
			}else{
			sendNPCDialogue(npcId, 9827, "You need 100 points to buy this item." );
			stage = 3;
			}
			break;
		case OPTION_3: //8842 - void knight gloves
			if (player.getPlayerData().getInvasionPoints() >= 100) {
			player.getInventory().addItem(8842, 1);
			player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints()
					- 100);
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().sendChatBoxInterface(1189);
			player.getPackets().sendItemOnIComponent(1189, 1, 8842, 1);
			player.getPackets().sendIComponentText(1189, 4, "Purchase successful.");
			for (Player players : World.getPlayers()) {
				if (players == null)
					continue;
				players.getPackets()
						.sendGameMessage(
								"<col=ff8c38><img=7>[Dark Invasion]: "
										+ player.getDisplayName()
										+ " has obtained Void knight gloves."); }
			stage = 3;
			}else{
			sendNPCDialogue(npcId, 9827, "You need 100 points to buy this item." );
			stage = 3;
			}
			break;
		case OPTION_4: //13727 stardust (100x)
			if (player.getPlayerData().getInvasionPoints() >= 125) {
			player.getInventory().addItem(13727, 100);
			player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints()
					- 125);
			player.getInterfaceManager().closeChatBoxInterface();
			player.getInterfaceManager().sendChatBoxInterface(1189);
			player.getPackets().sendItemOnIComponent(1189, 1, 13727, 1);
			player.getPackets().sendIComponentText(1189, 4, "Purchase successful.");
			for (Player players : World.getPlayers()) {
				if (players == null)
					continue;
				players.getPackets()
						.sendGameMessage(
								"<col=ff8c38><img=7>[Dark Invasion]: "
										+ player.getDisplayName()
										+ " has obtained 100x Stardust."); }
			stage = 3;
			}else{
			sendNPCDialogue(npcId, 9827, "You need 125 points to buy this item." );
			stage = 3;
			}
			break;
		case OPTION_5:
			stage = -1;
			break;
		default:
			end();
			break;
		}
		}
	}
	

	@Override
	public void finish() {

	}

}
