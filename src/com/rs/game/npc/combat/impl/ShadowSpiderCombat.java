package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;


public class ShadowSpiderCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { "Shadow Spider", 58};
	}

	@Override
	public int attack(NPC npc, Entity target) {//Shadow Spider prayer drain
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int damage = 0;
		
		npc.setNextAnimation(new Animation(defs.getAttackEmote()));
		damage = getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MELEE, target);
		if (target instanceof Player)
			((Player) target).getPrayer().drainPrayer(495);
		delayHit(npc, 0, target, getMeleeHit(npc, damage));
		return defs.getAttackDelay();
	}
}
