package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;
import com.rs.game.item.Item;
import com.rs.game.player.Player;

public class RepairItems {

	public enum BrokenItems {
//Main structure NAME(Broken itemId, repaired itemId,Price to repair it)
		GUTHANS_HELM(4908, 4724, 1000000),
		
		GUTHANS_PLATEBODY(4920, 4738, 1000000),
		
		GUTHANS_PLATESKIRT(4926, 4730, 1000000),
		
		GUTHANS_WARSPEAR(4914, 4726, 1000000),
		
		AHRIMS_HOOD(4860, 4708, 1000000),
		
		AHRIMS_STAFF(4866, 4710, 1000000),
		
		AHRIMS_ROBE_TOP(4872, 4712, 1000000),
		
		AHRIMS_ROBE_SKIRT(4878, 4714, 1000000),
		
		DHAROKS_HELM(4884, 4716, 1000000),
		
		DHAROKS_GREATAXE(4890, 4718, 1000000),
		
		DHAROKS_PLATEBODY(4896, 4720, 1000000),
		
		DHAROKS_PLATELEGS(4902, 4722, 1000000),
		
		KARILS_COIF(4932, 4732, 1000000),
		
		KARILS_CROSSBOW(4938, 4734, 1000000),
		
		KARILS_TOP(4944, 4736, 1000000),
		
		KARILS_SKIRT(4950, 4738, 1000000),
		
		TORAGS_HELM(4956, 4745, 1000000),
		
		TORAGS_HAMMER(4962, 4747, 1000000),
		
		TORAGS_PLATEBODY(4968, 4749, 1000000),
		
		TORAGS_PLATELEGS(4974, 4751, 1000000),
		
		VERACS_HELM(4980, 4753, 1000000),
		
		VERACS_FLAIL(4968, 4755, 1000000),
		
		VERACS_BRASSARD(4992, 4757, 1000000),
		
		VERACS_PLATESKIRT(4998, 4759, 1000000),

		TORVA_HELM(20138, 20135, 50000000),

		TORVA_PLATE(20142, 20139, 50000000),

		TORVA_LEGS(20146, 20143, 50000000),

		TORVA_GLOVES(24979, 24977, 50000000),

		TORVA_BOOTS(24985, 24983, 50000000),

		PERNIX_COWL(20150, 20147, 50000000),

		PERNIX_BODY(20154, 20151, 50000000),

		PERNIX_CHAPS(20158, 20155, 50000000),

		PERNIX_GLOVES(24976, 24974, 50000000),

		PERNIX_BOOTS(24991, 24989, 50000000),
		
		ZARYTE_BOW(20174, 20171, 75000000),

		VIRTUS_MASK(20162, 20159, 50000000),

		VIRTUS_TOP(20166, 20163, 50000000),

		VIRTUS_LEGS(20170, 20167, 50000000),

		VIRTUS_GLOVES(24982, 24980, 50000000),

		VIRTUS_BOOTS(24988, 24986, 50000000);
		
		
		private int id;
		private int id2;
		private int Price;

		private static Map<Integer, BrokenItems> BROKENITEMS = new HashMap<Integer, BrokenItems>();

		static {
			for (BrokenItems brokenitems : BrokenItems.values()) {
				BROKENITEMS.put(brokenitems.getId(), brokenitems);
			}
		}

		public static BrokenItems forId(int id) {
			return BROKENITEMS.get(id);
		}

		private BrokenItems(int id, int id2, int Price) {
			this.id = id;
			this.id2 = id2;
			this.Price = Price;
		}

		public int getId() {
			return id;
		}
		
		public int getId2() {
		return id2;
		}

		public int getPrice() {
		return Price;
		}
		
		
}
	public static void Repair(Player player,int itemId,int amount) {
	final BrokenItems brokenitems = BrokenItems.forId(itemId);
	Item item = new Item(brokenitems.getId(), 1);
	int price = brokenitems.getPrice();
	if (amount == 1) {
	if (player.getInventory().containsItem(995, price)) {
	player.getInventory().deleteItem(itemId, 1);
	player.getInventory().removeItemMoneyPouch(995, price);
	player.getInventory().addItem(brokenitems.getId2(), 1);
	player.getDialogueManager().startDialogue("SimpleMessage","You have repaired your item("+item.getName()+") for "+getFormattedNumber(price)+" coins.");
	return;
		} else {
	player.getDialogueManager().startDialogue("SimpleMessage","You dont have enough money to repair this item."+"You need "+getFormattedNumber(price)+" coins.");
	return;
	}
		} else {
	if (player.getInventory().containsItem(995, price * amount)) {
	player.getInventory().deleteItem(itemId, amount);
	player.getInventory().removeItemMoneyPouch(995, price * amount);
	player.getInventory().addItem(brokenitems.getId2(), amount);
	player.getDialogueManager().startDialogue("SimpleMessage","You have repaired your items("+amount+" X "+item.getName()+") for "+getFormattedNumber(price * amount)+" coins.");
	return;
		} else {
	player.getDialogueManager().startDialogue("SimpleMessage","You dont have enough money to repair these items."+"You need "+getFormattedNumber(price * amount)+" coins.");
	return;
	}
	}
}
	public static void CheckPrice(Player player,int itemId, int amount) {
	final BrokenItems brokenitems = BrokenItems.forId(itemId);
	int price = brokenitems.getPrice();
	player.getDialogueManager().startDialogue("SimpleNPCMessage",945,
							"These items will cost you " +getFormattedNumber(price * amount)+" coins.");
					return;
	}
private static String getFormattedNumber(int amount) {
		return new DecimalFormat("#,###,##0").format(amount).toString();
	}

}