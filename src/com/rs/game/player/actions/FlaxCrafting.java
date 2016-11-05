package com.rs.game.player.actions;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class FlaxCrafting extends Action {

	/**
	 * Enum for Flax | I was too bored to make new names for everything, ignore it.
	 * 
	 * @author BongoProd
	 * 
	 */
	public enum Orb {
		AIR_ORB(1779, 1777, 76, 1, 897, -1);


		private double experience;
		private int levelRequired;
		private int unmade, made;

		private int emote;
		private int gfxId;

		private Orb(int unmade, int made, double experience, int levelRequired,
				int emote, int gfxId) {
			this.unmade = unmade;
			this.made = made;
			this.experience = experience;
			this.levelRequired = levelRequired;
			this.emote = emote;
			this.gfxId = gfxId;
		}

		public int getLevelRequired() {
			return levelRequired;
		}

		public double getExperience() {
			return experience;
		}

		public int getUnMade() {
			return unmade;
		}

		public int getMade() {
			return made;
		}

		public int getEmote() {
			return emote;
		}

		public int getGfxId() {
			return gfxId;
		}

	}

	public static void make(Player player, Orb orb) {
		if (player.getInventory().getItems()
				.getNumberOf(new Item(orb.getUnMade(), 1)) <= 1) // contains just
			// 1 lets start
			player.getActionManager().setAction(new FlaxCrafting(orb, 1));
		else
			player.getDialogueManager().startDialogue("FlaxCraftingD", orb);
	}

	private Orb orb;
	private int quantity;

	public FlaxCrafting(Orb orb, int quantity) {
		this.orb = orb;
		this.quantity = quantity;
	}

	public boolean checkAll(Player player) {
		if (player.getSkills().getLevel(Skills.CRAFTING) < orb
				.getLevelRequired()) {
			player.getDialogueManager().startDialogue(
					"SimpleMessage",
					"You need a Crafting level of " + orb.getLevelRequired()
					+ " to make the bow string.");
			return false;
		}
		if (!player.getInventory().containsItem(1779, 1)) {
			player.getDialogueManager().startDialogue(
					"SimpleMessage",
					"You need atleast one flax to make a bow string.");
			return false;
		}
		if (player.getInventory().containsOneItem(orb.getUnMade())) {
			return true;
		}
		return true;
	}

	@Override
	public boolean start(Player player) {
		if (checkAll(player)) {
			setActionDelay(player, 4);
			player.setNextAnimation(new Animation(orb.getEmote()));
			return true;
		}
		return false;
	}

	@Override
	public boolean process(Player player) {
		return checkAll(player);
	}

	@Override
	public int processWithDelay(Player player) {
		player.getInventory().deleteItem(orb.getUnMade(), 1);
		player.getInventory().addItem(orb.getMade(), 1);
		player.getSkills().addXp(Skills.CRAFTING, orb.getExperience());
		player.getPackets().sendGameMessage(
				"You make the "
						+ ItemDefinitions.getItemDefinitions(orb.getUnMade())
						.getName().toLowerCase() + " into a bow string.", true);
		quantity--;
		if (quantity <= 0)
			return -1;
		player.setNextAnimation(new Animation(orb.getEmote()));
		return 0;
	}

	@Override
	public void stop(final Player player) {
		setActionDelay(player, 3);
	}
}
