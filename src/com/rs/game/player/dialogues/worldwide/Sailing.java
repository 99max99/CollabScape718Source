package com.rs.game.player.dialogues.worldwide;

import com.rs.game.player.dialogues.Dialogue;

public class Sailing extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Hello, would you like to travel?",
        		"The cost is 15,000 per trip.");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("What would you like to?", "Yes", "No");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        		if (player.getInventory().containsItem(995, 15000)) {
        			player.getInventory().removeItemMoneyPouch(995, 15000);
        			player.getInterfaceManager().sendInterface(95);
        		} else {
        			 sendNPCDialogue(npcId, 9827, "I'm sorry but you do not have enough money.");
        		}
        		stage = 4;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "No thanks, I can walk.");
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