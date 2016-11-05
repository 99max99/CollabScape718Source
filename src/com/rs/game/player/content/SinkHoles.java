package com.rs.game.player.content;

import java.util.Random;
import java.util.TimerTask;

import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.utils.Logger;

public class SinkHoles {

	public enum Sinkholes {
		FREMENNIK(new WorldTile(2781, 3608, 0), "Near a slayer cave east of my home."),
		BATTLE(new WorldTile(2547, 3254, 0), "In the midst of a battle  between homans and gnomes."),
		BRIMHAVEN(new WorldTile(2741, 3170, 0), "Near a dungeon entrance in the jungle."),
		CANIFS(new WorldTile(3518, 3512, 0), "Near a town east of the River Salve."),
		PORT_PHASMATYS(new WorldTile(3650, 3529, 0), "Near an altar besides a ghostly town."),
		OOGLOG(new WorldTile(2601, 2887, 0), "A good vacation spot."),
		BARBARIAN_OUTPOST(new WorldTile(2543, 3560, 0), "Where barbarians train for agility."),
		FIGHT_ARENA(new WorldTile(2623, 3134, 0), "Near an arena, beside a port town."),
		RANGING_GUILD(new WorldTile(2674, 3447, 0), "Beside a guild for archers."),
		MUDSKIPPER_POINT(new WorldTile(3010, 3150, 0), "Besides a dungeon entrance, south of a port town."),
		BARROWS(new WorldTile(3560, 3312, 0), "Place where several famous brothers lie."),
		MONASTERY(new WorldTile(3033, 3486, 0), "Between a mountain and a place of peace."),
		TAVERLY(new WorldTile(2927, 3401, 0), "Near a cave entrance in a druidic town."),
		LUMBRIDGE_SWAMP(new WorldTile(3220, 3195, 0), "In the swamps south of a town."),
		CATHERBY(new WorldTile(2819, 3477, 0), "North of a fishing town."),
		FISHING_COLONY(new WorldTile(2315, 3643, 0), "South of a fishing colony, where hunters prey."),
		SEERS_VILLAGE(new WorldTile(2751, 3425, 0), "Near a flax field where the future is seen."),
		FALADOR(new WorldTile(2987, 3405, 0), "North of the White Knight city."),
		AL_KHARID(new WorldTile(3304, 3260, 0), "North of a desert town."),
		RELLEKKA(new WorldTile(2607, 3624, 0), "Close to a lighthouse.");
	
		private WorldTile tile;
		private String hint;
	
		private Sinkholes(WorldTile tile, String hint) {
			this.tile = tile;
			this.hint = hint;
		}
		
		public WorldTile getTile() {
			return tile;
		}
		
		public String getHint() {
			return hint;
		}
		
		
	}
	
	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 59921:
			return true;
		default:
		return false;
		}
	}
	
	
	public static void enterSinkHole(Player player) {
		player.setNextWorldTile(new WorldTile(1561, 4381, 0));
		player.setNextAnimation(new Animation(7376));
		player.sm("You climb into the sinkhole.");
		player.sinkholes++;
			return;
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 59921) {
			enterSinkHole(player);
		}
	}
	
	public static void startEvent() {
	    int pick = new Random().nextInt(Sinkholes.values().length);
		final Sinkholes sinkhole = Sinkholes.values()[pick];
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int loop;
			@Override
			public void run() {
				try {
					if (loop < 2) {
					for (Player players : World.getPlayers()) {
						if (players == null || !World.isSinkArea(players.getTile()))
							continue;
						players.setNextWorldTile(sinkhole.getTile());
						players.sm("The sink hole has collapsed and you have escaped to avoid any damage.");
					}
					} else {
					cancel();
					}
					loop++;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 3600000);//3600000
		
		final WorldObject hole = new WorldObject(59921, 10, 0, sinkhole.getTile().getX(), sinkhole.getTile().getY(), 0);	
		World.sendWorldMessage("<img=7><col=ff0000>[Sink Hole] A sink hole has appeared, raid the dungeon for large amounts of resources!", false);
		World.sendWorldMessage("<img=7><col=ff0000>[Sink Hole] Location: "+sinkhole.getHint()+"", false);
		World.spawnTemporaryObject(hole, 3500000, true);
		return;
	}

}