package com.rs.game.npc.combat.impl.rfd;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;

/**
 * 
 * @author Taylor/ Edited Adam.
 *
 */

public class Agrith extends CombatScript{

	@Override
	public Object[] getKeys() {

		return new Object []{3493};
	}

	
	/**private boolean isDistant(NPC npc, Entity target) {
		int size = npc.getSize();
		if(size > target.getX() || target.getY()) {
			return true;
		} brb
		return false;
	}*/
	
	@Override
	public int attack(final NPC npc, final Entity target) {
		
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(4) == 0) {
			
		
						///now it will only do the next one instead of 2 hits thats op brb fix the  the what? like it doesnt do an anim just hits, cant  iwde kg thie anivm..  idk it
			npc.setNextGraphics(new Graphics(129));
			npc.setNextAnimation(new Animation(3501));
		target.applyHit(new Hit(target, Utils.random(90, 100), Hit.HitLook.REGULAR_DAMAGE));;// testiing it has more wait tho i was lvl 3 wen i got owned fix it
		if (Utils.getRandom(4) == 0) {
			npc.setNextGraphics(new Graphics(129));
			npc.setNextAnimation(new Animation(3501));
			target.applyHit(new Hit(target, Utils.random(80, 95), Hit.HitLook.REGULAR_DAMAGE));;// testiing it has more wait tho i was lvl
		}
	} else {
		npc.setNextGraphics(new Graphics(129));
		npc.setNextAnimation(new Animation(3501));
		target.applyHit(new Hit(target, Utils.random(80, 90), Hit.HitLook.REGULAR_DAMAGE));;// testiing it has more wait tho i was lvl 3 wen i got owned fix it
		
	}
		return defs.getAttackDelay();

	}
}