package com.rs.game.player.controlers.trollinvasion;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;


public class TrollGeneral extends CombatScript {

	@Override
	public Object[] getKeys() {

		return new Object[] { "Troll general", 12291 };
	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int hit = 0;
		npc.setNextAnimation(new Animation(13788));
	
			hit = getRandomMaxHit(npc, Utils.random(300, 400), NPCCombatDefinitions.MELEE, target);
			delayHit(npc, 1, target, getMeleeHit(npc, hit));
		return defs.getAttackDelay();
	}

}
