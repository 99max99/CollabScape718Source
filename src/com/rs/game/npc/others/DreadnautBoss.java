package com.rs.game.npc.others;

import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.controlers.DreadnautControler;
import com.rs.utils.Utils;

@SuppressWarnings("serial")
public class DreadnautBoss extends NPC {

	private DreadnautControler dreadnaut;

	public DreadnautBoss(int id, WorldTile tile, DreadnautControler dreadnaut) {
		super(id, tile, -1, true, true);
		this.dreadnaut = dreadnaut;
	}

	@Override
	public void sendDeath(Entity source) {
		if(dreadnaut != null) {
			dreadnaut.targetDied();
			dreadnaut = null;
		}
		super.sendDeath(source);
	}
	
	@Override
	public double getMeleePrayerMultiplier() {
		return getId() != 2030 ? 0 : Utils.random(3) == 0 ? 1 : 0;
	}
	
	
	public void disapear() {
		dreadnaut = null;
		finish();
	}
	@Override
	public void finish() {
		if(hasFinished())
			return;
		if(dreadnaut != null) {
			dreadnaut.targetFinishedWithoutDie();
			dreadnaut = null;
		}
		super.finish();
	}

}
