package com.rs.game.player.content;

import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class RingOfWealth {

	public enum rare_drop {
		COMMON(new Object[][] { { 1624, 10, 30 }, { 1622, 8, 22 },
				{ 1619, 5, 14 }, { 995, 25000, 49000 }, { 995, 30000, 30000 },
				{ 995, 90000, 90000 }, { 995, 111500, 111500 },
				{ 995, 120000, 120000 }, { 314, 60, 450 }, { 314, 65, 65 },
				{ 1777, 20, 45 }, }), UNCOMMON(new Object[][] { { 1617, 1, 1 },
				{ 985, 1, 1 }, { 987, 1, 1 }, { 830, 5, 5 },
				{ 9143, 200, 200 }, { 1215, 1, 1 }, { 533, 151, 500 },
				{ 536, 25, 55 }, { 2999, 25, 250 }, { 258, 33, 33 },
				{ 3001, 32, 120 }, { 270, 10, 100 }, { 454, 150, 7500 },
				{ 450, 150, 800 }, { 452, 70, 125 }, { 7937, 100, 14500 },
				{ 1441, 25, 35 }, { 1443, 25, 56 }, { 372, 125, 1000 },
				{ 384, 250, 500 }, { 5321, 3, 3 }, { 1631, 1, 1 },
				{ 18778, 1, 1 }, }), RARE(new Object[][] {
				{ 995, 25000, 49000 }, { 995, 30000, 30000 },
				{ 995, 90000, 90000 }, { 995, 111500, 111500 },
				{ 995, 120000, 120000 }, { 372, 250, 1000 }, { 384, 350, 350 },
				{ 990, 2, 2 }, { 1247, 1, 1 }, { 1319, 1, 1 }, { 1373, 1, 1 },
				{ 1149, 1, 1 }, { 563, 450, 450 }, { 560, 50, 500 },
				{ 561, 470, 770 }, { 566, 200, 200 }, { 565, 500, 500 },
				{ 892, 150, 500 }, { 1615, 1, 1 }, { 1392, 200, 200 },
				{ 574, 1000, 1000 }, { 570, 1000, 1000 }, { 452, 1, 100 },
				{ 2362, 1450, 7000 }, { 1516, 100, 4500 }, { 1514, 50, 350 },
				{ 21620, 4, 4 }, { 9342, 150, 150 }, { 15273, 100, 100 },
				{ 15273, 65, 350 }, }), ULTRARARE(new Object[][] {
				{ 1201, 1, 1 }, { 1217, 50, 50 }, { 20667, 1, 1 },
				{ 9144, 1, 1 }, { 6685, 250, 250 }, { 2363, 1, 1 },
				{ 2364, 50, 50 }, { 2364, 150, 150 }, { 6571, 1, 1 },
				{ 6572, 2, 2 }, { 9194, 12, 84 }, { 18778, 1, 1 }, });

		@SuppressWarnings("unused")
		private int id;
		private Object[][] data;

		@SuppressWarnings("unused")
		private rare_drop drops;

		private rare_drop(Object[][] data) {
			this.data = data;
		}

	}

	public static RingOfWealth random(Player player, rare_drop drops) {
		RingOfWealth drop = null;
		while (true) {
			int random = Utils.random(drops.data.length - 1);
			int itemid = (Integer) drops.data[random][0];
			int min = (Integer) drops.data[random][1];
			int max = (Integer) drops.data[random][2];
			if (drop == null) {
				drop = new RingOfWealth(drops, itemid, Utils.random(min, max));
			}
			break;
		}
		return drop;
	}
	@SuppressWarnings("unused")
	private rare_drop drop;
	private int itemId;

	private int Amount;

	public RingOfWealth(rare_drop drop, int itemId, int Amount) {
		this.drop = drop;
		this.itemId = itemId;
		this.Amount = Amount;
	}

	public int getAmount() {
		return Amount;
	}

	public int getItemId() {
		return itemId;
	}

}