package com.rs.game.player.controlers.trollinvasion;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;

public class TrollMountain extends CombatScript{

	@Override
	public Object[] getKeys() {
		return new Object [] {"Mountain troll"};
 	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int hit = 0;
		hit = getRandomMaxHit(npc, 250, NPCCombatDefinitions.MELEE, target);
		npc.setNextAnimation(new Animation(13786));
		delayHit(npc, 2, target, getMeleeHit(npc, hit));
		return defs.getAttackDelay();
	}
	

}
