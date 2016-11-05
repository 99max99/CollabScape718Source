package com.rs.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.GlobalPlayerInfo;
import com.rs.utils.ChatColors;
import com.rs.utils.Misc;
import com.rs.utils.SerializableFilesManager;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since Aug 12, 2013
 */
public class CustomisedShop implements Serializable {

	public CustomisedShop(Player player) {
		this.player = player;
	}

	public static void searchByName(Player searcher, String itemName) {
		if (itemName == null)
			return;
		searcher.lock();
		List<Player> allPlayers = new ArrayList<Player>();
		for (Player player : World.getPlayers()) {
			if (player == null)
				continue;
			allPlayers.add(player);
		}
		for (Player player : GlobalPlayerInfo.get().getSavedPlayers()) {
			if (player == null || player.getCustomisedShop() == null)
				continue;
			if (!allPlayers.contains(player))
				allPlayers.add(player);
		}
		List<Object[]> sellers = new ArrayList<Object[]>();
		for (Player player : allPlayers) {
			if (player.getCustomisedShop() == null)
				continue;
			for (MyShopItem item : player.getCustomisedShop().getShopItems()) {
				if (item == null
						|| item.getItem() == null
						|| item.getItem().getName() == null) 
					continue;
				if (item.getItem().getName().toLowerCase().contains(itemName.toLowerCase())) {
					sellers.add(new Object[] { player.getDisplayName(), item.getItem().getId(), item.getPrice() });
				}
			}
		}

		int interfaceId = 275;
		int startLine = 10;
		if (searcher != null) {
			for (int i = 0; i < 300; i++) {
				searcher.getPackets().sendIComponentText(interfaceId, i, "");
			}
			ListIterator<Object[]> it$ = sellers.listIterator();
			while(it$.hasNext()) {
				Object[] entry = it$.next();
				searcher.getPackets().sendIComponentText(interfaceId, startLine++, "" + entry[0] + "'s shop is selling: " + ItemDefinitions.getItemDefinitions((int) entry[1]).getName() + " for " + Misc.format((int) entry[2]) + " coins.");
			}
			searcher.getPackets().sendIComponentText(interfaceId, 1, "Searching for \"" + itemName + "\"");
			searcher.getInterfaceManager().sendInterface(interfaceId);
			searcher.unlock();
		}
	}

	public void sendOwnShop() {
		player.getPackets().sendItems(100, new Item[] { });
		int interfaceId = 1171;
		Item[] itemsArray = new Item[getShopItems().size() + 1];
		for (int i = 0; i < getShopItems().size(); i++) {
			itemsArray[i] = getShopItems().get(i).getItem();
		}
		player.getAttributes().put("editting_own_store", true);
		player.getPackets().sendItems(100, itemsArray);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getInterfaceManager().sendInventoryInterface(1266);
		player.getPackets().sendUnlockIComponentOptionSlots(1266, 0, 0, 27, 0, 1, 2);
		player.getPackets().sendInterSetItemsOptionsScript(interfaceId, 7, 100, 8, 3, "Remove");
		player.getPackets().sendInterSetItemsOptionsScript(1266, 0, 93, 4, 7, "Sell");
		player.getPackets().sendUnlockIComponentOptionSlots(interfaceId, 7, 0, 24, 0, 1, 2, 3);
		player.getPackets().sendIComponentText(interfaceId, 27, "Customise Your Store");
		player.getPackets().sendIComponentText(interfaceId, 4, "Remove All Items");
		if (!player.getInterfaceManager().containsInterface(interfaceId))
			player.getInterfaceManager().sendInterface(interfaceId);
	}

	/**
	 * Sends the shop items to the player opening your shop
	 * @param opener The player opening your shop
	 */
	public void open(Player opener) {
		int interfaceId = 1171;
		Item[] itemsArray = new Item[getShopItems().size() + 1];
		for (int i = 0; i < getShopItems().size(); i++) {
			itemsArray[i] = getShopItems().get(i).getItem();
		}
		opener.getPackets().sendItems(100, itemsArray);
		opener.getPackets().sendInterSetItemsOptionsScript(interfaceId, 7, 100, 8, 3, "Check Price", "Purchase");
		opener.getPackets().sendUnlockIComponentOptionSlots(interfaceId, 7, 0, 24, 0, 1, 2, 3);
		opener.getAttributes().put("viewing_player_shop", player);
		opener.getPackets().sendIComponentText(interfaceId, 27, player.getDisplayName() + "'s Store");

		if (!opener.getInterfaceManager().containsInterface(interfaceId))
			opener.getInterfaceManager().sendInterface(interfaceId);
	}

	/**
	 * Adds an item to your shop items
	 * @param item The item to add
	 * @return
	 */
	public boolean addItem(MyShopItem item) { //TODO Kyle needs to fix :)
	/*	int amount = 0;
		int index = (int) player.getBank().getIntegerOf(995);
		if (index + amount >= Integer.MAX_VALUE || index + amount < 0){
			player.getDialogueManager().startDialogue("SimpleMessage", "Sell price too high. This will cause an overflow in your bank.");
			return false;
		} */
		if (getShopItems().size() >= 24) {
			player.sendMessage("You do not have any more room in your shop to add an item to it.");
			return false;
		}
		if (!getShopItems().contains(item)) 
			return getShopItems().add(item);
		else
			return false;
	}

	/**
	 * Removes an item from the player's shop items array.
	 * @param item The item to remove
	 * @return
	 */
	public boolean removeItem(MyShopItem item, boolean myShop) {
		if (getShopItems().contains(item)) {
			boolean removed = getShopItems().remove(item);
			if (removed) {
				if (player.getAttributes() != null && player.getAttributes().get("editting_own_store") == null) {
					//player.getBank().addItemMoneyPouch(995, item.getPrice(), true);
					//player.getBank().refreshItems();
				}
				if (World.containsPlayer(player.getUsername())) {
					if (!myShop) {
						player.sendMessage("<col=" + ChatColors.MILD_BLUE + ">" + item.getItem().getName() + " has been bought from your store!");
					player.getBank().addItem(995, item.getPrice(), true);
					player.getBank().refreshItems();
					}
				}
				if (!World.containsPlayer(player.getUsername())) {
					SerializableFilesManager.savePlayer(player);
				}
			}
			return removed;
		} else
			return false;
	}

	/**
	 * @return the shopItems
	 */
	public List<MyShopItem> getShopItems() {
		return shopItems;
	}

	/**
	 * The owner of the shop.
	 */
	private final Player player;

	/**
	 * The items in the shop.
	 */
	private final List<MyShopItem> shopItems = new ArrayList<MyShopItem>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 8858101569046698742L;


	/**
	 * The item with it's customised price.
	 * 
	 * @author Tyluur
	 * 
	 */
	public static class MyShopItem implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4578135011527893771L;

		public MyShopItem(Item item, int price) {
			this.item = item;
			this.price = price;
		}

		/**
		 * @return the price
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * @return the item
		 */
		public Item getItem() {
			return item;
		}

		private final Item item;

		private final int price;

	}

}
