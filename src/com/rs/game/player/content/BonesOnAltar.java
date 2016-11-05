package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.game.player.actions.Cooking.Cookables;

@SuppressWarnings("unused")
public class BonesOnAltar extends Action {
	
	public final String MESSAGE = "The gods are very pleased with your offerings.";
	public final double MULTIPLIER = 3.5;
	
	public enum Bones {
		BONES(new Item(526, 1), 5),
		BURNT_BONES(new Item(528, 1), 5),
		WOLF_BONES(new Item(2859, 1), 5),
		MONKEY_BONES(new Item(3183, 1), 5),
		BAT_BONES(new Item(530, 1), 5),
		BIG_BONES(new Item(532, 1), 15),
		JOGRE_BONES(new Item(3125, 1), 15),
		ZOGRE_BONES(new Item(4812, 1), 23),
		BABYDRAGON_BONES(new Item(534, 1), 30),
		WYVERN_BONES(new Item(6812, 1), 50),
		DRAGON_BONES(new Item(536, 1), 72),
		FAYRG_BONES(new Item(4830, 1), 84),
		RAURG_BONES(new Item(4832, 1), 96),
		OURG_BONES(new Item(4834, 1), 140),
		FROST_DRAGON_BONES(new Item(18830, 1), 180),
		DAGANNOTH_BONES(new Item(6729, 1), 125);
	
		private static Map<Short, Bones> bones = new HashMap<Short, Bones>();
	
		public static Bones forId(short itemId) {
			return bones.get(itemId);
		}
	
		static {
			for (Bones bone: Bones.values()) {
				bones.put((short) bone.getBone().getId(), bone);
			}
		}
	
		private Item item;
		private int xp;
	
		private Bones(Item item, int xp) {
			this.item = item;
			this.xp = xp;
		}
		
		public Item getBone() {
			return item;
		}
		
		public int getXP() {
			return xp;
		}
	}
		
	private Bones bone;
	private int amount;
	private Item item;
	private WorldObject object;
	private Animation USING = new Animation(896);
	
	public BonesOnAltar(WorldObject object, Item item, int amount) {
		this.amount = amount;
		this.item = item;
		this.object = object;
	}
	
	public static Bones isGood(Item item) {
		return Bones.forId((short) item.getId());
	}
	
	public boolean start(Player player) {
		if((this.bone = Bones.forId((short) item.getId())) == null) {
			return false;
		}
		player.faceObject(object);
		return true;
	}
	
	public boolean process(Player player) {
		if (!World.containsObjectWithId(
						object, object.getId()))
			return false;
		if (!player.getInventory().containsItem(item.getId(), 1)) {
			return false;
		}
		if (!player.getInventory().containsItem(bone.getBone().getId(), 1)) {
			return false;
		}
		return true;
	}
	
	public int processWithDelay(Player player) {
			player.setNextAnimation(USING);
			player.getPackets().sendGraphics(new Graphics(624), object);
			player.getInventory().deleteItem(item.getId(), 1);
			player.getSkills().addXp(Skills.PRAYER, bone.getXP()*MULTIPLIER);
			player.getPackets().sendGameMessage(MESSAGE);
			player.getInventory().refresh();
			return 3;
	}

	public void stop(final Player player) {
		this.setActionDelay(player, 3);
	}
	
}