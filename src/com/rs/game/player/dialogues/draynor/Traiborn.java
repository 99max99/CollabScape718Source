package com.rs.game.player.dialogues.draynor;

import com.rs.game.player.dialogues.Dialogue;


public class Traiborn extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Hello, would you like some robes like mine?");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("How should you react?", "No thanks...", "Sure why not?");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
                stage = 1;
        	} else if(componentId == OPTION_2) {
                stage = 3;
            }
        	sendNPCDialogue(npcId, 9827, "OKAY HERE YOU GO!");
        	player.getInventory().addItem(577, 1);
        	player.getInventory().addItem(579, 1);
        	player.getInventory().addItem(2579, 1);
        	break;
        case 1:
        	 sendPlayerDialogue(9827, "But I said no...");
            stage = 2;
            break;
        case 2:
        	 sendPlayerDialogue(9827, "Thanks, but where is the robe bottom?");
        	 stage = 3;
            break;
        case 3:
            sendNPCDialogue(npcId, 9827, "NO!!!!! BUY YOUR OWN!!!");
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