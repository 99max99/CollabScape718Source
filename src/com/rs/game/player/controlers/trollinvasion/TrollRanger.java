package com.rs.game.player.controlers.trollinvasion;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;


public class TrollRanger extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { "Troll ranger" };
	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int damage = getRandomMaxHit(npc, 300, NPCCombatDefinitions.RANGE, target);
		npc.setNextGraphics(new Graphics(850));
		npc.setNextAnimation(new Animation(2134));
	//	World.sendProjectile(npc, target, 32, 34, 16, 30, 35, 16, 0);// 32
		delayHit(npc, 2, target, getRangeHit(npc, damage));
		return defs.getAttackDelay();
	}

}
