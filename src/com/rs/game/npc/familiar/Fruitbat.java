package com.rs.game.npc.familiar;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.utils.Utils;

public class Fruitbat extends Familiar {

	/**
	 * 
	 */
	
    private static final transient int[] FRUITS = new int[] { 5972, 5974, 2102, 2120, 1963, 2108, 5982 };

    private int fruitTicks;
    
	private static final long serialVersionUID = 6059371477618091701L;

	public Fruitbat(Player owner, Pouches pouch, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

    @Override
    public void processNPC() {
	super.processNPC();
	fruitTicks++;
	if (fruitTicks == 500)
	    addFruitReward();
    }

    private void addFruitReward() {
	getBob().getBeastItems().add(new Item(FRUITS[Utils.random(FRUITS.length)], 1));
	fruitTicks = 0;
    }

    @Override
    public String getSpecialName() {
	return "Fruitfall";
    }

    @Override
    public String getSpecialDescription() {
	return "Bat can get up to eight fruit and drop them on the ground around the player.";
    }

    @Override
    public int getBOBSize() {
	return 30;
    }

    @Override
    public int getSpecialAmount() {
	return 6;
    }

    @Override
    public SpecialAttack getSpecialAttack() {
	return SpecialAttack.CLICK;
    }

    @Override
    public boolean submitSpecial(Object object) {
	Player player = (Player) object;
	player.setNextAnimation(new Animation(7660));
	player.setNextGraphics(new Graphics(1316));
	WorldTile tile = this;
	for (int trycount = 0; trycount < Utils.getRandom(8); trycount++) {
	    tile = new WorldTile(this, 2);
	    World.sendGraphics(player, new Graphics(1331), tile);
	    World.addGroundItem(new Item(FRUITS[Utils.random(FRUITS.length)], 1), tile, player, true, 120, false);
	}
	return false;
    }
}