package com.rs.game.player.content;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class TeleTabs {
	
	public enum Tabs {
		VARROCK_TELEPORT(8007, 25, 35, new Item(563, 1), new Item(554, 1), new Item(556, 3)),
		LUMBRIDGE_TELEPORT(8008, 31, 41, new Item(563, 1), new Item(557, 1), new Item(556, 3)),
		FALADOR_TELEPORT(8009, 37, 48, new Item(563, 1), new Item(555, 1), new Item(556, 3)),
		HOUSE_TELEPORT(8013, 40, 30, new Item(563, 1), new Item(557, 1), new Item(556, 1)),
		CAMELOT_TELEPORT(8010, 45, 55, new Item(563, 1), new Item(556, 5)),
		ARDOUGNE_TELEPORT(8011, 51, 61, new Item(563, 2), new Item(555, 2)),
		WATCHTOWER_TELEPORT(8012, 58, 68, new Item(563, 2), new Item(557, 2)),
		BONES_TO_BANANAS(8014, 15, 25, new Item(561, 1), new Item(557, 2), new Item(555, 2)),
		BONES_TO_PEACHES(8015, 60, 35, new Item(561, 2), new Item(557, 4), new Item(555, 4)),
		SAPPHIRE_ENCHANT(8016, 7, 13, new Item(564, 1), new Item(555, 1)),
		EMERALD_ENCHANT(8017, 27, 27, new Item(564, 1), new Item(556, 3)),
		RUBY_ENCHANT(8018, 49, 44, new Item(564, 1), new Item(554, 5)),
		DIAMOND_ENCHANT(8019, 57, 50, new Item(564, 1), new Item(557, 10)),
		DRAGONSTONE_ENCHANT(8020, 68, 58, new Item(564, 1), new Item(557, 15), new Item(555, 15)),
		ONYX_ENCHANT(8021, 87, 72, new Item(564, 1), new Item(554, 20), new Item(557, 20));
	
		private int id;
		private int xp;
		private int level;
		private Item[] reqs;
	
		private Tabs(int id, int level, int xp, Item... reqs) {
			this.id = id;
			this.level = level;
			this.xp = xp;
			this.reqs = reqs;
		}
		
		public int getXP() {
			return xp;
		}
		
		public int getId() {
			return id;
		}
		
		public int getLevel() {
			return level;
		}
		
		public Item[] getRequirements() {
		    return reqs;
		}
		
	}
	
	public static void makeTeletab(Player player, int slot, int n) {
		Tabs tablet = null;
		if (slot == 15)
			tablet = Tabs.VARROCK_TELEPORT;
		else if (slot == 13)
			tablet = Tabs.LUMBRIDGE_TELEPORT;
		else if (slot == 12)
			tablet = Tabs.FALADOR_TELEPORT;
		else if (slot == 14)
			tablet = Tabs.HOUSE_TELEPORT;
		else if (slot == 5)
			tablet = Tabs.CAMELOT_TELEPORT;
		else if (slot == 2)
			tablet = Tabs.ARDOUGNE_TELEPORT;
		else if (slot == 16)
			tablet = Tabs.WATCHTOWER_TELEPORT;
		else if (slot == 3)
			tablet = Tabs.BONES_TO_BANANAS;
		else if (slot == 4)
			tablet = Tabs.BONES_TO_PEACHES;
		else if (slot == 11)
			tablet = Tabs.SAPPHIRE_ENCHANT;
		else if (slot == 8)
			tablet = Tabs.EMERALD_ENCHANT;
		else if (slot == 10)
			tablet = Tabs.RUBY_ENCHANT;
		else if (slot == 6)
			tablet = Tabs.DIAMOND_ENCHANT;
		else if (slot == 7)
			tablet = Tabs.DRAGONSTONE_ENCHANT;
		else if (slot == 9)
			tablet = Tabs.ONYX_ENCHANT;
		String name = tablet.toString().toLowerCase().replace('_', ' ');
		player.getInterfaceManager().closeScreenInterface();
		if (player.getSkills().getLevel(Skills.MAGIC) < tablet.getLevel()) {
			player.sm("You must have a Magic level of atleast "+tablet.getLevel()+" to create a "+name+" tablets.");
			return;
		}
		if (!player.getInventory().containsItems(tablet.getRequirements()) && !player.getInventory().containsItem(1761, n)) {
			player.sm("You do not have the right requirements to create this amount of "+name+" tablets.");
			return;
		}
		for (Item item : tablet.getRequirements())
		    player.getInventory().deleteItem(item.getId(), item.getAmount()*n);
		player.getInventory().deleteItem(1761, n);
		player.getSkills().addXp(Skills.MAGIC, tablet.getXP());
		player.getInventory().addItem(tablet.getId(), n);
		player.sm("You successfully create "+n+" "+name+" tablets.");
		return;
	}

}
