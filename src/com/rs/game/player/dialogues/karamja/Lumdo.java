package com.rs.game.player.dialogues.karamja;

import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;

public class Lumdo extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Would you like to travel to Ape Atol?");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("Would you?", "Yes", "No");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        			player.setNextWorldTile(new WorldTile(2806, 2713, 0));
        			 sendNPCDialogue(npcId, 9827, "Here you go, be careful! There are poisonous monsters on this island.");
        		stage = 4;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "No thanks.");
                stage = 4;
                break;
            }
        case 4:
            default:
                end();
            break;
        }
    }
    
    public void finish() {
        
    }
}