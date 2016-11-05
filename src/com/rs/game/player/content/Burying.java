package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class Burying {

	public enum Bone {
		NORMAL(526, 4.5),

		BURNT(528, 4.5),

		WOLF(2859, 4.5),

		MONKEY(3183, 5),

		BAT(530, 5.3),
		
		TROLL_LIEUTENANT(23032, 6.5),
		
		TROLL_GENERAL(23031, 6.5),

		BIG(532, 15),

		JOGRE(3125, 15),
		
		LONG(10976, 15),

		ZOGRE(4812, 22.5),

		SHAIKAHAN(3123, 25),

		BABY(534, 30),
		
		CURVED(10977, 30),

		WYVERN(6812, 50),

		DRAGON(536, 72),

		FAYRG(4830, 84),

		RAURG(4832, 96),

		DAGANNOTH(6729, 125),

		OURG(4834, 140),

		FROST_DRAGON(18830, 180),
		
		ANCIENT_BONES(15410, 180);

		private int id;
		private double experience;

		private static Map<Integer, Bone> bones = new HashMap<Integer, Bone>();

		static {
			for (Bone bone : Bone.values()) {
				bones.put(bone.getId(), bone);
			}
		}

		public static Bone forId(int id) {
			return bones.get(id);
		}

		private Bone(int id, double experience) {
			this.id = id;
			this.experience = experience;
		}

		public int getId() {
			return id;
		}

		public double getExperience() {
			return experience;
		}
		
		public static final Animation BURY_ANIMATION = new Animation(827);

		public static void bury(final Player player, int inventorySlot) {
			final Item item = player.getInventory().getItem(inventorySlot);
			if (item == null || Bone.forId(item.getId()) == null)
				return;
			if (player.getBoneDelay() > Utils.currentTimeMillis())
				return;
			final Bone bone = Bone.forId(item.getId());
			
			final ItemDefinitions itemDef = new ItemDefinitions(item.getId());
			player.getInventory().deleteItem(item.getId(), 1);
			player.addBoneDelay(3000);
			player.lock();
			player.getPackets().sendSound(2738, 0, 1);
			player.setNextAnimation(new Animation(827));
			player.isBurying = true;
			player.getPackets().sendGameMessage(
					"You dig a hole in the ground...");
			if (player.getEquipment().getAmuletId() == 19886) {
				if (player.getPrayer().getPrayerpoints() < (player.getSkills()
						.getLevelForXp(Skills.PRAYER) * 10)) {
				player.getPrayer().setPrayerpoints(player.getPrayer().getPrayerpoints() + 10);
				player.getPrayer().refreshPrayerPoints();
				}
			}
			if (player.getEquipment().getAmuletId() == 19887) {
				if (player.getPrayer().getPrayerpoints() < (player.getSkills()
						.getLevelForXp(Skills.PRAYER) * 10)) {
				if (bone.getId() == 526) {
					player.getPrayer().setPrayerpoints(player.getPrayer().getPrayerpoints() + 10);
				} else {
					player.getPrayer().setPrayerpoints(player.getPrayer().getPrayerpoints() + 20);
				}
				player.getPrayer().refreshPrayerPoints();
				}
			}
			if (player.getEquipment().getAmuletId() == 19888) {
				if (player.getPrayer().getPrayerpoints() < (player.getSkills()
						.getLevelForXp(Skills.PRAYER) * 10)) {
				if (bone.getId() == 532 || bone.getId() == 534 || bone.getId() == 6812) {
					player.getPrayer().setPrayerpoints(player.getPrayer().getPrayerpoints() + 20);
				} else if (bone.getId() == 536 || bone.getId() == 4834 || bone.getId() == 18830) {
					player.getPrayer().setPrayerpoints(player.getPrayer().getPrayerpoints() + 30);
				} else {
					player.getPrayer().setPrayerpoints(player.getPrayer().getPrayerpoints() + 10);
				}
				player.getPrayer().refreshPrayerPoints();
				}
			}
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					player.getPackets().sendGameMessage(
							"You bury the " + itemDef.getName().toLowerCase());
					player.unlock();
					player.getSkills().addXp(Skills.PRAYER,
							bone.getExperience());
					stop();
					player.isBurying = false;
				}

			}, 2);
		}
	}
}
