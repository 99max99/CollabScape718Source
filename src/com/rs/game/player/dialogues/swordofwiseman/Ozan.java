package com.rs.game.player.dialogues.swordofwiseman;


import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.player.controlers.TutorialIsland;
import com.rs.game.player.dialogues.Dialogue;


public class Ozan extends Dialogue {

	int npcId;
	TutorialIsland controler;
	
	public static final int OZAN = 15895;

	@Override
	public void start() {
		if (player.SOWQUEST == 2) {
		sendEntityDialogue(
				SEND_3_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(OZAN).name,
						"What do you want fool?"}, IS_NPC,
						OZAN, 9790);
	}
}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendPlayerDialogue(9790,
					"Do you have the King's sword?");
		} else if (stage == 0) {
			stage = 1;
			sendNPCDialogue(OZAN, 9845, "Oh... You mean the Sword of Wiseman?");
		} else if (stage == 1) {
			stage = 2;
			sendPlayerDialogue(9790,
					"Yes, that sword idiot.");
		} else if (stage == 2) {
			stage = 3;
			sendNPCDialogue(OZAN, 9845, "Yes, I do have that sword",
					"Why do you care anyways?");
		} else if (stage == 3) {
			stage = 4;
			sendPlayerDialogue(9845,
					"I need to return it to the king.");
		} else if (stage == 4) {
			stage = 5;
			sendNPCDialogue(OZAN, 9845, "That little midget can't even fight",
					"Why does he need it?");
		} else if (stage == 5) {
			stage = 6;
			sendPlayerDialogue(9845,
					"So he can protect Noszscape from all the ",
					"nasty demons out there!");
		} else if (stage == 6) {
			stage = 7;
			sendNPCDialogue(OZAN, 9845, "Fine, but you have a choice..",
					"You can either take the sword and you can protect Noszscape...");
		} else if (stage == 7) {
			stage = 8;
			sendNPCDialogue(OZAN, 9845, "Or I will return it to the king for him to",
					"protect Noszscape.");
		} else if (stage == 8) {
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Take the sword",
					"Have Ozan return it");
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(6609, 1);
				World.sendWorldMessage("<img=7><col=FF0000>News: " + player.getDisplayName() + " has completed the Sword of Wiseman Quest!", false);
				player.SOWQUEST += 1;
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				World.sendWorldMessage("<img=7><col=FF0000>News: " + player.getDisplayName() + " has completed the Sword of Wiseman Quest!", false);
				player.SOWQUEST += 1;
			end();
			}
		}
	}

	@Override
	public void finish() {

	}
}
