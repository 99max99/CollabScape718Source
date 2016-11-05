package com.rs.game.player.dialogues.interfaces;

import com.rs.game.player.dialogues.Dialogue;

public class Charges extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
    	sendPlayerDialogue( 9827,"I can buy Teleport Charges with Loyalty Tokens!");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("How many?", "10", "50", "100", "500", "1000");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        		if (player.getInventory().containsItem(29980, 500)) {
        			player.getInventory().deleteItem(29980, 500);
        			player.crystalcharges += 10;
        			player.getPackets().sendGameMessage("You purchase 10 Crystal Charges!");
        		} else {
        			player.getPackets().sendGameMessage("You need 500 Loyalty Tokens to purchase 10 charges.");
        		}
        		stage = 4;
                break;
        	} else if(componentId == OPTION_2) {
        		if (player.getInventory().containsItem(29980, 2250)) {
        			player.getInventory().deleteItem(29980, 2250);
        			player.crystalcharges += 50;
        			player.getPackets().sendGameMessage("You purchase 50 Crystal Charges!");
        		} else {
        			player.getPackets().sendGameMessage("You need 2,250 Loyalty Tokens to purchase 50 charges.");
        		}
    			stage = 4;
            break;
        	} else if(componentId == OPTION_3) {
        		if (player.getInventory().containsItem(29980, 4500)) {
        			player.getInventory().deleteItem(29980, 4500);
        			player.crystalcharges += 100;
        			player.getPackets().sendGameMessage("You purchase 100 Crystal Charges!");
        		} else {
        			player.getPackets().sendGameMessage("You need 4,500 Loyalty Tokens to purchase 100 charges.");
        		}
    			stage = 4;
            break;
        	} else if(componentId == OPTION_4) {
        		if (player.getInventory().containsItem(29980, 20000)) {
        			player.getInventory().deleteItem(29980, 20000);
        			player.crystalcharges += 500;
        			player.getPackets().sendGameMessage("You purchase 500 Crystal Charges!");
        		} else {
        			player.getPackets().sendGameMessage("You need 20,000 Loyalty Tokens to purchase 500 charges.");
        		}
            break;
        	} else if(componentId == OPTION_5) {
        		if (player.getInventory().containsItem(29980, 35000)) {
        			player.getInventory().deleteItem(29980, 35000);
        			player.crystalcharges += 1000;
        			player.getPackets().sendGameMessage("You purchase 1000 Crystal Charges!");
        		} else {
        			player.getPackets().sendGameMessage("You need 35,000 Loyalty Tokens to purchase 1000 charges.");
        		}
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