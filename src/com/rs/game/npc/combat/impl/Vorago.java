package com.rs.game.npc.combat.impl;

import java.util.ArrayList;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.utils.Utils;



/*
 * Made By: Jason
 * 			
 * 
 * 
 */
public class Vorago extends CombatScript{
	

	@Override
	public Object[] getKeys() {
		return new Object[] { 14741 };
	}

	@Override
	public int attack(NPC npc, Entity target) {
		final ArrayList<Entity> possibleTargets = npc.getPossibleTargets();
		Entity targets[] = possibleTargets.toArray(new Entity[possibleTargets.size()]);
		int[] randomHeal = {100,200,300,400};
		//Player pt = (Player) target;
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if(Utils.getRandom(15) == 0){
			for (final Entity t : possibleTargets) {
				if (t instanceof Player) {
					final Player p = (Player) t;
			p.getPackets().sendGameMessage("<col=ff0000>Vorago is about to use a massive magic attack, use protect from magic to avoid the damage!</col>");
				}
			}
			for (Entity t : npc.getPossibleTargets()) {
				delayHit(
						npc,
						10,
						t,
						getMagicHit(
								npc,
								getRandomMaxHit(npc, 800,
										NPCCombatDefinitions.MAGE, t)));
				World.sendProjectile(npc, t, 2938, 85, 16, 40, 300, 16, 0);
			}
		}else if(Utils.getRandom(15) == 0){
			for (final Entity t : possibleTargets) {
				if (t instanceof Player) {
					final Player p = (Player) t;
			p.getPackets().sendGameMessage("<col=ff0000>Vorago is about to use a massive range attack, use protect from range to avoid the damage!</col>");
				}
			}
			for (Entity t : npc.getPossibleTargets()) {
				delayHit(
						npc,
						10,
						t,
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 800,
										NPCCombatDefinitions.RANGE, t)));
				World.sendProjectile(npc, t, 2939, 85, 16, 40, 300, 16, 0);	
			}
		} else if(Utils.getRandom(10) == 0) {
			for (final Entity t : possibleTargets) {
				if (t instanceof Player) {
					final Player p = (Player) t;
			p.getPackets().sendGameMessage("<col=ff0000>Vorago leeches life from you!</col>");
				}
			}
			for (Entity t : npc.getPossibleTargets()) {
				int healAmount = randomHeal[Utils.getRandom(3)];
				delayHit(npc, 0, t, new Hit(npc, healAmount, HitLook.REGULAR_DAMAGE));
				npc.heal(healAmount);
				World.sendProjectile(npc, t, 2939, 85, 16, 40, 35, 16, 0);	
			}
		} else{
			
			npc.setNextAnimation(new Animation(12204));
			if(Utils.getRandom(8) ==0){
				final Player p = (Player) target;
				p.getPackets().sendGameMessage("<col=ff0000>Vorago Crushes you and completely bypasses your armor and prayer!</col>");
				delayHit(npc, 1, target, new Hit(npc, getRandomMaxHit(npc, 450,NPCCombatDefinitions.MELEE,target), HitLook.REGULAR_DAMAGE));
			} else {
			delayHit(
					npc,
					1,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, 400,
									NPCCombatDefinitions.MELEE, target)));
			}
		}
		return defs.getAttackDelay();
	}
	
	public void VoragoHeal(NPC npc){
		
	}

}
