package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;

/**
 * 
 * @author Mario(AlterOPSnet)
 * 
 *
 */

public class AirWizardCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 2712 };
	}
	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		
		npc.setNextAnimation(new Animation(14221));
		
		World.sendProjectile(npc,
								target,
										2699, 40, 40, 50, 50, 0, 0);
		delayHit(npc,
				14,
					target,
						getMagicHit(npc, Utils.random(70)));
		//target.setNextGraphics(new Graphics(2723, 100, 100));
		
				return defs.getAttackDelay();

	}
}