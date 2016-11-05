package com.rs.game.npc.vorago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.Hit.HitLook;
import com.rs.game.WorldTile;
import com.rs.game.npc.combat.impl.VoragoCombat;
import com.rs.game.npc.combat.impl.VoragoMinionCombat;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

@SuppressWarnings({ "serial", "unused" })
public class VoragoMinions extends NPC {
	
	public static final List<Player> playersOn = Collections
			.synchronizedList(new ArrayList<Player>());

	public VoragoMinions(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		final NPCCombatDefinitions defs = getCombatDefinitions();
		setForceFollowClose(true);
		setCapDamage(500);
	}
	

	@Override
	public void sendDeath(Entity source) {
		setNextAnimation(new Animation(getCombatDefinitions().getDeathEmote()));
		WorldTasksManager.schedule(new WorldTask() {
			public void run() {
				if (getId() == 17158) {//Stone clone
				int SDC = VoragoCombat.StoneDeadCount;
	        	VoragoCombat.CloneDead = true;
	        	VoragoCombat.StoneDeadCount = SDC + 1;
				reset();
				finish();
				stop();
				} else if (getId() == 17185) {//Scopulus
					if (VoragoCombat.ScopEnrage < 2) {
		        		VoragoCombat.ScopEnrage = 2;
		        		VoragoMinionCombat.EnrageMessage = false;
		        	} else {
		        		VoragoCombat.ScopDead = true;
		        	}
					reset();
					finish();
					stop();
				} else {//Vitalis
					reset();
					finish();
					stop();
				}
			}
	}, getCombatDefinitions().getDeathDelay());
	}
	
	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}
	
}