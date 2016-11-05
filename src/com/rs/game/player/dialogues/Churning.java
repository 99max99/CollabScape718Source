package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class Churning extends Dialogue {

	public int npcId = 81;
    
    @Override
    public void start() {
        sendNPCDialogue(npcId, 9827, "Moo, I'm a cow. What would you like to make?");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("Pick a Product.", "Pot of Cream", "Pat of Cheese", "Cheese Wheel");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        		if (player.getInventory().containsItem(1927, 1) && player.getInventory().containsItem(1923, 1)) {
        		player.getInventory().deleteItem(1927, 1);
        		player.getInventory().deleteItem(1923, 1);
        		player.getInventory().addItem(2130, 1);
        		sendNPCDialogue(npcId, 9827, "You have succesfully made a bowl of cream!");
        		player.getSkills().addXp(Skills.COOKING, 3);
        		} else  {
        		sendNPCDialogue(npcId, 9827, "You need a bowl and bucket of milk to make a pot of cream!");
        		}
        		stage = 4;
                break;
        	} else if(componentId == OPTION_2) {
        		if (player.getInventory().containsItem(1927, 1) && player.getInventory().containsItem(1923, 1)) {
        		player.getInventory().deleteItem(1927, 1);
        		player.getInventory().deleteItem(1923, 1);
        		player.getInventory().addItem(6697, 1);
        		sendNPCDialogue(npcId, 9827, "You have succesfully made a pat of cheese!");
        		player.getSkills().addXp(Skills.COOKING, 7);
        		} else  {
        		sendNPCDialogue(npcId, 9827, "You need a pat of butter and a bucket of milk to make a pat of cheese!");
        		}
    			stage = 4;
            break;
        	} else if(componentId == OPTION_3) {
        		if (player.getSkills().getLevel(Skills.COOKING) >= 48) {
        		if (player.getInventory().containsItem(1927, 1)) {
        		player.getInventory().deleteItem(1927, 1);
        		player.getInventory().addItem(18789, 1);
        		sendNPCDialogue(npcId, 9827, "You have succesfully made a cheese wheel!");
        		player.getSkills().addXp(Skills.COOKING, 15);
        		} else  {
        		sendNPCDialogue(npcId, 9827, "You need a bucket of milk to make a cheese wheel!");
        		}
        		} else {
        		sendNPCDialogue(npcId, 9827, "You need a cooking level of atleast 48 to create a cheese wheel.");	
        		}
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