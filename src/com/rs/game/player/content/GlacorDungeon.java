package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * 
 * @author Annoying
 *
 */

public class GlacorDungeon {
	
	/*
	 * Player enters the Dungeon
	 */
	
	public static void EnterDungeonBoost(final Player player) {
		player.lock(1);
		player.setNextAnimation(new Animation(10530));
		player.setNextGraphics(new Graphics(1340));
		int actualLevel = player.getSkills().getLevel(Skills.ATTACK);
		int realLevel = player.getSkills().getLevelForXp(Skills.ATTACK);
		int level = actualLevel > realLevel ? realLevel : actualLevel;
		player.getSkills().set(Skills.ATTACK,
				(int) (level + 25 + (realLevel * 0.22)));
		
		actualLevel = player.getSkills().getLevel(Skills.STRENGTH);
		realLevel = player.getSkills().getLevelForXp(Skills.STRENGTH);
		level = actualLevel > realLevel ? realLevel : actualLevel;
		player.getSkills().set(Skills.STRENGTH,
				(int) (level + 25 + (realLevel * 0.22)));

		actualLevel = player.getSkills().getLevel(Skills.DEFENCE);
		realLevel = player.getSkills().getLevelForXp(Skills.DEFENCE);
		level = actualLevel > realLevel ? realLevel : actualLevel;
		player.getSkills().set(Skills.DEFENCE,
				(int) (level + 25 + (realLevel * 0.22)));

		actualLevel = player.getSkills().getLevel(Skills.MAGIC);
		realLevel = player.getSkills().getLevelForXp(Skills.MAGIC);
		level = actualLevel > realLevel ? realLevel : actualLevel;
		player.getSkills().set(Skills.MAGIC, level + 27);

		actualLevel = player.getSkills().getLevel(Skills.RANGE);
		realLevel = player.getSkills().getLevelForXp(Skills.RANGE);
		level = actualLevel > realLevel ? realLevel : actualLevel;
		player.getSkills().set(Skills.RANGE,
				(int) (level + 20 + (Math.floor(realLevel / 5.2))));
	
	}
	
	/*
	 * Boolean for the coordinates
	 */
	
	public static boolean atGlacor(WorldTile tile) {
		if ((tile.getX() >= 4164 && tile.getX() <= 4223)
				&& (tile.getY() >= 5698 && tile.getY() <= 5754))
			return true;
		return false;
	}
	
	/*
	 * Bariers
	 */
	
	public static void Barier(final Player player, WorldObject object) {
		 player.lock(1);
		 player.faceObject(object);
		 player.setNextGraphics(new Graphics(3008));
		 final WorldTile toTile = new WorldTile(player.getX() == 4205 ? 4206 : 4205, 5751, player.getPlane());
		 player.addWalkSteps(player.getX() == 4205 ? 4206 : 4205, 5751, player.getPlane(), false);
		 WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.setNextWorldTile(toTile);
			}	
			 
		 }, 1);
	}
}