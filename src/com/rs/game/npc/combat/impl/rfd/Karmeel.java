package com.rs.game.npc.combat.impl.rfd;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * 
 * @author Adam
 * @since Aug,2nd.
 *
 */

public class Karmeel extends CombatScript{

	@Override
	public Object[] getKeys() {
		
		return new Object[] {3495};
	}
	
	/**
	 * npc.setNextAnimation(new Animation(1979));
      target.setNextGraphics(new Graphics(369));
      target.addFrozenBlockedDelay(100);
 World.sendProjectile(npc, target, 368, 60, 32, 50, 50, 0, 0);
		target.applyHit(new Hit(target, Utils.random(110, 135), Hit.HitLook.MAGIC_DAMAGE));;// testiing it has more wa
		return defs.getAttackDelay();
	 */

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (Utils.getRandom(4) == 0
						&& target.getFreezeDelay() < System
								.currentTimeMillis()) {
					   int newX =npc.getX() - 3;
		                int newY = npc.getY();
		               npc.addWalkSteps(newX, newY, -1, false);
					 npc.setNextAnimation(new Animation(1979));
					 npc.setNextForceTalk(new ForceTalk(("Semolina-Go!")));
					target.addFreezeDelay(3000);
					target.setNextGraphics(new Graphics(369));
					target.applyHit(new Hit(target, Utils.random(150, 160), Hit.HitLook.MAGIC_DAMAGE));
					if (target instanceof Player) {
						Player targetPlayer = (Player) target;
						targetPlayer.stopAll();
					}
				} else {
					 npc.setNextAnimation(new Animation(1979));
					target.applyHit(new Hit(target, Utils.random(150, 160), Hit.HitLook.MAGIC_DAMAGE));
				}
		}}, 1);
		
	
		return defs.getAttackDelay();
	}
}
