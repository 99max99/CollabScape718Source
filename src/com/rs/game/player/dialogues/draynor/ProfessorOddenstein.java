package com.rs.game.player.dialogues.draynor;

import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.dialogues.Dialogue;


public class ProfessorOddenstein extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = 286;
		sendPlayerDialogue(9827, "Hello?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			if (player.EC == 1) {
				sendNPCDialogue(npcId, 9827, "Be careful in here, there's a lot of dangerous equipment.");
				stage = 0;
			} else if (player.EC == 0 || player.EC == 3) {
				sendNPCDialogue(npcId, 9827, "I'm a very busy man, you must leave.");
				stage = 9;
			} else if (player.EC == 2) {
				sendNPCDialogue(npcId, 9827, "Did you find the items?");
				stage = 18;
			}
			break;
		case 0:
			sendPlayerDialogue(9827, "I'm looking for a guy called Ernest?");
			stage = 1;
			break;
		case 1:
			sendNPCDialogue(npcId, 9827, "Ah Ernest, top notch bloke. He's helping me with my", " experiments.");
			stage = 2;
			break;
		case 2:
			sendPlayerDialogue(9827, "So you know where he is then?");
			stage = 3;
			break;
		case 3:
			sendNPCDialogue(npcId, 9827, "He's that chicken over there.");
			stage = 4;
			break;
		case 4:
			sendPlayerDialogue(9827, "Ernest is a chicken...? Are you sure...?");
			stage = 5;
			break;
		case 5:
			sendNPCDialogue(npcId, 9827, "Oh, he isn't normally a chicken, or atleast he wasn't", "until he helped me test my pouletmorph machine.");
			stage = 6;
			break;
		case 6:
			sendNPCDialogue(npcId, 9827, "It was orriginally going to be called a transmutation", "machine. But after testing 'pouletmorph' seems more",
					"appropriate.");
			stage = 7;
			break;
		case 7:
			sendPlayerDialogue(9827, "I'm glad Veronica didn't actually get engaged to a", "chicken.");
			stage = 8;
			break;
		case 8:
			sendNPCDialogue(npcId, 9827, "Who's Veronica?");
			stage = 10;
			break;
		case 9:
			end();
			break;
		case 10:
			sendPlayerDialogue(9827, "Ernest's fiance. She probably doesn't want to marry a", "chicken.");
			stage = 11;
			break;
		case 11:
			sendNPCDialogue(npcId, 9827, "Ooh I dunno. She could get free eggs for breakfast.");
			stage = 12;
			break;
		case 12:
			sendPlayerDialogue(9827, "I think you better change him back.");
			stage = 13;
			break;
		case 13:
			sendNPCDialogue(npcId, 9827, "Umm... It's not so easy...");
			stage = 14;
			break;
		case 14:
			sendNPCDialogue(npcId, 9827, "My machine is broken, and the house gremlins have", "run off with some vital bits.");
			stage = 15;
			break;
		case 15:
			sendPlayerDialogue(9827, "Well I can look for them.");
			stage = 16;
			break;
		case 16:
			sendNPCDialogue(npcId, 9827, "That would be a help. They'll be somewhere in the", "manor house or it's grounds, the gremlins never get",
					"further than the entrance gate.");
			stage = 17;
			break;
		case 17:
			sendNPCDialogue(npcId, 9827, "I'm missing the pressure gauge and a rubber tube.", "They've got my oil can too, which I'm going to need",
					"to get this thing started again.");
			player.EC = 2;
			stage = 9;
			break;
		case 18:
			if (player.getInventory().containsItem(276, 1) && player.getInventory().containsItem(277, 1) && player.getInventory().containsItem(271, 1)) {
				sendPlayerDialogue(9827, "Yes, I have everything.");
				stage = 19;
			} else {
				sendPlayerDialogue(9827, "No, not yet.");
				stage = 9;
			}
			break;
		case 19:
			sendNPCDialogue(npcId, 9827, "Great! Give them here.");
			stage = 20;
			break;
		case 20:
			end();
			player.getInventory().deleteItem(276, 1);
			player.getInventory().deleteItem(277, 1);
			player.getInventory().deleteItem(271, 1);
			finish(player);
			break;
		}
	}

	public static void finish(final Player player) {
		int ernestTheChicken = 3290;
		final int ernestTheHuman = 287;
		player.addBoneDelay(5000);
		final NPC chicken = findNPC(ernestTheChicken);
		chicken.transformIntoNPC(ernestTheHuman);
					player.getInterfaceManager().sendErnestComplete();
					player.EC = 3;
					player.reset();

	}

	public static NPC findNPC(int id) {
		for (NPC npc : World.getNPCs()) {
			if (npc == null || npc.getId() != id)
				continue;
			return npc;
		}
		return null;
	}

	@Override
	public void finish() {
	}

}
