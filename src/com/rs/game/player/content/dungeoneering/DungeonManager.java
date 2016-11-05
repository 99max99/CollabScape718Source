package com.rs.game.player.content.dungeoneering;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.dungeonnering.Guardian;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.MapAreas;
import com.rs.utils.Utils;

public class DungeonManager {

	private DungeonPartyManager party;
	private Dungeon dungeon;
	private VisibleRoom[][] visibleMap;
	private int[] boundChuncks;
	private int dungeoneeringLevel;
	private int stage;

	// 7554
	public DungeonManager(DungeonPartyManager party, int size) {
		this.setParty(party);
		load(size);
	}

	public void enterRoom(Player player, int x, int y, WorldObject obj) {
		if (x >= visibleMap.length || y >= visibleMap[0].length) {
			//player.getSession().getChannel().close();
			player.getPackets().sendGameMessage("This is a null door, please stop clicking on it. Nothing is behind it.");
			return;
		}
		RoomReference roomReference = getCurrentRoomReference(player);
		if (visibleMap[x][y] != null) {
			int xOffset = x - roomReference.getX();
			int yOffset = y - roomReference.getY();
			player.setNextWorldTile(new WorldTile(player.getX() + xOffset * 3,
					player.getY() + yOffset * 3, 0));
			playMusic(player, new RoomReference(x, y));
		} else
			loadRoom(x, y);
		player.getSkills().addXp(Skills.DUNGEONEERING, 0.5);
	}

	public void loadRoom(int x, int y) {
		loadRoom(new RoomReference(x, y));
	}

