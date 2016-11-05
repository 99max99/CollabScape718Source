package com.rs.game.player.controlers.trollinvasion;


import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

@SuppressWarnings("serial")
public class Cliff_Troll extends NPC{
	
	private TrollInvasion controler;

	public Cliff_Troll(int id, WorldTile tile, TrollInvasion controler) {
		super(id, tile, -1, true, true);
		this.controler = controler;
		setForceMultiArea(true);
		setNoDistanceCheck(true);
		setForceAgressive(true);
	}
	
	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		resetWalkSteps();
		controler.addKill();
		getCombat().removeTarget();
		setNextAnimation(null);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
				} else if (loop >= defs.getDeathDelay()) {
					reset();
					finish();
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}


}
