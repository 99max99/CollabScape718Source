package com.rs.game.player.controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.GameEngine;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.godwars.GodWarMinion;
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class InstancedBossControler extends Controler {
	
	/**
	 * Doesn't send player home after logging out 
	 * Doesn't delete npc if it's
	 * dead after logging out
	 */
	
	public static class NPCInformation {

		private int npcId;
		private WorldTile tile;

		public NPCInformation(int npcId, WorldTile tile) {
			this.npcId = npcId;
			this.tile = tile;
		}

		public int getNPCId() {
			return npcId;
		}

		public WorldTile getTile() {
			return tile;
		}
	}
	
	public static enum Difficulty {
		Normal, Quick, Hard, QuickHard
	}
	
	public static enum Instance {
		ARMADYL(new WorldTile(2835, 5295, 0), new WorldTile(2835, 5294, 0), 351, 661, 0, new WorldTile(27, 7, 0), new NPCInformation[] { new NPCInformation(6222, new WorldTile(22, 12, 0)),
		/*
		 * new NPCInformation(6223, new WorldTile(24, 15, 0)), new
		 * NPCInformation(6225, new WorldTile(20, 15, 0)), new
		 * NPCInformation(6227, new WorldTile(30, 18, 0))
		 */}), BANDOS(new WorldTile(2863, 5357, 0), new WorldTile(2862, 5357, 0), 357, 668, 0, new WorldTile(7, 14, 0), new NPCInformation[] { new NPCInformation(6260, new WorldTile(14, 21, 0)),
		/*
		 * new NPCInformation(6261, new WorldTile(11, 24, 0)), new
		 * NPCInformation(6263, new WorldTile(18, 24, 0)), new
		 * NPCInformation(6265, new WorldTile(17, 18, 0))
		 */}), SARADOMIN(new WorldTile(2923, 5256, 0), new WorldTile(2923, 5257, 0), 363, 654, 0, new WorldTile(19, 24, 0), new NPCInformation[] { new NPCInformation(6247, new WorldTile(19, 17, 0)),
		/*
		 * new NPCInformation(6248, new WorldTile(14, 14, 0)), new
		 * NPCInformation(6250, new WorldTile(24, 15, 0)), new
		 * NPCInformation(6252, new WorldTile(15, 20, 0))
		 */}), ZAMORAK(new WorldTile(2925, 5332, 0), new WorldTile(2925, 5333, 0), 364, 664, 0, new WorldTile(13, 20, 0), new NPCInformation[] { new NPCInformation(6203, new WorldTile(16, 14, 0)),
		/*
		 * new NPCInformation(6208, new WorldTile(20, 15, 0)), new
		 * NPCInformation(6204, new WorldTile(20, 1, 0)), new
		 * NPCInformation(6206, new WorldTile(10, 10, 0))
		 */}), 
		 KBD(new WorldTile(2523, 5232, 0), new WorldTile(3067, 10254, 0), 281, 583, 0, new WorldTile(25, 17, 0), new NPCInformation[] { new NPCInformation(50, new WorldTile(22, 37, 0)) }), 
		 YKLAGOR(new WorldTile(2523, 5232, 0), new WorldTile(2524, 5234, 0), 314, 652, 0, new WorldTile(9, 18, 0), new NPCInformation[] { new NPCInformation(11872, new WorldTile(20, 18, 0)) }), 
		 BLINK(new WorldTile(1370, 6621, 0), new WorldTile(1369, 6623, 0), 170, 826, 0, new WorldTile(9, 14, 0), new NPCInformation[] { new NPCInformation(12878, new WorldTile(13, 15, 0)) }), 
		 SUNFREET(new WorldTile(3803, 3529, 0), new WorldTile(3803, 3529, 0), 470, 437, 0, new WorldTile(43, 34, 0), new NPCInformation[] { new NPCInformation(15222, new WorldTile(42, 48, 0)) }), 
		 LEEUNI(new WorldTile(2836, 2576, 0), new WorldTile(2836, 2576, 0), 352, 318, 0, new WorldTile(20, 32, 0), new NPCInformation[] { new NPCInformation(13216, new WorldTile(44, 45, 0)) }), 
		 CORP(new WorldTile(2975, 4384, 2), new WorldTile(2970, 4384, 2), 368, 543, 0, new WorldTile(31, 40, 2), new NPCInformation[] { new NPCInformation(8133, new WorldTile(44, 40, 2)) });

		private WorldTile coords, graveTile;
		private int chunkX;
		private int chunkY;
		private int chunkZ;
		private WorldTile landing;
		private NPCInformation[] npcs;

		private Instance(WorldTile coords, WorldTile graveTile, int chunkX, int chunkY, int chunkZ, WorldTile landing, NPCInformation[] npcs) {
			this.coords = coords;
			this.graveTile = graveTile;
			this.chunkX = chunkX;
			this.chunkY = chunkY;
			this.chunkZ = chunkZ;
			this.landing = landing;
			this.npcs = npcs;
		}

		public WorldTile getCoords() {
			return coords;
		}

		public WorldTile getGraveTile() {
			return graveTile;
		}

		public WorldTile getOriginalCoords(WorldTile tile) {
			int originalBaseX = 8 * (coords.getChunkX() - (Settings.MAP_SIZES[0] >> 4));
			int originalBaseY = 8 * (coords.getChunkY() - (Settings.MAP_SIZES[0] >> 4));
			int localX = tile.getLocalX();
			int localY = tile.getLocalY();
			/*
			 * System.out.println(originalBaseX);
			 * System.out.println(originalBaseY); System.out.println(localX);
			 * System.out.println(localY);
			 */
			return new WorldTile(originalBaseX + localX, originalBaseY + localY, coords.getPlane());
		}

		public int getChunkX() {
			return chunkX;
		}

		public int getChunkY() {
			return chunkY;
		}

		public int getChunkZ() {
			return chunkZ;
		}

		public int getPlane() {
			return coords.getPlane();
		}

		public WorldTile getLanding(int x, int y) {
			return new WorldTile(landing.getX() + x, landing.getY() + y, getPlane());
		}

		public NPCInformation[] getNPCs() {
			return npcs;
		}

		public WorldTile getNPCTile(int i, int x, int y) {
			return new WorldTile(npcs[i].getTile().getX() + x, npcs[i].getTile().getY() + y, getPlane());
		}
	}

	public static class InstancedBossSession implements Serializable {

		private static final long serialVersionUID = -5254867340710194145L;
		
		private Instance instance;
		private Difficulty difficulty;
		private int maximumMembers;
		private List<String> players;
		private int[] chunks;
		
		public InstancedBossSession(Instance instance, Difficulty difficulty, int maxPlayers) {
			this.instance = instance;
			this.difficulty = difficulty;
			players = new ArrayList<String>();
			maximumMembers = maxPlayers;
			buildMap();
			spawnNPCs();
		}

		public void buildMap() {
			chunks = RegionBuilder.findEmptyChunkBound(8, 8);
			RegionBuilder.copyAllPlanesMap(instance.getChunkX(), instance.getChunkY(), chunks[0], chunks[1], 8);
		}

		public void spawnNPCs() {
			for (int i = 0; i < instance.getNPCs().length; i++) {
				NPC npc = World.spawnNPC(instance.getNPCs()[i].getNPCId(), instance.getNPCTile(i, getX(), getY()), -1, true);
				npc.setForceMultiArea(true);
				npc.setForceMultiAttacked(true);
				//npc.setDifficulty(difficulty);
			}
		}

		public void joinSession(final Player player) {
			if (players.size() >= maximumMembers) {
				player.getDialogueManager().startDialogue("SimpleDialogue", "This session is full.");
			} else {
				players.add(player.getUsername());
				final InstancedBossSession session = this;
				if (instance == Instance.KBD) {
					Magic.pushLeverTeleport(player,
						instance.getLanding(getX(), getY()));
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							player.getControlerManager().startControler("InstancedBossControler", session);
						}
					}, 6);
				} else {
					player.setNextWorldTile(instance.getLanding(getX(), getY()));
					player.getControlerManager().startControler("InstancedBossControler", session);
				}
				player.setForceMultiArea(true);
				player.inInstancedDungeon = true;
			}
		}

		public void leaveSession(Player player) {
			if (player.inInstancedDungeon) {
				player.inInstancedDungeon = false;
				player.setForceMultiArea(false);
				if (players.contains(player.getUsername()))
					players.remove(player.getUsername());
			}
			if (players.isEmpty()) {
				final int regionId = player.getRegionId();
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						destroyMap();
						deleteNPCs(regionId);
					}
				}, 20);
			}
			player.getControlerManager().removeControlerWithoutCheck();
		}

		public void destroyMap() {
			GameEngine.get().slowExecutor().execute(new Runnable() {
				@Override
				public void run() {
					try {
						RegionBuilder.destroyMap(chunks[0], chunks[1], 8, 8);
					} catch (Exception e) {
						e.printStackTrace();
					} catch (Error e) {
						e.printStackTrace();
					}
				}
			});
		}

		public void deleteNPCs(int regionId) {
			List<Integer> npcsIndexes = World.getRegion(regionId).getNPCsIndexes();
			if (npcsIndexes != null) {
				for (int npcIndex : npcsIndexes) {
					NPC npc = World.getNPCs().get(npcIndex);
					if (npc == null/* || npc.isDead() */|| npc.hasFinished()) {
						continue;
					}
					for (int i = 0; i < instance.getNPCs().length; i++) {
						if (npc.getId() == instance.getNPCs()[i].getNPCId() || npc instanceof GodWarMinion) {// god
																												// war
																												// minions
																												// special
																												// case
							npc.finish();
						}
					}
				}
			}
		}

		public int[] getChunks() {
			return chunks;
		}

		public int getX() {
			return chunks[0] << 3;
		}

		public int getY() {
			return chunks[1] << 3;
		}

		public Instance getInstance() {
			return instance;
		}

		public Difficulty getDifficulty() {
			return difficulty;
		}
	}

	public static void startSession(Player player, Instance instance, Difficulty difficulty, int maxPlayers) {
		new InstancedBossSession(instance, difficulty, maxPlayers).joinSession(player);
	}

	public static void joinSession(Player player, Player dungeoneer) {
		((InstancedBossControler) dungeoneer.getControlerManager().getControler()).session.joinSession(player);
		player.getDialogueManager().finishDialogue();
	}

	public transient InstancedBossSession session;

	@Override
	public void start() {
		session = (InstancedBossSession) getArguments()[0];
	}
	
	@Override
	public boolean sendDeath() {
		player.lock(7);
		player.stopAll();
		if (player.getFamiliar() != null) {
			player.getFamiliar().sendDeath(player);
		}
		final WorldTile graveTile = session.instance.getGraveTile();
		// session.getInstance().getOriginalCoords(player);//session.instance.getCoords();//new
		// WorldTile(player);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.sm("Oh dear, you have died.");
					if (player.getAssassinsManager().getGameMode() == 4) {
						player.getAssassinsManager().resetTask();
						player.sm("You have failed your Assassin's contract, go try another.");
					}
				} else if (loop == 3) {
					player.reset();
					//player.setNextWorldTile(player.getHome());
					//player.setNextAnimation(new Animation(-1));
					session.leaveSession(player);
					player.getControlerManager().startControler("DeathEvent", graveTile, player.hasSkull());
				} else if (loop == 4) {
					player.killstreak = 0;
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}

	@Override
	public void magicTeleported(int type) {
		if (session == null)
			return;
		session.leaveSession(player);
	}

	@Override
	public boolean processMagicTeleport(WorldTile tile) {
		if (session == null)
			return true;
		session.leaveSession(player);
		return true;
	}

	@Override
	public boolean processItemTeleport(WorldTile tile) {
		if (session == null)
			return true;
		session.leaveSession(player);
		return true;
	}

	@Override
	public boolean processObjectTeleport(WorldTile tile) {
		if (session == null)
			return true;
		session.leaveSession(player);
		return true;
	}

	@Override
	public boolean processObjectClick1(WorldObject object) {
		return true;
	}
	
	@Override
	public void forceClose() {
		if (session == null) {
			//player.setLocation(player.getHome());
			player.setNextWorldTile(new WorldTile(2846,10209,0));
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2846,10209,0));
			//player.setLocation()
			return;
		}
		session.leaveSession(player);
		player.setNextWorldTile(new WorldTile(2846,10209,0));
	}
	
	@Override
	public boolean logout() {
		if (session == null) {
			player.setNextWorldTile(new WorldTile(2846,10209,0));
			return true;
		}
		session.leaveSession(player);
		player.setNextWorldTile(new WorldTile(2846,10209,0));
		return true;
	}
}