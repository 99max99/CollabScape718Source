package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;


public class WizardGrayzig extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
			sendNPCDialogue(npcId, 9827,"Grr... That Wizard Mizgog, he",
					"doesn't understand the full",
					"potential of my imp powers..");
	}


	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			sendPlayerDialogue(9827, "What is that lessar demon doing there?");
			stage = 0;
			break;
		case 0:
			sendNPCDialogue(npcId, 9827,"I was practicing my magic spells and summoned him.");
			stage = 1;
			break;
		case 1:
			sendNPCDialogue(npcId, 9827,"You can kill him for magic or ranged exp, I need",
					"practice summoning these demons anyways.");
			stage = 2;
			break;
		case 2:
			sendPlayerDialogue(9827, "Thanks!");
			stage = 3;
			break;
		case 3:
			end();
			break;
		}
	}
	public void finish() {
	}
}