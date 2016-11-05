package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * 
 * @author Jae
 *
 */

public class SunfreetCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 15222 };
	}


	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(1) == 0) {
			switch (Utils.getRandom(10)) {
			case 0:
				npc.setNextAnimation(new Animation(16317));
			for (Entity t : npc.getPossibleTargets()) {
				if (!t.withinDistance(npc, 18))
					continue;
				int damage = getRandomMaxHit(npc, defs.getMaxHit(),
						NPCCombatDefinitions.MAGE, t) / 2;
				if (damage > 0) {
					delayHit(npc, 1, t, getMagicHit(npc, damage));
					delayHit(npc, 1, t, getMagicHit(npc, damage));
					t.setNextGraphics(new Graphics (3002));
					t.setNextGraphics(new Graphics (3003));
					t.setNextGraphics(new Graphics (3005));
					npc.setNextAnimation(new Animation(16317));
				}
			}
			npc.setNextAnimation(new Animation(16318));
			break;
		case 1:
			npc.setNextAnimation(new Animation(16315));
			for (Entity t : npc.getPossibleTargets()) {
				if (!t.withinDistance(npc, 18))
					continue;
				int damage = getRandomMaxHit(npc, defs.getMaxHit(),
						NPCCombatDefinitions.MAGE, t) * 2 + 100;
				if (damage > 0) {
					World.sendProjectile(npc, target, 3004, 60, 32, 50, 50, 0, 0);
					delayHit(npc, 1, t, getMagicHit(npc, damage));
					delayHit(npc, 1, t, getMagicHit(npc, damage));
					t.setNextGraphics(new Graphics (3002));
					t.setNextGraphics(new Graphics (3003));
					t.setNextGraphics(new Graphics (3005));
				}
			}
			break;
		case 2:
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			npc.setCapDamage(800);
			delayHit(
					npc,
					0,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, defs.getMaxHit(),
									NPCCombatDefinitions.MELEE, target)));
			break;
		case 3:
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			npc.setCapDamage(800);
			delayHit(
					npc,
					0,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, defs.getMaxHit(),
									NPCCombatDefinitions.MELEE, target)));
			break;
		}
		}
		if (Utils.getRandom(4) == 3) {
			npc.setCapDamage(800);
			npc.setNextAnimation(new Animation(16317));
			npc.setNextAnimation(new Animation(16318));
			for (@SuppressWarnings("unused") Entity t : npc.getPossibleTargets()) {
				if (!target.withinDistance(npc, 18))
					continue;
				int damage = getRandomMaxHit(npc, defs.getMaxHit(),
						NPCCombatDefinitions.MELEE, target) * 2 + Utils.random(100);
				if (damage > 0) {
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							target.applyHit(new Hit(npc, Utils.random(650),
									HitLook.MAGIC_DAMAGE));
							target.applyHit(new Hit(npc, Utils.random(350),
									HitLook.MAGIC_DAMAGE));
							npc.heal(Utils.random(200));
					World.sendProjectile(npc, target, 3004, 60, 32, 50, 50, 0, 0);
					delayHit(npc, 1, target, getMagicHit(npc, Utils.random(1000)));
					delayHit(npc, 1, target, getMagicHit(npc, Utils.random(1000)));
					target.setNextGraphics(new Graphics (3002));
					target.setNextGraphics(new Graphics (3003));
					npc.setNextAnimation(new Animation(16317));
						}
						}, 7);

		} else {
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			npc.setCapDamage(800);
			delayHit(
					npc,
					0,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, defs.getMaxHit(),
									NPCCombatDefinitions.MELEE, target)));
		}
		return defs.getAttackDelay();
			}
		}
		int shit = Utils.random(4);
		return shit;
	}
}
