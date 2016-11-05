package com.rs.game.player.content.dungeoneering;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.content.dungeoneering.rooms.BossRoom;
import com.rs.game.player.content.dungeoneering.rooms.HandledRoom;
import com.rs.game.player.content.dungeoneering.rooms.NormalRoom;
import com.rs.game.player.content.dungeoneering.rooms.RoomEvent;
import com.rs.game.player.content.dungeoneering.rooms.StartRoom;
import com.rs.game.*;
import com.rs.utils.Utils;
import com.rs.game.npc.dungeonnering.*;

public class DungeonConstants {

	public static int ROTATIONS_COUNT = 4;

	/*
	 * objects handling
	 */
	public static final int[] DUNGEON_DOORS = new int[] { 50342 },
			DUNGEON_EXITS = new int[] { 51156 };

	/*
	 * floor types
	 */
	public static final int FROZEN_FLOORS = 0, ABANDONED_FLOORS = 1,
			FURNISHED_FLOORS = 2, ABANDONED2_FLOORS = 3, OCCULT_FLOORS = 4,
			WARPED_FLOORS = 5;

	/*
	 * dungeon sizes
	 */
	public static final int SMALL_DUNGEON = 0, MEDIUM_DUNGEON = 1,
			LARGE_DUNGEON = 2;

	public static final int[][] DUNGEON_RATIO = new int[][] {
			new int[] { 4, 4 }, new int[] { 4, 8 }, new int[] { 8, 8 } };

	/*
	 * door directions
	 */
	public static final int EAST_DOOR = 0, WEST_DOOR = 1, NORTH_DOOR = 2,
			SOUTH_DOOR = 3;

	public static final int[] START_ROOM_MUSICS = new int[] { 822 // FROZEN_FLOORS
	};

	public static final int[][] DANGEROUS_MUSICS = new int[][] {
	// FROZEN_FLOORS
	new int[] { 832, 834, 827, 811, 824, 808, 831, 810, 833, 837, 814, 821 // equal
																			// to
																			// boss
																			// unlockable
	} };

	public static final int[][] SAFE_MUSICS = new int[][] {
	// FROZEN_FLOORS
	new int[] { 804, 805, 806, 807, 812, 813, 826, 823, 825, 826, 828, 829,
			830, 835, 836

	} };

	/*
	 * Creatures
	 */
	public static final int[][] GUARDIAN_CREATURES = new int[][] {
	// FROZEN_FLOORS
	new int[] { 10765, // ice giants
			10772, // frost dragons
			10777, // iron dragon
			10784, // ice troll
			10793, // Thrower troll
			10799, // brute
			10816, // red dragon
			10823, // ghost
			10834, // Mysterious shade
			10907, // bat
			10912, // giant bat
			10698, // night spider
	} };

	/*
	 * Creatures
	 */
	public static final int[][] PASSIVE_CREATURES = new int[][] {
	// FROZEN_FLOORS
	new int[] { 11086 // Protomastyx
	} };

	public static final StartRoom[][] START_ROOMS = new StartRoom[][] {
	// FROZEN_FLOORS
	new StartRoom[] {
			new StartRoom(new int[] { 1, 2, 3, 4, 5, 6 }, 14, 632, EAST_DOOR,
					WEST_DOOR, NORTH_DOOR, SOUTH_DOOR),
			new StartRoom(new int[] { 1, 2, 3, 4, 5, 6 }, 14, 624, SOUTH_DOOR),
			new StartRoom(new int[] { 1, 2, 3, 4, 5, 6 }, 14, 626, WEST_DOOR,
					SOUTH_DOOR),
			new StartRoom(new int[] { 1, 2, 3, 4, 5, 6 }, 14, 630, WEST_DOOR,
					NORTH_DOOR, SOUTH_DOOR) } };

