package com.rs.game.player.controlers.trollinvasion;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;


public class CliffCombat extends CombatScript{

	@Override
	public Object[] getKeys() {
		return new Object [] {"Cliff", 13381};
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(2) == 0) { // range 
			npc.setNextAnimation(new Animation(1933));
				delayHit(
						npc,
						1,
						target,
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 335,
										NPCCombatDefinitions.RANGE, target)));
				target.setNextGraphics(new Graphics(755));
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
		}
		return defs.getAttackDelay();
	}
}
