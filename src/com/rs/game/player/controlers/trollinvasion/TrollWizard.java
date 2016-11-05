package com.rs.game.player.controlers.trollinvasion;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;


public class TrollWizard extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 12435 };

	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int hit = 0;
		npc.setNextAnimation(new Animation(1938));
		hit = getRandomMaxHit(npc, 300, NPCCombatDefinitions.MELEE, target);
		delayHit(npc, 2, target, getMeleeHit(npc, hit));
		return defs.getAttackDelay();
	}

}
