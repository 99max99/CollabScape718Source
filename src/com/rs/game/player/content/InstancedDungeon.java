package com.rs.game.player.content;

import java.util.ArrayList;
import java.util.List;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ChunkGenerator;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

/**
 * @author Miles Black (bobismyname)
 */

public class InstancedDungeon extends Controler {
	
	public enum Instance {

		ARMADYL(new WorldTile(2835, 5295, 0), 351, 661, 0, new WorldTile(27, 7,
				0), new NPC[] {
				new NPC(6222, new WorldTile(22, 12, 0), -1, true),
				new NPC(6223, new WorldTile(24, 15, 0), -1, true),
				new NPC(6225, new WorldTile(20, 15, 0), -1, true),
				new NPC(6227, new WorldTile(30, 18, 0), -1, true) }), 
		BANDOS(
				new WorldTile(2863, 5357, 0), 357, 668, 0, new WorldTile(7, 14,
						0), new NPC[] {
						new NPC(6260, new WorldTile(14, 21, 0), -1, true),
						new NPC(6261, new WorldTile(11, 24, 0), -1, true),
						new NPC(6263, new WorldTile(18, 24, 0), -1, true),
						new NPC(6265, new WorldTile(17, 18, 0), -1, true) }), 
		SARADOMIN(
				new WorldTile(2923, 5256, 0), 363, 654, 0, new WorldTile(19,
						24, 0), new NPC[] {
						new NPC(6247, new WorldTile(19, 17, 0), -1, true),
						new NPC(6248, new WorldTile(14, 14, 0), -1, true),
						new NPC(6250, new WorldTile(24, 15, 0), -1, true),
						new NPC(6252, new WorldTile(15, 20, 0), -1, true) }), 
		ZAMORAK(
				new WorldTile(2925, 5332, 0), 364, 664, 0, new WorldTile(13,
						20, 0), new NPC[] {
						new NPC(6203, new WorldTile(16, 14, 0), -1, true),
						new NPC(6208, new WorldTile(20, 15, 0), -1, true),
						new NPC(6204, new WorldTile(20, 1, 0), -1, true),
						new NPC(6206, new WorldTile(10, 10, 0), -1, true) }), 
		YKLAGOR(
				new WorldTile(2523, 5232, 0), 314, 652, 0, new WorldTile(9, 18,
						0), new NPC[] { new NPC(11872,
						new WorldTile(20, 18, 0), -1, true) }),
		BLINK(
				new WorldTile(1370, 6621, 0), 170, 826, 0, new WorldTile(9, 14,
						0), new NPC[] { new NPC(12878,
						new WorldTile(13, 15, 0), -1, true) }),
		SUNFREET(
				new WorldTile(3803, 3529, 0), 470, 437, 0, new WorldTile(43,
						34, 0), new NPC[] { new NPC(15222, new WorldTile(42,
						48, 0), -1, true) }), 
		LEEUNI(new WorldTile(2836, 2576,
				0), 352, 318, 0, new WorldTile(20, 32, 0), new NPC[] { new NPC(
				13216, new WorldTile(44, 45, 0), -1, true) }), 
		CORP(
				new WorldTile(2975, 4384, 2), 368, 543, 0, new WorldTile(31,
						40, 2), new NPC[] { new NPC(8133, new WorldTile(44, 40,
						2), -1, true) });

		private WorldTile coords;
		private int chunkX;
		private int chunkY;
		private int chunkZ;
		private WorldTile landing;
		private NPC[] npcs;

		private Instance(WorldTile coords, int chunkX, int chunkY, int chunkZ,
				WorldTile landing, NPC[] npcs) {
			this.coords = coords;
			this.chunkX = chunkX;
			this.chunkY = chunkY;
			this.chunkZ = chunkZ;
			this.landing = landing;
			this.npcs = npcs;
		}

		public WorldTile getCoords() {
			return coords;
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

		public WorldTile getLanding(ChunkGenerator c) {
			return new WorldTile(landing.getX() + c.getX(), landing.getY()
					+ c.getY(), chunkX);
		}

		public NPC[] getNPCs() {
			return npcs;
		}

		public WorldTile getNPCTile(int i, ChunkGenerator c) {
			return new WorldTile(npcs[i].getWorldTile().getX() + c.getX(),
					npcs[i].getWorldTile().getY() + c.getY(), i);
		}
	}
	
