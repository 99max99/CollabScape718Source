package com.rs.game.player.content;

import java.util.TimerTask;

import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.utils.Logger;

public class BossMobs {
	public static enum Delay {

		RIMMINGTON(3600000), HOME(1800000), FALADOR(7200000), VARROCK(
				6200000);

		public final int time;

		Delay(int time) {
			this.time = time;
		}

		public int getTime() {
			return time;
		}
	}

	public static boolean ongoingRimmington = false;
	
	public static boolean ongoingHome = false;
	
	public static boolean ongoingFalador = false;
	
	public static boolean ongoingVarrock = false;
	
	public static final void killEvents() {
		ongoingRimmington = false;
		ongoingHome = false;
		ongoingFalador = false;
		ongoingVarrock = false;
	}
	public static final void initGoblinRaids() {
		initRefresh();
		initAll();
	}

	public static final void initAll() {
		initRimmingtonRaid();
		initFaladorRaid();
		initHomeRaid();
		initVarrockRaid();
	}
	
	public static final void initRimmingtonRaid() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
@Override
			public void run() {
				try {
					initRefresh();
					World.sendWorldMessage(
							"<img=7><col=FF0000>News: Goblins have raided Rimmington!",
							false);
					World.spawnNPC(3264, new WorldTile(2987, 3220, 0), -1,
							true, true);
					World.spawnNPC(3264, new WorldTile(2987, 3221, 0), -1,
							true, true);
					World.spawnNPC(3264, new WorldTile(2987, 3222, 0), -1,
							true, true);
					ongoingRimmington = true;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, Delay.RIMMINGTON.getTime());
	}

	public static final void initFaladorRaid() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
@Override
			public void run() {
				try {
					initRefresh();
					World.sendWorldMessage(
							"<img=7><col=FF0000>News: Goblins have raided Falador!",
							false);
					World.spawnNPC(3264, new WorldTile(2990, 3406, 0), -1,
							true, true);
					World.spawnNPC(3264, new WorldTile(2990, 3407, 0), -1,
							true, true);
					World.spawnNPC(3264, new WorldTile(2990, 3408, 0), -1,
							true, true);
					ongoingFalador = true;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, Delay.FALADOR.getTime());
	}

	public static final void initHomeRaid() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
@Override
			public void run() {
				try {
					initRefresh();
					World.sendWorldMessage(
							"<img=7><col=FF0000>News: Goblins have raided Home!",
							false);
					World.spawnNPC(3264, new WorldTile(2738, 3569, 0), -1,
							true, true);
					World.spawnNPC(3264, new WorldTile(2738, 3568, 0), -1,
							true, true);
					World.spawnNPC(3264, new WorldTile(2738, 3567, 0), -1,
							true, true);
					ongoingHome = true;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, Delay.HOME.getTime());
	}

	public static final void initVarrockRaid() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
@Override
			public void run() {
				try {
					initRefresh();
					World.sendWorldMessage(
							"<img=7><col=FF0000>News: Goblins have raided Varrock!",
							false);
					World.spawnNPC(3264, new WorldTile(3160, 3389, 0), -1,
							true, true);
					World.spawnNPC(3264, new WorldTile(3160, 3388, 0), -1,
							true, true);
					World.spawnNPC(3264, new WorldTile(3160, 3387, 0), -1,
							true, true);
					ongoingVarrock = true;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, Delay.VARROCK.getTime());
	}

	public static final void initRefresh() {
		for (NPC n : World.getNPCs()) {
			if (n == null || n.getId() != 3264)
				continue;
			n.sendDeath(n);
		}
		killEvents();
	}
}
