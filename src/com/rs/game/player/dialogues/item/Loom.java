package com.rs.game.player.dialogues.item;

import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;

/**
 * @author Danny
 */


public class Loom extends Dialogue {

	public Loom() {
	}

	@Override
	public void start() {
		sendOptionsDialogue("Chose a Product.", "Sack", "Basket", "Unfinished Net", "Strip of Cloth");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		 switch (stage) {
	        case -1:
		if(componentId == OPTION_1) {
			if (player.getSkills().getLevel(Skills.CRAFTING) >= 21) {
    		if (player.getInventory().containsItem(5913, 4)) {
    		player.getInventory().deleteItem(5913, 4);
    		player.getInventory().addItem(5418, 1);
    		player.sm("You have succesfully made an empty sack!");
    		player.getSkills().addXp(Skills.CRAFTING, 4);
    		} else  {
    		player.sm("You need four jute fibres to make an empty sack!");
    		}
			} else {
			player.sm("You need a crafting level of atleast 21 to make this item.");	
			}
    		stage = 4;
            break;
		} else if(componentId == OPTION_2) {
			if (player.getSkills().getLevel(Skills.CRAFTING) >= 36) {
	    		if (player.getInventory().containsItem(5933, 6)) {
	    		player.getInventory().deleteItem(5933, 6);
	    		player.getInventory().addItem(5376, 1);
	    		player.sm("You have succesfully made a basket!");
	    		player.getSkills().addXp(Skills.CRAFTING, 6);
	    		} else  {
	    		player.sm("You need six jute fibres to make a basket!");
	    		}
				} else {
				player.sm("You need a crafting level of atleast 36 to make this item.");	
				}
	    		stage = 4;
	            break;
		} else if(componentId == OPTION_3) {
			if (player.getSkills().getLevel(Skills.CRAFTING) >= 42) {
	    		if (player.getInventory().containsItem(1779, 5)) {
	    		player.getInventory().deleteItem(1779, 5);
	    		player.getInventory().addItem(14858, 1);
	    		player.sm("You have succesfully made an unfinished net!");
	    		player.getSkills().addXp(Skills.CRAFTING, 12);
	    		} else  {
	    		player.sm("You need five bunches of flax to make an unfinished net!");
	    		}
				} else {
				player.sm("You need a crafting level of atleast 42 to make this item.");	
				}
	    		stage = 4;
	            break;
		} else if(componentId == OPTION_4) {
			if (player.getSkills().getLevel(Skills.CRAFTING) >= 10) {
	    		if (player.getInventory().containsItem(1759, 4)) {
	    		player.getInventory().deleteItem(1759, 4);
	    		player.getInventory().addItem(3224, 1);
	    		player.sm("You have succesfully made a strip of cloth!");
	    		player.getSkills().addXp(Skills.CRAFTING, 4);
	    		} else  {
	    		player.sm("You need four balls of wool to make a strip of cloth!");
	    		}
				} else {
				player.sm("You need a crafting level of atleast 10 to make this item.");	
				}
	    		stage = 4;
	            break;
		}
	        case 4:
	        	end();
	        	break;
	 }
		
	}

	@Override
	public void finish() {
		
	}
	
}