package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;


public class ZombieChampion extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 3066 };
	}

	
	@Override
	public int attack(final NPC npc, final Entity target) {
		if(npc.getHitpoints() < npc.getMaxHitpoints()/2 && Utils.random(5) == 0) { //if lower than 50% hp, 1/5 prob of healing 10%
			npc.heal(90);
			npc.setNextForceTalk(new ForceTalk("MY HEALTH REGAINS!!!!!!"));
		}
		npc.setCombatLevel(500);
		npc.setHitpoints(900);
		npc.setName("Grandpa Zombie!");
		npc.setCapDamage(500);
		
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(4) == 0) {
			switch (Utils.getRandom(10)) {
			case 0:
				npc.setNextForceTalk(new ForceTalk("Death to our enemies!"));
				break;
			case 1:
				npc.setNextForceTalk(new ForceTalk("Brargh!"));
				break;
			case 2:
				npc.setNextForceTalk(new ForceTalk("Break their bones!"));
				break;
			case 3:
				npc.setNextForceTalk(new ForceTalk("Now we CHARGEE!"));
				break;
			case 4:
				npc.setNextForceTalk(new ForceTalk("Split their skulls!"));
				break;
			case 5:
				npc.setNextForceTalk(new ForceTalk(
						"We shall eat there brains!"));
				break;
			case 6:
				npc.setNextForceTalk(new ForceTalk("CHAAARGE!"));
				break;
			case 7:
				npc.setNextForceTalk(new ForceTalk("Crush them underfoot!"));
				break;
			case 8:
				npc.setNextForceTalk(new ForceTalk("ALL THE FAME BEGINS NOW!"));
				break;
			case 9:
				npc.setNextForceTalk(new ForceTalk("GRAAAAAAAAAR!"));
				break;
			case 10:
				npc.setNextForceTalk(new ForceTalk(
						"FOR US WE KILL!"));
				break;
			}
		}
		if (Utils.getRandom(2) == 0) { // range magical attack
			for (Entity t : npc.getPossibleTargets()) {
				delayHit(
						npc,
						1,
						t,
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 355,
										NPCCombatDefinitions.RANGE, t)));
				World.sendProjectile(npc, t, 1200, 41, 16, 41, 35, 16, 0);
			   npc.setNextGraphics(new Graphics(365));
			   target.applyHit(new Hit(target, Utils.random(90, 140), Hit.HitLook.RANGE_DAMAGE));
			}
		} else { // melee attack
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			delayHit(
					npc,
					0,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, defs.getMaxHit(),
									NPCCombatDefinitions.MELEE, target)));
			  target.applyHit(new Hit(target, Utils.random(90, 140), Hit.HitLook.REGULAR_DAMAGE));
		}
		return defs.getAttackDelay();
	}
}
