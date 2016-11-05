package com.rs.game.player.actions;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;

/**
 * 
 * @author Trenton Basically just searched for every item with "of water" in it
 *         and added filling for it :P
 */

public class FillAction extends Action {

	private int amount;
	private Animation FILLING = new Animation(883);
	private Filler fil;

	public enum Filler {
		VIAL(new Item(229, 1), new Item(227, 1)),
		CLAY(new Item(434, 1), new Item(1761, 1)),
		BOWL(new Item(1923, 1), new Item(1921, 1)), 
		BUCKET(new Item(1925, 1), new Item(1929, 1)), 
		VASE(new Item(3734, 1), new Item(3735, 1)), 
		JUJU_VIAL(new Item(19996, 1), new Item(19994, 1)), 
		JUG(new Item(1935, 1), new Item(1937, 1));

		private static Map<Short, Filler> items = new HashMap<Short, Filler>();

		public static Filler forId(short itemId) {
			return items.get(itemId);
		}

		static {
			for (Filler ingredient : Filler.values()) {
				items.put((short) ingredient.getEmptyItem().getId(), ingredient);
			}
		}

		private Item empty;
		private Item filled;

		private Filler(Item empty, Item filled) {
			this.empty = empty;
			this.filled = filled;
		}

		public Item getEmptyItem() {
			return empty;
		}

		public Item getFilledItem() {
			return filled;
		}
	}

	public static Filler isFillable(Item item) {
		return Filler.forId((short) item.getId());
	}

	public FillAction(int amount, Filler fil) {
		this.amount = amount;
		this.fil = fil;
	}

	@Override
	public boolean start(Player player) {
		player.getPackets().sendGameMessage("You start filling!", true);
		return true;
	}

	@Override
	public boolean process(Player player) {
		if (!player.getInventory().containsItem(fil.getEmptyItem().getId(), 1)) {
			return false;
		}
		return true;
	}

	@Override
	public int processWithDelay(Player player) {
		amount--;
		player.setNextAnimation(FILLING);
		player.getInventory().deleteItem(fil.getEmptyItem().getId(), 1);
		player.getInventory().addItem(fil.getFilledItem().getId(), 1);
		if (amount > 0) {
			return 1;
		}
		return -1;
	}

	@Override
	public void stop(Player player) {

	}
}
