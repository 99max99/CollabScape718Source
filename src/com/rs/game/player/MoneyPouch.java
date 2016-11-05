package com.rs.game.player;

import java.io.Serializable;

import com.rs.utils.Utils;

public class MoneyPouch implements Serializable {

	private static final long serialVersionUID = -1699637125839716969L;

    private transient Player player;
    private boolean usingPouch;
    private int coinAmount;

    public void setPlayer(Player player) {
	this.player = player;
    }

    public void switchPouch() {
	usingPouch = !usingPouch;
	swap();
    }

    public void init() {
	if (usingPouch)
	    swap();
	refreshCoins();
    }

    private void swap() {
	player.getPackets().sendRunScript(5557, 1);
    }

    public void examinePouch() {
	player.getPackets().sendGameMessage("Your money pouch currently contains " + Utils.getFormattedNumber(coinAmount) + " coins.");
    }

    public void withdrawPouch() {
	player.getPackets().sendInputIntegerScript("Your money pouch contains " + Utils.getFormattedNumber(coinAmount) + " coins.<br>How many would you like to withdraw?");
	player.getTemporaryAttributtes().put("withdrawingPouch", Boolean.TRUE);
    }

    private void refreshCoins() {
	player.getPackets().sendRunScript(5560, coinAmount);
    }

    public boolean sendDynamicInteraction(int amount, boolean remove) {
	return sendDynamicInteraction(amount, remove, TYPE_INV);
    }

    public static final int TYPE_POUCH_INVENTORY = 0, TYPE_REMOVE = 1,
	    TYPE_INV = 2;

    /*
     * TYPE_POUCH_INVENTORY - from pouch to inventory TYPE_REMOVE - remove from
     * pouch as much as it can(example bank) TYPE_INV - remove/add from pouch
     * and if not enough, inventory
     */
    public boolean sendDynamicInteraction(int amount, boolean remove, int type) {
	if (amount == 0)
	    return false;
	if (remove) {
	    if (type == TYPE_POUCH_INVENTORY) {
		if (amount > coinAmount)
		    amount = coinAmount;
		int invAmt = player.getInventory().getAmountOf(995);
		if (coinAmount != 0 && invAmt + amount <= 0) {
		    amount = Integer.MAX_VALUE - invAmt;
		    player.getPackets().sendGameMessage("Not enough space in your inventory.");
		}
	    } else if (type == TYPE_INV && amount > coinAmount) {
		int removeAmt = amount - coinAmount;
		if (player.getInventory().getAmountOf(995) < removeAmt)
		    return false;
		player.getInventory().deleteItem(995, removeAmt);
		amount -= removeAmt;
	    }
	} else if (!remove && amount + coinAmount <= 0) {
	    if (type == TYPE_INV) // added from somewhere else example shop but
				  // moneypouch full so adds to inv
		player.getInventory().addItem(995, amount - (Integer.MAX_VALUE - coinAmount));
	    else
		player.getPackets().sendGameMessage("Your money-pouch is currently full. Your coins will now go to your inventory.");
	    amount = Integer.MAX_VALUE - coinAmount;
	}
	if (amount == 0)
	    return true;
	player.getPackets().sendGameMessage(Utils.getFormattedNumber(amount) + " coins have been " + (remove ? "removed" : "added") + " to your money pouch.");
	if (type == TYPE_POUCH_INVENTORY) {
	    if (remove) {
		if (!player.getInventory().addItem(995, amount))
		    return false;
	    } else
		player.getInventory().deleteItem(995, amount);
	}
	setAmount(amount, remove);
	return true;
    }

    void setAmount(int amt, boolean remove) {
	if (remove)
	    coinAmount -= amt;
	else
	    coinAmount += amt;
	player.getPackets().sendRunScript(5561, remove ? 0 : 1, amt);
	refreshCoins();
	if (coinAmount < 0 || coinAmount >= 100000000)
		player.warningLog(player);
    }

    public int getCoinsAmount() {
	return coinAmount;
    }
}