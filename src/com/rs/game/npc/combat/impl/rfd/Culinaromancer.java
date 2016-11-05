package com.rs.game.npc.combat.impl.rfd;

import com.rs.game.Animation;
import com.rs.game.Entity;
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

public class Culinaromancer extends CombatScript{

	@Override
	public Object[] getKeys() {
		return new Object[] {3491};
	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		/**
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				delayHit(
						npc,
						0,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, target)));
				*///now it will only do the next one instead of 2 hits thats op brb fix the  the what? like it doesnt do an anim just hits, cant  iwde kg thie anivm..  idk it
				target.applyHit(new Hit(target, Utils.random(180, 180), Hit.HitLook.REGULAR_DAMAGE));;// testiing it has more wait tho i was lvl 3 wen i got owned fix it
				npc.setNextAnimation(new Animation(805));
		return defs.getAttackDelay();
	}

}
