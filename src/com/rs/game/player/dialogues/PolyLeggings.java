package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.player.Skills;


public class PolyLeggings extends Dialogue {
	
	

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Crafting Leggings ",
				"Fungal - 1,000 flakes",
				"Grifolic - 1,200 flakes",
				"Ganodermic - 1,500 flakes");
		
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1 && player.getInventory().containsItem(22449, 1000)
                    && player.getInventory().containsItem(22454, 1)
                    && (player.getSkills().getLevel(Skills.CRAFTING) > 11)) {
				end();
				player.setNextAnimation(new Animation(1249));
				 player.getInventory().deleteItem(22449, 1000);
                 player.getInventory().deleteItem(22454, 1);
                 player.getInventory().addItem(22462, 1);
                 player.getSkills().addXp(Skills.CRAFTING, 80);                 
				
			} else if (componentId == OPTION_1 && !player.getInventory().containsItem(22449, 1000)
                    || !player.getInventory().containsItem(22454, 1)
                    || (player.getSkills().getLevel(Skills.CRAFTING) < 12)) {
				end();
				player.getDialogueManager().startDialogue("SimpleMessage","You need 1000 Fungal flakes, Mycelium leggings and 12 crafting to do this.");
                     
				return;
			} else if (componentId == OPTION_2 && player.getInventory().containsItem(22450, 1200)
                    && player.getInventory().containsItem(22454, 1)
                    && (player.getSkills().getLevel(Skills.CRAFTING) > 71)) {
				end();
				player.setNextAnimation(new Animation(1249));
				 player.getInventory().deleteItem(22450, 1200);
                 player.getInventory().deleteItem(22454, 1);
                 player.getInventory().addItem(22474, 1);
                 player.getSkills().addXp(Skills.CRAFTING, 100);
				
			} else if (componentId == OPTION_2
					&& !player.getInventory().containsItem(22450, 1200)
                    || !player.getInventory().containsItem(22454, 1)
                    || (player.getSkills().getLevel(Skills.CRAFTING) < 72)) {
				end();
				player.getDialogueManager().startDialogue("SimpleMessage","You need 1200 Grifo flakes, Mycelium leggings and 72 crafting to do this.");
                     
				return;
			} else if (componentId == OPTION_3 && player.getInventory().containsItem(22451, 1500)
                    && player.getInventory().containsItem(22454, 1)
                    && (player.getSkills().getLevel(Skills.CRAFTING) > 91)) {
				end();
				player.setNextAnimation(new Animation(1249));
				 player.getInventory().deleteItem(22451, 1500);
                 player.getInventory().deleteItem(22454, 1);
                 player.getInventory().addItem(22486, 1);
                 player.getSkills().addXp(Skills.CRAFTING, 150);
			
			} else if (componentId == OPTION_3
					&& !player.getInventory().containsItem(22451, 1500)
                    || !player.getInventory().containsItem(22454, 1)
                    || (player.getSkills().getLevel(Skills.CRAFTING) < 92)) {
				end();
				player.getDialogueManager().startDialogue("SimpleMessage","You need 1500 Gano flakes, Mycelium leggings and 92 crafting to do this.");
                     
				return;
			}
		}
		 
	}

	@Override
	public void finish() {

	}
}
