package com.rs.game.player.dialogues.portsarim;

import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;

public class SirRebrum extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Would you like to enter this the Grotworm Lair?");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("Chose an Option.", "Yes", "No");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        		player.setNextWorldTile(new WorldTile(1206, 6371, 0));
        		sendNPCDialogue(npcId, 9827, "Goodluck in your travels adventurer.");
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