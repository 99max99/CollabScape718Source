package com.rs.game.npc.combat.impl;

import java.util.ArrayList;

import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.World;
import com.rs.game.Animation;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.utils.Utils;

public class VoragoMinionCombat extends CombatScript {
	
	private int AttackStyle;
	public static boolean EnrageMessage;
	private VoragoCombat rago;
	
	@Override
	public Object[] getKeys() {
		return new Object[] { 17185, 17157, 17158 };
		
	}
	
	/** 17185 - Scopulus
	 * 17157 - Vitalis
	 * 17158 - Stone Clone
	 * **/
	
	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		final ArrayList<Entity> possibleTargets = npc.getPossibleTargets();
		final Player player = (Player) target;
		final int id = npc.getId();
		if (id == 17158) {//Stone clone
		npc.setName("Stone "+player.getDisplayName()+"");
		npc.setCombatLevel(player.getSkills().getCombatLevelWithSummoning());
		if (player.getCombatDefinitions().getBonuses()[0] > player.getCombatDefinitions().getBonuses()[3] &&
				player.getCombatDefinitions().getBonuses()[0] > player.getCombatDefinitions().getBonuses()[4]
						|| player.getCombatDefinitions().getBonuses()[1] > player.getCombatDefinitions().getBonuses()[3] &&
						player.getCombatDefinitions().getBonuses()[1] > player.getCombatDefinitions().getBonuses()[4]
						|| player.getCombatDefinitions().getBonuses()[2] > player.getCombatDefinitions().getBonuses()[3] &&
						player.getCombatDefinitions().getBonuses()[2] > player.getCombatDefinitions().getBonuses()[4]) {
			AttackStyle = 2;
		} else if (player.getCombatDefinitions().getBonuses()[4] > player.getCombatDefinitions().getBonuses()[3] &&
				player.getCombatDefinitions().getBonuses()[4] > player.getCombatDefinitions().getBonuses()[0]
						&& player.getCombatDefinitions().getBonuses()[4] > player.getCombatDefinitions().getBonuses()[1] &&
						player.getCombatDefinitions().getBonuses()[4] > player.getCombatDefinitions().getBonuses()[2]) {
			AttackStyle = 3;
		}	else {
			AttackStyle = 1;
		}
			if (AttackStyle == 1) { // Mage Clone
			npc.setNextAnimation(new Animation(17310));
			npc.setNextGraphics(new Graphics(3304));
				delayHit(
						npc,
						0,
						target,
						getMagicHit(
								npc,
								getRandomMaxHit(npc, 355,
										1000, target)));
				

			} else if (AttackStyle == 2) { // Melee clone 
				npc.setNextAnimation(new Animation(17304));
				delayHit( 
						npc,
						2,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, target)));
				
		} else { //Range Clone
			npc.setNextAnimation(new Animation(10504));
			npc.setNextGraphics(new Graphics(1838));
			delayHit( 
					npc,
					1,
					target,
					getRangeHit(
							npc,
							getRandomMaxHit(npc, defs.getMaxHit(),
									NPCCombatDefinitions.RANGE, target)));
			
		} 	
		} else if (id == 17157) {//Vitalis
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			delayHit(npc, 0, target, getMeleeHit(npc, getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.MELEE, target)));
			if (Utils.random(5) == 0) {
				player.addFreezeDelay(5000);
				player.addFoodDelay(5000);
				player.getPackets().sendGameMessage("The Vitalis stuns you.");
			}
		} else {//Scopulus
			final int damage = getRandomMaxHit(npc, defs.getMaxHit(),
					NPCCombatDefinitions.MELEE, target);
			npc.setForceFollowClose(true);
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			delayHit(npc, 0, target, getMeleeHit(npc, damage));
		
			
			if (rago.ScopEnrage == 2 && EnrageMessage == false) {
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
						p.getPackets().sendGameMessage("The remaining Scopulus becomes enraged as its twin dies.");
						EnrageMessage = true;
					}
				}
						
			}
		}
		if (id == 17185) {//Scopulus
		return defs.getAttackDelay()/rago.ScopEnrage;
		} else {//Vitalis or Stone clone
			return defs.getAttackDelay();
		}
	}
}