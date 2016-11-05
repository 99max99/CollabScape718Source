package com.rs.game.player.content;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.rs.game.item.Item;
import com.rs.game.player.Player;

/**
 * Handles a {@link Player}'s toolbelt.
 * 
 * @author Thomas Le Godais
 * 
 */
public class ToolBelt implements Serializable {

	private static final long serialVersionUID = 7706200801855080675L;
	private transient Player player;

	private static final int[] TOOLBELT_ITEMS = new int[] { 946, 1735, 1595, 1755, 1599, 1597, 1733, 1592, 5523, 13431, 307, 309, 311, 301, 303, 1265, 2347, 1351, 590, -1, 8794, 4, 9434, 11065, 1785, 2976, 1594, 5343, 5325, 5341, 5329, 233, 952, 305, 975, 11323, 2575, 2576, 13153, 10150 };
	private static final int[] CONFIG_IDS = new int[] { 2438, 2439 };

	private Map<Integer, Boolean> items;

	public ToolBelt(Player player) {
		this.player = player;
	}

	public void refresh() {
		if (items == null) {
			items = new HashMap<Integer, Boolean>();
			for (int itemId : TOOLBELT_ITEMS) {
				items.put(itemId, false);
			}
		}
		int[] configValue = new int[CONFIG_IDS.length];
		for (int i = 0; i < TOOLBELT_ITEMS.length; i++) {
			if (items.get(TOOLBELT_ITEMS[i]) == null) {
				continue;
			}
			boolean inToolbelt = items.get(TOOLBELT_ITEMS[i]);
			if (!inToolbelt) {
				continue;
			}
			int index = (i / 20);
			configValue[index] |= 1 << (i - (index * 20));
		}
		for (int i = 0; i < CONFIG_IDS.length; i++) {
			if (configValue[i] == 0) {
				continue;
			}
			player.getPackets().sendConfig(CONFIG_IDS[i], configValue[i]);
		}
	}

	public boolean addItem(Item item) {
		if (items.get(item.getId()) == null) {
			return false;
		}
		if (items.get(item.getId())) {
			player.sendMessage("This item is already in your toolbelt.");
			return false;
		}
		items.put(item.getId(), true);
		player.sendMessage("You add the " + item.getName() + " to your toolbelt.");
		refresh();
		return true;
	}

	public boolean contains(int itemId) {
		if (items.get(itemId) == null) {
			return false;
		}
		return items.get(itemId);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}