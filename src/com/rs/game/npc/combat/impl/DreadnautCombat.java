package com.rs.game.npc.combat.impl;

import com.rs.game.task.impl.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.World;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;;

public class DreadnautCombat extends CombatScript {



 @Override
	public Object[] getKeys() {
		return new Object[] { 12862 };
	}

 @Override
	public int attack(final NPC npc, final Entity target) {
	 Player targetPlayer = (Player) target;
		if (npc.getHitpoints() < npc.getMaxHitpoints() / 2
				&& Utils.random(5) == 0) { // if lower than 50% hp, 1/5 prob of
											// healing 10%
			npc.heal(30);
		}

		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(2) == 0) { // magical attack
			//npc.playSound(168, 2);
			npc.setNextAnimation(new Animation(14973));
			for (Entity t : npc.getPossibleTargets()) {
				delayHit(
						npc,
						1,
						t,
						getMagicHit(
								npc,
								getRandomMaxHit(npc, 200,
										NPCCombatDefinitions.MAGE, t)));
				World.sendProjectile(npc, t, 368, 41, 16, 41, 35, 16, 0);
				target.setFreezeDelay(300);
				target.setNextGraphics(new Graphics(369));
				npc.setNextForceTalk(new ForceTalk("You stand no chance!"));
			}
			loadSkillDrop(targetPlayer);
		} else if (Utils.getRandom(2) == 1) {
			npc.setNextAnimation(new Animation(14973));
			loadSkillDrop(targetPlayer);
			delayHit(
					npc,
					1,
					target,
					getRangeHit(
							npc,
							getRandomMaxHit(npc, 250,
									NPCCombatDefinitions.RANGE, target)));
			World.sendProjectile(npc, target, 1197, 41, 16, 41, 35, 16, 0);
			if (Utils.getRandom(4) == 0)
				target.getPoison().makePoisoned(50);
			npc.setNextForceTalk(new ForceTalk("I will end you!"));
		} else { // melee attack
			npc.setNextAnimation(new Animation(14973));
			delayHit(
					npc,
					0,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, 350,
									NPCCombatDefinitions.MELEE, target)));
		}
		return defs.getAttackDelay();
	}


}