package com.rs.game.player.content;

import com.rs.game.item.Item;
import com.rs.game.player.Player;

/**
 * 
 * @author Fuzen Seth
 * @since 14.12.2013
 * @information Represents a simple handler for item poisoning.
 * @updated 9.2.2014
 */
public class WeaponPoison {
	/**
	 * The items that can be poisoned.
	 */
	public static final int[] POISONABLE_ITEMS = {1215, 1203, 1205, 1207, 1209, 1211, 1213, 1215, 1217, 8872, 
		6591, 1237, 1239, 1241, 1243, 1245, 1247, 1249};
	/**
	 * Bolts that can be poisoned.
	 */
	public static final int[] POISONABLE_BOLTS = {};
	/**
	 * Regular weapon poison's item id.
	 */
	public static final int REGULAR_WEAPON_POISON = 187;
	
	/**
	 * Plus weapon poison's item id.
	 */
	public static final int PLUS_WEAPON_POISON = 5937;
	/**
	 * Super weapon poison's item id.
	 */
	public static final int SUPER_WEAPON_POISON = 5940;
	/**
	 * Items that can be used to poison a weapon.
	 */
	public static final int[] POISONING_ITEMS = {PLUS_WEAPON_POISON, REGULAR_WEAPON_POISON, SUPER_WEAPON_POISON};
	
	/**
	 * The actual item poisoning 'session'.
	 * @param player
	 * @param item
	 */
	public static final void poisonItem(Player player, Item item, Item usedWith) {
		if (player.isDead())
			return;
			player.sendMessage("You poison the "+getMessage(usedWith)+".");
			switch (item.getId()) {
			case REGULAR_WEAPON_POISON:
				player.getInventory().deleteItem(REGULAR_WEAPON_POISON, 1);
				break;
			case PLUS_WEAPON_POISON:
				player.getInventory().deleteItem(PLUS_WEAPON_POISON, 1);
				break;
			case SUPER_WEAPON_POISON:
				player.getInventory().deleteItem(SUPER_WEAPON_POISON, 1);
				break;
			}
			
		player.getInventory().addItem(getPoisonedId(player, item, usedWith), 0);
		player.getInventory().addItem(229, 1);
	}
	/**
	 * We will be getting the actual poisoned weapon' item id.
	 * @param player
	 * @param item
	 * @return
	 */
	public static final int getPoisonedId(Player player, Item item, Item usedWith) {
		switch (usedWith.getId()) {
		case 8872: //Bone dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 8874;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 8876;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 8878;
		case 1205: //Bronze dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1221;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5670;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5688;
		case 1215: //Dragon dagger.
		if (item.getId() == REGULAR_WEAPON_POISON)
			return 1231;
		else if (item.getId() == PLUS_WEAPON_POISON) 
			return 5680;
		else if (item.getId() == SUPER_WEAPON_POISON)
			return 5698;
		case 1203: //Iron dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1219;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5668;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5686;
		case 1207: //Steel dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1223;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5672;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5690;
		case 1217: //Black dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1233;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5682;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5700;
		case 6591: //White dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 6593;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 6595;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 6597;
		case 1209: //Mithril dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1225;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5674;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5692;
		case 1211: //Adamant dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1227;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5676;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5694;
		case 1213: //Rune dagger.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1229;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5678;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5696;
		case 1237: //Bronze spear.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1251;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5704;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5718;
		case 1239: //Iron spear.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1253;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5706;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5720;
		case 1241: //Steel spear.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1255;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5708;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5722;
		case 4580: //Black spear.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 4582;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5734;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5736;
		case 1243: //Mithril spear.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1257;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5710;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5724;
		case 1245: //Adamant spear.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1260;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5712;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5726;
		case 1247: //Rune spear.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1261;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 5714;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5728;
		case 1249: //Dragon spear.
			if (item.getId() == REGULAR_WEAPON_POISON)
				return 1263;
			else if (item.getId() == PLUS_WEAPON_POISON) 
				return 516;
			else if (item.getId() == SUPER_WEAPON_POISON)
				return 5730;
		}
		
		return getPoisonedId(player, item, usedWith);
}
	/**
	 * We get the shorten message. 
	 * @return
	 */
	private static String getMessage(Item usedWith) {
		if (usedWith.getName().contains("spear")) 
			return "spear";
		else if (usedWith.getName().contains("dagger"))
			return "dagger";
		return null;
	}
	/**
	 * We handle the item interaction.
	 * @param player
	 * @param item
	 * @return
	 */
	public static final boolean handleItemInteract(Player player, Item item , Item usedWith) {
	for (int i : POISONABLE_ITEMS) {
		if (item.getId() == i && usedWith.getId() == i)
			return false;
	for (int poisonables : POISONING_ITEMS)
		if ((item.getId() == poisonables) && (usedWith.getId() == i)) {
		player.addStopDelay(2);
		player.getInventory().deleteItem(usedWith.getId(), 1);
		poisonItem(player, item, usedWith);	
		return true;
	}
}
		return false;
	}
	
}