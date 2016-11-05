package com.rs.game.player.controlers.pestcontrol;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * @author Jae
 */
public class PestControler extends Controler {

	public static int playTimer = 400;
	public static boolean startedPlayTimer = false;
	public static int waitTimer = 50;
	public static boolean startedWaitTimer = false;

	@Override
	public void start() {
		System.out.println("Details: Game started: " + PestControl.startedGame + "Wait timer:" + waitTimer);
		process();
	}

	@Override
	public void process() {
		if (PestControl.startedGame == false && startedWaitTimer == true) {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					int minutes = waitTimer / 100;
					int seconds = (int) ((waitTimer - (minutes * 100)) * 0.6);
					String time = (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
					if (waitTimer == 0) {
						stop();
						startedWaitTimer = false;
						player.getControlerManager().removeControlerWithoutCheck();
						if (PestControl.lobbyPlayers.size() >= PestControl.MINIMUM_PLAYERS) {
							startedPlayTimer = true;
							PestControl.startedGame = true;
							PestControl.startGame(player);
							PestControl.startedGame = true;
							startedPlayTimer = true;
						} else if (PestControl.lobbyPlayers.size() < PestControl.MINIMUM_PLAYERS && !PestControl.startedGame) {
							player.sm("Unable to start a game, there's not enough players!");
							PestControl.restart(player);
						}
					}
					player.getPackets().sendIComponentText(407, 13, "Next departure in: " + time);
					player.getPackets().sendIComponentText(407, 3, "Novice");
					player.getPackets().sendIComponentText(407, 14, "Players ready: " + PestControl.lobbyPlayers.size());
					player.getPackets().sendIComponentText(407, 16, "Points: " + player.getPestPoints());
				}
			});
			--waitTimer;
		}
		if (PestControl.startedGame == true && startedPlayTimer == true) {
			// player.sm("time: " + playTimer);
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					int minutes = playTimer / 100;
					int seconds = (int) ((playTimer - (minutes * 100)) * 0.6);
					String time = (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
					playTimer--;
					if (playTimer == 335) {
						player.sm("The Western portal has been dropped!");
						PestControl.spawnOpenedPortals(0);
					}
					if (playTimer == 270) {
						player.sm("The Eastern portal has been dropped!");
						PestControl.spawnOpenedPortals(3);
					}
					if (playTimer == 245) {
						player.sm("The South Eastern portal has been dropped!");
						PestControl.spawnOpenedPortals(2);
					}
					if (playTimer == 200) {
						player.sm("The South Western portal has been dropped!");
						PestControl.spawnOpenedPortals(1);
					}
					if (playTimer == 0) {
						PestControl.endGame(player);
					}
					if (PestControl.voidKnight.getHitpoints() == 0) {
						player.sm("The void knight has fallen! The game was lost.");
						if (player.isInPestControlGame) {
							removeControler();
							PestControl.endGame(player);
						}
						waitTimer = 50;
						this.stop();
					}
					if (player.isInPestControlLobby) {
						player.getPackets().sendIComponentText(407, 13, "Next departure in: " + seconds);
						player.getPackets().sendIComponentText(407, 14, "Players ready: " + PestControl.lobbyPlayers.size());
						player.getPackets().sendIComponentText(407, 16, "Points: " + player.getPestPoints());
					}
					if (player.isInPestControlGame) {
						player.getPackets().sendIComponentText(408, 13, "" + PestControl.getPortals(0).getHitpoints());
						player.getPackets().sendIComponentText(408, 14, "" + PestControl.getPortals(1).getHitpoints());
						player.getPackets().sendIComponentText(408, 15, "" + PestControl.getPortals(2).getHitpoints());
						player.getPackets().sendIComponentText(408, 16, "" + PestControl.getPortals(3).getHitpoints());
						player.getPackets().sendIComponentText(408, 1, "" + PestControl.voidKnight.getHitpoints());
						player.getPackets().sendIComponentText(408, 11, "" + player.pestDamage);
						player.getPackets().sendIComponentText(408, 0, "" + time);
					}
				}
			});
		}
	}

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.getDialogueManager().startDialogue("SimpleMessage", "You can't leave just like that!");
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		player.getDialogueManager().startDialogue("SimpleMessage", "You can't leave just like that!");
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		player.getDialogueManager().startDialogue("SimpleMessage", "You can't leave just like that!");
		return false;
	}

	@Override
	public boolean canAttack(Entity target) {
		player.addPestDamage(132);
		return true;
	}

	@Override
	public boolean sendDeath() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.sm("Oh dear, you have died.");
				} else if (loop == 3) {
					if (PestControl.gamePlayers.contains(player)) {
						player.setLocation(PestControl.GAME_LOCATION);
					}
					player.reset();
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		});
		return true;
	}

	@Override
	public boolean logout() {
		if (player.isInPestControlGame) {
			PestControl.leaveGame(player);
		}
		if (player.isInPestControlLobby) {
			PestControl.leaveLobby(player);
		}
		return true;
	}

}