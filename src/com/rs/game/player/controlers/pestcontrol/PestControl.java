package com.rs.game.player.controlers.pestcontrol;

import java.util.LinkedList;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.others.VoidKnight;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class PestControl {

	public static boolean isInsidePestGame(WorldTile WorldTile) {
		return (WorldTile.getX() >= 2625 && WorldTile.getY() <= 2624 && WorldTile.getX() <= 2691 && WorldTile.getY() >= 2559);
	}

	public static boolean isInsideLobby(WorldTile WorldTile) {
		return (WorldTile.getX() >= 2660 && WorldTile.getY() <= 2643 && WorldTile.getX() <= 2663 && WorldTile.getY() >= 2638);
	}

	public static void loginCheck(Player player) {
		if (isInsidePestGame(player.getWorldTile())) {
			player.setNextWorldTile(OUTSIDE_LOBBY);
		}
	}

	private static final WorldTile VOID_KNIGHT_LOCATION = new WorldTile(2656, 2592, 0);
	public static LinkedList<Player> lobbyPlayers = new LinkedList<Player>();
	public static LinkedList<Player> gamePlayers = new LinkedList<Player>();
	public static WorldTile GAME_LOCATION = new WorldTile(2657, 2610, 0);
	public static WorldTile OUTSIDE_LOBBY = new WorldTile(2657, 2639, 0);
	private static WorldTile INSIDE_LOBBY = new WorldTile(2661, 2639, 0);
	public static NPC[] portal = new NPC[4];
	public static NPC[] portalAlive = new NPC[4];
	public static boolean startedGame = false;
	public static int lastIndex;
	public static final int MINIMUM_PLAYERS = 5;
	public static NPC voidKnight;
	public static boolean restarted = false;
	public static int[] PESTS_NOVICE = { 3772, 3762, 3742, 3732, 3747, 3727, 3752 };
	public static int[] PESTS_OTHER = { 3773, 3764, 3743, 3734, 3748, 3728, 3754, 3774, 3766, 3744, 3736, 3749, 3729, 3756, 3775, 3768, 3745, 3738, 3750, 3730, 3758, 3776, 3770, 3746, 3740, 3751,
			3731, 3760 };
	public static boolean[] PORTAL_STATE = { false, false, false, false };
	
	public static void joinLobby(Player player) {
		if (startedGame) {
			int minutes = PestControler.playTimer / 100;
			int seconds = (int) ((PestControler.playTimer - (minutes * 100)) * 0.6);
			String time = (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
			player.sm("There is already a game in progress, please wait " + time + " before joining the lobby.");
			return;
		}
		lobbyPlayers.add(player);
		player.isInPestControlLobby = true;
		player.isInPestControlGame = false;
		PestControler.startedWaitTimer = true;
		player.setNextWorldTile(INSIDE_LOBBY);
		player.getControlerManager().startControler("PestControler");
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 1 : 11, 407);
		player.sm("You board the lander.");
	}

	public static void restart(final Player player) {
		if (player.getBoneDelay() > Utils.currentTimeMillis())
			return;
		player.setBoneDelay(100000);
		startedGame = false;
		PestControler.waitTimer = 50;
		PestControler.startedWaitTimer = true;
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getControlerManager().startControler("PestControler");

			}
		});
	}

	public static void reJoinLobby(final Player player) {
		if (player.getBoneDelay() > Utils.currentTimeMillis())
			return;
		player.setBoneDelay(10);
		startedGame = false;
		PestControler.startedWaitTimer = true;
		PestControler.waitTimer = 50;
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getControlerManager().startControler("PestControler");
				player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 1 : 11, 407);
			}
		});
	}

	public static void leaveLobby(Player player) {
		if (lobbyPlayers.contains(player)) {
			lobbyPlayers.remove(player);
		}
		player.getControlerManager().forceStop();
		player.isInPestControlLobby = false;
		player.isInPestControlGame = false;
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 1 : 11);
		player.setNextWorldTile(OUTSIDE_LOBBY);
	}

	public static void startGame(Player player) {
		if (lobbyPlayers.contains(player)) {
			lobbyPlayers.remove(player);
			gamePlayers.add(player);
		}
		PestControl.lobbyPlayers.remove(player);
		PestControler.startedWaitTimer = false;
		PestControler.waitTimer = 400;
		PestControler.playTimer = 400;
		PestControler.startedPlayTimer = true;
		PestControl.startedGame = true;
		player.getControlerManager().forceStop();
		player.isInPestControlLobby = false;
		player.isInPestControlGame = true;
		startedGame = true;
		player.getControlerManager().startControler("PestControler");
		spawnMonsters(player);
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 1 : 11, 408);
		player.setNextWorldTile(GAME_LOCATION);
	}

	public static void endGame(Player player) {
		if (gamePlayers.contains(player)) {
			gamePlayers.remove(player);
		}
		player.isInPestControlLobby = false;
		player.isInPestControlGame = false;
		startedGame = false;
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 1 : 11);
		player.setNextWorldTile(OUTSIDE_LOBBY);
		lobbyPlayers.remove(player);
		gamePlayers.remove(player);
		player.getControlerManager().forceStop();
		if (portal[1].isDead() && portal[2].isDead() && portal[3].isDead() && portal[4].isDead()) {
			player.sm("You won the Pest Control game and are awarded 5 pest control points!");
			player.pestPoints += 5;
			player.pestWins++;
		} else {
			player.sm("You lost the Pest Control game.");
		}
		player.getControlerManager().forceStop();
	}

	public static void spawnMonsters(Player player) {
		voidKnight = new VoidKnight(3782, VOID_KNIGHT_LOCATION, 1, true, true);
		portal[0] = new NPC(6154, new WorldTile(2628, 2591, 0), -1, true, true);
		portal[1] = new NPC(6157, new WorldTile(2645, 2569, 0), -1, true, true);
		portal[2] = new NPC(6156, new WorldTile(2669, 2570, 0), -1, true, true);
		portal[3] = new NPC(6155, new WorldTile(2680, 2588, 0), -1, true, true);
		monsterTimer(player);
		for (int i = 0; i < 4; i++) {
		}
	}

	public static void spawnOpenedPortals(int id) {
		switch (id) {
		case 0:
			World.spawnNPC(6142, new WorldTile(portal[0].getWorldTile()), -1, true);
			portal[0].sendDeath(portal[0]);
			break;
		case 1:
			World.spawnNPC(6145, new WorldTile(portal[1].getWorldTile()), -1, true);
			portal[1].sendDeath(portal[1]);
			break;
		case 2:
			World.spawnNPC(6144, new WorldTile(portal[2].getWorldTile()), -1, true);
			portal[2].sendDeath(portal[2]);
			break;
		case 3:
			World.spawnNPC(6143, new WorldTile(portal[3].getWorldTile()), -1, true);
			portal[3].sendDeath(portal[3]);
			break;
		}
	}

	public static void monsterTimer(final Player player) {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (lastIndex == 7) {
					lastIndex = 0;
				}
				NPC monsters = new NPC(PESTS_NOVICE[lastIndex], getMonstersWorldTile(), -1, true, true);
				System.out.println("Just spawned PEST:" + monsters);
				player.getControlerManager().startControler("PestControler");
				if (startedGame == false) {
					this.stop();
				}
			}
		});
	}

	public static WorldTile getMonstersWorldTile() {
		if (getPortals(0).isDead()) {
			World.spawnNPC(PESTS_NOVICE[lastIndex], new WorldTile(2634, 2593, 0), -1, false);
			return new WorldTile(2634, 2593, 0);
		} else if (getPortals(1).isDead()) {
			World.spawnNPC(PESTS_NOVICE[lastIndex], new WorldTile(2670, 2576, 0), -1, false);
			return new WorldTile(2634, 2593, 0);
		} else if (getPortals(2).isDead()) {
			World.spawnNPC(PESTS_NOVICE[lastIndex], new WorldTile(2646, 2574, 0), -1, false);
			return new WorldTile(2646, 2574, 0);
		} else if (getPortals(3).isDead()) {
			World.spawnNPC(PESTS_NOVICE[lastIndex], new WorldTile(2676, 2589, 0), -1, false);
			return new WorldTile(2676, 2589, 0);
		}
		lastIndex++;
		return new WorldTile(2643, 2592, 0);
	}

	public static void leaveGame(Player player) {
		if (gamePlayers.contains(player)) {
			gamePlayers.remove(player);
		}
		player.isInPestControlLobby = false;
		player.isInPestControlGame = false;
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 1 : 11);
		player.setNextWorldTile(OUTSIDE_LOBBY);
		player.getControlerManager().forceStop();
	}
	

	public static NPC getPortals(int portalIndex) {
		return portal[portalIndex];
	}

}