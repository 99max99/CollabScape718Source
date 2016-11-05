package com.rs.game.player.dialogues.item;

import com.rs.game.Animation;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;


public class PolyVisor extends Dialogue {
	
	

	@Override
	public void start() {
		sendOptionsDialogue("Crafting a Visor",
				"Fungal - 300 flakes",
				"Grifolic - 400 flakes",
				"Ganodermic - 500 flakes");
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			if (componentId == OPTION_1 && player.getInventory().containsItem(22449, 300)
                    && player.getInventory().containsItem(22452, 1)
                    && (player.getSkills().getLevel(Skills.CRAFTING) > 2)) {
				end();
				player.setNextAnimation(new Animation(1249));
				 player.getInventory().deleteItem(22449, 300);
                 player.getInventory().deleteItem(22452, 1);
                 player.getInventory().addItem(22458, 1);
                 player.getSkills().addXp(Skills.CRAFTING, 80);                 
				
			} else if (componentId == OPTION_1 && !player.getInventory().containsItem(22449, 300)
                    || !player.getInventory().containsItem(22452, 1)
                    || (player.getSkills().getLevel(Skills.CRAFTING) < 3)) {
				end();
				player.getDialogueManager().startDialogue("SimpleMessage","You need 300 Fungal flakes, a Mycelium visor and 3 crafting to do this.");
                     
				return;
			} else if (componentId == OPTION_2 && player.getInventory().containsItem(22450, 400)
                    && player.getInventory().containsItem(22452, 1)
                    && (player.getSkills().getLevel(Skills.CRAFTING) > 64)) {
				end();
				player.setNextAnimation(new Animation(1249));
				 player.getInventory().deleteItem(22450, 400);
                 player.getInventory().deleteItem(22452, 1);
                 player.getInventory().addItem(22470, 1);
                 player.getSkills().addXp(Skills.CRAFTING, 100);
				
			} else if (componentId == OPTION_2
					&& !player.getInventory().containsItem(22450, 400)
                    || !player.getInventory().containsItem(22452, 1)
                    || (player.getSkills().getLevel(Skills.CRAFTING) < 65)) {
				end();
				player.getDialogueManager().startDialogue("SimpleMessage","You need 400 Grifo flakes, a Mycelium visor and 65 crafting to do this.");
                     
				return;
			} else if (componentId == OPTION_3 && player.getInventory().containsItem(22451, 500)
                    && player.getInventory().containsItem(22452, 1)
                    && (player.getSkills().getLevel(Skills.CRAFTING) > 85)) {
				end();
				player.setNextAnimation(new Animation(1249));
				 player.getInventory().deleteItem(22451, 500);
                 player.getInventory().deleteItem(22452, 1);
                 player.getInventory().addItem(22482, 1);
                 player.getSkills().addXp(Skills.CRAFTING, 150);
			
			} else if (componentId == OPTION_3
					&& !player.getInventory().containsItem(22451, 500)
                    || !player.getInventory().containsItem(22452, 1)
                    || (player.getSkills().getLevel(Skills.CRAFTING) < 86)) {
				end();
				player.getDialogueManager().startDialogue("SimpleMessage","You need 500 Gano flakes, a Mycelium visor and 86 crafting to do this.");
                     
				return;
			}
		}
		 
	}

	@Override
	public void finish() {

	}
}