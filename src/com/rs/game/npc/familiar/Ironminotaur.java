package com.rs.game.npc.familiar;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.Animation;
import com.rs.game.Graphics;

public class Ironminotaur extends Familiar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6059371477618091701L;

	public Ironminotaur(Player owner, Pouches pouch, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

    @Override
    public String getSpecialName() {
	return "Bull rush";
    }

    @Override
    public String getSpecialDescription() {
	return "A magical attack doing up to 40 life points of damage while stunning an opponent.";
    }

    @Override
    public int getBOBSize() {
	return 0;
    }

    @Override
    public int getSpecialAmount() {
	return 7;
    }

    @Override
    public SpecialAttack getSpecialAttack() {
	return SpecialAttack.ENTITY;
    }

    @Override
    public boolean submitSpecial(Object object) {
	getOwner().setNextGraphics(new Graphics(1316));
	getOwner().setNextAnimation(new Animation(7660));
	return true;
    }
}