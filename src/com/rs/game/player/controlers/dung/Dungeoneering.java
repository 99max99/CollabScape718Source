package com.rs.game.player.controlers.dung;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.game.player.controlers.dung.Floors;

import com.rs.cores.CoresManager;
import com.rs.game.Entity;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.others.RuneDungMonsters;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.FadingScreen;
import com.rs.utils.Logger;
import com.rs.utils.Misc;

/**
 * 
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2013-01-02
 * Modified by Jaejoong Kim @ Xiduth.
 */
public class Dungeoneering implements Serializable {

	private static final long serialVersionUID = 1110146966680563250L;

	private CopyOnWriteArrayList<Player> team; 

	private int gameInterface = 256;
	private long startTime;

	private ArrayList<WorldTile> randomNormals = new ArrayList<WorldTile>();
	private ArrayList<WorldTile> randomBosses = new ArrayList<WorldTile>();

	private Map<Integer, RuneDungMonsters> monsters = new HashMap<Integer, RuneDungMonsters>();

	private Floors floor;

	private int normalR;
	private int bossR;

	private boolean loadedGame = false;

	/**
	 * An array of values, used for the empty Dynamic region.
	 */
	private int[] boundChunks;

	public Dungeoneering(final CopyOnWriteArrayList<Player> team, int[] boundChunks) {
		this.team = team;		
		this.boundChunks = boundChunks;
		this.startTime = Misc.currentTimeMillis();
		startGame();
	}
	
