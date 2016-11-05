package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;

public class Conductor extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Hello, would you like to travel using minecarts?",
        		"The cost is 100,000 per trip.");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("Where would you like to go?", "Keldagrim", "Dwarven Mines", "White Wold Mountain", "Grand Exchange", "Nowhere");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        		if (player.getInventory().containsItem(995, 100000)) {
        			player.getInventory().removeItemMoneyPouch(995, 100000);
        			sendNPCDialogue(npcId, 9827, "Thank you for your travels, you have now arrived at your destination.");
        			player.setNextWorldTile(new WorldTile(2906, 10173, 0));
        		} else {
        			 sendNPCDialogue(npcId, 9827, "I'm sorry but you do not have enough money.");
        		}
        		stage = 4;
                break;
        	} else if(componentId == OPTION_2) {
        		if (player.getInventory().containsItem(995, 100000)) {
        			player.getInventory().removeItemMoneyPouch(995, 100000);
        			sendNPCDialogue(npcId, 9827, "Thank you for your travels, you have now arrived at your destination.");
        			player.setNextWorldTile(new WorldTile(2995, 9837, 0));
        		} else {
        			 sendNPCDialogue(npcId, 9827, "I'm sorry but you do not have enough money.");
        		}
        		stage = 4;
                break;
        	} else if(componentId == OPTION_3) {
        		if (player.getInventory().containsItem(995, 100000)) {
        			player.getInventory().removeItemMoneyPouch(995, 100000);
        			sendNPCDialogue(npcId, 9827, "Thank you for your travels, you have now arrived at your destination.");
        			player.setNextWorldTile(new WorldTile(2876, 9868, 0));
        		} else {
        			 sendNPCDialogue(npcId, 9827, "I'm sorry but you do not have enough money.");
        		}
        		stage = 4;
                break;
        	} else if(componentId == OPTION_4) {
        		if (player.getInventory().containsItem(995, 100000)) {
        			player.getInventory().removeItemMoneyPouch(995, 100000);
        			sendNPCDialogue(npcId, 9827, "Thank you for your travels, you have now arrived at your destination.");
        			player.setNextWorldTile(new WorldTile(3141, 3506, 0));
        		} else {
        			 sendNPCDialogue(npcId, 9827, "I'm sorry but you do not have enough money.");
        		}
        		stage = 4;
                break;
        	} else if(componentId == OPTION_5) {
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