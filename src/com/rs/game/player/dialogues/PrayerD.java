package com.rs.game.player.dialogues;

import com.rs.game.WorldObject;
import com.rs.game.player.content.BonesOnAltar;
import com.rs.game.player.content.BonesOnAltar.Bones;
import com.rs.game.player.content.SkillsDialogue;

public class PrayerD extends Dialogue {

		private Bones bones;
		private WorldObject object;

		@Override
		public void start() {
			this.bones = (Bones) parameters[0];
			this.object = (WorldObject) parameters[1];

			SkillsDialogue
					.sendSkillsDialogue(
							player,
							SkillsDialogue.OFFER,
							"How many would you like to offer?",
							player.getInventory().getItems()
									.getNumberOf(bones.getBone()),
							new int[] { bones.getBone().getId() }, null);
		}

		@Override
		public void run(int interfaceId, int componentId) {
			player.getActionManager().setAction(
					new BonesOnAltar(object, bones.getBone(), SkillsDialogue
							.getQuantity(player)));
		end();
	}

	@Override
	public void finish() {

	}

}