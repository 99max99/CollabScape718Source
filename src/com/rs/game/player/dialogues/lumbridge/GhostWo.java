package com.rs.game.player.dialogues.lumbridge;

import com.rs.game.player.dialogues.Dialogue;

public class GhostWo extends Dialogue {

	int npcId;

	@Override
	public void start() {
		sendPlayerDialogue( 9827, "blies, marajs, tea fef tu?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendNPCDialogue(457, 9827, "muhs rejo te la" );
			stage = 1;
		} else if (stage == 1) {
			sendPlayerDialogue( 9827, "I'm sorry i dont understand?");
			stage = 2;
		} else if (stage == 2) {
			sendNPCDialogue(457, 9827, "par muaj oks oeie umd!" );
				end();
			
				} else if (stage == 21) {
					switch (componentId) {
					case 1:
						break;
					}
				} else {
					end();
				}
		
		}

			public void finish() {
			}
}