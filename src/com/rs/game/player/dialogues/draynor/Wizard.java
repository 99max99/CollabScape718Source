package com.rs.game.player.dialogues.draynor;

import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;


public class Wizard extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Would you like to buy my armor for 250,000?");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("What would you like to say?", "Yes", "No");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        		if (player.getInventory().getCoinsAmount() >= 250000) {
        			player.getInventory().removeItemMoneyPouch(new Item(995, 250000));
        			player.getInventory().addItem(3385, 1);
        			player.getInventory().addItem(3387, 1);
        			player.getInventory().addItem(3389, 1);
        			player.getInventory().addItem(3391, 1);
        			player.getInventory().addItem(3393, 1);
        			sendNPCDialogue(npcId, 9827, "Nice doing business with you!");
        		} else {
        			sendNPCDialogue(npcId, 9827, "You don't have enough money...");	
        		}
                stage = 4;
                break;
        	} else if(componentId == OPTION_2) {
                sendPlayerDialogue(9827, "I'm not interested anymore...");
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