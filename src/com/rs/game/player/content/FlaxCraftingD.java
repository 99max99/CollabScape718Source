package com.rs.game.player.content;

import com.rs.game.player.actions.FlaxCrafting;
import com.rs.game.player.actions.FlaxCrafting.Orb;
import com.rs.game.player.dialogues.Dialogue;

public class FlaxCraftingD extends Dialogue {

	/**
	 * If you have more than one flax, this dialogue pops up
	 * 
	 * @author BongoProd
	 * 
	 */

	private Orb orb;

	@Override
	public void start() {
		this.orb = (Orb) parameters[0];
		SkillsDialogue
				.sendSkillsDialogue(
						player,
						SkillsDialogue.MAKE,
						"Choose how many you wish to make,<br>then click on the item to begin.",
						player.getInventory().getItems()
								.getNumberOf(orb.getUnMade()),
						new int[] { orb.getUnMade() }, null);

	}

	@Override
	public void run(int interfaceId, int componentId) {
		player.getActionManager().setAction(
				new FlaxCrafting(orb, SkillsDialogue.getQuantity(player)));

				
		end();
	}

	@Override
	public void finish() {

	}

}
