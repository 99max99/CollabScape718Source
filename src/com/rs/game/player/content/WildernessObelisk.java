package com.rs.game.player.content;

import java.util.TimerTask;

import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class WildernessObelisk {
	private static int X_COORD = 0, Y_COORD = 1;
	private static int PILLAR_LOC = 1, OBELISK_DETAILS = 0;
	private static int TELE_LOC = 0, TIMER = 1, THIS = 0;

	private static int[][][][] obelisk = {

			{
					{ { 3156, 3620 }, { 0 } },
					{ { 3158, 3622 }, { 3158, 3618 }, { 3154, 3618 },
							{ 3154, 3622 } } },
			{
					{ { 3307, 3916 }, { 0 } },
					{ { 3309, 3918 }, { 3305, 3918 }, { 3309, 3914 },
							{ 3305, 3914 } } },
			{
					{ { 3106, 3794 }, { 0 } },
					{ { 3108, 3792 }, { 3108, 3796 }, { 3104, 3792 },
							{ 3104, 3796 } } },
			{
					{ { 3219, 3656 }, { 0 } },
					{ { 3221, 3658 }, { 3221, 3654 }, { 3217, 3658 },
							{ 3217, 3654 } } },
			{
					{ { 2980, 3866 }, { 0 } },
					{ { 2982, 3868 }, { 2978, 3868 }, { 2982, 3864 },
							{ 2978, 3864 } } } };

	public static boolean handleObject(WorldObject o, Player player) {
		if (65609 < o.getId() && o.getId() < 65625) {
			for (int i = 0; i < getObelisk().length; i++) {
				for (int j = 0; j < 4; j++) {
					if (getObelisk()[i][PILLAR_LOC][j][X_COORD] == o.getX()
							&& getObelisk()[i][PILLAR_LOC][j][Y_COORD] == o
									.getY()) {
						if (getObelisk()[i][OBELISK_DETAILS][TIMER][THIS] > 0) {
							player.getPackets()
									.sendGameMessage(
											"You must wait "
													+ getObelisk()[i][OBELISK_DETAILS][TIMER][THIS]
													+ " second"
													+ (getObelisk()[i][OBELISK_DETAILS][TIMER][THIS] > 1 ? "s"
															: "")
													+ " for the ancient obelisk to recharge.");
							return true;
						}
						int xOffset = getObelisk()[i][OBELISK_DETAILS][TELE_LOC][X_COORD]
								- player.getX();
						int yOffset = getObelisk()[i][OBELISK_DETAILS][TELE_LOC][Y_COORD]
								- player.getY();
						obeliskTeleport(player, i, xOffset, yOffset);
						for (Player p : World.getPlayers()) {
							if (p != null || p != player) {
								if (p.withinDistance(player.getTile(), 5)) {
									obeliskTeleport(player, i, xOffset, yOffset);
								}
							}
						}
						return true;
					}
				}
			}
			return true;
		}
		return false;
	}

	public static void obeliskTeleport(final Player player, int currentObelisk,
			final int xOffset, final int yOffset) {
		final int nextObelisk = currentObelisk + 1;
		if (nextObelisk >= getObelisk().length) {
			player.setNextWorldTile(new WorldTile(
					getObelisk()[0][OBELISK_DETAILS][TELE_LOC][X_COORD]
							- xOffset,
					getObelisk()[0][OBELISK_DETAILS][TELE_LOC][Y_COORD]
							- yOffset, 0));
			return;
		}
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(8939));
					player.setNextGraphics(new Graphics(342));
					player.lock(4);
				} else if (loop == 1) {
					player.setNextWorldTile(new WorldTile(
							getObelisk()[nextObelisk][OBELISK_DETAILS][TELE_LOC][X_COORD]
									- xOffset,
							getObelisk()[nextObelisk][OBELISK_DETAILS][TELE_LOC][Y_COORD]
									- yOffset, 0));

				} else if (loop == 2) {
					player.setNextAnimation(new Animation(8941));
				}
				loop++;
			}
		}, 0, 1);
		getObelisk()[currentObelisk][OBELISK_DETAILS][TIMER][THIS] = 10;
		CoresManager.fastExecutor.schedule(new TimerTask() {
			boolean stop = true;

			@Override
			public void run() {

				for (int k = 0; k < getObelisk().length; k++) {
					if (getObelisk()[k][OBELISK_DETAILS][TIMER][THIS] > 0) {
						getObelisk()[k][OBELISK_DETAILS][TIMER][THIS]--;
						stop = false;
					}
				}
				if (stop) {
					cancel();
				}
			}
		}, 0, 1000);
	}

	public static int[][][][] getObelisk() {
		return obelisk;
	}
}