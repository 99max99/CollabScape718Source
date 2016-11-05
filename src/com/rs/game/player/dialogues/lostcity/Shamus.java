package com.rs.game.player.dialogues.lostcity;

import com.rs.game.player.dialogues.Dialogue;


public class Shamus extends Dialogue {

	int npcId = 654;

	@Override
	public void start() {
		if (player.spokeToWarrior == true) {
		sendNPCDialogue(npcId, 9827, "Ay yer big elephant! What would an elephant like yer be wanting with ol' Shamus then?");
		}
	} 


	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "I want to find Zanaris.", "Nothing.");
			break;
		case 0:
			if(componentId == OPTION_2) {
				stage = 1;
				sendPlayerDialogue(9827, "Nothing.");
			}else {
				stage = 2;
				sendPlayerDialogue(9827, "I want to find Zanaris?");
			}
			break;
		case 1:
			stage = -2;
			sendNPCDialogue(npcId, 9827, "Well, please return if you change your mind.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(npcId, 9827, "Zanaris is it now? Yer'll be needing to be going to that funny little shed out there in the swamp.");
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Tell me more about this shed.", "Sorry I dont like sheds.");
			break;
		case 4:
			if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "Sorry I dont like sheds.");
				end();
			}else {
				stage = 5;
				sendPlayerDialogue(9827, "Tell me more about this shed.");
			}
			break;
		case 5:
			stage = 6;
			sendNPCDialogue(npcId, 9827, "Certainly!");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue(npcId, 9827, "It is a magical, mysterious shed that when you enter when weilding the <u>DRAMEN STAFF</u> you enter the magical land of Zanaris");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue(npcId, 9827, "Pretty cool ey?");
			break;
		case 8:
			stage = 9;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "So how do i get this Dramen staff?", "no.", "Maybe.");
			break;
		case 9:
			if(componentId == OPTION_3) {
				stage = 1;
				sendPlayerDialogue(9827, "Maybe.");
			}else if(componentId == OPTION_2) {
				sendPlayerDialogue(9827, "no.");
				end();
			}else {
				stage = 10;
				sendPlayerDialogue(9827, "So how do i get this Dramen staff?");
				player.spokeToShamus = true;
			}
			break;
		case 10:
			if(player.getEquipment().wearingArmour()) {
				stage = -2;
				sendNPCDialogue(npcId, 9827, "You will need to make one, I found mine at Entrana, You can catch a boat their from Port Sarim.");
			}else{
				stage = 11;
				sendNPCDialogue(npcId, 9827, "You will need to make one, I found mine at Entrana, You can catch a boat their from Port Sarim.");
			}
			break;
		case 11:
			stage = 12;
			sendPlayerDialogue(9827, "Okay, thanks.");
			break;
		case 12:
			//PlayerLook.openThessaliasMakeOver(player);
			end();
			break;
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}
