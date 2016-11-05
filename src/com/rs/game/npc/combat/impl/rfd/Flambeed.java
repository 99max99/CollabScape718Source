package com.rs.game.npc.combat.impl.rfd;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Hit;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;

public class Flambeed extends CombatScript{

	@Override
	public Object[] getKeys() {
		// TODO Auto-generated method stub
		return new Object[] {3494};
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		
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
				target.applyHit(new Hit(target, Utils.random(120, 130), Hit.HitLook.REGULAR_DAMAGE));;// testiing it has more wait tho i was lvl 3 wen i got owned fix it
				npc.setNextAnimation(new Animation(1750));
		return defs.getAttackDelay();

	}
}
