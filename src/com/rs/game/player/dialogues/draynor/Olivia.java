package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;


/**
 * 
 * @author Josh - Jul 14 2013
 * 
 * Olivia's Seed Market Dialogue
 * 
 */
public class Olivia extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Hello, how can I help you today?");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("What would you like to say?", "Would you like to trade?", "Where do I get higher-level seeds from?");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
                sendPlayerDialogue(9827, "Would you like to trade?");
                stage = 1;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "Where do I get higher-level seeds from?");
                stage = 3;
                break;
            }
        case 1:
            sendNPCDialogue(npcId, 9827, "Certainly.");
            stage = 2;
            break;
        case 2:
            end();
            ShopsHandler.openShop(player, 102);
            break;
        case 3:
            sendNPCDialogue(npcId, 9827, "Master farmers usually carry a fer higher-level seeds",
                    "around with them, although I don't know if they'd want to",
                    "part with them for any price, to be honest.");
            stage = 4;
            break;
        case 4:
            default:
                end();
            break;
        }
    }
    
    public void finish() {
        
    }
}