	public static int maximumMembers;
	private final List<Player> dungeons = new ArrayList<Player>();
	private final List<Player> members = new ArrayList<Player>(maximumMembers);
	private ChunkGenerator chunkGenerator;
	private boolean joinMember;
	private Instance instance;

	@Override
	public void start() {
		instance = (Instance) getArguments()[3];
		Player dungeon = World.getPlayerByDisplayName((String) getArguments()[2]);
		joinMember = (boolean) getArguments()[1];
		if (!joinMember) {
			join(dungeon);
		} else {
			chunkGenerator = new ChunkGenerator(player);
			chunkGenerator.generate(instance.getChunkX(), instance.getChunkY(), instance.getChunkZ());
			player.inInstancedDungeon = true;
			dungeons.add(player);
			player.setNextWorldTile(instance.getLanding(chunkGenerator));
			player.setForceMultiArea(true);
			for (int i = 0; i < instance.getNPCs().length; i++) {
				NPC npc = World.spawnNPC(instance.getNPCs()[i].getId(), instance.getNPCTile(i, chunkGenerator), -1, true);
				npc.setForceMultiArea(true);
				npc.setForceMultiAttacked(true);
			}
		}
	}

	@Override
	public boolean logout() {
		if (player.inInstancedDungeon) {
			if (chunkGenerator != null)
				chunkGenerator.destroyRegion(false);
			player.inInstancedDungeon = false;
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966, 3401, 0));
			player.setForceMultiArea(false);
			for (int i = 0; i < instance.getNPCs().length; i++)
				World.removeNPC(instance.getNPCs()[i]);
		}
		return false;
	}
	
	@Override
	public void magicTeleported(int type) {
		if (player.inInstancedDungeon) {
			if (chunkGenerator != null)
				chunkGenerator.destroyRegion(false);
			player.inInstancedDungeon = false;
			player.setForceMultiArea(false);
			for (int i = 0; i < instance.getNPCs().length; i++)
				World.removeNPC(instance.getNPCs()[i]);
		}
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile tile) {
		if (player.inInstancedDungeon) {
			if (chunkGenerator != null)
				chunkGenerator.destroyRegion(false);
			player.inInstancedDungeon = false;
			player.setForceMultiArea(false);
			for (int i = 0; i < instance.getNPCs().length; i++)
				World.removeNPC(instance.getNPCs()[i]);
		}
		return true;
	}
	
	@Override
	public boolean processItemTeleport(WorldTile tile) {
		if (player.inInstancedDungeon) {
			if (chunkGenerator != null)
				chunkGenerator.destroyRegion(false);
			player.inInstancedDungeon = false;
			player.setForceMultiArea(false);
			for (int i = 0; i < instance.getNPCs().length; i++)
				World.removeNPC(instance.getNPCs()[i]);
		}
		return true;
	}
	
	@Override
	public boolean processObjectTeleport(WorldTile tile) {
		if (player.inInstancedDungeon) {
			if (chunkGenerator != null)
				chunkGenerator.destroyRegion(false);
			player.inInstancedDungeon = false;
			player.setForceMultiArea(false);
			for (int i = 0; i < instance.getNPCs().length; i++)
				World.removeNPC(instance.getNPCs()[i]);
		}
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
					player.sm("Your items will appear in the location of the regular boss.");
				} else if (loop == 3) {
					removeControler();
					if (instance == null) {
						player.getControlerManager().startControler("DeathEvent", Settings.HOME_PLAYER_LOCATION, false);
						stop();
						return;
					}
					player.getControlerManager().startControler("DeathEvent", instance.getCoords(), false);
					player.setNextAnimation(new Animation(-1));
					if (chunkGenerator != null)
						chunkGenerator.destroyRegion(false);
					player.setForceMultiArea(false);
					for (int i = 0; i < instance.getNPCs().length; i++)
						World.removeNPC(instance.getNPCs()[i]);
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}
	
	public void join(Player other) {
		if (player.getControlerManager().getControler() instanceof InstancedDungeon) {
			if (members.size() > maximumMembers)
				player.getDialogueManager().startDialogue("SimpleDialogue", "This session is full.");
			else {
				dungeons.add(player);
				player.setNextWorldTile(other);
				player.setForceMultiArea(true);
				player.inInstancedDungeon = true;
			}
		}
	}
	
	public void addMember(Player player) {
		if (player != null) {
			members.add(player);
		}
	}
	
	public void removeMember(Player player) {
		if (player != null) {
			members.remove(player);
		}
	}
}
