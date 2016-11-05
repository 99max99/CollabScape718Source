package com.rs.game.player.dialogues.karamja;

import com.rs.game.player.dialogues.Dialogue;

public class Saniboch extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Would you like to enter this marvelous dungeon?",
        		"There are great things down there, but there",
        		"also is an entrance fee of 150,000gp.",
        		"Will you buy passage to this dungeon?");
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
        		if (player.getInventory().containsItem(995, 150000)) {
        			player.getInventory().removeItemMoneyPouch(995, 150000);
        			player.brim = true;
        			 sendNPCDialogue(npcId, 9827, "You may now enter the dungeon.");
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