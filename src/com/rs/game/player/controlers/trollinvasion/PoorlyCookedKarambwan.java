package com.rs.game.player.controlers.trollinvasion;

import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;



public class PoorlyCookedKarambwan extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { "Poorly cooked Karambwan", 13669 };
	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
	npc.setForceAgressive(true);
		int hit = 0;
		int attackStyle = Utils.random(2);
		switch (attackStyle) {
		case 0:
			target.setNextForceTalk(new ForceTalk("*cough*"));
			npc.setNextForceTalk(new ForceTalk("*erk*"));
			hit = getRandomMaxHit(npc, 200, NPCCombatDefinitions.MELEE, target);
			delayHit(npc, 1, target, getMeleeHit(npc, hit));
			break;
		case 1:
			hit = getRandomMaxHit(npc, 200, NPCCombatDefinitions.MELEE, target);
			delayHit(npc, 1, target, getMeleeHit(npc, hit));
			break;
		}
		return defs.getAttackDelay();
	}

}
