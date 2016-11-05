package com.rs.game.player.dialogues.tutorial;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.dialogues.Dialogue;

public class Guthix extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Ahhh, you are finally complete!" }, IS_NPC, npcId, 9827);

	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Who are you?",
					"Where am I?",
					"Can you guide me around the server?");
			break;
		case 0:
			if(componentId == 2) {
				stage = 2;
				sendPlayerDialogue( 9827, "May I ask who you are?" );
			}else if (componentId == 3) {
				stage = 3;
				sendPlayerDialogue( 9827, "Do you have any idea where we are right now?" );
			}else{
				stage = 5;
				sendPlayerDialogue( 9827, "Can you please show me around the server? I want to get started." );
			}
			break;
		case 2:
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Why I am a preacher of Guthix, here to guide you in this world!" }, IS_NPC, npcId, 9827);
			stage = -1;
			break;
		case 3:
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"We are in the creation realm, where all characters start off." }, IS_NPC, npcId, 9827);
			stage = 4;
			break;
		case 4:
			stage = -1;
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"From here, you can see all of Noszscape. It is a beautiful sight, isn't it?" }, IS_NPC, npcId, 9827);
			break;
		case 5:
			stage = 6;
			sendEntityDialogue(SEND_1_TEXT_CHAT,
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Sure, let's start. If you ever get stuck in the tutorial, please talk to me again." }, IS_NPC, npcId, 9827);
			player.starterstage = 1;
			player.getDialogueManager().startDialogue("NewPlayerTutorial");
			break;
		case 6:
			end();
			break;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}