	public void loadRoom(final RoomReference reference) {
		final Room room = getDungeon().getRoom(reference);
		if (room == null)
			return;
		visibleMap[reference.getX()][reference.getY()] = new VisibleRoom(
				getParty().getFloorType(), room.getRoom());
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					openRoom(room, reference);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		});
	}

	public void openRoom(Room room, RoomReference reference) {
		int toChunkX = boundChuncks[0] + reference.getX() * 2;
		int toChunkY = boundChuncks[1] + reference.getY() * 2;
		RegionBuilder.copy2RatioSquare(room.getChunkX(), room.getChunkY(),
				toChunkX, toChunkY, room.getRotation());
		int regionId = (((toChunkX / 8) << 8) + (toChunkY / 8));
		for (DungeonPartyPlayer dplayer : getParty().getTeam()) {
			Player player = dplayer.getPlayer();
			if (!player.getMapRegionsIds().contains(regionId))
				continue;
			player.setForceNextMapLoadRefresh(true);
			player.loadMapRegions();
		}
		room.openRoom(this, reference);
	}

	public WorldTile getRoomCenterTile(RoomReference reference) {
		return new WorldTile(
				((boundChuncks[0] << 3) + reference.getX() * 16) + 8,
				((boundChuncks[1] << 3) + reference.getY() * 16) + 8, 0);
	}

	public RoomReference getCurrentRoomReference(WorldTile tile) {
		return new RoomReference((tile.getChunkX() - boundChuncks[0]) / 2,
				(tile.getChunkY() - boundChuncks[1]) / 2);
	}

	public WorldTile getHomeTile() {
		return getRoomCenterTile(getDungeon().getStartRoomReference());
	}

	public void telePartyToRoom(RoomReference reference) {
		WorldTile tile = getRoomCenterTile(reference);
		for (DungeonPartyPlayer dplayer : getParty().getTeam()) {
			dplayer.getPlayer().setNextWorldTile(tile);
			playMusic(dplayer.getPlayer(), reference);
		}
	}

	public void playMusic(Player player, RoomReference reference) {
		player.getMusicsManager().playMusic(
				visibleMap[reference.getX()][reference.getY()].getMusicId());
	}
	public void destroyDungeon() {
		boolean destroyed = false;
		if (destroyed)
			return;
		destroyed = true;
		MapAreas.removeArea(l);
		// gives 1 game ticket so people can leave
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				int[] ratio = getRatio(0);
				RegionBuilder.destroyMap(boundChuncks[0],
						boundChuncks[1], ratio[0] * 2, ratio[1] * 2);
			}
		}, 1);
	}

	public static int[] getRatio(int size) {
		int ratioX = 1, ratioY = 2;
		return new int[] { ratioX, ratioY };
	}
	public void linkPartyToDungeon() {
		for (DungeonPartyPlayer dplayer : getParty().getTeam()) {
			Player player = dplayer.getPlayer();
			player.getControlerManager().startControler("DungeonControler",
					this);
			player.stopAll();
			player.reset();
			player.setForceMultiArea(true);
			player.getCombatDefinitions().setSpellBook(3);
			dplayer.refreshDeaths();
			player.getPackets().sendGameMessage("");
			player.getPackets().sendGameMessage("-Welcome to Daemonheim-");
			player.getPackets().sendGameMessage(
					"Floor " + getParty().getFloor() + " Complexity "
							+ getParty().getComplexity() + " (Full)");
			player.getPackets().sendGameMessage("Dungeon Size: " + "Small");
			player.getPackets().sendGameMessage(
					"Party Size:Dificulty " + getParty().getTeam().size() + ":"
							+ getParty().getTeam().size());
			player.getPackets().sendGameMessage("");
		}
	}

	public DungeonPartyPlayer getDPlayer(Player player) {
		return getParty().getDPlayer(player);
	}

	public void spawnRandomNPCS(RoomReference reference) {
		int floorType = getParty().getFloorType();
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getPassiveCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, false);
		spawnNPC(reference, DungeonUtils.getPassiveCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, false);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
		spawnNPC(reference, DungeonUtils.getGuardianCreature(floorType),
				2 + Utils.getRandom(13), 2 + Utils.getRandom(13), true, true);
	}

	public void spawnNPC(RoomReference reference, int id, int x, int y) {
		spawnNPC(reference, id, x, y, false, false);
	}

	/*
	 * x 0-15, y 0-15
	 */
	public void stairs(RoomReference reference, int x, int y) {
			int rotation = getDungeon().getRoom(reference).getRotation();
			int opl = 1;
			if (rotation != 0) {
				for (int rotate = 0; rotate < (4 - rotation); rotate++) {
					opl++;
					int fakeX = x;
					int fakeY = y;
					x = fakeY;
					y = 15 - fakeX;
				}
			}
			WorldObject object;
			object = new WorldObject(3784, 10, rotation+3,
					((boundChuncks[0] << 3) + reference.getX() * 16) + x,
					((boundChuncks[1] << 3) + reference.getY() * 16) + y, 0);
				World.spawnObject(object, false);
		}

	public void spawnGlut(RoomReference reference, int id, int x, int y,
			boolean check, boolean guardian) {
		int rotation = getDungeon().getRoom(reference).getRotation();
		if (rotation != 0) {
			for (int rotate = 0; rotate < (4 - rotation); rotate++) {
				int fakeX = x;
				int fakeY = y;
				x = fakeY;
				y = 15 - fakeX;
			}
		}
		WorldTile tile = new WorldTile(
				((boundChuncks[0] << 3) + reference.getX() * 16) + x,
				((boundChuncks[1] << 3) + reference.getY() * 16) + y, 0);
		if (check
				&& !World.canMoveNPC(0, tile.getX(), tile.getY(),
						NPCDefinitions.getNPCDefinitions(id).size))
			return;
		
	}
	public void spawnLuminescent(RoomReference reference, int id, int x, int y,
			boolean check, boolean guardian) {
		int rotation = getDungeon().getRoom(reference).getRotation();
		if (rotation != 0) {
			for (int rotate = 0; rotate < (4 - rotation); rotate++) {
				int fakeX = x;
				int fakeY = y;
				x = fakeY;
				y = 15 - fakeX;
			}
		}
		WorldTile tile = new WorldTile(
				((boundChuncks[0] << 3) + reference.getX() * 16) + x,
				((boundChuncks[1] << 3) + reference.getY() * 16) + y, 0);
		if (check
				&& !World.canMoveNPC(0, tile.getX(), tile.getY(),
						NPCDefinitions.getNPCDefinitions(id).size))
			return;
	//	NPC n = new Luminescent(id, tile, this, reference);
	//	n.setForceMultiArea(true);
		if (guardian) {
		//	n.setForceAgressive(true);
	//		visibleMap[reference.getX()][reference.getY()].addGuardian(n);
		}
	}
	public void spawnTokash(RoomReference reference, int id, int x, int y,
			boolean check, boolean guardian) {
		int rotation = getDungeon().getRoom(reference).getRotation();
		if (rotation != 0) {
			for (int rotate = 0; rotate < (4 - rotation); rotate++) {
				int fakeX = x;
				int fakeY = y;
				x = fakeY;
				y = 15 - fakeX;
			}
		}
		WorldTile tile = new WorldTile(
				((boundChuncks[0] << 3) + reference.getX() * 16) + x,
				((boundChuncks[1] << 3) + reference.getY() * 16) + y, 0);
		if (check
				&& !World.canMoveNPC(0, tile.getX(), tile.getY(),
						NPCDefinitions.getNPCDefinitions(id).size))
			return;
		
	}
	public void spawnAstea(RoomReference reference, int id, int x, int y,
				boolean check, boolean guardian) {
			int rotation = getDungeon().getRoom(reference).getRotation();
			if (rotation != 0) {
				for (int rotate = 0; rotate < (4 - rotation); rotate++) {
					int fakeX = x;
					int fakeY = y;
					x = fakeY;
					y = 15 - fakeX;
				}
			}
			WorldTile tile = new WorldTile(
					((boundChuncks[0] << 3) + reference.getX() * 16) + x,
					((boundChuncks[1] << 3) + reference.getY() * 16) + y, 0);
			if (check
					&& !World.canMoveNPC(0, tile.getX(), tile.getY(),
							NPCDefinitions.getNPCDefinitions(id).size))
				return;
			
		}

	public void spawnNPC(RoomReference reference, int id, int x, int y,
			boolean check, boolean guardian) {
		int rotation = getDungeon().getRoom(reference).getRotation();
		if (rotation != 0) {
			for (int rotate = 0; rotate < (4 - rotation); rotate++) {
				int fakeX = x;
				int fakeY = y;
				x = fakeY;
				y = 15 - fakeX;
			}
		}
		WorldTile tile = new WorldTile(
				((boundChuncks[0] << 3) + reference.getX() * 16) + x,
				((boundChuncks[1] << 3) + reference.getY() * 16) + y, 0);
		if (check
				&& !World.canMoveNPC(0, tile.getX(), tile.getY(),
						NPCDefinitions.getNPCDefinitions(id).size))
			return;
		NPC n = guardian ? new Guardian(id, tile, this, reference) : World
				.spawnNPC(id, tile, -1, true, true);
		n.setForceMultiArea(true);
		if (guardian) {
			n.setForceAgressive(true);
			visibleMap[reference.getX()][reference.getY()].addGuardian(n);
		}
	}

	public void updateGuardian(RoomReference reference) {
		if (visibleMap[reference.getX()][reference.getY()].removeGuardians()) {
			for (DungeonPartyPlayer dplayer : getParty().getTeam()) {
				Player player = dplayer.getPlayer();
				RoomReference playerReference = getCurrentRoomReference(player);
				if (playerReference.getX() == reference.getX()
						&& playerReference.getY() == reference.getY())
					playMusic(player, reference);
			}
		}
	}

	public void removePlayer(Player player) {
		player.stopAll();
		player.reset();
		player.setForceMultiArea(false);
		player.getCombatDefinitions().removeDungeonneringBook();
	}
public int l;
protected RoomReference bossroom;
	public void load(final int size) {
		dungeoneeringLevel = getParty().getDungeoneeringLevel();
		visibleMap = new VisibleRoom[DungeonConstants.DUNGEON_RATIO[size][0]][DungeonConstants.DUNGEON_RATIO[size][1]];
		// slow executor loads dungeon as it may take up to few secs
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					// generates dungeon structure
					setDungeon(new Dungeon(getParty().getFloorType(), getParty()
							.getComplexity(), size));
					l = getDungeon().setBossRoom(dungeoneeringLevel);
					// finds an empty map area bounds
					// reserves all map area
					RegionBuilder.cutMap(boundChuncks[0], boundChuncks[1],
							getDungeon().getMapWidth() * 2,
							getDungeon().getMapHeight() * 2, 0);
					// loads start room
					loadRoom(getDungeon().getStartRoomReference());
					stage = 1;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}

	public boolean hasStarted() {
		return stage > 0;
	}

	public DungeonPartyManager getParty() {
		return party;
	}

	public void setParty(DungeonPartyManager party) {
		this.party = party;
	}

	public Dungeon getDungeon() {
		return dungeon;
	}

	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
}
