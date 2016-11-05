package com.rs.game.npc.familiar;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.npc.familiar.Familiar;


public class Pyrelord extends Familiar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6059371477618091701L;

	public Pyrelord(Player owner, Pouches pouch, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

    @Override
    public String getSpecialName() {
	return "Immense Heat";
    }

    @Override
    public String getSpecialDescription() {
	return "Craft a gold bar (and a gem if one wishes) into an item of Jewellery without using a furnace.";
    }

    @Override
    public int getBOBSize() {
	return 0;
    }

    @Override
    public int getSpecialAmount() {
	return 5;
    }

    @Override
    public SpecialAttack getSpecialAttack() {
	return SpecialAttack.ITEM;
    }

    @Override
    public boolean submitSpecial(Object object) {
	return false;
    }

}
