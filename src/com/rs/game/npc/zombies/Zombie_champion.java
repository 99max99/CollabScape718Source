package com.rs.game.npc.zombies;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.minigames.zombies.Zombies;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * 
 * @author Adam
 * @since Jully 31st 2012
 *NPC 3066
 *
 *
 */

@SuppressWarnings("serial")
public class Zombie_champion extends ZombiesNPC{

	private boolean spawnedZombies;
	private Zombies controler;
	
	public Zombie_champion(int id, WorldTile tile, Zombies controler) {
		super(id, tile);
		this.controler = controler;
	}
	
	@Override
	public void processNPC() {
		super.processNPC();
		if(!spawnedZombies && getHitpoints() < getMaxHitpoints() / 2) {
			spawnedZombies = true;
			controler.spawnZombieMembers();
		}
	}
	
	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		resetWalkSteps();
		getCombat().removeTarget();
		setNextAnimation(null);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(defs.getDeathEmote()));
					setNextGraphics(new Graphics(2924 + getSize()));
				} else if (loop >= defs.getDeathDelay()) {
					reset();
					finish();
					controler.win();
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}
	
	
	
}