	public static final NormalRoom[][] NORMAL_ROOMS = new NormalRoom[][] {
	// FROZEN_FLOORS
	new NormalRoom[] {
			new NormalRoom(1, 8, 240, SOUTH_DOOR),
			new NormalRoom(1, 10, 240, SOUTH_DOOR),
			new NormalRoom(1, 12, 240, SOUTH_DOOR)

			,
			new NormalRoom(1, 8, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 10, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 12, 242, SOUTH_DOOR, WEST_DOOR)

			,
			new NormalRoom(1, 8, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 10, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 12, 244, NORTH_DOOR, SOUTH_DOOR)

			,
			new NormalRoom(1, 8, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 10, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 12, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR)

			,
			new NormalRoom(1, 8, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR),
			new NormalRoom(1, 10, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR),
			new NormalRoom(1, 12, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR)

			,
			new NormalRoom(1, 14, 240, SOUTH_DOOR),
			new NormalRoom(1, 16, 240, SOUTH_DOOR),
			new NormalRoom(1, 18, 240, SOUTH_DOOR),
			new NormalRoom(1, 20, 240, SOUTH_DOOR),
			new NormalRoom(1, 22, 240, SOUTH_DOOR)

			,
			new NormalRoom(1, 24, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 26, 242, SOUTH_DOOR, WEST_DOOR)

			,
			new NormalRoom(1, 28, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR)

			,
			new NormalRoom(1, 30, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 32, 244, NORTH_DOOR, SOUTH_DOOR)

			,
			new NormalRoom(1, 34, 242, SOUTH_DOOR, WEST_DOOR)

			,
			new NormalRoom(1, 40, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 42, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 44, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 46, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 48, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 50, 244, NORTH_DOOR, SOUTH_DOOR)

			,
			new NormalRoom(1, 38, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR)

			,
			new NormalRoom(1, 36, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR)

			,
			new NormalRoom(1, 52, 240, SOUTH_DOOR),
			new NormalRoom(1, 56, 240, SOUTH_DOOR),
			new NormalRoom(1, 56, 240, SOUTH_DOOR),
			new NormalRoom(1, 58, 240, SOUTH_DOOR),
			new NormalRoom(1, 60, 240, SOUTH_DOOR)

			,
			new NormalRoom(1, 62, 240, SOUTH_DOOR),
			new NormalRoom(1, 64, 240, SOUTH_DOOR),
			new NormalRoom(1, 66, 240, SOUTH_DOOR),
			new NormalRoom(1, 68, 240, SOUTH_DOOR),
			new NormalRoom(1, 70, 240, SOUTH_DOOR),
			new NormalRoom(1, 72, 240, SOUTH_DOOR),
			new NormalRoom(1, 74, 240, SOUTH_DOOR),
			new NormalRoom(1, 76, 240, SOUTH_DOOR),
			new NormalRoom(1, 78, 240, SOUTH_DOOR)

			,
			new NormalRoom(1, 62, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 64, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 66, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 68, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 70, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 72, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 74, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 76, 242, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 78, 242, SOUTH_DOOR, WEST_DOOR)

			,
			new NormalRoom(1, 62, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 64, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 66, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 68, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 70, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 72, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 74, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 76, 244, NORTH_DOOR, SOUTH_DOOR),
			new NormalRoom(1, 78, 244, NORTH_DOOR, SOUTH_DOOR)

			,
			new NormalRoom(1, 62, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 64, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 66, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 68, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 70, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 72, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 74, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 76, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR),
			new NormalRoom(1, 78, 246, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR)

			,
			new NormalRoom(1, 62, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR),
			new NormalRoom(1, 64, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR),
			new NormalRoom(1, 66, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR)

			,
			new NormalRoom(1, 72, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR),
			new NormalRoom(1, 74, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR),
			new NormalRoom(1, 76, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR),
			new NormalRoom(1, 78, 248, NORTH_DOOR, SOUTH_DOOR, WEST_DOOR,
					EAST_DOOR) } };

	public static final HandledRoom[][] PUZZLE_ROOMS = new HandledRoom[][] {};

	public static final BossRoom[][] BOSS_ROOMS = new BossRoom[][] {
	// FROZEN_FLOORS
	new BossRoom[] {
			// Gluttonous behemoth
			new BossRoom(new RoomEvent() {
				@Override
				public void openRoom(DungeonManager dungeon,
						RoomReference reference) {
					int id = getBossId(1,
							dungeon.getParty().getDungeoneeringLevel()*2,
							"Gluttonous behemoth");
							dungeon.spawnGlut(reference, id, 4, 2, false, false);
							dungeon.bossroom = reference;
					

				}
			}, 817, 1, 28, 624),
			//icy bones, 196:4996
			new BossRoom(new RoomEvent() {
				@Override
				public void openRoom(DungeonManager dungeon,
						RoomReference reference) {
					int level = dungeon.getParty().getDungeoneeringLevel();
					Player p = World.getPlayer(dungeon.getParty().getLeader());
					int id = getBossId(1,
								level*2,
								"To'Kash the Bloodchiller");
						dungeon.spawnTokash(reference, id, 5, 5, false, false);
						dungeon.bossroom = reference;

				}
			}, 816, 1, 26, 626),
			// Astea Frostweb
			new BossRoom(new RoomEvent() {
				@Override
				public void openRoom(DungeonManager dungeon,
						RoomReference reference) {
					int level = dungeon.getParty().getDungeoneeringLevel();
					Player p = World.getPlayer(dungeon.getParty().getLeader());
					int id = getBossId(1,
								level*2,
								"Astea Frostweb");
					//	new AsteaFrostweb(id, reference, dungeon);
				//	World.spawnNPC(id, new WorldTile(reference.getX(), reference.getY(), 0), -1, true, true);
						dungeon.spawnAstea(reference, id, 2, 2, false, false);
						dungeon.bossroom = reference;

				}
			}, 816, 1, 30, 624) } };
	
	public static int getBossId(int numberNPCSonRoom,
			int startPartyTotalCombatLevel, String npcName) {
			int recommendedLevel = startPartyTotalCombatLevel / numberNPCSonRoom;
			int lastCombatLevelDifference = -1;
			int npcId = -1;
			for (int id = 0; id < Utils.getNPCDefinitionsSize(); id++) {
				NPCDefinitions npcDef = NPCDefinitions.getNPCDefinitions(id);
				if (npcDef.name != null && npcDef.name.equals(npcName)) {
					int difference = npcDef.combatLevel - recommendedLevel;
					if (difference < 0)
						difference = -difference;
					if (lastCombatLevelDifference == -1
							|| difference < lastCombatLevelDifference
							|| (difference == lastCombatLevelDifference && NPCDefinitions
									.getNPCDefinitions(npcId).combatLevel < npcDef.combatLevel)) {
						lastCombatLevelDifference = difference;
						npcId = id;
					}
				}
			}
			return npcId;
		}

}
