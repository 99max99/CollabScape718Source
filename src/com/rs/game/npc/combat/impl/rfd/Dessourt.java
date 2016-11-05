package com.rs.game.npc.combat.impl.rfd;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;

/**
 * 
 * @author Adam
 * @since Aug, 2nd.
 *
 */

public class Dessourt extends CombatScript{
	@Override
	public Object[] getKeys() {
		
		return new Object[] {3496};
	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(3) == 0) {
	npc.setNextAnimation(new Animation(3508));
		target.applyHit(new Hit(target, Utils.random(160, 170), Hit.HitLook.REGULAR_DAMAGE));;// testiing it has more wa
		npc.setNextGraphics(new Graphics(550));
		npc.setNextForceTalk(new ForceTalk("Hssssssssssss"));
		}
		if (Utils.getRandom(3) == 0) {
			npc.setNextAnimation(new Animation(3508));
			target.applyHit(new Hit(target, Utils.random(160, 170), Hit.HitLook.MAGIC_DAMAGE));// testiing it has more wa
		}
		return defs.getAttackDelay();
}
}
 