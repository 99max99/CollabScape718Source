package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;


public class Scattering {

	public enum Ash {
		IMPIOUS(20264, 4),
        ACCURSED(20266, 12),
        INFERNAL(20268, 62);

		private int id;
		private double experience;

		private static Map<Integer, Ash> ashes = new HashMap<Integer, Ash>();

		static {
			for (Ash ash : Ash.values()) {
				ashes.put(ash.getId(), ash);
			}
		}

		public static Ash forId(int id) {
			return ashes.get(id);
		}

		private Ash(int id, double experience) {
			this.id = id;
			this.experience = experience;
		}

		public int getId() {
			return id;
		}

		public double getExperience() {
			return experience;
		}

		public static void scatter(final Player player, int inventorySlot) {
			final Item item = player.getInventory().getItem(inventorySlot);
			if (item == null || Ash.forId(item.getId()) == null)
				return;
			if (player.getAshDelay() > Utils.currentTimeMillis())
				return;
			final Ash ash = Ash.forId(item.getId());
			final ItemDefinitions itemDef = new ItemDefinitions(item.getId());
			player.addAshDelay(1500);
			player.setNextAnimation(new Animation(445));
			if (ash.getId() == 20264) {
				player.setNextGraphics(new Graphics(56));
			} else if (ash.getId() == 20266) {
				player.setNextGraphics(new Graphics(47));
			} else if (ash.getId() == 20268) {
				player.setNextGraphics(new Graphics(40));
			}
			player.getPackets().sendGameMessage("You scatter the ashes in the wind.");
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					player.getInventory().deleteItem(item.getId(), 1);
					player.getSkills().addXp(Skills.PRAYER,
							ash.getExperience());
					stop();
				}

			}, 2);
		}
	}
	public static boolean scatter(final Player player, int slotId) {
		final Item item = player.getInventory().getItem(slotId);
		if (item == null || Ash.forId(item.getId()) == null)
			return false;
		if (player.getAshDelay() > Utils.currentTimeMillis())
			return true;
		final Ash ash = Ash.forId(item.getId());
		final ItemDefinitions itemDef = new ItemDefinitions(item.getId());
		player.addAshDelay(1500);
		player.setNextAnimation(new Animation(445));
		if (ash.getId() == 20264) {
			player.setNextGraphics(new Graphics(56));
		} else if (ash.getId() == 20266) {
			player.setNextGraphics(new Graphics(47));
		} else if (ash.getId() == 20268) {
			player.setNextGraphics(new Graphics(40));
		}
		player.getPackets().sendGameMessage("You scatter the ashes in the wind.");
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getInventory().deleteItem(item.getId(), 1);
				player.getSkills().addXp(Skills.PRAYER,
						ash.getExperience());
				stop();
			}

		}, 2);
		return false;
	}
}
