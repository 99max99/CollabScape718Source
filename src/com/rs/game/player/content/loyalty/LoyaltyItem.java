package com.rs.game.player.content.loyalty;

public class LoyaltyItem {

	/** The price */
	public int price;

	/** Favorited or not */
	public boolean favorited;

	/** Bought or not */
	public boolean bought;

	/** Slot id */
	public int slot;

	/** Regular ID */
	public int id;

	/** Hash ID */
	public int hash;

	/** Category */
	public String category;

	/**
	 * Constructs a new loyalty item
	 * 
	 * @param price
	 *            The price
	 * @param slot
	 *            The slot
	 */
	public LoyaltyItem(int price, int slot, int id, String category) {
		this.price = price;
		this.slot = slot;
		this.category = category;
		this.id = id;
	}

}