	public void fade(final Player player) {
		final long time = FadingScreen.fade(player);
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					FadingScreen.unfade(player, time, new Runnable() {
						@Override
						public void run() {

						}
					});
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}

		});
	}

	private void startGame() {;
		if (!loadedGame) {
			int lvl = 0;
			for (Player player : team) {
				fade(player);
				lvl += player.getSkills().getLevel(Skills.DUNGEONEERING);
			}
			setFloor(getFloorByLevel(lvl));
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					if (getFloor() == Floors.FIRST)
						RegionBuilder.copyAllPlanesMap(14, 554, boundChunks[0], boundChunks[1], 20);
					else
						RegionBuilder.copyAllPlanesMap(10, 688, boundChunks[0], boundChunks[1], 20);
					CoresManager.slowExecutor.schedule(new Runnable() {

						@Override
						public void run() {
							for (Player player : team) {
								int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
								player.getPrayer().restorePrayer(
										maxPrayer);
								player.heal(player.getHitpoints() * 10);
								player.setNextWorldTile(getWorldTile(10, 8));
								player.getCombatDefinitions().resetSpells(true);
								player.setInDung(true);
								player.stopAll();
								player.sendMessage("<col=FF0000>You step into the misty dungeon and prepare for battle on the " + getFloor().name().toLowerCase() + " floor.");
							}
							loadObjects();
							loadMonsters();
							loadItems();
						}
					}, 0, TimeUnit.MILLISECONDS);
				}
			}, 0, TimeUnit.MILLISECONDS);
			loadedGame = true;
		}
	}

	private Floors getFloorByLevel(int level) {
		if (level < 79)
			return Floors.FIRST;
		else
			return Floors.SECOND;

	}
	
	private void loadObjects() {
		World.spawnObject(new WorldObject(43589, 10, 0, new WorldTile(getWorldTile(5, 9))), true);
		World.spawnObject(new WorldObject(43589, 10, 0, new WorldTile(getWorldTile(4, 9))), true);
		World.spawnObject(new WorldObject(43589, 10, 0, new WorldTile(getWorldTile(6, 9))), true);
		World.spawnObject(new WorldObject(43589, 10, 0, new WorldTile(getWorldTile(5, 7))), true);
		World.spawnObject(new WorldObject(43589, 10, 0, new WorldTile(getWorldTile(5, 7))), true);
		World.spawnObject(new WorldObject(43589, 10, 0, new WorldTile(getWorldTile(4, 7))), true);
		World.spawnObject(new WorldObject(43589, 10, 0, new WorldTile(getWorldTile(6, 7))), true);
		Iterator<Portals> it = Portals.getMap().values().iterator();
		while(it.hasNext()) {
			Portals portal = it.next();
			World.spawnObject(new WorldObject(2273, 10, 0, getWorldTile(portal.spawnTile().getX(), portal.spawnTile().getY())), true);
		}
		World.addGroundItem(new Item(20821, 1), getWorldTile(5, 7), null, false, 1000);
		World.addGroundItem(new Item(16401, 1), getWorldTile(5, 7), null, false, 1000);
		World.addGroundItem(new Item(16423, 1), getWorldTile(5, 7), null, false, 1000);	
		World.addGroundItem(new Item(16953, 1), getWorldTile(7, 7), null, false, 1000);
		World.addGroundItem(new Item(16951, 1), getWorldTile(7, 7), null, false, 1000);
		World.addGroundItem(new Item(16963, 1), getWorldTile(7, 7), null, false, 1000);
		World.addGroundItem(new Item(16421, 1), getWorldTile(7, 7), null, false, 1000);
		World.addGroundItem(new Item(16685, 1), getWorldTile(7, 7), null, false, 1000);
		World.addGroundItem(new Item(17255, 1), getWorldTile(7, 7), null, false, 1000);
		World.addGroundItem(new Item(16687, 1), getWorldTile(4, 7), null, false, 1000);
		World.addGroundItem(new Item(17257, 1), getWorldTile(4, 7), null, false, 1000);
		World.addGroundItem(new Item(16949, 1), getWorldTile(4, 7), null, false, 1000);
		int[] pots = {16683, 17253, 16705, 139, 139, 139, 139, 139, 15272, 15272, 15272, 15272, 15272, 15272, 15332, 15332, 15332  };
		for(int i = 0; i < pots.length; i++) {
		World.addGroundItem(new Item(pots[i]), new WorldTile(getWorldTile(6, 7)), null, false, 1000);
		World.addGroundItem(new Item(139, 1), getWorldTile(5, 9), null, false, 1000);
		World.addGroundItem(new Item(15272, 1), getWorldTile(5, 9), null, false, 1000);
		World.addGroundItem(new Item(139, 1), getWorldTile(5, 9), null, false, 1000);
		World.addGroundItem(new Item(139, 1), getWorldTile(5, 9), null, false, 1000);
		World.addGroundItem(new Item(6685, 1), getWorldTile(5, 9), null, false, 1000);
		World.addGroundItem(new Item(15300, 1), getWorldTile(5, 9), null, false, 1000);
		World.addGroundItem(new Item(15272, 1), getWorldTile(4, 9), null, false, 1000);
		World.addGroundItem(new Item(15272, 1), getWorldTile(4, 9), null, false, 1000);
		World.addGroundItem(new Item(139, 1), getWorldTile(4, 9), null, false, 1000);
		World.addGroundItem(new Item(6685, 1), getWorldTile(4, 9), null, false, 1000);
		World.addGroundItem(new Item(15330, 1), getWorldTile(4, 9), null, false, 1000);
		World.addGroundItem(new Item(15272, 1), getWorldTile(7, 9), null, false, 1000);
		World.addGroundItem(new Item(15272, 1), getWorldTile(7, 9), null, false, 1000);
		World.addGroundItem(new Item(139, 1), getWorldTile(7, 9), null, false, 1000);
		World.addGroundItem(new Item(6685, 1), getWorldTile(7, 9), null, false, 1000);
		World.addGroundItem(new Item(15300, 1), getWorldTile(7, 9), null, false, 1000);
		World.addGroundItem(new Item(15272, 1), getWorldTile(6, 9), null, false, 1000);
		World.addGroundItem(new Item(15272, 1), getWorldTile(6, 9), null, false, 1000);
		World.addGroundItem(new Item(139, 1), getWorldTile(6, 9), null, false, 1000);
		World.addGroundItem(new Item(6685, 1), getWorldTile(6, 9), null, false, 1000);
		World.addGroundItem(new Item(15300, 1), getWorldTile(6, 9), null, false, 1000);
		}
}

	private void loadTileList() {
		if (getFloor() == Floors.FIRST) {
			WorldTile[] tiles = new WorldTile[] { getWorldTile(24, 25),
					getWorldTile(24, 7), getWorldTile(40, 7),
					getWorldTile(8, 23), getWorldTile(25, 7),
					getWorldTile(23, 22), getWorldTile(20, 21),
					getWorldTile(20, 29) };
			for (WorldTile tile : tiles)
				randomNormals.add(tile);
			WorldTile[] bossTiles = new WorldTile[] { getWorldTile(40, 20), getWorldTile(40, 21), getWorldTile(40, 22), getWorldTile(40, 23) };
			for (WorldTile tile : bossTiles)
				randomBosses.add(tile);
		} else if (getFloor() == Floors.SECOND) {
			WorldTile[] tiles = new WorldTile[] { getWorldTile(23, 8),
					getWorldTile(23, 24), getWorldTile(23, 28),
					getWorldTile(39, 24) };
			for (WorldTile tile : tiles)
				randomNormals.add(tile);
			WorldTile[] bossTiles = new WorldTile[] { getWorldTile(25, 55),
					getWorldTile(35, 55), 
					getWorldTile(39, 39),
					getWorldTile(39, 10) };
			for (WorldTile tile : bossTiles)
				randomBosses.add(tile);
		}
	}

	private void loadMonsters() {
		new RuneDungMonsters(11226, getWorldTile(7, 5), MonsterTypes.NORMAL);
		loadTileList();
		Iterator<Monsters> it = Monsters.getMap().values().iterator();
		while(it.hasNext()) {
			Monsters monster = it.next();
			if (monster.getFloor() == getFloor()) {
				WorldTile spawnLoc = monster.getTypes() == MonsterTypes.NORMAL ? getRandomNormalSpawn() : getRandomBossSpawn();
				if (monster.getTypes() == MonsterTypes.NORMAL)
					randomNormals.remove(normalR);
				else
					randomBosses.remove(bossR);
				monsters.put(monster.getId(), new RuneDungMonsters(monster.getId(), spawnLoc, monster.getTypes()));
			}
		}
	}

	private void loadItems() {
		for (Player player : team) {
			player.getInventory().addItem(16945, 1);
			player.getInventory().addItem(18367, 1);
			player.getInventory().addItem(16337, 1);
			player.getInventory().addItem(18373, 1);
			player.getInventory().addItem(18371, 1);
			player.getInventory().addItem(17017, 1);
			player.getInventory().addItem(16472, 300);
			player.getInventory().addItem(17291, 1);
			player.getInventory().addItem(16709, 1);
			player.getInventory().addItem(16452, 300);
			player.getInventory().addItem(16709, 1);
			int[] elementals = { 556, 557, 555 };
			int[] casts = { 558, 560, 562, 565 };
			for (int i = 0; i < elementals.length; i++) {
				player.getInventory().addItem(elementals[i], 500);
			}
			for (int i = 0; i < casts.length; i++) {
				player.getInventory().addItem(casts[i], 150);
			}
		}
	}

	private WorldTile getRandomBossSpawn(){
		bossR = Misc.random(randomBosses.size());
		return randomBosses.get(bossR);
	}

	private WorldTile getRandomNormalSpawn() {
		normalR = Misc.random(randomNormals.size());
		return randomNormals.get(normalR);
	}

	public void removeMonster(int npc) {
		synchronized (monsters) {
			monsters.remove(npc);
			sendInfo();
			for (Player player : team) 
			player.getSkills().addXp(Skills.DUNGEONEERING, 8);
			if (getFloor() == Floors.SECOND) {
				for (Player player : team) {
				player.getSkills().addXp(Skills.DUNGEONEERING, 200);
				player.setDungTokens(player.getDungTokens() + 100);
			}
			}
		}
			if (monsters.size() == 0) {
				for (Player player : team) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You have completed the dungeon, speak with the starting NPC.");
					NPC guide = findNPC(11226); 
					if (guide != null)
						player.getHintIconsManager().addHintIcon(guide, 0, -1, false);
						player.getInventory().reset();
						player.getEquipment().reset();						
				}
			}
		}
	
	public NPC findNPC(int id) {
		for (NPC npc : World.getNPCs()) {
			if (npc == null || npc.getId() != id)
				continue;
			return npc;
		}
		return null;
	}

	public void sendInfo() {
		for (Player player : team) {
			for (int i = 0; i < 16; i++) {
				player.getPackets().sendIComponentText(gameInterface, i, "");
			}
			player.getPackets().sendIComponentText(gameInterface, 5, "Dungeoneering Information");
			player.getPackets().sendIComponentText(gameInterface, 11, "Damage Inflicted:");
			player.getPackets().sendIComponentText(gameInterface, 12, "Monsters Left:");
			player.getPackets().sendIComponentText(gameInterface, 13, "Players:");
			player.getPackets().sendIComponentText(gameInterface, 14, "Floor:");
			if (player.getControlerManager().getControler() instanceof RuneDungGame)
				player.getPackets().sendIComponentText(gameInterface, 6, "                     " + ((RuneDungGame) player.getControlerManager().getControler()).getDamage());
			player.getPackets().sendIComponentText(gameInterface, 7, "                     "  + getMonsterSize());
			player.getPackets().sendIComponentText(gameInterface, 8, "                     " + getTeam().size());
			player.getPackets().sendIComponentText(gameInterface, 9, "                     " + (getFloor() == null ? -1 : getFloor().ordinal() + 1));
			player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 11 : 27, gameInterface);
		}
	}

	public void finishGame(Player player) {
		if (((RuneDungGame) player.getControlerManager().getControler()).isGaveReward())
			return;
		if (team.size() == 0)
			removeMapChunks();
		((RuneDungGame) player.getControlerManager().getControler()).setGaveReward(true);
		giveReward(player);
		player.lock();
		for (Item item : player.getInventory().getItems().toArray()) {
			if (item == null) continue;
			player.getInventory().deleteItem(item);
		}
		for (Item item : player.getEquipment().getItems().toArray()) {
			if (item == null) continue;
			player.getEquipment().deleteItem(item.getId(), item.getAmount());
		}
		player.unlock();
		player.getAppearence().generateAppearenceData();
	}

	public void giveReward(Player player) {
		int interfaceId = 921;
		int damage = ((RuneDungGame) player.getControlerManager().getControler()).getDamage();
		long time = (TimeUnit.MILLISECONDS.toSeconds(Misc.currentTimeMillis() - startTime));
		int tokens = (int) (time / 5) + damage / 100 * 35;
		for (int i = 0; i < 45; i++) {
			player.getPackets().sendIComponentText(interfaceId, i, "");
		}
		player.getPackets().sendIComponentText(interfaceId, 44, "Dungeoneering Rewards");
		player.getPackets().sendIComponentText(interfaceId, 11, "Partners");
		player.getPackets().sendIComponentText(interfaceId, 17, "Partner No. 1:");
		player.getPackets().sendIComponentText(interfaceId, 12, "Partner No. 2:");
		player.getPackets().sendIComponentText(interfaceId, 13, "Partner No. 3:");
		if (team.size() > 0)
			player.getPackets().sendIComponentText(interfaceId, 14, team.get(0).getDisplayName());
		if (team.size() >= 2)
			player.getPackets().sendIComponentText(interfaceId, 15, team.get(1).getDisplayName());
		if (team.size() >= 3)
			player.getPackets().sendIComponentText(interfaceId, 16, team.get(2).getDisplayName());
		player.getPackets().sendIComponentText(interfaceId, 24, "Details");
		player.getPackets().sendIComponentText(interfaceId, 25, "Floor Completed:");
		player.getPackets().sendIComponentText(interfaceId, 26, "Total Damage:");
		player.getPackets().sendIComponentText(interfaceId, 27, "Time Spent:");
		player.getPackets().sendIComponentText(interfaceId, 28, "EXP/Tokens:");
		int totalDmg = 0;
		for (Player t : team) {
			synchronized (t) {
				totalDmg += ((RuneDungGame) t.getControlerManager().getControler()).getDamage();
			}
		}
		player.getPackets().sendIComponentText(interfaceId, 29, "" + (getFloor().ordinal()) + 1);
		player.getPackets().sendIComponentText(interfaceId, 30, "" + totalDmg);
		player.getPackets().sendIComponentText(interfaceId, 31, "" + time + " sec.");
		player.getPackets().sendIComponentText(interfaceId, 32, "" + (damage / 100 * 2 * 25) + "/" + tokens);
		player.getInterfaceManager().sendInterface(interfaceId);
		player.sendMessage("You have received dungeoneering experience, and " + tokens + " tokens.");
		int lvl = player.getSkills().getLevel(Skills.DUNGEONEERING);
		int bonus = 1;
		if (World.dungeoneering == true) {
			bonus = 2;
		}
		player.getSkills().addXp(Skills.DUNGEONEERING, (getExperienceToAdd(lvl, damage)/5)*bonus);
		player.completedDungeons++;
		player.setDungTokens(player.getDungTokens() + tokens*bonus);
		int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
		player.getPrayer().restorePrayer(
				maxPrayer);
		player.heal(player.getHitpoints() * 10);
	}

	
	private double getExperienceToAdd(int lvl, int damage) {
		double doubler = lvl < 30 ? 1 : lvl > 30 && lvl < 50 ? 1.5 : lvl > 50 && lvl < 60 ? 3 : lvl > 60 && lvl < 70 ? 3.5 : lvl > 70 && lvl < 90 ? 6 : lvl > 90 && lvl < 100 ? 7.5 : lvl > 100 ? 15 : 4;
		return (damage / 10) * doubler;
	}

	public void removeFromGame(Player player, boolean logout) {
		player.setInDung(false);
		for (Item item : player.getInventory().getItems().toArray()) {
			if (item == null) continue;
			player.getInventory().deleteItem(item);
		}
		for (Item item : player.getEquipment().getItems().toArray()) {
			if (item == null) continue;
			player.getEquipment().deleteItem(item.getId(), item.getAmount());
		}
		if (player.getFamiliar() != null) {
			if (player.getFamiliar().getBob() != null)
				player.getFamiliar().getBob().getBeastItems().clear();
			player.getFamiliar().dissmissFamiliar(false);
		}
		player.getCombatDefinitions().setSpellBook(0);
		player.getCombatDefinitions().setDungSpellBook(false);
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 746 : 548, player.getInterfaceManager().hasRezizableScreen() ? 11: 27);
		finishGame(player);
		if (!logout)
			player.setNextWorldTile(new WorldTile(3450, 3718, 0));
		else
			player.setLocation(new WorldTile(3450, 3718, 0));
		team.remove(player);
	}

	private void removeMapChunks() {
		Iterator<RuneDungMonsters> monsterIterator = monsters.values()
				.iterator();
		synchronized (monsters) {
			while (monsterIterator.hasNext()) {
				RuneDungMonsters npc = monsterIterator.next();
				npc.setFinished(true);
				World.updateEntityRegion(npc);
				monsterIterator.remove();
			}
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChunks[0], boundChunks[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);
	}

	/**
	 * Retrieves a new {@code WorldTile} using the boundChunks of the dynamic region.
	 * @param mapX The 'x' coordinate value.
	 * @param mapY The 'y' coordinate value.
	 * @return a new {@code WorldTile}
	 */
	public WorldTile getWorldTile(int mapX, int mapY) {
		return new WorldTile(boundChunks[0] * 8 + mapX, boundChunks[1] * 8 + mapY, 0);
	}

	public CopyOnWriteArrayList<Player> getTeam() {
		return team;
	}

	public Map<Integer, RuneDungMonsters> getMonsters() {
		return monsters;
	}

	public int getMonsterSize() {
		return monsters.size();
	}

	public ArrayList<WorldTile> getNormalsList() {
		return randomNormals;
	}

	public int getRandom() {
		return normalR;
	}

	public Floors getFloor() {
		return floor;
	}

	public void setFloor(Floors floor) {
		this.floor = floor;
	}

	public int[] getBoundChunks() {
		return boundChunks;
	}
	
	public boolean logout() {
		return false;
	}

	public void sendDrop(WorldTile worldTile, MonsterTypes type, Entity owner) {
		  int[] rares = { 20833, 16425, 16689, 17259, 16709, 16665, 16687, 17257, 16953, 16423, 16401, 16907};
		  int[] basics = { 17239, 17607, 17341, 16889, 16935, 17019, 17063,
		    16647, 16669, 16691, 16383, 16361, 16405, 16273, 15753,
		    15761, 16281, 16347, 16369, 16391, 16413, 16447, 16655,
		    16677, 16699, 16721, 16897, 16943, 16957, 16961, 16973,
		    17027, 17095, 17247, 17349, 20872, 15765, 16285, 16307, 16351,
		    16373, 16395, 16417, 16457, 16659, 16681, 16703, 16725, 16805,
		    16901, 16947, 17031, 17111, 17251, 17353, 15755, 16275, 16341, 16385, 
		    16407, 16432, 16649, 16671, 16693, 16715, 16765, 16891, 16931, 16937, 17021, 
		    17071, 17242, 17344, 15757, 16277, 16343, 16387, 16409, 16437, 16651, 16673, 
		    16695, 16717, 16773, 16893, 16939, 17023, 17079, 17243, 17345, 15759, 16279, 
		    16345, 16389, 16411, 16442, 16653, 16675, 16697, 16719, 16781, 16895, 16941, 
		    17025, 17087, 17245, 17347, 15763, 16283, 16349, 16393, 16415, 16452, 16657, 
		    16679, 16701, 16723, 16797, 16899, 16945, 17029, 17103, 17429, 17351, 16661, 
		    17253, 16683, 16949, 16419, 16705, 16397, 16903, 16663, 16685, 17255, 16707, 
		    16951, 16421, 16399, 16905};
		int count;
		switch(type){
		case BOSSES:
			count = 0;
			for(int i = 0; i < 2; i++) {
				if (++count > 2) break;
				World.addGroundItem(new Item(rares[Misc.random(rares.length)]), worldTile, (Player) owner, true, 60);
			}
			break;
		case NORMAL:
			count = 0;
			for(int i = 0; i < 2; i++) {
				if (++count > 2) break;
				Item item = new Item(basics[Misc.random(basics.length)]);
				if (item.getDefinitions().isNoted())
					item.setId(item.getDefinitions().getCertId());
				World.addGroundItem(item, worldTile, (Player) owner, true, 60);
			}
			break;
		}
	}


}
