package com.rs.game.player.dialogues.karamja;

import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

public class KaramjaTrip extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Would you like to travel to Karamja for 3,000gp?");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("Chose an Option?", "Yes", "No");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        		if (player.getInventory().getCoinsAmount() >= 3000) {
        			player.getInventory().removeItemMoneyPouch(new Item(995, 3000));
        			player.setNextWorldTile(new WorldTile(2956, 3146, 0));
        			 sendNPCDialogue(npcId, 9827, "You have now arrived at Karamja.");
        		} else {
        			 sendNPCDialogue(npcId, 9827, "I'm sorry but you do not have enough money.");
        		}
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