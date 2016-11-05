package com.rs.game.npc.familiar;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Summoning.Pouches;

public class Spiritkalphite extends Familiar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6059371477618091701L;

	public Spiritkalphite(Player owner, Pouches pouch, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

    @Override
    public String getSpecialName() {
	return "Sandstorm";
    }

    @Override
    public String getSpecialDescription() {
	return "Attacks up to five opponents with a max damage of 50.";
    }

    @Override
    public int getBOBSize() {
	return 0;
    }

    @Override
    public int getSpecialAmount() {
	return 6;
    }

    @Override
    public SpecialAttack getSpecialAttack() {
	return SpecialAttack.ENTITY;
    }

    @Override
    public boolean submitSpecial(Object object) {
	return false;
    }
}
